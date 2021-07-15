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
public class ProposalPage extends ScreenManager {

	public ProposalPage(WebDriver driver)
	{
		super(driver);
	}

	@Override
	protected String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@FindBy(xpath="//span[text()='M/S']")
	@CacheLookup
	public WebElement m_sHdfc;

	@FindBy(xpath="//span[text()='MR']")
	@CacheLookup
	public WebElement mrHdfc;

	@FindBy(xpath="//span[text()='MRS']")
	@CacheLookup
	public WebElement mrsHdfc;

	@FindBy(xpath="//span[text()='MS']")
	@CacheLookup
	public WebElement msHdfc;


	@FindBy(xpath="//input[@placeholder='First Name']")
	@CacheLookup
	public WebElement fName;

	@FindBy(xpath="//input[@placeholder='Middle Name']")
	@CacheLookup
	public WebElement mName;

	@FindBy(xpath="//input[@placeholder='Last Name']")
	@CacheLookup
	public WebElement lname;


	@FindBy(id="nomineeFirstName")
	@CacheLookup
	public WebElement nomineefName;

	@FindBy(id="nomineeMiddleName")
	@CacheLookup
	public WebElement nomineemName;

	@FindBy(id="nomineeLastName")
	@CacheLookup
	public WebElement nomineelname;

	@FindBy(xpath="//span[text()='MALE']")
	@CacheLookup
	public WebElement maleHdfc;

	@FindBy(xpath="//span[text()='FEMALE']")
	@CacheLookup
	public WebElement femaleHdfc;

	@FindBy(xpath="//span[text()='Male']")
	@CacheLookup
	public WebElement male;

	@FindBy(xpath="//span[text()='Female']")
	@CacheLookup
	public WebElement female;

	@FindBy(xpath="//input[@placeholder='Email']")
	@CacheLookup
	public WebElement email;

	@FindBy(xpath="//input[@placeholder='Phone']")
	@CacheLookup
	public WebElement phone;

	@FindBy(xpath="//input[contains(@placeholder , 'Address')]")
	@CacheLookup
	public WebElement perAddress;

	@FindBy(xpath="//input[contains(@placeholder , 'Address')]")
	//input[contains(@placeholder,Address)]
	@CacheLookup
	public WebElement address;

	@FindBy(xpath="//input[@placeholder='Pincode']")
	@CacheLookup
	public WebElement pincode;

	@FindBy(xpath="//span[text()='Same as Permanent Address']")
	@CacheLookup
	public WebElement sameAsPermAdd;

	@FindBy(xpath="//input[contains(@id , 'omineeName')]")
	@CacheLookup
	public WebElement nomineeName;

	@FindBy(id="NomineeAge")
	@CacheLookup
	public WebElement nomineeAge;

	@FindBy(xpath="//input[@placeholder='Nominee Relationship']")
	@CacheLookup
	public WebElement NomineeRelation;

	@FindBy(xpath="//li[@data-value='SIBLING']")
	@CacheLookup
	public WebElement siblingHdfc;

	@FindBy(xpath = "//input[contains(@id,'icenseNumber')]")
	@CacheLookup
	public WebElement vehicleLicenseNumber;

	@FindBy(xpath="//input[contains(@id,'hassisNumber')]")
	@CacheLookup
	public WebElement vehicleChassisNumber;

	@FindBy(xpath="//input[contains(@id,'ngineNumber')]")
	@CacheLookup
	public WebElement vehicleEngineNumber;

	@FindBy(xpath="//input[@placeholder='Previous Insurer Name']")
	@CacheLookup
	public WebElement prevInsurerDropdown;

	@FindBy(xpath = "//input[contains(@id,'previousPolicyNumber')]")
	@CacheLookup
	public WebElement previousPolicyNumber;

	@FindBy(xpath="//li[contains(@data-value, 'Cholamandalam')]")
	@CacheLookup
	public WebElement prevInsurerName;

	@FindBy(xpath="//span[text()='Review & Submit']")
	@CacheLookup
	public WebElement reviewNsubmit;

	@FindBy(xpath="//div[@aria-haspopup='listbox']")
	@CacheLookup
	public WebElement listbox;

	@FindBy(xpath="//span[text()='M/S']")
	@CacheLookup
	public WebElement m_s;

	@FindBy(xpath="//span[text()='Mr.']")
	@CacheLookup
	public WebElement mr;

