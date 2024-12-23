package utilities;

import java.io.IOException;

import base.BaseClass;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import io.appium.java_client.AppiumDriver;

public class Listener extends BaseClass implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase Case Execution Started :"+result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase Passed :"+result.getName());

    }


    @Override
    public void onTestFailure(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase Failed :"+result.getName());
        String testMethodName = result.getMethod().getMethodName();
        AppiumDriver driver = null;
        try {
            driver =(AppiumDriver)result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
        } catch(Exception e)
        {
            e.printStackTrace();
        }

        try {
            getScreenShotPath(driver, testMethodName);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // TODO Auto-generated method stub
        System.out.println("Testcase Case Execution Skipped :"+result.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onStart(ITestContext context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onFinish(ITestContext context) {
        // TODO Auto-generated method stub

    }
}
