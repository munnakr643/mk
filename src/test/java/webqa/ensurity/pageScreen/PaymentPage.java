package webqa.ensurity.pageScreen;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import webqa.ensurity.pageScreen.manager.ScreenManager;

@Getter
@Setter
public class PaymentPage extends ScreenManager {

	public PaymentPage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@FindBy(xpath="//div[text()='Total Premium']")
	@CacheLookup
	public WebElement totalPremium;

	@FindBy(xpath="//div[text()='Total Premium']/following-sibling::div")
	@CacheLookup
	public WebElement totalPremiumAmt;

	@FindBy(xpath="//div[text()='IDV']")
	@CacheLookup
	public WebElement idv;

	@FindBy(xpath="//div[text()='IDV']/following-sibling::div")
	@CacheLookup
	public WebElement idvValue;

	@FindBy(xpath="//span[text()='Copy Payment Link']")
	@CacheLookup
	public WebElement copyPaymentLink;

	@FindBy(xpath="//span[text()='Your proposal is created successfully!']")
	@CacheLookup
	public WebElement proposalCreated;

	@FindBy(xpath="//img[@alt='ins Company Logo']")
	@CacheLookup
	public WebElement companyLogo;

	@FindBy(xpath="//span[text()='Make Payment']")
	@CacheLookup
	public WebElement makePayment;

	@FindBy(xpath="//span[text()='Back Home']")
	@CacheLookup
	public WebElement backHome;

	@FindBy(xpath="//button[@id='mock_button']")
	@CacheLookup
	public WebElement mockPay;

	@FindBy(xpath="//img[contains(@src,digit)]")
	@CacheLookup
	public WebElement digitLogo;

	@FindBy(id="cardTab")
	@CacheLookup
	public WebElement creditDebit;

	@FindBy(id="netbankTab")
	@CacheLookup
	public WebElement netbanking;

	@FindBy(id="walletTab")
	@CacheLookup
	public WebElement wallet;

	@FindBy(id="emiTab")
	@CacheLookup
	public WebElement emi;

	@FindBy(xpath="//*[text()=' Payment Success Page!']")
	@CacheLookup
	public WebElement paymentSuccess;

	@FindBy(xpath="//head[@runat='success']")
	@CacheLookup
	public WebElement paymentSuccess1;

	@FindBy(xpath="//span[text()='Download policy']")
	@CacheLookup
	public WebElement downloadPolicy;

	@FindBy(xpath="//p[text()='Success']")
	@CacheLookup
	public WebElement successPage;

	@FindBy(xpath="//button[@id='proceed-button']")
	@CacheLookup
	public WebElement sendAnyway;

	@FindBy(xpath = "//label[@id='Amount']")
	@CacheLookup
	public WebElement amt;

	@FindBy(xpath = "//div[text()='Any Credit/Debit Card']")
	@CacheLookup
	public WebElement creditDebitHdfc;

	@FindBy(xpath = "//input[@value='Pay']")
	@CacheLookup
	public WebElement payHdfc;

	@FindBy(xpath = "//span[text()='Payment Gateway Test']")
	@CacheLookup
	public WebElement paymentGatewayTest;

	@FindBy(xpath = "//input[@value='Submit']")
	@CacheLookup
	public WebElement submit;

	@FindBy(xpath = "//input[@id='txtCardNo']")
	@CacheLookup
	public WebElement txtCardNo;

	@FindBy(xpath = "//input[@id='txtPinNo']")
	@CacheLookup
	public WebElement cvvHdfc;

	@FindBy(xpath = "//img[@alt='Tataaig']")
	@CacheLookup
	public WebElement tataLogo;

	@FindBy(xpath = "//span[@class='total-amount']")
	@CacheLookup
	public WebElement payAmtTata;

	@FindBy(xpath = "//span[text()='Cards (Credit/Debit)']")
	@CacheLookup
	public WebElement cardTata;

	@FindBy(xpath = "//span[text()='Ok']")
	@CacheLookup
	public WebElement ok;

	@FindBy(id = "logoImg")
	@CacheLookup
	public WebElement bajajLogo;

	@FindBy(xpath = "//span[text()='Credit / Debit Cards']")
	@CacheLookup
	public WebElement creditDebitBajaj;

	@FindBy(xpath = "//span[text()='Net Banking']")
	@CacheLookup
	public WebElement netBankingBajaj;

	@FindBy(xpath = "//img[@alt='HDFC BANK']")
	@CacheLookup
	public WebElement bajajPayByHDFC;

	@FindBy(xpath = "//span[text()='Payable Amount']")
	@CacheLookup
	public WebElement payAmtBajaj;

	@FindBy(xpath = "//img[@class='imageHeader']")
	@CacheLookup
	public WebElement relianceLogo;

	@FindBy(xpath = "//span[text()='Credit Card']")
	@CacheLookup
	public WebElement creditCardRel;

