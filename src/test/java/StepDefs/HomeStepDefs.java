package StepDefs;

import Pages.BookingPage;
import Pages.HomePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Map;

public class HomeStepDefs {
    private final HomePage homePage;
    private final BookingPage bookingPage;
    private final WebDriverWait wait;
    private long pageLoadStart;
    private long pageLoadEnd;

    public HomeStepDefs() {
        homePage = new HomePage(Hooks.driver);
        bookingPage = new BookingPage(Hooks.driver);
        wait = new WebDriverWait(Hooks.driver, Duration.ofSeconds(10));

    }

    @Given("I am on the Restful Booker Platform homepage")
    public void iAmOnTheHomepage() {
        Hooks.driver.get("https://automationintesting.online/");
        Assert.assertEquals(Hooks.driver.getTitle(), "Restful-booker-platform demo",
                "Incorrect page title");
    }

    @Then("the following navigation links should be visible:")
    public void verifyNavigationLinks(DataTable dataTable) {
        List<String> expectedLinks = dataTable.asList();

        // First ensure the page is fully loaded
        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState").equals("complete"));

        // Maximize window to ensure all elements are visible
        Hooks.driver.manage().window().maximize();

        // Wait with more detailed failure information
        try {
            wait.until(driver -> {
                List<String> currentLinks = homePage.getVisibleNavigationLinks();
                System.out.println("Found links: " + currentLinks); // Debug information
                if (currentLinks.isEmpty()) {
                    return false;
                }
                // Compare both size and content
                return currentLinks.size() == expectedLinks.size() &&
                        currentLinks.containsAll(expectedLinks);
            });
        } catch (TimeoutException e) {
            List<String> actualLinks = homePage.getVisibleNavigationLinks();
            throw new AssertionError(
                    String.format("Timeout waiting for navigation links. Expected %d links %s, but found %d links %s",
                            expectedLinks.size(), expectedLinks, actualLinks.size(), actualLinks));
        }

        // Final verification
        List<String> actualLinks = homePage.getVisibleNavigationLinks();
        Assert.assertEquals(actualLinks, expectedLinks, "Navigation links don't match expected values");
        Allure.step("Navigation links displayed: " + actualLinks.toString().replace(",", ",\n").replace("[", "[\n").replace("]", "\n]"));
    }

    @When("I click on each navigation link")
    public void clickEachNavigationLink() {
        homePage.clickAllNavigationLinks();
        Allure.step("Clicked all navigation links");
    }


    @When("I locate the {string} section")
    public void locateSection(String sectionName) {
        Assert.assertTrue(homePage.scrollToSection(sectionName),
                "Could not locate the " + sectionName + " section");
        Allure.step("Scrolled to the " + sectionName + " section");
    }

    @When("I enter the following dates:")
    public void enterDates(DataTable dataTable) {
        Map<String, String> dates = dataTable.asMaps().get(0);
        homePage.enterDates(dates.get("checkin"), dates.get("checkout"));
        Allure.step("Entered dates: " + dates.toString().replace(",", ",\n").replace("{", "{\n").replace("}", "\n}"));
    }

    @When("I click {string} button")
    public void clickButton(String buttonText) {
        homePage.clickButton(buttonText);
        Allure.step("Clicked " + buttonText + " button");
    }

    @Then("I should see available rooms for the selected dates")
    public void verifyAvailableRooms() {
        Assert.assertTrue(homePage.areAvailableRoomsDisplayed(),
                "Available rooms are not displayed");
        Allure.step("Available rooms displayed");
    }

    @When("I click on the check-in date field")
    public void clickCheckInField() {
        homePage.clickCheckInDateField();
        Allure.step("Clicked on the check-in date field");
    }

    @Then("I should not be able to select dates in the past")
    public void verifyPastDatesDisabled() {
        Assert.assertTrue(homePage.arePastDatesDisabled(),
                "Past dates are not disabled in the calendar");
        Allure.step("Past dates disabled in the calendar");
    }

    @When("I select a check-in date")
    public void selectCheckInDate() {
        homePage.selectFirstAvailableDate();
    }

    @Then("the check-out date picker should only allow future dates")
    public void verifyCheckoutDateRestrictions() {
        Assert.assertTrue(homePage.areCheckoutDatesValid(),
                "Invalid checkout dates are selectable");
        Allure.step("Checkout dates restricted to future dates");
    }

    @When("I scroll to the rooms section")
    public void scrollToRoomsSection() {
        homePage.scrollToRoomsSection();
        Allure.step("Scrolled to the rooms section");
    }


