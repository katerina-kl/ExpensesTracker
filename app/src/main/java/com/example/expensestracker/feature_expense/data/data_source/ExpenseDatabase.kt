package com.example.expensestracker.feature_expense.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expensestracker.feature_expense.domain.model.ExpenseEntity

@Database(
    entities = [ExpenseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExpenseDatabase: RoomDatabase() {

    abstract val expenseDao: ExpenseDao

    companion object {
        const val DATABASE_NAME = "expense_db"
    }
}