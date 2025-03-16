package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

public class ProductPage extends BaseLibrary {

    private static final By PRODUCT_TITLE = By.id("productTitle");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-button");
    private static final By CONFIRMATION_MESSAGE = By.xpath("//h1[contains(@class, 'sw-atc-text')]");

    @Step("Ürün sayfasına girilir. Doğru ürün sayfa kontolü sağlanır")
    public ProductPage validationPage(String text){
        try {
            WebElement productTitleElement = wait.until(ExpectedConditions.presenceOfElementLocated(PRODUCT_TITLE));
            String actualTitle = productTitleElement.getText().trim();
            Assert.assertTrue(actualTitle.contains(text), "Ürün sayfasındaki başlık, listelenen ürün başlığıyla uyuşmuyor!");
            screenshot();
            System.out.println("Doğru ürün sayfası gidildi. Ürün başlığı: " + actualTitle);
        } catch (Exception e) {
            Assert.fail("Ürün sayfası doğrulanamadı: " + e.getMessage());
        }
        return this;
    }

    @Step("Ürün sepete eklenir")
    public ProductPage addToCart(){
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(ADD_TO_CART_BUTTON));
            addToCartButton.click();
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(CONFIRMATION_MESSAGE));
            String confirmationText = confirmationMessage.getText().trim();
            Assert.assertTrue(confirmationText.contains("Sepete eklendi"), "Sepete ekleme doğrulaması başarısız! Beklenen: 'Sepete eklendi', Bulunan: " + confirmationText);
            screenshot();
            System.out.println("Ürün sepete başarıyla eklendi.");
        }catch (Exception e) {
            Assert.fail("Sepete ekleme işlemi başarısız oldu: " + e.getMessage());
        }
        return this;
    }
}
