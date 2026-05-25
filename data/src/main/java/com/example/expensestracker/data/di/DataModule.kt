package com.example.expensestracker.data.di

import android.app.Application
import androidx.room.Room
import com.example.expensestracker.data.local.ExpenseDao
import com.example.expensestracker.data.local.ExpenseDatabase
import com.example.expensestracker.data.repository.ExpenseRepositoryImpl
import com.example.expensestracker.domain.repository.ExpenseRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    fun provideExpenseDatabase(app: Application): ExpenseDatabase = Room.databaseBuilder(
        app,
        ExpenseDatabase::class.java,
        ExpenseDatabase.DATABASE_NAME,
    ).build()

    @Provides
    fun provideExpenseDao(db: ExpenseDatabase): ExpenseDao = db.expenseDao
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExpenseRepository(impl: ExpenseRepositoryImpl): ExpenseRepository
}
