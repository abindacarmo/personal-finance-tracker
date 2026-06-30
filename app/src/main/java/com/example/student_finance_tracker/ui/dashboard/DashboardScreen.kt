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
import com.example.student_finance_tracker.viewmodel.FinanceViewModel

@Composable
fun DashboardScreen(
    onNavigateToAddTransaction: () -> Unit = {},
    viewModel: FinanceViewModel? = null // Diubah menjadi nullable (? = null)
) {
    // Mengambil data secara aman jika viewModel ada, jika tidak gunakan list kosong
    // Menambahkan tipe eksplisit <Transaction> untuk memperbaiki error inferensi tipe
    val transactions by viewModel?.transactions?.collectAsState(initial = emptyList<Transaction>()) 
        ?: remember { mutableStateOf(emptyList<Transaction>()) }

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
            item { AddTransactionSection() }
            item { RecentEntriesSection() }
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(SecondaryPink)
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.Center).size(24.dp),
                    tint = Color.White
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Luminous Finance",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink
            )
        }
        IconButton(onClick = { /* Settings */ }) {
            Icon(Icons.Default.Settings, contentDescription = "Settings", tint = PrimaryPink)
        }
    }
}

@Composable
fun BalanceSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Available Balance", color = TextGray, fontSize = 14.sp)
        Text(
            text = "$825.50",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextDark
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SummaryCard(
                title = "Income",
                amount = "+ $280",
                color = IncomeGreen,
                modifier = Modifier.weight(1f)
            )
            SummaryCard(
                title = "Expenses",
                amount = "- $120",
                color = ExpenseRed,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun SummaryCard(title: String, amount: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.7f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 12.sp, color = TextGray)
            Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
        }
    }
}

