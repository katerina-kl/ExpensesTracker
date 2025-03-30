package com.example.expensestracker.feature_expense.data.repository

import com.example.expensestracker.feature_expense.data.data_source.ExpenseDao
import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.model.toDomain
import com.example.expensestracker.feature_expense.domain.model.toEntity
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ExpenseRepositoryImpl(
    private val dao: ExpenseDao
) : ExpenseRepository {

    override fun getExpenses(): Flow<List<Expense>> {
        return dao.getExpenses().map { expenses ->
            expenses.map { it.toDomain() }
        }
    }

    override suspend fun getExpenseById(id: Int): Expense? {
        return dao.getExpenseById(id)?.toDomain()
    }

    override suspend fun insertExpense(expense: Expense) {
        dao.insertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) {
        dao.deleteExpense(expense.toEntity())
    }
}