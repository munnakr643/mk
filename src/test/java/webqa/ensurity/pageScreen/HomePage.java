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
public class HomePage extends ScreenManager {

	public HomePage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@FindBy(xpath="//img[@alt='Broker Logo']")
	@CacheLookup
	public WebElement brokerLogo;

	@FindBy(xpath="//span[text()=' Leads ']")
	@CacheLookup
	public WebElement leads;

	@FindBy(xpath="//p[text()='Earnings']")
	@CacheLookup
	public WebElement earnings;

	@FindBy(xpath="//span[text()=' Profile ']")
	@CacheLookup
	public WebElement profile;

	@FindBy(xpath="(//span[text()='Quote'])[1]")
	@CacheLookup
	public WebElement quote;

	@FindBy(xpath="//b[text()='Generate Quote']")
	@CacheLookup
	public WebElement generateQuote;

}

