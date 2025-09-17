# Yedunath Demo - Android Portfolio App

A modern Android portfolio management application built with Clean Architecture principles, showcasing stock holdings with real-time P&L calculations and professional UI design.

## 📱 Features

- **Portfolio Overview**: Display total current value, investment, and P&L with percentage calculations
- **Holdings List**: Detailed view of individual stock holdings with quantity, LTP, and P&L
- **Real-time Calculations**: Dynamic P&L calculations based on current market prices
- **Expandable Summary**: Collapsible portfolio summary section with detailed breakdown
- **Color-coded P&L**: Green for profits, red for losses across the entire app
- **Professional UI**: Modern Material Design with clean, intuitive interface

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

```
app/
├── data/           # Data layer (API, repositories, mappers)
├── domain/         # Business logic layer (models, use cases)
├── presentation/   # UI layer (fragments, adapters, viewmodels)
└── utils/          # Utility classes and constants
```

### Architecture Layers:

- **Data Layer**: Handles API calls, data mapping, and repository implementations
- **Domain Layer**: Contains business logic, use cases, and domain models
- **Presentation Layer**: Manages UI components, ViewModels, and user interactions
- **Utils Layer**: Centralized constants, UI utilities, and helper functions

## 🛠️ Tech Stack

### Core Technologies
- **Language**: Kotlin
- **Architecture**: MVVM + Clean Architecture
- **UI**: Android Views with Material Design
- **Dependency Injection**: Dagger Hilt
- **Networking**: Retrofit + OkHttp
- **Async Programming**: Kotlin Coroutines + Flow
- **JSON Parsing**: Gson

### Testing
- **Unit Testing**: JUnit, MockK
- **Architecture Testing**: AndroidX Arch Core Testing
- **Coroutines Testing**: Kotlinx Coroutines Test
- **Flow Testing**: Turbine

### Build System
- **Build Tool**: Gradle with Kotlin DSL
- **Version Catalog**: Centralized dependency management with `libs.versions.toml`

## 📦 Dependencies

All dependencies are managed through Gradle Version Catalog (`gradle/libs.versions.toml`):

### Main Dependencies
- AndroidX Core KTX, AppCompat, Material Design
- RecyclerView, Lifecycle (ViewModel, LiveData)
- Navigation Component
- Retrofit, OkHttp Logging Interceptor
- Kotlin Coroutines
- Dagger Hilt
- Gson

### Testing Dependencies
- JUnit, MockK, Turbine
- AndroidX Testing (JUnit, Espresso)
- Architecture Components Testing

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 11 or higher
- Android SDK API 24+

### Setup Instructions

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Yedunath_Demo
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an existing project"
   - Navigate to the project directory

3. **Build the project**
   ```bash
   ./gradlew build
   ```

4. **Run the app**
   - Connect an Android device or start an emulator
   - Click "Run" in Android Studio or use:
   ```bash
   ./gradlew installDebug
   ```

## 📁 Project Structure

```
app/src/main/java/com/demo/yedunath_demo/
├── data/
│   ├── mapper/           # Data to domain model mappers
│   ├── model/            # API response models
│   ├── remote/           # API service interfaces
│   └── repository/       # Repository implementations
├── di/                   # Dependency injection modules
├── domain/
│   ├── model/            # Domain models
│   ├── repository/       # Repository interfaces
│   └── usecase/          # Business logic use cases
├── fragments/            # UI fragments
├── presentation/
│   ├── adapter/          # RecyclerView adapters
│   └── viewmodel/        # ViewModels
└── utils/
    ├── Constants.kt      # App-wide constants
    ├── Resource.kt       # Network state wrapper
    └── UIUtils.kt        # UI formatting utilities
```

## 🎨 UI Components

### Portfolio Fragment
- **Header Section**: Total P&L display with expandable details
- **Holdings List**: RecyclerView showing individual stock data
- **Tab Layout**: Navigation between different portfolio views

### Key UI Features
- Expandable portfolio summary
- Color-coded P&L indicators
- Professional Material Design theming
- Responsive layout design

## 💡 Key Implementation Details

### Clean Architecture Benefits
- **Testability**: Each layer can be tested independently
- **Maintainability**: Clear separation of concerns
- **Scalability**: Easy to add new features
- **Flexibility**: Can swap implementations without affecting other layers

### SOLID Principles Applied
- **Single Responsibility**: Each class has one reason to change
- **Open/Closed**: Open for extension, closed for modification
- **Dependency Inversion**: Depend on abstractions, not concretions

### Best Practices Implemented
- Centralized string and constant management
- Version catalog for dependency management
- Extension functions for reusable UI logic
- Proper error handling with Resource wrapper
- Coroutines for asynchronous operations

### Test Coverage
- **ViewModels**: Business logic and state management
- **Use Cases**: Domain layer business rules
- **Repositories**: Data layer operations
- **Mappers**: Data transformation logic

## 🔧 Configuration

### Build Variants
- **Debug**: Development build with logging enabled
- **Release**: Production build with optimizations


## 📱 Screenshots


