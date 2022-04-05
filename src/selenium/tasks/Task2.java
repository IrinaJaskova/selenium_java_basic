package selenium.tasks;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class Task2 {
    WebDriver driver;

    @Before
    public void openPage() {
        String libWithDriversLocation = System.getProperty("user.dir") + File.separator + "lib" + File.separator;
        System.setProperty("webdriver.chrome.driver", libWithDriversLocation + "chromedriver" + new selenium.ChangeToFileExtension().extension());
        driver = new ChromeDriver();
        driver.get("https://kristinek.github.io/site/tasks/provide_feedback");
    }

    @After
    public void closeBrowser() {
        driver.close();
    }

    @Test
    public void initialFeedbackPage() throws Exception {
//         TODO:
//         check that all field are empty and no tick are clicked
        driver.findElement(By.id("fb_name")).clear();
        driver.findElement(By.id("fb_age")).clear();
        driver.findElement(By.name("comment")).clear();

        assertEquals("", driver.findElement(By.id("fb_name")).getAttribute("value"));
        assertEquals("", driver.findElement(By.id("fb_age")).getAttribute("value"));
        assertEquals("", driver.findElement(By.name("comment")).getAttribute("value"));

        assertFalse(driver.findElement(By.cssSelector("[value*=\"English\"]")).isSelected());
        assertFalse(driver.findElement(By.cssSelector("[value*=\"French\"]")).isSelected());
        assertFalse(driver.findElement(By.cssSelector("[value*=\"Spanish\"]")).isSelected());
        assertFalse(driver.findElement(By.cssSelector("[value*=\"Chinese\"]")).isSelected());

//         "Don't know" is selected in "Genre"
        assertTrue(driver.findElement(By.cssSelector("input[type=\"radio\"]:disabled")).isSelected());

//         "Choose your option" in "How do you like us?"
        WebElement drop = driver.findElement(By.id("like_us"));
        Select dropdownSelect = new Select(drop);
        List<WebElement> allSelections = dropdownSelect.getAllSelectedOptions();

        assertEquals("Choose your option", allSelections.get(0).getText());

//         check that the button send is blue with white letters
        WebElement button = driver.findElement(By.className("w3-btn-block"));
        assertEquals("rgba(33, 150, 243, 1)", button.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", button.getCssValue("color"));
    }

    @Test
    public void emptyFeedbackPage() throws Exception {
//         TODO:
//         click "Send" without entering any data
        driver.findElement(By.className("w3-btn-block")).click();

//         check fields are empty or null
        assertEquals("", driver.findElement(By.id("name")).getText());
        assertEquals("", driver.findElement(By.id("age")).getText());
        assertEquals("", driver.findElement(By.id("language")).getText());
        assertEquals("null", driver.findElement(By.id("gender")).getText());
        assertEquals("null", driver.findElement(By.id("option")).getText());
        assertEquals("", driver.findElement(By.id("comment")).getText());

//         check button colors
//         (green with white letter and red with white letters)
        WebElement buttonYes = driver.findElement(By.className("w3-green"));
        assertEquals("rgba(76, 175, 80, 1)", buttonYes.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", buttonYes.getCssValue("color"));

        WebElement buttonNo = driver.findElement(By.className("w3-red"));
        assertEquals("rgba(244, 67, 54, 1)", buttonNo.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", buttonNo.getCssValue("color"));

    }

    @Test
    public void notEmptyFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form, click "Send"
        driver.findElement(By.id("fb_name")).sendKeys("TestName");
        driver.findElement(By.id("fb_age")).sendKeys("25");
        driver.findElement(By.cssSelector("[value*=\"English\"]")).click();
        driver.findElement(By.cssSelector("[value*=\"female\"]")).click();

        WebElement drop = driver.findElement(By.id("like_us"));
        Select dropdownSelect = new Select(drop);
        dropdownSelect.selectByVisibleText("Good");

        driver.findElement(By.name("comment")).sendKeys("Test Comment");

        driver.findElement(By.className("w3-btn-block")).click();

//         check fields are filled correctly
        assertEquals("TestName", driver.findElement(By.id("name")).getText());
        assertEquals("25", driver.findElement(By.id("age")).getText());
        assertEquals("English", driver.findElement(By.id("language")).getText());
        assertEquals("female", driver.findElement(By.id("gender")).getText());
        assertEquals("Good", driver.findElement(By.id("option")).getText());
        assertEquals("Test Comment", driver.findElement(By.id("comment")).getText());

//         check button colors
//         (green with white letter and red with white letters)
        WebElement buttonYes = driver.findElement(By.className("w3-green"));
        assertEquals("rgba(76, 175, 80, 1)", buttonYes.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", buttonYes.getCssValue("color"));

        WebElement buttonNo = driver.findElement(By.className("w3-red"));
        assertEquals("rgba(244, 67, 54, 1)", buttonNo.getCssValue("background-color"));
        assertEquals("rgba(255, 255, 255, 1)", buttonNo.getCssValue("color"));
    }

    @Test
    public void yesOnWithNameFeedbackPage() throws Exception {
        String testName = "TestName";
//         TODO:
//         enter only name
        driver.findElement(By.id("fb_name")).sendKeys(testName);

//         click "Send"
        driver.findElement(By.className("w3-btn-block")).click();

//         click "Yes"
        driver.findElement(By.className("w3-green")).click();

//         check message text: "Thank you, NAME, for your feedback!"
        assertEquals("Thank you, " + testName + ", for your feedback!", driver.findElement(By.id("message")).getText());

//         color of text is white with green on the background
        WebElement background = driver.findElement(By.className("w3-green"));
        assertEquals("rgba(76, 175, 80, 1)", background.getCssValue("background-color"));
        WebElement message = driver.findElement(By.id("message"));
        assertEquals("rgba(255, 255, 255, 1)", message.getCssValue("color"));
    }

    @Test
    public void yesOnWithoutNameFeedbackPage() throws Exception {
//         TODO:
//         click "Send" (without entering anything
        driver.findElement(By.className("w3-btn-block")).click();

//         click "Yes"
        driver.findElement(By.className("w3-green")).click();

//         check message text: "Thank you for your feedback!"
        assertEquals("Thank you for your feedback!", driver.findElement(By.id("message")).getText());

//         color of text is white with green on the background
        WebElement background = driver.findElement(By.className("w3-green"));
        assertEquals("rgba(76, 175, 80, 1)", background.getCssValue("background-color"));
        WebElement message = driver.findElement(By.id("message"));
        assertEquals("rgba(255, 255, 255, 1)", message.getCssValue("color"));
    }

    @Test
    public void noOnFeedbackPage() throws Exception {
//         TODO:
//         fill the whole form
        driver.findElement(By.id("fb_name")).sendKeys("TestName");
        driver.findElement(By.id("fb_age")).sendKeys("25");
        driver.findElement(By.cssSelector("[value*=\"English\"]")).click();
        driver.findElement(By.cssSelector("[value*=\"female\"]")).click();

        WebElement drop = driver.findElement(By.id("like_us"));
        Select dropdownSelect = new Select(drop);
        dropdownSelect.selectByVisibleText("Good");

        driver.findElement(By.name("comment")).sendKeys("Test Comment");

//         click "Send"
        driver.findElement(By.className("w3-btn-block")).click();

//         click "No"
        driver.findElement(By.className("w3-red")).click();

//         check fields are filled correctly
        assertEquals("TestName", driver.findElement(By.id("fb_name")).getAttribute("value"));
        assertEquals("25", driver.findElement(By.id("fb_age")).getAttribute("value"));
        assertEquals("Test Comment", driver.findElement(By.name("comment")).getAttribute("value"));
        assertTrue(driver.findElement(By.cssSelector("[value*=\"English\"]")).isSelected());
        assertTrue(driver.findElement(By.cssSelector("[value*=\"female\"]")).isSelected());

        drop = driver.findElement(By.id("like_us"));
        dropdownSelect = new Select(drop);
        List<WebElement> allSelections = dropdownSelect.getAllSelectedOptions();

        assertEquals("Good", allSelections.get(0).getText());

    }
}
