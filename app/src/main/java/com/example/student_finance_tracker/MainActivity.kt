package com.example.student_finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_finance_tracker.ui.dashboard.DashboardScreen
import com.example.student_finance_tracker.ui.transaction.AddTransactionScreen
import com.example.student_finance_tracker.ui.theme.StudentfinancetrackerTheme
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Inisialisasi Firebase dengan proteksi agar tidak double init
        try {
            FirebaseApp.initializeApp(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
