package base;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.HidesKeyboard;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

public class ActionClass extends BaseClass {
    protected void waitForVisibilityOfElement(WebElement locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    protected void waitForVisibilityOf(By locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // This method will wait for clickable element, waiting time maximum 30 seconds
    protected void waitForClickabilityOfElement(WebElement locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected void waitForClickabilityOf(By locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    // This method will wait for visibility of element, waiting time maximum 60
    // seconds
    protected void waitForDiscoveryOfWe(WebElement locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(locator));
    }

    protected void waitForDiscoveryOf(By locator) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /************* Declare and initialise a fluent wait <selenium 4> ***********/
    protected void waitForPresenceOfElement(By locator) {
        FluentWait<AppiumDriver> wait=new FluentWait<>(driver) // Use AndroidDriver directly
                .pollingEvery(Duration.ofMillis(15)).ignoring(NoSuchElementException.class)
                .withTimeout(Duration.ofSeconds(50000));
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForPresenceOfElement(WebElement element) {
        FluentWait<AppiumDriver> wait=new FluentWait<>(driver).pollingEvery(Duration.ofMillis(15))
                .ignoring(NoSuchElementException.class).withTimeout(Duration.ofSeconds(30000));
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // This method perform swipe action Horizontally
    public static void swipeHorizontal(AppiumDriver driver, double startPercentage, double finalPercentage,
                                       double anchorPercentage, int duration) throws Exception {
        Dimension size=driver.manage().window().getSize();
        int anchor=(int) (size.height * anchorPercentage);
        int startPoint=(int) (size.width * startPercentage);
        int endPoint=(int) (size.width * finalPercentage);

        // Calculate swipe distance based on screen size
        int swipeDistance=endPoint - startPoint;

        new Actions(driver).moveByOffset(swipeDistance, 0) // Move horizontally by calculated distance
                .pause(Duration.ofMillis(duration)) // Use pause for the desired duration
                .release().perform();
    }

    // This method perform swipe action Vertically //**
    // swipeVertical(driver,0.9,0.01,0.5,3000); //

    public void swipeVertical(AppiumDriver driver, double startPercentage, double finalPercentage,
                              double anchorPercentage, int duration) throws Exception {
        Dimension size=driver.manage().window().getSize();
        int anchor=(int) (size.width * anchorPercentage);
        int startPoint=(int) (size.height * startPercentage);
        int endPoint=(int) (size.height * finalPercentage);

        // Calculate swipe distance based on screen size
        int swipeDistance=endPoint - startPoint;

        new Actions(driver).moveByOffset(0, swipeDistance) // Move vertically by calculated distance
                .pause(Duration.ofMillis(duration)) // Introduce a pause before releasing
                .release().perform();
    }

    // This method perform Hide keyboard action

    public void hideKeyBoard() {
        ((AndroidDriver) driver).pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }

    /**
     * method to go back by Android Native back click
     */

    public void acceptAlert() {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    /**
     * method to scroll down on screen from java-client 6
     *
     * @param swipeTimes       number of times swipe operation should work
     * @param durationForSwipe time duration of a swipe operation
     */
    public void scrollMiddleDown(int swipeTimes, int durationForSwipe) {
        Dimension dimension=driver.manage().window().getSize();

        for (int i=0; i < swipeTimes; i++) {
            // Calculate swipe distance and midpoint
            int swipeDistance=(int) (dimension.getHeight() * 0.5 - dimension.getHeight() * 0.2);
            int midpoint=(int) (dimension.getHeight() / 2.0);

            // Perform vertical swipe using W3C Actions
            new Actions(driver).moveByOffset(0, swipeDistance) // Move vertically by calculated distance
                    .pause(Duration.ofMillis(durationForSwipe)) // Pause before releasing
                    .release().perform();
        }
    }

    public static void scrollToText(AndroidDriver driver, String text) {
        // Use AppiumBy for scroll action
        By scroll= AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                + "new UiSelector().text(\"" + text + "\"));");

        // Find the element using the scroll action
        WebElement eleText=driver.findElement(scroll);
    }

    public static void scrollToId(AndroidDriver driver, String id) {
        // Use AppiumBy for scroll action
        By scroll=AppiumBy.androidUIAutomator("new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                + "new UiSelector().resourceIdMatches(\"" + id + "\"));");

        // Find the element using the scroll action
        WebElement eleId=driver.findElement(scroll);
    }

    /**
     * New Methods for KB App
     **/

    public void enterValue(WebElement targetElement, String content) {
        try {
            waitForVisibilityOfElement(targetElement); // Adjust timeout as needed

            targetElement.sendKeys(content);
            System.err.println("Value entered  successfully: " + targetElement);


        } catch (NoSuchElementException e) {
            System.err.println("Element not found: " + targetElement + " - " + e.getMessage());
        } catch (ElementNotInteractableException e) {
            System.err.println("Element not interactable: " + targetElement + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error setting content: " + e.getMessage());
        }
    }

    public void clearTextField(WebElement targetElement) {
        waitForPresenceOfElement(targetElement); // Assuming this function exists
        targetElement.clear();
    }

    public void clickElement(WebElement targetElement) {
        try {
            waitForDiscoveryOfWe(targetElement); // Assuming this function exists
            targetElement.click();
            System.out.println("Element clicked successfully: " + targetElement);
        } catch (Exception e) {
            System.err.println("Error clicking element: " + targetElement + " - " + e.getMessage());
            // Handle specific exceptions here for better diagnostics
        }
    }


    public String getElementText(WebElement elementLocator) {
        try {
            WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50)); // Adjust timeout as needed
            WebElement element=wait.until(ExpectedConditions.visibilityOf(elementLocator));
            return element.getText();
        } catch (Exception e) {
            System.err.println("Error getting element text: " + elementLocator + " - " + e.getMessage());
            // Optionally handle specific exceptions (refer to previous responses for
            // examples)
            return ""; // Or handle it differently based on your needs
        }
    }

    public boolean isElementVisiblePageLoad(WebElement elementLocator) {
        try {
            waitForPresenceOfElement(elementLocator);
            return true;
        } catch (TimeoutException e) {
            // Log or handle TimeoutException as needed
            System.out.println("Not visible: " + elementLocator + " - " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.out.println("Not visible: " + elementLocator);
            // Log or handle other exceptions as needed
            return false;
        }
    }

    public boolean isElementVisible(WebElement elementLocator) {
        try {
            //waitForPresenceOfElement(elementLocator);
            waitForDiscoveryOfWe(elementLocator);
            return true;
        } catch (TimeoutException e) {
            // Log or handle TimeoutException as needed
            System.out.println("Not visible: " + elementLocator);
            return false;
        } catch (Exception e) {
            System.out.println("Not visible: " + elementLocator);
            // Log or handle other exceptions as needed
            return false;
        }
    }

    public static List<String> getAllTextLabels() {
        // Combine locators for both TextView and EditText using OR operator
        By textElementsLocator=By.xpath("//*[self::android.widget.TextView or self::android.widget.EditText]");

        List<WebElement> textElements=driver.findElements(textElementsLocator);
        List<String> textLabels=new ArrayList<>();

        for (WebElement element : textElements) {
            // Extract text only if element is displayed
            if (element.isDisplayed()) {
                textLabels.add(element.getText());
            }
        }

        return textLabels;
    }

    public boolean isToastMessagePresent(String toastMessage) {
        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(50)); // Adjust timeout as needed
        // Use XPath to locate Toast messages with dynamic text
        By toastLocator=By.xpath("//*[@class='android.widget.TextView' and contains(@text, '" + toastMessage + "')]");
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(toastLocator));
            System.out.println("Toast message '" + toastMessage + "' found.");
            return true;
        } catch (TimeoutException e) {
            System.out.println("Toast message not found within timeout.");
            return false;
        }
    }

    /**
     * Using Appium driver according to new version Methods
     **/

    public void wait(int seconds) throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(50)); // Sets implicit wait for 10 seconds
    }

    public void tapByCoordinates(int x, int y) {
        PointerInput input=new PointerInput(PointerInput.Kind.TOUCH, "finger1");
        Sequence tap=new Sequence(input, 1);
        tap.addAction(input.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(input.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(input.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

    public void scrollDownAct(int swipeTimes, int durationForSwipe) {
        final var finger=new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var start=new Point(314, 1269);
        var end=new Point(250, 353);
        var swipe=new Sequence(finger, swipeTimes);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationForSwipe),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public void scrollUpAct(int swipeTimes, int durationForSwipe) {
        final var finger=new PointerInput(PointerInput.Kind.TOUCH, "finger");
        var start=new Point(250, 353);  // Start from a lower point on the screen
        var end=new Point(314, 1269);   // End at a higher point on the screen
        var swipe=new Sequence(finger, swipeTimes);
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
                PointerInput.Origin.viewport(), start.getX(), start.getY()));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(durationForSwipe),
                PointerInput.Origin.viewport(), end.getX(), end.getY()));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(swipe));
    }

