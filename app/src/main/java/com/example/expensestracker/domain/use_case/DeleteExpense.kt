package com.example.expensestracker.domain.use_case

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository

class DeleteExpense(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(expense: Expense) {
        repository.deleteExpense(expense)
    }
}