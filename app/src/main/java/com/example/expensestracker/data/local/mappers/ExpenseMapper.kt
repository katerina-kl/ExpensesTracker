package com.example.expensestracker.data.local.mappers

import com.example.expensestracker.data.local.ExpenseEntity
import com.example.expensestracker.domain.model.Category
import com.example.expensestracker.domain.model.Expense
import com.example.expensestracker.domain.model.Price


fun Expense.toEntity(): ExpenseEntity {
    return ExpenseEntity(
        title = this.title,
        content = this.content,
        timestamp = this.timestamp,
        categoryIcon = this.category.icon,
        categoryName = this.category.name,
        categoryColor = this.category.color,
        priceAmount = this.price.amount,
        priceCurrency =  this.price.currency,
        id = this.id
    )
}



fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        title = this.title,
        content = this.content,
        timestamp = this.timestamp,
        category = Category(this.categoryIcon,this.categoryName,this.categoryColor),
        price = Price(this.priceAmount,this.priceCurrency),
        id = this.id
    )
}



