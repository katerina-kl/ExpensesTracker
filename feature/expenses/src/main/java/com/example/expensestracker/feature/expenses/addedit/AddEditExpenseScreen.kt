package com.example.expensestracker.feature.expenses.addedit

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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.expensestracker.core.designsystem.R as DesignSystemR
import com.example.expensestracker.core.designsystem.category.color
import com.example.expensestracker.core.designsystem.category.displayName
import com.example.expensestracker.core.designsystem.component.TransparentHintTextField
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.feature.expenses.R
import kotlinx.coroutines.launch

@Composable
fun AddEditExpenseRoute(
    onBack: () -> Unit,
    viewModel: AddEditExpenseViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                AddEditExpenseUiEvent.Saved -> onBack()
                is AddEditExpenseUiEvent.ShowError -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }
            }
        }
    }

    AddEditExpenseScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onEvent = viewModel::onEvent,
    )
}

@Composable
internal fun AddEditExpenseScreen(
    uiState: AddEditExpenseUiState,
    snackbarHostState: SnackbarHostState,
    onEvent: (AddEditExpenseEvent) -> Unit,
) {
    val scrollState = rememberScrollState()
    val backgroundAnimatable = remember(uiState.category) {
        Animatable(uiState.category.color())
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(AddEditExpenseEvent.SaveExpense) },
                containerColor = MaterialTheme.colorScheme.primary,
            ) {
                Icon(
                    painter = painterResource(DesignSystemR.drawable.baseline_save_alt_24),
                    contentDescription = stringResource(R.string.add_edit_save_cd),
                )
            }
        },
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundAnimatable.value)
                .padding(innerPadding),
        ) {
            CategoryPicker(
                selected = uiState.category,
                onCategorySelected = { category ->
                    scope.launch {
                        backgroundAnimatable.animateTo(
                            targetValue = category.color(),
                            animationSpec = tween(durationMillis = 500),
                        )
                    }
                    onEvent(AddEditExpenseEvent.CategoryChanged(category))
                },
                scrollState = scrollState,
            )

            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = uiState.title.text,
                hint = stringResource(uiState.title.hintRes),
                isHintVisible = uiState.title.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium,
                onValueChange = { onEvent(AddEditExpenseEvent.TitleChanged(it)) },
                onFocusChange = { onEvent(AddEditExpenseEvent.TitleFocusChanged(it)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = uiState.priceAmount.text,
                hint = stringResource(uiState.priceAmount.hintRes),
                isHintVisible = uiState.priceAmount.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium,
                onValueChange = { onEvent(AddEditExpenseEvent.PriceChanged(it)) },
                onFocusChange = { onEvent(AddEditExpenseEvent.PriceFocusChanged(it)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = uiState.priceCurrency.text,
                hint = stringResource(uiState.priceCurrency.hintRes),
                isHintVisible = uiState.priceCurrency.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineMedium,
                onValueChange = { onEvent(AddEditExpenseEvent.CurrencyChanged(it)) },
                onFocusChange = { onEvent(AddEditExpenseEvent.CurrencyFocusChanged(it)) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = uiState.content.text,
                hint = stringResource(uiState.content.hintRes),
                isHintVisible = uiState.content.isHintVisible,
                textStyle = MaterialTheme.typography.bodyLarge,
                onValueChange = { onEvent(AddEditExpenseEvent.ContentChanged(it)) },
                onFocusChange = { onEvent(AddEditExpenseEvent.ContentFocusChanged(it)) },
                modifier = Modifier.fillMaxHeight(),
            )
        }
    }
}

@Composable
private fun CategoryPicker(
    selected: ExpenseCategory,
    onCategorySelected: (ExpenseCategory) -> Unit,
    scrollState: androidx.compose.foundation.ScrollState,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ExpenseCategory.entries.forEach { category ->
            val isSelected = category == selected
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(category.color())
                    .border(
                        width = 3.dp,
                        color = if (isSelected) Color.Black else Color.Transparent,
                        shape = CircleShape,
                    )
                    .clickable { onCategorySelected(category) },
            ) {
                Text(
                    text = category.displayName(),
                    color = Color.Black,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(12.dp),
                )
            }
        }
    }
}
