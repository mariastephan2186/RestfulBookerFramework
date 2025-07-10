package Pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Duration TIMEOUT = Duration.ofSeconds(10);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void click(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            WebElement element = driver.findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
            waitForClickable(locator); // wait again after scroll
            try {
                element.click();
            } catch (Exception ex) {
                // If regular click still fails, try JavaScript click
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            }
        }
    }

    protected void type(By locator, String text) {
        WebElement element = waitForElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected String getText(By locator) {
        return waitForElement(locator).getText();
    }

    protected boolean isDisplayed(By locator) {
        try {
            return waitForElement(locator).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    protected void selectDropdownByText(By locator, String text) {
        Select select = new Select(waitForElement(locator));
        select.selectByVisibleText(text);
    }

    protected boolean isSelected(By locator) {
        return waitForElement(locator).isSelected();
    }

    protected void switchToAlert() {
        wait.until(ExpectedConditions.alertIsPresent());
    }

    protected String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    protected void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    protected void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    protected void switchToFrame(WebElement frame) {
        driver.switchTo().frame(frame);
    }

    protected void switchToFrame(int index) {
        driver.switchTo().frame(index);
    }
}
