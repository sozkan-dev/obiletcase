package org.sozkan.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sozkan.driver.DriverManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Driver;
import java.time.Duration;
import java.util.Properties;

public class CommonMethods {
    public static void wait(Integer seconds, WebElement element) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOf(element));
    }
    public static void waitUntilElementIsClickable(Integer seconds,WebElement element) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitUntilVisibilityOfElementLocated(Integer seconds,By by) {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(seconds));
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void click(WebElement element) {
        LoggerUtil.info("Clicked on " + element.getText());
        waitUntilElementIsClickable(15, element);
        element.click();

    }

    public static void sendKeys(WebElement element, String text) {
        LoggerUtil.info("Sent keys " + text + " to " + element.getText());
        wait(15, element);
        element.sendKeys(text + Keys.ENTER);

    }
    public static String getText(WebElement element){
        wait(15,element);
        return element.getText();
    }

    public static String getCurrentURL(){
        return DriverManager.getDriver().getCurrentUrl();
    }

    public static void selectByVisibleText(WebElement element, String text){
        try{
            wait(10, element);

            Select select = new Select(element);
            select.selectByVisibleText(text);
        }catch (NoSuchElementException e){
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void closeModal(){
        Actions actions = new Actions(DriverManager.getDriver());
        actions.sendKeys(Keys.ESCAPE).perform();
    }
    public static void moveToElement(WebElement element){
        wait(10,element);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element).perform();
    }

    public static File writeToPropertiesFile(String from, String to, String price){
        Properties properties = new Properties();
        properties.setProperty("from",from);
        properties.setProperty("to",to);
        properties.setProperty("price",price);

        File file = new File("src/main/java/org/sozkan/resources/data.properties");

        try(FileOutputStream fos = new FileOutputStream(file)){
            properties.store(fos, "Temp Data");
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }


}
