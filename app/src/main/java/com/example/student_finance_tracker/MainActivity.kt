package com.example.student_finance_tracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.student_finance_tracker.ui.dashboard.DashboardScreen
import com.example.student_finance_tracker.ui.transaction.AddTransactionScreen
import com.example.student_finance_tracker.ui.theme.StudentfinancetrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
            DashboardScreen(
                onNavigateToAddTransaction = {
                    navController.navigate("add_transaction")
                }
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
