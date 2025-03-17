package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.testng.Assert;
import util.Actions;

public class ProductPage extends BaseLibrary {

    private static final By PRODUCT_TITLE = By.id("productTitle");
    private static final By ADD_TO_CART_BUTTON = By.id("add-to-cart-button");
    private static final By CONFIRMATION_MESSAGE = By.xpath("//h1[contains(@class, 'sw-atc-text')]");

    Actions actions = new Actions();

    @Step("Ürün sayfasına girilir. Doğru ürün sayfa kontolü sağlanır")
    public ProductPage validationPage(String text){
        try {
            String actualTitle = actions.getTextFromElement(PRODUCT_TITLE);
            Assert.assertTrue(actualTitle.contains(text), "Ürün sayfasındaki başlık, listelenen ürün başlığıyla uyuşmuyor! actualTitle = " + actualTitle + "text= "+ text);
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
            actions.clickButton(ADD_TO_CART_BUTTON);
            String confirmationText = actions.getTextFromElement(CONFIRMATION_MESSAGE);
            Assert.assertTrue(confirmationText.contains("Sepete eklendi"), "Sepete ekleme doğrulaması başarısız! Beklenen: 'Sepete eklendi', Bulunan: " + confirmationText);
            screenshot();
            System.out.println("Ürün sepete başarıyla eklendi.");
        }catch (Exception e) {
            Assert.fail("Sepete ekleme işlemi başarısız oldu: " + e.getMessage());
        }
        return this;
    }
}
