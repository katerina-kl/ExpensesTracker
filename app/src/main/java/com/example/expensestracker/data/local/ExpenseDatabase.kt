package com.example.expensestracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

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