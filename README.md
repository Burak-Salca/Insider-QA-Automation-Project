# Amazon Test Otomasyon Projesi

Bu proje, Amazon Türkiye web sitesinde ürün arama, sepete ekleme ve sepetten çıkarma işlemlerini test eden kapsamlı bir otomasyon projesidir. Page Object Model (POM) tasarım deseni kullanılarak geliştirilmiş olup, yüksek düzeyde bakım yapılabilirlik ve yeniden kullanılabilirlik sağlamaktadır.

## 🎯 Proje Özellikleri

- **Dinamik Test Yapısı**: Test senaryoları parametrik olarak tasarlanmıştır:
  - İstenilen herhangi bir ürün araması yapılabilir
  - Herhangi bir sayfa numarasına gidilebilir
  - Seçilen sayfadaki herhangi bir ürüne erişilebilir
- **Page Object Model**: Her sayfa için ayrı class'lar oluşturularak kod tekrarı önlenmiş ve bakım kolaylığı sağlanmıştır
- **Clean Code**: Locator'lar ve metodlar düzenli bir şekilde organize edilmiştir
- **Detaylı Raporlama**: Allure Report kullanılarak kapsamlı test raporları oluşturulmuştur
- **Görsel Doğrulama**: Kritik test adımlarında screenshot'lar alınarak görsel doğrulama sağlanmıştır

## 📊 Test Raporu

Test sonuçlarını detaylı bir şekilde görüntülemek için Allure Report kullanılmıştır. Raporlarda:
- Test adımlarının detaylı açıklamaları
- Başarılı/Başarısız test sayıları
- Test süreleri
- Hata durumunda screenshot'lar
- Kritik adımların görsel kayıtları

bulunmaktadır.

Detaylı test raporlarına [buradan](https://insider-qa-automation-project.vercel.app/) ulaşabilirsiniz.

## 🔍 Test Senaryosu

Pozitif test senaryosu aşağıdaki adımları içermektedir:

1. Amazon.com.tr adresine git
2. Ana sayfada olduğunuzu doğrula
3. Arama kutusuna istenilen ürünü yaz ve ara
4. Arama sonuçlarının görüntülendiğini doğrula
5. İstenilen sayfa numarasına git ve doğrula
6. Seçilen sayfadaki istenilen sıradaki ürüne git
7. Ürün detay sayfasında olduğunu doğrula
8. Ürünü sepete ekle
9. Ürünün sepete eklendiğini doğrula
10. Sepet sayfasına git 
11. Doğru ürünün sepete eklendiğini kontrol et
12. Ürünü sepetten sil ve silindiğini doğrula
13. Ana sayfaya dön ve doğrula

## 🛠️ Kullanılan Teknolojiler

- Java 11
- Selenium WebDriver
- TestNG
- Maven
- Allure Report
- Log4j



