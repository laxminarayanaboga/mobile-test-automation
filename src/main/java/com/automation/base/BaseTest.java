package com.automation.base;

import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;
import com.automation.utils.ScreenshotUtils;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base Test class that all test classes should extend
 * Handles driver setup, teardown, and common test operations
 */
public class BaseTest {
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected AndroidDriver driver;
    
    @BeforeSuite
    public void beforeSuite() {
        logger.info("Starting test suite execution");
        ExtentReportManager.initializeReport();
        
        // Start Appium server if configured to do so
        String startServer = ConfigReader.getProperty("start.appium.server");
        if ("true".equalsIgnoreCase(startServer)) {
            DriverManager.startAppiumServer();
        }
    }
    
    @BeforeClass
    public void beforeClass() {
        logger.info("Setting up test class: " + this.getClass().getSimpleName());
    }
    
    @BeforeMethod
    public void beforeMethod() {
        logger.info("Starting test method");
        
        // Initialize driver for each test
        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();
        
        // Create test entry in extent report
        ExtentReportManager.createTest(getClass().getSimpleName());
    }
    
    @AfterMethod
    public void afterMethod() {
        logger.info("Finishing test method");
        
        // Quit driver after each test
        DriverManager.quitDriver();
    }
    
    @AfterClass
    public void afterClass() {
        logger.info("Tearing down test class: " + this.getClass().getSimpleName());
    }
    
    @AfterSuite
    public void afterSuite() {
        logger.info("Finishing test suite execution");
        
        // Stop Appium server if it was started
        String startServer = ConfigReader.getProperty("start.appium.server");
        if ("true".equalsIgnoreCase(startServer)) {
            DriverManager.stopAppiumServer();
        }
        
        // Flush extent reports
        ExtentReportManager.flushReports();
    }
    
    /**
     * Log test information
     */
    protected void logTestInfo(String message) {
        logger.info(message);
        ExtentReportManager.logInfo(message);
    }
    
    /**
     * Take screenshot with custom name
     */
    protected void takeScreenshot(String screenshotName) {
        try {
            String screenshotPath = ScreenshotUtils.takeScreenshot(screenshotName);
            ExtentReportManager.addScreenshot(screenshotPath);
            logger.info("Screenshot taken: " + screenshotName);
        } catch (Exception e) {
            logger.error("Failed to take screenshot: " + e.getMessage());
        }
    }
}
