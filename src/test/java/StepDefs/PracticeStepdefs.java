package StepDefs;

import Actions.PracticeActions;
import StepDefs.Hooks.Hooks;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

@Feature("Practice Page")
public class PracticeStepdefs {
    private WebDriver driver;
    private PracticeActions practicePageActions2;

    public PracticeStepdefs(Hooks hooks) {
        this.driver = hooks.driver; // Use the driver from Hooks
    }

    @Before
    public void setup() {

        practicePageActions2 = new PracticeActions(driver);
        practicePageActions2.openPracticePage();
    }


    @Given("I am on the practice page")
    public void iAmOnThePracticePage() {
        Assert.assertEquals(driver.getTitle(), "Practice Page",
                "Failed to navigate to Practice Page");
        Allure.step("I am on the practice page");
    }

    @When("I type {string} in the suggestion box")
    public void iTypeInTheSuggestionBox(String text) {
        practicePageActions2.searchAndSelectSuggestion(text);
        Allure.step("I type '" + text + "' in the suggestion box");
    }

    @Then("I should see suggestions containing {string}")
    public void iShouldSeeSuggestionsContaining(String expectedText) {
        Assert.assertTrue(practicePageActions2.verifySuggestionExists(expectedText),
                "Expected suggestion containing '" + expectedText + "' was not found");
        Allure.step("I should see suggestions containing '" + expectedText + "'");
    }

    @Then("I should not see suggestions containing the term {string}")
    public void iShouldNotSeeSuggestionsContainingTheTerm(String invalidText) {
        practicePageActions2.performNegativeTest(invalidText);
        Allure.step("I should not see suggestions containing the term '" + invalidText + "'");
        Assert.assertTrue(practicePageActions2.verifySuggestionExists(invalidText),
                "Unexpected suggestion '" + invalidText + "' was found");

    }

    @When("I select {string} from the dropdown")
    public void iSelectFromTheDropdown(String option) {
        practicePageActions2.selectFromDropdown(option);
        Allure.step("I select '" + option + "' from the dropdown");
    }

    @Then("{string} should be selected in the dropdown")
    public void shouldBeSelectedInTheDropdown(String expectedOption) {
        String actualOption = practicePageActions2.getSelectedDropdownOption();
        Assert.assertEquals(actualOption, expectedOption,
                "Expected option '" + expectedOption + "' but found '" + actualOption + "'");
        Allure.step("Expected option '" + expectedOption + "' but found '" + actualOption + "'");
    }


    @When("I select radio button {string}")
    public void iSelectRadioButton(String button) {
        practicePageActions2.selectRadioButtonAndVerify(Integer.parseInt(button) - 1);
        Allure.step("I select radio button '" + button + "'");
    }

    @Then("the radio button {string} should be selected")
    public void theRadioButtonShouldBeSelected(String button) {
        Assert.assertTrue(practicePageActions2.isRadioButtonSelected(Integer.parseInt(button) - 1),
                "Radio button " + button + " is not selected");
        Allure.step("Radio button " + button + " should be selected");

    }

    @When("I select checkbox {string}")
    public void iSelectCheckbox(String checkbox) {
        practicePageActions2.selectCheckbox(checkbox);
    }

    @Then("checkbox {string} should be selected")
    public void checkboxShouldBeSelected(String checkbox) {
        Assert.assertTrue(practicePageActions2.isCheckboxSelected(checkbox),
                "Checkbox " + checkbox + " is not selected");
    }

    @When("I deselect checkbox {string}")
    public void iDeselectCheckbox(String checkbox) {
        practicePageActions2.deselectCheckbox(checkbox);
    }

    @Then("checkbox {string} should not be selected")
    public void checkboxShouldNotBeSelected(String checkbox) {
        Assert.assertFalse(practicePageActions2.isCheckboxSelected(checkbox),
                "Checkbox " + checkbox + " is still selected");

    }

    @When("I click on the Open Window button")
    public void iClickOnTheOpenWindowButton() {
        practicePageActions2.clickOpenWindow();
    }

    @Then("I should be able to handle the new window")
    public void iShouldBeAbleToHandleTheNewWindow() {
        Assert.assertTrue(practicePageActions2.handleNewWindow(),
                "Failed to handle new window");

    }

    @When("I enter {string} and click Alert")
    public void iEnterAndClickAlert(String name) {
        practicePageActions2.handleAlert(name);
    }

    @Then("I should be able to handle the alert")
    public void iShouldBeAbleToHandleTheAlert() {
        Assert.assertTrue(practicePageActions2.verifyAndAcceptAlert(),
                "Failed to handle alert");

    }


    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();


        }
    }


}
