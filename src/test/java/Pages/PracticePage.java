package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class PracticePage extends BasePage {
    private static final String PAGE_URL = "https://rahulshettyacademy.com/AutomationPractice/";

    // Locators
    private final By radioButtons = By.name("radioButton");
    private final By suggestionBox = By.id("autocomplete");
    private final By dropdown = By.id("dropdown-class-example");
    private final By checkboxes = By.cssSelector("input[type='checkbox']");
    private final By openWindowButton = By.id("openwindow");
    private final By openTabButton = By.id("opentab");
    private final By alertTextBox = By.id("name");
    private final By alertButton = By.id("alertbtn");
    private final By confirmButton = By.id("confirmbtn");
    private final By showHideField = By.id("displayed-text");
    private final By hideButton = By.id("hide-textbox");
    private final By showButton = By.id("show-textbox");
    private final By mouseHover = By.id("mousehover");
    private final By iframeExample = By.id("courses-iframe");

    public PracticePage(WebDriver driver) {
        super(driver);
    }

    public void navigateTo() {
        driver.get(PAGE_URL);
    }

    public void selectRadioButton(int index) {
        List<WebElement> radios = driver.findElements(radioButtons);
        if (index >= 0 && index < radios.size()) {
            radios.get(index).click();
        }
    }

    public void typeInSuggestionBox(String text) {
        type(suggestionBox, text);
    }

    public void selectFromDropdown(String option) {
        selectDropdownByText(dropdown, option);
    }

    public void selectCheckbox(int index) {
        List<WebElement> boxes = driver.findElements(checkboxes);
        if (index >= 0 && index < boxes.size()) {
            boxes.get(index).click();
        }
    }

    public void openWindow() {
        click(openWindowButton);
    }

    public void openTab() {
        click(openTabButton);
    }

    public void handleAlert(String name, boolean accept) {
        type(alertTextBox, name);
        click(alertButton);
        if (accept) {
            acceptAlert();
        } else {
            dismissAlert();
        }
    }

    public void handleConfirm(String name, boolean accept) {
        type(alertTextBox, name);
        click(confirmButton);
        if (accept) {
            acceptAlert();
        } else {
            dismissAlert();
        }
    }

    public void toggleElement(boolean show) {
        click(show ? showButton : hideButton);
    }

    public boolean isElementDisplayed() {
        return isDisplayed(showHideField);
    }

    public void hoverOverElement() {
        WebElement element = waitForElement(mouseHover);
        // Implement hover action
    }

    public void switchToIframe() {
        driver.switchTo().frame(driver.findElement(iframeExample));
    }

    public void switchToMainContent() {
        driver.switchTo().defaultContent();
    }
}

