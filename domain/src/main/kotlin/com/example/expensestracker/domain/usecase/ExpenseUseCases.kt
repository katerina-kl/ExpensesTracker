package com.example.expensestracker.domain.usecase

data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val getExpense: GetExpense,
    val addExpense: AddExpense,
    val deleteExpense: DeleteExpense,
    val validateExpense: ValidateExpense,
)
