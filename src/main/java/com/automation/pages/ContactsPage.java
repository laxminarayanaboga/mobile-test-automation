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
    
    @FindBy(id = "com.google.android.contacts:id/toolbar_button")
    private WebElement saveButton;
    
    @FindBy(xpath = "//android.widget.TextView[@text='Contacts']")
    private WebElement contactsTitle;
    
    // Alternative locators if the above don't work
    private final String ADD_CONTACT_XPATH = "//android.widget.ImageButton[@content-desc='Create contact']";
    private final String SAVE_CONTACT_XPATH = "//android.widget.Button[@text='Save']";
    private final String FIRST_NAME_XPATH = "//android.widget.EditText[contains(@text,'First') or contains(@hint,'First')]";
    private final String LAST_NAME_XPATH = "//android.widget.EditText[contains(@text,'Last') or contains(@hint,'Last')]";
    private final String PHONE_XPATH = "//android.widget.EditText[contains(@text,'Phone') or contains(@hint,'Phone')]";
    private final String EMAIL_XPATH = "//android.widget.EditText[contains(@text,'Email') or contains(@hint,'Email')]";
    
    /**
     * Click the add contact button to create a new contact
     */
    public void clickAddContact() {
        logger.info("Attempting to click add contact button");
        try {
            if (isElementPresent(addContactButton)) {
                click(addContactButton);
                logger.info("Clicked add contact button using ID locator");
            } else {
                // Try alternative locator
                WebElement addBtn = findElementSafely(AppiumBy.xpath(ADD_CONTACT_XPATH));
                if (addBtn != null) {
                    click(addBtn);
                    logger.info("Clicked add contact button using XPath locator");
                } else {
                    logger.error("Add contact button not found");
                    throw new RuntimeException("Cannot find add contact button");
                }
            }
        } catch (Exception e) {
            logger.error("Error clicking add contact button: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter first name in the contact form
     */
    public void enterFirstName(String firstName) {
        logger.info("Entering first name: " + firstName);
        try {
            if (isElementPresent(firstNameField)) {
                sendKeys(firstNameField, firstName);
                logger.info("Entered first name using ID locator");
            } else {
                WebElement nameField = findElementSafely(AppiumBy.xpath(FIRST_NAME_XPATH));
                if (nameField != null) {
                    sendKeys(nameField, firstName);
                    logger.info("Entered first name using XPath locator");
                } else {
                    logger.error("First name field not found");
                    throw new RuntimeException("Cannot find first name field");
                }
            }
        } catch (Exception e) {
            logger.error("Error entering first name: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter last name in the contact form
     */
    public void enterLastName(String lastName) {
        logger.info("Entering last name: " + lastName);
        try {
            if (isElementPresent(lastNameField)) {
                sendKeys(lastNameField, lastName);
                logger.info("Entered last name using ID locator");
            } else {
                WebElement nameField = findElementSafely(AppiumBy.xpath(LAST_NAME_XPATH));
                if (nameField != null) {
                    sendKeys(nameField, lastName);
                    logger.info("Entered last name using XPath locator");
                } else {
                    logger.error("Last name field not found");
                    throw new RuntimeException("Cannot find last name field");
                }
            }
        } catch (Exception e) {
            logger.error("Error entering last name: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter phone number in the contact form
     */
    public void enterPhoneNumber(String phoneNumber) {
        logger.info("Entering phone number: " + phoneNumber);
        try {
            if (isElementPresent(phoneField)) {
                sendKeys(phoneField, phoneNumber);
                logger.info("Entered phone number using ID locator");
            } else {
                WebElement phone = findElementSafely(AppiumBy.xpath(PHONE_XPATH));
                if (phone != null) {
                    sendKeys(phone, phoneNumber);
                    logger.info("Entered phone number using XPath locator");
                } else {
                    logger.error("Phone field not found");
                    throw new RuntimeException("Cannot find phone field");
                }
            }
        } catch (Exception e) {
            logger.error("Error entering phone number: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Enter email address in the contact form
     */
    public void enterEmail(String email) {
        logger.info("Entering email: " + email);
        try {
            if (isElementPresent(emailField)) {
                sendKeys(emailField, email);
                logger.info("Entered email using ID locator");
            } else {
                WebElement emailElement = findElementSafely(AppiumBy.xpath(EMAIL_XPATH));
                if (emailElement != null) {
                    sendKeys(emailElement, email);
                    logger.info("Entered email using XPath locator");
                } else {
                    logger.error("Email field not found");
                    throw new RuntimeException("Cannot find email field");
                }
            }
        } catch (Exception e) {
            logger.error("Error entering email: " + e.getMessage());
            throw e;
        }
    }
    
    /**
     * Save the contact
     */
    public void saveContact() {
        logger.info("Attempting to save contact");
        try {
            if (isElementPresent(saveButton)) {
                click(saveButton);
                logger.info("Clicked save button using ID locator");
            } else {
                WebElement saveBtn = findElementSafely(AppiumBy.xpath(SAVE_CONTACT_XPATH));
                if (saveBtn != null) {
                    click(saveBtn);
                    logger.info("Clicked save button using XPath locator");
                } else {
                    logger.error("Save button not found");
                    throw new RuntimeException("Cannot find save button");
                }
            }
        } catch (Exception e) {
            logger.error("Error saving contact: " + e.getMessage());
            throw e;
        }
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
        enterPhoneNumber(phoneNumber);
        enterEmail(email);
        
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
