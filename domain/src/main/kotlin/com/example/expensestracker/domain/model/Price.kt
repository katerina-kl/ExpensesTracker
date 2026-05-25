package com.example.expensestracker.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Price(
    val amount: Double,
    val currency: String,
)
