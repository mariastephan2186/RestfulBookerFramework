package Pages;

import StepDefs.Hooks;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class HomePage extends BasePage {
    private final By nameField = By.id("name");
    private final By emailField = By.id("email");
    private final By phoneField = By.id("phone");
    private final By subjectField = By.id("subject");
    private final By messageField = By.xpath("//textarea[@data-testid='ContactDescription' and @id='description']");
    private final By submitButton = By.xpath("//button[contains(text(),'Submit')]");
    private final By successMessage = By.xpath("//section[@id='contact']");
    private final By nameError = By.id("name-error");
    private final By emailError = By.id("email-error");
    private final By navigationLinks = By.xpath("//div[@id='navbarNav']//a");
    private final By availableRooms = By.className("room-card");
    private final By calendarContainer = By.className("react-datepicker-popper");
    private final By calendarDayNames = By.className("react-datepicker__day-name");
    private final By calendarWidget = By.className("react-datepicker");
    private final By bookingForm = By.id("booking-form");
    private final By bookNowButton = By.cssSelector("button.book-now");
    private final By checkInDateField = By.xpath("//label[@for='checkin' and @class='form-label' and text()='Check In']");
    private final By checkOutDateField = By.xpath("//label[@for='checkout' and @class='form-label' and text()='Check Out']");


    public HomePage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillContactForm(String name, String email, String phone, String subject, String message) {
        type(nameField, name);
        type(emailField, email);
        type(phoneField, phone);
        type(subjectField, subject);
        type(messageField, message);
    }

    public void clickSubmit() {
        wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        click(submitButton);
    }

    public boolean isSuccessMessageDisplayed() {
        return isDisplayed(successMessage);
    }

    public boolean areValidationErrorsDisplayed() {
        return isDisplayed(nameError) || isDisplayed(emailError);
    }

    public List<String> getVisibleNavigationLinks() {
        return driver.findElements(navigationLinks)
                .stream()
                .filter(WebElement::isDisplayed)
                .map(WebElement::getText)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .collect(Collectors.toList());
    }

    public void clickAllNavigationLinks() {
        navigationLinks.findElements(driver)
                .forEach(link -> {
                    link.click();
                    waitForPageLoad();
                });
    }

    private void waitForPageLoad() {
        try {
            wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState").equals("complete"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Page load timeout", e);
        }
    }

    public boolean scrollToSection(String sectionName) {
        WebElement section = driver.findElement(
                By.xpath(String.format("//section[contains(.,'%s')]", sectionName)));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", section);
        return section.isDisplayed();
    }

    public void scrollToContactForm() {
        try {
            WebElement contactSection = Hooks.driver.findElement(By.xpath("//*[contains(@id,'contact')]"));
            ((JavascriptExecutor) Hooks.driver).executeScript("arguments[0].scrollIntoView(true);", contactSection);
            wait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOf(contactSection),
                    ExpectedConditions.elementToBeClickable(contactSection)
            ));
            if (!contactSection.isDisplayed()) {
                throw new ElementNotInteractableException("Contact section is not visible after scroll");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to interact with contact form after scroll: " + e.getMessage(), e);
        }

    }

    public boolean areAvailableRoomsDisplayed() {
        return !driver.findElements(availableRooms).isEmpty();
    }


    public void enterDates(String checkin, String checkout) {
        // Use JavaScript to set the values since the fields might be readonly
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set check-in date
        WebElement checkinElement = waitForElement(By.cssSelector("input[name='checkin']"));
        js.executeScript("arguments[0].value = arguments[1]", checkinElement, checkin);

        // Set check-out date
        WebElement checkoutElement = waitForElement(By.cssSelector("input[name='checkout']"));
        js.executeScript("arguments[0].value = arguments[1]", checkoutElement, checkout);

        // Trigger change event to ensure the application recognizes the new values
        js.executeScript("arguments[0].dispatchEvent(new Event('change'))", checkinElement);
        js.executeScript("arguments[0].dispatchEvent(new Event('change'))", checkoutElement);
    }


    public boolean areAllFormFieldsPresent(List<String> expectedFields) {
        WebElement form = driver.findElement(bookingForm);
        return expectedFields.stream()
                .allMatch(field -> form.findElement(
                                By.xpath(String.format(".//input[@placeholder='%s']", field)))
                        .isDisplayed());
    }

    public boolean isCalendarProperlyFormatted() {
        return isDisplayed(calendarWidget) &&
                driver.findElement(calendarWidget).getCssValue("display")
                        .equals("grid");
    }

    public boolean isCalendarWidgetDisplayed() {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(calendarContainer));
            // Then wait for the actual calendar widget and day names to be visible
            boolean isWidgetVisible = wait.until(ExpectedConditions.and(
                    ExpectedConditions.visibilityOfElementLocated(calendarWidget),
                    ExpectedConditions.visibilityOfElementLocated(calendarDayNames)
            ));

            WebElement calendar = driver.findElement(calendarWidget);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", calendarWidget);
            // Add a small pause to allow the scroll to complete
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            // Wait for the calendar to be both present and visible
            return isWidgetVisible && calendar.isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            System.out.println("Calendar widget verification failed: " + e.getMessage());
            return false;
        }
    }

    public void scrollToRoomsSection() {
        WebElement roomsSection = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("section#rooms.section-divider")));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView(true);", roomsSection);
    }

    public boolean areCheckoutDatesValid() {
        List<WebElement> checkoutDates = driver.findElements(
                By.cssSelector(".calendar-day"));
        return checkoutDates.stream()
                .filter(date -> !date.getAttribute("class").contains("disabled"))
                .allMatch(date -> {
                    // Add your date validation logic here
                    return true;
                });
    }

    public boolean arePastDatesDisabled() {
        List<WebElement> pastDates = driver.findElements(
                By.cssSelector(".calendar-day.past"));
        return pastDates.stream().allMatch(date ->
                date.getAttribute("class").contains("disabled"));
    }

    public void selectFirstAvailableDate() {
        WebElement firstAvailableDate = driver.findElement(
                By.cssSelector(".calendar-day:not(.disabled)"));
        click(By.cssSelector(".calendar-day:not(.disabled)"));
    }

    public void clickCheckInDateField() {
        click(checkInDateField);
    }


    public void clickButton(String buttonText) {
        By buttonLocator = By.xpath(String.format("//button[contains(text(),'%s')]", buttonText));
        click(buttonLocator);
    }


    public boolean isNoAvailabilityMessageDisplayed() {
        return false;
    }

    public boolean areAlternativeDatesOffered() {
        return false;
    }

    public boolean isRoomCardDisplayed() {
        try {
            return waitForElement(availableRooms).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }

    public boolean doAllRoomCardsContainButton(String buttonText) {
        List<WebElement> roomCards = driver.findElements(availableRooms);
        return roomCards.stream().allMatch(card -> {
            try {
                By buttonLocator = By.xpath(".//button[contains(text(),'" + buttonText + "')]");
                return card.findElement(buttonLocator).isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
        });

    }

    public void goToBookingSection() {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 50)");
            Thread.sleep(500);
        } catch (Exception ignored) {
        }
    }
}
