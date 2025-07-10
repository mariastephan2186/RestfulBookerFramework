package Utils;

import java.util.Properties;

public class TestContext {
    private static final Properties testData = new Properties();

    static {
        testData.put("FIRSTNAME", ConfigReader.getProperty("test.firstname").trim().replaceAll("\\s+", " "));
        testData.put("LASTNAME", ConfigReader.getProperty("test.lastname").trim().replaceAll("\\s+", " "));
        testData.put("EMAIL", ConfigReader.getProperty("test.email"));
        testData.put("PHONE", ConfigReader.getProperty("test.phone"));
    }

    public static String getTestData(String key) {
        return testData.getProperty(key);
    }
}