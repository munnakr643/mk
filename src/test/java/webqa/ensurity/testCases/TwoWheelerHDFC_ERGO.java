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

public class TwoWheelerHDFC_ERGO extends BaseTest {


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
    public void verify2wRolloverPolicyPurchageWithHdfcErgo(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using HDFC insurer");
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
        scrollUpDown(0, 800);
        clickOnViewDetailsHdfcErgo();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        //prposalBasicDetailsHdfc();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        if (policyType.equalsIgnoreCase("saod")) {
            selectExpiryDate2("2022");
        } else if (policyType.equalsIgnoreCase("Bundle")) {
            selectExpiryDate2("2022");
        } else if (policyType.equalsIgnoreCase("5yrTP")) {
            selectExpiryDate2("2022");
        }
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(3);
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
    public void verify2wRolloverPolicyPurchageWithHdfcErgoBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before    july 2018 using HDFC insurer");
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
        scrollUpDown(0, 800);
        clickOnViewDetailsHdfcErgo();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
        buyNowAction();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
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
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(3);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "ncb")
    public void verify2wRolloverPolicyPurchageWithHdfcErgoBeforeJuly2018withDiffrentNcb(String ncb) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo using " + ncb + " ncb");
        ExtentTestManager.getTest().setDescription("This test validates verifyRolloverPolicyPurchageWithHdfcErgo using " + ncb + " ncb");
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
        selectRegistrationDate("2015");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent(ncb);
        selectPrevInsurer();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed("comprehensive");
        scrollToInsurer("hdfcergo");
        clickOnViewDetailsHdfcErgo();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(3);
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

    @Test(priority = -2, groups = {"regression"}, dataProvider = "breakin")
    public void verify2wBreakInPolicyPurchageWithHdfcErgo(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakInPolicyPurchageWithHdfcErgo for " + polExpType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + polExpType + " which Regestration date after before 2018 using HDFC insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectTwoWheeler();
        enterCustomerName("webQAHDFC"+time());
        clickOnNextBtn();
        selectMMV();
        rtoLocation();
        selectRegistrationDate("2015");
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
        scrollUpDown(0, 800);
        clickOnViewDetailsHdfcErgo();
        getSubtotal();
        String idv="";
        if (policyType.equalsIgnoreCase("1yrCp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        //prposalBasicDetailsHdfc();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        if (polExpType.contains("90")){
            selectFromDropDown("Bharti");
            enterPrevPolicyNum("AR00" + randomeNum8());
            selectExpiryDate("2021");
            clickOnNext();}
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (policyType.equalsIgnoreCase("1yrCp")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(1);
        clickOnDownloadPolicy();
        waitFor(3);
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
    public void verify2wNewVehiclePolicyPurchageWithHdfcErgo(String policyType) throws IOException {
        logger.info("This test validates verifyNewVehiclePolicyPurchageWithHdfcErgo for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verifyNewVehiclePolicyPurchageWithHdfcErgo for " + policyType + " Plan");
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
        selectNewVehicle();
        clickOnNext();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("5tp")){
            changePolicyTypeForNewVehicle("5tp");
        }
        isPlanPageDisplayed();
        isNewVehiclePolTypeDisplayed(policyType);
        scrollUpDown(0, 900);
        waitFor(1);
        if (policyType.equalsIgnoreCase("5tp")){
            clickBuyNowHdfcErgo();
        }else {
            clickOnViewDetailsHdfcErgo();
            getSubtotal();
            buyNowAction();}
        //prposalBasicDetailsHdfc();
        String idv="";
        if (!policyType.equalsIgnoreCase("5tp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        selcectManufactureDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("5tp")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(3);
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
    public void verify2wRolloverPolicyPurchageAddAddonsWithHdfcErgo(String addons) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo for " + addons + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + addons + " which Regestration date after july 2018 using HDFC insurer");
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
        selectPrevPolType("saod");
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed("saod");
        clickOnAddons();
        selectAddOns(addons);
        clickOnApply();
        isInsurerDisplayedOnPlanpage();
        isPlanPageDisplayed();
        scrollUpDown(0, 800);
        clickOnViewDetailsHdfcErgo();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        //prposalBasicDetailsHdfc();
        selectSalutation("MR");
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGenderUpperCase("MALE");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectAsSame();
        clickOnNext();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDownHdfc("SIBLING");
        clickOnNext();
        entervehicleNumer("MH-01-DA-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        selectExpiryDate2("2022");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(3);
        clickOnDownloadPolicy();
        waitFor(3);
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
