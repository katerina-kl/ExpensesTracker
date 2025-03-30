package com.example.expensestracker.feature_expense.presentation.add_edit_expense

data class ExpenseTextFieldState(
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)