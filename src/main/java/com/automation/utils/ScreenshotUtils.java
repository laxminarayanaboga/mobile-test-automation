package com.automation.utils;

import com.automation.base.DriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Screenshot utility for capturing and saving screenshots
 */
public class ScreenshotUtils {
    
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtils.class);
    private static final String SCREENSHOT_DIR = "test-output/screenshots";
    
    /**
     * Take screenshot and save with timestamp
     */
    public static String takeScreenshot(String testName) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(SCREENSHOT_DIR);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            // Generate screenshot filename with timestamp
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            String filePath = SCREENSHOT_DIR + "/" + fileName;
            
            // Take screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getDriver();
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destinationFile = new File(filePath);
            
            // Copy screenshot to destination
            FileUtils.copyFile(sourceFile, destinationFile);
            
            logger.info("Screenshot captured: " + filePath);
            return filePath;
            
        } catch (IOException e) {
            logger.error("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Take screenshot for test failure
     */
    public static String takeFailureScreenshot(String testName) {
        String screenshotPath = takeScreenshot(testName + "_FAILURE");
        if (screenshotPath != null) {
            ExtentReportManager.addScreenshot(screenshotPath);
        }
        return screenshotPath;
    }
    
    /**
     * Take screenshot for test pass
     */
    public static String takePassScreenshot(String testName) {
        String screenshotPath = takeScreenshot(testName + "_PASS");
        if (screenshotPath != null) {
            ExtentReportManager.addScreenshot(screenshotPath);
        }
        return screenshotPath;
    }
}
