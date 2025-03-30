package com.example.expensestracker.feature_expense.data.data_source

import androidx.room.*
import com.example.expensestracker.feature_expense.domain.model.ExpenseEntity
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