	@FindBy(xpath = "(//span[text()='Debit Cards'])[2]")
	@CacheLookup
	public WebElement debitCardsRel;

	@FindBy(xpath = "(//span[text()='Net Banking'])[2]")
	@CacheLookup
	public WebElement netBankingRel;

	@FindBy(id = "netBankingBank")
	@CacheLookup
	public WebElement selecteBankRel;

	@FindBy(xpath = "//option[@value='AvenuesTest']")
	@CacheLookup
	public WebElement avenuesTestRel;

	@FindBy(xpath = "//form/div/div[2]/div[3]/div[4]/div[4]/div/div/div[3]/div/div[5]/a[1]")
	@CacheLookup
	public WebElement makePaymentRel;

	@FindBy(id = "expMonth")
	@CacheLookup
	public WebElement expMonBajaj;

	@FindBy(id = "expYear")
	@CacheLookup
	public WebElement expYearBajaj;

	@FindBy(id = "cvv")
	@CacheLookup
	public WebElement cvvBajaj;

	@FindBy(xpath = "(//button[@type='submit'])[3]")
	@CacheLookup
	public WebElement makePaymentBajaj;

	@FindBy(xpath = "//input[@type='submit']")
	@CacheLookup
	public WebElement returnToMerchantSite;

	@FindBy(id = "contact")
	@CacheLookup
	public WebElement phoneIl;

	@FindBy(id = "email")
	@CacheLookup
	public WebElement email;

	@FindBy(xpath = "//div[@class='button']")
	@CacheLookup
	public WebElement proceedIl;

	@FindBy(id = "logo")
	@CacheLookup
	public WebElement logoIl;

	@FindBy(xpath = "//div[text()='Card']")
	@CacheLookup
	public WebElement cardIl;

	@FindBy(xpath = "//button[@id='details-button']")
	@CacheLookup
	public WebElement browserAdvanced;

	@FindBy(xpath = "//a[@id='proceed-link']")
	@CacheLookup
	public WebElement browserProceed;

	@FindBy(id = "card_number")
	@CacheLookup
	public WebElement card_numberIl;

//	@FindBy(xpath = "//input[@id='card_number']")
	@FindBy(xpath = "//input[@placeholder='Enter your card number']")
	@CacheLookup
	public WebElement cardNumDigit;

	//@FindBy(xpath = "//input[@id='name_on_card']")
	@FindBy(xpath = "//input[@placeholder='Name on the card']")
	@CacheLookup
	public WebElement nameOnCardDigit;

	@FindBy(id = "card_expiry")
	@CacheLookup
	public WebElement card_expiryIl;

	@FindBy(id = "card_name")
	@CacheLookup
	public WebElement card_nameIl;

	@FindBy(id = "card_cvv")
	@CacheLookup
	public WebElement card_cvvIl;

	@FindBy(xpath = "//button[@class='success']")
	@CacheLookup
	public WebElement successIL;

	@FindBy(xpath = "//span[text()='Futuregenerali']")
	@CacheLookup
	public WebElement logoFuture;

	@FindBy(xpath = "//div[text()='Choose a payment option']")
	@CacheLookup
	public WebElement choosePaymentOption;

	@FindBy(xpath = "//span[@class='card-num']")
	@CacheLookup
	public WebElement cardsFuture;

	@FindBy(id = "cardNumber")
	@CacheLookup
	public WebElement cardNumberFuture;

	@FindBy(id = "cardExpiry")
	@CacheLookup
	public WebElement cardExpiryFuture;

	@FindBy(id = "cardCvv")
	@CacheLookup
	public WebElement cardCvvFuture;

	@FindBy(id = "cardOwnerName")
	@CacheLookup
	public WebElement cardOwnerNameFuture;

	@FindBy(xpath = "//button[@type='submit']")
	@CacheLookup
	public WebElement proceedFuture;

	@FindBy(xpath = "//input[@type='button']")
	@CacheLookup
	public WebElement payFuture;

	@FindBy(id = "password")
	@CacheLookup
	public WebElement otpFuture;

	@FindBy(xpath = "//input[@id='card_exp_month']")
	@CacheLookup
	public WebElement expMonthDigit;

	@FindBy(xpath = "//input[@id='card_exp_year']")
	@CacheLookup
	public WebElement expYearDigit;

	@FindBy(xpath = "//input[@id='security_code']")
	@CacheLookup
	public WebElement cvvDigit;

	@FindBy(xpath="//div[contains(text(),'Premium')]/following-sibling::div")
	@CacheLookup
	public WebElement premiumTotalPaymentPage;

	@FindBy(xpath = "//span[text()='Comprehensive'] | //span[text()='SAOD']| //span[text()='Third Party']|//*[contains(text(),'TP')]")
	@CacheLookup
	public WebElement cpPolTypePaymentPage;
}

