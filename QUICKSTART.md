# Quick Start Guide for DemoPOS

## One-Command Setup

```bash
cd DemoPOS-AndroidApp && ./gradlew clean build && ./gradlew installDebug
```

## Project Structure at a Glance

```
app/src/main/java/com/demopos/
├── common/        # Constants (AppConstants.kt)
├── di/            # Hilt injection (DatabaseModule, NetworkModule, RepositoryModule)
├── data/
│   ├── local/     # Room database (entities, DAOs, database)
│   ├── remote/    # Retrofit API client
│   ├── models/    # DTOs
│   └── repositories/  # Repository implementations
├── domain/
│   ├── entities/  # Domain models
│   ├── repositories/  # Repository interfaces
│   └── usecases/  # Business logic (coming Phase 2+)
├── presentation/
│   ├── ui/
│   │   ├── screens/    # Composable screens
│   │   ├── components/ # Reusable UI components
│   │   └── theme/      # Material 3 theme
│   ├── viewmodels/     # ViewModels
│   ├── navigation/     # Navigation setup
│   └── state/          # UI state classes
├── utils/         # Helpers (IdGenerator, DateUtils, CurrencyUtils)
├── MainActivity.kt
└── DemoPOSApplication.kt
```

## Current Phase Status

### ✅ Phase 1: Complete
- ✓ Gradle & dependencies
- ✓ Hilt DI setup  
- ✓ Room database (12 entities + DAOs)
- ✓ Retrofit API client
- ✓ Base ViewModel
- ✓ Navigation foundation
- ✓ Material 3 theme

### ⏳ Phase 2: Next (Authentication)
- [ ] Login screen
- [ ] PIN login
- [ ] Session management
- [ ] Remember user

## Key Concepts Used

### 1. Clean Architecture
```
Presentation → Domain → Data
   (UI)       (Logic)   (Storage/API)
```

### 2. Repository Pattern
```
ViewModel → Repository Interface → Repository Impl
                                      ↓ (hides)
                              Room DB + Retrofit API
```

### 3. StateFlow (Reactive)
```
UI observes ← StateFlow ← ViewModel ← Repository ← Database
(LiveView)    (Mutable)   (Updates)    (Queries)   (Source)
```

### 4. Dependency Injection (Hilt)
```
Activity/ViewModel @Inject Repository
              ↓
        Hilt Container
              ↓
        Returns singleton
```

## Database Tables (12 Total)

| Table | Purpose | Status |
|-------|---------|--------|
| users | Staff members | ✓ Ready |
| auth_sessions | Login tokens | ✓ Ready |
| categories | Product categories | ✓ Ready |
| products | Product catalog | ✓ Ready |
| inventory | Stock levels | ✓ Ready |
| orders | Customer orders | ✓ Ready |
| order_items | Order line items | ✓ Ready |
| payments | Payment records | ✓ Ready |
| customers | Customer profiles | ✓ Ready |
| receipts | Receipt records | ✓ Ready |
| sync_queue | Offline sync queue | ✓ Ready |
| activity_logs | Audit trail | ✓ Ready |

## API Endpoints (To be Implemented)

### Authentication
- `POST /auth/login` - Email/password login
- `POST /auth/refresh` - Token refresh
- `POST /auth/logout` - Sign out

### Products
- `GET /products` - List products
- `GET /products/:id` - Product details
- `GET /categories` - List categories

### Orders
- `POST /orders` - Create order
- `GET /orders/:id` - Get order
- `GET /orders` - List orders

### Reports
- `GET /reports/sales` - Daily sales
- `GET /reports/profit` - Profit analytics

## Common Development Tasks

### Add a New Screen

1. **Create ViewModel** (`presentation/viewmodels/NewViewModel.kt`)
   ```kotlin
   class NewViewModel : BaseViewModel<UiState, UiEvent>() {
       // Your logic here
   }
   ```

2. **Create Screen** (`presentation/ui/screens/NewScreen.kt`)
   ```kotlin
   @Composable
   fun NewScreen(viewModel: NewViewModel) {
       // Your UI here
   }
   ```

3. **Add Route** (`presentation/navigation/AppNavGraph.kt`)
   ```kotlin
   composable("new_screen") {
       NewScreen(viewModel())
   }
   ```

### Add a New Repository

1. **Create Interface** (`domain/repositories/NewRepository.kt`)
   ```kotlin
   interface NewRepository {
       suspend fun getData(): Result<Data>
   }
   ```

2. **Implement** (`data/repositories/NewRepositoryImpl.kt`)
   ```kotlin
   class NewRepositoryImpl @Inject constructor(
       private val dao: NewDao
   ) : NewRepository { }
   ```

3. **Inject** (`di/RepositoryModule.kt`)
   ```kotlin
   @Binds
   abstract fun bindNewRepository(impl: NewRepositoryImpl): NewRepository
   ```

### Query Database

```kotlin
// In DAO
@Query("SELECT * FROM products WHERE id = :productId")
suspend fun getProduct(productId: String): ProductEntity?

// In Repository
suspend fun getProduct(id: String): Product? {
    return productDao.getProduct(id)?.toDomain()
}

// In ViewModel
val product = repository.getProduct(id)
```

## Debug Tips

### View Database
```bash
# Open database with Android Studio
# View → Tool Windows → Device File Explorer
# Navigate: data/data/com.demopos/databases/demopos_database
```

### Check Logs
```bash
# Filter by app tag
adb logcat -s "DemoPOS"

# Or in Android Studio: View → Logcat
```

### Test with Mock Data
```kotlin
// Existing mock in AuthRepositoryImpl.kt
// Mock API responses without real backend
```

## File Sizes

- **APK Size**: ~15 MB (release)
- **Database**: ~1-10 MB (depending on data)
- **Total Install**: ~50-100 MB

## Performance Targets

- App startup: < 2 seconds
- Screen navigation: < 300ms
- Product list scroll: 60 FPS
- POS checkout: Instant response

## Security Checklist

- ✓ Encrypted shared preferences via DataStore
- ✓ HTTPS-ready API client
- ✓ Session timeout support
- ✓ ProGuard obfuscation (release)
- ✓ Audit logging ready
- [ ] Implement TLS pinning (Phase 13)
- [ ] Add encryption at rest (Phase 12)

## Next Steps

1. **For Development**:
   - Start with Phase 2 (Authentication screens)
   - Use existing mock data in AuthRepositoryImpl
   - Create LoginScreen composable

2. **For Integration**:
   - Update `ApiClient.kt` BASE_URL
   - Implement actual API endpoints
   - Replace mock data with real calls

3. **For Testing**:
   - Run `./gradlew test` for unit tests
   - Run `./gradlew connectedAndroidTest` for UI tests
   - Achieve 80%+ code coverage

## Resources

- 📖 [Full Setup Guide](./docs/SETUP.md)
- 📐 [Architecture Details](./docs/ARCHITECTURE.md)
- 🗄️ [Database Schema](./docs/DATABASE.md)
- 📋 [All 16 Phases](./docs/PHASES.md)
- 🔗 [Kotlin Docs](https://kotlinlang.org)
- 🎨 [Jetpack Compose](https://developer.android.com/jetpack/compose)

---

**Version**: 1.0.0  
**Status**: Phase 1 Complete ✓  
**Next**: Phase 2 - Authentication Ready
