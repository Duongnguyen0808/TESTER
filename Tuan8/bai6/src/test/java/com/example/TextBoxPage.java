package com.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Page Object Model cho https://demoqa.com/text-box
 */
public class TextBoxPage {

    private WebDriver driver;

    @FindBy(id = "userName")
    private WebElement nameField;

    @FindBy(id = "userEmail")
    private WebElement emailField;

    @FindBy(id = "currentAddress")
    private WebElement currentAddressField;

    @FindBy(id = "permanentAddress")
    private WebElement permanentAddressField;

    @FindBy(id = "submit")
    private WebElement submitBtn;

    @FindBy(id = "output")
    private WebElement outputSection;

    @FindBy(id = "name")
    private WebElement outputName;

    @FindBy(id = "email")
    private WebElement outputEmail;

    public TextBoxPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void fillAndSubmit(String name, String email, String address, String permAddress) {
        if (name != null && !name.isEmpty()) {
            nameField.clear();
            nameField.sendKeys(name);
        }
        if (email != null && !email.isEmpty()) {
            emailField.clear();
            emailField.sendKeys(email);
        }
        if (address != null && !address.isEmpty()) {
            currentAddressField.clear();
            currentAddressField.sendKeys(address);
        }
        if (permAddress != null && !permAddress.isEmpty()) {
            permanentAddressField.clear();
            permanentAddressField.sendKeys(permAddress);
        }
        // Scroll to submit button and click
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView(true);", submitBtn);
        ((org.openqa.selenium.JavascriptExecutor) driver)
            .executeScript("arguments[0].click();", submitBtn);
    }

    public boolean isOutputDisplayed() {
        try {
            return outputSection.isDisplayed() && !outputSection.getText().trim().isEmpty();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public String getOutputName() {
        try {
            return outputName.getText();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return "";
        }
    }

    public String getOutputEmail() {
        try {
            return outputEmail.getText();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return "";
        }
    }

    public boolean isEmailFieldError() {
        String cls = emailField.getAttribute("class");
        return cls != null && cls.contains("field-error");
    }
}
