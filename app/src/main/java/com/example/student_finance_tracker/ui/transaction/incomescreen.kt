package com.example.student_finance_tracker.ui.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun IncomeCategorySection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val incomeCategories = remember {
        listOf(
            CategoryItem("Salary", Icons.Default.Payments),
            CategoryItem("Savings", Icons.Default.Savings),
            CategoryItem("Gift", Icons.Default.Redeem),
            CategoryItem("Investment", Icons.AutoMirrored.Filled.TrendingUp),
            CategoryItem("Freelance", Icons.Default.Badge),
            CategoryItem("Others", Icons.Default.Add)
        )
    }

    CategoryGrid(
        categories = incomeCategories,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected
    )
}
