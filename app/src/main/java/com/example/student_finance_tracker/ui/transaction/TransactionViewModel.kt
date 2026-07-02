package com.example.student_finance_tracker.ui.transaction

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.student_finance_tracker.data.local.AppDatabase
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.data.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TransactionRepository
    val allTransactions: Flow<List<Transaction>>

    init {
        val dao = AppDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(dao)
        allTransactions = repository.allTransactions
    }

    fun saveTransaction(
        amount: String,
        category: String,
        date: Long,
        note: String,
        isExpense: Boolean,
        onSuccess: () -> Unit
    ) {
        // Validasi sederhana: Jangan simpan jika angka kosong atau tidak valid
        val amountDouble = amount.toDoubleOrNull() ?: 0.0
        if (amountDouble <= 0.0) return

        viewModelScope.launch {
            val newTransaction = Transaction(
                amount = amountDouble,
                category = category,
                date = date,
                note = note,
                isExpense = isExpense
            )
            repository.insert(newTransaction)
            onSuccess() // Panggil callback setelah berhasil simpan
        }
    }
}
