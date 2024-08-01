package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;


public class WildberriesAutomation {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\User\\Desktop\\driver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.get("https://www.wildberries.ru/");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("searchInput")));

            WebElement searchBox = driver.findElement(By.id("searchInput"));
            searchBox.sendKeys("мобильный телефон");
            searchBox.sendKeys(Keys.RETURN);

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-card")));

            List<WebElement> goods = driver.findElements(By.className("product-card"));

            if (!goods.isEmpty()) {
                WebElement firstProduct = goods.get(0);

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", firstProduct);

                wait.until(ExpectedConditions.elementToBeClickable(firstProduct));

                wait.until(driver1 -> {
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    return (Boolean) js.executeScript("return arguments[0].getBoundingClientRect().top >= 0 && arguments[0].getBoundingClientRect().bottom <= window.innerHeight;", firstProduct);
                });

                firstProduct.click();

                WebElement addToCartButton = driver.findElement(By.className("btn-main"));  // Используем findElement
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
                Thread.sleep(5000);
                WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@href='/lk/basket']")));
                cartButton.click();

                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("basket-section")));
            } else {
                System.out.println("Товары не найдены.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
}