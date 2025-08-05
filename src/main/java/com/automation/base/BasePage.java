package com.automation.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

/**
 * Base Page class that all page object classes should extend
 * Provides common page operations and driver management
 */
public class BasePage {
    
    protected AndroidDriver driver;
    protected WebDriverWait wait;
    protected static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected static final int DEFAULT_WAIT_TIME = 10;
    
    public BasePage() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_WAIT_TIME));
        
        // Initialize page elements using Appium PageFactory
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }
    
    /**
     * Get page title or current activity
     */
    public String getCurrentActivity() {
        return driver.currentActivity();
    }
    
    /**
     * Check if the page is loaded by verifying key elements
     */
    public boolean isPageLoaded() {
        // Override in child classes with specific page verification logic
        return true;
    }
    
    /**
     * Navigate back using device back button
     */
    public void goBack() {
        driver.navigate().back();
        logger.info("Navigated back");
    }
    
    /**
     * Hide keyboard if visible
     */
    public void hideKeyboard() {
        try {
            if (driver.isKeyboardShown()) {
                driver.hideKeyboard();
                logger.info("Keyboard hidden");
            }
        } catch (Exception e) {
            logger.debug("Keyboard not shown or couldn't hide: " + e.getMessage());
        }
    }
    
    /**
     * Take screenshot
     */
    public String takeScreenshot() {
        // Implementation will be added in utility class
        return "screenshot_path";
    }
    
    /**
     * Check if element is present
     */
    protected boolean isElementPresent(WebElement element) {
        try {
            return element != null && element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Find element safely without throwing exception
     */
    protected WebElement findElementSafely(By locator) {
        try {
            return driver.findElement(locator);
        } catch (NoSuchElementException e) {
            logger.debug("Element not found: " + locator);
            return null;
        }
    }
    
    /**
     * Click element safely
     */
    protected void click(WebElement element) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element));
            element.click();
            logger.debug("Clicked element successfully");
        } catch (Exception e) {
            logger.error("Failed to click element: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Send keys to element safely
     */
    protected void sendKeys(WebElement element, String text) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            element.clear();
            element.sendKeys(text);
            logger.debug("Sent keys to element: " + text);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Wait for element to be clickable
     */
    protected void waitForElementToBeClickable(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
