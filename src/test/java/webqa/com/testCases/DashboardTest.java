package webqa.com.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webqa.com.base.BaseTest;
import webqa.com.report.ExtentTestManager;

import java.io.IOException;

public class DashboardTest extends BaseTest {

    public static Logger logger = LogManager.getLogger(websiteTest.class);
    public static websiteTest webSite=new websiteTest();

    @DataProvider(name = "city")
    public static Object[][] selCity() {
        return new Object[][]{{"Bengaluru","6367357224"},{"Mysuru","6367357225"},{"Hyderabad","6367357226"}};
    }

    @DataProvider(name = "phone")
    public static Object[][] phone() {
        return new Object[][]{{"6367357214"}, {"6367357215"},{"6367357216"}};
    }



    @Test(priority = 1, groups = {"regression"})
    public void verifyAbcdtest() throws IOException {
        logger.info("This test validates sales dashboard first call");
        ExtentTestManager.getTest().setDescription("This test validates sales dashboard first call");
        webSite.verifyServiceRequestCreation();
        switchToChildWindow();
        waitFor(2);
        scrollToText("Language");
        scrollToButtom();
        scrollToTop();
        scrollToText("Construction Purpose");
        scrollToButtom();
        waitFor(12);
    }

    @Test(priority = 1, groups = {"regression"})
    public void verifyAirTest() throws IOException {
        logger.info("This test validates sales dashboard Quotation creation");
        ExtentTestManager.getTest().setDescription("This test validates sales dashboard Quotation creation");
        webSite.verifyServiceRequestCreation();
        switchToChildWindow();
        waitFor(12);
    }
}
