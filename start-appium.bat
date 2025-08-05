@echo off
echo Starting Appium Server...
echo Server will start on http://127.0.0.1:4723
echo.
echo Press Ctrl+C to stop the server
echo.

appium --address 127.0.0.1 --port 4723 --base-path /
