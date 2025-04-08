package com.example.expensestracker.presentation.ui.features.expense_feature.add_edit_expense

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expensestracker.R
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.presentation.ui.features.expense_feature.add_edit_expense.components.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun AddEditExpenseScreen(
    navController: NavController,
    expenseColor: Int,
    viewModel: AddEditExpenseViewModel = hiltViewModel()
) {
    val titleState = viewModel.expenseTitle.value
    val contentState = viewModel.expenseContent.value
    val priceState = viewModel.expensePrice.value
    val currencyState = viewModel.expenseCurrency.value
    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }

    val expenseBackgroundAnimatable = remember {
        Animatable(
            Color(if (expenseColor != -1) expenseColor else viewModel.expenseCategory.value.color)
        )
    }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when(event) {
                is AddEditExpenseViewModel.UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditExpenseViewModel.UiEvent.SaveExpense -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditExpenseEvent.SaveExpense)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(painter = painterResource(R.drawable.baseline_save_alt_24), contentDescription = "Save expense")
            }
        },
        content =   { innerSpacing ->
            Column(
                modifier = Modifier
                    .consumeWindowInsets(innerSpacing)
                    .fillMaxSize()
                    .background(expenseBackgroundAnimatable.value)
                    .padding(innerSpacing)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Expense.categories.forEach { category ->
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(category.color))
                                .border(
                                    width = 3.dp,
                                    color = if (viewModel.expenseCategory.value.color == category.color) {
                                        Color.Black
                                    } else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        expenseBackgroundAnimatable.animateTo(
                                            targetValue = Color(category.color),
                                            animationSpec = tween(
                                                durationMillis = 500
                                            )
                                        )
                                    }
                                    viewModel.onEvent(AddEditExpenseEvent.ChangeCategory(category))
                                }
                        ) {
                            Text(
                                text = category.name,
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.align(Alignment.Center).padding(12.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditExpenseEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditExpenseEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = priceState.text,
                    hint = priceState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditExpenseEvent.EnteredPrice(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditExpenseEvent.ChangePriceFocus(it))
                    },
                    isHintVisible = priceState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = currencyState.text,
                    hint = currencyState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditExpenseEvent.EnteredCurrency(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditExpenseEvent.ChangeCurrencyFocus(it))
                    },
                    isHintVisible = currencyState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                TransparentHintTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditExpenseEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditExpenseEvent.ChangeContentFocus(it))
                    },
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    )

}