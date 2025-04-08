package com.example.expensestracker.di

import android.app.Application
import androidx.room.Room
import com.example.expensestracker.data.local.ExpenseDatabase
import com.example.expensestracker.data.repository.ExpenseRepositoryImpl
import com.example.expensestracker.domain.use_case.AddExpense
import com.example.expensestracker.domain.use_case.DeleteExpense
import com.example.expensestracker.domain.use_case.GetExpenses
import com.example.expensestracker.domain.repository.ExpenseRepository
import com.example.expensestracker.domain.use_case.ExpenseUseCases
import com.example.expensestracker.domain.use_case.GetExpense
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
