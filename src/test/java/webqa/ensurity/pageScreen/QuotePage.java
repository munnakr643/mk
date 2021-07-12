package webqa.ensurity.pageScreen;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import webqa.ensurity.pageScreen.manager.ScreenManager;


public class QuotePage extends ScreenManager {

	public QuotePage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}



	@FindBy(xpath="//span[text()='Health Insurance']")
	@CacheLookup
	public WebElement healthInsurance;

	@FindBy(xpath="//span[text()='Car Insurance']")
	@CacheLookup
	public WebElement carInsurance;

	@FindBy(xpath="//span[text()='Two Wheeler Insurance']")
	@CacheLookup
	public WebElement twoWheeler;

	@FindBy(xpath="//span[text()='Commercial Vehicle Insurance']")
	@CacheLookup
	public WebElement commercialVehicle;

	@FindBy(xpath="//input[@placeholder='Customer Name (Optional)']")
	@CacheLookup
	public WebElement customerName;

	@FindBy(xpath="//span[text()='Next']")
	//button[@type='submit']
	@CacheLookup
	public WebElement nextbtn;

	@FindBy(xpath="//form/div[2]/button[2]/span[1]")
	@CacheLookup
	public WebElement nextbutton;

	@FindBy(xpath="(//button[@type='button'])[1]")
	@CacheLookup
	public WebElement searchButton;

	@FindBy(xpath = "//input[contains(@placeholder ,'Model')]")
	@CacheLookup
	public WebElement searchModel;

	@FindBy(xpath="(//input[@type='text'])[2]")
	@CacheLookup
	public WebElement inputBar;

	@FindBy(xpath="(//div[@role='button'])[3]")
	@CacheLookup
	public WebElement firstRow;

	@FindBy(xpath="(//div[@role='button'])[4]")
	@CacheLookup
	public WebElement secondRow;

	@FindBy(xpath="//div/div/div/div[2]/ul/div[1]")
	@CacheLookup
	public WebElement modelRow1;

	@FindBy(xpath="//div/div/div/div[2]/ul/div[2]")
	@CacheLookup
	public WebElement modelRow2;

	@FindBy(xpath="//div[text()='Bajaj']")
	@CacheLookup
	public WebElement bajaj;

	@FindBy(xpath="//div[text()='Hero Honda']")
	@CacheLookup
	public WebElement heroHonda;

	@FindBy(xpath="//div[text()='Hero Motocorp']")
	@CacheLookup
	public WebElement heroMotocorp;

	@FindBy(xpath="//div[text()='Honda']")
	@CacheLookup
	public WebElement honda;

	@FindBy(xpath="//div[text()='HONDA']")
	@CacheLookup
	public WebElement honda4w;

	@FindBy(xpath="//div[text()='Yamaha']")
	@CacheLookup
	public WebElement yamaha;

	@FindBy(xpath="//div[text()='TVS']")
	@CacheLookup
	public WebElement tvs;

	@FindBy(xpath="//div[text()='Pulsar 150']")
	@CacheLookup
	public WebElement pulsar150;

	@FindBy(xpath="//div[text()='Activa']")
	@CacheLookup
	public WebElement activa;

	@FindBy(xpath="//div[text()='Petrol']")
	@CacheLookup
	public WebElement petrol;

	@FindBy(xpath="//div[text()='Diesel']")
	@CacheLookup
	public WebElement diesel;

	@FindBy(xpath="//div[text()='Petrol+cng']")
	@CacheLookup
	public WebElement cngPetrol;

	@FindBy(xpath="//div[text()='City']")
	@CacheLookup
	public WebElement hondaCity;

	@FindBy(xpath="//div[text()='3G (109 CC)']")
	@CacheLookup
	public WebElement _3G109cc;

	@FindBy(xpath="//div[text()='1.3 EXI (1343 CC)']")
	@CacheLookup
	public WebElement _1point3EXI1343CC;

	@FindBy(xpath="//div[text()='Mumbai']")
	@CacheLookup
	public WebElement mumbai;

	@FindBy(xpath="//input[@placeholder='City']")
	@CacheLookup
	public WebElement city;

	@FindBy(xpath="//span[text()='MH01 (Mumbai South - Tardeo)']")
	@CacheLookup
	public WebElement mumbaiSouth;

	@FindBy(xpath="//button[@aria-label='Choose date']")
	@CacheLookup
	public WebElement chooseDate;

	@FindBy(xpath="//button[text()='2019']")
	@CacheLookup
	public WebElement yr2019;

	@FindBy(xpath="//button[@aria-label='next month']")
	@CacheLookup
	public WebElement nextMonth;

	@FindBy(xpath="//button[@aria-label='previous month']")
	@CacheLookup
	public WebElement prevMonth;

	@FindBy(xpath="//span[text()='16']")
	@CacheLookup
	public WebElement date16;

	@FindBy(xpath="//span[contains(text(),'new vehicle')]")
	@CacheLookup
	public WebElement newVehicle;

	@FindBy(xpath="//div[text()='Not expired yet']")
	@CacheLookup
	public WebElement rollOver;

	@FindBy(xpath="//div[contains(text() , 'Expired within 90')]")
	@CacheLookup
	public WebElement breakin45;

	@FindBy(xpath="//div[contains(text() , 'Expired more than 90')]")
	@CacheLookup
	public WebElement breakin90;

	@FindBy(xpath="//div[contains(text() , 'Don’t know')]")
	@CacheLookup
	public WebElement dontKnowPPExpDate;

	@FindBy(xpath="//div[text()='Yes']")
	@CacheLookup
	public WebElement yes;

	@FindBy(xpath="//div[text()='No']")
	@CacheLookup
	public WebElement no;

	@FindBy(xpath="//div[text()='35%']")
	@CacheLookup
	public WebElement ncb35;

	@FindBy(xpath="//div[contains(text(),'remember my NCB')]")
	@CacheLookup
	public WebElement dontRememberNcb;

	@FindBy(xpath="//span[text()='Acko']")
	@CacheLookup
	public WebElement acko;

	@FindBy(xpath="//div[text()='SAOD']")
	@CacheLookup
	public WebElement saod;

	@FindBy(xpath="//div[text()='Bundled 1+5 yrs Comprehensive Policy']")
	@CacheLookup
	public WebElement bundle1plus5;

	@FindBy(xpath="//div[text()='5 yr Third Party Only']")
	@CacheLookup
	public WebElement tp5yr;

	@FindBy(xpath="//div[text()='1 yr Comprehensive/ Standard Policy']")
	@CacheLookup
	public WebElement comp1yr;

	@FindBy(xpath="//div[text()='1 yr Third Party only']")
	@CacheLookup
	public WebElement tp1yr;

	@FindBy(xpath="//div[text()='Bundled 1+3 yrs Comprehensive Policy']")
	@CacheLookup
	public WebElement bundle1plus3;

	@FindBy(xpath="//div[text()='3 yr Third Party Only']")
	@CacheLookup
	public WebElement tp3yr;

	@FindBy(xpath="//span[text()='Generate Quote']")
	@CacheLookup
	public WebElement generateQuote;

	@FindBy(xpath="//div[text()='Comprehensive']")
	@CacheLookup
	public WebElement polTypeComp;

	@FindBy(xpath="//div[text()='Third Party']")
	@CacheLookup
	public WebElement polTypeTp;

	@FindBy(xpath="//div[text()='SAOD']")
	@CacheLookup
	public WebElement polTypeOD;

	@FindBy(xpath="//div[text()='1OD + 5TP']")
	@CacheLookup
	public WebElement polType1OD_5TP;

	@FindBy(xpath="//div[text()='5TP']")
	@CacheLookup
	public WebElement polType5TP;

	@FindBy(xpath="//div[text()='1OD + 3TP']")
	@CacheLookup
	public WebElement polType1OD_3TP;

	@FindBy(xpath="//div[text()='3TP']")
	@CacheLookup
	public WebElement polType3TP;

	@FindBy(xpath="//span[text()='Comprehensive']")
	@CacheLookup
	public WebElement insPolTypeComp;

	@FindBy(xpath="//span[text()='Third Party']")
	@CacheLookup
	public WebElement insPolTypeTp;

	@FindBy(xpath="//span[text()='SAOD']")
	@CacheLookup
	public WebElement insPolTypeOD;

	@FindBy(xpath="//span[text()='1OD + 5TP']")
	@CacheLookup
	public WebElement insPolType1OD_5TP;

	@FindBy(xpath="//span[text()='5TP']")
	@CacheLookup
	public WebElement insPolType5TP;

	@FindBy(xpath="//div[@data-cy='button-1OD + 5TP']")
	@CacheLookup
	public WebElement _1OD_5TP;

	@FindBy(xpath="//div[@data-cy='button-5TP']")
	@CacheLookup
	public WebElement _5TP;

	@FindBy(xpath="//span[text()='1OD + 3TP']")
	@CacheLookup
	public WebElement insPolType1OD_3TP;

	@FindBy(xpath="//span[text()='3TP']")
	@CacheLookup
	public WebElement insPolType3TP;

	@FindBy(xpath="//div[@data-cy='button-1OD + 3TP']")
	@CacheLookup
	public WebElement _1OD_3TP;

	@FindBy(xpath="//div[@data-cy='button-3TP']")
	@CacheLookup
	public WebElement _3TP;

	@FindBy(xpath="//div[@data-cy='button-Comprehensive']")
	@CacheLookup
	public WebElement comprehensive;

	@FindBy(xpath="//div[@data-cy='button-Third Party']")
	@CacheLookup
	public WebElement thirdParty;

	@FindBy(xpath="//span[text()='Apply']")
	@CacheLookup
	public WebElement apply;

	@FindBy(xpath="(//span[text()='Buy Now'])[2]")
	@CacheLookup
	public WebElement buyNow;

	@FindBy(xpath="//span[text()='View Details']")
	@CacheLookup
	public WebElement viewDetails;

	@FindBy(xpath="//img[contains(@src,'godigit')]/parent::div/parent::div/following-sibling::div[2]/button")
	@CacheLookup
	public WebElement digoitBuyNow;

	@FindBy(xpath="//img[contains(@src,'tataaig')]/parent::div/parent::div/following-sibling::div[2]/button")
	@CacheLookup
	public WebElement tataBuyNow;

	@FindBy(xpath="//img[contains(@src,'godigit')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement digitViewDetails;

	@FindBy(xpath="//img[contains(@src,'reliance_motor')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement relianceViewDetails;

	@FindBy(xpath="//img[contains(@src,'hdfcergo')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement hdfcViewDetails;

	@FindBy(xpath="//img[@data-locator='hdfcergo']/parent::div/parent::div/following-sibling::div[2]/button[1]")
	@CacheLookup
	public WebElement hdfcBuyNow;

	@FindBy(xpath="//img[contains(@src,'hdfcergo')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement hdfcB;

	@FindBy(xpath="//img[@data-locator='icici']/parent::div/parent::div/following-sibling::div[2]/button[1]")
	@CacheLookup
	public WebElement iciciBuyNow;

	@FindBy(xpath="//img[contains(@src,'tataaig')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement tataViewDetails;

	@FindBy(xpath="//img[contains(@src,'icici_lombard')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement iciciViewDetails;

	@FindBy(xpath="//img[contains(@src,'bajaj_allianz')]/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement bajajViewDetails;

	@FindBy(xpath="//img[@data-locator='futureGenerali']/parent::div/parent::div/following-sibling::div[2]/button[2]")
	@CacheLookup
	public WebElement futureViewDetails;

	@FindBy(xpath="//div[contains(text(),'Sub-Total')]/following-sibling::div")
	@CacheLookup
	public WebElement subTotal;

	@FindBy(xpath="//div[contains(text(),'GST')]/following-sibling::div")
	@CacheLookup
	public WebElement gst18;

	@FindBy(xpath="//div[contains(text(),'Amount Payable')]/following-sibling::div")
	@CacheLookup
	public WebElement premiumAmt;

	@FindBy(xpath="//span[contains(text(),'Premium Annually')]/following-sibling::span")
	@CacheLookup
	public WebElement premiumAnnuallyAmt;

	@FindBy(xpath="//span[contains(text(),'IDV')]/following-sibling::span")
	@CacheLookup
	public WebElement idvAmt;

	@FindBy(xpath="//div[contains(text(),'Motor Profile')]")
	@CacheLookup
	public WebElement motorProfile;

	@FindBy(xpath="//div[text()='Policy Type']")
	@CacheLookup
	public WebElement polTypePlanPage;

	@FindBy(xpath="//div[text()='IDV']")
	@CacheLookup
	public WebElement idv;

	@FindBy(xpath="//div[text()='Best Match']")
	@CacheLookup
	public WebElement bestMatch;

	@FindBy(xpath="//div[text()='Add Ons']")
	@CacheLookup
	public WebElement addOns;

	@FindBy(xpath="//div[contains(text(),'Own Damage')]/following-sibling::div")
	@CacheLookup
	public WebElement ownDamageAmt;

	@FindBy(xpath="//div[contains(text(),'AddOns')]/following-sibling::div")
	@CacheLookup
	public WebElement addonsAccessoriesAmount;

	@FindBy(xpath="//div[contains(text(),'Discounts')]/following-sibling::div")
	@CacheLookup
	public WebElement ncbDiscountAmt;

	@FindBy(xpath="//div[contains(text(),'No Claim')]/following-sibling::div")
	@CacheLookup
	public WebElement ncbAmt;

	@FindBy(xpath = "//span[@class='MuiIconButton-label']")
	@CacheLookup
	public WebElement planBackBtn;

	@FindBy(xpath = "(//span[@class='MuiIconButton-label'])[3]")
	@CacheLookup
	public WebElement backBtnAddonPage;

	@FindBy(xpath = "//div[text()='Addons']")
	@CacheLookup
	public WebElement addonsText;

}

