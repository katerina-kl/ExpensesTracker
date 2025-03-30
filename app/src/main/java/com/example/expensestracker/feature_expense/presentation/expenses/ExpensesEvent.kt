package com.example.expensestracker.feature_expense.presentation.expenses

import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.util.ExpenseOrder

sealed class ExpensesEvent {
    data class Order(val expenseOrder: ExpenseOrder): ExpensesEvent()
    data class DeleteExpense(val expense: Expense): ExpensesEvent()
    object RestoreExpense: ExpensesEvent()
    object ToggleOrderSection: ExpensesEvent()
}