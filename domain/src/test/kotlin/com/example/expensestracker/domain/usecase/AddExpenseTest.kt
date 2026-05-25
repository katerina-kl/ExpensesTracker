package com.example.expensestracker.domain.usecase

import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.domain.model.ExpenseValidationError
import com.example.expensestracker.domain.model.InvalidExpenseException
import com.example.expensestracker.domain.model.Price
import com.example.expensestracker.domain.repository.ExpenseRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AddExpenseTest {

    private val repository: ExpenseRepository = mockk(relaxUnitFun = true)
    private val validate = ValidateExpense()
    private val addExpense = AddExpense(repository, validate)

    private val validExpense = Expense(
        id = 0L,
        title = "Coffee",
        content = "Latte",
        timestamp = 0L,
        category = ExpenseCategory.Groceries,
        price = Price(amount = 3.0, currency = "EUR"),
    )

    init {
        coEvery { repository.getExpenses() } returns emptyFlow()
    }

    @Test
    fun `invokes repository when expense is valid`() = runTest {
        coEvery { repository.insertExpense(validExpense) } returns 42L

        val result = addExpense(validExpense)

        assertTrue(result.isSuccess)
        assertEquals(42L, result.getOrNull())
        coVerify(exactly = 1) { repository.insertExpense(validExpense) }
    }

    @Test
    fun `does not call repository when validation fails`() = runTest {
        val invalid = validExpense.copy(title = "")

        val result = addExpense(invalid)

        assertTrue(result.isFailure)
        val error = (result.exceptionOrNull() as? InvalidExpenseException)?.error
        assertEquals(ExpenseValidationError.BlankTitle, error)
        coVerify(exactly = 0) { repository.insertExpense(any()) }
    }
}
