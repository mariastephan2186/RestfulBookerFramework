package StepDefs;

import Actions.PracticeActions;
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
    private PracticeActions practicePageActions2;

    @Before
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        practicePageActions2 = new PracticeActions(driver);
    }

    @Given("I am on the practice page")
    public void iAmOnThePracticePage() {
        practicePageActions2.openPracticePage();
    }

    @When("I type {string} in the suggestion box")
    public void iTypeInTheSuggestionBox(String text) {
        practicePageActions2.searchAndSelectSuggestion(text);
    }

    @Then("I should see suggestions containing {string}")
    public void iShouldSeeSuggestionsContaining(String expectedText) {
        Assert.assertTrue(practicePageActions2.verifySuggestionExists(expectedText),
                "Expected suggestion containing '" + expectedText + "' was not found");
    }

    @Then("I should not see suggestions containing the term {string}")
    public void iShouldNotSeeSuggestionsContainingTheTerm(String invalidText) {
        practicePageActions2.performNegativeTest(invalidText);

    }

    @When("I select {string} from the dropdown")
    public void iSelectFromTheDropdown(String option) {
        practicePageActions2.selectFromDropdown(option);
    }

    @Then("{string} should be selected in the dropdown")
    public void shouldBeSelectedInTheDropdown(String expectedOption) {
        String  actualOption = practicePageActions2.getSelectedDropdownOption();
        Assert.assertEquals(actualOption, expectedOption,
                "Expected option '" + expectedOption + "' but found '" + actualOption + "'");
    }


    @When("I select radio button {string}")
    public void iSelectRadioButton(String button) {
        practicePageActions2.selectRadioButtonAndVerify(Integer.parseInt(button) - 1);
    }

    @Then("the radio button {string} should be selected")
    public void theRadioButtonShouldBeSelected(String button) {
        Assert.assertTrue(practicePageActions2.isRadioButtonSelected(Integer.parseInt(button) - 1),
                "Radio button " + button + " is not selected");

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