	@FindBy(xpath="//span[text()='Mrs.']")
	@CacheLookup
	public WebElement mrs;

	@FindBy(xpath="//span[text()='Ms.']")
	@CacheLookup
	public WebElement ms;

	@FindBy(xpath="//span[text()='Dr.']")
	@CacheLookup
	public WebElement dr;

	@FindBy(xpath="//span[text()='Miss']")
	@CacheLookup
	public WebElement miss;

	@FindBy(xpath="//span[text()='Married']")
	@CacheLookup
	public WebElement married;

	@FindBy(xpath="//span[text()='Single']")
	@CacheLookup
	public WebElement single;

	@FindBy(xpath="//span[text()='Divorcee']")
	@CacheLookup
	public WebElement divorcee;

	@FindBy(xpath="//span[text()='Widow']")
	@CacheLookup
	public WebElement widow;

	@FindBy(xpath="//span[text()='Widower']")
	@CacheLookup
	public WebElement widower;

	//@FindBy(id="communicationAddressLine1")
	@FindBy(xpath = "//input[contains(@id,'ddressLine1')]")
	@CacheLookup
	public WebElement addressLine1;

//	@FindBy(id="communicationAddressLine2")
	@FindBy(xpath = "//input[contains(@id,'ddressLine2')]")
	@CacheLookup
	public WebElement addressLine2;

//	@FindBy(id="communicationAddressLine3")
	@FindBy(xpath = "//input[contains(@id,'ddressLine3')]")
	@CacheLookup
	public WebElement addressLine3;

	@FindBy(xpath="//span[text()='Same as Communication Address']")
	@CacheLookup
	public WebElement sameAsCommunication;

	@FindBy(xpath="//div[text()='Registration Address']/parent::div/following-sibling::div")
	@CacheLookup
	public WebElement sameAsCommRegAdd;

	@FindBy(xpath="//p[text()='Is Vehicle hypothecated?']/parent::div/following-sibling::div[1]")
	@CacheLookup
	public WebElement yes2;

	@FindBy(xpath="//p[text()='Is Vehicle hypothecated?']/parent::div/following-sibling::div[2]")
	@CacheLookup
	public WebElement no2;

	@FindBy(xpath="//span[text()='Yes, Agree']//parent::button")
	@CacheLookup
	public WebElement agree;

	@FindBy(xpath="//div[text()='Financier Address']/parent::div/div[2]/div/input")
	@CacheLookup
	public WebElement financierAddress;

	@FindBy(id = "financerName")
	@CacheLookup
	public WebElement financerName;

	@FindBy(xpath="//span[text()='Comprehensive']")
	@CacheLookup
	public WebElement comprehensive;

	@FindBy(xpath="//span[text()='Thirdparty']")
	@CacheLookup
	public WebElement thirdparty;

	@FindBy(xpath="(//span[text()='Yes'])[2]")
	@CacheLookup
	public WebElement yes2Tata;

	@FindBy(xpath="(//span[text()='No'])[2]")
	@CacheLookup
	public WebElement no2Tata;

//	@FindBy(id="tpPolicyNumber")
	@FindBy(xpath = "//input[contains(@id,'pPolicyNumber')]")
	@CacheLookup
	public WebElement tpPolicyNumber;

	@FindBy(xpath="(//div[@aria-haspopup='listbox'])[3]")
	@CacheLookup
	public WebElement listbox3;

	@FindBy(xpath="//span[text()='Open Proposal']")
	@CacheLookup
	public WebElement openProposal;

	@FindBy(id = "previousTpAddress")
	@CacheLookup
	public WebElement prevTpAddress;


	@FindBy(id = "pervClaims")
	@CacheLookup
	public WebElement pervClaims;

	@FindBy(id = "PrevTenure")
	@CacheLookup
	public WebElement prevTenure;

	@FindBy(id = "cpaappointeeName")
	@CacheLookup
	public WebElement appointeeName;

	@FindBy(id = "previousInsurerPincode")
	@CacheLookup
	public WebElement previousInsurerPincode;

	@FindBy(id = "previousInsurerAddressLine1")
	@CacheLookup
	public WebElement previousInsurerAddressLine1;

	@FindBy(id = "previousInsurerAddressLine2")
	@CacheLookup
	public WebElement previousInsurerAddressLine2;

	@FindBy(xpath="//input[@placeholder='dd/mm/yyyy']")
	@CacheLookup
	public WebElement date;
}

