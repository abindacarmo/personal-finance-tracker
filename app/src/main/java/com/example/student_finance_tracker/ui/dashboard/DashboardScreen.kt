package com.example.student_finance_tracker.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.ui.theme.*
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel

@Composable
fun DashboardScreen(
    onNavigateToAddTransaction: () -> Unit = {},
    transactionViewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val transactions by transactionViewModel.allTransactions.collectAsState(initial = emptyList())

    Scaffold(
        bottomBar = { BottomNavigationBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTransaction,
                containerColor = PrimaryPink,
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(24.dp))
            }
        },
        containerColor = BackgroundLight
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HeaderSection() }
            item { BalanceSection() }
            item { ExpenseSummarySection() }
            item { AddTransactionSection(viewModel = transactionViewModel) }
            item { RecentEntriesSection(transactions = transactions) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun AddTransactionSection(viewModel: TransactionViewModel) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var note by remember { mutableStateOf("") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "QUICK ADD EXPENSE",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Amount", fontSize = 12.sp, color = TextGray)
                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("0", color = TextGray.copy(alpha = 0.5f)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = true
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Category", fontSize = 12.sp, color = TextGray)
                    TextField(
                        value = category,
                        onValueChange = { category = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = true
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = "Note", fontSize = 12.sp, color = TextGray)
            TextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("What for?", color = TextGray.copy(alpha = 0.5f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = { 
                    if (amount.isNotEmpty()) {
                        viewModel.saveTransaction(
                            amount = amount,
                            category = category,
                            date = System.currentTimeMillis(),
                            note = note,
                            isExpense = true,
                            onSuccess = {
                                amount = ""
                                note = ""
                            }
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save Record", color = Color.White)
            }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(CircleShape).background(SecondaryPink)) {
                Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.align(Alignment.Center).size(24.dp), tint = Color.White)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Luminous Finance", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
        }
        IconButton(onClick = { }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = PrimaryPink)
        }
    }
}

@Composable
fun BalanceSection() {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Available Balance", color = TextGray, fontSize = 14.sp)
        Text(text = "$825.50", fontSize = 32.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SummaryCard(title = "Income", amount = "+ $280", color = IncomeGreen, modifier = Modifier.weight(1f))
            SummaryCard(title = "Expenses", amount = "- $120", color = ExpenseRed, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f)), shape = RoundedCornerShape(16.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, color = TextGray)
            Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun RecentEntriesSection(transactions: List<Transaction>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Recent Entries", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Text(text = "View All", color = ExpenseRed, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        transactions.take(3).forEach { transaction ->
            TransactionItem(
                icon = if (transaction.isExpense) Icons.Default.Restaurant else Icons.Default.Payments,
                title = transaction.category,
                subtitle = transaction.note,
                amount = if (transaction.isExpense) "- $${transaction.amount}" else "+ $${transaction.amount}",
                isIncome = !transaction.isExpense
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(icon: ImageVector, title: String, subtitle: String, amount: String, isIncome: Boolean) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)), shape = RoundedCornerShape(16.dp)) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundLight)) {
                Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.Center).size(20.dp), tint = TextGray)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = subtitle, fontSize = 12.sp, color = TextGray)
            }
            Text(text = amount, color = if (isIncome) IncomeGreen else ExpenseRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(selected = true, onClick = { }, icon = { Icon(Icons.Default.Dashboard, contentDescription = null) }, label = { Text("Dashboard") }, colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, indicatorColor = SecondaryPink.copy(alpha = 0.1f)))
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.History, contentDescription = null) }, label = { Text("History") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = null) }, label = { Text("Trends") })
    }
}

@Composable
fun ExpenseSummarySection() {
    Text(text = "Spending Summary", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
    // Implementasi grafik bisa ditambahkan di sini
}
