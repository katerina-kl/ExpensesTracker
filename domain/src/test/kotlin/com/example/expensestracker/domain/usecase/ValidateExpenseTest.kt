package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.domain.model.ExpenseValidationError
import com.example.expensestracker.domain.model.InvalidExpenseException
import com.example.expensestracker.domain.model.Price
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidateExpenseTest {

    private val validate = ValidateExpense()

    private fun expense(
        title: String = "Coffee",
        content: String = "Latte and a croissant",
        amount: Double = 4.5,
        currency: String = "EUR",
    ) = Expense(
        id = 0L,
        title = title,
        content = content,
        timestamp = 0L,
        category = ExpenseCategory.Groceries,
        price = Price(amount = amount, currency = currency),
    )

    @Test
    fun `valid expense returns success`() {
        val result = validate(expense())

        assertTrue(result.isSuccess)
    }

    @Test
    fun `blank title returns BlankTitle error`() {
        val result = validate(expense(title = "  "))

        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.BlankTitle, error)
    }

    @Test
    fun `blank content returns BlankContent error`() {
        val result = validate(expense(content = ""))

        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.BlankContent, error)
    }

    @Test
    fun `blank currency returns BlankCurrency error`() {
        val result = validate(expense(currency = ""))

        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.BlankCurrency, error)
    }

    @Test
    fun `NaN price returns InvalidPrice error`() {
        val result = validate(expense(amount = Double.NaN))

        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.InvalidPrice, error)
    }

    @Test
    fun `negative price returns InvalidPrice error`() {
        val result = validate(expense(amount = -1.0))

        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.InvalidPrice, error)
    }
}
