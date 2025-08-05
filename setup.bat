@echo off
echo ================================
echo Mobile Test Automation Setup
echo ================================
echo.

echo Checking Java installation...
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java not found! Please install Java JDK 11+
    pause
    exit /b 1
)
echo ✓ Java found
echo.

echo Checking Maven installation...
mvn --version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven not found! Please install Apache Maven
    pause
    exit /b 1
)
echo ✓ Maven found
echo.

echo Checking Android SDK...
adb version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Android SDK not found! Please install Android Studio and set ANDROID_HOME
    pause
    exit /b 1
)
echo ✓ Android SDK found
echo.

echo Checking Node.js...
node -v >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Node.js not found! Please install Node.js 14+
    pause
    exit /b 1
)
echo ✓ Node.js found
echo.

echo Checking Appium...
appium -v >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo WARNING: Appium not found! Installing Appium globally...
    npm install -g appium
    if %ERRORLEVEL% NEQ 0 (
        echo ERROR: Failed to install Appium! Please run: npm install -g appium
        pause
        exit /b 1
    )
    echo ✓ Appium installed successfully
) else (
    echo ✓ Appium found
)
echo.

echo Running Appium Doctor for Android...
appium-doctor --android
echo.

echo Downloading Maven dependencies...
mvn clean install -DskipTests
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven build failed!
    pause
    exit /b 1
)
echo ✓ Dependencies downloaded successfully
echo.

echo ================================
echo Setup completed successfully!
echo ================================
echo.
echo Next steps:
echo 1. Connect your Android device or start an emulator
echo 2. Run start-appium.bat to start Appium server
echo 3. Run run-tests.bat to execute tests
echo.
pause
