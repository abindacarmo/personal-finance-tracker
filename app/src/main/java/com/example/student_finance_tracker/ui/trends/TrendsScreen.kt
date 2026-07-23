package com.example.student_finance_tracker.ui.trends

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.navigation.Screen
import com.example.student_finance_tracker.ui.theme.ExpenseRed
import com.example.student_finance_tracker.ui.theme.IncomeGreen
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel
import com.example.student_finance_tracker.utils.formatDollar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrendsScreen(
    navController: NavHostController,
    transactionViewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val transactions by transactionViewModel.allTransactions.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Financial Trends", fontWeight = FontWeight.Bold) }
            )
        },
        bottomBar = {
            TrendsBottomNavigationBar(navController)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                SectionTitle("Monthly Overview")
                SimpleBarChart(transactions)
            }

            item {
                SectionTitle("Spending by Category")
                CategoryBreakdown(transactions)
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
fun SimpleBarChart(transactions: List<Transaction>) {
    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }.toFloat()
    val totalExpense = transactions.filter { it.isExpense }.sumOf { it.amount }.toFloat()
    val maxVal = maxOf(totalIncome, totalExpense).coerceAtLeast(1f)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.Bottom
            ) {
                Bar(
                    value = totalIncome,
                    maxValue = maxVal,
                    color = IncomeGreen,
                    label = "Income"
                )

                Bar(
                    value = totalExpense,
                    maxValue = maxVal,
                    color = ExpenseRed,
                    label = "Expense"
                )
            }
        }
    }
}

@Composable
fun RowScope.Bar(value: Float, maxValue: Float, color: Color, label: String) {
    val heightPercentage = if (maxValue > 0) value / maxValue else 0f

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = formatDollar(value.toDouble()),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(heightPercentage.coerceAtLeast(0.05f))
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .background(color)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = label, fontSize = 12.sp)
    }
}

@Composable
fun CategoryBreakdown(transactions: List<Transaction>) {
    val expenses = transactions.filter { it.isExpense }
    val totalExpenseSum = expenses.sumOf { it.amount }

    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }
        .toList()
        .sortedByDescending { it.second }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        categoryTotals.forEach { (category, amount) ->
            val percentage = if (totalExpenseSum > 0) (amount / totalExpenseSum).toFloat() else 0f

            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = category, fontSize = 14.sp)
                    Text(text = formatDollar(amount), fontSize = 14.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(6.dp))
                LinearProgressIndicator(
                    progress = { percentage },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
            }
        }
    }
}

@Composable
fun TrendsBottomNavigationBar(navController: NavHostController) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = false,
            onClick = { 
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = true }
                }
            },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* Navigasi ke History */ },
            icon = { Icon(Icons.Default.History, contentDescription = null) },
            label = { Text("History") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = null) },
            label = { Text("Trends") }
        )
    }
}
