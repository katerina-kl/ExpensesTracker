package com.example.expensestracker.feature_expense.domain.use_case

import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository

class GetExpense(
    private val repository: ExpenseRepository
) {

    suspend operator fun invoke(id: Int): Expense? {
        return repository.getExpenseById(id)
    }
}