package org.sozkan.base;

import org.openqa.selenium.WebDriver;
import org.sozkan.driver.DriverManager;
import org.sozkan.pages.HomePage;
import org.sozkan.pages.HotelSearchPage;
import org.sozkan.pages.PaymentPage;
import org.sozkan.pages.TripSearchPage;
import org.sozkan.utils.ConfigFileReader;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest extends DriverManager {
    protected WebDriver driver;
    protected HomePage homePage;
    protected TripSearchPage tripSearchPage;
    protected PaymentPage paymentPage;
    protected HotelSearchPage hotelSearchPage;



    @BeforeMethod
    public void start(){
        String url = ConfigFileReader.readConfigFile("src/main/java/org/sozkan/resources/config.properties","url");

        driver = getDriver();
        driver.get(url);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        setUp();

    }

    public void setUp(){
        driver = getDriver();
        homePage = new HomePage(driver);
        tripSearchPage = new TripSearchPage(driver);
        paymentPage = new PaymentPage(driver);
        hotelSearchPage = new HotelSearchPage(driver);
    }


}

