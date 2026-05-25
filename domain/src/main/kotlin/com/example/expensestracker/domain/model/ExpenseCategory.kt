package com.example.expensestracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class ExpenseCategory(val id: String) {
    Home("home"),
    Groceries("groceries"),
    Transportation("transportation"),
    Health("health"),
    SelfCare("self_care");

    companion object {
        fun fromId(id: String): ExpenseCategory =
            entries.firstOrNull { it.id == id } ?: Home
    }
}
