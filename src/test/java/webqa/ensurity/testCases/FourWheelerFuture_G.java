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

public class FourWheelerFuture_G extends BaseTest {


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
}
