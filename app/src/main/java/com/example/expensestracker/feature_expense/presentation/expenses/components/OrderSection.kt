package com.example.expensestracker.feature_expense.presentation.expenses.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.expensestracker.feature_expense.domain.util.ExpenseOrder
import com.example.expensestracker.feature_expense.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    expenseOrder: ExpenseOrder = ExpenseOrder.Date(OrderType.Descending),
    onOrderChange: (ExpenseOrder) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Title",
                selected = expenseOrder is ExpenseOrder.Title,
                onSelect = { onOrderChange(ExpenseOrder.Title(expenseOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Date",
                selected = expenseOrder is ExpenseOrder.Date,
                onSelect = { onOrderChange(ExpenseOrder.Date(expenseOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Category",
                selected = expenseOrder is ExpenseOrder.Color,
                onSelect = { onOrderChange(ExpenseOrder.Color(expenseOrder.orderType)) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Price",
                selected = expenseOrder is ExpenseOrder.Price,
                onSelect = { onOrderChange(ExpenseOrder.Price(expenseOrder.orderType)) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            DefaultRadioButton(
                text = "Ascending",
                selected = expenseOrder.orderType is OrderType.Ascending,
                onSelect = {
                    onOrderChange(expenseOrder.copy(OrderType.Ascending))
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = expenseOrder.orderType is OrderType.Descending,
                onSelect = {
                    onOrderChange(expenseOrder.copy(OrderType.Descending))
                }
            )
        }
    }
}