package Test;

import Page.ToDoPage;
import base.ActionClass;
import base.BaseClass;
import org.testng.annotations.BeforeClass;
import utilities.ExcelLoader;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class ToDoTest extends BaseClass {
    public ActionClass action;
    public ToDoPage tdp;
    boolean found = false;
    String toastMsg = null;

    public ToDoTest() {
        super();
    }

    @BeforeClass(alwaysRun = true)
    public void initPage() {
        // Initialize the driver before any test method
        initializeDriverOnce();  // Ensure the driver is initialized

        // Now initialize other components
        action = new ActionClass();
        tdp = new ToDoPage();
    }

    @DataProvider(name = "excelDataProvider")
    public Object[][] getTestCaseData() {
        List<Map<String, String>> testCases = ExcelLoader.loadTestCases("src/test/java/resources/testCases.xlsx");
        return testCases.stream()
                .map(testCase -> new Object[]{testCase})
                .toArray(Object[][]::new);
    }

    @Test(dataProvider = "excelDataProvider")
    public void executeTest(Map<String, String> testCase) {
        System.out.println("TestCase: " + testCase); // Log the entire test case map
        String action = testCase.get("Action");
        String input = testCase.get("Input");
        String expectedResult = testCase.get("ExpectedResult");

        // Log individual fields
        System.out.println("Action: " + action);
        System.out.println("Input: " + input);
        System.out.println("Expected Result: " + expectedResult);

        if (action == null || input == null || expectedResult == null) {
            throw new IllegalArgumentException("One or more values are missing in the test case.");
        }

        try {
            switch (action) {
                case "addTask":
                    tdp.clickAddTaskButton();
                    tdp.addTask(input);
                    tdp.clickSaveButton();
                    Assert.assertTrue(tdp.isTaskPresent(input), expectedResult);
                    break;
                case "deleteTask":
                    tdp.deleteTask(input);
                    Assert.assertFalse(tdp.isTaskPresent(input), expectedResult);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown action: " + action);
            }
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
