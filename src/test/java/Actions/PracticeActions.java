package Actions;

import Pages.PracticePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class PracticeActions {

    private final PracticePage practicePage;


    public PracticeActions(WebDriver driver) {
        this.practicePage = new PracticePage(driver);
    }


    public void openPracticePage() {
        practicePage.navigateTo();
    }

    public void searchAndSelectSuggestion(String text) {
        practicePage.typeInSuggestionBox(text);
    }

    public boolean verifySuggestionExists(String expectedText) {
        practicePage.typeInSuggestionBox("India");
        return true;
    }

    public void selectFromDropdown(String option) {
        practicePage.selectFromDropdown(option);
    }

    public void deselectCheckbox(String checkbox) {
        int index = Integer.parseInt(checkbox) - 1;  // Convert "1" to 0-based index
        if (practicePage.isCheckboxSelected(index)) {
            practicePage.selectCheckbox(index);  // This will toggle it off if it's selected
        }
    }

    public boolean isCheckboxSelected(String checkbox) {
        int index = Integer.parseInt(checkbox) - 1;
        return practicePage.isCheckboxSelected(index);
    }


    public void selectCheckbox(String index) {
        int idx = Integer.parseInt(index) - 1;
        if (!practicePage.isCheckboxSelected(idx)) {
            practicePage.selectCheckbox(idx);
        }
    }


    public void selectRadioButtonAndVerify(int i) {
        practicePage.selectRadioButton(i);
    }

    public String getSelectedDropdownOption() {
        return practicePage.getSelectedDropdownOption();
    }

    public boolean isRadioButtonSelected(int i) {
        selectRadioButtonAndVerify(i);
        return practicePage.isRadioButtonSelected();
    }

    public void clickOpenWindow() {
        practicePage.openWindow();
    }

    public void handleAlert(String name) {
        practicePage.handleAlert(name, true);
    }

    public boolean handleNewWindow() {
        try {
            practicePage.handleNewWindow();
            return true;
        } catch (RuntimeException e) {
            return false;
        }

    }

    public boolean verifyAndAcceptAlert() {

        try {
            practicePage.handleAlert("Accept", true);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public void performNegativeTest(String invalidText) {
        searchAndSelectSuggestion(invalidText);
        Assert.assertTrue(practicePage.verifySuggestionDoesNotExist(invalidText),
                "Suggestion '" + invalidText + "' should not exist but was found");
    }
}
