package org.sozkan.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sozkan.driver.DriverManager;
import org.sozkan.utils.CommonMethods;

import java.util.List;

public class TripSearchPage {

    public TripSearchPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'ins-element-close-button')]/div")
    private WebElement closeModalButton;

    @FindBy(xpath = "//div[@class='action col']/button[@class='open']")
    private List<WebElement> tripSelectSeatButtons;

    @FindBy(xpath = "//*[name()='svg']//*[name()='a' and contains(@class, 'available') and contains(@class,'not-single-seat')]")
    private List<WebElement> availableSeats;

    @FindBy(xpath = "//span[@class='origin location']")
    private WebElement fromLocation;

    @FindBy(xpath = "//span[@class='destination location']")
    private WebElement toLocation;

    @FindBy(xpath = "//span[@class='amount']")
    private WebElement price;

    @FindBy(xpath = "//span[contains(text(),'Onayla ve Devam Et')]/parent::button")
    private WebElement confirmAndProceedButton;

    @FindBy(xpath = "//div[@class='header ']/span[@class='title']")
    private WebElement ticketPackagesModal;



    public List<WebElement> getTripSelectSeatButtons(){
        return tripSelectSeatButtons;
    }
    public List<WebElement> getAvailableSeats(){
        return availableSeats;
    }


    @Step("Selecting Seat")
    public void selectSeat(){
        try{
            CommonMethods.wait(30,closeModalButton);
            CommonMethods.closeModal();

        }catch (TimeoutException e){
            System.out.println("No modal found!");
        }

        for(WebElement button : getTripSelectSeatButtons()){
            CommonMethods.click(button);

            try{
                if(getAvailableSeats().isEmpty()){
                    CommonMethods.click(button.findElement(By.xpath("./following-sibling::button")));
                    continue;
                }else{
                    CommonMethods.click(availableSeats.get(0));
                    selectGender();
                    storeTripData(CommonMethods.getText(fromLocation),CommonMethods.getText(toLocation),CommonMethods.getText(price));
                    CommonMethods.click(confirmAndProceedButton);
                    CommonMethods.wait(10,ticketPackagesModal);
                    CommonMethods.closeModal();


                    break;
                }
            }catch (Exception e){
                CommonMethods.closeModal();
                CommonMethods.click(button.findElement(By.xpath("./following-sibling::button")));
                continue;
            }


        }
    }


    @Step("Selecting Gender")
    public void selectGender(){
        WebElement maleOption = DriverManager.getDriver().findElement(By.xpath("//button[contains(@class,'male')]"));
        WebElement femaleOption = DriverManager.getDriver().findElement(By.xpath("//button[contains(@class,'female')]"));

        String maleClass = maleOption.getAttribute("class");
        String femaleClass = femaleOption.getAttribute("class");

        boolean isMaleDisabled = maleClass.contains("disabled");
        boolean isFemaleDisabled = femaleClass.contains("disabled");


        if(isMaleDisabled && !isFemaleDisabled){
            CommonMethods.click(femaleOption);
            System.out.println("Female selected");
        }else if(isFemaleDisabled && !isMaleDisabled){
            CommonMethods.click(maleOption);
            System.out.println("Male selected");
        }else if(!isMaleDisabled && !isFemaleDisabled){
            CommonMethods.click(femaleOption);
            System.out.println("Female selected");
        }else {
            System.out.println("No gender selected");
        }
    }

    @Step("Storing Trip Data")
    public void storeTripData(String from, String to, String price){
        CommonMethods.writeToPropertiesFile(from,to,price);
    }




}
