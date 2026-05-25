package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository

class DeleteExpense(
    private val repository: ExpenseRepository,
) {

    suspend operator fun invoke(expense: Expense): Result<Unit> = runCatching {
        repository.deleteExpense(expense)
    }
}
