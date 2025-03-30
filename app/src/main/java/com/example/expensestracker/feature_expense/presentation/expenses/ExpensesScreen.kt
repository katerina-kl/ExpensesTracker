package com.example.expensestracker.feature_expense.presentation.expenses
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensestracker.R
import com.example.expensestracker.feature_expense.presentation.expenses.components.ExpenseItem
import com.example.expensestracker.feature_expense.presentation.expenses.components.OrderSection
import com.example.expensestracker.feature_expense.presentation.util.Screen
import kotlinx.coroutines.launch



@ExperimentalAnimationApi
@Composable
fun ExpensesScreen(
    navController: NavController,
    viewModel: ExpensesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditExpenseScreen.route)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon( painter = painterResource(R.drawable.baseline_add_24)
                    , contentDescription = "Add expense")
            }
        },
        content =   { innerPadding->
            Column(
                modifier = Modifier
                    .consumeWindowInsets(innerPadding)
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Your expenses",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)// Material 3 typography
                    )
                    IconButton(
                        onClick = {
                            viewModel.onEvent(ExpensesEvent.ToggleOrderSection)
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_reorder_24) ,
                            contentDescription = "Sort"
                        )
                    }
                }
                AnimatedVisibility(
                    visible = state.isOrderSectionVisible,
                    enter = fadeIn() + slideInVertically(),
                    exit = fadeOut() + slideOutVertically()
                ) {
                    OrderSection(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        expenseOrder = state.expenseOrder,
                        onOrderChange = {
                            viewModel.onEvent(ExpensesEvent.Order(it))
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.expenses) { expense ->
                        ExpenseItem(
                            expense = expense,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    navController.navigate(
                                        Screen.AddEditExpenseScreen.route +
                                                "?expenseId=${expense.id}&expenseColor=${expense.category.color}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(ExpensesEvent.DeleteExpense(expense))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Expense deleted",
                                        actionLabel = "Undo"
                                    )
                                    if(result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(ExpensesEvent.RestoreExpense)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
            }
        }
    )

}
