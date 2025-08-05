package org.sozkan.tests;

import org.sozkan.base.BaseTest;
import org.sozkan.utils.LoggerUtil;
import org.testng.annotations.Test;

public class PurchasingBusTicketAndHotelFilterTest extends BaseTest {

    @Test(priority = 1, description = "Purchasing Bus Ticket")
    public void testPurchasingBusTicket(){
        homePage.isHomePage();
        homePage.selectLocations("İstanbul Anadolu","İzmir");
        tripSearchPage.selectSeat();
        paymentPage.checkPaymentDetails();
        LoggerUtil.info("Bus Ticket Purchased Successfully!");
    }

    @Test(priority = 2, description = "Hotel Filter")
    public void testHotelFilter(){
        homePage.isHomePage();
        homePage.clickOnHotel();
        homePage.selectHotelOrigin("İstanbul");
        hotelSearchPage.validateFilterResult("Yarım Pansiyon");
        hotelSearchPage.validateSorting();
    }
}
