package com.example.student_finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_finance_tracker.ui.dashboard.DashboardScreen
import com.example.student_finance_tracker.ui.transaction.AddTransactionScreen
import com.example.student_finance_tracker.ui.theme.StudentfinancetrackerTheme
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudentfinancetrackerTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "dashboard") {
        composable("dashboard") {
            // Gunakan TransactionViewModel yang sama agar konsisten
            val transactionViewModel: TransactionViewModel = viewModel()
            DashboardScreen(
                onNavigateToAddTransaction = {
                    navController.navigate("add_transaction")
                },
                transactionViewModel = transactionViewModel
            )
        }
        composable("add_transaction") {
            AddTransactionScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
