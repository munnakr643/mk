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
public class LoginPage extends ScreenManager {

	public LoginPage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@FindBy(xpath="//img[@alt='bnb-logo']")
	@CacheLookup
	public WebElement crmBnbLogo;

	@FindBy(xpath = "//h1[text()='CRM']")
	@CacheLookup
	public WebElement crmText;

	@FindBy(xpath = "//button[text()='Login with Google']")
	@CacheLookup
	public WebElement crmLoginText;

	@FindBy(xpath="//button[@type='button']")
	@CacheLookup
	public WebElement crmLoginButton;

	@FindBy(xpath ="//input[@aria-label='Enter your email']")
	@CacheLookup
	public WebElement crmUserId;

	@FindBy(xpath ="//span[text()='Next']")
	@CacheLookup
	public WebElement crmNext;

	@FindBy(xpath ="//input[@aria-label='Enter your password']")
	@CacheLookup
	public WebElement crmPassword;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 1')]")
	@CacheLookup
	public WebElement otpbox1;

	@FindBy(xpath="//cr-toggle[@id='cookie-controls-toggle']")
	@CacheLookup
	public WebElement cookiesToggle;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 3')]")
	@CacheLookup
	public WebElement otpbox3;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 4')]")
	@CacheLookup
	public WebElement otpbox4;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 5')]")
	@CacheLookup
	public WebElement otpbox5;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 6')]")
	@CacheLookup
	public WebElement otpbox6;


}

