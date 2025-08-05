@echo off
echo ================================
echo Running Create Contact Test Only
echo ================================
echo.

echo Checking connected devices...
adb devices
echo.

echo Starting Create Contact test execution...
echo.

REM Run only the CreateContactTest
mvn test -Dtest=CreateContactTest

REM Check if tests completed successfully
if %ERRORLEVEL% EQU 0 (
    echo.
    echo ✓ Create Contact test completed successfully!
    echo Check the reports folder for detailed results.
) else (
    echo.
    echo ✗ Create Contact test failed!
    echo Check the logs for error details.
)

echo.
echo Opening test reports...
start "" "reports"

pause
