package com.example.student_finance_tracker.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Utilitas untuk memformat angka ke dalam format mata uang Dolar ($).
 */
fun formatDollar(amount: Double): String {
    val localeUS = Locale.US
    val formatter = NumberFormat.getCurrencyInstance(localeUS)
    return formatter.format(amount)
}
