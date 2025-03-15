import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

public class ProductAddToCartTest {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeMethod
    public void beforeTest(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.amazon.com.tr/");
    }

    @Test
    public void ProductAddToCartTest(){
        //1- https://www.amazon.com.tr/ adresine gidin.
        //2- Ana sayfada olduğunu doğrula. Çerezleri onayla.
        try {

            WebElement logoElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("nav-logo-sprites")));
            String logoText = logoElement.getAttribute("aria-label");
            Assert.assertTrue(logoText.contains("Amazon"), "Ana sayfa doğrulanamadı!");

            WebElement cookiesButton = driver.findElement(By.id("sp-cc-accept"));
            if(cookiesButton.isDisplayed()){
                cookiesButton.click();
                System.out.println("Çerezler kabul edildi.");
            }
            else{
                System.out.println("Çerez pencersi çıkmadı");
            }

        } catch (Exception e) {
            Assert.fail("Ana sayfa doğrulanamadı, element bulunamadı veya metin uyuşmuyor: " + e.getMessage());
        }

        //3- Ekranın üst kısmındaki arama alanına "samsung" yazın ve arama yapın.
        //4- Açılan sayfada Samsung ile ilgili sonuçların görüntülendiğini doğrulayın.
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys("samsung", Keys.ENTER);
        try{
            WebElement searchResultText = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.a-color-state.a-text-bold")));
            String searchText = searchResultText.getText().toLowerCase();
            Assert.assertTrue(searchText.contains("samsung"), "Arama başarılı olmadı! Beklenen: 'samsung', Ancak Bulunan: " + searchText);
            System.out.println("Arama başarıyla gerçekleşti ve doğrulandı.");
        } catch (Exception e) {
            Assert.fail("Arama başarılı değil: " + e.getMessage());
        }

        //5- Arama sonuçlarından 2. sayfaya tıklayın ve açılan sayfada 2. sayfanın görüntülendiğini doğrulayın.
        goToPage(2);

        //6- Üstten 3. ürüne gidin.
        String productTitle = goToProduct(4);

        //7- Ürün sayfasında olduğunuzu doğrulayın.
        try {
            WebElement productTitleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("productTitle")));
            String actualTitle = productTitleElement.getText().trim();
            Assert.assertTrue(actualTitle.contains(productTitle), "Ürün sayfasındaki başlık, listelenen ürün başlığıyla uyuşmuyor!");
            System.out.println("Doğru ürün sayfası gidildi. Ürün başlığı: " + actualTitle);
        } catch (Exception e) {
            Assert.fail("Ürün sayfası doğrulanamadı: " + e.getMessage());
        }

        //8- Ürünü sepete ekleyin.
        //9- Ürünün sepete eklendiğini doğrulayın.
        try {
            WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("add-to-cart-button")));
            addToCartButton.click();
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(@class, 'sw-atc-text')]")));
            String confirmationText = confirmationMessage.getText().trim();
            Assert.assertTrue(confirmationText.contains("Sepete eklendi"), "Sepete ekleme doğrulaması başarısız! Beklenen: 'Sepete eklendi', Bulunan: " + confirmationText);
            System.out.println("Ürün sepete başarıyla eklendi.");
        }catch (Exception e) {
            Assert.fail("Sepete ekleme işlemi başarısız oldu: " + e.getMessage());
        }

        //10- Sepet sayfasına gidin.
        try {
            WebElement goToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(@href, '/cart') and contains(text(), 'Sepete Git')]")));
            goToCartButton.click();
            System.out.println("Sepet git butonuna başarıyla başarıyla tıklanıldı.");
        } catch (Exception e) {
            Assert.fail("Sepete git butonuna tıklanamadı: " + e.getMessage());
        }

        //11- Sepet sayfasında olduğunuzu ve doğru ürünün sepete eklendiğini doğrulayın.
        try {
            // Sepet başlığını bekle ve doğrula
            WebElement cartHeader = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("sc-active-items-header")));
            String headerText = cartHeader.getText().trim();
            Assert.assertTrue(headerText.contains("Alışveriş Sepeti"), "Sepet sayfası doğrulanamadı! Beklenen: 'Alışveriş Sepeti', Ancak Bulunan: " + headerText);
            System.out.println("Sepet sayfasına başarıyla ulaşıldı.");
        } catch (Exception e) {
            Assert.fail("Sepet sayfası doğrulanamadı: " + e.getMessage());
        }
        try {
            WebElement cartProductTitleElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.a-truncate-cut")));
            String cartProductTitle = cartProductTitleElement.getText().trim();
            String truncatedProductTitle = productTitle.length() > 136 ? productTitle.substring(0, 136) : productTitle;
            String truncatedCartTitle = cartProductTitle.length() > 136 ? cartProductTitle.substring(0, 136) : cartProductTitle;

            Assert.assertTrue(truncatedCartTitle.equals(truncatedProductTitle),
                    "Sepete eklenen ürün, seçilen ürünle eşleşmiyor! Beklenen: '" + truncatedProductTitle + "', Ancak Bulunan: '" + truncatedCartTitle + "'");

            System.out.println("Ürün başarıyla sepete eklendi ve doğrulandı.");
        } catch (Exception e) {
            Assert.fail("Sepet doğrulaması sırasında hata oluştu: " + e.getMessage());
        }

        //12- Ürünü sepetten silin ve silindiğini doğrulayın.
        try {
            // Sepetteki tüm ürünleri al
            List<WebElement> cartItems = driver.findElements(By.xpath("//div[contains(@class, 'sc-list-item')]"));

            for (WebElement item : cartItems) {

                WebElement productNameElement = item.findElement(By.xpath(".//span[contains(@class, 'a-truncate-cut')]"));
                String cartProductName = productNameElement.getText().trim();

                    // Eğer ürün ismi, silmek istediğimiz ürünle eşleşiyorsa
                if (cartProductName.contains(productTitle.substring(0, Math.min(productTitle.length(), 136)))) {
                    // Sil butonunu bul ve tıkla
                    WebElement deleteButton = item.findElement(By.xpath(".//input[@type='submit' and @value='Sil' and @data-feature-id='item-delete-button']"));
                    deleteButton.click();
                    // Silme işlemi sonrası bekleme
                    wait.until(ExpectedConditions.stalenessOf(item));
                    // **Silme işlemi doğrulaması**: `data-removed="true"` elementi var mı?
                    WebElement removedMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-removed='true']")));
                    String removedText = removedMessage.getText();

                    // Silme işleminin başarılı olduğunu doğrula
                    Assert.assertTrue(removedText.contains("Alışveriş Sepetiniz konumundan kaldırıldı"),
                            "Ürün silme işlemi başarısız!");

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

        //13- Ana sayfaya geri dönün ve ana sayfada olduğunuzu doğrulayın.
        try {
            // Amazon ana sayfa logosunun tıklanabilir olmasını bekleyelim
            WebElement homeButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-logo-sprites")));
            homeButton.click();

            // URL'nin ana sayfa olup olmadığını kontrol edelim
            wait.until(ExpectedConditions.urlToBe("https://www.amazon.com.tr/ref=nav_logo"));

            Assert.assertEquals(driver.getCurrentUrl(), "https://www.amazon.com.tr/ref=nav_logo", "Ana sayfa doğrulanamadı!");
            System.out.println("Ana sayfaya başarıyla dönüldü ve doğrulandı.");
        } catch (Exception e) {
            Assert.fail("Ana sayfaya dönme işlemi başarısız: " + e.getMessage());
        }
    }

    public void goToPage(int pageNumber) {
        WebElement paginationBar = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("s-pagination-strip")));
        scrollToElementSmoothly(paginationBar);
        try {
            WebElement pageButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@aria-label='" + pageNumber + " sayfasına git']")));
            pageButton.click();
            WebElement activePage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("span.s-pagination-item.s-pagination-selected")));
            String currentPage = activePage.getText();
            Assert.assertEquals(currentPage, String.valueOf(pageNumber), pageNumber + ". sayfaya geçiş başarısız!");
            System.out.println(pageNumber + ". sayfaya başarıyla geçildi.");
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
            // Önce küçük bir miktar kaydır (Smooth bir şekilde aşırı aşağı kaydırmayı engeller)
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 300);");
            Thread.sleep(500);

            // Sonrasında tam olarak elemanı ortala
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);

            // Elementin tamamen yüklenmesini bekle
            wait.until(ExpectedConditions.visibilityOf(element));

            System.out.println("Scroll işlemi başarıyla yapıldı.");
        } catch (Exception e) {
            Assert.fail("Scroll işlemi başarısız: " + e.getMessage());
        }
    }

}
