# DemoPOS Setup Instructions

## Prerequisites

### System Requirements
- macOS 12+, Windows 10+, or Linux (Ubuntu 20.04+)
- 8GB RAM minimum
- 10GB free disk space
- Internet connection

### Software Requirements
- **Android Studio**: 2023.1.1 or later
  - Download: https://developer.android.com/studio
- **Kotlin**: 1.9.10 (included with Android Studio)
- **JDK**: 17 or later
  - Download: https://www.oracle.com/java/technologies/javase-jdk17-downloads.html
- **Android SDK**: 34 (API 34)

### Verify Installation

```bash
# Check Java version
java -version
# Should output Java 17 or higher

# Check Kotlin
kotlin -version
# Should output Kotlin 1.9.10+
```

---

## Installation Steps

### 1. Clone Repository

```bash
git clone https://github.com/baconene/DemoPOS-AndroidApp.git
cd DemoPOS-AndroidApp
```

### 2. Open in Android Studio

1. Launch Android Studio
2. Select **File** → **Open**
3. Navigate to the cloned `DemoPOS-AndroidApp` folder
4. Click **Open**
5. Android Studio will:
   - Detect the Gradle build system
   - Download required SDKs
   - Sync the project
   - Index the code

Wait for Gradle sync to complete (check the status bar at bottom)

### 3. Install Android SDK Components

Android Studio should auto-install required components. If not:

1. **Tools** → **SDK Manager**
2. Under "SDK Platforms" tab:
   - Check **Android 14 (API 34)**
3. Under "SDK Tools" tab:
   - Ensure **Android SDK Build-Tools 34.x.x** is checked
   - Ensure **Android Emulator** is checked
4. Click **Apply** → **OK**

### 4. Create Android Virtual Device (Emulator)

**For Phone:**
1. **Tools** → **Device Manager**
2. Click **Create Device**
3. Select **Pixel 7** (or preferred phone)
4. Click **Next**
5. Select **Android 14 (API 34)** → **Next**
6. Name: `Pixel_7_API34` → **Finish**

**For Tablet (Recommended for POS):**
1. **Tools** → **Device Manager**
2. Click **Create Device**
3. Select **Pixel Tablet** (or **Tablet** category)
4. Click **Next**
5. Select **Android 14 (API 34)** → **Next**
6. Name: `Pixel_Tablet_API34` → **Finish**

### 5. Build the Project

```bash
# Via command line
./gradlew clean build

# Or via Android Studio
# Build → Make Project (Ctrl+F9 / Cmd+F9)
```

Wait for build to complete. You should see:
```
Build successful
```

### 6. Run on Emulator

**Option 1: Via Android Studio**
1. Click **Run** (or Shift+F10)
2. Select your virtual device
3. Click **OK**

**Option 2: Via Command Line**
```bash
./gradlew installDebug
```

The app will launch on the emulator.

### 7. Run on Physical Device (Optional)

**Prerequisites:**
- Android device with Android 7.0+
- USB cable
- Developer mode enabled

**Steps:**
1. Connect device via USB
2. Enable Developer Mode:
   - Go to **Settings** → **About Phone**
   - Tap **Build Number** 7 times
   - Go back → **Developer Options** → Enable **USB Debugging**
3. Click **Run** in Android Studio
4. Select your device
5. Click **OK**

---

## Build Variants

### Debug Build (Development)
```bash
./gradlew assembleDebug
```
- Features Timber logging
- ProGuard disabled
- Debuggable
- Output: `app/build/outputs/apk/debug/app-debug.apk`

### Release Build (Production)
```bash
./gradlew assembleRelease
```
- ProGuard enabled (code obfuscation)
- Optimized for size/performance
- Not debuggable
- Output: `app/build/outputs/apk/release/app-release.apk`

**Note:** Release builds require signing. See **Signing Configuration** below.

---

## Signing Configuration (Release)

### Generate Keystore

```bash
# macOS/Linux
keytool -genkey -v -keystore ~/demopos.keystore \
  -alias demopos_key \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10950

# Windows
keytool -genkey -v -keystore %USERPROFILE%\demopos.keystore -alias demopos_key -keyalg RSA -keysize 2048 -validity 10950
```

Answer the prompts (keep password safe!)

### Configure Gradle

Edit `app/build.gradle.kts` and add:

```kotlin
android {
    signingConfigs {
        create("release") {
            storeFile = file("/path/to/demopos.keystore")
            storePassword = "your_store_password"
            keyAlias = "demopos_key"
            keyPassword = "your_key_password"
        }
    }
    
    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
        }
    }
}
```

