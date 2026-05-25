package com.example.expensestracker.domain.repository

import com.example.expensestracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow


interface ExpenseRepository {

    fun getExpenses(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Long): Expense?

    suspend fun insertExpense(expense: Expense): Long

    suspend fun deleteExpense(expense: Expense)
}
