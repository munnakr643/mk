package webqa.ensurity.pageScreen;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import webqa.ensurity.pageScreen.manager.ScreenManager;


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


	@FindBy(xpath="//input[@name='phone']")
	@CacheLookup
	public WebElement userId;

	@FindBy(name="password")
	@CacheLookup
	public WebElement txtPassword;

	@FindBy(xpath="//*[@id='root']/div/div[2]/div/form/div/div[4]/button")
	@CacheLookup
	public WebElement proceed;

	//@FindBy(xpath ="//button[@type='submit']")
	@FindBy(xpath ="(//span[@class='MuiButton-endIcon MuiButton-iconSizeMedium'])[3]")
	@CacheLookup
	public WebElement proceedBtn;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 1')]")
	@CacheLookup
	public WebElement otpbox1;

	@FindBy(xpath="//input[contains(@aria-label,'Digit 2')]")
	@CacheLookup
	public WebElement otpbox2;

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

