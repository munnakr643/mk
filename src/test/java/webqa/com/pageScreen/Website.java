package webqa.com.pageScreen;

import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import webqa.com.pageScreen.manager.ScreenManager;

@Getter
@Setter
public class Website extends ScreenManager {

	public Website(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@FindBy(xpath="//figure[@class='bb-site-logo']")
	@CacheLookup
	public WebElement bnbLogo;

	@FindBy(xpath="//a[text()='Our Projects']")
	@CacheLookup
	public WebElement ourProject;

	@FindBy(xpath="//a[text()='Cost Estimator']")
	@CacheLookup
	public WebElement costEstimator;

	@FindBy(xpath="//a[text()='How it Works']")
	@CacheLookup
	public WebElement howItWork;

	@FindBy(xpath="//a[text()='More']")
	@CacheLookup
	public WebElement more;

	@FindBy(xpath="//div[text()='Chat With Us']")
	@CacheLookup
	public WebElement chatWithUs;

	@FindBy(xpath="//div[text()='+91 7505 205 205']")
	@CacheLookup
	public WebElement phone7505;

	@FindBy(xpath="//span[@class='icon-ic_call']")
	@CacheLookup
	public WebElement callIcon;

	@FindBy(xpath = "//a[contains(@class ,'lets-build-shadow')]")
	@CacheLookup
	public WebElement letsBuild;

	@FindBy(xpath="//p[text()='Talk to our Expert']")
	@CacheLookup
	public WebElement talktoExpert;

	@FindBy(xpath="//input[@id='name']")
	@CacheLookup
	public WebElement name;

	@FindBy(xpath="//input[@id='email']")
	@CacheLookup
	public WebElement email;

	@FindBy(xpath="//input[@id='phone']")
	@CacheLookup
	public WebElement phone;

	@FindBy(xpath="//select[@id='city']")
	@CacheLookup
	public WebElement city;

	@FindBy(xpath="//button[@type='submit']")
	@CacheLookup
	public WebElement bookConsultation;

	@FindBy(xpath="//h1[text()='Thank you!']")
	@CacheLookup
	public WebElement Thankyou;

	@FindBy(xpath="//p[contains(text(),'Thank you for filling')]")
	@CacheLookup
	public WebElement Thankyouforfilling;

	@FindBy(xpath="//span[text()='Bengaluru']")
	@CacheLookup
	public WebElement bengaluru;

	@FindBy(xpath="//span[text()='Hyderabad']")
	@CacheLookup
	public WebElement hyderabad;

	@FindBy(xpath="//span[text()='Mysuru']")
	@CacheLookup
	public WebElement mysuru;

	@FindBy(xpath="//h5[text()='Select your city']")
	@CacheLookup
	public WebElement selectYourCity;




}