### Build Release APK

```bash
./gradlew assembleRelease
```

Output: `app/build/outputs/apk/release/app-release.apk`

---

## Project Configuration

### API Configuration

Edit `app/src/main/java/com/demopos/data/remote/ApiClient.kt`:

```kotlin
obj ect ApiClient {
    private const val BASE_URL = "https://your-api-domain.com/v1/"
    // Configure your API base URL
}
```

### Database Configuration

The database is automatically created at:
- Path: `/data/data/com.demopos/databases/demopos_database`

To inspect the database:
1. **View** → **Tool Windows** → **Device File Explorer**
2. Navigate: `data/data/com.demopos/databases/`
3. Right-click `demopos_database` → **Save As**

Open with [DB Browser for SQLite](https://sqlitebrowser.org/)

### Timber Logging

Logging is enabled in debug builds. View logs:

```bash
./gradlew test -i
```

Or in Android Studio:
1. **View** → **Tool Windows** → **Logcat**
2. Filter by `DemoPOS` or specific tag

---

## Troubleshooting

### Gradle Sync Issues

**Problem**: Gradle sync fails

**Solution**:
```bash
# Clear Gradle cache
./gradlew clean

# Retry sync
./gradlew sync  # or File → Sync Now in Android Studio
```

### Build Fails with Kotlin Error

**Problem**: Kotlin compilation error

**Solution**:
- Ensure JDK 17+ is selected in **File** → **Project Structure** → **SDK Location**
- Invalidate cache: **File** → **Invalidate Caches** → **Invalidate and Restart**

### Emulator Won't Start

**Problem**: Emulator crashes on launch

**Solution**:
1. Delete the device: **Tools** → **Device Manager** → Right-click → **Delete**
2. Create a new device with recommended settings
3. Ensure Virtualization is enabled in BIOS (Windows)

### App Crashes on Startup

**Problem**: App crashes immediately

**Solution**:
1. Check Logcat for error messages
2. Ensure Room database was created: `adb shell ls /data/data/com.demopos/databases/`
3. Clear app data: `adb shell pm clear com.demopos`
4. Rebuild and reinstall

---

## Development Workflow

### File Structure for Development

```
DemoPOS-AndroidApp/
├── .git/                 # Git repository
├── .gitignore
├── app/                  # Main app module
├── docs/                 # Documentation
├── .gradle/              # Gradle cache (auto-generated)
├── build/                # Build outputs (auto-generated)
├── build.gradle.kts      # Project build config
├── settings.gradle.kts   # Project settings
└── README.md
```

### Common Commands

```bash
# Build
./gradlew build
./gradlew assembleDebug
./gradlew assembleRelease

# Run tests
./gradlew test                    # Unit tests
./gradlew connectedAndroidTest    # UI tests

# Clean
./gradlew clean
./gradlew cleanBuildCache

# Code quality
./gradlew lint

# Install on device
./gradlew installDebug
adb install app/build/outputs/apk/debug/app-debug.apk

# View logs
adb logcat
adb logcat -s DemoPOS  # Filter by tag

# Database inspection
adb shell
cd /data/data/com.demopos/databases/
```

### IDE Shortcuts

| Action | Windows/Linux | macOS |
|--------|---------------|-------|
| Build Project | Ctrl+F9 | Cmd+F9 |
| Run App | Shift+F10 | Ctrl+R |
| Debug App | Shift+F9 | Ctrl+D |
| Rerun Last | Ctrl+F5 | Cmd+R |
| Logcat | Alt+6 | Cmd+6 |
| Gradle | Alt+1 | Cmd+1 |

---

## Next Steps

1. ✅ Project Setup Complete
2. 📖 Read [Architecture Documentation](./ARCHITECTURE.md)
3. 🔧 Review [Database Schema](./DATABASE.md)
4. 💻 Start implementing Phase 2 (Authentication)
5. 🧪 Write tests as you develop

---

## Support Resources

- **Android Docs**: https://developer.android.com/docs
- **Jetpack Compose**: https://developer.android.com/jetpack/compose
- **Kotlin Docs**: https://kotlinlang.org/docs
- **Room Database**: https://developer.android.com/training/data-storage/room
- **Retrofit**: https://square.github.io/retrofit/
- **Hilt**: https://dagger.dev/hilt/

---

**Version**: 1.0.0  
**Last Updated**: 2026-05-22
