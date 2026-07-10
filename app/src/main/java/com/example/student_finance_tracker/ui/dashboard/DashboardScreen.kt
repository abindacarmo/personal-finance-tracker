package com.example.student_finance_tracker.ui.dashboard

import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_finance_tracker.data.Transaction
import com.example.student_finance_tracker.ui.theme.*
import com.example.student_finance_tracker.ui.transaction.TransactionViewModel
import com.example.student_finance_tracker.utils.formatDollar

@Composable
fun DashboardScreen(
    onNavigateToAddTransaction: () -> Unit = {},
    transactionViewModel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val transactions by transactionViewModel.allTransactions.collectAsState(initial = emptyList())

    DashboardContent(
        transactions = transactions,
        onNavigateToAddTransaction = onNavigateToAddTransaction,
        onSyncClick = { transactionViewModel.syncDataWithCloud() },
        onSaveTransaction = { amount, category, note, isExpense ->
            transactionViewModel.saveTransaction(
                amount = amount,
                category = category,
                date = System.currentTimeMillis(),
                note = note,
                isExpense = isExpense,
                onSuccess = {
                    // Berhasil simpan
                }
            )
        }
    )
}

@Composable
fun DashboardContent(
    transactions: List<Transaction>,
    onNavigateToAddTransaction: () -> Unit,
    onSyncClick: () -> Unit,
    onSaveTransaction: (String, String, String, Boolean) -> Unit
) {
    Scaffold(
        bottomBar = { BottomNavigationBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddTransaction,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = Modifier.size(24.dp))
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { HeaderSection(onSyncClick = onSyncClick) }
            item { BalanceSection(transactions = transactions) }
            item { ExpenseSummarySection() }
            item { AddTransactionSection(onSave = onSaveTransaction) }
            item { RecentEntriesSection(transactions = transactions) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun AddTransactionSection(onSave: (String, String, String, Boolean) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }
    var note by remember { mutableStateOf("") }
    var isExpense by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isExpense) "QUICK ADD EXPENSE" else "QUICK ADD INCOME",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isExpense) ExpenseRed else IncomeGreen
                )
                
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Expense", fontSize = 12.sp)
                    Switch(
                        checked = !isExpense,
                        onCheckedChange = { isExpense = !it },
                        modifier = Modifier.scale(0.8f)
                    )
                    Text("Income", fontSize = 12.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Amount", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("0") },
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
                    Text(text = "Category", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
            Text(text = "Note", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            TextField(
                value = note,
                onValueChange = { note = it },
                placeholder = { Text("What for?") },
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
                    if (amount.isNotBlank()) {
                        onSave(amount, category, note, isExpense)
                        // Kosongkan form setelah klik
                        amount = ""
                        note = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isExpense) ExpenseRed else IncomeGreen
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save Record", color = Color.White)
            }
        }
    }
}

@Composable
fun HeaderSection(onSyncClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondary)
            ) {
                Icon(
                    Icons.Default.Person, 
                    contentDescription = null, 
                    modifier = Modifier.align(Alignment.Center).size(24.dp), 
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(text = "Luminous Finance", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        }
        Row {
            IconButton(onClick = onSyncClick) {
                Icon(Icons.Default.CloudUpload, contentDescription = "Sync", tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = { }) {
                Icon(Icons.Default.Settings, contentDescription = "Settings", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun BalanceSection(transactions: List<Transaction>) {
    val totalIncome = transactions.filter { it.isIncome }.sumOf { it.amount }
    val totalExpenses = transactions.filter { it.isExpense }.sumOf { it.amount }
    val balance = totalIncome - totalExpenses

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Available Balance", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        Text(
            text = formatDollar(balance), 
            fontSize = 32.sp, 
            fontWeight = FontWeight.ExtraBold, 
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            SummaryCard(title = "Income", amount = "+ ${formatDollar(totalIncome)}", color = IncomeGreen, modifier = Modifier.weight(1f))
            SummaryCard(title = "Expenses", amount = "- ${formatDollar(totalExpenses)}", color = ExpenseRed, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier, 
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)), 
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun RecentEntriesSection(transactions: List<Transaction>) {
    Column {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Text(text = "Recent Entries", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            Text(text = "View All", color = MaterialTheme.colorScheme.primary, fontSize = 12.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        transactions.take(5).forEach { transaction ->
            TransactionItem(
                icon = if (transaction.isExpense) Icons.Default.Restaurant else Icons.Default.Payments,
                title = transaction.category,
                subtitle = transaction.note,
                amount = if (transaction.isExpense) "- ${formatDollar(transaction.amount)}" else "+ ${formatDollar(transaction.amount)}",
                isIncome = transaction.isIncome
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(icon: ImageVector, title: String, subtitle: String, amount: String, isIncome: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(), 
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface), 
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.Center).size(20.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(text = subtitle, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(text = amount, color = if (isIncome) IncomeGreen else ExpenseRed, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = true, onClick = { }, 
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) }, 
            label = { Text("Dashboard") }
        )
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.History, contentDescription = null) }, label = { Text("History") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = null) }, label = { Text("Trends") })
    }
}

@Composable
fun ExpenseSummarySection() {
    Column {
        Text(text = "Spending Summary", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
    }
}
