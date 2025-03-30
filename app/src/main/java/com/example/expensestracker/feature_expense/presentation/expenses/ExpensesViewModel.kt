package com.example.expensestracker.feature_expense.presentation.expenses

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.use_case.ExpenseUseCases
import com.example.expensestracker.feature_expense.domain.util.ExpenseOrder
import com.example.expensestracker.feature_expense.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases
) : ViewModel() {

    private val _state = mutableStateOf(ExpensesState())
    val state: State<ExpensesState> = _state

    private var recentlyDeletedExpense: Expense? = null

    private var getExpensesJob: Job? = null

    init {
        getExpenses(ExpenseOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.Order -> {
                if (state.value.expenseOrder::class == event.expenseOrder::class &&
                    state.value.expenseOrder.orderType == event.expenseOrder.orderType
                ) {
                    return
                }
                getExpenses(event.expenseOrder)
            }
            is ExpensesEvent.DeleteExpense -> {
                viewModelScope.launch {
                    expenseUseCases.deleteExpense(event.expense)
                    recentlyDeletedExpense = event.expense
                }
            }
            is ExpensesEvent.RestoreExpense -> {
                viewModelScope.launch {
                    expenseUseCases.addExpense(recentlyDeletedExpense ?: return@launch)
                    recentlyDeletedExpense = null
                }
            }
            is ExpensesEvent.ToggleOrderSection -> {
                _state.value = state.value.copy(
                    isOrderSectionVisible = !state.value.isOrderSectionVisible
                )
            }
        }
    }

    private fun getExpenses(expenseOrder: ExpenseOrder) {
        getExpensesJob?.cancel()
        getExpensesJob = expenseUseCases.getExpenses(expenseOrder)
            .onEach { expenses ->
                _state.value = state.value.copy(
                    expenses = expenses,
                    expenseOrder = expenseOrder
                )
            }
            .launchIn(viewModelScope)
    }
}