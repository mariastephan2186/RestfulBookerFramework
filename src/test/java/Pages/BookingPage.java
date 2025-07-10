
package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BookingPage extends BasePage {
    // Room Details Locators
    private final By roomImage = By.className("room-image");
    private final By roomTitle = By.className("room-title");
    private final By roomType = By.className("room-type");
    private final By roomPrice = By.className("room-price");
    private final By roomDescription = By.className("room-description");
    private final By roomFeatures = By.className("room-features");
    private final By roomAccessibility = By.className("room-accessibility");

    // Booking Form Locators
    private final By firstNameInput = By.xpath("//input[@aria-label='Firstname']");
    private final By lastNameInput = By.xpath("//input[@aria-label='Lastname']");
    private final By emailInput = By.xpath("//input[@aria-label='Email']");
    private final By phoneInput = By.xpath("//input[@aria-label='Phone']");
    private final By checkInInput = By.id("checkin");
    private final By checkOutInput = By.id("checkout");
    private final By bookNowButton = By.xpath("//a[contains(text(),'Book Now')]");
    private final By booknowButton = By.xpath("//a[contains(text(),'Book now')]");
    private final By reserveNowButton = By.xpath("//button[contains(text(),'Reserve Now')]");

    // Calendar Locators
    private final By calendarWidget = By.className("calendar");
    private final By nextMonthButton = By.className("next-month");
    private final By previousMonthButton = By.className("prev-month");

    // Confirmation Elements
    private final By bookingConfirmation = By.className("booking-confirmation");
    private final By bookingReference = By.className("booking-reference");
    private final By errorMessage = By.cssSelector(".alert.alert-danger");

    public BookingPage(WebDriver driver) {
        super(driver);
    }

    // Navigation Methods
    public void navigateToReservation(String roomId, String checkin, String checkout) {
        driver.get(String.format("https://automationintesting.online/reservation/%s?checkin=%s&checkout=%s",
                roomId, checkin, checkout));
    }

    public void clickBookNowButton() {
        click(bookNowButton);
    }

    public void clickBooknowButton() {
        click(booknowButton);
    }

    // Room Information Methods
    public String getRoomTitle() {
        return getText(roomTitle);
    }

    public String getRoomType() {
        return getText(roomType);
    }

    public String getRoomPrice() {
        return getText(roomPrice);
    }

    public String getRoomDescription() {
        return getText(roomDescription);
    }

    public boolean isRoomImageDisplayed() {
        return isDisplayed(roomImage);
    }

    // Booking Form Methods
    public void fillBookingForm(String firstname, String lastname, String email, String phone) {
        type(firstNameInput, firstname);
        type(lastNameInput, lastname);
        type(emailInput, email);
        type(phoneInput, phone);
    }

    public void setDates(String checkinDate, String checkoutDate) {
        type(checkInInput, checkinDate);
        type(checkOutInput, checkoutDate);
    }

    public void submitBooking() {
        click(booknowButton);
    }

    public void scrollToReserveNowButton() {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 1000);");
        waitForElement(reserveNowButton);
    }

    public void clickReserveNowButton() {
        click(reserveNowButton);
    }

    public void clickBookNowButton2() {
        click(bookNowButton);
    }

    // Calendar Methods
    public void clickNextMonth() {
        click(nextMonthButton);
    }

    public void clickPreviousMonth() {
        click(previousMonthButton);
    }

    public void selectDate(String date) {
        click(By.xpath(String.format("//div[@class='calendar']//td[text()='%s']", date)));
    }

    // Validation Methods
    public boolean isBookingConfirmed() {
        return isDisplayed(bookingConfirmation);
    }

    public String getBookingReference() {
        return getText(bookingReference);
    }

    public boolean hasError() {
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage() {
        return getText(errorMessage);
    }

    // Complete Booking Process
    public void completeBooking(String firstName, String lastName, String email, String phone,
                                String checkinDate, String checkoutDate) {
        fillBookingForm(firstName, lastName, email, phone);
        setDates(checkinDate, checkoutDate);
        submitBooking();
    }

    // Validation Helper Methods
    public boolean isFormValid() {
        return !hasError() &&
                !getText(firstNameInput).isEmpty() &&
                !getText(lastNameInput).isEmpty() &&
                !getText(emailInput).isEmpty() &&
                !getText(phoneInput).isEmpty();
    }

    public boolean areDatesValid() {
        return !getText(checkInInput).isEmpty() &&
                !getText(checkOutInput).isEmpty();
    }

}