package StepDefs;

import Pages.BookingPage;
import Pages.HomePage;
import Utils.ConfigReader;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.testng.Assert;

import java.util.Map;

public class BookingStepDefs {
    private final HomePage homePage;
    private final BookingPage BookingPage;

    public BookingStepDefs() {
        homePage = new HomePage(Hooks.driver);
        BookingPage = new BookingPage(Hooks.driver);
    }


    @When("I fill in the contact form with following details:")
    public void iFillInContactForm(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        homePage.fillContactForm(
                data.get("name"),
                data.get("email"),
                data.get("phone"),
                data.get("subject"),
                data.get("message")
        );
        Allure.step("Contact form filled with: " + data.toString().replace(",", ",\n").replace("{", "{\n").replace("}", "\n}"));
    }

    @When("I click the submit button")
    public void iClickSubmitButton() {
        homePage.clickSubmit();
        Allure.step("Submit button clicked");
    }

    @Then("I should see the message {string}")
    public void iShouldSeeMessage(String message) {
        Assert.assertTrue(homePage.isSuccessMessageDisplayed());
        Allure.step("Success message displayed: " + message);
    }


    @When("I fill in the booking form with following details:")
    public void iFillInBookingForm(DataTable dataTable) {
        BookingPage.scrollToReserveNowButton();
        BookingPage.clickReserveNowButton();
        Map<String, String> data = dataTable.asMaps().get(0);
        String email = resolveConfigValue(data.get("email"));
        String phone = resolveConfigValue(data.get("phone"));
        String firstName = resolveConfigValue(data.get("firstname"));
        String lastName = resolveConfigValue(data.get("lastname"));
        BookingPage.fillBookingForm(
                firstName,
                lastName,
                email,
                phone
        );
        Allure.step("Booking form filled with: " + data.toString().replace(",", ",\n").replace("{", "{\n").replace("}", "\n}"));
    }

    private String resolveConfigValue(String value) {
        if (value != null && value.startsWith("${") && value.endsWith("}")) {
            String key = value.substring(2, value.length() - 1);
            return ConfigReader.getProperty("test." + key.toLowerCase());
        }
        return value;

    }

    @When("I select check-in date")
    public void iSelectCheckInDate() {
        BookingPage.setDates("2025-07-10", "2025-07-15");
        Allure.step("Check-in date selected: 2025-07-10 - 2025-07-15");
    }

    @When("I click the book button")
    public void iClickBookButton() {
        BookingPage.clickBookNowButton();
        BookingPage.clickBooknowButton();
        Allure.step("Book button clicked");
    }


    @When("I complete the reservation form")
    public void completeReservationForm(DataTable dataTable) {
        Map<String, String> data = dataTable.asMaps().get(0);
        BookingPage.completeBooking(
                data.get("firstName"),
                data.get("lastName"),
                data.get("email"),
                data.get("phone"),
                data.get("checkinDate"),
                data.get("checkoutDate")
        );
        Allure.step("Reservation form completed with: " + data.toString().replace(",", ",\n").replace("{", "{\n").replace("}", "\n}"));
    }

    @Then("I should see the booking confirmation")
    public void verifyBookingConfirmation() {
        Assert.assertTrue(BookingPage.isBookingConfirmed());
        Assert.assertNotNull(BookingPage.getBookingReference());
        Allure.step("Booking confirmation displayed");
    }

    @And("I click the Reserve Now button")
    public void iClickTheReserveNowButton() {
        BookingPage.clickReserveNowButton();
        Allure.step("Reserve Now button clicked");
    }
}