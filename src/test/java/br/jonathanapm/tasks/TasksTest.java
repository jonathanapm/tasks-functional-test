package br.jonathanapm.tasks;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class TasksTest {

    private static final String url = "http://localhost:8081/";

    @Test
    public void shouldSaveTaskWithSuccess() {
        WebDriver driver = getApplication();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste Via Selenium " + LocalDateTime.now());

            driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(1L)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Sucess!", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithSuccess() {
        WebDriver driver = getApplication();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste Via Selenium " + LocalDateTime.now());

            driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(-1L)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Due date must not be in past", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithoutDescription() {
        WebDriver driver = getApplication();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("dueDate")).sendKeys(LocalDate.now().plusDays(1L)
                    .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the task description", message);
        } finally {
            driver.quit();
        }
    }

    @Test
    public void shouldNotSaveTaskWithoutDate() {
        WebDriver driver = getApplication();

        try {
            driver.findElement(By.id("addTodo")).click();

            driver.findElement(By.id("task")).sendKeys("Teste Via Selenium " + LocalDateTime.now());

            driver.findElement(By.id("saveButton")).click();

            String message = driver.findElement(By.id("message")).getText();

            Assert.assertEquals("Fill the due date", message);
        } finally {
            driver.quit();
        }
    }

    private WebDriver getApplication() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");

        WebDriver driver = new ChromeDriver(options);
        driver.navigate().to(url.concat("tasks/"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }
}
