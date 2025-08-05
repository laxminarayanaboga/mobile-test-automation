@echo off
echo ================================
echo Running Mobile Test Automation
echo ================================
echo.

echo Checking connected devices...
adb devices
echo.

echo Starting test execution...
echo.

REM Run all tests
mvn test

REM Check if tests completed successfully
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Tests completed successfully!
    echo.
    echo Reports generated:
    echo - ExtentReports: reports\
    echo - TestNG Reports: test-output\
    echo - Logs: test-output\logs\
) else (
    echo.
    echo ✗ Tests failed! Check logs for details.
)

echo.
echo Opening test reports...
if exist "reports\*.html" (
    for %%f in (reports\*.html) do (
        start "" "%%f"
        goto :found
    )
)
:found

echo.
pause
