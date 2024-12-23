package Page;

import base.BaseClass;
import base.ActionClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class ToDoPage extends BaseClass {
    public ActionClass action = new ActionClass();

    // Initialize elements with the driver from BaseClass
    public ToDoPage() {
        if (BaseClass.driver == null) {
            throw new IllegalStateException("Driver is not initialized.");
        }
        PageFactory.initElements(BaseClass.driver, this);
    }

    // Locators
    @FindBy(xpath = "//android.widget.ImageButton[@resource-id='com.peterdpong.checked:id/floating_action_button']")
    private WebElement addTaskButton;

    @FindBy(xpath = "//android.widget.EditText[@resource-id='com.peterdpong.checked:id/titleTextInput']")
    private WebElement taskInputField;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Save Task']")
    private WebElement saveTaskButton;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Delete Task']")
    private WebElement deleteTaskButton;

    @FindBy(xpath = "//android.widget.Button[@content-desc='Edit Task']")
    private WebElement editTaskButton;

    // Actions
    /**
     * Click the "Add Task" button.
     */
    public void clickAddTaskButton() {
        action.clickElement(addTaskButton);
    }

    /**
     * Enter a task name in the input field.
     *
     * @param taskName The name of the task.
     */
    public void enterTaskName(String taskName) {
        action.enterValue(taskInputField, taskName);
    }

    /**
     * Click the "Save Task" button.
     */
    public void clickSaveTaskButton() {
        action.clickElement(saveTaskButton);
    }

    /**
     * Click the "Delete Task" button.
     */
    public void clickDeleteTaskButton() {
        action.clickElement(deleteTaskButton);
    }

    /**
     * Click the "Edit Task" button.
     */
    public void clickEditTaskButton() {
        action.clickElement(editTaskButton);
    }

    public void clickSaveButton() {
        action.clickElement(editTaskButton);
    }

    /**
     * Add a new task.
     *
     * @param taskName Name of the task to be added.
     */
    public void addTask(String taskName) {
        clickAddTaskButton();
        enterTaskName(taskName);
        clickSaveTaskButton();
    }

    /**
     * Check if a task is present.
     *
     * @param taskName Name of the task to check.
     * @return True if the task is found, false otherwise.
     */
    public boolean isTaskPresent(String taskName) {
        try {
            WebElement task = driver.findElement(By.xpath("//android.widget.TextView[@text='" + taskName + "']"));
            return task.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Edit an existing task.
     *
     * @param oldTaskName The current name of the task to be edited.
     * @param newTaskName The new name for the task.
     */
    public void editTask(String oldTaskName, String newTaskName) {
        WebElement task = driver.findElement(By.xpath("//android.widget.TextView[@text='" + oldTaskName + "']"));
        action.clickElement(task);
        clickEditTaskButton();
        enterTaskName(newTaskName);
        clickSaveTaskButton();
    }

    /**
     * Delete an existing task.
     *
     * @param taskName Name of the task to delete.
     */
    public void deleteTask(String taskName) {
        WebElement task = driver.findElement(By.xpath("//android.widget.TextView[@text='" + taskName + "']"));
        action.clickElement(task);
        clickDeleteTaskButton();
    }

    /**
     * Wait for the task to be present on the page before interacting.
     *
     * @param taskName The name of the task to wait for.
     * @param timeout  Maximum time to wait for the task to be present.
     */
    public void waitForTaskToBePresent(String taskName, int timeout) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//android.widget.TextView[@text='" + taskName + "']")));
    }
}

