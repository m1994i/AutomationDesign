package automationDesign;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import org.openqa.selenium.chrome.ChromeDriver;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class automationTest {

        WebDriver driver;
        ExtentReports extent;
        ExtentTest test;
        @BeforeMethod
        public void setDriver(){
                String path=System.getProperty("user.dir")+"\\reports\\report.html";
                ExtentSparkReporter reporter=new ExtentSparkReporter(path);
                reporter.config().setReportName("Web Automation Test");
                reporter.config().setDocumentTitle("Test Results");

                extent = new ExtentReports();
                extent.attachReporter(reporter);
                extent.setSystemInfo("Tester", "Milan Ivanovic");

                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                driver = new ChromeDriver(options);
                driver.manage().window().maximize();
                driver.get("https://planetasport.rs/?gad_source=1&gclid=CjwKCAjwkJm0BhBxEiwAwT1AXEZGs1mPiPrlwoPi2Zkrd2niRHb3bssCNAAj_CrIcD1xUNX4mUlFcBoC6TwQAvD_BwE");
                driver.findElement(By.id("btn-cookie-allow")).click();
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
                test = extent.createTest("Test Functions", "Testiranje funkcionalnosti sajta");

        }
        @Test
        public void testFunctions() throws InterruptedException {


        WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement men = driver.findElement(By.xpath("//a[@class='parent' and text()='MUŠKARCI']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(men).perform();
        //Assert.assertTrue(men.);

        driver.findElement(By.xpath("(//a[@href='https://planetasport.rs/muskarci/obuca/patike-za-fudbal.html'])[1]")).click();
        //driver.findElement(By.xpath("(//a[@class='page'])[1]")).click();


        WebElement shoes = driver.findElement(By.cssSelector("div[id='product-item-info_1427989'] a[class='product-item-link']"));
        wait.until(ExpectedConditions.elementToBeClickable(shoes));
        shoes.click();

        String expectedURL = "https://planetasport.rs/patike-predator-club-tf-m-ig7711.html";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(expectedURL, actualURL);
        test.pass("Ocekivana stranica je ucitana");

        driver.findElement(By.xpath("//div[@id='option-label-size-158-item-54']")).click();


        WebElement buttonCart = driver.findElement(By.xpath("//button[@class='action primary tocart']"));
        buttonCart.click();
        Thread.sleep(1000);
        Assert.assertTrue(buttonCart.isDisplayed(), "Element nije dodat u korpu");
        test.pass("Proizvod je dodat u korpu");


        driver.navigate().back();
        driver.navigate().back();
        Thread.sleep(1000);

        WebElement search = driver.findElement(By.id("search"));
        search.click();
        search.sendKeys("adidas");
        Thread.sleep(1000);
        List<WebElement> showedProducts = driver.findElements(By.cssSelector("a[onclick='klevu_analytics.stopClickDefault( event );']"));
        for (WebElement showedProd:showedProducts ){
                if (showedProd.getText().equalsIgnoreCase("adidas")){
                        showedProd.click();
                        break;
                }
                Assert.assertTrue(showedProd.isSelected(), "Adidas nije kliknut");

        }
                test.pass("Predlog adidas je kliknut");
        driver.findElement(By.id("zsdev-popup-newsletter-close-btn")).click();
        driver.findElement(By.xpath("(//span[@class='am-collapse-icon'])[5]")).click();
        driver.findElement(By.xpath("(//span[contains(@class,'am-collapse-icon')])[6]")).click();
        driver.findElement(By.xpath("(//span[@class='label' and text()='PATIKE ZA TRČANJE'])[2]")).click();

        WebElement checkBoxBelePatike = driver.findElement(By.xpath("//a[@href='https://planetasport.rs/catalogsearch/result/index/?cat=24634&lista=35452&q=adidas']"));
        //wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-mask")));
        wait.until(ExpectedConditions.visibilityOf(checkBoxBelePatike));
        wait.until(ExpectedConditions.elementToBeClickable(checkBoxBelePatike));
        checkBoxBelePatike.click();

        //Assert.assertTrue(checkBoxBelePatike.isSelected(), "Checkbox nije oznacen");
        test.pass("Checkbox oznacen");

        }
        @AfterMethod
        public void tearDown(){
                extent.flush();

        }
        @AfterTest
        public void tearDownQuit() {
                driver.quit();

        }

}
