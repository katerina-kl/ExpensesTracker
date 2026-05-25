package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository

class AddExpense(
    private val repository: ExpenseRepository,
    private val validateExpense: ValidateExpense,
) {

    suspend operator fun invoke(expense: Expense): Result<Long> = runCatching {
        validateExpense(expense).getOrThrow()
        repository.insertExpense(expense)
    }
}
