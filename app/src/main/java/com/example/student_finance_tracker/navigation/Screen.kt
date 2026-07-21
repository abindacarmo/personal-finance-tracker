package com.example.student_finance_tracker.navigation

sealed class Screen(val route: String) {
    object Dashboard : Screen("dashboard")
    object AddTransaction : Screen("add_transaction")
    object Trends : Screen("trends")
}
