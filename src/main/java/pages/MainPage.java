package pages;

import base.BaseLibrary;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import util.Actions;

public class MainPage extends BaseLibrary {

    private static final By COOKIES_ACCEPT_BUTTON = By.id("sp-cc-accept");
    private static final By SEARCH_BOX = By.id("twotabsearchtextbox");
    private static final By SEARCH_RESULT_TEXT = By.cssSelector("span.a-color-state.a-text-bold");

    Actions actions = new Actions();

    @Step("Ana sayfa yüklenir, çerezler kabul edilir.")
    public MainPage goToMainPage(){
        try {
            actions.validationUrl(url);
            WebElement cookiesButton = driver.findElement(COOKIES_ACCEPT_BUTTON);
            if(cookiesButton.isDisplayed()){
                cookiesButton.click();
                screenshot();
                System.out.println("Çerezler kabul edildi.");
            }
            else{
                System.out.println("Çerez pencersi çıkmadı");
            }
        } catch (Exception e) {
            Assert.fail("Ana sayfa doğrulanamadı, element bulunamadı veya metin uyuşmuyor: " + e.getMessage());
        }
        return this;
    }

    @Step("Arama çubuğuna istenilen ürün girilir")
    public MainPage enterSearchBox(String searchData){
        actions.sendKey(SEARCH_BOX,searchData);
        try{
            String searchTextResult = actions.getTextFromElement(SEARCH_RESULT_TEXT);
            Assert.assertTrue(searchTextResult.contains(searchData), "Arama başarılı olmadı! Beklenen: 'samsung', Ancak Bulunan: " + searchTextResult);
            screenshot();
            System.out.println("Arama başarıyla gerçekleşti ve doğrulandı.");
        } catch (Exception e) {
            Assert.fail("Arama başarılı değil: " + e.getMessage());
        }
        return this;
    }

    @Step("Arama sonuçlarında istenilen sayfaya gidilir ")
    public MainPage goToPage(int pageNum){
        actions.goToPage(pageNum);
        return this;
    }

    @Step("Arama sonuçlarında istenilen ürün seçilir ")
    public String goToProduct(int productNum){
        return actions.goToProduct(productNum);
    }

}
