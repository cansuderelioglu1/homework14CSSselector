package demoqa.com.test;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

import java.time.Duration;


public class DemoqaTest {

    private WebDriver driver;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-web-security");
        options.addArguments("--allow-running-insecure-content");

        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30)); //timeout
        driver.manage().window().maximize();

    }
    @Test(priority = 0)
    public void testDoubleClickButton() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://demoqa.com/elements");
        WebElement buttons=driver.findElement(By.cssSelector("#item-4"));
        js.executeScript("arguments[0].scrollIntoView(true);", buttons);
        buttons.click();


        WebElement btnDoubleClick = driver.findElement(By.cssSelector("button#doubleClickBtn"));

        js.executeScript("arguments[0].scrollIntoView(true);", btnDoubleClick);

        Actions actions = new Actions(driver);
        actions.doubleClick(btnDoubleClick).perform();

        WebElement result = driver.findElement(By.cssSelector("#doubleClickMessage"));
        Assert.assertEquals(result.getText(), "You have done a double click");
    }

    @Test(priority = 1)
    public void testClickButton() {
//" "Click Me" butonunu bulmak için:ID sürekli değiştiği için for each ile tüm buttons ları gezdik.Text Click Me olan butonu buton a eşitledi.

        JavascriptExecutor js = (JavascriptExecutor) driver;

        driver.get("https://demoqa.com/elements");
        WebElement buttons=driver.findElement(By.cssSelector("#item-4"));
        js.executeScript("arguments[0].scrollIntoView(true);", buttons);
        buttons.click();

        List<WebElement> allButton = driver.findElements(By.cssSelector("button"));
        WebElement btnClick = null; // ilk başta ne olduğunu bilmediğimiz için null
        for (WebElement button : allButton) {
            if (button.getText().equals("Click Me")) { // her döndüğü buton a üzerinde Click Me yazıyor mu diye sorar
                btnClick = button;
                break;
            }
        }

        js.executeScript("arguments[0].scrollIntoView(true);", btnClick);

        btnClick.click();
//*[@id="dynamicClickMessage"]
        WebElement result = driver.findElement(By.cssSelector("#dynamicClickMessage"));
        Assert.assertEquals(result.getText(), "You have done a dynamic click");

    }

    @Test(priority = 2)
    public void testAddRecord() {
        driver.get("https://demoqa.com/webtables");
        WebElement btnAdd = driver.findElement(By.cssSelector("button#addNewRecordButton"));
        btnAdd.click();


        WebElement textFirstName = driver.findElement(By.cssSelector("input#firstName"));
        WebElement textLastName = driver.findElement(By.cssSelector("input#lastName"));
        WebElement textEmail = driver.findElement(By.cssSelector("input#userEmail"));
        WebElement textAge = driver.findElement(By.cssSelector("input#age"));
        WebElement textSalary = driver.findElement(By.cssSelector("input#salary"));
        WebElement textDepartment = driver.findElement(By.cssSelector("input#department"));


        textFirstName.sendKeys("Cansu");
        textLastName.sendKeys("Derelioglu");
        textEmail.sendKeys("cansu@gmail.com");
        textAge.sendKeys("30");
        textSalary.sendKeys("1000");
        textDepartment.sendKeys("Test");

        WebElement btnSubmit = driver.findElement(By.cssSelector("button#submit"));
        btnSubmit.click();

        // Bir çok edit butonu olduğu için for each
        List<WebElement> rows = driver.findElements(By.cssSelector("div.rt-tbody div.rt-tr-group"));
        WebElement btnEdit = null;
        for (WebElement row : rows) {
            if (row.getText().contains("Cansu")) {
                btnEdit = row.findElement(By.cssSelector("span[title='Edit']"));
                break;
            }
        }


        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", btnEdit);
        btnEdit.click();


        textSalary = driver.findElement(By.cssSelector("input#salary"));
        textSalary.clear();
        textSalary.sendKeys("2000");

        btnSubmit = driver.findElement(By.cssSelector("button#submit"));
        btnSubmit.click();


        WebElement salaryCell = null;
        for (WebElement row : rows) {
            if (row.getText().contains("Cansu")) {
                salaryCell = row.findElement(By.cssSelector("div.rt-td:nth-child(5)"));
                break;
            }
        }

        Assert.assertEquals(salaryCell.getText(), "2000");


    }


    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }

    }

}
