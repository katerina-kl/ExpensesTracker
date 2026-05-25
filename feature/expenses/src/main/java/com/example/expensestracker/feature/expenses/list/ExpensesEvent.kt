package com.example.expensestracker.feature.expenses.list

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.util.ExpenseOrder

sealed interface ExpensesEvent {
    data class Order(val expenseOrder: ExpenseOrder) : ExpensesEvent
    data class DeleteExpense(val expense: Expense) : ExpensesEvent
    data object RestoreExpense : ExpensesEvent
    data object ToggleOrderSection : ExpensesEvent
    data object DismissError : ExpensesEvent
}

sealed interface ExpensesUiEvent {
    data object ExpenseDeleted : ExpensesUiEvent
}
