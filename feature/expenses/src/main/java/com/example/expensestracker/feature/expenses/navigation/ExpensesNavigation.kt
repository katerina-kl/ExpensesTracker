package com.example.expensestracker.feature.expenses.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.expensestracker.feature.expenses.addedit.AddEditExpenseRoute
import com.example.expensestracker.feature.expenses.addedit.AddEditExpenseScreen
import com.example.expensestracker.feature.expenses.list.ExpensesListRoute
import com.example.expensestracker.feature.expenses.list.ExpensesScreen
import kotlinx.serialization.Serializable

@Serializable
data object ExpensesListDestination

@Serializable
data class AddEditExpenseDestination(
    val expenseId: Long = 0L,
)

fun NavGraphBuilder.expensesGraph(navController: NavController) {
    composable<ExpensesListDestination> {
        ExpensesListRoute(
            onNavigateToAddExpense = {
                navController.navigate(AddEditExpenseDestination())
            },
            onNavigateToEditExpense = { id ->
                navController.navigate(AddEditExpenseDestination(expenseId = id))
            },
        )
    }
    composable<AddEditExpenseDestination> {
        AddEditExpenseRoute(onBack = { navController.navigateUp() })
    }
}
