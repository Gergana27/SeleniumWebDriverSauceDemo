package googlesearchtests;

import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class Parameterized_DataProvider {
    //Annotation of the data provider
    @Parameterized.Parameters(name="SearchTerm: {0}, expected result: {1}")
    public static Iterable<Object[]> data() {
        //here we can add any custom logic

       // return Arrays.asList(new Object[][] {
       //     {},
       //     {}
       // });

        return Arrays.asList(new Object[][] {
                {"Telerik academy Alpha", "IT Career Start in 6 Months - Telerik Academy Alpha"},
                {  "Telerik", "Telerik Academy: Programming and Digital Training"}
        });
    }

    @Parameterized.Parameter(value = 0)
    public String searchTerm;

    @Parameterized.Parameter(value = 1)
    public String expectedResult;

    @Test
    public void searchForTelerikAcademyAlpha() {
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
        searchField.sendKeys(searchTerm);

        //Submit the form
        searchField.sendKeys(Keys.ENTER);

        //Wait for a specific web element
        WebElement resultsNavigation = driver.findElement(By.xpath("//div[@role='navigation']"));
        wait.until(ExpectedConditions.visibilityOf(resultsNavigation));

        //Assert the results
        List<WebElement> resultsList = driver.findElements(By.xpath("//h3"));
        WebElement firstResult = resultsList.get(0);
        var actualResult = firstResult.getText();

        Assertions.assertEquals(expectedResult, actualResult);

        //tearDown()
        driver.close();
    }
}
