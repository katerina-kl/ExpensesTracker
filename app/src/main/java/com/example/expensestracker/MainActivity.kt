package com.example.expensestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.expensestracker.presentation.ui.features.expense_feature.add_edit_expense.AddEditExpenseScreen
import com.example.expensestracker.presentation.ui.features.expense_feature.expenses.ExpensesScreen
import com.example.expensestracker.presentation.util.Screen
import com.example.expensestracker.presentation.ui.theme.ExpensesTrackerTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpensesTrackerTheme  {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ExpensesScreen.route
                    ) {
                        composable(route = Screen.ExpensesScreen.route) {
                            ExpensesScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditExpenseScreen.route +
                                    "?expenseId={expenseId}&expenseColor={expenseColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "expenseId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "expenseColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("expenseColor") ?: -1
                            AddEditExpenseScreen(
                                navController = navController,
                                expenseColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}