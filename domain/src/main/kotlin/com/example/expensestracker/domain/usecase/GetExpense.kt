package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository

class GetExpense(
    private val repository: ExpenseRepository,
) {

    suspend operator fun invoke(id: Long): Expense? = repository.getExpenseById(id)
}
