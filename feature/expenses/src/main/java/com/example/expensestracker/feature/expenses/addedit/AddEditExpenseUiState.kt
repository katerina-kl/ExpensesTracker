package com.example.expensestracker.feature.expenses.addedit

import com.example.expensestracker.core.common.ui.UiText
import com.example.expensestracker.domain.model.ExpenseCategory

data class AddEditExpenseUiState(
    val title: TextFieldState = TextFieldState(hintRes = com.example.expensestracker.feature.expenses.R.string.add_edit_hint_title),
    val priceAmount: TextFieldState = TextFieldState(hintRes = com.example.expensestracker.feature.expenses.R.string.add_edit_hint_price),
    val priceCurrency: TextFieldState = TextFieldState(hintRes = com.example.expensestracker.feature.expenses.R.string.add_edit_hint_currency),
    val content: TextFieldState = TextFieldState(hintRes = com.example.expensestracker.feature.expenses.R.string.add_edit_hint_content),
    val category: ExpenseCategory = ExpenseCategory.Home,
    val isLoading: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: UiText? = null,
)

data class TextFieldState(
    val text: String = "",
    val hintRes: Int,
    val isHintVisible: Boolean = true,
)
