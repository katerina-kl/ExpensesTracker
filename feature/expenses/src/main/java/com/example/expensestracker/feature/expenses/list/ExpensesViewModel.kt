package com.example.expensestracker.feature.expenses.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensestracker.core.common.ui.UiText
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.usecase.ExpenseUseCases
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType
import com.example.expensestracker.feature.expenses.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val expenseUseCases: ExpenseUseCases,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val orderFlow: MutableStateFlow<ExpenseOrder> =
        MutableStateFlow(ExpenseOrder.Date(OrderType.Descending))

    private val isOrderSectionVisible: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val errorMessage: MutableStateFlow<UiText?> = MutableStateFlow(null)

    private val expensesResult: Flow<Result<List<Expense>>> =
        orderFlow.flatMapLatest { order ->
            expenseUseCases.getExpenses(order)
                .map { Result.success(it) }
                .onStart { emit(Result.success(emptyList())) }
                .catch { emit(Result.failure(it)) }
        }

    val uiState: StateFlow<ExpensesUiState> = combine(
        expensesResult,
        orderFlow,
        isOrderSectionVisible,
        errorMessage,
    ) { result, order, visible, error ->
        val loadError = result.exceptionOrNull()
        ExpensesUiState(
            isLoading = false,
            expenses = result.getOrDefault(emptyList()),
            expenseOrder = order,
            isOrderSectionVisible = visible,
            errorMessage = error ?: loadError?.let { UiText.of(R.string.expenses_load_error) },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = ExpensesUiState(),
    )

    private val _uiEvents = Channel<ExpensesUiEvent>(Channel.BUFFERED)
    val uiEvents: Flow<ExpensesUiEvent> = _uiEvents.receiveAsFlow()

    fun onEvent(event: ExpensesEvent) {
        when (event) {
            is ExpensesEvent.Order -> {
                val current = orderFlow.value
                val sameOrder = current::class == event.expenseOrder::class &&
                    current.orderType == event.expenseOrder.orderType
                if (!sameOrder) orderFlow.value = event.expenseOrder
            }

            is ExpensesEvent.DeleteExpense -> viewModelScope.launch {
                expenseUseCases.deleteExpense(event.expense)
                    .onSuccess {
                        savedStateHandle[KEY_RECENTLY_DELETED] =
                            Json.encodeToString(Expense.serializer(), event.expense)
                        _uiEvents.trySend(ExpensesUiEvent.ExpenseDeleted)
                    }
                    .onFailure {
                        errorMessage.value = UiText.of(R.string.expenses_load_error)
                    }
            }

            ExpensesEvent.RestoreExpense -> viewModelScope.launch {
                val payload: String = savedStateHandle[KEY_RECENTLY_DELETED] ?: return@launch
                val expense = runCatching {
                    Json.decodeFromString(Expense.serializer(), payload)
                }.getOrNull() ?: return@launch
                // Re-insert as a new row; original id (if any) may collide if Room is still flushing.
                expenseUseCases.addExpense(expense.copy(id = 0L))
                savedStateHandle.remove<String>(KEY_RECENTLY_DELETED)
            }

            ExpensesEvent.ToggleOrderSection -> {
                isOrderSectionVisible.value = !isOrderSectionVisible.value
            }

            ExpensesEvent.DismissError -> {
                errorMessage.value = null
            }
        }
    }

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
        const val KEY_RECENTLY_DELETED = "recently_deleted_expense_id"
    }
}
