package sausedemotests;

import core.BaseTests;
import org.example.BrowserTypes;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductTests extends BaseTests {

    @BeforeAll
    public static void setup(){
        driver = startBrowser(BrowserTypes.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        driver.get("https://www.saucedemo.com/");

        authenticateWithUser("standard_user", "secret_sauce");
    }

    @Test
    public void addProduct_by_name(){
        var product = getProductByTitle("Sauce Labs Backpack");
        product.findElement(By.className("btn_inventory")).click();
    }

    @Test
    public void findAllProducts(){
        var productList = getAllProducts();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart(){

    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation(){

    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm(){

    }
}
