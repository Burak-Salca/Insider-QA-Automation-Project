package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Data {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    protected String url = "https://www.amazon.com.tr/";
    protected String searchText = "samsung";
    protected int pageNumber = 2;
    protected int productNumber = 3;
}
