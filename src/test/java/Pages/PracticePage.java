package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Set;

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
        List<WebElement> radios = driver.findElements(By.cssSelector("input[type='radio']"));
        if (index >= 0 && index < radios.size()) {
            // Wait for the element to be clickable before interacting
            WebElement radioButton = wait.until(ExpectedConditions.elementToBeClickable(radios.get(index)));
            radioButton.click();
        } else {
            throw new IllegalArgumentException("Radio button index out of bounds: " + index);
        }

    }

    public void typeInSuggestionBox(String text) {
        type(suggestionBox, text);
    }

    public void selectFromDropdown(String option) {

        By dropdownLocator = By.id("dropdown-class-example");
        WebElement dropdownElement = wait.until(ExpectedConditions.elementToBeClickable(dropdownLocator));
        Select dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(option);

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


    public boolean isCheckboxSelected(int index) {
        By checkboxLocator = By.xpath("//input[@type='checkbox']");
        List<WebElement> checkboxes = driver.findElements(checkboxLocator);
        if (index >= 0 && index < checkboxes.size()) {
            return checkboxes.get(index).isSelected();
        }
        throw new IllegalArgumentException("Checkbox index out of bounds: " + index);
    }

    public String getSelectedDropdownOption() {
        By dropdownLocator = By.id("dropdown-class-example");
        WebElement dropdownElement = wait.until(ExpectedConditions.presenceOfElementLocated(dropdownLocator));
        Select dropdown = new Select(dropdownElement);
        return dropdown.getFirstSelectedOption().getText();
    }


    public void handleNewWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String windowHandle : windows) {
            if (!windowHandle.equals(mainWindow)) {
                try {
                    driver.switchTo().window(windowHandle);
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    break;
                } catch (Exception e) {
                    throw new RuntimeException("Failed to handle new window", e);
                }
            }
        }
    }

    public boolean isRadioButtonSelected() {
        By radioButtonLocator = By.xpath("//input[@type='radio']");
        List<WebElement> radioButtons = driver.findElements(radioButtonLocator);
        return radioButtons.stream().anyMatch(WebElement::isSelected);
    }

    public boolean verifySuggestionDoesNotExist(String expectedText) {
        try {
            WebElement suggestion = driver.findElement(By.xpath("//div[contains(text(),'" + expectedText + "')]"));
            return false; // suggestion exists when it shouldn't
        } catch (NoSuchElementException e) {
            return true; // expected behavior - element not found
        }
    }

    // Using switchToAlert() and getAlertText() for alert handling
    public String handleSimpleAlert() {
        click(alertButton);
        switchToAlert();
        String alertText = getAlertText();
        driver.switchTo().alert().accept();
        return alertText;
    }

    // Another example of confirm alert
    public String handleConfirmAlert(boolean accept) {
        click(confirmButton);
        switchToAlert();
        String alertText = getAlertText();
        if (accept) {
            driver.switchTo().alert().accept();
        } else {
            driver.switchTo().alert().dismiss();
        }
        return alertText;
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

