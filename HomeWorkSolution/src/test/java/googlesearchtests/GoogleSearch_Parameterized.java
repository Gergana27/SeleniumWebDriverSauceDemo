package googlesearchtests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class GoogleSearch_Parameterized {

    @Test
    public void searchByTermOptimized() {
        //Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); //Explicit wait - This will wait until a condition is met or reaching the timeout

        //Make it full screen
        driver.manage().window().maximize();

        //Go to the BaseURL
        driver.get("https://google.com");

        //Accept cookies
        WebElement acceptCookieBtn = driver.findElement(By.id("L2AGLb"));
        acceptCookieBtn.click();

        //Type in the search field
        WebElement searchField = driver.findElement(By.name("q"));
        wait.until(ExpectedConditions.visibilityOf(searchField)); //This will periodically check if the element is visible until true or reach the timeout
        searchField.sendKeys("Telerik academy Alpha");

        //Submit the form
        searchField.sendKeys(Keys.ENTER);

        //Wait for a specific web element
        WebElement resultsNavigation = driver.findElement(By.xpath("//div[@role='navigation']"));
        wait.until(ExpectedConditions.visibilityOf(resultsNavigation));

        //Assert the results
        List<WebElement> resultsList = driver.findElements(By.xpath("//h3"));
        WebElement firstResult = resultsList.get(0);
        var actualResult = firstResult.getText();

        Assertions.assertEquals(actualResult, "IT Career Start in 6 Months - Telerik Academy Alpha");

        //tearDown()
        driver.close();
    }
}
