package com.example.expensestracker.feature_expense.presentation.add_edit_expense

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.feature_expense.domain.model.Category
import com.example.expensestracker.feature_expense.domain.model.InvalidExpenseException
import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.model.Price
import com.example.expensestracker.feature_expense.domain.use_case.ExpenseUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _expenseTitle = mutableStateOf(ExpenseTextFieldState(
        hint = "Enter title..."
    ))
    val expenseTitle: State<ExpenseTextFieldState> = _expenseTitle

    private val _expensePrice = mutableStateOf(ExpenseTextFieldState(
        hint = "Enter price..."
    ))
    val expensePrice: State<ExpenseTextFieldState> = _expensePrice

    private val _expenseCurrency = mutableStateOf(ExpenseTextFieldState(
        hint = "Enter currency..."
    ))
    val expenseCurrency: State<ExpenseTextFieldState> = _expenseCurrency

    private val _expenseContent = mutableStateOf(ExpenseTextFieldState(
        hint = "Enter some content"
    ))
    val expenseContent: State<ExpenseTextFieldState> = _expenseContent

    private val _expenseCategory = mutableStateOf(Expense.categories.random())
    val expenseCategory: State<Category> = _expenseCategory

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentexpenseId: Int? = null

    init {
        savedStateHandle.get<Int>("expenseId")?.let { expenseId ->
            if(expenseId != -1) {
                viewModelScope.launch {
                    expenseUseCases.getExpense(expenseId)?.also { expense ->
                        currentexpenseId = expense.id
                        _expenseTitle.value = expenseTitle.value.copy(
                            text = expense.title,
                            isHintVisible = false
                        )
                        _expensePrice.value = expensePrice.value.copy(
                            text = expense.price.amount.toString(),
                            isHintVisible = false
                        )
                        _expenseCurrency.value = expenseCurrency.value.copy(
                            text = expense.price.currency,
                            isHintVisible = false
                        )
                        _expenseContent.value = _expenseContent.value.copy(
                            text = expense.content,
                            isHintVisible = false
                        )
                        _expenseCategory.value = expense.category
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditExpenseEvent) {
        when(event) {
            is AddEditExpenseEvent.EnteredTitle -> {
                _expenseTitle.value = expenseTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditExpenseEvent.ChangeTitleFocus -> {
                _expenseTitle.value = expenseTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            expenseTitle.value.text.isBlank()
                )
            }
            is AddEditExpenseEvent.ChangePriceFocus -> {
                _expensePrice.value = expensePrice.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            expensePrice.value.text.isBlank()
                )
            }
            is AddEditExpenseEvent.ChangeCurrencyFocus -> {
                _expenseCurrency.value = expenseCurrency.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            expenseCurrency.value.text.isBlank()
                )
            }
            is AddEditExpenseEvent.EnteredContent -> {
                _expenseContent.value = _expenseContent.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.EnteredPrice -> {
                _expensePrice.value = _expensePrice.value.copy(
                    text = event.value
                )
            }

            is AddEditExpenseEvent.EnteredCurrency -> {
                _expenseCurrency.value = _expenseCurrency.value.copy(
                    text = event.value
                )

            }
            is AddEditExpenseEvent.ChangeContentFocus -> {
                _expenseContent.value = _expenseContent.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _expenseContent.value.text.isBlank()
                )
            }
            is AddEditExpenseEvent.ChangeCategory -> {
                _expenseCategory.value = event.category
            }
            is AddEditExpenseEvent.SaveExpense -> {
                viewModelScope.launch {
                    try {
                        expenseUseCases.addExpense(
                            Expense(
                                title = expenseTitle.value.text,
                                content = expenseContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                category = expenseCategory.value,
                                price = Price(expensePrice.value.text.toDoubleOrNull(),expenseCurrency.value.text),
                                id = currentexpenseId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveExpense)
                    } catch(e: InvalidExpenseException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Couldn't save expense"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveExpense: UiEvent()
    }
}