package com.example.expensestracker.feature_expense.domain.use_case

import com.example.expensestracker.feature_expense.domain.model.InvalidExpenseException
import com.example.expensestracker.feature_expense.domain.model.Expense
import com.example.expensestracker.feature_expense.domain.repository.ExpenseRepository


class AddExpense(
    private val repository: ExpenseRepository
) {

    @Throws(InvalidExpenseException::class)
    suspend operator fun invoke(expense: Expense) {
        if (expense.title.isBlank()) {
            throw InvalidExpenseException("The title of the expense can't be empty.")
        }
        if (expense.content.isBlank()) {
            throw InvalidExpenseException("The content of the expense can't be empty.")
        }
        if (expense.price.currency.isBlank()) {
            throw InvalidExpenseException("Please enter a currency")
        }
        if (expense.price.amount == null) {
            throw InvalidExpenseException("Please enter a valid price")
        }
        repository.insertExpense(expense)
    }
}