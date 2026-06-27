package com.example.student_finance_tracker.ui.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Notes
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.student_finance_tracker.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen(onBackClick: () -> Unit = {}) {
    var isExpense by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("0") }
    
    // State kategori terpilih yang otomatis reset saat pindah tipe (Expense/Income)
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
                actions = {
                    IconButton(onClick = { /* Handle menu */ }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "More", tint = PrimaryPink)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = BackgroundLight)
            )
        },
        containerColor = BackgroundLight
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Toggle Switch (Expense / Income)
            TransactionTypeToggle(
                isExpense = isExpense,
                onToggle = { isExpense = it }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Amount Section
            Text(
                text = "AMOUNT ($)",
                fontSize = 12.sp,
                color = TextGray,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(
                    text = "$",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = PrimaryPink.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = amount,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = TextGray.copy(alpha = 0.3f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Icon(
                    Icons.Default.UnfoldMore,
                    contentDescription = null,
                    tint = TextGray.copy(alpha = 0.3f)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Category Selection Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose Category",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextDark
                )
                Text(
                    text = "See All",
                    fontSize = 14.sp,
                    color = SecondaryPink,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { /* Action */ }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Menggunakan Komponen Terpisah berdasarkan state
            if (isExpense) {
                ExpenseCategorySection(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            } else {
                IncomeCategorySection(
                    selectedCategory = selectedCategory,
                    onCategorySelected = { selectedCategory = it }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Date Selection
            Text(
                text = "Transaction Date",
                fontSize = 14.sp,
                color = TextGray,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .clickable { /* Date Picker */ },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "27 / 10 / 2023", fontSize = 16.sp, color = TextDark)
                Spacer(modifier = Modifier.weight(1f))
                Icon(Icons.Default.CalendarToday, contentDescription = null, tint = TextGray, modifier = Modifier.size(18.dp))
            }
            HorizontalDivider(color = TextGray.copy(alpha = 0.1f))

            Spacer(modifier = Modifier.height(16.dp))

            // Notes Section
            Text(
                text = "Notes (Optional)",
                fontSize = 14.sp,
                color = TextGray,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.AutoMirrored.Filled.Notes, contentDescription = null, tint = PrimaryPink, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Write details here...", fontSize = 16.sp, color = TextGray.copy(alpha = 0.4f))
            }
            HorizontalDivider(color = TextGray.copy(alpha = 0.1f))

            Spacer(modifier = Modifier.height(32.dp))

            // Dynamic Info Card
            BudgetInfoCard(isExpense = isExpense)

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = { /* Save transaction */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryPink),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Icon(Icons.Default.Save, contentDescription = null, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Save Transaction", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
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
            Text(
                text = "Expense",
                color = if (isExpense) Color.White else TextGray,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
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
            Text(
                text = "Income",
                color = if (!isExpense) Color.White else TextGray,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        }
    }
}

data class CategoryItem(val name: String, val icon: ImageVector)

@Composable
fun CategoryGrid(
    categories: List<CategoryItem>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
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
                Text(
                    text = category.name,
                    fontSize = 11.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center
                )
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
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isExpense) Icons.Default.Star else Icons.AutoMirrored.Filled.TrendingUp, 
                    contentDescription = null, 
                    tint = contentColor, 
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = if (isExpense) "Stay on budget today!" else "Income analysis",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = contentColor
                )
                Text(
                    text = if (isExpense) "Your spending is 15% lower than usual." else "You've earned more than last month.",
                    fontSize = 12.sp,
                    color = contentColor.copy(alpha = 0.8f)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTransactionPreview() {
    StudentfinancetrackerTheme {
        AddTransactionScreen()
    }
}
