package org.sozkan.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sozkan.utils.CommonMethods;
import org.sozkan.utils.ConfigFileReader;
import org.sozkan.utils.LoggerUtil;
import org.testng.Assert;

public class PaymentPage {

    public PaymentPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[@class='intro-table']/table/tbody/tr[2]/td[1]")
    private WebElement fromLocation;

    @FindBy(xpath = "//div[@class='intro-table']/table/tbody/tr[2]/td[2]")
    private WebElement toLocation;

    @FindBy(xpath = "//div[@id='amount']")
    private WebElement price;


    @Step("Checking Payment Details")
    public void checkPaymentDetails(){
        String from = ConfigFileReader.readConfigFile("src/main/java/org/sozkan/resources/data.properties","from");
        String to = ConfigFileReader.readConfigFile("src/main/java/org/sozkan/resources/data.properties","to");
        String priceValue = ConfigFileReader.readConfigFile("src/main/java/org/sozkan/resources/data.properties","price");
        Assert.assertEquals(CommonMethods.getText(fromLocation),from);
        Assert.assertEquals(CommonMethods.getText(toLocation),to);
        Assert.assertEquals(CommonMethods.getText(price),priceValue);
        LoggerUtil.info("Payment Details Checked Successfully!");
    }


}
