package org.sozkan.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sozkan.utils.CommonMethods;
import org.sozkan.utils.LoggerUtil;
import org.testng.Assert;

import java.util.List;

public class HomePage {

    public HomePage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//ob-select[@id='origin']/input")
    private WebElement fromLocation;
    @FindBy(xpath = "//ob-select[@id='origin']/div[@class='results'][1]/ul/li")
    private List<WebElement> fromList;


    @FindBy(xpath = "//ob-select[@id='destination']/input")
    private WebElement toLocation;
    @FindBy(xpath = "//ob-select[@id='destination']/div[@class='results'][1]/ul/li")
    private List<WebElement> toList;

    @FindBy(xpath = "//button[contains(text(),'Otobüs Ara')]")
    private WebElement searchButton;


    @FindBy(xpath = "//li[@class='rentcar ']/a[@href='/otel']")
    private WebElement hotel;
    @FindBy(xpath = "//input[@id='origin-input']")
    private WebElement originInput;
    @FindBy(xpath = "//ul/li[@class='item']/div/div[@class='origin-name']")
    private List<WebElement> hotelOriginList;
    @FindBy(xpath = "//button[@id='search-button']")
    private WebElement searchButtonHotel;



    public List<WebElement> getHotelOriginList(){
        return hotelOriginList;
    }



    @Step("Verify Home Page")
    public void isHomePage(){
           String expectedUrl = "https://www.obilet.com/";
           String actualUrl = CommonMethods.getCurrentURL();
           Assert.assertEquals(actualUrl, expectedUrl);
    }

    @Step("Selecting {from} and {to} Locations")
    public void selectLocations(String from, String to){

        try{
            fromLocation.clear();
            CommonMethods.click(fromLocation);
            WebElement fromLocationItem=
                    fromList.stream().filter(e -> e.getText().contains(from)).findFirst().orElse(null);
            CommonMethods.click(fromLocationItem);
        }catch (Exception e){
            LoggerUtil.error("From Location is not in the list!");
        }

        try{
            toLocation.clear();
            CommonMethods.click(toLocation);
            WebElement toLocationItem = toList.stream().filter(e -> e.getText().contains(to)).findFirst().orElse(null);
            CommonMethods.click(toLocationItem);
        }catch (Exception e){
            LoggerUtil.error("To Location is not in the list!");
        }

        clickOnSearchButton();

    }

    @Step("Clicking on Search Button")
    public void clickOnSearchButton(){
        CommonMethods.click(searchButton);
    }

    @Step("Clicking on Hotel")
    public void clickOnHotel(){
        CommonMethods.click(hotel);
    }

    @Step("Selecting Hotel Origin")
    public void selectHotelOrigin(String origin){

        CommonMethods.waitUntilElementIsClickable(10,searchButtonHotel);
        CommonMethods.click(originInput);
        WebElement originItem = hotelOriginList.stream().filter(e -> e.getText().contains(origin)).findFirst().orElse(null);
        try{
            CommonMethods.click(originItem);
        }catch (NullPointerException e){
            LoggerUtil.error("Hotel Origin is not in the list!");
        }
        CommonMethods.click(searchButtonHotel);
    }
}
