package webqa.ensurity.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webqa.ensurity.base.BaseTest;
import webqa.ensurity.report.ExtentTestManager;

import java.io.IOException;

public class TwoWheelerFUTURE_G extends BaseTest {


    public static Logger logger = LogManager.getLogger(TwoWheelerBAJAJ.class);

    @DataProvider(name = "policyType")
    public static Object[][] policyType() {
        return new Object[][]{
                {"saod"}, {"Bundle"}, {"5yrTP"}};
    }

    @DataProvider(name = "policyTypeIN")
    public static Object[][] policyTypeIN() {
        return new Object[][]{{"1yrCp"},
                {"saod"}, {"Bundle"}, {"5yrTP"},
                {"1yrTP"}};
    }

    @DataProvider(name = "polType")
    public static Object[][] polType() {
        return new Object[][]{{"1yrCp"},{"1yrTP"}};
    }

    @DataProvider(name = "ncb")
    public static Object[][] ncb() {
        return new Object[][]{{"0%"},{"20%"},{"25%"},{"35%"},{"45%"},{"50%"},{"I don't remember my NCB"}};
    }

    @DataProvider(name = "rollOver")
    public static Object[][] rollOver() {
        return new Object[][]{
                {"saod"}, {"Bundle"}};
    }

    @DataProvider(name = "breakin")
    public static Object[][] breakin() {
        return new Object[][]{
                {"Expired within 90 days","1yrCp"},
                {"Expired more than 90 days ago","1yrCp"},
                {"Don’t know previous policy details","1yrCp"},
                {"Expired within 90 days","1yrTP"},
                {"Expired more than 90 days ago","1yrTP"},
                {"Don’t know previous policy details","1yrTP"}
        };
    }

    @DataProvider(name = "NewVehiclePolType")
    public static Object[][] NewVehiclePolType() {
        return new Object[][]{{"1OD + 5TP"},
                {"5TP"}};
    }

    @DataProvider(name = "policyTyped")
    public static Object[][] policyTyped() {
        return new Object[][]{
                {"saod"},{"1yrCp"}};
    }

    @DataProvider(name = "addons")
    public static Object[][] addons() {
        return new Object[][]{
                {"Zero Depreciation"}};
    }


    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyType")
    public void verify2wRolloverPolicyPurchageWithFuture(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithFuture for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verifyRolloverPolicyPurchageWithFuture for " + policyType + " Plan Regestration date after july 2018 using future insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        enterMMVDetails("Bajaj","Discover","DTS-Si ELECTRIC");
        enterRtoCode("BR01");
        selectRegistrationDate("2019");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        selectPrevPolType(policyType);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
        scrollUpDown(0, 100);
        clickOnViewDetailsfutureGeneral();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectSalutation("MS");
        enterFirstName("Shalini");
        enterMiddleName("R");
        enterLastName("Singh");
        selectDobAction("1999");
        selectGender("Female");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectMaritalStatus("Single");
        selectFromDropDown("Accountant");
        clickOnNext();
        EnterAddressForFuture("Belly road", "1st street", "South Patna", "800001", "Bihar", "Patna");
        clickOnNext();
        enterNomineeName("Sumit Singh");
        selectDobAction("1991");
        selectFromDropDown("Brother");
        clickOnNext();
        entervehicleNumer("BR01PB" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectFromDropDown("HDFC");
        polExpDateRollover();
        enterAddressLine1FL("Balley Road");
        enterAddressLine2FL("Patna");
        enterePreviousInsurerPincode("800001");
        enterPrevTpPolicyNumber("Ar00"+randomeNum8());
        selectInsurerFromDropDown("Bharti",2);
        prevTpStartDate("2018",9);
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        clickOnSendAnyway();
        isFutureGeneralPaymentPageDisplayed();
        enterFuturePaymentDetailsAndProceed("5123456789012346","1222","123","Shalini Singh");
        waitFor(5);
        refreshPage();
        switchToTab(parentWindow);
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(9);
        if (isPolicyDownloadMessageDispalyed()){
            policyDownloadMessage();
        }else {
            driver.get("chrome://downloads/");
            waitFor(3);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify2wRolloverPolicyPurchageWithFutureBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithFuture for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verifyRolloverPolicyPurchageWithFuture for " + policyType + " Plan Regestration date after july 2018 using future insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        enterMMVDetails("Bajaj","Discover","DTS-Si ELECTRIC");
        enterRtoCode("BR01");
        selectRegistrationDate("2018");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
        waitFor(3);
        scrollUpDown(0, 100);
        clickOnViewDetailsfutureGeneral();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectSalutation("MS");
        enterFirstName("Shalini");
        enterMiddleName("R");
        enterLastName("Singh");
        selectDobAction("1999");
        selectGender("Female");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectMaritalStatus("Single");
        selectFromDropDown("Accountant");
        clickOnNext();
        EnterAddressForFuture("Belly road", "1st street", "South Patna", "800001", "Bihar", "Patna");
        clickOnNext();
        enterNomineeName("Sumit Singh");
        selectDobAction("1991");
        selectFromDropDown("Brother");
        clickOnNext();
        entervehicleNumer("BR01PB" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectFromDropDown("HDFC");
        selectExpiryDate("2021");
        enterAddressLine1FL("Balley Road");
        enterAddressLine2FL("Patna");
        enterePreviousInsurerPincode("800001");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        clickOnSendAnyway();
        isFutureGeneralPaymentPageDisplayed();
        enterFuturePaymentDetailsAndProceed("5123456789012346","1222","123","Shalini Singh");
        waitFor(5);
        refreshPage();
        switchToTab(parentWindow);
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(9);
        if (isPolicyDownloadMessageDispalyed()){
            policyDownloadMessage();
        }else {
            driver.get("chrome://downloads/");
            waitFor(3);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify2wNewVehiclePolicyPurchageWithFuture(String policyType) throws IOException {
        logger.info("This test validates verify2wNewVehiclePolicyPurchageWithFuture for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify2wNewVehiclePolicyPurchageWithFuture for " + policyType + " plan");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("future"+time());
        clickOnNextBtn();
        enterMMVDetails("Bajaj","Discover","DTS-Si ELECTRIC");
        enterRtoCode("BR01");
        selectNewVehicle();
        clickOnNext();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("5tp")){
            changePolicyTypeForNewVehicle("5tp");
        }
        isPlanPageDisplayed();
        isNewVehiclePolTypeDisplayed(policyType);
        scrollToButtom();
        clickOnViewDetailsfutureGeneral();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectSalutation("MS");
        enterFirstName("Shalini");
        enterMiddleName("R");
        enterLastName("Singh");
        selectDobAction("1999");
        selectGender("Female");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectMaritalStatus("Single");
        selectFromDropDown("Accountant");
        clickOnNext();
        EnterAddressForFuture("Belly road", "1st street", "South Patna", "800001", "Bihar", "Patna");
        clickOnNext();
        enterNomineeName("Sumit Singh");
        selectDobAction("1991");
        selectFromDropDown("Brother");
        clickOnNext();
        entervehicleNumer("BR01PB" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        selectPrevMonthDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        clickOnSendAnyway();
        isFutureGeneralPaymentPageDisplayed();
        enterFuturePaymentDetailsAndProceed("5123456789012346","1222","123","Shalini Singh");
        waitFor(5);
        refreshPage();
        switchToTab(parentWindow);
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(9);
        if (isPolicyDownloadMessageDispalyed()){
            policyDownloadMessage();
        }else {
            driver.get("chrome://downloads/");
            waitFor(3);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}

    }
}
