package util;

import base.BaseLibrary;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class Actions extends BaseLibrary {

    public void goToPage(int pageNumber) {
        WebElement paginationBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("s-pagination-strip")));
        scrollToElementSmoothly(paginationBar);
        try {
            WebElement pageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='" + pageNumber + " sayfasına git']")));
            pageButton.click();
            screenshot();
            wait.until(ExpectedConditions.urlContains("page=" + pageNumber));
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("page=" + pageNumber), pageNumber + ". sayfaya geçiş başarısız! Beklenen URL içinde 'page=" + pageNumber + "' bulunamadı. Bulunan: " + currentUrl);
            System.out.println(pageNumber + ". sayfaya başarıyla geçildi. Güncel URL: " + currentUrl);
        } catch (Exception e) {
            Assert.fail(pageNumber + ". sayfaya geçilemedi: " + e.getMessage());
        }
    }

    public String goToProduct(int order){
        if (order < 1 || order > 65) {
            return("Geçersiz ürün sırası: " + order + ". Lütfen 1 ile 65 arasında bir değer girin.");
        }
        int targetIndex = order + 2;
        try {
            WebElement product = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-index='" + targetIndex + "']")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", product);
            WebElement productLink = product.findElement(By.xpath(".//a[contains(@class, 'a-link-normal') and contains(@class, 's-no-outline')]"));
            WebElement h2TitleElement = product.findElement(By.xpath(".//h2"));
            screenshot();
            String productTitle = h2TitleElement.getText().trim();
            productLink.click();
            System.out.println("Ürün başarılı bir şekilde seçildi");
            return productTitle;
        } catch (Exception e) {
            Assert.fail("Ürün seçme işlemi başarısız oldu: " + e.getMessage());
            return ("Ürün seçme işlemi başarısız oldu: " + e.getMessage());
        }
    }

    public void scrollToElementSmoothly(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            wait.until(ExpectedConditions.visibilityOf(element));
            System.out.println("Scroll işlemi başarıyla yapıldı.");
        } catch (Exception e) {
            Assert.fail("Scroll işlemi başarısız: " + e.getMessage());
        }
    }

    public void validationUrl(String urlData){
        String currentUrl = driver.getCurrentUrl();
        wait.until(ExpectedConditions.urlContains(urlData));
        Assert.assertEquals(currentUrl, urlData, "Ana sayfa doğrulanamadı! Beklenen URL: " + urlData + "Ancak Bulunan: " + currentUrl);
        System.out.println("Ana sayfaya başarıyla doğrulandı.");
    }

    public void clickButton(By locater){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(locater));
        button.click();
    }

}
