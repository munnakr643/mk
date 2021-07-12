package webqa.ensurity.pageScreen;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import webqa.ensurity.pageScreen.manager.ScreenManager;


public class PolicyDownloadPage extends ScreenManager {

	public PolicyDownloadPage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}


	@FindBy(xpath="//span[text()='Back Home']")
	@CacheLookup
	public WebElement backHome;

	@FindBy(id="cardTab")
	@CacheLookup
	public WebElement creditDebit;

	@FindBy(xpath="//span[text()='Download policy']")
	@CacheLookup
	public WebElement downloadPolicy;

	@FindBy(xpath="//p[text()='Success']")
	@CacheLookup
	public WebElement successPage;

	@FindBy(xpath = "//p[contains(text(),'Insurance ')] | //p[contains(text(),'Policy download already in process')]")
	@CacheLookup
	public WebElement insurerDownloadMessage;

	@FindBy(xpath = "//p[contains(text(),'Policy download already in process')]")
	@CacheLookup
	public WebElement insurerDownloadMessageRel;

	@FindBy(xpath = "//div[contains(@style,'0ms; visibility')]")
	@CacheLookup
	public WebElement loadingIconDisable;

	@FindBy(xpath="//p[contains(text(),'Transaction')]")
	@CacheLookup
	public WebElement transactionNumText;

	@FindBy(xpath="//p[contains(text(),'Transaction')]/following-sibling::p[1]")
	@CacheLookup
	public WebElement transactionID;

	@FindBy(xpath="//p[contains(text(),'Transaction')]/following-sibling::p[2]")
	@CacheLookup
	public WebElement policyNumText;

	@FindBy(xpath="//p[contains(text(),'Transaction')]/following-sibling::p[2]")
	@CacheLookup
	public WebElement policyNum;

}

