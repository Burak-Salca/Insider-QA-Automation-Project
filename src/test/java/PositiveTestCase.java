import base.BaseLibrary;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.MainPage;
import pages.ProductPage;

public class PositiveTestCase extends BaseLibrary {

    private MainPage mainPage;
    private ProductPage productPage;
    private CartPage cartPage;

    @BeforeMethod
    @Override
    public void beforeTest() {
        super.beforeTest();
        mainPage = new MainPage();
        productPage = new ProductPage();
        cartPage = new CartPage();
    }

    @Test(description = "İstenilen ürünün sepete eklenip çıkarılma testi ")
    public void ProductAddToCartTest(){
        mainPage.goToMainPage()                                       //1- https://www.amazon.com.tr/ adresine gidin. //2- Ana sayfada olduğunu doğrula. Çerezleri onayla.
                .enterSearchBox(searchText)                           //3- Ekranın üst kısmındaki arama alanına "samsung" yazın ve arama yapın.//4- Açılan sayfada Samsung ile ilgili sonuçların görüntülendiğini doğrulayın.
                .goToPage(pageNumber);                                //5- Arama sonuçlarından 2. sayfaya tıklayın ve açılan sayfada 2. sayfanın görüntülendiğini doğrulayın.
        String productTitle = mainPage.goToProduct(productNumber);    //6- Üstten 3. ürüne gidin.
        productPage.validationPage(productTitle)                      //7- Ürün sayfasında olduğunuzu doğrulayın.
                   .addToCart();                                      //8- Ürünü sepete ekleyin. //9- Ürünün sepete eklendiğini doğrulayın.
        cartPage.goToCart()                                           //10- Sepet sayfasına gidin.
                .findProduct(productTitle)                            //11- Sepet sayfasında olduğunuzu ve doğru ürünün sepete eklendiğini doğrulayın.
                .deleteProduct(productTitle)                          //12- Ürünü sepetten silin ve silindiğini doğrulayın.
                .goToMainPage();                                      //13- Ana sayfaya geri dönün ve ana sayfada olduğunuzu doğrulayın.
    }
}