@Composable
fun ExpenseSummarySection() {
    var selectedPeriod by remember { mutableStateOf("Weekly") }
    val periods = listOf("Weekly", "Monthly", "Yearly")

    Column {
        Text(
            text = "Spending Summary",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        
        Row(
            modifier = Modifier.padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            periods.forEach { period ->
                val isSelected = selectedPeriod == period
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = period,
                        color = if (isSelected) PrimaryPink else TextGray,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .clickable { selectedPeriod = period }
                            .padding(bottom = 4.dp)
                    )
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .width(20.dp)
                                .height(2.dp)
                                .background(PrimaryPink, RoundedCornerShape(2.dp))
                        )
                    } else {
                        Box(modifier = Modifier.height(2.dp))
                    }
                }
            }
        }
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f)),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$selectedPeriod Spending", fontSize = 12.sp, color = TextGray)
                    Text(
                        text = when(selectedPeriod) {
                            "Weekly" -> "$120.00"
                            "Monthly" -> "$480.00"
                            else -> "$5,200.00"
                        }, 
                        fontSize = 14.sp, 
                        fontWeight = FontWeight.Bold, 
                        color = PrimaryPink
                    )
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val bars = when(selectedPeriod) {
                        "Weekly" -> listOf(0.4f, 0.6f, 0.5f, 0.9f, 0.3f, 0.5f, 0.4f)
                        "Monthly" -> listOf(0.7f, 0.4f, 0.8f, 0.5f, 0.6f, 0.3f, 0.9f)
                        else -> listOf(0.5f, 0.7f, 0.6f, 0.8f, 0.9f, 0.4f, 0.5f)
                    }
                    val labels = when(selectedPeriod) {
                        "Weekly" -> listOf("Mo", "Tu", "We", "Th", "Fr", "Sa", "Su")
                        "Monthly" -> listOf("W1", "W2", "W3", "W4", "W5", "W6", "W7")
                        else -> listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
                    }
                    val amounts = when(selectedPeriod) {
                        "Weekly" -> listOf("$12", "$18", "$15", "$28", "$10", "$15", "$12")
                        "Monthly" -> listOf("$80", "$50", "$90", "$65", "$75", "$40", "$80")
                        else -> listOf("$400", "$600", "$550", "$700", "$820", "$380", "$500")
                    }
                    
                    bars.forEachIndexed { index, barHeight ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxHeight().weight(1f)
                        ) {
                            if (index == 3) {
                                Text(text = amounts[index], fontSize = 10.sp, fontWeight = FontWeight.Bold, color = PrimaryPink)
                                Spacer(modifier = Modifier.height(4.dp))
                            } else {
                                Box(modifier = Modifier.height(14.dp)) 
                            }
                            
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(TextGray.copy(alpha = 0.05f))
                                )
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .fillMaxHeight(barHeight)
                                        .clip(RoundedCornerShape(6.dp))
                                        .background(if (index == 3) PrimaryPink else SecondaryPink.copy(alpha = 0.4f))
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = labels[index],
                                fontSize = 10.sp, 
                                color = if(index == 3) PrimaryPink else TextGray,
                                fontWeight = if(index == 3) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddTransactionSection() {
    var description by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Food") }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.6f)),
        shape = RoundedCornerShape(24.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                text = "ADD TRANSACTION",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = PrimaryPink
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(text = "Description", fontSize = 12.sp, color = TextGray)
            TextField(
                value = description,
                onValueChange = { description = it },
                placeholder = { Text("Afternoon coffee...", fontSize = 16.sp, color = TextDark.copy(alpha = 0.3f)) },
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = TextDark)
            )
            
            HorizontalDivider(modifier = Modifier.padding(bottom = 8.dp), color = TextGray.copy(alpha = 0.1f))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Amount", fontSize = 12.sp, color = TextGray)
                    TextField(
                        value = amount,
                        onValueChange = { amount = it },
                        placeholder = { Text("0", fontSize = 16.sp, color = TextDark.copy(alpha = 0.3f)) },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        ),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = TextDark)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Category", fontSize = 12.sp, color = TextGray)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        TextField(
                            value = category,
                            onValueChange = { category = it },
                            modifier = Modifier.weight(1f),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                            ),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = TextDark)
                        )
                        Icon(Icons.Default.KeyboardArrowDown, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Button(
                onClick = { /* Save action with description, amount, category */ },
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
fun RecentEntriesSection() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recent Manual Entries",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
            )
            Text(text = "View All", color = ExpenseRed, fontSize = 12.sp)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        TransactionItem(Icons.Default.Restaurant, "Bento Lunch", "Today • Food", "- $3.00", false)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionItem(Icons.Default.DirectionsCar, "Online Taxi", "Yesterday • Transport", "- $0.80", false)
        Spacer(modifier = Modifier.height(8.dp))
        TransactionItem(Icons.Default.AccountBalanceWallet, "Transfer from Mom", "Oct 20 • Pocket Money", "+ $32.00", true)
    }
}

@Composable
fun TransactionItem(icon: ImageVector, title: String, subtitle: String, amount: String, isIncome: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundLight)
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.align(Alignment.Center).size(20.dp), tint = TextGray)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = subtitle, fontSize = 12.sp, color = TextGray)
            }
            Text(
                text = amount,
                color = if (isIncome) IncomeGreen else ExpenseRed,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(containerColor = Color.White) {
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.Dashboard, contentDescription = null) },
            label = { Text("Dashboard") },
            colors = NavigationBarItemDefaults.colors(selectedIconColor = PrimaryPink, selectedTextColor = PrimaryPink, indicatorColor = SecondaryPink.copy(alpha = 0.1f))
        )
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.History, contentDescription = null) }, label = { Text("History") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.AutoMirrored.Filled.ShowChart, contentDescription = null) }, label = { Text("Trends") })
        NavigationBarItem(selected = false, onClick = { }, icon = { Icon(Icons.Default.AccountBalance, contentDescription = null) }, label = { Text("Vault") })
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
    StudentfinancetrackerTheme {
        DashboardScreen(
            onNavigateToAddTransaction = {}
        )
    }
}
