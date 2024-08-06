package org.example;

import com.example.api.WebDriverConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;


public class WildberriesAutomation {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        driver = WebDriverConfig.createDriver();
        wait = WebDriverConfig.createWait(driver);
    }
    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    public void testWildberriesSearchAndAddToCart() throws InterruptedException {
        driver.get("https://www.wildberries.ru/");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));

        WebElement searchBox = driver.findElement(By.id("searchInput"));
        searchBox.sendKeys("мобильный телефон");
        searchBox.sendKeys(Keys.RETURN);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-card")));

        List<WebElement> goods = driver.findElements(By.className("product-card"));

        WebElement firstProduct = goods.get(0);

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstProduct);

        wait.until(ExpectedConditions.elementToBeClickable(firstProduct));

        wait.until(driver1 -> {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            return (Boolean) js.executeScript("return arguments[0].getBoundingClientRect().top >= 0 && arguments[0].getBoundingClientRect().bottom <= window.innerHeight;", firstProduct);
        });

        firstProduct.click();
        Thread.sleep(10000);

        WebElement addToCartButton = driver.findElement(By.className("btn-main"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);

        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/lk/basket']")));
        cartButton.click();
        Thread.sleep(10000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("basket-section")));
        List<WebElement> cartItems = driver.findElements(By.className("accordion__list-item"));

        if (cartItems.isEmpty()) {
            throw new RuntimeException("В корзине нет товаров.");
        }
    }
}