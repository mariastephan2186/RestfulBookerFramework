package Tests;


import Actions.PracticeActions;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class PracticePageTest {
    private WebDriver driver;
    private PracticeActions practiceActions;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        practiceActions = new PracticeActions(driver);
    }

    @BeforeMethod
    public void navigateToPage() {
        practiceActions.openPracticePage();
    }

    @Test
    public void testRadioButtons() {
        practiceActions.selectRadioButtonAndVerify(0);
    }

    @Test
    public void testSuggestionBox() {
        practiceActions.searchAndSelectSuggestion("Ind");
        practiceActions.verifySuggestionExists("India");
        practiceActions.searchAndSelectSuggestion("USA");
        practiceActions.verifySuggestionExists("United States of America");
    }

    @Test
    public void testDropdown() {
        practiceActions.selectFromDropdown("Option1");
        practiceActions.getSelectedDropdownOption();

    }

    @Test
    public void testCheckboxes() {
        practiceActions.selectCheckbox("1");
        practiceActions.isCheckboxSelected("1");
        practiceActions.deselectCheckbox("1");
        practiceActions.isCheckboxSelected("1");
    }

    @Test
    public void testWindowHandling() {
        practiceActions.handleNewWindow();
        practiceActions.openPracticePage();
    }

    @Test
    public void testAlertHandling() {
        practiceActions.handleAlert("Accept");
    }

    @Test
    public void testSuggestionBoxNegative() {
        // Test invalid country name
        practiceActions.performNegativeTest("XYZABC");

        // Test with special characters
        practiceActions.performNegativeTest("@#$%");

        // Test with numbers
        practiceActions.performNegativeTest("123456");

        // Test with empty input
        practiceActions.performNegativeTest("");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}