package webqa.ensurity.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webqa.ensurity.base.BaseTest;
import webqa.ensurity.report.ExtentTestManager;

import java.io.IOException;

public class FourWheelerTATA_AIG extends BaseTest {


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
        selectRegistrationDate("2019");
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
}
