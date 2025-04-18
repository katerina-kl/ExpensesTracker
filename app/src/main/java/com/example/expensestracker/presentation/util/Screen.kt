package com.example.expensestracker.presentation.util

sealed class Screen(val route: String) {
    object ExpensesScreen: Screen("expenses_screen")
    object AddEditExpenseScreen: Screen("add_edit_expense_screen")
}