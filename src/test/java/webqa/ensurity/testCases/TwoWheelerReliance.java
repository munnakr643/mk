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

public class TwoWheelerReliance extends BaseTest {


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
    public void verify2wRolloverPolicyPurchageWithReliance(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithReliance for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using reliance insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        //enterCustomerName("webQA");
        clickOnNextBtn();
        selectMMV();
        rtoLocation();
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
        scrollUpDown(0, 200);
        clickOnViewDetailsReliance();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectTitle("Mr");
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        selectMaritalStatus("Single");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        waitFor(1);
        EnterAddressForReliance("Church Road", "1st street", "South Mumbai", "400001", "Maharashtra", "Mumbai", "Mumbai");
        clickOnNext();
        waitFor(1);
        enterNomineeName("Rishi Singh");
        selectDobAction("1996");
        selectFromDropDown("Brother");
        enterPermAddress("Church Road");
        clickOnNext();
        waitFor(1);
        entervehicleNumer("MH-01-DE-" + randomeNum());
        selcectManufactureDate("2019");
        enterChassisNumber("CH3234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        isVehicleHypothecated("No");
        clickOnNext();
        selectFromDropDown("Bharati AXA");
        selectExpiryDate("2021");
        enterPrevPolicyNum("AR00" + randomeNum8());
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isReliancePaymentPageDisplayed();
        proceedReliancePayment();
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify2wRolloverPolicyPurchageWithRelianceBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithReliance for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before july 2018 using reliance insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        selectMMV();
        rtoLocation();
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
        scrollUpDown(0, 200);
        clickOnViewDetailsReliance();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectTitle("Mr");
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        selectMaritalStatus("Single");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        waitFor(1);
        EnterAddressForReliance("Church Road", "1st street", "South Mumbai", "400001", "Maharashtra", "Mumbai", "Mumbai");
        clickOnNext();
        waitFor(1);
        enterNomineeName("Rishi Singh");
        selectDobAction("1996");
        selectFromDropDown("Brother");
        enterPermAddress("Church Road");
        clickOnNext();
        waitFor(1);
        entervehicleNumer("MH-01-DE-" + randomeNum());
        selcectManufactureDate("2018");
        enterChassisNumber("CH3234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        isVehicleHypothecated("No");
        clickOnNext();
        selectFromDropDown("Bharati AXA");
        selectExpiryDate("2021");
        enterPrevPolicyNum("AR00" + randomeNum8());
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
        isReliancePaymentPageDisplayed();
        proceedReliancePayment();
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

            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}


    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify2wNewVehiclePolicyPurchageWithReliance(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithReliance for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using reliance insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        //enterCustomerName("webQA");
        clickOnNextBtn();
        selectMMV();
        rtoLocation();
        selectNewVehicle();
        clickOnNext();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("5tp")){
            changePolicyTypeForNewVehicle("5tp");
        }
        isPlanPageDisplayed();
        isNewVehiclePolTypeDisplayed(policyType);
        scrollUpDown(0, 200);
        clickOnViewDetailsReliance();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("5tp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectTitle("Mr");
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        selectMaritalStatus("Single");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        waitFor(1);
        EnterAddressForReliance("Church Road", "1st street", "South Mumbai", "400001", "Maharashtra", "Mumbai", "Mumbai");
        clickOnNext();
        waitFor(1);
        enterNomineeName("Rishi Singh");
        selectDobAction("1996");
        selectFromDropDown("Brother");
        enterPermAddress("Church Road");
        clickOnNext();
        waitFor(1);
        entervehicleNumer("MH-01-DE-" + randomeNum());
        selcectManufactureDate("2021");
        enterChassisNumber("CH3234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        isVehicleHypothecated("No");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("5tp")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isReliancePaymentPageDisplayed();
        proceedReliancePayment();
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
    public void verify2wBreakInPolicyPurchageWithReliance(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakInPolicyPurchageWithReliance for " + policyType + " with "+polExpType);
        ExtentTestManager.getTest().setDescription("This test validates 2wBreakInPolicyPurchageWithReliance for " + policyType + " with "+polExpType);
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        selectMMV();
        rtoLocation();
        selectRegistrationDate("2018");
        clickOnNext();
        selectPolicyExpiry(polExpType);
        if (polExpType.contains("90")){
            prevPolicyClaim("No");
            prevNcbPercent("0%");
            selectPrevInsurer();}
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsReliance();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectTitle("Mr");
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        selectMaritalStatus("Single");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        waitFor(1);
        EnterAddressForReliance("Church Road", "1st street", "South Mumbai", "400001", "Maharashtra", "Mumbai", "Mumbai");
        clickOnNext();
        waitFor(1);
        enterNomineeName("Rishi Singh");
        selectDobAction("1996");
        selectFromDropDown("Brother");
        enterPermAddress("Church Road");
        clickOnNext();
        waitFor(1);
        entervehicleNumer("MH-01-DE-" + randomeNum());
        selcectManufactureDate("2018");
        enterChassisNumber("CH3234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        isVehicleHypothecated("No");
        clickOnNext();
        if (!polExpType.contains("Don’t know")){
            selectFromDropDown("Bharati AXA");
            selcectPrevPolExpDateBreakin("2021");
            enterPrevPolicyNum("AR00" + randomeNum8());
            clickOnNext();}
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isReliancePaymentPageDisplayed();
        proceedReliancePayment();
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
