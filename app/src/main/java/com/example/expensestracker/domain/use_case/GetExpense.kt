package com.example.expensestracker.domain.use_case

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository

class GetExpense(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(id: Int): Expense? {
        return repository.getExpenseById(id)
    }
}