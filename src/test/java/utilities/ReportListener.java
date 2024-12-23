package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static base.BaseClass.props;

public class ReportListener implements ITestListener {
    DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh-mm-ssaa");
    Date date = new Date();
    String currentDate = dateFormat.format(date);

    DateFormat dateFormatNew = new SimpleDateFormat("dd-MMM-yyyy");
    Date dateNew = new Date();
    String currentDateNew = dateFormatNew.format(dateNew);

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static ExtentSparkReporter htmlReporter;

    @Override
    public void onStart(ITestContext context) {
        // Create report folder based on the current date
        File file = new File(System.getProperty("user.dir") + "\\test-ExtentReport\\" + currentDateNew);
        if (!file.exists()) {
            file.mkdir();
        }

        // Initialize ExtentSparkReporter
        htmlReporter = new ExtentSparkReporter(file + "\\AutomationTestResult " +
                props.getProperty("appName") + "_" + props.getProperty("appVersion") + "_" + currentDate + ".html");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Enable test history and charts
        htmlReporter.config().setTimelineEnabled(true);
        htmlReporter.config().enableOfflineMode(true);  // Helps with charts display when no internet connection
        //htmlReporter.config().setReportHistoryDirectory(System.getProperty("user.dir") + "\\test-ExtentReport\\");

        // Set system information for the report
        extent.setSystemInfo("Platform Name", props.getProperty("platformName"));
        extent.setSystemInfo("Platform Version", props.getProperty("platformVersion"));
        extent.setSystemInfo("Application Name", props.getProperty("appName"));
        extent.setSystemInfo("Application Version", props.getProperty("appVersion"));

        // Set report appearance
        htmlReporter.config().setDocumentTitle("Automation Test Results");
        htmlReporter.config().setReportName("Automation Test Results of: " +
                props.getProperty("appName") + "_" + props.getProperty("appVersion") + ": Android App");
        htmlReporter.config().setTheme(Theme.DARK);  // Set the report theme
    }

    @Override
    public void onFinish(ITestContext context) {
        // Flush the ExtentReports instance
        if (extent != null) {
            extent.flush();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        // Create a new test in ExtentReports
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // Log success status
        test.get().log(Status.PASS, "Test Case Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // Log failure status
        test.get().log(Status.FAIL, "Test Case Failed: " + result.getName());
        test.get().log(Status.FAIL, "Error Details: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // Log skipped status
        test.get().log(Status.SKIP, "Test Case Skipped: " + result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // Not used but required to override
    }

    @Override
    public void onTestFailedWithTimeout(ITestResult result) {
        // Log failure due to timeout
        test.get().log(Status.FAIL, "Test Case Failed due to Timeout: " + result.getName());
    }

    public static ExtentTest getTest() {
        return test.get();
    }
}
