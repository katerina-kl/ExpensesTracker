package com.example.expensestracker.feature.expenses.addedit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.expensestracker.core.common.ui.UiText
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.domain.model.ExpenseValidationError
import com.example.expensestracker.domain.model.InvalidExpenseException
import com.example.expensestracker.domain.model.Price
import com.example.expensestracker.domain.usecase.ExpenseUseCases
import com.example.expensestracker.feature.expenses.R
import com.example.expensestracker.feature.expenses.navigation.AddEditExpenseDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddEditExpenseViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val args: AddEditExpenseDestination = savedStateHandle.toRoute()

    private val _uiState = MutableStateFlow(
        AddEditExpenseUiState(category = ExpenseCategory.entries.random()),
    )
    val uiState: StateFlow<AddEditExpenseUiState> = _uiState.asStateFlow()

    private val _uiEvents = Channel<AddEditExpenseUiEvent>(Channel.BUFFERED)
    val uiEvents: Flow<AddEditExpenseUiEvent> = _uiEvents.receiveAsFlow()

    private var currentExpenseId: Long = args.expenseId

    init {
        if (args.expenseId != 0L) {
            loadExpense(args.expenseId)
        }
    }

    private fun loadExpense(id: Long) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val expense = expenseUseCases.getExpense(id)
            _uiState.update { state ->
                if (expense == null) {
                    state.copy(isLoading = false)
                } else {
                    currentExpenseId = expense.id
                    state.copy(
                        isLoading = false,
                        title = state.title.copy(text = expense.title, isHintVisible = false),
                        priceAmount = state.priceAmount.copy(
                            text = expense.price.amount.toString(),
                            isHintVisible = false,
                        ),
                        priceCurrency = state.priceCurrency.copy(
                            text = expense.price.currency,
                            isHintVisible = false,
                        ),
                        content = state.content.copy(
                            text = expense.content,
                            isHintVisible = false,
                        ),
                        category = expense.category,
                    )
                }
            }
        }
    }

    fun onEvent(event: AddEditExpenseEvent) {
        when (event) {
            is AddEditExpenseEvent.TitleChanged -> _uiState.update {
                it.copy(title = it.title.copy(text = event.value))
            }
            is AddEditExpenseEvent.TitleFocusChanged -> _uiState.update {
                it.copy(
                    title = it.title.copy(
                        isHintVisible = !event.focusState.isFocused && it.title.text.isBlank(),
                    ),
                )
            }

            is AddEditExpenseEvent.PriceChanged -> _uiState.update {
                it.copy(priceAmount = it.priceAmount.copy(text = event.value))
            }
            is AddEditExpenseEvent.PriceFocusChanged -> _uiState.update {
                it.copy(
                    priceAmount = it.priceAmount.copy(
                        isHintVisible = !event.focusState.isFocused && it.priceAmount.text.isBlank(),
                    ),
                )
            }

            is AddEditExpenseEvent.CurrencyChanged -> _uiState.update {
                it.copy(priceCurrency = it.priceCurrency.copy(text = event.value))
            }
            is AddEditExpenseEvent.CurrencyFocusChanged -> _uiState.update {
                it.copy(
                    priceCurrency = it.priceCurrency.copy(
                        isHintVisible = !event.focusState.isFocused && it.priceCurrency.text.isBlank(),
                    ),
                )
            }

            is AddEditExpenseEvent.ContentChanged -> _uiState.update {
                it.copy(content = it.content.copy(text = event.value))
            }
            is AddEditExpenseEvent.ContentFocusChanged -> _uiState.update {
                it.copy(
                    content = it.content.copy(
                        isHintVisible = !event.focusState.isFocused && it.content.text.isBlank(),
                    ),
                )
            }

            is AddEditExpenseEvent.CategoryChanged -> _uiState.update {
                it.copy(category = event.category)
            }

            AddEditExpenseEvent.SaveExpense -> saveExpense()
        }
    }

    private fun saveExpense() {
        viewModelScope.launch {
            val current = _uiState.value
            _uiState.update { it.copy(isSaving = true) }
            val expense = Expense(
                id = currentExpenseId,
                title = current.title.text,
                content = current.content.text,
                timestamp = System.currentTimeMillis(),
                category = current.category,
                price = Price(
                    amount = current.priceAmount.text.toDoubleOrNull() ?: Double.NaN,
                    currency = current.priceCurrency.text,
                ),
            )
            expenseUseCases.addExpense(expense)
                .onSuccess {
                    _uiState.update { it.copy(isSaving = false) }
                    _uiEvents.trySend(AddEditExpenseUiEvent.Saved)
                }
                .onFailure { throwable ->
                    val message = (throwable as? InvalidExpenseException)?.error?.toUiText()
                        ?: UiText.of(R.string.error_save_failed)
                    _uiState.update { it.copy(isSaving = false) }
                    _uiEvents.trySend(AddEditExpenseUiEvent.ShowError(message))
                }
        }
    }
}

private fun ExpenseValidationError.toUiText(): UiText = when (this) {
    ExpenseValidationError.BlankTitle -> UiText.of(R.string.error_blank_title)
    ExpenseValidationError.BlankContent -> UiText.of(R.string.error_blank_content)
    ExpenseValidationError.BlankCurrency -> UiText.of(R.string.error_blank_currency)
    ExpenseValidationError.InvalidPrice -> UiText.of(R.string.error_invalid_price)
}
