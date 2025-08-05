@echo off
echo ================================
echo Device and Emulator Manager
echo ================================
echo.

REM Set Android SDK paths
set ANDROID_HOME=%LOCALAPPDATA%\Android\Sdk
set EMULATOR_PATH=%ANDROID_HOME%\emulator
set PLATFORM_TOOLS=%ANDROID_HOME%\platform-tools

REM Check if Android SDK exists
if not exist "%ANDROID_HOME%" (
    echo ERROR: Android SDK not found at %ANDROID_HOME%
    echo Please install Android Studio first
    pause
    exit /b 1
)

:menu
echo Choose an option:
echo 1. List connected devices
echo 2. List available emulators
echo 3. Start an emulator
echo 4. Check device info
echo 5. Check installed apps
echo 6. Exit
echo.
set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" goto list_devices
if "%choice%"=="2" goto list_emulators
if "%choice%"=="3" goto start_emulator
if "%choice%"=="4" goto device_info
if "%choice%"=="5" goto check_apps
if "%choice%"=="6" goto exit
goto menu

:list_devices
echo.
echo Connected devices:
"%PLATFORM_TOOLS%\adb" devices -l
echo.
goto menu

:list_emulators
echo.
echo Available emulators:
"%EMULATOR_PATH%\emulator" -list-avds
echo.
goto menu

:start_emulator
echo.
echo Available emulators:
"%EMULATOR_PATH%\emulator" -list-avds
echo.
set /p avd_name="Enter AVD name to start (or press Enter for default): "
if "%avd_name%"=="" (
    echo No emulator name provided. Trying to start first available emulator...
    for /f %%i in ('"%EMULATOR_PATH%\emulator" -list-avds') do (
        set avd_name=%%i
        goto start_emu
    )
)
:start_emu
if not "%avd_name%"=="" (
    echo Starting emulator: %avd_name%
    echo This may take 2-3 minutes...
    echo.
    echo Starting with additional memory and graphics settings...
    start "" "%EMULATOR_PATH%\emulator" -avd %avd_name% -memory 2048 -gpu host -no-snapshot-load -wipe-data
    echo.
    echo Emulator starting in background...
    echo Please wait for the Android home screen to appear...
    echo This can take 3-5 minutes on first boot.
    echo.
    echo Press any key to return to menu (emulator will continue running)
    pause >nul
) else (
    echo No emulators found! Please create one in Android Studio first.
    echo.
    echo To create an emulator:
    echo 1. Open Android Studio
    echo 2. Tools → AVD Manager  
    echo 3. Create Virtual Device
    echo 4. Choose Pixel 3a or similar
    echo 5. Choose API 30 or higher
)
echo.
goto menu

:device_info
echo.
echo Device information:
"%PLATFORM_TOOLS%\adb" shell getprop ro.product.model
"%PLATFORM_TOOLS%\adb" shell getprop ro.build.version.release
"%PLATFORM_TOOLS%\adb" shell getprop ro.product.manufacturer
echo.
goto menu

:check_apps
echo.
echo Checking for Google Contacts app:
"%PLATFORM_TOOLS%\adb" shell pm list packages | findstr contacts
echo.
echo Checking for system contacts app:
"%PLATFORM_TOOLS%\adb" shell pm list packages | findstr com.android.contacts
echo.
goto menu

:exit
echo Goodbye!
exit /b 0