    public void datepickedob(String childagemonth) {
        Scanner scanner=new Scanner(childagemonth);
        String ages; // To store user input for age
        // Prompt user for combined age input
        System.out.print("Enter age in the format 'XyXm' (e.g., 5y1m): ");
        ages=scanner.nextLine();

        // ages = childagemonth;
        LocalDate today=LocalDate.now();
        // Extract years and months from the input string
        int years=0;
        int months=0;
        try {
            String[] parts=ages.split("[ym]"); // Split based on "y" or "m"
            years=Integer.parseInt(parts[0]);
            months=Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid age format. Please use 'XyXm'.");
            scanner.close();
            return; // Exit the program on invalid input
        }
        // Calculate year of birth based on years
        int currentYear=today.getYear();
        int yearOfBirth=currentYear - years;
        // Handle DOB month:
        // **No second prompt for birth month, directly use current month**
        int approxYears=months / 12; // Approximate years based on months
        int remainingMonths=months % 12;
        LocalDate adjustedDob=LocalDate.of(yearOfBirth - approxYears, today.getMonthValue(), 1);
        adjustedDob=adjustedDob.minusMonths(remainingMonths); // Adjust for remaining months
        System.out.println("Note: Birth month is estimated based on current date.");
        // Separate formatting for date and month (numerical)
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("dd");
        DateTimeFormatter monthFormatter=DateTimeFormatter.ofPattern("MMMM");
        DateTimeFormatter monthNumberFormatter=DateTimeFormatter.ofPattern("M"); // Numeric month format
        DateTimeFormatter dobFormatter=DateTimeFormatter.ofPattern("dd MMMM yyyy"); // Full DOB format
        String date=dateFormatter.format(adjustedDob);
        String month=monthFormatter.format(adjustedDob);
        String formattedDob=dobFormatter.format(adjustedDob);
        int currentMonthNumber=today.getMonthValue();
        int birthMonthNumber=adjustedDob.getMonthValue();
        // **All requested output statements:**
        System.out.println("Current year: " + today.getYear());
        System.out.println("Current month (number): " + currentMonthNumber);
        System.out.println("Day (birth date in number): " + date);
        System.out.println("Month (birth month in number): " + birthMonthNumber);
        System.out.println("Year of birth (based on calculation): " + adjustedDob.getYear());
        System.out.println("Month(birth month in words) : " + month);
        System.out.println("DOB(birth dob ) : " + formattedDob);
        scanner.close();
        /*** End ***/

        int childyear=adjustedDob.getYear();
        String selectdate=formattedDob;
        int cyear=today.getYear();
        int cmonth=currentMonthNumber;
        int bmonth=birthMonthNumber;

        enterYear(childyear);
        entermonth(cmonth, bmonth, childyear, cyear);
        System.out.println(selectdate);
        selectDate(selectdate);

    }

    public void enterYear(int year) {
        String xpath="//android.widget.TextView[@resource-id='android:id/text1' and @text=\"" + year + "\"]";
        driver.findElement(By.xpath(xpath)).click();
    }

    public void entermonth(int cmonth, int bmonth, int byear, int cyear) {
        System.out.println("current month:" + cmonth);
        System.out.println("child birth month:" + bmonth);

        int m=0;
        m=cmonth - bmonth;
        System.out.println(m);
        int mplus=0;

        if (m < 0) {
            mplus=Math.abs(m);
        } else {
            mplus=m;
        }

        int im=mplus;
        System.out.println(im);
        for (int i=0; i < im; i++) {
            if ((cyear == byear) || (bmonth < cmonth)) {
                driver.findElement(By.id("android:id/prev")).click();
            } else

                driver.findElement(By.id("android:id/next")).click();
            // System.out.println(i);
        }

    }

    public void selectDate(String date) {
        System.out.println("date from the action class is " + date);
        String xpath="//android.view.View[@content-desc=\"" + date + "\"]";
        driver.findElement(By.xpath(xpath)).click();
        String xpathone="//android.widget.Button[@resource-id='android:id/button1']";
        driver.findElement(By.xpath(xpathone)).click();
    }


}

