package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;

public class BaseTest extends Data {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    @BeforeMethod(description = "Tarayıcı açılır.")
    public void beforeTest(){
        cleanAllureResults();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(url);
    }

    @AfterMethod(description = "Tarayıcı kapanır.")
    public void afterTest(){
        driver.quit();
    }

    void cleanAllureResults() {
        File allureResults = new File("allure-results");
        if (allureResults.exists() && allureResults.isDirectory()) {
            for (File file : allureResults.listFiles()) {
                file.delete();
            }
        }
    }
}
