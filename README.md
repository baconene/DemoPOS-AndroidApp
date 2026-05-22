# DemoPOS - Android Point of Sale Application

## 🎯 Overview

DemoPOS is a **modern, production-ready Android POS (Point of Sale) application** built with **Kotlin + Jetpack Compose** following clean architecture principles. It's designed for retailers, restaurants, and service providers to manage sales, inventory, and operations efficiently on tablets and phones.

### Key Features
✅ **Offline-First Architecture** - Fully functional without internet, auto-syncs when reconnected  
✅ **Multi-Role Access Control** - Admin, Cashier, Manager with role-based features  
✅ **Multiple Payment Methods** - Cash, GCash, Maya, Credit/Debit cards with split payment  
✅ **Thermal Printer Integration** - Bluetooth thermal printer support  
✅ **Comprehensive Inventory** - Stock tracking, low-stock alerts, auto-deduction  
✅ **Advanced Analytics** - Daily/weekly/monthly reports with PDF/Excel export  
✅ **Responsive Design** - Optimized for tablets (landscape) and phones  
✅ **Dark Mode Support** - Material 3 light/dark theme  
✅ **Professional UI** - Inspired by Shopify POS, Square, Toast POS  

---

## 📦 Tech Stack

### Core Technologies
- **Language**: Kotlin 1.9.10
- **UI Framework**: Jetpack Compose + Material 3
- **Architecture**: Clean Architecture + MVVM
- **Database**: Room SQLite (offline-first)
- **Networking**: Retrofit + OkHttp
- **Dependency Injection**: Hilt/Dagger
- **Async**: Coroutines + StateFlow
- **Testing**: JUnit, Mockito, Compose Testing

### Android Versions
- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)
- **Compile SDK**: 34

---

## 🏗️ Project Structure

```
DemoPOS/
├── app/
│   ├── src/main/
│   │   ├── java/com/demopos/
│   │   │   ├── common/              # Constants and utilities
│   │   │   ├── di/                  # Hilt dependency injection
│   │   │   │   ├── DatabaseModule
│   │   │   │   ├── NetworkModule
│   │   │   │   └── RepositoryModule
│   │   │   ├── data/                # Data layer
│   │   │   │   ├── local/           # Room database
│   │   │   │   │   ├── database/    # Database setup
│   │   │   │   │   ├── dao/         # Data access objects
│   │   │   │   │   ├── entities/    # Room entities
│   │   │   │   │   └── converters/  # Type converters
│   │   │   │   ├── remote/          # API integration
│   │   │   │   ├── models/          # DTOs
│   │   │   │   └── repositories/    # Repository implementations
│   │   │   ├── domain/              # Domain layer (business logic)
│   │   │   │   ├── entities/        # Domain entities
│   │   │   │   ├── repositories/    # Repository interfaces
│   │   │   │   └── usecases/        # Business logic (Phase 2+)
│   │   │   ├── presentation/        # Presentation layer (UI)
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/     # Composable screens
│   │   │   │   │   ├── components/  # Reusable components
│   │   │   │   │   └── theme/       # Material 3 theme
│   │   │   │   ├── viewmodels/      # ViewModels
│   │   │   │   ├── navigation/      # Navigation setup
│   │   │   │   └── state/           # UI state classes
│   │   │   ├── utils/               # Helper utilities
│   │   │   ├── DemoPOSApplication   # App initialization
│   │   │   └── MainActivity
│   │   ├── res/                     # Resources
│   │   │   ├── values/
│   │   │   ├── values-night/        # Dark mode
│   │   │   ├── drawable/
│   │   │   └── xml/
│   │   └── AndroidManifest.xml
│   ├── src/test/                    # Unit tests
│   ├── src/androidTest/             # UI/integration tests
│   ├── build.gradle.kts             # Module build config
│   └── proguard-rules.pro
├── build.gradle.kts                 # Project build config
├── settings.gradle.kts              # Project settings
└── README.md
```

---

## 🏛️ Architecture Overview

### Clean Architecture Layers

```
┌─────────────────────────────────────────┐
│   PRESENTATION (UI Layer)               │
│   - Screens (Jetpack Compose)           │
│   - ViewModels                          │
│   - Navigation                          │
└─────────────────────────────────────────┘
           ↓ (uses)
┌─────────────────────────────────────────┐
│   DOMAIN (Business Logic)               │
│   - Entities                            │
│   - Repository Interfaces               │
│   - Use Cases                           │
└─────────────────────────────────────────┘
           ↓ (implements)
┌─────────────────────────────────────────┐
│   DATA (Data Access)                    │
│   - Room Database (Local)               │
│   - Retrofit API (Remote)               │
│   - Repository Implementations          │
│   - DTOs & Models                       │
└─────────────────────────────────────────┘
```

