package com.example.expensestracker.core.designsystem.category

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.expensestracker.core.designsystem.R
import com.example.expensestracker.core.designsystem.theme.BabyBlue
import com.example.expensestracker.core.designsystem.theme.LightGreen
import com.example.expensestracker.core.designsystem.theme.RedOrange
import com.example.expensestracker.core.designsystem.theme.RedPink
import com.example.expensestracker.core.designsystem.theme.Violet
import com.example.expensestracker.domain.model.ExpenseCategory

/**
 * Maps a pure-domain [ExpenseCategory] to UI concerns (icon and color).
 *
 * Keeping this mapping in the design system means the domain layer remains free of Android types,
 * while themes can vary their visual representation independently.
 */
@DrawableRes
fun ExpenseCategory.iconRes(): Int = when (this) {
    ExpenseCategory.Home -> R.drawable.baseline_home_filled_24
    ExpenseCategory.Groceries -> R.drawable.baseline_local_grocery_store_24
    ExpenseCategory.Transportation -> R.drawable.baseline_directions_car_filled_24
    ExpenseCategory.Health -> R.drawable.baseline_healing_24
    ExpenseCategory.SelfCare -> R.drawable.baseline_person_24
}

fun ExpenseCategory.color(): Color = when (this) {
    ExpenseCategory.Home -> RedOrange
    ExpenseCategory.Groceries -> LightGreen
    ExpenseCategory.Transportation -> Violet
    ExpenseCategory.Health -> BabyBlue
    ExpenseCategory.SelfCare -> RedPink
}

@Composable
@ReadOnlyComposable
fun ExpenseCategory.displayName(): String = stringResource(
    when (this) {
        ExpenseCategory.Home -> R.string.category_home
        ExpenseCategory.Groceries -> R.string.category_groceries
        ExpenseCategory.Transportation -> R.string.category_transportation
        ExpenseCategory.Health -> R.string.category_health
        ExpenseCategory.SelfCare -> R.string.category_self_care
    },
)
