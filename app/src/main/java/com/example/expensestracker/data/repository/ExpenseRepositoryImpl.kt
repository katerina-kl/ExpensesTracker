package com.example.expensestracker.data.repository

import com.example.expensestracker.data.local.ExpenseDao
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.data.local.mappers.toDomain
import com.example.expensestracker.data.local.mappers.toEntity
import com.example.expensestracker.domain.repository.ExpenseRepository
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