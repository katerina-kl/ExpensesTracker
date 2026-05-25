package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseValidationError

class ValidateExpense {

    operator fun invoke(expense: Expense): Result<Unit> {
        val error: ExpenseValidationError? = when {
            expense.title.isBlank() -> ExpenseValidationError.BlankTitle
            expense.content.isBlank() -> ExpenseValidationError.BlankContent
            expense.price.currency.isBlank() -> ExpenseValidationError.BlankCurrency
            expense.price.amount.isNaN() || expense.price.amount < 0.0 ->
                ExpenseValidationError.InvalidPrice
            else -> null
        }
        return if (error == null) {
            Result.success(Unit)
        } else {
            Result.failure(com.example.expensestracker.domain.model.InvalidExpenseException(error))
        }
    }
}
