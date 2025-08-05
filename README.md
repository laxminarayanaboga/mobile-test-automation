# Mobile Test Automation Framework

## Overview
This is a comprehensive mobile test automation framework built with Java, Maven, TestNG, and Appium for Android app testing. The framework includes working tests for the **Google Contacts app** and provides a solid foundation for automating any Android application.

## Tech Stack
- **Java 21**: Core programming language
- **Maven 3.9+**: Build automation and dependency management  
- **TestNG**: Testing framework
- **Appium 2.19+**: Mobile automation tool
- **Selenium WebDriver**: Web driver for mobile apps
- **ExtentReports**: Test reporting with screenshots
- **Logback**: Logging framework

## Project Structure
```
mobile-test-automation/
├── src/
│   ├── main/java/com/automation/
│   │   ├── base/           # Base classes (DriverManager, BaseTest, BasePage)
│   │   ├── pages/          # Page Object Model classes (ContactsPage)
│   │   └── utils/          # Utility classes (ConfigReader, ExtentReportManager, etc.)
│   └── test/
│       ├── java/com/automation/tests/  # Test classes
│       └── resources/
│           ├── config/     # Configuration files
│           ├── testng.xml  # TestNG suite configuration
│           └── logback.xml # Logging configuration
├── reports/                # HTML test reports with screenshots
├── test-output/           # Test outputs, logs, and screenshots
├── downloads/             # APK downloads directory
├── *.bat                 # Batch scripts for easy execution
├── pom.xml               # Maven configuration
└── README.md            # This file
```

## Quick Start Guide

