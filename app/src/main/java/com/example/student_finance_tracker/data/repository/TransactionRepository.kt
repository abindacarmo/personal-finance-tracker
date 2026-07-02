package com.example.student_finance_tracker.data.repository

import com.example.student_finance_tracker.data.local.dao.TransactionDao
import com.example.student_finance_tracker.data.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDao) {
    val allTransactions: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insertTransaction(transaction)
    }

    suspend fun delete(id: Int) {
        transactionDao.deleteTransactionById(id)
    }
}
