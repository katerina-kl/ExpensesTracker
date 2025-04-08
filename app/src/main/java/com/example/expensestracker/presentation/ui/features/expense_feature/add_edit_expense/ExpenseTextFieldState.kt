package com.example.expensestracker.presentation.ui.features.expense_feature.add_edit_expense

data class ExpenseTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)