package com.example.expensestracker.feature.expenses.addedit

import androidx.compose.ui.focus.FocusState
import com.example.expensestracker.domain.model.ExpenseCategory

sealed interface AddEditExpenseEvent {
    data class TitleChanged(val value: String) : AddEditExpenseEvent
    data class TitleFocusChanged(val focusState: FocusState) : AddEditExpenseEvent

    data class PriceChanged(val value: String) : AddEditExpenseEvent
    data class PriceFocusChanged(val focusState: FocusState) : AddEditExpenseEvent

    data class CurrencyChanged(val value: String) : AddEditExpenseEvent
    data class CurrencyFocusChanged(val focusState: FocusState) : AddEditExpenseEvent

    data class ContentChanged(val value: String) : AddEditExpenseEvent
    data class ContentFocusChanged(val focusState: FocusState) : AddEditExpenseEvent

    data class CategoryChanged(val category: ExpenseCategory) : AddEditExpenseEvent
    data object SaveExpense : AddEditExpenseEvent
}

sealed interface AddEditExpenseUiEvent {
    data object Saved : AddEditExpenseUiEvent
    data class ShowError(val message: com.example.expensestracker.core.common.ui.UiText) :
        AddEditExpenseUiEvent
}
