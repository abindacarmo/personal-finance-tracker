package com.example.student_finance_tracker.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.student_finance_tracker.ui.dashboard.DashboardScreen
import com.example.student_finance_tracker.ui.transaction.AddTransactionScreen
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel
import com.example.student_finance_tracker.ui.trends.TrendsScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route
    ) {
        composable(Screen.Dashboard.route) {
            val transactionViewModel: TransactionViewModel = viewModel()
            DashboardScreen(
                navController = navController, // Tambahkan baris ini
                onNavigateToAddTransaction = {
                    navController.navigate(Screen.AddTransaction.route)
                },
                transactionViewModel = transactionViewModel
            )
        }
        composable(Screen.AddTransaction.route) {
            AddTransactionScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
        composable(Screen.Trends.route) {
            TrendsScreen(navController = navController)
        }
    }
}
