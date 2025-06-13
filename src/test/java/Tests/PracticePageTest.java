package Tests;


import Actions.PracticePageActions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class PracticePageTest {
    private WebDriver driver;
    private PracticePageActions practicePageActions;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        practicePageActions = new PracticePageActions(driver);
    }

    @BeforeMethod
    public void navigateToPage() {
        practicePageActions.openPracticePage();
    }

    @Test
    public void testRadioButtons() {
        practicePageActions.selectRadioButtonAndVerify(0);
    }

    @Test
    public void testSuggestionBox() {
        practicePageActions.searchAndSelectSuggestion("Ind");
        practicePageActions.verifySuggestionExists("India");
        practicePageActions.searchAndSelectSuggestion("USA");
        practicePageActions.verifySuggestionExists("United States of America");
    }

    @Test
    public void testDropdown() {
        practicePageActions.selectFromDropdown("Option1");
        practicePageActions.getSelectedDropdownOption();

    }

    @Test
    public void testCheckboxes() {
        practicePageActions.selectCheckbox("1");
        practicePageActions.isCheckboxSelected("1");
        practicePageActions.deselectCheckbox("1");
        practicePageActions.isCheckboxSelected("1");
    }

    @Test
    public void testWindowHandling() {
        practicePageActions.handleNewWindow();
        practicePageActions.openPracticePage();
    }

    @Test
    public void testAlertHandling() {
        practicePageActions.handleAlert("Accept");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}