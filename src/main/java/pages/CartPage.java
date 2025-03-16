package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import util.Actions;

import java.util.List;

public class CartPage extends BaseLibrary {

    private static final By GO_TO_CART_BUTTON = By.xpath("//a[contains(@href, '/cart') and contains(text(), 'Sepete Git')]");
    private static final By CART_HEADER = By.id("sc-active-items-header");
    private static final By CART_PRODUCT_TITLE = By.cssSelector("span.a-truncate-cut");
    private static final By CART_ITEMS = By.xpath("//div[contains(@class, 'sc-list-item')]");
    private static final By DELETE_BUTTON = By.xpath(".//input[@type='submit' and @value='Sil' and @data-feature-id='item-delete-button']");
    private static final By REMOVED_MESSAGE = By.xpath("//div[@data-removed='true']");
    private static final By HOME_BUTTON = By.id("nav-logo-sprites");

    Actions actions = new Actions();

    @Step("Sepet sayfasına gidilir.")
    public CartPage goToCart(){
        try {
            actions.clickButton(GO_TO_CART_BUTTON);
            screenshot();
            System.out.println("Sepet git butonuna başarıyla başarıyla tıklanıldı.");
        } catch (Exception e) {
            Assert.fail("Sepete git butonuna tıklanamadı: " + e.getMessage());
        }
        return this;
    }

    @Step("Sepet sayfasında doğru ürün mü eklenmiş kontrol edilir")
    public CartPage findProduct(String productName){
        try {
            WebElement cartHeader = wait.until(ExpectedConditions.presenceOfElementLocated(CART_HEADER));
            String headerText = cartHeader.getText().trim();
            Assert.assertTrue(headerText.contains("Alışveriş Sepeti"), "Sepet sayfası doğrulanamadı! Beklenen: 'Alışveriş Sepeti', Ancak Bulunan: " + headerText);
            System.out.println("Sepet sayfasına başarıyla ulaşıldı.");
        } catch (Exception e) {
            Assert.fail("Sepet sayfası doğrulanamadı: " + e.getMessage());
        }
        try {
            WebElement cartProductTitleElement = wait.until(ExpectedConditions.presenceOfElementLocated(CART_PRODUCT_TITLE));
            String cartProductTitle = cartProductTitleElement.getText().trim();
            String normalizedCartProductTitle = cartProductTitle.replaceAll("[.]{3,}|…", "").trim();
            String normalizedProductTitle = productName.replaceAll("[.]{3,}|…", "").trim();
            Assert.assertTrue(normalizedProductTitle.contains(normalizedCartProductTitle),"Sepete eklenen ürün, seçilen ürünle eşleşmiyor! Beklenen: '" + normalizedProductTitle + "', Ancak Bulunan: '" + normalizedCartProductTitle + "'");
            screenshot();
            System.out.println("Ürün başarıyla sepete eklendi ve doğrulandı.");
        } catch (Exception e) {
            Assert.fail("Sepet doğrulaması sırasında hata oluştu: " + e.getMessage());
        }
        return this;
    }

    @Step("Eklenilen ürün sepetten silinir.")
    public CartPage deleteProduct(String productName){
        try {
            List<WebElement> cartItems = driver.findElements(CART_ITEMS);
            for (WebElement item : cartItems) {
                WebElement productNameElement = item.findElement(CART_PRODUCT_TITLE);
                String cartProductName = productNameElement.getText().trim();

                if (cartProductName.contains(productName.substring(0, Math.min(productName.length(), 136)))) {
                    actions.clickButton(DELETE_BUTTON);
                    wait.until(ExpectedConditions.stalenessOf(item));
                    WebElement removedMessage = wait.until(ExpectedConditions.presenceOfElementLocated(REMOVED_MESSAGE));
                    String removedText = removedMessage.getText();
                    Assert.assertTrue(removedText.contains("Alışveriş Sepetiniz konumundan kaldırıldı"), "Ürün silme işlemi başarısız!");
                    screenshot();
                    System.out.println("Ürün başarıyla silindi ve doğrulandı: " + cartProductName);
                    break;
                }
                else{
                    Assert.fail("Ürün silme işlemi başarısız");
                }
            }
        } catch (Exception e) {
            Assert.fail("Ürün silme işlemi başarısız: " + e.getMessage());
        }
        return this;
    }

    @Step("Ana sayfa dönülür.")
    public CartPage goToMainPage(){
        try {
            actions.clickButton(HOME_BUTTON);
            actions.validationUrl("https://www.amazon.com.tr/ref=nav_logo");
            screenshot();
        } catch (Exception e) {
            Assert.fail("Ana sayfaya dönme işlemi başarısız: " + e.getMessage());
        }
        return this;
    }
}
