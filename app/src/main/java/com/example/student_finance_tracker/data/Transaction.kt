package com.example.student_finance_tracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val amount: Double = 0.0,
    val category: String = "Food",
    val date: Long = System.currentTimeMillis(),
    val note: String = "",
    val isIncome: Boolean = false,
    val isExpense: Boolean = false,
)
