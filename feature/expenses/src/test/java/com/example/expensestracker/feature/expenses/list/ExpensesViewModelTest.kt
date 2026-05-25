package com.example.expensestracker.feature.expenses.list

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.domain.model.Price
import com.example.expensestracker.domain.repository.ExpenseRepository
import com.example.expensestracker.domain.usecase.AddExpense
import com.example.expensestracker.domain.usecase.DeleteExpense
import com.example.expensestracker.domain.usecase.ExpenseUseCases
import com.example.expensestracker.domain.usecase.GetExpense
import com.example.expensestracker.domain.usecase.GetExpenses
import com.example.expensestracker.domain.usecase.ValidateExpense
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExpensesViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private val repository: ExpenseRepository = mockk(relaxUnitFun = true)
    private val expenseFlow = MutableStateFlow<List<Expense>>(emptyList())
    private lateinit var useCases: ExpenseUseCases
    private lateinit var viewModel: ExpensesViewModel

    private val expense = Expense(
        id = 1L,
        title = "Coffee",
        content = "Latte",
        timestamp = 100L,
        category = ExpenseCategory.Groceries,
        price = Price(amount = 3.0, currency = "EUR"),
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.getExpenses() } returns expenseFlow
        coEvery { repository.deleteExpense(any()) } returns Unit
        coEvery { repository.insertExpense(any()) } returns expense.id
        coEvery { repository.getExpenseById(any()) } returns expense
        val validate = ValidateExpense()
        useCases = ExpenseUseCases(
            getExpenses = GetExpenses(repository),
            getExpense = GetExpense(repository),
            addExpense = AddExpense(repository, validate),
            deleteExpense = DeleteExpense(repository),
            validateExpense = validate,
        )
        viewModel = ExpensesViewModel(useCases, SavedStateHandle())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState emits Success state after expenses flow emits`() = runTest {
        viewModel.uiState.test {
            // Drop initial loading + onStart-empty-list emissions; we only care about the post-set state.
            skipItems(2)
            expenseFlow.value = listOf(expense)

            val state = awaitItem()
            assertEquals(listOf(expense), state.expenses)
            assertFalse(state.isLoading)
            assertEquals(null, state.errorMessage)
        }
    }

    @Test
    fun `ToggleOrderSection flips visibility`() = runTest {
        viewModel.uiState.test {
            awaitItem() // initial
            assertFalse(awaitItem().isOrderSectionVisible) // after flow emits empty

            viewModel.onEvent(ExpensesEvent.ToggleOrderSection)
            assertEquals(true, awaitItem().isOrderSectionVisible)
        }
    }

    @Test
    fun `Order event updates expenseOrder`() = runTest {
        val newOrder = ExpenseOrder.Title(OrderType.Ascending)
        viewModel.uiState.test {
            awaitItem() // initial
            awaitItem() // first emission from flow

            viewModel.onEvent(ExpensesEvent.Order(newOrder))
            val state = awaitItem()
            assertEquals(ExpenseOrder.Title::class, state.expenseOrder::class)
            assertEquals(OrderType.Ascending, state.expenseOrder.orderType)
        }
    }

    @Test
    fun `DeleteExpense calls repository and emits ExpenseDeleted`() = runTest {
        viewModel.uiEvents.test {
            viewModel.onEvent(ExpensesEvent.DeleteExpense(expense))
            assertEquals(ExpensesUiEvent.ExpenseDeleted, awaitItem())
        }
        coVerify(exactly = 1) { repository.deleteExpense(any()) }
    }
}
