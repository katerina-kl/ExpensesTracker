package com.example.expensestracker.domain.repository

import com.example.expensestracker.domain.model.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {

    fun getExpenses(): Flow<List<Expense>>

    suspend fun getExpenseById(id: Int): Expense?

    suspend fun insertExpense(expense: Expense)

    suspend fun deleteExpense(expense: Expense)
}