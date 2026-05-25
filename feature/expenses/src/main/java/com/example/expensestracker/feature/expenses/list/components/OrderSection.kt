package com.example.expensestracker.feature.expenses.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.expensestracker.core.designsystem.component.DefaultRadioButton
import com.example.expensestracker.domain.util.ExpenseOrder
import com.example.expensestracker.domain.util.OrderType
import com.example.expensestracker.feature.expenses.R

@Composable
fun OrderSection(
    expenseOrder: ExpenseOrder,
    onOrderChange: (ExpenseOrder) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_title),
                selected = expenseOrder is ExpenseOrder.Title,
                onSelect = { onOrderChange(ExpenseOrder.Title(expenseOrder.orderType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_date),
                selected = expenseOrder is ExpenseOrder.Date,
                onSelect = { onOrderChange(ExpenseOrder.Date(expenseOrder.orderType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_category),
                selected = expenseOrder is ExpenseOrder.Category,
                onSelect = { onOrderChange(ExpenseOrder.Category(expenseOrder.orderType)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_by_price),
                selected = expenseOrder is ExpenseOrder.Price,
                onSelect = { onOrderChange(ExpenseOrder.Price(expenseOrder.orderType)) },
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioButton(
                text = stringResource(R.string.sort_ascending),
                selected = expenseOrder.orderType is OrderType.Ascending,
                onSelect = { onOrderChange(expenseOrder.copy(OrderType.Ascending)) },
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = stringResource(R.string.sort_descending),
                selected = expenseOrder.orderType is OrderType.Descending,
                onSelect = { onOrderChange(expenseOrder.copy(OrderType.Descending)) },
            )
        }
    }
}
