package com.example.student_finance_tracker.data.remote

import com.example.student_finance_tracker.data.Transaction
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val firestore = FirebaseFirestore.getInstance()
    private val transactionsCollection = firestore.collection("transactions")

    suspend fun uploadTransactions(transactions: List<Transaction>): Result<Unit> {
        return try {
            val batch = firestore.batch()
            transactions.forEach { transaction ->
                val docRef = transactionsCollection.document(transaction.id)
                batch.set(docRef, transaction)
            }
            batch.commit().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun saveTransaction(transaction: Transaction): Result<Unit> {
        return try {
            transactionsCollection.document(transaction.id).set(transaction).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
