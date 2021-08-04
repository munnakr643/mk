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

public class FourWheeler extends BaseTest {


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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "NewVehiclePolType")
    public void verify4wNewVehiclePolicyPurchageWithHdfcErgo(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using HDFC insurer");
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
        selectNewVehicle();
        clickOnNext();
        clickOngenerateQuote();
        isPlanPageDisplayed();
        if (policyType.equalsIgnoreCase("3tp")){
            change4wPolicyTypeForNewVehicle("3tp");
        }
        isPlanPageDisplayed();
        is4wNewVehiclePolTypeDisplayed(policyType);
//        scrollUpDown(0, 900);
//        waitFor(1);
//        if (policyType.equalsIgnoreCase("3tp")){
//            clickBuyNowHdfcErgo();
//        }else {
        scrollToInsurer("hdfcergo");
        clickOnViewDetailsHdfcErgo();
        getSubtotal();
        buyNowAction();
        //prposalBasicDetailsHdfc();
        String idv="";
        if (!policyType.equalsIgnoreCase("3tp")){
            idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        selectSalutation("MR");
        enterFirstName("Raman");
        enterLastName("Sharma");
        selectDobAction("1992");
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
        entervehicleNumer("MH-01-AR-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        selcectManufactureDate("2021");
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(1);
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("3tp")){
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithHdfcErgo(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithHdfcErgo for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using HDFC insurer");
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
        selectRegistrationDate(2,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
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
        waitFor(3);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify4wBreakInPolicyPurchageWithHdfcErgo(String polExpType,String policyType) throws IOException {
        logger.info("This test validates verify4wBreakInPolicyPurchageWithHdfcErgo for " + polExpType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + polExpType + " which Regestration date after before 2018 using HDFC insurer");
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
        selectRegistrationDate(3,2,1);
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
        if (!policyType.equalsIgnoreCase("1yrTP")){
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
        entervehicleNumer("MH-01-AD-" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        if (polExpType.contains("90")){
            selectFromDropDown("Bharti");
            enterPrevPolicyNum("AR00" + randomeNum8());
            selectExpiryDate("2021");
            clickOnNext();}
        clickOnReviewNSubmit();
        waitFor(3);
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyType.equalsIgnoreCase("1yrTP")){
            isBreakingPaymentPageDisplayed();
            isTotalPremiumEqualTo(premiumAnnualy);
            isIDVEqualTo(idv);
        }else {
            clickOnMakePayment();
            String parentWindow = driver.getWindowHandle();
            switchToChildWindow();
            waitElement(paymentPage.sendAnyway, 70);
            enterHdfcPaymentDetails("5123456789012346", "123");
            refreshPage();
            switchToTab(parentWindow);
            refreshPage();
            isPolicyDownloadPageDisplayed();
            waitFor(3);
            clickOnDownloadPolicy();
            waitFor(3);
            if (isPolicyDownloadMessageDispalyed()) {
                policyDownloadMessage();
            } else {
                driver.get("chrome://downloads/");
                waitFor(3);
                JavascriptExecutor js = (JavascriptExecutor) driver;
                WebElement e = (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
                logger.info(e.getAttribute("href"));
                String pdfurl = e.getAttribute("href");
                waitFor(1);
                readPdfContent(pdfurl);
            }
        }

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithDigit(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Digit insurer");
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
        selectRegistrationDate(2,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
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
        entervehicleNumer("MH01DT" + randomeNum());
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify4wRolloverPolicyPurchageWithDigitBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates 4wRolloverPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date before july 2018 using Digit insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        select4wMMV();
        rtoLocation();
        selectRegistrationDate(3,2,2);
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
        entervehicleNumer("MH01ED" + randomeNum());
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify4wBreakinPolicyPurchageWithDigit(String polExpType,String policyType) throws IOException {
        logger.info("This test validates verify4wBreakinPolicyPurchageWithDigit for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify4wBreakinPolicyPurchageWithDigit for " + policyType + " plan which Regestration date before july 2018 using Digit insurer");
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
        selectRegistrationDate(3,2,1);
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
        entervehicleNumer("MH01EC" + randomeNum());
        selcectManufactureDate("2018");
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNextButton();
        if (polExpType.contains("90")){
            selectFromDropDown("Bharti");
            enterPrevPolicyNum("AR00" + randomeNum8());
            selcectManufactureDate("2021");
            clickOnNext();}
        clickOnReviewNSubmit();
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithReliance(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithReliance for " + policyType + "Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using reliance insurer");
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
        selectRegistrationDate(2,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify4wBreakInPolicyPurchageWithReliance(String polExpType,String policyType) throws IOException {
        logger.info("This test validates 2wBreakInPolicyPurchageWithReliance for " + policyType + " with "+polExpType);
        ExtentTestManager.getTest().setDescription("This test validates 2wBreakInPolicyPurchageWithReliance for " + policyType + " with "+polExpType);
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        select4wMMV();
        rtoLocation();
        selectRegistrationDate(3,2,1);
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
        selectFromDropDown("Bharati AXA");
        selcectPrevPolExpDateBreakin("2021");
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
            WebElement e= (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);}


    }


    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify4wRolloverPolicyPurchageWithTataAig(String policyTypeTA) {
        logger.info("This test validates verify4wRolloverPolicyPurchageWithTataAig for " + policyTypeTA + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyTypeTA + " which Regestration date after july 2018 using TataAig insurer");
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
        selectRegistrationDate(2,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyTypeTA);
        clickOngenerateQuote();
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
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        if (!policyTypeTA.equalsIgnoreCase("1yrTP")){
        isIDVEqualTo(idv);}
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isTataAigPaymentPageDisplayed();


    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithIcici(String policyType) throws IOException {
        logger.info("This test validates verifyRolloverPolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates " + policyType + " which Regestration date after july 2018 using Icici insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        //enterCustomerName("webQA");
        clickOnNextBtn();
//        enterMMVDetails("FORD","Figo","Duratec Petrol LXI");
//        enterRtoCode("HR05");
        select4wMMV();
        rtoLocation();
        selectRegistrationDate(2,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
        scrollToButtom();
        clickOnViewDetailsIciciL();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
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
        if (policyType.equalsIgnoreCase("saod")||policyType.equalsIgnoreCase("Bundle")){
            prevTpStartDate("2019",6);
            prevTpStartDate("2022",7);
            scrollUpDown(0,200);
            waitFor(1);
            selectInsurerFromDropDown("Bharti",3);
            enterPrevTpPolicyNumber("AR00"+randomeNum8());
        }
        clickOnNext();
        clickOnReviewNSubmit();
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "breakin")
    public void verify4wBreakInPolicyPurchageWithIcici(String polExpType,String policyType) throws IOException {
        logger.info("This test validates verify4wBreakInPolicyPurchageWithIcici for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates verify4wBreakInPolicyPurchageWithIcici for " + policyType + " which Regestration date after july 2018 using Icici insurer");
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
        selectRegistrationDate(3,2,2);
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
        String idv="";
        if (!policyType.equalsIgnoreCase("1yrTP")){
         idv=getIdvAmount();}
        String premiumAnnualy=getPremiumAmount();
        enterFirstName("Tanmay");
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
        if (polExpType.contains("90")){
            selectFromDropDown("Bharti A");
            selcectPrevPolExpDateBreakin("2021");
            enterPrevPolicyNum("AR00" + randomeNum8());
            clickOnNext();}
        clickOnReviewNSubmit();
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
        selectRegistrationDate(3,3,2);
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
        selectRegistrationDate(3,2,2);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "policyTypeOD")
    public void verify4wRolloverPolicyPurchageWithFuture(String policyType) throws IOException {
        logger.info("This test validates 4wRolloverPolicyPurchageWithFuture for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates 4wRolloverPolicyPurchageWithFuture for " + policyType + " Plan Regestration date after july 2018 using future insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        enterMMVDetails("MARUTI SUZUKI","Swift","LXI (1298 CC");
        enterRtoCode("DL01");
        selectRegistrationDate(3,9,5);
        clickOnNext();
        notExpYet();
        prevPolicyClaim("No");
        prevNcbPercent("20%");
        selectPrevInsurer();
        select4wPrevPolType(policyType);
        clickOngenerateQuote();
        isPlanPageDisplayed();
        isSelectedPolTypeDisplayed(policyType);
        scrollToInsurer("futureGenerali");
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
        EnterAddressForFuture("MG road", "1st street", "North Delhi", "110009", "Delhi", "North West");
        clickOnNext();
        enterNomineeName("Sumit Singh");
        selectDobAction("1991");
        selectFromDropDown("Brother");
        clickOnNext();
        entervehicleNumer("DL01PB" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectFromDropDown("HDFC");
        polExpDateRollover();
        enterAddressLine1FL("MG Road");
        enterAddressLine2FL("Delhi");
        enterePreviousInsurerPincode("110009");
        enterPrevTpPolicyNumber("Ar00"+randomeNum8());
        selectInsurerFromDropDown("Bharti",2);
        prevTpStartDate("2019",9);
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
            String pdfuri=e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfuri);}

    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
    public void verify4wRolloverPolicyPurchageWithFutureBeforeJuly2018(String policyType) throws IOException {
        logger.info("This test validates 4wRolloverPolicyPurchageWithFuture for " + policyType + " Plan");
        ExtentTestManager.getTest().setDescription("This test validates 4wRolloverPolicyPurchageWithFuture for " + policyType + " Plan Regestration date after july 2018 using future insurer");
        entreUserName();
        clickOnProceedBtn();
        enterOtp();
        clickOnProceedBtnAfterOtp();
        isHomePageScreenDisplay();
        selectFourWheeler();
        enterCustomerName("webQA"+time());
        clickOnNextBtn();
        enterMMVDetails("HONDA","Accord","2.4 VTI L MT (2354 CC)");
        enterRtoCode("DL01");
        selectRegistrationDate(3,9,4);
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
        scrollToInsurer("futureGenerali");
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
        EnterAddressForFuture("MG road", "1st street", "North Delhi", "110009", "Delhi", "North West");
        clickOnNext();
        enterNomineeName("Sumit Singh");
        selectDobAction("1991");
        selectFromDropDown("Brother");
        clickOnNext();
        entervehicleNumer("DL01AB" + randomeNum());
        enterChassisNumber("CH1234567" + randomeNum8());
        enterEngineNumber("EN1234567" + randomeNum8());
        clickOnNext();
        enterPrevPolicyNum("AR00" + randomeNum8());
        selectFromDropDown("HDFC");
        polExpDateRollover();
        enterAddressLine1FL("MG Road");
        enterAddressLine2FL("Delhi");
        enterePreviousInsurerPincode("110009");
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
        selectRegistrationDate(3,9,4);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchagewithAddonsForHdfc_Ergo(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageAddAddonsWithHdfc_Ergo for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageAddAddonsWithHdfc_Ergo for " + addons + " addons");
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
        selectRegistrationDate(3,9,6);
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
        clickOnNext();
        clickOnReviewNSubmit();
        waitFor(3);
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        waitElement(paymentPage.sendAnyway,70);
        enterHdfcPaymentDetails("5123456789012346","123");
        refreshPage();
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
        selectRegistrationDate(3,9,9);
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

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchageWithAddonsForReliance(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageWithAddonsReliance for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageWithAddonsForReliance for " + addons + " addons");
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
        selectRegistrationDate(3,9,4);
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
        scrollUpDown(0, 200);
        clickOnViewDetailsReliance();
        getSubtotal();
        String idv = getIdvAmount();
        String premiumAnnualy = getPremiumAmount();
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
        if (isPolicyDownloadMessageDispalyed()) {
            policyDownloadMessage();
        } else {
            driver.get("chrome://downloads/");
            waitFor(3);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            WebElement e = (WebElement) js.executeScript(" return document.querySelector('body > downloads-manager').shadowRoot.querySelector('#frb0').shadowRoot.querySelector('#url')");
            logger.info(e.getAttribute("href"));
            String pdfurl = e.getAttribute("href");
            waitFor(1);
            readPdfContent(pdfurl);
        }
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchageWithAddonsForTATA(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageWithAddonsForTATA for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageWithAddonsForTATA for " + addons + " addons");
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
        selectRegistrationDate(3,9,4);
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
        scrollUpDown(0, 100);
        clickOnViewDetailsTataAig();
        getSubtotal();
        String idv = "";
        idv = getIdvAmount();
        String premiumAnnualy = getPremiumAmount();
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
        enterTextAction("MALE", 1);
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
        selectComprehensiveForProposal();
        clickOnNextButton();
        clickOnReviewNSubmit();
        isPaymentPageDisplayed();
        isTotalPremiumEqualTo(premiumAnnualy);
        isIDVEqualTo(idv);
        clickOnMakePayment();
        String parentWindow = driver.getWindowHandle();
        switchToChildWindow();
        isTataAigPaymentPageDisplayed();
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "addons")
    public void verify4wRolloverPolicyPurchageWithAddonsForICICI(String addons) throws IOException {
        logger.info("This test validates verify4wRolloverPolicyPurchageWithAddonsForTATA for " + addons + " addons");
        ExtentTestManager.getTest().setDescription("This test validates verify4wRolloverPolicyPurchageWithAddonsForTATA for " + addons + " addons");
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
        selectRegistrationDate(3,9,5);
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
        scrollToButtom();
        scrollUpDown(0, -100);
        clickOnViewDetailsIciciL();
        String idv=getIdvAmount();
        String premiumAnnualy=getPremiumAmount();
        verify18percentGstAmt(get18percentGstAmt());
        verifyPremiumAmount(getSubTotalWith18percentGst());
        getSubtotal();
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
        clickOnNext();
        clickOnReviewNSubmit();
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

}
