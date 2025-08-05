@echo off
echo ================================
echo Mobile Test Automation Setup
echo ================================
echo.

echo Checking Java installation...
java -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Java not found! Please install Java JDK 11+
    pause
    exit /b 1
)
echo ✓ Java found
echo.

echo Checking Maven installation...
mvn -version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Maven not found! Please install Apache Maven
    pause
    exit /b 1
)
echo ✓ Maven found
echo.

echo Checking Android SDK...
adb version
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Android SDK not found! Please install Android Studio and set ANDROID_HOME
    pause
    exit /b 1
)
echo ✓ Android SDK found
echo.

echo Checking Node.js...
node -v
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Node.js not found! Please install Node.js 14+
    pause
    exit /b 1
)
echo ✓ Node.js found
echo.

echo Checking Appium...
appium -v
if %ERRORLEVEL% NEQ 0 (
    echo ERROR: Appium not found! Please install: npm install -g appium
    pause
    exit /b 1
)
echo ✓ Appium found
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
