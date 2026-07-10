package com.example.student_finance_tracker.ui.transaction

import android.app.Application
import android.widget.Toast
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

    fun syncDataWithCloud() {
        viewModelScope.launch {
            try {
                val result = repository.syncWithCloud()
                result.onSuccess {
                    Toast.makeText(getApplication(), "Berhasil sinkron ke Cloud!", Toast.LENGTH_SHORT).show()
                }.onFailure { e ->
                    Toast.makeText(getApplication(), "Gagal sinkron: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveTransaction(
        amount: String,
        category: String,
        date: Long,
        note: String,
        isExpense: Boolean,
        onSuccess: () -> Unit
    ) {
        val amountDouble = amount.toDoubleOrNull() ?: 0.0
        if (amountDouble <= 0.0) {
            Toast.makeText(getApplication(), "Masukkan jumlah uang yang valid", Toast.LENGTH_SHORT).show()
            return
        }

        viewModelScope.launch {
            try {
                val newTransaction = Transaction(
                    amount = amountDouble,
                    category = category,
                    date = date,
                    note = note,
                    isExpense = isExpense,
                    isIncome = !isExpense
                )
                repository.insert(newTransaction)
                Toast.makeText(getApplication(), "Transaksi berhasil disimpan!", Toast.LENGTH_SHORT).show()
                onSuccess()
            } catch (e: Exception) {
                Toast.makeText(getApplication(), "Gagal menyimpan: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }
}
