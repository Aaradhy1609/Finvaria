# 🚀 Quick Run Guide - Finvaria App

## ✅ The Issue is FIXED!

**All manifest merger errors have been resolved!** Your app now builds successfully.

---

## 🎯 Fastest Way to Run

### Method 1: Use the Build Script (Easiest)

```powershell
.\build-and-run.ps1
```

Then select option 2 to build and install on your device!

### Method 2: Manual Commands

```powershell
# Set Java (run this once per terminal session)
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Build and install on connected device
.\gradlew installDebug
```

### Method 3: Android Studio

1. Open project in Android Studio
2. Press **Shift + F10** (or click green Run button)
3. Select your device

---

## 📱 Your APK is Ready!

**Location:**

```
app\build\outputs\apk\debug\app-debug.apk
```

**Size:** 117.2 MB

**Install manually:**

```powershell
adb install app\build\outputs\apk\debug\app-debug.apk
```

---

## 🔧 What Was Fixed?

1. ✅ Removed `app/libs/temp/` directory (was causing manifest conflicts)
2. ✅ Updated Gradle versions (AGP 8.9.1, Activity Compose 1.9.3)
3. ✅ Added manifest merger overrides
4. ✅ Excluded old support libraries
5. ✅ Fixed Kotlin compilation errors
6. ✅ Added packaging options

**Full details:** See `BUILD_FIX_SUMMARY.md`

---

## ⚡ Commands Cheat Sheet

```powershell
# Build APK only
.\gradlew assembleDebug

# Build and install on device
.\gradlew installDebug

# Clean and rebuild
.\gradlew clean assembleDebug

# Check connected devices
adb devices

# View logs
adb logcat | Select-String "Finvaria"
```

---

## 🎉 You're All Set!

The app is ready to run. Just connect your Android device or start an emulator and use one of the
methods above!

**Need help?** Check `BUILD_FIX_SUMMARY.md` for detailed troubleshooting.
