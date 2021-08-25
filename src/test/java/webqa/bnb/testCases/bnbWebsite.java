package webqa.bnb.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webqa.bnb.base.BaseTest;
import webqa.bnb.report.ExtentTestManager;

import java.io.IOException;

public class bnbWebsite extends BaseTest {


    public static Logger logger = LogManager.getLogger(bnbWebsite.class);
    String crnName;
    @DataProvider(name = "city")
    public static Object[][] selCity() {
        return new Object[][]{{"Bengaluru","6367357224"},{"Mysuru","6367357225"},{"Hyderabad","6367357226"}};
    }

    @DataProvider(name = "phone")
    public static Object[][] phone() {
        return new Object[][]{{"6367357214"}, {"6367357215"},{"6367357216"}};
    }


    @Test(priority = 1, groups = {"regression"})
    public void verifyServiceRequestCreation() throws IOException {
        logger.info("This test validates create service request for Bengaluru city");
        ExtentTestManager.getTest().setDescription("This test validates create service request for Bengaluru city");
        String phoneNum="65"+randomeNum8();
        launchUrl(baseURL);
        selectCityName("Bengaluru");
        isBnbWebHomePageDisplay();
        clickOnLetsBuildBtn();
        verifyLetsBuildForm();
        crnName=enterName("TestQA"+randomeNum());
        enterEmail("qatest"+randomeNum()+"@yopmail.com");
        enterMobileNumber(phoneNum);
        selectCity("Bengaluru");
        clickOnBookFreeConsultation();
        verifyThankYouPage();

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "city")
    public void verifySRCreationForCity(String city,String phone) throws IOException {
        logger.info("This test validates create service request for " + city + " city");
        ExtentTestManager.getTest().setDescription("This test validates create service request for "+city+" city");
        launchUrl(baseURL);
        selectCityName(city);
        isBnbWebHomePageDisplay();
        clickOnLetsBuildBtn();
        verifyLetsBuildForm();
        enterName("TestQA"+city);
        enterEmail("qatest057@gmail.com");
        enterMobileNumber(phone);
        selectCity(city);
        clickOnBookFreeConsultation();
        verifyThankYouPage();
    }




}
