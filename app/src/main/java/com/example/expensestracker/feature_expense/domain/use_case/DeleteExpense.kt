package com.example.expensestracker.feature_expense.domain.use_case

import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository

class DeleteExpense(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {
        repository.deleteExpense(expense)
    }
}