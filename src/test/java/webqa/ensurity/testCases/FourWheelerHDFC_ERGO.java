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

public class FourWheelerHDFC_ERGO extends BaseTest {


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

}