    @Then("I should see the booking form with:")
    public void verifyBookingForm(DataTable dataTable) {
        List<String> expectedFields = dataTable.asList(String.class);
        Assert.assertTrue(homePage.areAllFormFieldsPresent(expectedFields),
                "Booking form is missing required fields");
        Allure.step("Booking form displayed with required fields: " + expectedFields.toString().replace(",", ",\n").replace("[", "[\n").replace("]", "\n]"));
    }

    @Then("I should see the calendar widget")
    public void verifyCalendarWidget() {
        Assert.assertTrue(homePage.isCalendarWidgetDisplayed(),
                "Calendar widget is not displayed");
        Allure.step("Calendar widget displayed");
    }

    @When("I view the calendar on different screen sizes:")
    public void setScreenSizes(DataTable dataTable) {
        List<String> devices = dataTable.asList(String.class);
        for (String device : devices) {
            Dimension dimension = getDeviceDimension(device);
            Hooks.driver.manage().window().setSize(dimension);
            Assert.assertTrue(homePage.isCalendarProperlyFormatted(),
                    "Calendar is not properly formatted on " + device);
        }
        Allure.step("Calendar displayed on different screen sizes: " + devices.toString().replace(",", ",\n").replace("[", "[\n").replace("]", "\n]"));
    }

    private Dimension getDeviceDimension(String device) {
        return switch (device.toLowerCase()) {
            case "mobile" -> new Dimension(375, 812);  // iPhone X
            case "tablet" -> new Dimension(768, 1024); // iPad
            default -> new Dimension(1920, 1080);      // desktop
        };
    }

    @Then("I should see a message indicating no availability")
    public void verifyNoAvailabilityMessage() {
        Assert.assertTrue(homePage.isNoAvailabilityMessageDisplayed(),
                "No availability message is not displayed");
    }

    @Then("I should be offered alternative dates")
    public void verifyAlternativeDates() {
        Assert.assertTrue(homePage.areAlternativeDatesOffered(),
                "Alternative dates are not offered");
    }


    @Then("I should see validation messages for required fields")
    public void iShouldSeeValidationMessages() {
        Assert.assertTrue(homePage.areValidationErrorsDisplayed());
    }


    @When("I click the submit button without any entry")
    public void iClickTheSubmitButtonWithoutAnyEntry() {
        homePage.scrollToContactForm();
        homePage.clickSubmit();
        Allure.step("Submit button clicked without any entry");
    }

    @Then("I should see at least one room card displayed")
    public void iShouldSeeAtLeastOneRoomCardDisplayed() {
        Assert.assertTrue(homePage.isRoomCardDisplayed(),
                "No room cards are displayed on the page");
        Allure.step("Room card displayed");
    }

    @Then("each room card should contain {string} button")
    public void eachRoomCardShouldContainButton(String buttonText) {
        Assert.assertTrue(homePage.doAllRoomCardsContainButton(buttonText),
                "Not all room cards contain the " + buttonText + " button");
        Allure.step("Room cards contain " + buttonText + " button");
    }


    @When("I measure the page load time")
    public void measurePageLoadTime() {
        pageLoadStart = System.currentTimeMillis();
        Hooks.driver.navigate().refresh();
        // Wait for document.readyState to be complete
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(10)).until(
                webDriver -> ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState").equals("complete"));
        pageLoadEnd = System.currentTimeMillis();
        Allure.step("Page load time measured");
    }

    @Then("the page should load within {int} seconds")
    public void verifyPageLoadTime(int expectedSeconds) {
        long actualLoadTime = pageLoadEnd - pageLoadStart;
        Assert.assertTrue(actualLoadTime < expectedSeconds * 1000,
                String.format("Page load time of %dms exceeded expected %dms",
                        actualLoadTime, expectedSeconds * 1000));
        Allure.step("Page load time within expected range: " + actualLoadTime + "ms");
    }


    @Then("the Time to First Byte \\(TTFB) should be less than {int}ms")
    public void verifyTTFB(int expectedTTFB) {
        JavascriptExecutor js = (JavascriptExecutor) Hooks.driver;
        Long ttfb = (Long) js.executeScript(
                "return window.performance.timing.responseStart - window.performance.timing.navigationStart;");

        Assert.assertTrue(ttfb < expectedTTFB,
                String.format("TTFB of %dms exceeded expected %dms", ttfb, expectedTTFB));
        Allure.step("TTFB within expected range: " + ttfb + "ms");
    }

}
