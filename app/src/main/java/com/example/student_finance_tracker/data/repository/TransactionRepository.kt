package com.example.student_finance_tracker.data.repository

import com.example.student_finance_tracker.data.local.dao.TransactionDao
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.data.remote.FirestoreService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionRepository(
    private val transactionDao: TransactionDao,
    private val firestoreService: FirestoreService = FirestoreService()
) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        // 1. Simpan ke database lokal (Room)
        transactionDao.insertTransaction(transaction)
        
        // 2. Simpan ke cloud di latar belakang
        CoroutineScope(Dispatchers.IO).launch {
            try {
                firestoreService.saveTransaction(transaction)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun delete(id: String) {
        transactionDao.deleteTransactionById(id)
    }

    /**
     * Mengunggah data lokal ke Firestore.
     */
    suspend fun syncWithCloud(): Result<Unit> {
        return try {
            val localTransactions = allTransactions.first()
            firestoreService.uploadTransactions(localTransactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Mengambil data dari Firestore dan memperbarui database lokal.
     */
    suspend fun fetchFromCloud(): Result<Unit> {
        return try {
            val result = firestoreService.fetchTransactions()
            if (result.isSuccess) {
                val remoteTransactions = result.getOrNull() ?: emptyList()
                withContext(Dispatchers.IO) {
                    remoteTransactions.forEach { transaction ->
                        transactionDao.insertTransaction(transaction)
                    }
                }
                Result.success(Unit)
            } else {
                Result.failure(result.exceptionOrNull() ?: Exception("Unknown error fetching from cloud"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
