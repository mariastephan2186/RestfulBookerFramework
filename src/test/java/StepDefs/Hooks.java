package StepDefs;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;

public class Hooks {
    private static final Logger log = LoggerFactory.getLogger(Hooks.class);
    public static WebDriver driver;

    // Remove @Inject annotation - PicoContainer will handle injection automatically
    public Hooks() {
    }

    @Before
    public void setUp() {
        // Initialize Actions with the driver
        driver = new ChromeDriver(); // or whatever browser you're using
        driver.manage().window().maximize();
    }
   /*
   //Run browser tests in headless mode
       public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");  // For newer Chrome versions
        options.addArguments("--disable-gpu");   // Recommended for Windows
        options.addArguments("--window-size=1920,1080");  // Set window size
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        BaseStepDefs.Actions.initializeActions(driver);
    }
    */


    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            log.error("Scenario failed: {}", scenario.getName());
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            Allure.addAttachment("Failed Screenshot",
                    new ByteArrayInputStream(screenshot));

        }
        log.info("Finished scenario: {}", scenario.getName());

        // Cleanup logic
        if (driver != null) {
            driver.quit();
        }

    }
}