package com.example.expensestracker.presentation.ui.features.expense_feature.add_edit_expense

import androidx.compose.ui.focus.FocusState
import com.example.expensestracker.domain.model.Category

sealed class AddEditExpenseEvent{
    data class EnteredTitle(val value: String): AddEditExpenseEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditExpenseEvent()
    data class ChangePriceFocus(val focusState: FocusState): AddEditExpenseEvent()
    data class ChangeCurrencyFocus(val focusState: FocusState): AddEditExpenseEvent()
    data class EnteredContent(val value: String): AddEditExpenseEvent()
    data class EnteredPrice(val value: String): AddEditExpenseEvent()
    data class EnteredCurrency(val value: String): AddEditExpenseEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditExpenseEvent()
    data class ChangeCategory(val category: Category): AddEditExpenseEvent()
    object SaveExpense: AddEditExpenseEvent()
}