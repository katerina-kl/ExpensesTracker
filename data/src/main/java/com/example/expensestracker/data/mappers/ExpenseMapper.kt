package com.example.expensestracker.data.mappers

import com.example.expensestracker.data.local.ExpenseEntity
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.ExpenseCategory
import com.example.expensestracker.domain.model.Price

internal fun Expense.toEntity(): ExpenseEntity = ExpenseEntity(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    categoryId = category.id,
    priceAmount = price.amount,
    priceCurrency = price.currency,
)

internal fun ExpenseEntity.toDomain(): Expense = Expense(
    id = id,
    title = title,
    content = content,
    timestamp = timestamp,
    category = ExpenseCategory.fromId(categoryId),
    price = Price(amount = priceAmount, currency = priceCurrency),
)