### Key Design Patterns

1. **Repository Pattern** - Abstracts data sources
2. **MVVM** - ViewModel manages UI state
3. **StateFlow/Flow** - Reactive data streams
4. **Dependency Injection (Hilt)** - Loosely coupled components
5. **Event-Driven Architecture** - Sealed class events
6. **Offline Queue Pattern** - Sync pending transactions

---

## 💾 Database Schema

### Core Tables

| Table | Purpose |
|-------|----------|
| `users` | Staff members with roles (Admin, Cashier, Manager) |
| `auth_sessions` | User authentication sessions |
| `categories` | Product categories |
| `products` | Product catalog with pricing |
| `inventory` | Stock levels and tracking |
| `orders` | Customer orders |
| `order_items` | Line items in orders |
| `payments` | Payment records (Cash, Card, Digital) |
| `customers` | Customer profiles & loyalty |
| `receipts` | Receipt records |
| `sync_queue` | Offline queue for sync |
| `activity_logs` | Audit trail |

### Relationships
```
Users ──┬─→ AuthSessions
        └─→ Orders
        └─→ ActivityLogs

Categories ──→ Products ──┬─→ Inventory
                           └─→ OrderItems

Orders ──┬─→ OrderItems
         ├─→ Payments
         ├─→ Customers
         └─→ Receipts

SyncQueue (tracks offline changes)
ActivityLogs (audit trail)
```

---

## 🔐 Security Features

- **Secure Local Storage** - Encrypted shared preferences (DataStore)
- **Session Management** - Token-based auth with expiry
- **Role-Based Access Control** - Feature gating by user role
- **Activity Logging** - Complete audit trail of sensitive operations
- **ProGuard Obfuscation** - Code protection in release builds
- **HTTPS Only** - TLS certificate pinning ready

---

## 📱 UI/UX Design

### Material 3 Design System
- Modern color scheme with dynamic colors support
- Responsive layouts for phones and tablets
- Touch-friendly buttons and spacing
- Smooth animations and transitions
- Accessibility-first approach

### Key Screens
1. **Splash/Login** - Authentication
2. **Dashboard** - Sales overview
3. **POS Checkout** - Fast cashier workflow
4. **Inventory** - Stock management
5. **Reports** - Analytics and insights
6. **Settings** - Configuration

---

## 🚀 Getting Started

### Prerequisites
- Android Studio 2023.1+
- Kotlin 1.9.10
- JDK 17
- Android SDK 34

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/baconene/DemoPOS-AndroidApp.git
   cd DemoPOS-AndroidApp
   ```

2. **Open in Android Studio**
   - File → Open → Select project directory
   - Wait for Gradle sync

3. **Build & Run**
   ```bash
   # Build debug APK
   ./gradlew assembleDebug
   
   # Run on emulator/device
   ./gradlew installDebug
   ```

4. **Release Build**
   ```bash
   ./gradlew assembleRelease
   ```

---

## 📋 Implementation Phases

### Phase 1: ✅ Completed
- Project setup & Gradle configuration
- Hilt DI setup
- Room database & entities
- Retrofit API client
- Base ViewModel
- Navigation foundation

### Phase 2: 🔄 In Progress
- Authentication screens (Login, PIN)
- Session management
- Remember user feature

### Phases 3-16
See `/docs/PHASES.md` for detailed phase breakdown

---

## 🧪 Testing

### Unit Tests
```bash
./gradlew test
```

### UI/Integration Tests
```bash
./gradlew connectedAndroidTest
```

### Test Coverage
Aim for >80% coverage on critical paths

---

## 📚 Documentation

- **Architecture** - See `/docs/ARCHITECTURE.md`
- **API Integration** - See `/docs/API_INTEGRATION.md`
- **Database** - See `/docs/DATABASE.md`
- **UI Components** - See `/docs/COMPONENTS.md`

---

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/name`
2. Commit changes: `git commit -am 'Add feature'`
3. Push to branch: `git push origin feature/name`
4. Create Pull Request

### Code Style
- Follow Kotlin conventions
- Max line length: 100 characters
- Use meaningful variable names
- Document complex logic

---

## 📄 License

MIT License - See LICENSE file

---

## 🙏 Credits

Built with ❤️ using Kotlin and Jetpack Compose

Inspired by industry leaders:
- Shopify POS
- Square POS
- Toast POS

---

## 📞 Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Check existing documentation
- Review example code in the repository

---

**Version**: 1.0.0  
**Last Updated**: 2026-05-22  
**Status**: Active Development
