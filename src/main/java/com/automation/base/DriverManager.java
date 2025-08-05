package com.automation.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.time.Duration;

/**
 * Driver Manager class to handle Appium driver initialization and management
 */
public class DriverManager {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static ThreadLocal<AndroidDriver> driver = new ThreadLocal<>();
    private static AppiumDriverLocalService service;
    
    /**
     * Initialize Android driver with capabilities
     */
    public static void initializeDriver() {
        try {
            UiAutomator2Options options = new UiAutomator2Options();
            
            // Basic capabilities
            options.setPlatformName("Android");
            options.setDeviceName("emulator-5554"); // This can be overridden by actual device name
            options.setAutomationName("UiAutomator2");
            
            // Launch Contacts app
            options.setAppPackage("com.google.android.contacts");
            options.setAppActivity("com.android.contacts.activities.PeopleActivity");
            
            // Optional capabilities
            options.setNewCommandTimeout(Duration.ofSeconds(300));
            options.setNoReset(true); // Don't reset app state
            
            // Initialize driver
            URL appiumServerURL = new URL("http://127.0.0.1:4723");
            driver.set(new AndroidDriver(appiumServerURL, options));
            
            // Set implicit wait
            getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            
            logger.info("Android driver initialized successfully");
            
        } catch (Exception e) {
            logger.error("Failed to initialize Android driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }
    
    /**
     * Get the current driver instance
     */
    public static AndroidDriver getDriver() {
        return driver.get();
    }
    
    /**
     * Start Appium server programmatically
     */
    public static void startAppiumServer() {
        try {
            AppiumServiceBuilder builder = new AppiumServiceBuilder()
                    .withIPAddress("127.0.0.1")
                    .usingPort(4723)
                    .withArgument(() -> "--base-path", "/")
                    .withTimeout(Duration.ofSeconds(30));
            
            service = AppiumDriverLocalService.buildService(builder);
            service.start();
            
            logger.info("Appium server started on http://127.0.0.1:4723");
            
        } catch (Exception e) {
            logger.error("Failed to start Appium server: " + e.getMessage());
            throw new RuntimeException("Appium server start failed", e);
        }
    }
    
    /**
     * Stop Appium server
     */
    public static void stopAppiumServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            logger.info("Appium server stopped");
        }
    }
    
    /**
     * Quit driver and clean up
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
            logger.info("Driver quit successfully");
        }
    }
    
    /**
     * Check if driver is initialized
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}
