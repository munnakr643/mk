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

public class TwoWheelerDIGIT extends BaseTest {


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
            if(polExpType.contains(" more than")){
                enterDate("25032021");
            }else {
                selcectPrevPolExpDateBreakin("2021");}
            clickOnNext();}
        clickOnReviewNSubmit();
        if (policyType.equalsIgnoreCase("1yrCp")) {
            isBreakingPaymentPageDisplayed();
            isTotalPremiumEqualTo(premiumAnnualy);
            isIDVEqualTo(idv);
        }else {
            isPaymentPageDisplayed();
            isTotalPremiumEqualTo(premiumAnnualy);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchageWithAddonsForDigit(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageAddAddonsWithDigit for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageAddAddonsWithDigit for " + addons + " addons");
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
        entervehicleNumer("MH01DT" + randomeNum());
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
}
