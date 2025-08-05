package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.base.DriverManager;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.ScreenshotUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Simple device connectivity test
 */
public class DeviceConnectivityTest extends BaseTest {
    
    @Test(priority = 1, description = "Test basic device connectivity and driver initialization")
    public void testDeviceConnectivity() {
        logger.info("Starting test: Device Connectivity");
        ExtentReportManager.createTest("Device Connectivity Test");
        
        try {
            // Check if driver is initialized
            boolean isDriverReady = DriverManager.isDriverInitialized();
            Assert.assertTrue(isDriverReady, "Driver should be initialized");
            
            // Get device info
            String currentActivity = DriverManager.getDriver().currentActivity();
            logger.info("Current activity: " + currentActivity);
            
            // Get device orientation
            org.openqa.selenium.ScreenOrientation orientation = DriverManager.getDriver().getOrientation();
            logger.info("Device orientation: " + orientation);
            
            ExtentReportManager.logPass("Device connectivity test passed!");
            ExtentReportManager.logInfo("Current Activity: " + currentActivity);
            ExtentReportManager.logInfo("Device Orientation: " + orientation);
            
            ScreenshotUtils.takePassScreenshot("DeviceConnectivity");
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Device connectivity test failed: " + e.getMessage());
            ScreenshotUtils.takeFailureScreenshot("DeviceConnectivityFailed");
            throw e;
        }
    }
}
