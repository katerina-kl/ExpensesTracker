package com.example.expensestracker.feature_expense.domain.use_case

data class ExpenseUseCases(
    val getExpenses: GetExpenses,
    val deleteExpense: DeleteExpense,
    val addExpense: AddExpense,
    val getExpense: GetExpense
)