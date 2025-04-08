package com.example.expensestracker.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenseentity")
    fun getExpenses(): Flow<List<ExpenseEntity>>

    @Query("SELECT * FROM expenseentity WHERE id = :id")
    suspend fun getExpenseById(id: Int): ExpenseEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)
}