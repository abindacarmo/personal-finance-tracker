package com.example.student_finance_tracker.data.repository

import com.example.student_finance_tracker.data.local.dao.TransactionDao
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.data.remote.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val firestoreService: FirestoreService = FirestoreService()
) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        // 1. Simpan ke database lokal (Room) - ini harus diprioritaskan
        transactionDao.insertTransaction(transaction)
        
        // 2. Simpan ke cloud di "latar belakang" (Background Thread)
        // Kita gunakan CoroutineScope baru agar tidak memblokir UI saat koneksi lambat
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firestoreService.saveTransaction(transaction)
            } catch (e: Exception) {
                // Jika gagal cloud, tidak apa-apa, data sudah aman di lokal
                e.printStackTrace()
            }
        }
    }

    suspend fun delete(id: String) {
        transactionDao.deleteTransactionById(id)
    }

    suspend fun syncWithCloud(): Result<Unit> {
        return try {
            val localTransactions = allTransactions.first()
            firestoreService.uploadTransactions(localTransactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
