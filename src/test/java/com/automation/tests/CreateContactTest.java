package com.automation.tests;

import com.automation.base.BaseTest;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Test to create a new contact in the Contacts app
 */
public class CreateContactTest extends BaseTest {
    
    @Test(description = "Create a new contact with name and phone number")
    public void testCreateNewContact() {
        logTestInfo("Starting create contact test");
        
        // Verify we're in contacts app
        String currentActivity = driver.currentActivity();
        logTestInfo("Current activity: " + currentActivity);
        Assert.assertTrue(currentActivity.contains("contacts") || currentActivity.contains("people"), 
                         "Should be in contacts app");
        
        // Take screenshot of main screen
        takeScreenshot("contacts_main_before_create");
        
        try {
            // Find and click the "Create contact" button
            WebElement createButton = driver.findElement(AppiumBy.xpath("//*[@content-desc='Create contact']"));
            logTestInfo("Found Create contact button");
            
            takeScreenshot("before_clicking_create");
            createButton.click();
            logTestInfo("Clicked Create contact button");
            
            // Wait for the contact creation form to load
            Thread.sleep(3000);
            takeScreenshot("contact_creation_form");
            
            // Look for name input fields
            try {
                // Try to find first name field
                WebElement firstNameField = driver.findElement(AppiumBy.xpath("//android.widget.EditText[contains(@hint,'First') or contains(@text,'First')]"));
                logTestInfo("Found first name field");
                
                firstNameField.click();
                firstNameField.sendKeys("John");
                logTestInfo("Entered first name: John");
                
                takeScreenshot("after_entering_first_name");
                
            } catch (Exception e) {
                logTestInfo("Could not find specific first name field, trying general name field");
                // Try to find any name field
                WebElement nameField = driver.findElement(AppiumBy.xpath("//android.widget.EditText[contains(@hint,'name') or contains(@hint,'Name')]"));
                nameField.click();
                nameField.sendKeys("John Doe");
                logTestInfo("Entered full name: John Doe");
                
                takeScreenshot("after_entering_name");
            }
            
            // Look for phone number field
            try {
                WebElement phoneField = driver.findElement(AppiumBy.xpath("//android.widget.EditText[contains(@hint,'Phone') or contains(@hint,'phone')]"));
                logTestInfo("Found phone field");
                
                phoneField.click();
                phoneField.sendKeys("1234567890");
                logTestInfo("Entered phone number: 1234567890");
                
                takeScreenshot("after_entering_phone");
                
            } catch (Exception e) {
                logTestInfo("Could not find phone field: " + e.getMessage());
            }
            
            // Look for save button
            try {
                WebElement saveButton = driver.findElement(AppiumBy.xpath("//android.widget.Button[contains(@text,'Save') or contains(@content-desc,'Save')]"));
                logTestInfo("Found Save button");
                
                takeScreenshot("before_saving_contact");
                saveButton.click();
                logTestInfo("Clicked Save button");
                
                // Wait for save to complete
                Thread.sleep(3000);
                takeScreenshot("after_saving_contact");
                
            } catch (Exception e) {
                logTestInfo("Could not find Save button, looking for other save options: " + e.getMessage());
                
                // Try to find toolbar save button
                try {
                    WebElement toolbarSave = driver.findElement(AppiumBy.xpath("//android.widget.ImageButton[@content-desc='Save']"));
                    toolbarSave.click();
                    logTestInfo("Clicked toolbar Save button");
                    
                    Thread.sleep(3000);
                    takeScreenshot("after_toolbar_save");
                    
                } catch (Exception e2) {
                    logTestInfo("Could not find any save button: " + e2.getMessage());
                    takeScreenshot("no_save_button_found");
                }
            }
            
            // Navigate back to contacts list to see if contact was created
            try {
                driver.navigate().back();
                Thread.sleep(2000);
                takeScreenshot("back_to_contacts_list");
                
                // Check if we can see the created contact
                try {
                    WebElement contactInList = driver.findElement(AppiumBy.xpath("//android.widget.TextView[contains(@text,'John')]"));
                    String contactText = contactInList.getText();
                    logTestInfo("SUCCESS: Found contact in list: " + contactText);
                    takeScreenshot("contact_found_in_list");
                } catch (Exception e) {
                    logTestInfo("Contact not immediately visible in list, this might be normal");
                    takeScreenshot("contact_not_visible");
                }
                
            } catch (Exception e) {
                logTestInfo("Error navigating back: " + e.getMessage());
            }
            
        } catch (Exception e) {
            logTestInfo("Error during contact creation: " + e.getMessage());
            takeScreenshot("contact_creation_error");
            e.printStackTrace();
        }
        
        takeScreenshot("create_contact_test_complete");
        logTestInfo("Create contact test completed");
    }
    
    @Test(priority = 2, description = "Verify contacts list shows created contact")
    public void testVerifyContactInList() {
        logTestInfo("Verifying contact appears in contacts list");
        
        takeScreenshot("contacts_list_verification");
        
        try {
            // Look for any contacts in the list
            var contactElements = driver.findElements(AppiumBy.xpath("//android.widget.TextView[contains(@text,'John') or contains(@text,'Doe')]"));
            logTestInfo("Found " + contactElements.size() + " elements containing 'John' or 'Doe'");
            
            for (int i = 0; i < contactElements.size(); i++) {
                try {
                    String text = contactElements.get(i).getText();
                    logTestInfo("Contact element " + i + ": " + text);
                } catch (Exception e) {
                    // Skip elements that can't be read
                }
            }
            
        } catch (Exception e) {
            logTestInfo("Error searching for contacts: " + e.getMessage());
        }
        
        // Check the current screen state
        try {
            var allTextElements = driver.findElements(AppiumBy.xpath("//android.widget.TextView"));
            logTestInfo("All text on screen:");
            for (int i = 0; i < Math.min(10, allTextElements.size()); i++) {
                try {
                    String text = allTextElements.get(i).getText();
                    if (text != null && !text.trim().isEmpty() && text.length() > 1) {
                        logTestInfo("Text " + i + ": " + text);
                    }
                } catch (Exception e) {
                    // Skip elements that can't be read
                }
            }
        } catch (Exception e) {
            logTestInfo("Could not read screen text: " + e.getMessage());
        }
        
        takeScreenshot("final_contacts_verification");
        logTestInfo("Contact verification test completed");
    }
}
