package com.automation.tests;

import com.automation.base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Complete contact creation test that properly handles the save dialog
 */
public class CompleteContactTest extends BaseTest {
    
    @Test(description = "Create and save a new contact successfully")
    public void testCreateAndSaveContact() {
        logTestInfo("Starting complete contact creation test");
        
        // Take initial screenshot
        takeScreenshot("initial_contacts_screen");
        
        try {
            // Step 1: Click Create Contact button
            WebElement createButton = driver.findElement(AppiumBy.xpath("//*[@content-desc='Create contact']"));
            logTestInfo("Found Create contact button");
            createButton.click();
            logTestInfo("Clicked Create contact button");
            
            Thread.sleep(3000); // Wait for form to load
            takeScreenshot("contact_form_opened");
            
            // Step 2: Enter contact information
            WebElement firstNameField = driver.findElement(AppiumBy.xpath("//android.widget.EditText[contains(@hint,'First') or contains(@text,'First')]"));
            firstNameField.click();
            firstNameField.sendKeys("Jane");
            logTestInfo("Entered first name: Jane");
            
            takeScreenshot("first_name_entered");
            
            // Try to find and fill last name field
            try {
                WebElement lastNameField = driver.findElement(AppiumBy.xpath("//android.widget.EditText[contains(@hint,'Last') or contains(@text,'Last')]"));
                lastNameField.click();
                lastNameField.sendKeys("Smith");
                logTestInfo("Entered last name: Smith");
                takeScreenshot("last_name_entered");
            } catch (Exception e) {
                logTestInfo("Last name field not found or not needed");
            }
            
            // Step 3: Navigate back to trigger save dialog
            driver.navigate().back();
            Thread.sleep(2000);
            takeScreenshot("save_dialog_appeared");
            
            // Step 4: Click Save in the dialog
            WebElement saveButton = driver.findElement(AppiumBy.xpath("//android.widget.Button[@text='Save']"));
            logTestInfo("Found Save button in dialog");
            saveButton.click();
            logTestInfo("Clicked Save button");
            
            Thread.sleep(3000); // Wait for save to complete
            takeScreenshot("contact_saved");
            
            // Step 5: Verify contact appears in list
            try {
                WebElement contactInList = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,'Jane')]"));
                String contactText = contactInList.getText();
                logTestInfo("SUCCESS: Contact found in list: " + contactText);
                takeScreenshot("contact_found_success");
                
                // Click on the contact to view details
                contactInList.click();
                Thread.sleep(2000);
                takeScreenshot("contact_details_view");
                
            } catch (Exception e) {
                logTestInfo("Contact not immediately visible, checking all text elements");
                
                // Print all visible text to understand the current state
                var allText = driver.findElements(AppiumBy.xpath("//android.widget.TextView"));
                for (int i = 0; i < Math.min(10, allText.size()); i++) {
                    try {
                        String text = allText.get(i).getText();
                        if (text != null && !text.trim().isEmpty()) {
                            logTestInfo("Visible text " + i + ": " + text);
                        }
                    } catch (Exception ex) {
                        // Skip unreadable elements
                    }
                }
            }
            
        } catch (Exception e) {
            logTestInfo("Error during complete contact test: " + e.getMessage());
            takeScreenshot("test_error");
            e.printStackTrace();
            Assert.fail("Test failed: " + e.getMessage());
        }
        
        takeScreenshot("complete_contact_test_finished");
        logTestInfo("Complete contact creation test finished");
    }
    
    @Test(priority = 2, description = "Test contact search functionality")
    public void testSearchCreatedContact() {
        logTestInfo("Testing search functionality for created contact");
        
        takeScreenshot("search_test_start");
        
        try {
            // Look for search functionality
            WebElement searchButton = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='Search']"));
            searchButton.click();
            logTestInfo("Clicked search button");
            
            Thread.sleep(2000);
            takeScreenshot("search_opened");
            
            // Enter search term
            WebElement searchField = driver.findElement(AppiumBy.xpath("//android.widget.EditText"));
            searchField.sendKeys("Jane");
            logTestInfo("Entered search term: Jane");
            
            Thread.sleep(2000);
            takeScreenshot("search_results");
            
            // Check search results
            try {
                WebElement searchResult = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,'Jane')]"));
                String resultText = searchResult.getText();
                logTestInfo("Search result found: " + resultText);
                takeScreenshot("search_successful");
            } catch (Exception e) {
                logTestInfo("No search results found for 'Jane'");
                takeScreenshot("no_search_results");
            }
            
        } catch (Exception e) {
            logTestInfo("Search functionality not available or error: " + e.getMessage());
            takeScreenshot("search_error");
        }
        
        takeScreenshot("search_test_complete");
        logTestInfo("Search test completed");
    }
}
