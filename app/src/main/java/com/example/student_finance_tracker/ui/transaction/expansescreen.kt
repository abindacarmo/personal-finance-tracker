package com.example.student_finance_tracker.ui.transaction

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun ExpenseCategorySection(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val expenseCategories = remember {
        listOf(
            CategoryItem("Food", Icons.Default.Restaurant),
            CategoryItem("Transport", Icons.Default.DirectionsCar),
            CategoryItem("Rent", Icons.Default.Home),
            CategoryItem("Shopping", Icons.Default.ShoppingBag),
            CategoryItem("Entertainment", Icons.Default.Movie),
            CategoryItem("Health", Icons.Default.HealthAndSafety),
            CategoryItem("Bills", Icons.Default.ElectricBolt),
            CategoryItem("Others", Icons.Default.Add)
        )
    }

    CategoryGrid(
        categories = expenseCategories,
        selectedCategory = selectedCategory,
        onCategorySelected = onCategorySelected
    )
}
