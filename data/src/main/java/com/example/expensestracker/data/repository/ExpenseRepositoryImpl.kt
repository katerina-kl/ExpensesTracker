package com.example.expensestracker.data.repository

import com.example.expensestracker.core.common.dispatcher.IoDispatcher
import com.example.expensestracker.data.local.ExpenseDao
import com.example.expensestracker.data.mappers.toDomain
import com.example.expensestracker.data.mappers.toEntity
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.repository.ExpenseRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@Singleton
internal class ExpenseRepositoryImpl @Inject constructor(
    private val dao: ExpenseDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ExpenseRepository {

    override fun getExpenses(): Flow<List<Expense>> =
        dao.getExpenses()
            .map { entities -> entities.map { it.toDomain() } }
            .flowOn(ioDispatcher)

    override suspend fun getExpenseById(id: Long): Expense? = withContext(ioDispatcher) {
        dao.getExpenseById(id)?.toDomain()
    }

    override suspend fun insertExpense(expense: Expense): Long = withContext(ioDispatcher) {
        dao.insertExpense(expense.toEntity())
    }

    override suspend fun deleteExpense(expense: Expense) = withContext(ioDispatcher) {
        dao.deleteExpense(expense.toEntity())
    }
}
