package com.automation.tests;

import com.automation.base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Simple test to explore and interact with the Contacts app
 */
public class ContactsExplorationTest extends BaseTest {
    
    @Test(description = "Explore Contacts app interface and take screenshots")
    public void testContactsAppExploration() {
        logTestInfo("Starting Contacts app exploration");
        
        // Verify we're in the contacts app
        String currentActivity = driver.currentActivity();
        logTestInfo("Current activity: " + currentActivity);
        Assert.assertTrue(currentActivity.contains("contacts") || currentActivity.contains("people"), 
                         "Should be in contacts app");
        
        // Take screenshot of main contacts screen
        takeScreenshot("contacts_main_screen");
        
        // Find all clickable elements to understand the interface
        List<WebElement> clickableElements = driver.findElements(AppiumBy.xpath("//*[@clickable='true']"));
        logTestInfo("Found " + clickableElements.size() + " clickable elements");
        
        // Find all text elements to see what's displayed
        List<WebElement> textElements = driver.findElements(AppiumBy.xpath("//android.widget.TextView"));
        logTestInfo("Found " + textElements.size() + " text elements");
        
        // Log some of the text we can see
        for (int i = 0; i < Math.min(5, textElements.size()); i++) {
            try {
                String text = textElements.get(i).getText();
                if (text != null && !text.trim().isEmpty()) {
                    logTestInfo("Text element " + i + ": " + text);
                }
            } catch (Exception e) {
                // Skip elements that can't be read
            }
        }
        
        // Look for common contact app elements
        try {
            WebElement addButton = driver.findElement(AppiumBy.xpath("//*[contains(@content-desc,'Create') or contains(@content-desc,'Add')]"));
            logTestInfo("Found add/create button: " + addButton.getAttribute("content-desc"));
            takeScreenshot("found_add_button");
        } catch (Exception e) {
            logTestInfo("No obvious add/create button found");
        }
        
        // Look for FAB (Floating Action Button) which is common in Material Design
        try {
            WebElement fab = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton"));
            logTestInfo("Found potential FAB button: " + fab.getAttribute("content-desc"));
            takeScreenshot("found_fab");
        } catch (Exception e) {
            logTestInfo("No FAB found");
        }
        
        // Look for menu/options
        try {
            WebElement menu = driver.findElement(AppiumBy.xpath("//*[contains(@content-desc,'menu') or contains(@content-desc,'More')]"));
            logTestInfo("Found menu option: " + menu.getAttribute("content-desc"));
        } catch (Exception e) {
            logTestInfo("No obvious menu found");
        }
        
        // Check if there are any existing contacts
        try {
            List<WebElement> contactItems = driver.findElements(AppiumBy.xpath("//android.widget.LinearLayout[contains(@resource-id,'contact')]"));
            logTestInfo("Found " + contactItems.size() + " potential contact items");
        } catch (Exception e) {
            logTestInfo("Could not find contact items with standard locator");
        }
        
        // Final screenshot
        takeScreenshot("contacts_exploration_complete");
        
        logTestInfo("Contacts app exploration completed successfully");
    }
    
    @Test(priority = 2, description = "Test basic interaction with contacts app")
    public void testBasicContactsInteraction() {
        logTestInfo("Testing basic interactions");
        
        // Take screenshot at start
        takeScreenshot("interaction_start");
        
        // Try to tap on different areas to see what happens
        try {
            // Get screen size
            int screenWidth = driver.manage().window().getSize().getWidth();
            int screenHeight = driver.manage().window().getSize().getHeight();
            
            logTestInfo("Screen size: " + screenWidth + "x" + screenHeight);
            
            // Look for any button or interactive element
            List<WebElement> buttons = driver.findElements(AppiumBy.xpath("//android.widget.Button | //android.widget.ImageButton"));
            logTestInfo("Found " + buttons.size() + " buttons");
            
            if (buttons.size() > 0) {
                for (int i = 0; i < Math.min(2, buttons.size()); i++) {
                    try {
                        WebElement button = buttons.get(i);
                        String desc = button.getAttribute("content-desc");
                        String text = button.getText();
                        logTestInfo("Button " + i + " - desc: " + desc + ", text: " + text);
                        
                        // Take screenshot before clicking
                        takeScreenshot("before_button_click_" + i);
                        
                        button.click();
                        
                        // Wait a moment and take screenshot after click
                        Thread.sleep(2000);
                        takeScreenshot("after_button_click_" + i);
                        
                        // Navigate back if we moved to a different screen
                        driver.navigate().back();
                        Thread.sleep(1000);
                        
                    } catch (Exception e) {
                        logTestInfo("Could not interact with button " + i + ": " + e.getMessage());
                    }
                }
            }
            
        } catch (Exception e) {
            logTestInfo("Error during interaction: " + e.getMessage());
        }
        
        takeScreenshot("interaction_complete");
        logTestInfo("Basic interaction test completed");
    }
}
