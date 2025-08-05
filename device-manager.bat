@echo off
echo ================================
echo Device and Emulator Manager
echo ================================
echo.

:menu
echo Choose an option:
echo 1. List connected devices
echo 2. List available emulators
echo 3. Start an emulator
echo 4. Check device info
echo 5. Install Calculator APK (if needed)
echo 6. Exit
echo.
set /p choice="Enter your choice (1-6): "

if "%choice%"=="1" goto list_devices
if "%choice%"=="2" goto list_emulators
if "%choice%"=="3" goto start_emulator
if "%choice%"=="4" goto device_info
if "%choice%"=="5" goto install_apk
if "%choice%"=="6" goto exit
goto menu

:list_devices
echo.
echo Connected devices:
adb devices -l
echo.
goto menu

:list_emulators
echo.
echo Available emulators:
emulator -list-avds
echo.
goto menu

:start_emulator
echo.
echo Available emulators:
emulator -list-avds
echo.
set /p avd_name="Enter AVD name to start: "
if not "%avd_name%"=="" (
    echo Starting emulator: %avd_name%
    start emulator -avd %avd_name%
    echo Emulator starting in background...
) else (
    echo No AVD name provided
)
echo.
goto menu

:device_info
echo.
echo Device information:
adb shell getprop ro.product.model
adb shell getprop ro.build.version.release
adb shell getprop ro.product.manufacturer
echo.
goto menu

:install_apk
echo.
echo Note: Calculator app is usually pre-installed on Android devices
echo If you have a specific Calculator APK, place it in the project root and update this script
echo.
REM adb install calculator.apk
goto menu

:exit
echo Goodbye!
exit /b 0
