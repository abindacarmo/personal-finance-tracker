package com.example.student_finance_tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.data.local.dao.TransactionDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID

class FinanceViewModel(private val dao: TransactionDao) : ViewModel() {

    val transactions: StateFlow<List<Transaction>> = dao.getAllTransactions()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addTransaction(desc: String, amount: Double, isIncome: Boolean, category: String) {
        viewModelScope.launch {
            val newTransaction = Transaction(
                id = UUID.randomUUID().toString(),
                description = desc,
                amount = amount,
                category = category,
                isIncome = isIncome,
                isExpense = !isIncome,
                note = "" // Menambahkan nilai eksplisit untuk menghindari error 'No value passed'
            )
            dao.insertTransaction(newTransaction)
        }
    }
}
