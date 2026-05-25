package com.example.expensestracker.di

import com.example.expensestracker.domain.repository.ExpenseRepository
import com.example.expensestracker.domain.usecase.AddExpense
import com.example.expensestracker.domain.usecase.DeleteExpense
import com.example.expensestracker.domain.usecase.ExpenseUseCases
import com.example.expensestracker.domain.usecase.GetExpense
import com.example.expensestracker.domain.usecase.GetExpenses
import com.example.expensestracker.domain.usecase.ValidateExpense
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Use cases are pure-Kotlin classes from `:domain`, so they cannot be `@Inject`-constructed
 * without dragging Hilt into the domain module. We bind them here in `:app` via `@Provides`.
 *
 * The [ExpenseRepository] dependency itself is bound in `:data` via `@Binds`.
 */
@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideValidateExpense(): ValidateExpense = ValidateExpense()

    @Provides
    @Singleton
    fun provideExpenseUseCases(
        repository: ExpenseRepository,
        validateExpense: ValidateExpense,
    ): ExpenseUseCases = ExpenseUseCases(
        getExpenses = GetExpenses(repository),
        getExpense = GetExpense(repository),
        addExpense = AddExpense(repository, validateExpense),
        deleteExpense = DeleteExpense(repository),
        validateExpense = validateExpense,
    )
}