### Prerequisites
1. **Java 21**: Download from [Eclipse Adoptium](https://adoptium.net/)
2. **Maven 3.9+**: Download from [Apache Maven](https://maven.apache.org/download.cgi)
3. **Node.js 22+**: Download from [Node.js](https://nodejs.org/) (required for Appium)
4. **Android Studio**: Download from [Android Developers](https://developer.android.com/studio) (for emulator)

### 🚀 Easy Setup with Batch Scripts

#### 1. Run Setup (First Time Only)
```bash
setup.bat
```
This will:
- Verify Java, Maven, Node.js installation
- Install Appium and UiAutomator2 driver
- Set up Android SDK path
- Create Android emulator

#### 2. Start Services
```bash
# Terminal 1: Start Appium Server
start-appium.bat

# Terminal 2: Start Android Emulator (if needed)
device-manager.bat
# Select option 1 to start emulator
```

#### 3. Run Tests
```bash
# Run all tests
run-tests.bat

# Or run specific tests with Maven
mvn test -Dtest=ContactsExplorationTest
mvn test -Dtest=DeviceConnectivityTest
```

### Manual Setup (Alternative)

#### Environment Variables
```bash
JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.x.x-hotspot
ANDROID_HOME=C:\Users\{username}\AppData\Local\Android\Sdk
```

#### Installation Commands
```bash
# Install Appium
npm install -g appium@2.19.0
appium driver install uiautomator2

# Verify installation
appium driver list --installed
appium -v
```

## What's Working Right Now ✅

### ✅ Framework Features
- **Device Connectivity**: Connects to Android emulator (emulator-5554)
- **Google Contacts App Automation**: Successfully launches and interacts with contacts app
- **Page Object Model**: ContactsPage class with element locators and methods
- **Screenshot Capture**: Automatic screenshots at each test step
- **HTML Reporting**: Beautiful ExtentReports with embedded screenshots
- **Logging**: Comprehensive logging with different levels
- **Test Execution**: Multiple test types running successfully

### ✅ Working Test Cases
1. **DeviceConnectivityTest**: ✅ PASS - Verifies device connection and basic functionality
2. **ContactsExplorationTest**: ✅ PASS - Explores contacts app interface, finds UI elements
3. **CreateContactTest**: ✅ Partially Working - Opens contact form, enters data (save dialog discovered)

### ✅ Test Results Summary
- **Device Connection**: Successfully connects to emulator
- **App Launch**: Google Contacts app opens correctly  
- **UI Interaction**: Found "Create contact" button, form fields
- **Data Entry**: Successfully entered names in contact form
- **Screenshots**: All test steps documented with screenshots

## Running Tests

### Quick Test Commands
```bash
# Test device connectivity (recommended first test)
mvn test -Dtest=DeviceConnectivityTest

# Explore contacts app interface  
mvn test -Dtest=ContactsExplorationTest

# Try contact creation (work in progress)
mvn test -Dtest=CreateContactTest

# Run all tests
mvn test
```

### ⚠️ Important Notes
- Make sure emulator is running (`device-manager.bat` → option 1)
- Appium server must be started (`start-appium.bat`)
- Tests take time - wait for completion
- Check `reports/` folder for detailed HTML reports with screenshots

## Configuration

The framework is currently configured for:
- **App Package**: `com.google.android.contacts`
- **Main Activity**: `com.android.contacts.activities.PeopleActivity`
- **Device**: `emulator-5554` (Android emulator)
- **Appium Server**: `http://127.0.0.1:4723`

Configuration files:
- `src/test/resources/config/config.properties`: App and device settings
- `src/test/resources/testng.xml`: Test suite configuration

## Test Reports & Screenshots 📊

### HTML Reports
- **Location**: `reports/ExtentReport_[timestamp].html`
- **Features**: 
  - Test execution summary
  - Pass/Fail status with details
  - Embedded screenshots for each step
  - Execution timeline
  - System information

### Screenshots
- **Location**: `test-output/screenshots/`
- **Naming**: `[test_name]_[status]_[timestamp].png`
- **Captured**: At every significant test step

### Example Report Contents
```
✅ DeviceConnectivityTest: Device connectivity verified
✅ ContactsExplorationTest: Found 8 clickable elements, "Create contact" button located
🔄 CreateContactTest: Contact form opened, data entered, save dialog appeared
```

## Troubleshooting 🛠️

### Common Issues & Solutions

#### 1. "No devices attached" 
```bash
# Check emulator status
adb devices

# Start emulator
device-manager.bat  # Option 1
```

#### 2. "Appium server not running"
```bash
# Start Appium server
start-appium.bat

# Or manually
appium --address 127.0.0.1 --port 4723
```

#### 3. "Element not found" 
- Check screenshots in `test-output/screenshots/` to see actual UI
- Element locators may need adjustment if UI changed
- Use `ContactsExplorationTest` to discover current UI elements

#### 4. Tests running slowly
- This is normal for mobile automation
- Emulator performance depends on system resources
- Real devices typically faster than emulators

#### 5. Contact not saving properly
- This is a known issue - the save dialog appears but requires proper handling
- Framework successfully enters data and detects save dialog
- Future enhancement needed for complete save flow

### Debug Commands
```bash
# Check connected devices
adb devices

# View device logs  
adb logcat

# Check current app activity
adb shell dumpsys activity activities | grep -i contact
```

## Extending the Framework 🔧

### Adding New Test Cases
1. Create new test class in `src/test/java/com/automation/tests/`
2. Extend `BaseTest` class
3. Use `logTestInfo()` and `takeScreenshot()` for documentation
4. Follow existing patterns in `ContactsExplorationTest`

### Adding New Page Objects
1. Create new page class in `src/main/java/com/automation/pages/`
2. Extend `BasePage` class  
3. Define element locators and business methods
4. Follow patterns in `ContactsPage`

### Testing Different Apps
1. Update app package/activity in `DriverManager.java`
2. Create corresponding page objects
3. Adapt test locators for new app UI

## What You've Built 🏆

This framework demonstrates:
- **Professional mobile automation setup**
- **Real app testing** (Google Contacts)
- **Industry-standard patterns** (Page Object Model)
- **Comprehensive reporting** with visual evidence
- **Maintainable code structure**
- **Ready for extension** to other apps

Perfect foundation for mobile testing projects! 🎉

## Next Steps 💡

1. **Fix Contact Save**: Handle the save dialog properly in contact creation
2. **Add More Apps**: Extend to test other Android apps (Settings, Calculator, etc.)
3. **Real Device Testing**: Connect and test with physical Android devices
4. **CI/CD Integration**: Set up automated test execution
5. **Cross-Platform**: Extend framework to support iOS testing

## License
This project is licensed under the MIT License - see the LICENSE file for details.