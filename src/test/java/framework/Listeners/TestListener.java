package framework.Listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import java.util.logging.Logger;

public class TestListener implements ITestListener {
    private static final Logger LOGGER = Logger.getLogger(TestListener.class.getName());

    @Override
    public void onTestStart(ITestResult result) {
        LOGGER.info("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        LOGGER.info("Test passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LOGGER.severe("Test failed: " + result.getName());
        // Add screenshot capture logic here
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LOGGER.warning("Test skipped: " + result.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        LOGGER.info("Starting test suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        LOGGER.info("Finished test suite: " + context.getName());
    }
}
