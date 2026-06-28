<div align="center">

# 💰 Luminous Finance - Student Tracker

### Track every dollar. Own your finances. Built by a student, for students.

![Android](https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin%202.0-purple?style=for-the-badge&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose%20(M3)-blue?style=for-the-badge&logo=jetpackcompose)
![Status](https://img.shields.io/badge/Status-In%20Progress-orange?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-red?style=for-the-badge)

<br/>

> 📱 *A modern personal finance tracker with a clean aesthetic, built to help students manage dormitory life and beyond.*  
> *Now fully supporting USD currency and modular category management.*

<br/>

[Features](#🎯-why-i-built-this) • [Architecture](#🏗️-architecture) • [Progress](#📈-build-progress) • [Getting Started](#🚀-getting-started)

</div>

---

## 🎯 Why I Built This

As a student living in a dormitory, I realized I had **no idea where my money was going**.

- 💸 I didn't know how much I spent each day, week, or month.
- 📊 I couldn't see which category was draining my budget.
- 💵 I needed a way to track expenses in **USD currency** with a clean, modern UI.
- 😰 By mid-month, I'd already overspent without realizing it.

So I decided to **build the solution myself** — Luminous Finance.

---

## 🏗️ Architecture

This project follows **MVVM + Clean Architecture** principles to ensure the code is maintainable and scalable.

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.0.21 |
| UI | Jetpack Compose (Material 3) |
| Navigation | Compose Navigation |
| Icons | Material Icons Extended |
| Architecture | MVVM + Clean Architecture |
| Local Database | Room (Entity defined) |
| SDK | Android API 36 (Android 15) |

---

## 📁 Project Structure

```
app/src/main/java/com/example/student_finance_tracker/
├── 📁 ui/
│   ├── 📁 dashboard/
│   │   └── DashboardScreen.kt (USD Charts & Summary)
│   ├── 📁 transaction/
│   │   ├── transactionScreen.kt (Main Add Screen & Switch Logic)
│   │   ├── incomescreen.kt (Modular Income Categories)
│   │   └── expansescreen.kt (Modular Expense Categories)
│   └── 📁 theme/
├── 📁 data/
│   └── Transaction.kt (Database Model)
└── MainActivity.kt (App Navigation & Entry Point)
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug or later
- **Android SDK 36** (Required for the latest dependencies)
- Kotlin 2.0+

### Clone & Run
```bash
# Clone the repository
git clone https://github.com/abindacarmo/personal-finance-tracker.git

# Open in Android Studio
# Let Gradle sync, then hit Run ▶️
```

---

## 📄 License

```
MIT License — feel free to use this as a learning reference!
```

---

<div align="center">

**If this project helped or inspired you, please leave a ⭐**

</div>
