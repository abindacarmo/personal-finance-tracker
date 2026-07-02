package com.example.student_finance_tracker.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.TrendingUp
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_finance_tracker.ui.theme.*
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(
    onBackClick: () -> Unit = {},
    viewmodel: TransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var isExpense by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())
    
    val formattedDate = remember(datePickerState.selectedDateMillis) {
        val millis = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
        Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("dd / MM / yyyy"))
    }

    var selectedCategory by remember(isExpense) { 
        mutableStateOf(if (isExpense) "Food" else "Salary") 
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Add Transaction",
                        color = PrimaryPink,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryPink)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        bottomBar = {
            // Pindahkan tombol simpan ke Bottom Bar agar selalu terlihat
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.Transparent,
                tonalElevation = 8.dp
            ) {
                Button(
                    onClick = {
                        if (amount.isNotEmpty()) {
                            viewmodel.saveTransaction(
                                amount = amount,
                                category = selectedCategory,
                                date = datePickerState.selectedDateMillis ?: System.currentTimeMillis(),
                                note = note,
                                isExpense = isExpense,
                                onSuccess = { onBackClick() }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.Save, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Save Transaction", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        },
        containerColor = BackgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            TransactionTypeToggle(
                isExpense = isExpense,
                onToggle = { isExpense = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "AMOUNT ($)", fontSize = 12.sp, color = TextGray, fontWeight = FontWeight.Bold)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "$", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = PrimaryPink.copy(alpha = 0.5f))
                Spacer(modifier = Modifier.width(8.dp))
                TextField(
                    value = amount,
                    onValueChange = { if (it.all { char -> char.isDigit() || char == '.' }) amount = it },
                    placeholder = { Text("0", fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = TextGray.copy(alpha = 0.2f)) },
                    modifier = Modifier.widthIn(min = 100.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    ),
                    textStyle = LocalTextStyle.current.copy(fontSize = 48.sp, fontWeight = FontWeight.ExtraBold, color = TextDark),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Choose Category", modifier = Modifier.fillMaxWidth(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(modifier = Modifier.height(16.dp))

            if (isExpense) {
                ExpenseCategorySection(selectedCategory = selectedCategory, onCategorySelected = { selectedCategory = it })
            } else {
                IncomeCategorySection(selectedCategory = selectedCategory, onCategorySelected = { selectedCategory = it })
            }

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = formattedDate,
                onValueChange = {},
                readOnly = true,
                label = { Text("Date") },
                trailingIcon = { 
                    Icon(
                        imageVector = Icons.Default.CalendarMonth, 
                        contentDescription = "Pick Date",
                        modifier = Modifier.clickable { showDatePicker = true }
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Notes (Optional)") },
                placeholder = { Text("Write details here...") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            BudgetInfoCard(isExpense = isExpense)
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = { TextButton(onClick = { showDatePicker = false }) { Text("OK") } }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun TransactionTypeToggle(isExpense: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color.White)
            .padding(4.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (isExpense) SecondaryPink else Color.Transparent)
                .clickable { onToggle(true) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Expense", color = if (isExpense) Color.White else TextGray, fontWeight = FontWeight.Bold)
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(10.dp))
                .background(if (!isExpense) SecondaryPink else Color.Transparent)
                .clickable { onToggle(false) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Income", color = if (!isExpense) Color.White else TextGray, fontWeight = FontWeight.Bold)
        }
    }
}

data class CategoryItem(val name: String, val icon: ImageVector)

@Composable
fun CategoryGrid(categories: List<CategoryItem>, selectedCategory: String, onCategorySelected: (String) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier.height(180.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        userScrollEnabled = false
    ) {
        items(categories) { category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable { onCategorySelected(category.name) }
            ) {
                Box(
                    modifier = Modifier
                        .size(54.dp)
                        .clip(CircleShape)
                        .background(if (selectedCategory == category.name) SecondaryPink.copy(alpha = 0.15f) else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        category.icon,
                        contentDescription = category.name,
                        tint = if (selectedCategory == category.name) PrimaryPink else TextGray.copy(alpha = 0.6f),
                        modifier = Modifier.size(26.dp)
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = category.name, fontSize = 11.sp, color = TextGray)
            }
        }
    }
}

@Composable
fun BudgetInfoCard(isExpense: Boolean) {
    val backgroundColor = if (isExpense) SecondaryPink.copy(alpha = 0.15f) else IncomeGreen.copy(alpha = 0.15f)
    val contentColor = if (isExpense) PrimaryPink else IncomeGreen

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(modifier = Modifier.padding(20.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(modifier = Modifier.size(44.dp).clip(CircleShape).background(Color.White), contentAlignment = Alignment.Center) {
                Icon(if (isExpense) Icons.Default.Star else Icons.AutoMirrored.Filled.TrendingUp, null, tint = contentColor)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = if (isExpense) "Stay on budget today!" else "Income analysis", fontWeight = FontWeight.ExtraBold, color = contentColor)
                Text(text = if (isExpense) "Your spending is lower than usual." else "You've earned more today.", fontSize = 12.sp, color = contentColor.copy(alpha = 0.8f))
            }
        }
    }
}
