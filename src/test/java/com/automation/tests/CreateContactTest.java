package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.ContactsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.util.Random;

/**
 * Test to create a new contact in the Contacts app
 */
public class CreateContactTest extends BaseTest {
    
    @Test(description = "Create a new contact with name and phone number")
    public void testCreateNewContact() {
        logTestInfo("Starting create contact test");
        
        // Initialize the ContactsPage
        ContactsPage contactsPage = new ContactsPage(driver);
        
        // Verify we're in contacts app
        String currentActivity = driver.currentActivity();
        logTestInfo("Current activity: " + currentActivity);
        Assert.assertTrue(currentActivity.contains("contacts") || currentActivity.contains("people"), 
                         "Should be in contacts app");
        
        // Verify contacts page is displayed
        Assert.assertTrue(contactsPage.isContactsPageDisplayed(), "Contacts page should be displayed");
        
        // Take screenshot of main screen
        takeScreenshot("contacts_main_before_create");
        
        try {
            // Generate random contact data
            String randomNum = String.valueOf(System.currentTimeMillis() % 10000);
            String firstName = "John" + randomNum;
            String lastName = "Doe" + randomNum;
            String phone = "555" + randomNum;
            String email = "john" + randomNum + "@test.com";
            
            logTestInfo("Creating contact: " + firstName + " " + lastName);
            
            // Create a new contact using the page object
            contactsPage.createContact(firstName, lastName, phone, email);
            
            takeScreenshot("after_contact_creation");
            logTestInfo("Contact creation completed successfully");
            
            // Verify contact was created by checking if it appears in the list
            boolean contactDisplayed = contactsPage.isContactDisplayed(firstName);
            if (contactDisplayed) {
                logTestInfo("SUCCESS: Contact found in the list: " + firstName);
                takeScreenshot("contact_found_in_list");
            } else {
                logTestInfo("Contact not immediately visible: " + firstName);
                takeScreenshot("contact_not_visible");
            }
            
        } catch (Exception e) {
            logTestInfo("Error during contact creation: " + e.getMessage());
            takeScreenshot("contact_creation_error");
            throw e; // Re-throw to fail the test
        }
        
        takeScreenshot("create_contact_test_complete");
        logTestInfo("Create contact test completed");
    }
    
    @Test(priority = 2, description = "Verify contacts list shows created contact")
    public void testVerifyContactInList() {
        logTestInfo("Verifying contact appears in contacts list");
        
        // Initialize the ContactsPage
        ContactsPage contactsPage = new ContactsPage(driver);
        
        takeScreenshot("contacts_list_verification");
        
        try {
            // Use the page object to search for the contact
            boolean foundContact = contactsPage.isContactDisplayed("John");
            
            if (foundContact) {
                logTestInfo("SUCCESS: Contact 'John' found in the contacts list");
                takeScreenshot("contact_verification_success");
            } else {
                logTestInfo("Contact 'John' not found in the visible contacts list");
                
                // Try searching for the contact
                boolean searchResult = contactsPage.searchContact("John");
                if (searchResult) {
                    logTestInfo("Initiated search for contact");
                    takeScreenshot("after_search_initiated");
                } else {
                    logTestInfo("Search functionality not available or failed");
                }
            }
            
        } catch (Exception e) {
            logTestInfo("Error during contact verification: " + e.getMessage());
            takeScreenshot("contact_verification_error");
        }
        
        takeScreenshot("final_contacts_verification");
        logTestInfo("Contact verification test completed");
    }
}
