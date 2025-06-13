package StepDefs;

import Actions.PracticePageActions;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class PracticeStepdefs {
    private WebDriver driver;
    private PracticePageActions practicePageActions;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        practicePageActions = new PracticePageActions(driver);
    }

    @Given("I am on the practice page")
    public void iAmOnThePracticePage() {
        practicePageActions.openPracticePage();
    }

    @When("I type {string} in the suggestion box")
    public void iTypeInTheSuggestionBox(String text) {
        practicePageActions.searchAndSelectSuggestion(text);
    }

    @Then("I should see suggestions containing {string}")
    public void iShouldSeeSuggestionsContaining(String expectedText) {
        Assert.assertTrue(practicePageActions.verifySuggestionExists(expectedText),
                "Expected suggestion containing '" + expectedText + "' was not found");
    }

    @When("I select {string} from the dropdown")
    public void iSelectFromTheDropdown(String option) {
        practicePageActions.selectFromDropdown(option);
    }

    @Then("{string} should be selected in the dropdown")
    public void shouldBeSelectedInTheDropdown(String expectedOption) {
        String actualOption = practicePageActions.getSelectedDropdownOption();
        Assert.assertEquals(actualOption, expectedOption,
                "Expected option '" + expectedOption + "' but found '" + actualOption + "'");
    }

    @When("I select radio button {string}")
    public void iSelectRadioButton(String button) {
        practicePageActions.selectRadioButtonAndVerify(Integer.parseInt(button) - 1);
    }

    @Then("the radio button {string} should be selected")
    public void theRadioButtonShouldBeSelected(String button) {
        Assert.assertTrue(practicePageActions.isRadioButtonSelected(Integer.parseInt(button) - 1),
                "Radio button " + button + " is not selected");
    }

    @When("I select checkbox {string}")
    public void iSelectCheckbox(String checkbox) {
        practicePageActions.selectCheckbox(checkbox);
    }

    @Then("checkbox {string} should be selected")
    public void checkboxShouldBeSelected(String checkbox) {
        Assert.assertTrue(practicePageActions.isCheckboxSelected(checkbox),
                "Checkbox " + checkbox + " is not selected");
    }

    @When("I deselect checkbox {string}")
    public void iDeselectCheckbox(String checkbox) {
        practicePageActions.deselectCheckbox(checkbox);
    }

    @Then("checkbox {string} should not be selected")
    public void checkboxShouldNotBeSelected(String checkbox) {
        Assert.assertFalse(practicePageActions.isCheckboxSelected(checkbox),
                "Checkbox " + checkbox + " is still selected");
    }

    @When("I click on the Open Window button")
    public void iClickOnTheOpenWindowButton() {
        practicePageActions.clickOpenWindow();
    }

    @Then("I should be able to handle the new window")
    public void iShouldBeAbleToHandleTheNewWindow() {
        Assert.assertTrue(practicePageActions.handleNewWindow(),
                "Failed to handle new window");
    }

    @When("I enter {string} and click Alert")
    public void iEnterAndClickAlert(String name) {
        practicePageActions.handleAlert(name);
    }

    @Then("I should be able to handle the alert")
    public void iShouldBeAbleToHandleTheAlert() {
        Assert.assertTrue(practicePageActions.verifyAndAcceptAlert(),
                "Failed to handle alert");
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
