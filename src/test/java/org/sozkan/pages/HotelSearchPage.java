package org.sozkan.pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.sozkan.utils.CommonMethods;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelSearchPage {

    public HotelSearchPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='filter-item__name']")
    private List<WebElement> filterNameList;
    @FindBy(xpath = "//div[@class='hotel-list js-hotel-list']/div[@class='hotel-item  ']")
    private List<WebElement> hotelList;

    @FindBy(xpath = "//button[@class='head-filter__btn']")
    private WebElement sortingButton;
    @FindBy(xpath="//ul[@class='content']/li/button[contains(text(),'Fiyat (Düşükten yükseğe)')]")
    private WebElement priceAscendingButton;
    @FindBy(xpath = "//div[@class='hotel-price__amount']")
    private List<WebElement> priceList;

    public List<WebElement> getFilterNameList(){
        return filterNameList;
    }
    public List<WebElement> getHotelList(){
        return hotelList;
    }
    public List<WebElement> getPriceList(){
        return priceList;
    }

    @Step("Validating Filter Result")
    public void validateFilterResult(String filterName){
        CommonMethods.waitUntilElementIsClickable(10,filterNameList.get(0));
        WebElement selectedFilter = filterNameList.stream().filter(e -> e.getText().contains(filterName)).findFirst().orElse(null);
        WebElement checkbox = selectedFilter.findElement(By.xpath("./preceding-sibling::input"));
        if(!checkbox.isSelected()){
            CommonMethods.click(checkbox);
        }
        WebElement filterResult = selectedFilter.findElement(By.xpath("./span"));
        String resultText = filterResult.getText().replace("(","").replace(")","").trim();
        Integer result = Integer.parseInt(resultText);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        CommonMethods.waitUntilElementIsClickable(10,hotelList.get(0));

        scrollPageUntilLastHotel();

        System.out.println("Actual: "+ result+" Expected: "+ hotelList.size());
        Assert.assertEquals(result,getHotelList().size());

    }

    @Step("Validating Sorting")
    public void validateSorting(){
        CommonMethods.moveToElement(sortingButton);
        CommonMethods.click(sortingButton);
        CommonMethods.click(priceAscendingButton);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        scrollPageUntilLastHotel();


        List<WebElement> unsortedPriceList = getPriceList();
        List<Integer> prices = new ArrayList<>();


        for(WebElement element : unsortedPriceList){
            CommonMethods.moveToElement(element);

            String p = CommonMethods.getText(element).replace("TL","").replace(".","").trim();
            prices.add(Integer.parseInt(p));
        }
        List<Integer> sortedPrices = prices.stream().sorted().collect(Collectors.toList());

        System.out.println("Actual: "+ sortedPrices+" Expected: "+ prices.toString());
        System.out.println("Actual: "+ sortedPrices.size()+" Expected: "+ prices.size());
        Assert.assertEquals(sortedPrices,prices);

    }

    public void scrollPageUntilLastHotel(){
        while(true){

            Integer previousSize = getHotelList().size();
            //CommonMethods.waitUntilElementIsClickable(10,hotelList.get(previousSize-1));
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            CommonMethods.moveToElement(getHotelList().get(previousSize-1));
            Integer currentSize = getHotelList().size();
            if(previousSize == currentSize){
                break;
            }
        }
    }

}
