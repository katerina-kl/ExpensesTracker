package com.example.expensestracker.feature.expenses.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensestracker.core.common.ui.UiText
import com.example.expensestracker.core.designsystem.R as DesignSystemR
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.feature.expenses.R
import com.example.expensestracker.feature.expenses.list.components.ExpenseItem
import com.example.expensestracker.feature.expenses.list.components.OrderSection

@Composable
fun ExpensesListRoute(
    onNavigateToAddExpense: () -> Unit,
    onNavigateToEditExpense: (Long) -> Unit,
    viewModel: ExpensesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val deletedMessage = stringResource(R.string.expenses_deleted)
    val undoLabel = stringResource(R.string.expenses_undo)

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                ExpensesUiEvent.ExpenseDeleted -> {
                    val result = snackbarHostState.showSnackbar(
                        message = deletedMessage,
                        actionLabel = undoLabel,
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(ExpensesEvent.RestoreExpense)
                    }
                }
            }
        }
    }

    ExpensesScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
        onAddExpenseClick = onNavigateToAddExpense,
        onExpenseClick = { onNavigateToEditExpense(it.id) },
    )
}

/**
 * Stateless screen — easy to preview and unit-test.
 */
@Composable
internal fun ExpensesScreen(
    uiState: ExpensesUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (ExpensesEvent) -> Unit,
    onAddExpenseClick: () -> Unit,
    onExpenseClick: (Expense) -> Unit,
) {
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddExpenseClick,
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    painter = painterResource(DesignSystemR.drawable.baseline_add_24),
                    contentDescription = stringResource(R.string.expenses_add_cd),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.expenses_title),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp),
                )
                IconButton(onClick = { onEvent(ExpensesEvent.ToggleOrderSection) }) {
                    Icon(
                        painter = painterResource(DesignSystemR.drawable.baseline_reorder_24),
                        contentDescription = stringResource(R.string.expenses_sort_cd),
                    )
                }
            }
            AnimatedVisibility(
                visible = uiState.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    expenseOrder = uiState.expenseOrder,
                    onOrderChange = { onEvent(ExpensesEvent.Order(it)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.fillMaxSize()) {
                when {
                    uiState.isLoading -> LoadingIndicator()
                    uiState.errorMessage != null -> ErrorState(message = uiState.errorMessage)
                    uiState.expenses.isEmpty() -> EmptyState()
                    else -> ExpensesList(
                        expenses = uiState.expenses,
                        onExpenseClick = onExpenseClick,
                        onDeleteExpense = { onEvent(ExpensesEvent.DeleteExpense(it)) },
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorState(message: UiText, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = message.asString(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun EmptyState(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
            text = stringResource(R.string.expenses_empty),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(24.dp),
        )
    }
}

@Composable
private fun ExpensesList(
    expenses: List<Expense>,
    onExpenseClick: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit,
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = expenses, key = { it.id }) { expense ->
            ExpenseItem(
                expense = expense,
                onDeleteClick = { onDeleteExpense(expense) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .clickable { onExpenseClick(expense) },
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
