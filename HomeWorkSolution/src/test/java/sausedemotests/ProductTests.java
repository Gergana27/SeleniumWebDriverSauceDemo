package sausedemotests;

import core.BaseTests;
import org.example.BrowserTypes;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductTests extends BaseTests {

    @BeforeAll
    public static void setup() {
        driver = startBrowser(BrowserTypes.CHROME);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20L));
        driver.get("https://www.saucedemo.com/");
        authenticateWithUser("standard_user", "secret_sauce");
    }

    @Test
    public void addProduct_by_name() {
        WebElement product = this.getProductByTitle("Sauce Labs Backpack");
        product.findElement(By.className("btn_inventory")).click();
    }

    @Test
    public void findAllProducts() {
        List<WebElement> productList = this.getAllProducts();
    }

    @Test
    public void productAddedToShoppingCart_when_addToCart() {
        WebElement product1 = this.getProductByTitle("Sauce Labs Backpack");
        product1.findElement(By.className("btn_inventory")).click();
        WebElement product2 = this.getProductByTitle("Sauce Labs Bike Light");
        product2.findElement(By.className("btn_inventory")).click();
        WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
        cartButton.click();
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertTrue(cartItems.stream().anyMatch((item) -> {
            return item.getText().equals("Sauce Labs Backpack");
        }));
        Assertions.assertTrue(cartItems.stream().anyMatch((item) -> {
            return item.getText().equals("Sauce Labs Bike Light");
        }));
    }

    @Test
    public void userDetailsAdded_when_checkoutWithValidInformation() {
        this.productAddedToShoppingCart_when_addToCart();
        WebElement checkoutButton = driver.findElement(By.id("checkout"));
        checkoutButton.click();
        WebElement firstName = driver.findElement(By.id("first-name"));
        WebElement lastName = driver.findElement(By.id("last-name"));
        WebElement postalCode = driver.findElement(By.id("postal-code"));
        firstName.sendKeys(new CharSequence[]{"John"});
        lastName.sendKeys(new CharSequence[]{"Doe"});
        postalCode.sendKeys(new CharSequence[]{"12345"});
        WebElement continueButton = driver.findElement(By.id("continue"));
        continueButton.click();
        WebElement summaryTitle = driver.findElement(By.className("title"));
        Assertions.assertEquals("Checkout: Overview", summaryTitle.getText());
    }

    @Test
    public void orderCompleted_when_addProduct_and_checkout_withConfirm() {
        this.userDetailsAdded_when_checkoutWithValidInformation();
        WebElement finishButton = driver.findElement(By.id("finish"));
        finishButton.click();
        WebElement orderConfirmationMessage = driver.findElement(By.className("complete-header"));
        Assertions.assertEquals("Thank you for your order!", orderConfirmationMessage.getText());
        WebElement cartButton = driver.findElement(By.className("shopping_cart_link"));
        cartButton.click();
        List<WebElement> cartItems = driver.findElements(By.className("inventory_item_name"));
        Assertions.assertTrue(cartItems.isEmpty(), "Shopping cart should be empty after order is completed.");
    }
}
