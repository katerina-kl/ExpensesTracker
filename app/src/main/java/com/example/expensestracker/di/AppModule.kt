package com.example.expensestracker.di

import android.app.Application
import androidx.room.Room
import com.example.expensestracker.feature_expense.data.data_source.ExpenseDatabase
import com.example.expensestracker.feature_expense.data.repository.ExpenseRepositoryImpl
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository
import com.example.expensestracker.feature_expense.domain.use_case.*
import com.example.expensestracker.feature_expense.domain.use_case.ExpenseUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(app: Application): ExpenseDatabase {
        return Room.databaseBuilder(
            app,
            ExpenseDatabase::class.java,
            ExpenseDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(db: ExpenseDatabase): ExpenseRepository {
        return ExpenseRepositoryImpl(db.expenseDao)
    }

    @Provides
    @Singleton
    fun provideExpenseUseCases(repository: ExpenseRepository): ExpenseUseCases {
        return ExpenseUseCases(
            getExpenses = GetExpenses(repository),
            deleteExpense = DeleteExpense(repository),
            addExpense = AddExpense(repository),
            getExpense = GetExpense(repository)
        )
    }
}
