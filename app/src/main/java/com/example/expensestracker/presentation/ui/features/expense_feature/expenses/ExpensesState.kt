package com.example.expensestracker.presentation.ui.features.expense_feature.expenses

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType

data class ExpensesState(
    val expenses: List<Expense> = emptyList(),
    val expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false
)