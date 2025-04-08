package com.example.expensestracker.presentation.ui.features.expense_feature.expenses

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.util.ExpenseOrder

sealed class ExpensesEvent {
    data class Order(val expenseOrder: ExpenseOrder): ExpensesEvent()
    data class DeleteExpense(val expense: Expense): ExpensesEvent()
    object RestoreExpense: ExpensesEvent()
    object ToggleOrderSection: ExpensesEvent()
}