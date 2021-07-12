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

public class FourWheelerBAJAJ extends BaseTest {


    public static Logger logger = LogManager.getLogger(BaseTest.class);

    @DataProvider(name = "policyTypeOD")
    public static Object[][] policyType() {
        return new Object[][]{
                {"saod"}, {"Bundle"},{"3yrTP"}};
    }

    @DataProvider(name = "policyTyped")
    public static Object[][] policyTyped() {
        return new Object[][]{
                {"saod"},{"1yrCp"}};
    }

    @DataProvider(name = "breakin")
    public static Object[][] breakin() {
        return new Object[][]{
                {"Expired within 90 days","1yrCp"},
                {"Expired more than 90 days ago","1yrCp"},{"Don’t know previous policy details","1yrCp"},
                {"Expired within 90 days","1yrTP"},
                {"Expired more than 90 days ago","1yrTP"},{"Don’t know previous policy details","1yrTP"}
        };
    }

    @DataProvider(name = "polType")
    public static Object[][] polType() {
        return new Object[][]{{"1yrCp"}, {"1yrTP"}};
    }

    @DataProvider(name = "NewVehiclePolType")
    public static Object[][] NewVehiclePolType() {
        return new Object[][]{{"1OD + 3TP"},
                {"3TP"}};
    }

    @DataProvider(name = "addons")
    public static Object[][] addons() {
        return new Object[][]{
                {"Roadside Assistance"},
                {"Zero Depreciation"},{"Invoice Cover"},{"Engine Protector"},{"NCB Protection"},{"Consumable"},{"Key and Lock Replacement"},{"Tyre Protector"},{"RIM Damage Cover"},{"Loss of Personal Belongings"},{"Geographic Extension"},{"Secure Towing"}
                ,{"Emergency Transport and Hotel Expenses"},{"Repair Glass/Fiber/plastic"},{"PA to unnamed passenger"},{"PA to unnamed Paid Driver"},
                {"Legal Liability to Paid Driver"}};
    }




    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithBajaj(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithBajaj for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Bajaj insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        //enterCustomerName("webQA");
        clickOnNextBtn();
        select4wMMV();
        rtoLocation();
        selectRegistrationDate("2019");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsBajaj();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
        buyNowAction();
        enterFirstName("Rohit");
        enterLastName("Dubey");
        selectDobAction("1989");
        selectGenderUpperCase("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DT-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        if (policyType.equalsIgnoreCase("3yrTP")){
            selectExpiryDate("2021");
        }
        else if (policyType.equalsIgnoreCase("1yrTP")){
            selectExpiryDate("2021");
        }
        else {
            selectExpiryDate("2020");
            selectExpiryDate2Bajaj("2018");
            selectFromDropDown("Shriram");
            enterPrevTpAddress("Mumbai");
            enterPrevTpPolicyNumber("AR01" + randomeNum8());
            enterPrevPolicyClaim("0");
            scrollUpDown(0,100);
            enterPrevTpTenure("3");
        }
        clickOnNext();
        clickOnReviewNSubmit();
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        clickOnOk();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isBajajPaymentPageDisplayed();
        proceedBajajPayment();
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify4wBreakInPolicyPurchageWithBajaj(String polExpType,String policyType) throws IOException {
        logger.info("This test validates verify4wBreakInPolicyPurchageWithBajaj for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify4wBreakInPolicyPurchageWithBajaj for " + policyType + " which Regestration date after july 2018 using Bajaj insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        enterCustomerName("webQA");
        clickOnNextBtn();
        select4wMMV();
        rtoLocation();
        selectRegistrationDate("2018");
        clickOnNext();
        selectPolicyExpiry(polExpType);
        if (polExpType.contains("90")){
            prevPolicyClaim("No");
            prevNcbPercent("20%");
            selectPrevInsurer();}
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsBajaj();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Sanjeet");
        enterLastName("Singh");
        selectDobAction("1989");
        selectGenderUpperCase("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DT-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        if (polExpType.contains("90")){
            enterPrevPolicyNum("AR00" + randomeNum8());
            selcectPrevPolExpDateBreakin("2021");
            clickOnNext();}
        clickOnReviewNSubmit();
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        clickOnOk();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isBajajPaymentPageDisplayed();
        proceedBajajPayment();
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchageWithAddonsForBajaj(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageWithAddonsForBajaj for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageWithAddonsForBajaj for " + addons + " addons");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        //enterCustomerName("webQA");
        clickOnNextBtn();
        select4wMMV();
        rtoLocation();
        selectRegistrationDate("2018");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        waitFor(1);
        clickOngenerateQuote();
        isPlanPageDisplayed();
//        scrollUpDown(0, 200);
        clickOnAddons();
        scrollToText(addons);
        selectAddOns(addons);
        clickOnApply();
        isInsurerDisplayedOnPlanpage();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed("Comprehensive");
//        scrollUpDown(0, 200);
        clickOnViewDetailsBajaj();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
        buyNowAction();
        enterFirstName("Rohit");
        enterLastName("Dubey");
        selectDobAction("1989");
        selectGenderUpperCase("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DT-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        clickOnOk();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isBajajPaymentPageDisplayed();
        proceedBajajPayment();
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
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
