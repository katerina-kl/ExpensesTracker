package com.example.expensestracker.feature.expenses.list

import com.example.expensestracker.core.common.ui.UiText
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType

data class ExpensesUiState(
    val isLoading: Boolean = true,
    val expenses: List<Expense> = emptyList(),
    val expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending),
    val isOrderSectionVisible: Boolean = false,
    val errorMessage: UiText? = null,
)
