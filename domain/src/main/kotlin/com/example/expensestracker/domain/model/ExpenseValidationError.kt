package com.example.expensestracker.domain.model

sealed interface ExpenseValidationError {
    data object BlankTitle : ExpenseValidationError
    data object BlankContent : ExpenseValidationError
    data object BlankCurrency : ExpenseValidationError
    data object InvalidPrice : ExpenseValidationError
}

class InvalidExpenseException(
    val error: ExpenseValidationError,
) : Exception("Invalid expense: $error")
