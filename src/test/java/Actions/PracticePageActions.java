package Actions;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class PracticePageActions {
    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By suggestionInput = By.id("autocomplete");
    private final By suggestionsList = By.cssSelector(".ui-menu-item div");
    private final By dropdownSelect = By.id("dropdown-class-example");
    private final By radioButtons = By.xpath("//input[@type='radio']");
    private final By checkboxes = By.xpath("//input[@type='checkbox']");
    private final By openWindowButton = By.id("openwindow");
    private final By openTabButton = By.id("opentab");
    private final By alertInput = By.id("name");
    private final By alertButton = By.id("alertbtn");
    private final By confirmButton = By.id("confirmbtn");
    private final By hideShowInput = By.id("displayed-text");
    private final By hideButton = By.id("hide-textbox");
    private final By showButton = By.id("show-textbox");

    public PracticePageActions(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPracticePage() {
        driver.get("https://rahulshettyacademy.com/AutomationPractice/");
    }

    // Radio Button Methods
    public void selectRadioButtonAndVerify(int index) {
        List<WebElement> radios = driver.findElements(radioButtons);
        if (index >= 0 && index < radios.size()) {
            radios.get(index).click();
        }
    }

    public boolean isRadioButtonSelected(int index) {
        List<WebElement> radios = driver.findElements(radioButtons);
        return index >= 0 && index < radios.size() && radios.get(index).isSelected();
    }

    // Suggestion Box Methods
    public void searchAndSelectSuggestion(String searchText) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(suggestionInput));
        element.clear();
        element.sendKeys(searchText);
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestionsList));
    }

    public boolean verifySuggestionExists(String expected) {
        List<WebElement> suggestions = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(suggestionsList)
        );
        return suggestions.stream()
                .map(WebElement::getText)
                .anyMatch(text -> text.contains(expected));
    }

    // Dropdown Methods
    public void selectFromDropdown(String option) {
        Select dropdown = new Select(wait.until(ExpectedConditions.elementToBeClickable(dropdownSelect)));
        dropdown.selectByVisibleText(option);
    }

    public String getSelectedDropdownOption() {
        Select dropdown = new Select(driver.findElement(dropdownSelect));
        return dropdown.getFirstSelectedOption().getText();
    }

    // Checkbox Methods
    public void selectCheckbox(String index) {
        int idx = Integer.parseInt(index) - 1;
        List<WebElement> boxes = driver.findElements(checkboxes);
        if (!boxes.get(idx).isSelected()) {
            boxes.get(idx).click();
        }
    }

    public void deselectCheckbox(String index) {
        int idx = Integer.parseInt(index) - 1;
        List<WebElement> boxes = driver.findElements(checkboxes);
        if (boxes.get(idx).isSelected()) {
            boxes.get(idx).click();
        }
    }

    public boolean isCheckboxSelected(String index) {
        int idx = Integer.parseInt(index) - 1;
        List<WebElement> boxes = driver.findElements(checkboxes);
        return boxes.get(idx).isSelected();
    }

    // Window Handling Methods
    public void clickOpenWindow() {
        wait.until(ExpectedConditions.elementToBeClickable(openWindowButton)).click();
    }

    public boolean handleNewWindow() {
        String mainWindow = driver.getWindowHandle();
        Set<String> windows = driver.getWindowHandles();

        for (String windowHandle : windows) {
            if (!windowHandle.equals(mainWindow)) {
                try {
                    driver.switchTo().window(windowHandle);
                    driver.close();
                    driver.switchTo().window(mainWindow);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        }
        return false;
    }

    // Tab Handling Methods
    public void clickOpenTab() {
        wait.until(ExpectedConditions.elementToBeClickable(openTabButton)).click();
    }

    public boolean handleNewTab() {
        return handleNewWindow(); // Same logic as window handling
    }

    // Alert Handling Methods
    public void handleAlert(String name) {
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(alertInput));
        nameInput.clear();
        nameInput.sendKeys(name);
        driver.findElement(alertButton).click();
    }

    public void handleConfirm(String name, boolean accept) {
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(alertInput));
        nameInput.clear();
        nameInput.sendKeys(name);
        driver.findElement(confirmButton).click();

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        if (accept) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    public boolean verifyAndAcceptAlert() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Hide/Show Element Methods
    public void toggleElement(boolean show) {
        wait.until(ExpectedConditions.elementToBeClickable(show ? showButton : hideButton)).click();
    }

    public boolean isElementDisplayed() {
        try {
            return driver.findElement(hideShowInput).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    // Mouse Hover Methods
    public void performMouseHover(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        // Implement mouse hover using Actions class
        // Actions actions = new Actions(driver);
        // actions.moveToElement(element).perform();
    }

    // Table Methods
    public List<WebElement> getTableRows(By tableLocator) {
        WebElement table = wait.until(ExpectedConditions.presenceOfElementLocated(tableLocator));
        return table.findElements(By.tagName("tr"));
    }

    // iFrame Methods
    public void switchToIframe(String frameId) {
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameId));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    // Element State Methods
    public boolean isElementEnabled(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).isEnabled();
    }

    public boolean isElementPresent(By locator) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Utility Methods
    public String getElementText(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator)).getText();
    }

    public void waitForElementVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementClickability(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
}