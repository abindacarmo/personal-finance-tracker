

<div align="center">

# 💰 Personal Finance Tracker

### Track every dollar. Own your finances. Built by a student, for students.

![Android](https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-purple?style=for-the-badge&logo=kotlin)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-blue?style=for-the-badge&logo=jetpackcompose)
![Status](https://img.shields.io/badge/Status-In%20Progress-orange?style=for-the-badge)
![License](https://img.shields.io/badge/License-MIT-red?style=for-the-badge)

<br/>

> 📱 *A personal finance tracker built from scratch as a student learning Android development.*  
> *Every commit is a step forward. Every feature ships real value.*

<br/>

[Features](#-features) • [Architecture](#-architecture) • [Progress](#-build-progress) • [Getting Started](#-getting-started) • [Roadmap](#-roadmap)

</div>

---

## 🎯 Why I Built This

As a student living in a dormitory, I realized I had **no idea where my money was going**.

- 💸 I didn't know how much I spent each day, week, or month
- 📊 I couldn't see which category was draining my budget
- 🔁 I kept forgetting to record small recurring expenses like internet bills
- 😰 By mid-month, I'd already overspent without realizing it

So I decided to **build the solution myself** — and learn Android development along the way.

---

## 🏗️ Architecture

This project follows **MVVM + Clean Architecture** principles.

```
┌─────────────────────────────────────┐
│           UI Layer (View)           │
│        Jetpack Compose Screens      │
└─────────────┬───────────────────────┘
              │ observes StateFlow
              ▼
┌─────────────────────────────────────┐
│          ViewModel Layer            │
│     State • Logic • UI Events       │
└─────────────┬───────────────────────┘
              │ calls
              ▼
┌─────────────────────────────────────┐
│         Repository Layer            │
│      Single Source of Truth         │
└──────────┬──────────────────────────┘
           │
    ┌──────┴──────┐
    ▼             ▼
┌────────┐   ┌──────────────┐
│  Room  │   │ Google Drive │
│   DB   │   │   (Backup)   │
└────────┘   └──────────────┘
```

### Database Schema (5 Tables)

```
transactions ──────┐
categories   ───── ├──── connected via category_id (FK)
budgets      ──────┤
recurring    ──────┘
users
```

### Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture |
| Local Database | Room |
| Async | Coroutines + StateFlow |
| Dependency Injection | Hilt |
| Charts | Vico |
| Backup | Google Drive API |
| Notifications | WorkManager |
| Export | PDF / CSV |

---

## 📁 Project Structure

```
app/src/main/java/com/example/student_finance_tracker/
├── 📁 ui/
│   ├── 📁 screens/
│   │   ├── DashboardScreen.kt
│   │   ├── AddTransactionScreen.kt
│   │   ├── ReportScreen.kt
│   │   ├── BudgetScreen.kt
│   │   └── CategoryScreen.kt
│   └── 📁 components/
├── 📁 viewmodel/
├── 📁 repository/
└── 📁 data/
    ├── 📁 local/
    │   ├── 📁 entity/
    │   └── 📁 dao/
    └── 📁 remote/
```

---

## 📈 Build Progress

This project is being built **daily** as part of my Android learning journey.

| Week | Goal | Status |
|---|---|---|
| Week 1 | Project setup, database schema, Room entities & DAOs | 🔨 In Progress |
| Week 2 | Repository + ViewModel + Dashboard screen | 📋 Planned |
| Week 3 | Add transaction screen + category management | 📋 Planned |
| Week 4 | Weekly report + bar chart | 📋 Planned |
| Week 5 | Budget alerts + push notifications | 📋 Planned |
| Week 6 | Export PDF/CSV + Google Drive backup | 📋 Planned |
| Week 7 | Password protection + UI polish | 📋 Planned |
| Week 8 | Testing + final release v1.0 | 📋 Planned |

> 💡 *I update this table every week. Follow the repo to track my progress!*

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 36
- Kotlin 1.9+

### Clone & Run
```bash
# Clone the repository
git clone https://github.com/abindacarmo/personal-finance-tracker.git

# Open in Android Studio
# File → Open → select the project folder

# Let Gradle sync, then hit Run ▶️
```

### Minimum Requirements
- Android 8.0 (API 26) or higher
- ~50MB storage

---

## 🗺️ Roadmap

```
[v1.0 - MVP]          [v2.0 - Smart Budget]     [v3.0 - Cloud]
─────────────         ──────────────────────     ──────────────
✦ Record income  →    ✦ Budget per category  →   ✦ Google Drive
✦ Record expense      ✦ Push notifications       ✦ PDF/CSV export
✦ Categories          ✦ Auto-recurring           ✦ Multi-device
✦ Dashboard           ✦ Month comparison         ✦ Widgets
✦ Weekly chart        ✦ Password lock
```

---

## 📚 What I'm Learning

This project is my hands-on journey into Android development. Along the way I'm learning:

- ✅ Kotlin fundamentals
- ✅ Jetpack Compose UI
- ✅ Room database & DAO patterns
- ✅ MVVM architecture
- 🔨 Coroutines & StateFlow
- 📋 Hilt dependency injection
- 📋 WorkManager for background tasks
- 📋 Google Drive API integration

---

## 🤝 Contributing

This is a personal learning project, but feedback and suggestions are very welcome!

- ⭐ **Star** this repo if you find it useful or want to follow my progress
- 🐛 **Open an issue** if you spot a bug or have a feature idea
- 💬 **Comment** on commits if you have tips — I'm still learning!

---

## 👤 Developer

**Brigida de Carvalho Carmo**  
Student | Universitas | NIM: 20230204058  
📍 Dili, Timor-Leste

*"I didn't find the app I needed, so I'm building it."*

---

## 📄 License

```
MIT License — feel free to use this as a learning reference!
```

---

<div align="center">

**If this project helped or inspired you, please leave a ⭐**

*Built with 💙 and a lot of trial & error*

![Visitor Count](https://visitor-badge.laobi.icu/badge?page_id=abindacarmo.personal-finance-tracker)

</div>
