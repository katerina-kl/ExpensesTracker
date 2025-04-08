package com.example.expensestracker.domain.model

import androidx.compose.ui.graphics.toArgb
import androidx.room.PrimaryKey
import com.example.expensestracker.R
import com.example.expensestracker.presentation.ui.theme.BabyBlue
import com.example.expensestracker.presentation.ui.theme.LightGreen
import com.example.expensestracker.presentation.ui.theme.RedOrange
import com.example.expensestracker.presentation.ui.theme.RedPink
import com.example.expensestracker.presentation.ui.theme.Violet


data class Expense(
    val title: String,
    val content: String,
    val timestamp: Long,
    val category: Category,
    val price: Price,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val categories = listOf(
           Category(R.drawable.baseline_home_filled_24,"Home", RedOrange.toArgb()) ,
            Category(R.drawable.baseline_local_grocery_store_24,"Groceries", LightGreen.toArgb()) ,
            Category(R.drawable.baseline_directions_car_filled_24,"Transportation", Violet.toArgb()) ,
            Category(R.drawable.baseline_healing_24,"Health", BabyBlue.toArgb()) ,
            Category(R.drawable.baseline_person_24,"Self Care", RedPink.toArgb()) ,
        )
    }
}

data class Category(
    val icon: Int,
    val name: String,
    val color: Int
    )

data class Price(val amount: Double?, val currency: String)

class InvalidExpenseException(message: String): Exception(message)