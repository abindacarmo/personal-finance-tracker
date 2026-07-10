package com.example.student_finance_tracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.data.local.dao.TransactionDao

@Database(entities = [Transaction::class], version = 2) // Diubah ke versi 2
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "student_finance_db")
                    .fallbackToDestructiveMigration() // Ini akan menghapus data lama dan membuat baru dengan struktur String ID
                    .build()
                    .also { Instance = it }
            }
        }
    }
}