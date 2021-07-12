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

public class TwoWheeler extends BaseTest {


    public static Logger logger = LogManager.getLogger(BaseTest.class);

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

    @Test(priority = -1, groups = {"regression"}, dataProvider = "policyType")
    public void verify2wRolloverPolicyPurchageWithDigit(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Digit insurer");
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
        selectRegistrationDate("2019");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        selectPrevPolType(policyType);
        waitFor(1);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsDigit();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCity("Mumbai");
        selectDistrict("Mumbai");
        selectCountry();
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AD" + randomeNum());
        //  entervehicleNumer("MH01AD5261");
        selcectManufactureDate("2019");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isDigitPaymentPageDisplayed();
        enterDigitPaymentDetailsAndProceed("5123456789012346","123","11","22","Rahul Singh");
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(2);
        clickOnDownloadPolicy();
        waitFor(5);
        if (isPolicyDownloadMessageDispalyed()){
            policyDownloadMessage();
        }else {
            driver.get("chrome://downloads/");
            waitFor(3);
            refreshPage();
            waitFor(1);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify2wRolloverPolicyPurchageWithDigitBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before july 2018 using Digit insurer");
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
        selectRegistrationDate("2018");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        waitFor(1);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsDigit();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCity("Mumbai");
        selectDistrict("Mumbai");
        selectCountry();
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AD" + randomeNum());
        //  entervehicleNumer("MH01AD5261");
        selcectManufactureDate("2018");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isDigitPaymentPageDisplayed();
        enterDigitPaymentDetailsAndProceed("5123456789012346","123","11","22","Rahul Singh");
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(2);
        clickOnDownloadPolicy();
        waitFor(5);
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
    public void verify2wBreakinPolicyPurchageWithDigit(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakinPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates 2wBreakinPolicyPurchageWithDigit for " + policyType + " plan which Regestration date before july 2018 using Digit insurer");
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
        clickOnViewDetailsDigit();
        getSubtotal();
        String idv="";
        if (policyType.equalsIgnoreCase("1yrCp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCity("Mumbai");
        selectDistrict("Mumbai");
        selectCountry();
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AD" + randomeNum());
        //  entervehicleNumer("MH01AD5261");
        selcectManufactureDate("2018");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        if (polExpType.contains("90")){
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selcectPrevPolExpDateBreakin("2021");
        clickOnNext();}
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isDigitPaymentPageDisplayed();
        enterDigitPaymentDetailsAndProceed("5123456789012346","123","11","22","Rahul Singh");
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(2);
        clickOnDownloadPolicy();
        waitFor(5);
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
    public void verify2wNewVehiclePolicyPurchageWithDigit(String policyType) throws IOException {
        logger.info("This test validates verifyNewVehiclePolicyPurchageWithDigit for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verifyNewVehiclePolicyPurchageWithDigit for " + policyType + " Plan");
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
        waitFor(1);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("5tp")){
            changePolicyTypeForNewVehicle("5tp");
        }
        isPlanPageDisplayed();
        isNewVehiclePolTypeDisplayed(policyType);
//        scrollUpDown(0, 200);
        clickOnViewDetailsDigit();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("5tp")){
         idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Satvik");
        enterMiddleName("R");
        enterLastName("Mehta");
        selectDobAction("1988");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCity("Mumbai");
        selectDistrict("Mumbai");
        selectCountry();
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AD" + randomeNum());
        //  entervehicleNumer("MH01AD5261");
        selcectManufactureDate("2021");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("5tp")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isDigitPaymentPageDisplayed();
        enterDigitPaymentDetailsAndProceed("5123456789012346","123","11","22","Rahul Singh");
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(2);
        clickOnDownloadPolicy();
        waitFor(5);
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
    public void verify2wRolloverPolicyPurchageWithDigitBeforeJuly2018withDiffrentNcb(String ncb) throws IOException {
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
        clickOnViewDetailsDigit();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCity("Mumbai");
        selectDistrict("Mumbai");
        selectCountry();
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AD" + randomeNum());
        //  entervehicleNumer("MH01AD5261");
        selcectManufactureDate("2015");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isDigitPaymentPageDisplayed();
        enterDigitPaymentDetailsAndProceed("5123456789012346","123","11","22","Rahul Singh");
        isPaymentSuccessPageDisplayed();
        refreshPage();
        closeCurrentTab();
        switchToTab(parentWindow);
        logger.info(driver.getCurrentUrl());
        refreshPage();
        isPolicyDownloadPageDisplayed();
        waitFor(2);
        clickOnDownloadPolicy();
        waitFor(5);
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

    @DataProvider(name = "policyTypeTA")
    public static Object[][] policyTypeTA() {
        return new Object[][]{{"1yrCp"}, {"1yrTP"}};
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeTA")
    public void verify2wRolloverPolicyPurchageWithTataAig(String policyTypeTA) {
        logger.info("This test validates verifyRolloverPolicyPurchageWithTataAig for " + policyTypeTA + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyTypeTA + " which Regestration date before july 2018 using TataAig insurer");
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
        selectRegistrationDate("2018");
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyTypeTA.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyTypeTA);
        scrollUpDown(0, 100);
        clickOnViewDetailsTataAig();
        getSubtotal();
        String idv="";
        if (!policyTypeTA.equalsIgnoreCase("1yrTP")){
        idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowTataAction();
        clockOnAgree();
        selectFinanceType("BANK");
        enterFinancerNameTata("HDFC");
        enterFinancerAddressTata("Church road mumbai");
        actionOnProceed();
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        enterTextAction("MALE",1);
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectFromDropDown("Mr");
        selectMaritalStatus("Single");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        clickOnNextButton();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("OTHERS");
        clickOnNextButton();
        entervehicleNumer("MH-01-AD-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectExpiryDate("2021");
        if (policyTypeTA.equalsIgnoreCase("1yrCp")) {
            selectComprehensiveForProposal();
        } else if (policyTypeTA.equalsIgnoreCase("1yrTP")) {
            selectThirdpartyForProposal();
        }
        clickOnNextButton();
        clickOnReviewNSubmit();
        waitFor(3);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyTypeTA.equalsIgnoreCase("1yrTP")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isTataAigPaymentPageDisplayed();


    }

    @Test(priority = 1, groups = {"regression","breakin"}, dataProvider = "breakin")
    public void verify2wBreakInPolicyPurchageWithTataAig(String polExpType,String policyType) {
        logger.info("This test validates 2wBreakInPolicyPurchageWithTataAig for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test 2wBreakInPolicyPurchageWithTataAig " + policyType + " which Regestration date after july 2018 using TataAig insurer");
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
        scrollUpDown(0, 100);
        clickOnViewDetailsTataAig();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
        buyNowTataAction();
        clockOnAgree();
        selectFinanceType("BANK");
        enterFinancerNameTata("HDFC");
        enterFinancerAddressTata("Church road mumbai");
        actionOnProceed();
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        enterTextAction("MALE",1);
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectFromDropDown("Mr");
        selectMaritalStatus("Single");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        clickOnNextButton();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("OTHERS");
        clickOnNextButton();
        entervehicleNumer("MH-01-AD-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        if(polExpType.contains("90")){
        selectFromDropDown("Bharti");
        enterPrevPolicyNum("AR00" + randomeNum8());
        selcectPrevPolExpDateBreakin("2021");
        if (policyType.equalsIgnoreCase("1yrCp")) {
            selectComprehensiveForProposal();
        } else if (policyType.equalsIgnoreCase("1yrTP")) {
            selectThirdpartyForProposal();
        }
        clickOnNextButton();}
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isIDVEqualTo(idv);}

        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isTataAigPaymentPageDisplayed();
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify2wNewVehiclePolicyPurchageWithTataAig(String policyType) {
        logger.info("This test validates verify2wNewVehiclePolicyPurchageWithTataAig for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify2wNewVehiclePolicyPurchageWithTataAig for " + policyType + " plan");
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
        scrollUpDown(0, 100);
        clickOnViewDetailsTataAig();
        String idv="";
        if (!policyType.equalsIgnoreCase("5tp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
        buyNowTataAction();
        clockOnAgree();
        selectFinanceType("BANK");
        enterFinancerNameTata("HDFC");
        enterFinancerAddressTata("Church road mumbai");
        actionOnProceed();
        enterFirstName("Ravin");
        enterLastName("Sharma");
        selectDobAction("1993");
        enterTextAction("MALE",1);
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        selectFromDropDown("Mr");
        selectMaritalStatus("Single");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        clickOnNextButton();
        enterNomineeName("Rahul bhatt");
        enterNomineeAge("34");
        selectFromDropDown("OTHERS");
        clickOnNextButton();
        entervehicleNumer("MH-01-AD-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("5tp")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isTataAigPaymentPageDisplayed();


    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyType")
    public void verify2wRolloverPolicyPurchageWithIcici(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Icici insurer");
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
        scrollToButtom();
        clickOnViewDetailsIciciL();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCityUpperCase("Mumbai");
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AR" + randomeNum());
        selcectManufactureDate("2019");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti A");
        selectExpiryDate("2021");
        enterPrevPolicyNum("AR00" + randomeNum8());
        prevPolStartDate("2020");
        if (policyType.equalsIgnoreCase("saod")||policyType.equalsIgnoreCase("Bundle")||policyType.equalsIgnoreCase("5yrTP")){
            prevTpStartDate("2018",7);
            prevTpExpiryDate("2021");
            scrollUpDown(0,200);
            waitFor(1);
            selectInsurerFromDropDown("Bharti",3);
            enterPrevTpPolicyNumber("AR00"+randomeNum8());
        }
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        clickOnOk();
        waitFor(2);
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        browserProceedForNotSecureLink();
        waitFor(9);
        driver.switchTo().frame(0);
        isICICIPaymentPageDisplayed();
        enterIciciPaymentDetailsAndProceed("5123456789012346","123","1222","Rahul singh");
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
    public void verify2wRolloverPolicyPurchageWithIciciBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before july 2018 using Icici insurer");
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
        scrollToButtom();
        scrollUpDown(0, -100);
        clickOnViewDetailsIciciL();
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        backBtnPlanDetailsPage();
        clickBuyNowIcici();
        enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCityUpperCase("Mumbai");
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AR" + randomeNum());
        selcectManufactureDate("2018");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        selectFromDropDown("Bharti A");
        selectExpiryDate("2021");
        enterPrevPolicyNum("AR00" + randomeNum8());
        prevPolStartDate("2020");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        clickOnOk();
        waitFor(2);
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        browserProceedForNotSecureLink();
        waitFor(9);
        driver.switchTo().frame(0);
        isICICIPaymentPageDisplayed();
        enterIciciPaymentDetailsAndProceed("5123456789012346","123","1222","Rahul singh");
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
    public void verify2wBreakInPolicyPurchageWithIcici(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakInPolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates 2wBreakInPolicyPurchageWithIcici for " + policyType + " which Regestration date after july 2018 using Icici insurer");
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
            prevNcbPercent("20%");
            selectPrevInsurer();}
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("1yrTP")){
            changePolicyType("tp");
        }
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
        scrollToButtom();
        clickBuyNowIcici();
        enterFirstName("Tanmay");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCityUpperCase("Mumbai");
        clickOnNextButton();
        enterNomineeFirstName("Rahul");
        enterNomineeMiddleName("Singh");
        enterNomineeLastName("Kanwar");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AR" + randomeNum());
        selcectManufactureDate("2018");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        if (polExpType.contains("90")){
        selectFromDropDown("Bharti A");
        selcectPrevPolExpDateBreakin("2021");
        enterPrevPolicyNum("AR00" + randomeNum8());
        prevPolStartDateBreakIn("2020");
        clickOnNext();}
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isIDVEqualTo(idv);}
        clickOnMakePayment();
        clickOnOk();
        waitFor(2);
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        browserProceedForNotSecureLink();
        waitFor(9);
        driver.switchTo().frame(0);
        isICICIPaymentPageDisplayed();
        enterIciciPaymentDetailsAndProceed("5123456789012346","123","1222","Rahul singh");
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify2wNewVehiclePolicyPurchageWithIcici(String policyType) throws IOException {
        logger.info("This test validates verify2wNewVehiclePolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify2wNewVehiclePolicyPurchageWithIcici for " + policyType + " Plan");
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
        scrollToButtom();
        clickOnViewDetailsIciciL();
        getSubtotal();
        String idv="";
        if (!policyType.equalsIgnoreCase("5tp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        buyNowAction();
       String st= enterFirstName("Ravin");
        enterMiddleName("Raj");
        enterLastName("Sharma");
        selectDobAction("1993");
        selectGender("Male");
        enterEmailId("ensuredit@gmail.com");
        enterPhone("6367357113");
        clickOnNext();
        enterPermAddress("Church road");
        enterPincode("400001");
        selectState("Maharashtra");
        selectCityUpperCase("Mumbai");
        clickOnNextButton();
        enterNomineeFirstName("Rajesh");
        enterNomineeMiddleName("Ranjan");
        enterNomineeLastName("Singh");
        selectDobAction("1997");
        enterTextAction("Male",1);
        selectFromDropDown("Brother");
        clickOnNextButton();
        entervehicleNumer("MH01AR" + randomeNum());
        selcectManufactureDate("2021");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        inputValidation(st);
        clickOnNextButton();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("5tp")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        clickOnOk();
        waitFor(2);
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitFor(2);
        browserProceedForNotSecureLink();
        waitFor(9);
        driver.switchTo().frame(0);
        isICICIPaymentPageDisplayed();
        enterIciciPaymentDetailsAndProceed("5123456789012346","123","1222","Rahul singh");
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyType")
    public void verify2wRolloverPolicyPurchageWithBajaj(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithBajaj for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Bajaj insurer");
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
        clickOnViewDetailsBajaj();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
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
        if (policyType.equalsIgnoreCase("1yrCp")){
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
        waitFor(2);
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
        waitFor(5);
        if (isPolicyDownloadMessageDispalyed()){
            policyDownloadMessage();
        }else {
            driver.get("chrome://downloads/");
            waitFor(3);
            JavascriptExecutor js = (JavascriptExecutor) driver;
//            WebElement e= (WebElement) js.executeScript(" return document.querySelector('downloads-manager').shadowRoot.querySelector('iron-list > downloads-item').shadowRoot.querySelector('div > div>div>a')");
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify2wRolloverPolicyPurchageWithBajajBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithBajaj for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before july 2018 using Bajaj insurer");
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
        waitFor(2);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify2wBreakInPolicyPurchageWithBajaj(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakInPolicyPurchageWithBajaj for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates 2wBreakInPolicyPurchageWithBajaj for " + policyType + " which Regestration date after july 2018 using Bajaj insurer");
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
        waitFor(2);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify2wNewVehiclePolicyPurchageWithBajaj(String policyType) throws IOException {
        logger.info("This test validates verify2wNewVehiclePolicyPurchageWithBajaj for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify2wNewVehiclePolicyPurchageWithBajaj for " + policyType + " Plan");
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
        scrollUpDown(0, 200);
        clickOnViewDetailsBajaj();
        getSubtotal();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
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
        entervehicleNumer("MH-01-DB-" + randomeNum());
        selcectManufactureDate("2021");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(2);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        clickOnOk();
        String parentWindow = driver.getWindowHandle();
        waitFor(3);
        switchToChildWindow();
        waitFor(2);
        isBajajPaymentPageDisplayed();
        proceedBajajPayment();
        isPaymentSuccessPageDisplayed();
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
        enterMMVDetails("Bajaj","Discover","DTS-SI Electric");
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
        enterMMVDetails("Bajaj","Discover","DTS-SI Electric");
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
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        enterMMVDetails("Bajaj","Discover","DTS-SI Electric");
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
