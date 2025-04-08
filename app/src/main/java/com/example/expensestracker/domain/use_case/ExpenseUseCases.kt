package com.example.expensestracker.domain.use_case

data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val deleteExpense: DeleteExpense,
    val addExpense: AddExpense,
    val getExpense: GetExpense
)