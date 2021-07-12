package webqa.ensurity.testCases;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import webqa.ensurity.base.BaseTest;
import webqa.ensurity.report.ExtentTestManager;

public class TwoWheelerTATA_AIG extends BaseTest {


    public static Logger logger = LogManager.getLogger(TwoWheelerBAJAJ.class);

    @DataProvider(name = "policyType")
    public static Object[][] policyType() {
        return new Object[][]{
                {"saod"}, {"Bundle"}, {"5yrTP"}};
    }


    @DataProvider(name = "polType")
    public static Object[][] polType() {
        return new Object[][]{{"1yrCp"},{"1yrTP"}};
    }

    @DataProvider(name = "ncb")
    public static Object[][] ncb() {
        return new Object[][]{{"0%"},{"20%"},{"25%"},{"35%"},{"45%"},{"50%"},{"I don't remember my NCB"}};
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


    @DataProvider(name = "policyTypeTA")
    public static Object[][] policyTypeTA() {
        return new Object[][]{{"1yrCp"}, {"1yrTP"}};
    }

    @Test(priority = 1, groups = {"regression"}, dataProvider = "polType")
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
        selectYesNo("Yes");
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
        selectYesNo("Yes");
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
        selectYesNo("Yes");
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
}
