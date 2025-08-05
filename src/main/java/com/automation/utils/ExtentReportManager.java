package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Extent Report Manager for generating beautiful test reports
 */
public class ExtentReportManager {
    
    private static final Logger logger = LoggerFactory.getLogger(ExtentReportManager.class);
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    private static String reportPath;
    
    /**
     * Initialize Extent Reports
     */
    public static void initializeReport() {
        if (extent == null) {
            // Create reports directory if it doesn't exist
            File reportsDir = new File("reports");
            if (!reportsDir.exists()) {
                reportsDir.mkdirs();
            }
            
            // Generate report file name with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            reportPath = "reports/ExtentReport_" + timestamp + ".html";
            
            // Initialize Spark reporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Mobile Test Automation Report");
            sparkReporter.config().setReportName("Android App Test Results");
            
            // Initialize Extent Reports
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            
            // Add system information
            extent.setSystemInfo("Platform", "Android");
            extent.setSystemInfo("Automation Tool", "Appium");
            extent.setSystemInfo("Framework", "TestNG");
            extent.setSystemInfo("Tester", System.getProperty("user.name"));
            
            logger.info("Extent Reports initialized. Report will be saved at: " + reportPath);
        }
    }
    
    /**
     * Create a new test in the report
     */
    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }
    
    /**
     * Get current test instance
     */
    public static ExtentTest getTest() {
        return test.get();
    }
    
    /**
     * Log info message to report
     */
    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().info(message);
        }
    }
    
    /**
     * Log pass message to report
     */
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }
    
    /**
     * Log fail message to report
     */
    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        }
    }
    
    /**
     * Log warning message to report
     */
    public static void logWarning(String message) {
        if (getTest() != null) {
            getTest().warning(message);
        }
    }
    
    /**
     * Add screenshot to report
     */
    public static void addScreenshot(String screenshotPath) {
        if (getTest() != null) {
            getTest().addScreenCaptureFromPath(screenshotPath);
        }
    }
    
    /**
     * Flush reports and save
     */
    public static void flushReports() {
        if (extent != null) {
            extent.flush();
            logger.info("Extent Reports flushed successfully. Report saved at: " + reportPath);
        }
    }
    
    /**
     * Get report path
     */
    public static String getReportPath() {
        return reportPath;
    }
}
