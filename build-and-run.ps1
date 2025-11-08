# Finvaria App - Build and Run Script
# This script sets up Java and builds/installs the app

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Finvaria App - Build & Run Script" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Set JAVA_HOME
Write-Host "Setting up Java environment..." -ForegroundColor Yellow
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Verify Java
Write-Host "Verifying Java installation..." -ForegroundColor Yellow
java -version
Write-Host ""

# Ask user what to do
Write-Host "What would you like to do?" -ForegroundColor Green
Write-Host "1. Build APK (assembleDebug)" -ForegroundColor White
Write-Host "2. Build and Install on device (installDebug)" -ForegroundColor White
Write-Host "3. Clean and rebuild" -ForegroundColor White
Write-Host "4. Just clean" -ForegroundColor White
Write-Host ""

$choice = Read-Host "Enter your choice (1-4)"

switch ($choice) {
    "1" {
        Write-Host "`nBuilding APK..." -ForegroundColor Green
        .\gradlew assembleDebug --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "`n✅ BUILD SUCCESSFUL!" -ForegroundColor Green
            Write-Host "APK Location: app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Cyan
        }
    }
    "2" {
        Write-Host "`nBuilding and installing..." -ForegroundColor Green
        .\gradlew installDebug --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "`n✅ BUILD AND INSTALL SUCCESSFUL!" -ForegroundColor Green
            Write-Host "App should now be on your device!" -ForegroundColor Cyan
        }
    }
    "3" {
        Write-Host "`nCleaning and rebuilding..." -ForegroundColor Green
        .\gradlew clean assembleDebug --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "`n✅ CLEAN BUILD SUCCESSFUL!" -ForegroundColor Green
            Write-Host "APK Location: app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Cyan
        }
    }
    "4" {
        Write-Host "`nCleaning..." -ForegroundColor Green
        .\gradlew clean --no-daemon
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "`n✅ CLEAN SUCCESSFUL!" -ForegroundColor Green
        }
    }
    default {
        Write-Host "`n❌ Invalid choice. Exiting." -ForegroundColor Red
        exit 1
    }
}

Write-Host "`nPress any key to exit..."
$null = $Host.UI.RawUI.ReadKey("NoEcho,IncludeKeyDown")
