package base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

public class BaseClass {
    public static AppiumDriver driver;
    private static final Logger logger = LoggerFactory.getLogger(BaseClass.class);
    public String appiumServer = "127.0.0.1";
    public int appiumPort = 4723;
    URL appiumURL = null;
    public static Properties props = null;

    public BaseClass() {
        try {
            props = new Properties();
            FileInputStream config = new FileInputStream(System.getProperty("user.dir") + "/src/test/java/com/qa/config/android.properties");
            props.load(config);
        } catch (FileNotFoundException e) {
            logger.error("Property file not found: " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("Error loading properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public AppiumDriver initializeDriver() {
        try {
            if (props == null) {
                logger.error("Properties file not loaded successfully.");
                throw new RuntimeException("Configuration file not found.");
            }

            appiumURL = new URL("http://" + appiumServer + ":" + appiumPort + "/wd/hub");
            this.driver = new AppiumDriver(appiumURL, setAppCapabilitiesAndroid());
        } catch (Exception e) {
            logger.error("Error initializing Appium driver: " + e.getMessage());
            e.printStackTrace();
        }
        return driver;
    }

    public DesiredCapabilities setAppCapabilitiesAndroid() {
        DesiredCapabilities cap = new DesiredCapabilities();
        cap.setCapability("automationName", "UiAutomator2");
        cap.setCapability("platformName", "Android");

        // Handling multiple device types
        String deviceType = props.getProperty("deviceType");
        if ("emulator".equalsIgnoreCase(deviceType)) {
            cap.setCapability("avd", props.getProperty("emulatorName"));
        } else {
            cap.setCapability("udid", props.getProperty("udid"));
        }

        cap.setCapability("platformVersion", props.getProperty("platformVersion"));
        cap.setCapability("fullReset", false);
        cap.setCapability("noReset", false);
        cap.setCapability("app", System.getProperty("user.dir") + "/Applications/" + props.getProperty("apkFileName"));
        cap.setCapability("appPackage", props.getProperty("appPackage"));
        cap.setCapability("appActivity", props.getProperty("appActivity"));
        cap.setCapability("autoGrantPermissions", true);
        cap.setCapability("unicodeKeyboard", true);
        cap.setCapability("resetKeyboard", true);
        return cap;
    }

    public String getScreenShotPath(AppiumDriver driver, String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destinationFile = System.getProperty("user.dir") + "/Screenshots/" + testCaseName + ".png";
        FileUtils.copyFile(source, new File(destinationFile));
        return destinationFile;
    }

    // Method to quit driver and cleanup resources
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
            logger.info("Appium driver stopped successfully.");
        }
    }

    // Ensure the driver is initialized
    public static void initializeDriverOnce() {
        if (driver == null) {
            BaseClass baseClass = new BaseClass();
            baseClass.initializeDriver();
        }
    }

    // After each test class run, stop the driver
    @AfterClass(alwaysRun = true)
    public void tearDown() {
        stopDriver();
    }
}
