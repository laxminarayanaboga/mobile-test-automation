package com.automation.pages;

import com.automation.base.BasePage;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Page Object Model for Android Contacts App
 * Handles all contact-related operations like creating, editing, and viewing contacts
 */
public class ContactsPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(ContactsPage.class);
    
    // Constructor
    public ContactsPage(AndroidDriver driver) {
        super();
        PageFactory.initElements(driver, this);
        logger.info("ContactsPage initialized");
    }
    
    // Elements using different locator strategies
    @FindBy(id = "com.google.android.contacts:id/floating_action_button")
    private WebElement addContactButton;
    
    @FindBy(xpath = "//android.widget.EditText[@text='First name']")
    private WebElement firstNameField;
    
    @FindBy(xpath = "//android.widget.EditText[@text='Last name']")
    private WebElement lastNameField;
    
    @FindBy(xpath = "//android.widget.EditText[@text='Phone']")
    private WebElement phoneField;
    
    @FindBy(xpath = "//android.widget.EditText[@text='Email']")
    private WebElement emailField;
    
    @FindBy(xpath = "//androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View[2]/android.view.View[2]/android.widget.Button")
    private WebElement saveButton;
    
    @FindBy(xpath = "//android.widget.TextView[@text='Contacts']")
    private WebElement contactsTitle;
    
    /**
     * Click the add contact button to create a new contact
     */
    public void clickAddContact() {
        logger.info("Clicking add contact button");
        click(addContactButton);
    }
    
    /**
     * Enter first name in the contact form
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: " + firstName);
        sendKeys(firstNameField, firstName);
    }
    
    /**
     * Enter last name in the contact form
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: " + lastName);
        sendKeys(lastNameField, lastName);
    }
    
    /**
     * Enter phone number in the contact form
     */
    public void enterPhoneNumber(String phoneNumber) {
        logger.info("Entering phone number: " + phoneNumber);
        sendKeys(phoneField, phoneNumber);
    }
    
    /**
     * Enter email address in the contact form
     */
    public void enterEmail(String email) {
        logger.info("Entering email: " + email);
        sendKeys(emailField, email);
    }
    
    /**
     * Save the contact
     */
    public void saveContact() {
        logger.info("Clicking save button");
        click(saveButton);
    }
    
    /**
     * Verify contacts page is displayed
     */
    public boolean isContactsPageDisplayed() {
        logger.info("Checking if contacts page is displayed");
        try {
            boolean isDisplayed = isElementPresent(contactsTitle) || 
                                findElementSafely(AppiumBy.xpath("//android.widget.TextView[contains(@text,'Contact')]")) != null;
            logger.info("Contacts page displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error checking contacts page: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Create a complete contact with all details
     */
    public void createContact(String firstName, String lastName, String phoneNumber, String email) {
        logger.info("Creating new contact: " + firstName + " " + lastName);
        
        clickAddContact();
        waitForElementToBeClickable(2); // Wait for form to load
        
        enterFirstName(firstName);
        enterLastName(lastName);
        // enterPhoneNumber(phoneNumber);
        // enterEmail(email);
        
        saveContact();
        waitForElementToBeClickable(2); // Wait for save to complete
        
        logger.info("Contact creation completed");
    }
    
    /**
     * Search for a contact by name
     */
    public boolean searchContact(String contactName) {
        logger.info("Searching for contact: " + contactName);
        try {
            // Look for search functionality
            WebElement searchIcon = findElementSafely(AppiumBy.xpath("//android.widget.ImageView[@content-desc='Search']"));
            if (searchIcon != null) {
                click(searchIcon);
                
                WebElement searchField = findElementSafely(AppiumBy.xpath("//android.widget.EditText"));
                if (searchField != null) {
                    sendKeys(searchField, contactName);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            logger.error("Error searching for contact: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Verify a contact exists in the list
     */
    public boolean isContactDisplayed(String contactName) {
        logger.info("Checking if contact is displayed: " + contactName);
        try {
            WebElement contact = findElementSafely(AppiumBy.xpath("//android.widget.TextView[contains(@text,'" + contactName + "')]"));
            boolean isDisplayed = contact != null;
            logger.info("Contact " + contactName + " displayed: " + isDisplayed);
            return isDisplayed;
        } catch (Exception e) {
            logger.error("Error checking if contact is displayed: " + e.getMessage());
            return false;
        }
    }
}
