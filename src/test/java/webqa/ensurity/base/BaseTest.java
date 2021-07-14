package webqa.ensurity.base;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import webqa.ensurity.pageScreen.*;
import webqa.ensurity.report.ExtentTestManager;
import webqa.ensurity.utilities.ReadConfig;
import webqa.ensurity.utilities.ScreenUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest {

	ReadConfig readconfig = new ReadConfig();

	public String baseURL = readconfig.getApplicationURL();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	public static WebDriver driver;

	protected static HomePage homePage;
	protected static LoginPage loginPage;
	protected static QuotePage quotePage;
	protected static ProposalPage proposalPage;
	protected static PaymentPage paymentPage;
	protected static PolicyDownloadPage policyDownloadPage;



	public static Logger logger = LogManager.getLogger(BaseTest.class);

	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setup(String br) {

		logger = Logger.getLogger("EnsuredIT");
		PropertyConfigurator.configure("log4j.properties");
		logger.info("---------------- Start Logging ----------------");
		logger.info("BaseBrowserClass: Setting up web browser");

		if (br.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", readconfig.getChromePath());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");

			driver = new ChromeDriver(options);
		} else if (br.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", readconfig.getFirefoxPath());
			driver = new FirefoxDriver();
		} else if (br.equals("safari")) {
			driver = new SafariDriver();
		}
		driver.manage().window().maximize();

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		quotePage = new QuotePage(driver);
		proposalPage = new ProposalPage(driver);
		paymentPage = new PaymentPage(driver);
		policyDownloadPage = new PolicyDownloadPage(driver);

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.get(baseURL);
	}


	public void refreshPage(){
		waitFor(1);
		driver.navigate().refresh();
		logger.info("page refresh successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "page refresh successfully");
		waitFor(2);
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
		logger.info("browser tear down successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "browser tear down successfully");
	}

	@AfterMethod
	public void extentReportsFailOperation(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			String base64Screenshot = ScreenUtil.getBase64Screenshot(driver);
			ExtentTestManager.getTest().log(LogStatus.FAIL,
					"Test " + result.getMethod() + " has failed",
					ExtentTestManager.getTest().addBase64ScreenShot(base64Screenshot));
		}
	}

	public void captureScreen(WebDriver driver, String tname) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tname + ".png");
		FileUtils.copyFile(source, target);
		System.out.println("Screenshot taken");
	}

	public void waitElement(WebElement element, int timer) {
		WebDriverWait wait = new WebDriverWait(driver, timer);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitElementForClickable(WebElement element, int timer) {
		WebDriverWait wait = new WebDriverWait(driver, timer);
		wait.until(ExpectedConditions.elementToBeClickable(element));
	}

	public static void waitFor(double seconds) {
		try {
			Thread.sleep((int) (seconds * 1000));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String randomestring() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return (generatedstring);
	}

	public static String randomeNum() {
		String generatedString2 = RandomStringUtils.randomNumeric(4);
		return (generatedString2);
	}

	public static String randomeNum8() {
		String generatedString2 = RandomStringUtils.randomNumeric(8);
		return (generatedString2);
	}

	/*
	for scroll up use +ve number for yOffset like jse.executeScript("window.scrollBy(0,500)");
	for scroll down use -ve number for yOffset like jse.executeScript("window.scrollBy(0,-500)");
	 */
	public void scrollUpDown(int xOffset,int yOffset) {
		waitFor(2);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		waitFor(1);
		js.executeScript("window.scrollBy("+xOffset+", "+yOffset+")");
		waitFor(1);
	}

	public void clickByCoordinate(int xCoordinate,int YCoordinate) {
		Actions action = new Actions(driver);
		action.moveByOffset(xCoordinate,YCoordinate).doubleClick().perform();

	}

	public void scrollToButtom(){
		waitFor(2);
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void switchToChildWindow() {
		waitFor(3);
		logger.info(driver.getWindowHandles().size());
//		for (String childWindow : driver.getWindowHandles()) {
//			waitFor(1);
//			driver.switchTo().window(childWindow);
//		}
		String parentWindow = driver.getWindowHandle();
		Set<String> s1 = driver.getWindowHandles();
		Iterator<String> i1 = s1.iterator();
		while (i1.hasNext()) {
			String childWindow = i1.next();
			if (!parentWindow.equalsIgnoreCase(childWindow)) {
				driver.switchTo().window(childWindow);
			}
		}

		logger.info("Child window selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Child window selected successfully");
	}

	public void switchToTab(String parentWindow) {
		waitFor(1);
			driver.switchTo().window(parentWindow);
		logger.info("parent window selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "parent window selected successfully");
	}

	public void closeCurrentTab() {
		waitFor(1);
		driver.close();
		logger.info("current tab closed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "current tab closed successfully");
	}

	public void entreUserName() {
		waitElement(loginPage.userId, 3);
		assertThat(loginPage.userId.isDisplayed(), equalTo(true));
		loginPage.userId.sendKeys(readconfig.getUsername());
		logger.info("userID " + readconfig.getUsername() + " Enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "userID " + readconfig.getUsername() + " Enter successfully");
	}

	public void clickOnProceedBtn() {
		waitElement(loginPage.proceed, 3);
		assertThat(loginPage.proceed.isDisplayed(), equalTo(true));
		loginPage.proceed.click();
		logger.info("proceed btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "proceed btn clicked successfully");
	}

	public void actionOnProceed(){
		enterTextAction("Proceed",1);
		logger.info("proceed btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "proceed btn clicked successfully");
	}
	

	public void enterOtp()  {
		waitElement(loginPage.otpbox1, 7);
		assertThat(loginPage.otpbox1.isDisplayed(), equalTo(true));
		String otp=readconfig.getPassword();
		for(int i=1;i<=otp.length();i++){
			String num= String.valueOf(otp.charAt(i-1));
			String e="Digit "+i;
			WebElement enterotp=driver.findElement(By.xpath("//input[contains(@aria-label,'"+e+"')]"));
			enterotp.sendKeys(num);
		}
		logger.info("otp " +otp+ " Enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "otp " +otp+ " Enter successfully");
		waitFor(1);
	}

	public void clickOnProceedBtnAfterOtp() {
		waitElement(loginPage.proceedBtn, 3);
		assertThat(loginPage.proceedBtn.isDisplayed(), equalTo(true));
		loginPage.proceedBtn.click();
		logger.info("proceed btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "proceed btn clicked successfully");
	}

	public void isHomePageScreenDisplay() {
		waitElement(homePage.brokerLogo, 30);
		assertThat(homePage.brokerLogo.isDisplayed(), equalTo(true));
		assertThat(homePage.leads.isDisplayed(), equalTo(true));
		assertThat(homePage.profile.isDisplayed(), equalTo(true));
		assertThat(homePage.generateQuote.isDisplayed(), equalTo(true));
		logger.info("Homepage screen is appearing");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Homepage screen is appearing");
		waitFor(1);
	}

	public void selectTwoWheeler() {
		waitElement(quotePage.twoWheeler, 3);
		assertThat(quotePage.twoWheeler.isDisplayed(), equalTo(true));
		quotePage.twoWheeler.click();
		logger.info("select two wheeler successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select two wheeler successfully");
	}

	public void selectFourWheeler() {
		waitElement(quotePage.carInsurance, 3);
		assertThat(quotePage.carInsurance.isDisplayed(), equalTo(true));
		quotePage.carInsurance.click();
		logger.info("select two wheeler successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select two wheeler successfully");
	}

	public void enterCustomerName(String custrName) {
		waitElement(quotePage.customerName, 3);
		assertThat(quotePage.customerName.isDisplayed(), equalTo(true));
		quotePage.customerName.sendKeys(custrName);
		logger.info("customerName "+custrName+" entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "customerName "+custrName+" entered successfully");
	}

		public void clickOnNextBtn() {
		waitFor(1);
			waitElement(quotePage.nextbtn, 3);
			assertThat(quotePage.nextbtn.isDisplayed(), equalTo(true));
			quotePage.nextbtn.click();
			logger.info("Next Btn btn clicked successfully");
			ExtentTestManager.getTest().log(LogStatus.PASS, "Next Btn btn clicked successfully");
		}

	public void clickOnNextButton() {
		waitFor(1);
		waitElement(quotePage.nextbutton, 3);
		assertThat(quotePage.nextbutton.isDisplayed(), equalTo(true));
		quotePage.nextbutton.click();
		logger.info("Next Btn btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Next Btn btn clicked successfully");
	}

	public void enterMMVDetails(String make,String model,String variant) {
		waitElement(quotePage.searchButton, 3);
		assertThat(quotePage.searchButton.isDisplayed(), equalTo(true));
		quotePage.searchButton.click();
		waitElement(quotePage.inputBar, 3);
		assertThat(quotePage.inputBar.isDisplayed(), equalTo(true));
		quotePage.inputBar.sendKeys(make);
		waitElement(quotePage.firstRow, 3);
		assertThat(quotePage.firstRow.isDisplayed(), equalTo(true));
		if (make.equalsIgnoreCase(quotePage.firstRow.getText())){
			quotePage.firstRow.click();
		}else {
			quotePage.secondRow.click();
		}
		logger.info("make selected as "+make+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "make selected as "+make+" successfully");
		waitFor(1);
		Actions act=new Actions(driver);
		// search button using action class due to facing StaleElementReferenceException
		act.moveToElement(driver.findElement(By.xpath("(//button[@type='button'])[1]"))).click().perform();
		waitFor(1);
		//enter rto code using action class due to facing StaleElementReferenceException
		act.sendKeys(driver.findElement(By.xpath("(//input[@type='text'])[2]")),model).build().perform();
		waitFor(1);
		waitElement(quotePage.modelRow1, 3);
		assertThat(quotePage.modelRow1.isDisplayed(), equalTo(true));
		if (model.equalsIgnoreCase(quotePage.modelRow1.getText())){
			quotePage.modelRow1.click();
		}else {
			quotePage.modelRow2.click();
		}
		logger.info("model selected as "+model+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "model selected as "+model+" successfully");
		waitElement(quotePage.petrol, 3);
		assertThat(quotePage.petrol.isDisplayed(), equalTo(true));
		quotePage.petrol.click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitFor(1);
		driver.findElement(By.xpath("//div[contains(text(),'"+variant+"')]")).click();
		logger.info("variant selected as "+variant+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "variant selected as "+variant+" successfully");
	}

	public void selectMMV() {
		waitElement(quotePage.honda, 3);
		assertThat(quotePage.honda.isDisplayed(), equalTo(true));
		quotePage.honda.click();
		logger.info("select honda successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select honda successfully");
		waitElement(quotePage.activa, 3);
		assertThat(quotePage.activa.isDisplayed(), equalTo(true));
		quotePage.activa.click();
		logger.info("select activa successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select activa successfully");
		waitElement(quotePage.petrol, 3);
		assertThat(quotePage.petrol.isDisplayed(), equalTo(true));
		quotePage.petrol.click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitElement(quotePage._3G109cc, 3);
		assertThat(quotePage._3G109cc.isDisplayed(), equalTo(true));
		quotePage._3G109cc.click();
		logger.info("select 3G109cc successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select 3G109cc successfully");
	}

	public void select4wMMV() {
		waitElement(quotePage.honda4w, 3);
		assertThat(quotePage.honda4w.isDisplayed(), equalTo(true));
		quotePage.honda4w.click();
		logger.info("select honda successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select honda successfully");
		waitElement(quotePage.hondaCity, 3);
		assertThat(quotePage.hondaCity.isDisplayed(), equalTo(true));
		quotePage.hondaCity.click();
		logger.info("select activa successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select activa successfully");
		waitElement(quotePage.petrol, 3);
		assertThat(quotePage.petrol.isDisplayed(), equalTo(true));
		quotePage.petrol.click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitElement(quotePage._1point3EXI1343CC, 3);
		assertThat(quotePage._1point3EXI1343CC.isDisplayed(), equalTo(true));
		quotePage._1point3EXI1343CC.click();
		logger.info("select 3G109cc successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select 3G109cc successfully");
	}

	public void enterRtoCode(String rtoCode){
		waitFor(1);
		Actions act=new Actions(driver);
		// search button using action class due to facing StaleElementReferenceException
		act.moveToElement(driver.findElement(By.xpath("(//button[@type='button'])[1]"))).click().perform();
		waitFor(1);
		//enter rto code using action class due to facing StaleElementReferenceException
		act.sendKeys(driver.findElement(By.xpath("(//input[@type='text'])[2]")),rtoCode).build().perform();
		waitFor(1);
		act.moveToElement(driver.findElement(By.xpath("(//div[@role='button'])[3]"))).click().perform();
		logger.info("rtoCode selected as "+rtoCode+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "rtoCode selected as "+rtoCode+" successfully");
	}

	public void rtoLocation() {
		waitElement(quotePage.mumbai, 3);
		assertThat(quotePage.mumbai.isDisplayed(), equalTo(true));
		quotePage.mumbai.click();
		logger.info("select mumbai successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select mumbai successfully");
		waitFor(1);
		waitElement(quotePage.mumbai, 3);
		assertThat(quotePage.mumbai.isDisplayed(), equalTo(true));
		quotePage.city.sendKeys(" ");
		waitFor(1);
		waitElement(quotePage.mumbaiSouth, 3);
		assertThat(quotePage.mumbaiSouth.isDisplayed(), equalTo(true));
		quotePage.mumbaiSouth.click();
		logger.info("select mumbaiSouth successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select mumbaiSouth successfully");
	}

	public void selectRegistrationDate(String year) {
		waitFor(1);
		//clickOnChooseDate();
		waitElement(quotePage.chooseDate, 3);
		assertThat(quotePage.chooseDate.isDisplayed(), equalTo(true));
		quotePage.chooseDate.click();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		waitElement(quotePage.date16, 3);
		assertThat(quotePage.date16.isDisplayed(), equalTo(true));
		quotePage.date16.click();
		logger.info("Registration date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Registration date selected successfully");
	}

	public void selectNewVehicle(){
		waitElementForClickable(quotePage.newVehicle, 3);
		assertThat(quotePage.newVehicle.isDisplayed(), equalTo(true));
		quotePage.newVehicle.click();
		logger.info("NewVehicle selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "NewVehicle selected successfully");
	}

	public void selectPrevMonthDate(String year) {
		waitFor(1);
		clickOnChooseDate();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//button[@aria-label='previous month']"))).click().perform();
		waitFor(1);
		chooseDateUsingAction();
		logger.info("Registration date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Registration date selected successfully");
	}

	public void selectExpiryDate(String year) {
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String formatted = df.format(new Date());
		int currentDate=Integer.parseInt(formatted);
		waitFor(1);
		clickOnChooseDate();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		if(currentDate>=15){
		nextMonthAction();}
		waitFor(1);
		chooseDateUsingAction();
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}
	public void selectExpiryDate2(String year) {
		waitFor(1);
		clickOnChooseDate2(4);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		nextMonthAction();
		waitFor(1);
		chooseDateUsingAction();
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}


	public void selectExpiryDate2Bajaj(String year) {
		waitFor(1);
		clickOnChooseDate2(3);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		nextMonthAction();
		waitFor(1);
		chooseDateUsingAction();
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}

	public void selectDOB(String year) {
		waitElement(quotePage.chooseDate, 3);
		assertThat(quotePage.chooseDate.isDisplayed(), equalTo(true));
		quotePage.chooseDate.click();
		waitFor(1);
		WebElement element=driver.findElement(By.xpath("//button[text()='1944']"));
		element.sendKeys(Keys.PAGE_DOWN);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		chooseDateUsingAction();
		logger.info("Registration date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Registration date selected successfully");
	}

	public void selectDobAction(String year) {
		waitFor(1);
		clickOnChooseDate();
		waitFor(1);
		WebElement element=driver.findElement(By.xpath("//button[text()='1944']"));
		element.sendKeys(Keys.PAGE_DOWN);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		chooseDateUsingAction();
		logger.info("date of birth selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "DOB selected successfully");
	}

	public void notExpYet() {
		waitElement(quotePage.rollOver, 3);
		assertThat(quotePage.rollOver.isDisplayed(), equalTo(true));
		quotePage.rollOver.click();
		logger.info("prev policy expiry selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry selected successfully");
	}

	public void selectPolicyExpiry(String polExpiry){
		waitElement(quotePage.rollOver, 3);
		assertThat(quotePage.rollOver.isDisplayed(), equalTo(true));
		assertThat(quotePage.breakin45.isDisplayed(), equalTo(true));
		assertThat(quotePage.breakin90.isDisplayed(), equalTo(true));
		assertThat(quotePage.dontKnowPPExpDate.isDisplayed(), equalTo(true));
		if (polExpiry.contains("Don’t")){
			quotePage.dontKnowPPExpDate.click();
		}else if (polExpiry.contains("more")){
			quotePage.breakin90.click();
		}else {
			quotePage.breakin45.click();
		}
		logger.info("prev policy expiry selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry selected successfully");
	}

	public void expiredWithin90days() {
		waitElement(quotePage.breakin45, 3);
		assertThat(quotePage.breakin45.isDisplayed(), equalTo(true));
		quotePage.breakin45.click();
		logger.info("prev policy expiry date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry date selected successfully");
	}

	public void expiredMore90days() {
		waitElement(quotePage.breakin90, 3);
		assertThat(quotePage.breakin90.isDisplayed(), equalTo(true));
		quotePage.breakin90.click();
		logger.info("prev policy expiry date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry date selected successfully");
	}

	public void backBtnPlanDetailsPage() {
		waitElement(quotePage.planBackBtn, 3);
		assertThat(quotePage.planBackBtn.isDisplayed(), equalTo(true));
		quotePage.planBackBtn.click();
		logger.info("clicked on planBackBtn successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, " clicked on planBackBtn successfully");
	}

	public void prevPolicyClaim(String Yes_No) {
		waitElement(quotePage.yes, 3);
		assertThat(quotePage.yes.isDisplayed(), equalTo(true));
		if(Yes_No.equalsIgnoreCase("Yes")){
			quotePage.yes.click();
		}else {
			quotePage.no.click();
		}
		logger.info("prev policy claimed selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy claimed selected successfully");
	}

	public void prevNcbPercent(String ncbPercetage) {
		waitElement(quotePage.ncb35, 5);
		assertThat(quotePage.ncb35.isDisplayed(), equalTo(true));
		waitFor(1);
		if (ncbPercetage.contains("remember"))
		{waitElement(quotePage.ncb35, 5);
			assertThat(quotePage.ncb35.isDisplayed(), equalTo(true));
			quotePage.dontRememberNcb.click(); }
		else {
		driver.findElement(By.xpath("//div[text()='"+ncbPercetage+"']")).click();}
		logger.info("Prev NCB "+ncbPercetage+"% selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Prev NCB  "+ncbPercetage+"% selected successfully");
	}

	public void selectPrevInsurer() {
		waitElement(quotePage.acko, 3);
		assertThat(quotePage.acko.isDisplayed(), equalTo(true));
		quotePage.acko.click();
		logger.info("prev policy insurer selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy insurer selected successfully");
	}

	public void selectPrevPolType(String polType) {
		waitElement(quotePage.comp1yr, 5);
		assertThat(quotePage.comp1yr.isDisplayed(), equalTo(true));
		if (polType.equalsIgnoreCase("saod")){
			quotePage.saod.click();}
		else if (polType.equalsIgnoreCase("Bundle")){
			quotePage.bundle1plus5.click();
		}else if (polType.equalsIgnoreCase("5yrTP")){
			quotePage.tp5yr.click();
		}else if (polType.equalsIgnoreCase("1yrTP")){
			quotePage.tp1yr.click();
		}else {
			quotePage.comp1yr.click();
		}
		logger.info("prev policy Type selected as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy Type selected as "+polType+" successfully");
	}

	public void select4wPrevPolType(String polType) {
		waitElement(quotePage.comp1yr, 5);
		assertThat(quotePage.comp1yr.isDisplayed(), equalTo(true));
		if (polType.equalsIgnoreCase("saod")){
			quotePage.saod.click();}
		else if (polType.equalsIgnoreCase("Bundle")){
			quotePage.bundle1plus3.click();
		}else if (polType.equalsIgnoreCase("3yrTP")){
			quotePage.tp3yr.click();
		}else if (polType.equalsIgnoreCase("1yrTP")){
			quotePage.tp1yr.click();
		}else {
			quotePage.comp1yr.click();
		}
		logger.info("prev policy Type selected as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy Type selected as "+polType+" successfully");

	}

	public void clickOngenerateQuote() {
		waitElement(quotePage.generateQuote, 13);
		assertThat(quotePage.generateQuote.isDisplayed(), equalTo(true));
		quotePage.generateQuote.click();
		try {
			waitFor(6);
			Actions action = new Actions(driver);
			action.moveToElement(quotePage.generateQuote).doubleClick().perform();
			logger.info("clicked on generate quote successfully");
		}catch (Exception e){}
		logger.info("clicked on generate quote successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on generate quote successfully");
	}

	public void clickOnNext() {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
					@Override
					public Object apply(Object arg0) {
						//button[@type='submit']
						WebElement e = driver.findElement(By.xpath("//span[text()='Next']"));
						Actions action = new Actions(driver);
						action.moveToElement(e).doubleClick().perform();
						return true;
					}
				});

	}


	public void clickOnPrevInsurerListbox() {
		waitFor(.5);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//button[@aria-label='Open']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void clickOnPrevFinacierType() {
		waitFor(.5);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//div[@aria-haspopup='listbox']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void clickOnPrevInsurerUsingInstance(int instance) {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				String strrr="(//button[@aria-label='Open'])"+ "[" + instance + "]";
				WebElement e = driver.findElement(By.xpath(strrr));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void openDropDown(String text) {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//input[@placeholder ='"+text+"']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void clickOnChooseDate() {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//button[@aria-label='Choose date']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void clickOnChooseDate2(int i) {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				String st="//div/form/div/div/div["+i+"]/div/div/div/div/button";
				//(//button[@data-mui-test='open-picker-from-keyboard'])[2]
				WebElement e = driver.findElement(By.xpath(st));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void chooseDateUsingAction() {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//span[text()='16']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}


	public void enterTextAction(String text,int intance) {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				String xpathUsingText="(//span[contains(text(),'"+text+"')])"+ "[" + intance + "]";
				logger.info("----------xpathUsingText---------"+xpathUsingText);
				WebElement e = driver.findElement(By.xpath(xpathUsingText));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void buyNowAction() {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//span[text()='Buy Now']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void buyNowTataAction() {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("(//span[text()='Buy Now'])[2]"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void nextMonthAction() {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//button[@aria-label='next month']"));
				//WebElement e=quotePage.nextMonth;
				Actions action = new Actions(driver);
//				action.moveToElement(e).click().perform();
				action.click(e).build().perform();
				return true;
			}
		});
	}

	public void polExpDateRollover(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 10);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("ddMMyyyy");
		String date1 = format1.format(date);
		waitElement(proposalPage.date,3);
		assertThat(proposalPage.date.isDisplayed(), equalTo(true));
		proposalPage.date.sendKeys(date1);
	}

	public void enterDate(String ddmmyyyy){
		waitElement(proposalPage.date,3);
		assertThat(proposalPage.date.isDisplayed(), equalTo(true));
		proposalPage.date.sendKeys(ddmmyyyy);
	}


	public void isSelectedPolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("saod")|polType.equalsIgnoreCase("Bundle")|polType.equalsIgnoreCase("3yrTP")|polType.equalsIgnoreCase("5yrTP")){
			waitElement(quotePage.polTypeOD, 30);
			assertThat(quotePage.polTypeOD.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolTypeOD.isDisplayed(), equalTo(true));
		}else if (polType.equalsIgnoreCase("1yrTP")){
			waitElement(quotePage.polTypeTp, 120);
			assertThat(quotePage.polTypeTp.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolTypeTp.isDisplayed(), equalTo(true));
		}else {
			waitElement(quotePage.polTypeComp, 60);
			assertThat(quotePage.polTypeComp.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolTypeComp.isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void isNewVehiclePolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("5TP")){
			waitElement(quotePage.polType5TP, 70);
			assertThat(quotePage.polType5TP.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolType5TP.isDisplayed(), equalTo(true));
		} else {
			waitElement(quotePage.insPolType1OD_5TP, 3);
			assertThat(quotePage.insPolType1OD_5TP.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolType1OD_5TP.isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void is4wNewVehiclePolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("3TP")){
			waitElement(quotePage.polType3TP, 70);
			assertThat(quotePage.polType3TP.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolType3TP.isDisplayed(), equalTo(true));
		} else {
			waitElement(quotePage.insPolType1OD_3TP, 3);
			assertThat(quotePage.insPolType1OD_3TP.isDisplayed(), equalTo(true));
			assertThat(quotePage.insPolType1OD_3TP.isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void changePolicyTypeForNewVehicle(String changeTo){
		waitElement(quotePage.polTypePlanPage,3);
		assertThat(quotePage.polTypePlanPage.isDisplayed(), equalTo(true));
		quotePage.polTypePlanPage.click();
		waitElement(quotePage.apply,3);
		assertThat(quotePage.apply.isDisplayed(), equalTo(true));
		assertThat(quotePage._1OD_5TP.isDisplayed(), equalTo(true));
		assertThat(quotePage._5TP.isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("5TP")){
			quotePage._5TP.click();
			waitElement(quotePage.apply,3);
			quotePage.apply.click();
		}else {
			quotePage.planBackBtn.click();}
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void change4wPolicyTypeForNewVehicle(String changeTo){
		waitElement(quotePage.polTypePlanPage,3);
		assertThat(quotePage.polTypePlanPage.isDisplayed(), equalTo(true));
		quotePage.polTypePlanPage.click();
		waitElement(quotePage.apply,3);
		assertThat(quotePage.apply.isDisplayed(), equalTo(true));
		assertThat(quotePage._1OD_3TP.isDisplayed(), equalTo(true));
		assertThat(quotePage._3TP.isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("3TP")){
			quotePage._3TP.click();
			waitElement(quotePage.apply,3);
			quotePage.apply.click();
		}else {
			quotePage.backBtnAddonPage.click(); }
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void changePolicyType(String changeTo){
		waitElement(quotePage.polTypePlanPage,3);
		assertThat(quotePage.polTypePlanPage.isDisplayed(), equalTo(true));
		quotePage.polTypePlanPage.click();
		waitElement(quotePage.apply,3);
		assertThat(quotePage.apply.isDisplayed(), equalTo(true));
		assertThat(quotePage.comprehensive.isDisplayed(), equalTo(true));
		assertThat(quotePage.thirdParty.isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("TP")){
			quotePage.thirdParty.click();
		}else {
			quotePage.comprehensive.click(); }
		waitElement(quotePage.apply,3);
		quotePage.apply.click();
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void isPlanPageDisplayed(){
		waitElement(quotePage.viewDetails, 120);
		waitElement(quotePage.motorProfile, 3);
		assertThat(quotePage.motorProfile.isDisplayed(), equalTo(true));
		assertThat(quotePage.polTypePlanPage.isDisplayed(), equalTo(true));
		assertThat(quotePage.idv.isDisplayed(), equalTo(true));
		assertThat(quotePage.bestMatch.isDisplayed(), equalTo(true));
		assertThat(quotePage.addOns.isDisplayed(), equalTo(true));
		logger.info("verify appearance of plan page successfully");
		logger.info("Quote Name - "+quotePage.motorProfile.getText());
		logger.info(driver.getCurrentUrl());
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify appearance of plan page successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Quote Name - "+quotePage.motorProfile.getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, driver.getCurrentUrl());
	}


	public void clickOnViewDetailsDigit(){
		waitElement(quotePage.digitViewDetails, 3);
		assertThat(quotePage.digitViewDetails.isDisplayed(), equalTo(true));
		quotePage.digitViewDetails.click();
		logger.info("clicked on Digit view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Digit view Details successfully");

	}

	public void clickOnViewDetailsReliance(){
		waitElement(quotePage.relianceViewDetails, 3);
		assertThat(quotePage.relianceViewDetails.isDisplayed(), equalTo(true));
		quotePage.relianceViewDetails.click();
		logger.info("clicked on Reliance view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Reliance view Details successfully");

	}

	public void clickOnBuyNow(){
		waitElement(quotePage.buyNow, 3);
		assertThat(quotePage.buyNow.isDisplayed(), equalTo(true));
		quotePage.buyNow.click();
		logger.info("clicked on Buy now successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Buy Now successfully");

	}

	public void clickOnViewDetailsHdfcErgo(){
		waitElement(quotePage.hdfcViewDetails, 3);
		assertThat(quotePage.hdfcViewDetails.isDisplayed(), equalTo(true));
		quotePage.hdfcViewDetails.click();
		logger.info("clicked on HdfcErgo view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on HdfcErgo view Details successfully");
	}

	public void clickBuyNowHdfcErgo(){
		waitElement(quotePage.hdfcBuyNow, 3);
		assertThat(quotePage.hdfcBuyNow.isDisplayed(), equalTo(true));
		quotePage.hdfcBuyNow.click();
		logger.info("clicked on HdfcErgo BuyNow successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on HdfcErgo BuyNow successfully");
	}

	public void clickBuyNowIcici(){
		waitElement(quotePage.iciciBuyNow, 3);
		assertThat(quotePage.iciciBuyNow.isDisplayed(), equalTo(true));
		quotePage.iciciBuyNow.click();
		logger.info("clicked on icici BuyNow successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on icici BuyNow successfully");
	}

	public void clickOnViewDetailsTataAig(){
		waitElement(quotePage.tataViewDetails, 3);
		assertThat(quotePage.tataViewDetails.isDisplayed(), equalTo(true));
		quotePage.tataViewDetails.click();
		logger.info("clicked on TataAig view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on TataAig view Details successfully");

	}

	public void clickOnViewDetailsIciciL(){
		waitElement(quotePage.iciciViewDetails, 3);
		assertThat(quotePage.iciciViewDetails.isDisplayed(), equalTo(true));
		quotePage.iciciViewDetails.click();
		logger.info("clicked on ICICI Lombard view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on ICICI Lombard view Details successfully");
	}

	public void clickOnViewDetailsBajaj(){
		waitElement(quotePage.bajajViewDetails, 3);
		assertThat(quotePage.bajajViewDetails.isDisplayed(), equalTo(true));
		quotePage.bajajViewDetails.click();
		logger.info("clicked on Bajaj Allianz view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Bajaj Allianz view Details successfully");

	}

	public void clickOnViewDetailsfutureGeneral(){
		waitElement(quotePage.futureViewDetails, 3);
		assertThat(quotePage.futureViewDetails.isDisplayed(), equalTo(true));
		quotePage.futureViewDetails.click();
		logger.info("clicked on futureGeneral view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on futureGeneral view Details successfully");

	}

	public void getSubtotal(){
		waitElement(quotePage.subTotal, 3);
		assertThat(quotePage.subTotal.isDisplayed(), equalTo(true));
		logger.info("verify subTotal "+quotePage.subTotal.getText()+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify subTotal "+quotePage.subTotal.getText()+" successfully");
	}

	public String getSubTotalWith18percentGst(){
		waitElement(quotePage.subTotal, 3);
		assertThat(quotePage.subTotal.isDisplayed(), equalTo(true));
		String subTotalAmt=quotePage.subTotal.getText().replaceAll("₹", "").replaceAll(",","").trim();
		float stAmt=Float.parseFloat(subTotalAmt);
		float gst18=(stAmt*18)/100;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		return	df.format(stAmt+gst18).replaceAll(",","");
	}

	public String get18percentGstAmt(){
		waitElement(quotePage.subTotal, 3);
		assertThat(quotePage.subTotal.isDisplayed(), equalTo(true));
		String subTotalAmt=quotePage.subTotal.getText().replaceAll("₹", "").replaceAll(",","").trim();
		float stAmt=Float.parseFloat(subTotalAmt);
		float gst18=(stAmt*18)/100;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		return	df.format(gst18).replaceAll(",","");
	}

	public void verify18percentGstAmt(String calTax){
		float ft=Float.parseFloat(calTax);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		String calTax1=df.format(ft+1).replaceAll(",","");
		String calTax2=df.format(ft-1).replaceAll(",","");
		waitElement(quotePage.gst18, 3);
		assertThat(quotePage.gst18.isDisplayed(), equalTo(true));
		String gstAmt=quotePage.gst18.getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(gstAmt, anyOf(is(calTax), is(calTax1),is(calTax2)));
		logger.info("verify gst 18% as amount "+calTax+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify gst 18% as amount "+calTax+" successfully");
	}

	public void verifyPremiumAmount(String calAmount){
		float f=Float.parseFloat(calAmount);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		String calvalue1=df.format(f+1).replaceAll(",","");
		String calvalue2=df.format(f-1).replaceAll(",","");
		waitElement(quotePage.premiumAmt, 3);
		assertThat(quotePage.premiumAmt.isDisplayed(), equalTo(true));
		String totalAmt=quotePage.premiumAmt.getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(totalAmt, anyOf(is(calAmount), is(calvalue1),is(calvalue2)));
		logger.info("verify premium amount "+calAmount+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount "+calAmount+" successfully");
	}

	public String getPremiumAmount(){
		waitElement(quotePage.premiumAnnuallyAmt, 3);
		assertThat(quotePage.premiumAnnuallyAmt.isDisplayed(), equalTo(true));
		String totalAmt=quotePage.premiumAnnuallyAmt.getText();
		String premiumAmt=totalAmt.replaceAll("₹", "").replaceAll(",","").trim();
		logger.info("verify premium amount "+totalAmt+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount "+totalAmt+" successfully");
		return premiumAmt;
	}

	public String getIdvAmount(){
		waitElement(quotePage.idvAmt, 3);
		assertThat(quotePage.idvAmt.isDisplayed(), equalTo(true));
		String idvAmt=quotePage.idvAmt.getText().replaceAll("₹", "").replaceAll(",","").trim();
		logger.info("verify premium amount "+idvAmt+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount "+idvAmt+" successfully");
		return idvAmt;
	}

	public void isTotalPremiumEqualTo(String premium){
		waitElement(paymentPage.premiumTotalPaymentPage, 3);
		assertThat(paymentPage.premiumTotalPaymentPage.isDisplayed(), equalTo(true));
		String totalPremium=paymentPage.premiumTotalPaymentPage.getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(totalPremium, equalTo(premium));
		logger.info("verify premium amount on payment page "+totalPremium+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount on payment page "+totalPremium+" successfully");
	}

	public void isIDVEqualTo(String idvAmt){
		waitElement(paymentPage.idvValue, 3);
		assertThat(paymentPage.idvValue.isDisplayed(), equalTo(true));
		String idvAmount=paymentPage.idvValue.getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(idvAmount, equalTo(idvAmt));
		logger.info("verify IDV amount on payment page "+idvAmount+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify IDV amount on payment page "+idvAmount+" successfully");
	}

	public void selectSalutation(String Salutation){
		waitElement(proposalPage.mrHdfc, 3);
		assertThat(proposalPage.mrHdfc.isDisplayed(), equalTo(true));
		if (Salutation.equalsIgnoreCase("MS")){
			proposalPage.msHdfc.click();
		}else if (Salutation.equalsIgnoreCase("MRS")){
			proposalPage.mrsHdfc.click();
		}else if (Salutation.equalsIgnoreCase("M_S")){
			proposalPage.m_sHdfc.click();
		}else {
			proposalPage.mrHdfc.click();}
		logger.info("salution selected as "+Salutation+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "salution selected "+Salutation+" successfully");
	}

	public void selectTitle(String Title){
		waitElement(proposalPage.mr, 3);
		assertThat(proposalPage.mr.isDisplayed(), equalTo(true));
		if (Title.equalsIgnoreCase("Ms")){
			proposalPage.ms.click();
		}else if (Title.equalsIgnoreCase("Mrs")){
			proposalPage.mrs.click();
		}else if (Title.equalsIgnoreCase("M_S")){
			proposalPage.m_s.click();
		}else if (Title.equalsIgnoreCase("Dr")){
			proposalPage.dr.click();
		}else if (Title.equalsIgnoreCase("Miss")){
			proposalPage.miss.click();
		}else {
			proposalPage.mr.click();}
		logger.info("Title selected as "+Title+ "successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Title selected as "+Title+"successfully");
	}

	public String enterFirstName(String firstName){
		waitElement(proposalPage.fName, 3);
		assertThat(proposalPage.fName.isDisplayed(), equalTo(true));
		proposalPage.fName.sendKeys(firstName);
		logger.info("First Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "First Name enter successfully");
		return firstName;
	}

	public void enterMiddleName(String midName){
		waitElement(proposalPage.mName, 3);
		assertThat(proposalPage.mName.isDisplayed(), equalTo(true));
		proposalPage.mName.sendKeys(midName);
		logger.info("Middle Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Middle Name enter successfully");
	}


	public void enterLastName(String lastName){
		waitElement(proposalPage.lname, 3);
		assertThat(proposalPage.lname.isDisplayed(), equalTo(true));
		proposalPage.lname.sendKeys(lastName);
		logger.info("Last Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Last Name enter successfully");
	}

	public void enterNomineeFirstName(String firstName){
		waitElement(proposalPage.nomineefName, 5);
		assertThat(proposalPage.nomineefName.isDisplayed(), equalTo(true));
		proposalPage.nomineefName.sendKeys(firstName);
		logger.info("First Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "First Name enter successfully");
	}

	public void enterNomineeMiddleName(String midName){
		waitElement(proposalPage.nomineemName, 3);
		assertThat(proposalPage.nomineemName.isDisplayed(), equalTo(true));
		proposalPage.nomineemName.sendKeys(midName);
		logger.info("Middle Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Middle Name enter successfully");
	}


	public void enterNomineeLastName(String lastName){
		waitElement(proposalPage.nomineelname, 3);
		assertThat(proposalPage.nomineelname.isDisplayed(), equalTo(true));
		proposalPage.nomineelname.sendKeys(lastName);
		logger.info("Last Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Last Name enter successfully");
	}

	public void selectGenderUpperCase(String gender){
		waitElement(proposalPage.maleHdfc, 3);
		assertThat(proposalPage.maleHdfc.isDisplayed(), equalTo(true));
		if (gender.equalsIgnoreCase("FEMALE")){
			proposalPage.femaleHdfc.click();
		}else {
			proposalPage.maleHdfc.click();}
		logger.info("gender selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "gender selected successfully");
	}


	public void selectGender(String gender){
		waitElement(proposalPage.male, 3);
		assertThat(proposalPage.male.isDisplayed(), equalTo(true));
		if (gender.equalsIgnoreCase("Female")){
			proposalPage.female.click();
		}else {
			proposalPage.male.click();}
		logger.info("gender selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "gender selected successfully");
	}

	public void enterEmailId(String email){
		waitElement(proposalPage.email, 3);
		assertThat(proposalPage.email.isDisplayed(), equalTo(true));
		proposalPage.email.sendKeys(email);
		logger.info("enter email id successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter email id successfully");
	}

	public void enterPhone(String phone){
		waitElement(proposalPage.phone, 3);
		assertThat(proposalPage.phone.isDisplayed(), equalTo(true));
		proposalPage.phone.sendKeys(phone);
		logger.info("enter phone successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter phone successfully");
	}

	public void enterPermAddress(String address){
		waitElement(proposalPage.address, 3);
		assertThat(proposalPage.address.isDisplayed(), equalTo(true));
		proposalPage.address.sendKeys(address);
		logger.info("enter address successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter address successfully");
	}

	public void enterPincode(String phone){
		waitElement(proposalPage.pincode, 3);
		assertThat(proposalPage.pincode.isDisplayed(), equalTo(true));
		proposalPage.pincode.sendKeys(phone);
		logger.info("enter pincode successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter pincode successfully");
	}

	public void selectAsSame(){
		waitElement(proposalPage.sameAsPermAdd, 3);
		assertThat(proposalPage.sameAsPermAdd.isDisplayed(), equalTo(true));
		proposalPage.sameAsPermAdd.click();
		logger.info("click as Same Address successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click as Same Address successfully");
	}

	public void enterNomineeName(String NomineeName){
		waitElement(proposalPage.nomineeName, 3);
		assertThat(proposalPage.nomineeName.isDisplayed(), equalTo(true));
		proposalPage.nomineeName.sendKeys(NomineeName);
		logger.info("enter NomineeName successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter NomineeName successfully");
	}

	public void enterNomineeAge(String NomineeAge){
		waitElement(proposalPage.nomineeAge, 3);
		assertThat(proposalPage.nomineeAge.isDisplayed(), equalTo(true));
		proposalPage.nomineeAge.sendKeys(NomineeAge);
		logger.info("enter NomineeAge successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter NomineeAge successfully");
	}

	public void openDropDownBox(){
		waitFor(3);
		waitElement(proposalPage.listbox, 3);
		assertThat(proposalPage.listbox.isDisplayed(), equalTo(true));
		proposalPage.listbox.click();
		logger.info("listbox open successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "listbox open successfully");
	}


	public void selectFromDropDownHdfc(String dropDownText){
		String st=dropDownText.toUpperCase();
		clickOnPrevInsurerListbox();
//		openDropDownBox();
		waitFor(1);
		waitElement(driver.findElement(By.xpath("//li[text()='"+st+"']")), 3);
		driver.findElement(By.xpath("//li[text()='"+st+"']")).click();
		//li[@data-value='SIBLING']
		logger.info("select "+st+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+st+" from dropdown successfully");
	}

	public void selectFromDropDown(String dropDownText){
		clickOnPrevInsurerListbox();
		waitFor(1);
		//li[contains(@data-value,'Bharti')]
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");
		waitFor(1);
	}

	public void selectFinanceType(String dropDownText){
		clickOnPrevFinacierType();
		waitFor(1);
		//li[contains(@data-value,'Bharti')]
		waitElement(driver.findElement(By.xpath("//li[contains(@data-value,'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(@data-value,'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");
		waitFor(1);
	}

	public void selectInsurerFromDropDown(String dropDownText,int intance){
		clickOnPrevInsurerUsingInstance(intance);
		waitFor(1);
		//li[contains(@data-value,'Bharti')]
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");
		waitFor(1);
	}

	public void entervehicleNumer(String vehicleNum){
		waitElement(proposalPage.vehicleLicenseNumber, 6);
		assertThat(proposalPage.vehicleLicenseNumber.isDisplayed(), equalTo(true));
		proposalPage.vehicleLicenseNumber.sendKeys(vehicleNum);
		logger.info("enter vehicleNumer successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter vehicleNumer successfully");
	}


	public void enterChassisNumber(String ChassisNumber){
		waitElement(proposalPage.vehicleChassisNumber, 3);
		assertThat(proposalPage.vehicleChassisNumber.isDisplayed(), equalTo(true));
		proposalPage.vehicleChassisNumber.sendKeys(ChassisNumber);
		logger.info("enter ChassisNumber successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter ChassisNumber successfully");
	}


	public void enterEngineNumber(String EngineNumber){
		waitElement(proposalPage.vehicleEngineNumber, 3);
		assertThat(proposalPage.vehicleEngineNumber.isDisplayed(), equalTo(true));
		proposalPage.vehicleEngineNumber.sendKeys(EngineNumber);
		logger.info("enter EngineNumber successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter EngineNumber successfully");
	}

	public void enterPrevPolicyNum(String prevPolicyNum){
		waitElement(proposalPage.previousPolicyNumber, 3);
		assertThat(proposalPage.previousPolicyNumber.isDisplayed(), equalTo(true));
		proposalPage.previousPolicyNumber.sendKeys(prevPolicyNum);
		logger.info("enter previousPolicyNumber successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter previousPolicyNumber successfully");
	}

	public void clickOnReviewNSubmit(){
		waitFor(1);
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		waitFor(2);
		waitElement(proposalPage.reviewNsubmit, 13);
		assertThat(proposalPage.reviewNsubmit.isDisplayed(), equalTo(true));
		proposalPage.reviewNsubmit.click();
		logger.info("clicked on reviewNsubmit successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on reviewNsubmit successfully");
	}


	public void selectMaritalStatus(String maritalStatus){
		waitElement(proposalPage.single, 3);
		assertThat(proposalPage.single.isDisplayed(), equalTo(true));
		if (maritalStatus.equalsIgnoreCase("Married")){
			proposalPage.married.click();
		}else if (maritalStatus.equalsIgnoreCase("Divorcee")){
			proposalPage.divorcee.click();}
		else if (maritalStatus.equalsIgnoreCase("Widow")){
			proposalPage.widow.click(); }
		else if (maritalStatus.equalsIgnoreCase("Widower")){
			proposalPage.widower.click();}
		else {
			proposalPage.single.click();}
		logger.info("marital status selected as "+maritalStatus+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "marital status selected as "+maritalStatus+" successfully");
	}

	public void enterAddressLine1(String addressLine1){
		waitElement(proposalPage.addressLine1, 3);
		assertThat(proposalPage.addressLine1.isDisplayed(), equalTo(true));
		proposalPage.addressLine1.sendKeys(addressLine1);
		logger.info("enter addressLine1 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine1 successfully");
	}

	public void enterAddressLine2(String addressLine2){
		waitElement(proposalPage.addressLine2, 3);
		assertThat(proposalPage.addressLine2.isDisplayed(), equalTo(true));
		proposalPage.addressLine2.sendKeys(addressLine2);
		logger.info("enter addressLine2 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine2 successfully");
	}

	public void enterAddressLine1FL(String addressLine1){
//		Actions act=new Actions(driver);
//		act.sendKeys(driver.findElement(By.xpath("//input[contains(@id,'ddressLine1')]")),addressLine1).build().perform();
		waitElement(proposalPage.previousInsurerAddressLine1, 3);
		assertThat(proposalPage.previousInsurerAddressLine1.isDisplayed(), equalTo(true));
		proposalPage.previousInsurerAddressLine1.sendKeys(addressLine1);
		logger.info("enter addressLine1 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine1 successfully");
	}

	public void enterAddressLine2FL(String addressLine2){
//		Actions act=new Actions(driver);
//		act.sendKeys(driver.findElement(By.xpath("//input[contains(@id,'ddressLine2')]")),addressLine2).build().perform();
		waitElement(proposalPage.previousInsurerAddressLine2, 3);
		assertThat(proposalPage.previousInsurerAddressLine2.isDisplayed(), equalTo(true));
		proposalPage.previousInsurerAddressLine2.sendKeys(addressLine2);
		logger.info("enter addressLine2 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine2 successfully");
	}

	public void enterAddressLine3(String addressLine3){
		waitElement(proposalPage.addressLine3, 3);
		assertThat(proposalPage.addressLine3.isDisplayed(), equalTo(true));
		proposalPage.addressLine3.sendKeys(addressLine3);
		logger.info("enter addressLine3 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine3 successfully");
	}

	public void selectStateUpperCase(String state){
		String dropDownText=state.toUpperCase();
		openDropDown("State");
		waitFor(1);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");

	}

	public void selectCityUpperCase(String city){
		String dropDownText=city.toUpperCase();
		openDropDown("City");
		waitFor(.5);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");

	}

	public void selectDistrictUpperCase(String District){
		String dropDownText=District.toUpperCase();
		openDropDown("District");
		waitFor(.5);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+dropDownText+"')]")).click();
		logger.info("select "+dropDownText+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+dropDownText+" from dropdown successfully");

	}

	public void selectState(String state){
		openDropDown("State");
		waitFor(.5);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+state+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+state+"')]")).click();
		logger.info("select "+state+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+state+" from dropdown successfully");
		waitFor(.5);
	}

	public void selectCity(String city){
		openDropDown("City");
		waitFor(1);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+city+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+city+"')]")).click();
		logger.info("select "+city+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+city+" from dropdown successfully");
		waitFor(.5);
	}

	public void selectDistrict(String District){
		openDropDown("District");
		waitFor(.7);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'"+District+"')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'"+District+"')]")).click();
		logger.info("select "+District+" from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select "+District+" from dropdown successfully");
		waitFor(.5);
	}

	public void selectCountry(){
		openDropDown("Country");
		waitFor(.5);
		waitElement(driver.findElement(By.xpath("//li[contains(text(),'India')]")), 3);
		driver.findElement(By.xpath("//li[contains(text(),'India')]")).click();
		logger.info("select as India from dropdown successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select as India from dropdown successfully");
		waitFor(.5);
	}

	public void selectSameAsCommAdd(){
		waitElement(proposalPage.sameAsCommunication, 3);
		assertThat(proposalPage.sameAsCommunication.isDisplayed(), equalTo(true));
		proposalPage.sameAsCommunication.click();
		logger.info("Same As Communication Address selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Same As Communication Address selected successfully");
	}

	public void selectSameAsCommAddForRegnAdd(){
		waitElement(proposalPage.sameAsCommRegAdd, 3);
		assertThat(proposalPage.sameAsCommRegAdd.isDisplayed(), equalTo(true));
		proposalPage.sameAsCommRegAdd.click();
		logger.info("Same As Communication Address selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Same As Communication Address selected successfully");
	}

	public void EnterAddressForReliance(String Addl1,String Addl2,String Addl3,String pincode,String State,String City,String Distric){
		enterAddressLine1(Addl1);
		enterAddressLine2(Addl2);
		enterAddressLine3(Addl3);
		enterPincode(pincode);
		selectStateUpperCase(State);
		selectCityUpperCase(City);
		selectDistrictUpperCase(Distric);
		selectCountry();
		scrollUpDown(0,150);
		waitFor(1);
		selectSameAsCommAdd();
		scrollToButtom();
		waitFor(1);
		selectSameAsCommAddForRegnAdd();
		logger.info("Enter Address details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Enter Address details successfully");
	}

	public void EnterAddressForFuture(String Addl1,String Addl2,String Addl3,String pincode,String State,String City){
		enterAddressLine1(Addl1);
		enterAddressLine2(Addl2);
		enterAddressLine3(Addl3);
		enterPincode(pincode);
		selectState(State);
		selectCity(City);
		selectCountry();
		scrollToButtom();
		waitFor(1);
		selectAsSame();
		logger.info("Enter Address details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Enter Address details successfully");
	}

	public void selcectManufactureDate(String year){
		selectPrevMonthDate(year);
		logger.info("select manufacturing date successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select manufacturing date successfully");
	}

	public void selcectPrevPolExpDateBreakin(String year){
		selectPrevMonthDate(year);
		logger.info("select manufacturing date successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select manufacturing date successfully");
	}

	public void isVehicleHypothecated(String yesNo){
		waitElement(proposalPage.yes2, 3);
		assertThat(proposalPage.yes2.isDisplayed(), equalTo(true));
		if (yesNo.equalsIgnoreCase("Yes")){
			proposalPage.yes2.click();
		}else {
			proposalPage.no2.click();
		}
		logger.info("selected as "+yesNo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected as "+yesNo+" successfully");
		waitFor(1);
		scrollUpDown(0,200);
	}


	public void selectYesNo(String yesNo){
		waitElement(proposalPage.yes2Tata, 3);
		assertThat(proposalPage.yes2Tata.isDisplayed(), equalTo(true));
		if (yesNo.equalsIgnoreCase("Yes")){
			proposalPage.yes2Tata.click();
		}else {
			proposalPage.no2Tata.click();
		}
		logger.info("selected as "+yesNo+"successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected as "+yesNo+"successfully");
	}

	public void clockOnAgree(){
		waitElement(proposalPage.agree, 3);
		assertThat(proposalPage.agree.isDisplayed(), equalTo(true));
		proposalPage.agree.click();
		logger.info("Same As Communication Address selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Same As Communication Address selected successfully");
	}

	public void enterFinancerNameTata(String name){
		waitFor(3);
		waitElement(proposalPage.financerName, 20);
		assertThat(proposalPage.financerName.isDisplayed(), equalTo(true));
		proposalPage.financerName.click();
		proposalPage.financerName.sendKeys(name);
		waitFor(1);
		proposalPage.financerName.sendKeys(Keys.DOWN,Keys.ENTER);
		logger.info("financerName selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "financerName selected successfully");
	}

	public void enterFinancerAddressTata(String address){
		waitFor(1);
		waitElement(proposalPage.financierAddress, 3);
		assertThat(proposalPage.financierAddress.isDisplayed(), equalTo(true));
		proposalPage.financierAddress.sendKeys(address);
		logger.info("financer Address entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "financer Address entered successfully");
	}

	public void selectComprehensiveForProposal(){
		waitElement(proposalPage.comprehensive, 3);
		assertThat(proposalPage.comprehensive.isDisplayed(), equalTo(true));
		proposalPage.comprehensive.click();
		logger.info("comprehensive selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "comprehensive selected successfully");
	}

	public void selectThirdpartyForProposal(){
		waitElement(proposalPage.thirdparty, 3);
		assertThat(proposalPage.thirdparty.isDisplayed(), equalTo(true));
		proposalPage.thirdparty.click();
		logger.info("thirdparty selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "thirdparty selected successfully");
	}

	public void isPaymentPageDisplayed(){
		waitingForPaymentPage();
		waitElement(paymentPage.cpPolTypePaymentPage, 70);
		assertThat(paymentPage.companyLogo.isDisplayed(), equalTo(true));
		assertThat(paymentPage.backHome.isDisplayed(), equalTo(true));
		assertThat(paymentPage.makePayment.isDisplayed(), equalTo(true));
		assertThat(paymentPage.copyPaymentLink.isDisplayed(), equalTo(true));
		assertThat(paymentPage.proposalCreated.isDisplayed(), equalTo(true));
		logger.info("PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentPage verified successfully");
	}

	public void isBreakingPaymentPageDisplayed(){
		waitingForPaymentPage();
		waitElement(paymentPage.cpPolTypePaymentPage, 70);
		assertThat(paymentPage.companyLogo.isDisplayed(), equalTo(true));
		assertThat(paymentPage.makePayment.isDisplayed(), equalTo(true));
		logger.info("breaking inspection PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "breaking inspection PaymentPage verified successfully");
	}

	public void clickOnMakePayment(){
		waitElement(paymentPage.makePayment, 20);
		assertThat(paymentPage.makePayment.isDisplayed(), equalTo(true));
		paymentPage.makePayment.click();
		logger.info("clicked on makePayment successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on makePayment successfully");
	}

	public void isDigitPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.mockPay, 30);
		assertThat(paymentPage.mockPay.isDisplayed(), equalTo(true));
		assertThat(paymentPage.wallet.isDisplayed(), equalTo(true));
		assertThat(paymentPage.netbanking.isDisplayed(), equalTo(true));
		logger.info("Digit PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit PaymentPage verified successfully");
	}

	public void isTataAigPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.tataLogo, 30);
		assertThat(paymentPage.tataLogo.isDisplayed(), equalTo(true));
		assertThat(paymentPage.payAmtTata.isDisplayed(), equalTo(true));
		assertThat(paymentPage.cardTata.isDisplayed(), equalTo(true));
		logger.info("TATA Aig PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "TATA Aig PaymentPage verified successfully");
	}


	public void isBajajPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.bajajLogo, 30);
		assertThat(paymentPage.bajajLogo.isDisplayed(), equalTo(true));
		assertThat(paymentPage.creditDebitBajaj.isDisplayed(), equalTo(true));
		assertThat(paymentPage.payAmtBajaj.isDisplayed(), equalTo(true));
		assertThat(paymentPage.netBankingBajaj.isDisplayed(), equalTo(true));
		logger.info("Bajaj PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Bajaj PaymentPage verified successfully");
	}

	public void isICICIPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.logoIl, 30);
		assertThat(paymentPage.email.isDisplayed(), equalTo(true));
		assertThat(paymentPage.phoneIl.isDisplayed(), equalTo(true));
		assertThat(paymentPage.proceedIl.isDisplayed(), equalTo(true));
		logger.info("ICICI L PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "ICICI L PaymentPage verified successfully");
	}

	public void isReliancePaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.creditCardRel, 30);
		assertThat(paymentPage.relianceLogo.isDisplayed(), equalTo(true));
		assertThat(paymentPage.creditCardRel.isDisplayed(), equalTo(true));
		assertThat(paymentPage.debitCardsRel.isDisplayed(), equalTo(true));
		assertThat(paymentPage.netBankingRel.isDisplayed(), equalTo(true));
		logger.info("Reliance PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Reliance PaymentPage verified successfully");
	}

	public void clickOnMockPayment(){
		waitFor(2);
		waitElement(paymentPage.mockPay, 3);
		assertThat(paymentPage.mockPay.isDisplayed(), equalTo(true));
		paymentPage.mockPay.click();
		logger.info("clicked on mock payment successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on mock payment successfully");
	}

	public void isPaymentSuccessPageDisplayed(){
		waitElement(paymentPage.paymentSuccess, 70);
		assertThat(paymentPage.paymentSuccess.isDisplayed(), equalTo(true));
		waitFor(2);
		driver.navigate().refresh();
		logger.info("PaymentSuccessPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentSuccessPage verified successfully");
	}

	public void isPaymentSuccessPageDisplayedForFL(){
		waitElement(paymentPage.paymentSuccess, 70);
		assertThat(paymentPage.paymentSuccess.isDisplayed(), equalTo(true));
		waitFor(2);
		driver.navigate().refresh();
		logger.info("PaymentSuccessPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentSuccessPage verified successfully");
	}

	public void isPolicyDownloadPageDisplayed(){
		waitElement(policyDownloadPage.backHome, 70);
		assertThat(policyDownloadPage.successPage.isDisplayed(), equalTo(true));
		assertThat(policyDownloadPage.downloadPolicy.isDisplayed(), equalTo(true));
		logger.info("DownloadPageDisplayed verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "DownloadPageDisplayed successfully");
	}

	public void clickOnDownloadPolicy(){
		waitElement(policyDownloadPage.downloadPolicy, 30);
		assertThat(policyDownloadPage.downloadPolicy.isDisplayed(), equalTo(true));
		waitElementForClickable(policyDownloadPage.downloadPolicy,30);
		waitFor(5);
		refreshPage();
		waitFor(5);
		enterTextAction("Download policy",1);
		waitFor(2);
		waitingForDownloadPlicy();
		logger.info("clicked on downloadPolicy successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on downloadPolicy successfully");
	}

	public void clickOnOk(){
		waitElement(paymentPage.ok, 30);
		assertThat(paymentPage.ok.isDisplayed(), equalTo(true));
		paymentPage.ok.click();
		logger.info("ok clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "OK clicked successfully");
	}

	public void clickOnAddons(){
		waitElement(quotePage.addOns, 30);
		assertThat(quotePage.addOns.isDisplayed(), equalTo(true));
		quotePage.addOns.click();
		logger.info("click on addOns successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click on addOns successfully");
	}

	public void clickOnApply(){
		waitElement(quotePage.apply,3);
		assertThat(quotePage.apply.isDisplayed(), equalTo(true));
		quotePage.apply.click();
		logger.info("click on apply successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click on apply successfully");
	}

	public void prevPolStartDate(String year) {
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String formatted = df.format(new Date());
		int currentDate=Integer.parseInt(formatted);
		waitFor(1);
		clickOnChooseDate2(6);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		if(currentDate>=15){
			nextMonthAction();}
		waitFor(1);
		enterTextAction("17",1);
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}

	public void prevPolStartDateBreakIn(String year) {
		waitFor(1);
		clickOnChooseDate2(6);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		Actions act=new Actions(driver);
		act.moveToElement(driver.findElement(By.xpath("//button[@aria-label='previous month']"))).click().perform();
		waitFor(1);
		enterTextAction("17",1);
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}

	public void prevTpStartDate(String year,int instance) {
		SimpleDateFormat df = new SimpleDateFormat("dd");
		String formatted = df.format(new Date());
		int currentDate=Integer.parseInt(formatted);
		waitFor(1);
		clickOnChooseDate2(instance);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		if(currentDate>=15){
		nextMonthAction();}
		waitFor(1);
		enterTextAction("17",1);
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}

	public void prevTpExpiryDate(String year) {
		waitFor(1);
		clickOnChooseDate2(8);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+year+"']")).click();
		waitFor(1);
		nextMonthAction();
		waitFor(1);
		enterTextAction("17",1);
		logger.info("Date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Date selected successfully");
	}

	public void openPrevTpInsurer(){
		waitElement(proposalPage.listbox3, 30);
		assertThat(proposalPage.listbox3.isDisplayed(), equalTo(true));
		proposalPage.listbox3.click();
		logger.info("openPrevTpInsurer successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "openPrevTpInsurer successfully");
	}

	public String time(){
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ddHH_mm");
		String formattedDate = myDateObj.format(myFormatObj);
		return formattedDate;
	}


	public void enterPrevTpPolicyNumber(String tpPolNum){
		waitElement(proposalPage.tpPolicyNumber, 30);
		assertThat(proposalPage.tpPolicyNumber.isDisplayed(), equalTo(true));
		proposalPage.tpPolicyNumber.sendKeys(tpPolNum);
		logger.info("tpPolicyNumber entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "tpPolicyNumber entered successfully");
	}


	public void enterHdfcPaymentDetails(String cardNumber,String cvv){
		waitElement(paymentPage.sendAnyway,30);
		assertThat(paymentPage.sendAnyway.isDisplayed(), equalTo(true));
		paymentPage.sendAnyway.click();
		waitElement(paymentPage.amt,30);
		assertThat(paymentPage.amt.isDisplayed(), equalTo(true));
		assertThat(paymentPage.creditDebitHdfc.isDisplayed(), equalTo(true));
		paymentPage.creditDebitHdfc.click();
		assertThat(paymentPage.payHdfc.isDisplayed(), equalTo(true));
		paymentPage.payHdfc.click();
		waitElement(paymentPage.paymentGatewayTest,30);
		assertThat(paymentPage.paymentGatewayTest.isDisplayed(), equalTo(true));
		assertThat(paymentPage.submit.isDisplayed(), equalTo(true));
		paymentPage.submit.click();
		waitFor(2);
		waitElement(paymentPage.txtCardNo,30);
		assertThat(paymentPage.txtCardNo.isDisplayed(), equalTo(true));
		paymentPage.txtCardNo.sendKeys(cardNumber);
		assertThat(paymentPage.cvvHdfc.isDisplayed(), equalTo(true));
		paymentPage.cvvHdfc.sendKeys(cvv);
		waitFor(1);
		actionOnSubmit();
		waitFor(2);
		waitElement(paymentPage.paymentSuccess,70);
		assertThat(paymentPage.paymentSuccess.isDisplayed(), equalTo(true));
		logger.info("HDFC Payment Details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "HDFC Payment Details entered successfully");
	}

	public void proceedReliancePayment(){
		waitElement(paymentPage.netBankingRel,30);
		assertThat(paymentPage.netBankingRel.isDisplayed(), equalTo(true));
		paymentPage.netBankingRel.click();
		waitElement(paymentPage.selecteBankRel,10);
		assertThat(paymentPage.selecteBankRel.isDisplayed(), equalTo(true));
		Select bank=new Select(paymentPage.selecteBankRel);
		bank.selectByVisibleText("AvenuesTest");
		waitElement(paymentPage.makePaymentRel,30);
		assertThat(paymentPage.makePaymentRel.isDisplayed(), equalTo(true));
		paymentPage.makePaymentRel.click();
		//enterTextAction("Make Payment",2);
		waitElement(paymentPage.returnToMerchantSite,8);
		assertThat(paymentPage.returnToMerchantSite.isDisplayed(), equalTo(true));
		paymentPage.returnToMerchantSite.click();
		waitFor(3);
		waitElement(paymentPage.sendAnyway,10);
		assertThat(paymentPage.sendAnyway.isDisplayed(), equalTo(true));
		paymentPage.sendAnyway.click();
		waitFor(2);
		logger.info("Reliance Payment has been completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Reliance Payment has been completed successfully");
	}

	public void proceedBajajPayment(){
		waitElement(paymentPage.netBankingBajaj,30);
		assertThat(paymentPage.netBankingBajaj.isDisplayed(), equalTo(true));
		paymentPage.netBankingBajaj.click();
		waitElement(paymentPage.bajajPayByHDFC,10);
		assertThat(paymentPage.bajajPayByHDFC.isDisplayed(), equalTo(true));
		paymentPage.bajajPayByHDFC.click();
		waitElement(paymentPage.makePaymentBajaj,3);
		assertThat(paymentPage.makePaymentBajaj.isDisplayed(), equalTo(true));
		paymentPage.makePaymentBajaj.click();
		waitFor(9);
		enterTextAction("Make Payment",1);
		waitElement(paymentPage.returnToMerchantSite,8);
		assertThat(paymentPage.returnToMerchantSite.isDisplayed(), equalTo(true));
		paymentPage.returnToMerchantSite.click();
		waitFor(3);
		waitElement(paymentPage.sendAnyway,10);
		assertThat(paymentPage.sendAnyway.isDisplayed(), equalTo(true));
		paymentPage.sendAnyway.click();
		logger.info("BAJAJ Payment has been completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "BAJAJ Payment has been completed successfully");
	}

	public void actionOnSubmit() {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				//button[@type='submit']
				WebElement e = driver.findElement(By.xpath("//input[@value='Submit']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).doubleClick().perform();
				return true;
			}
		});
	}

	public void policyDownloadMessageHdfc(){
		waitElement(policyDownloadPage.insurerDownloadMessage, 70);
		assertThat(policyDownloadPage.insurerDownloadMessage.isDisplayed(),equalTo(true));
		assertThat(policyDownloadPage.insurerDownloadMessage.getText(),equalTo("Insurance Co. did not provide the policy PDF. It usually takes 1 hour. Please check after 1 hour in Earnings section."));
		logger.info("Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessage.getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessage.getText());
	}

	public boolean isPolicyDownloadMessageDispalyed(){
		try {
			waitElement(policyDownloadPage.insurerDownloadMessage, 3);
			assertThat(policyDownloadPage.insurerDownloadMessage.isDisplayed(),equalTo(true));
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public boolean isPolicyDownloadMessageDispalyedReliance(){
		try {
			waitElement(policyDownloadPage.insurerDownloadMessageRel, 70);
			assertThat(policyDownloadPage.insurerDownloadMessageRel.isDisplayed(),equalTo(true));
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public void enterPrevTpAddress(String tpAddress){
		waitElement(proposalPage.prevTpAddress, 30);
		assertThat(proposalPage.prevTpAddress.isDisplayed(), equalTo(true));
		proposalPage.prevTpAddress.sendKeys(tpAddress);
		logger.info("tpAddress entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "tpAddress entered successfully");
	}

	public void enterPrevPolicyClaim(String claim){
		waitElement(proposalPage.pervClaims, 30);
		assertThat(proposalPage.pervClaims.isDisplayed(), equalTo(true));
		proposalPage.pervClaims.sendKeys(claim);
		logger.info("pervClaims entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "pervClaims entered successfully");
	}

	public void enterPrevTpTenure(String tenure){
		waitElement(proposalPage.prevTenure, 30);
		assertThat(proposalPage.prevTenure.isDisplayed(), equalTo(true));
		proposalPage.prevTenure.sendKeys(tenure);
		logger.info("pervClaims entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "pervClaims entered successfully");
	}

	public void browserProceedForNotSecureLink(){
		waitElement(paymentPage.browserAdvanced, 30);
		assertThat(paymentPage.browserAdvanced.isDisplayed(), equalTo(true));
		paymentPage.browserAdvanced.click();
		waitElement(paymentPage.browserProceed, 30);
		assertThat(paymentPage.browserProceed.isDisplayed(), equalTo(true));
		paymentPage.browserProceed.click();
		logger.info("click on advanced and proceed successfully for not secure link");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click on advanced and proceed successfully for not secure link");
	}


	public void proceedWithEmailNPhone(String Phone,String email){
		waitFor(1);
		waitElement(paymentPage.phoneIl, 30);
		assertThat(paymentPage.phoneIl.isDisplayed(), equalTo(true));
		paymentPage.phoneIl.sendKeys(Phone);
		assertThat(paymentPage.email.isDisplayed(), equalTo(true));
		paymentPage.email.sendKeys(email);
		assertThat(paymentPage.proceedIl.isDisplayed(), equalTo(true));
		paymentPage.proceedIl.click();
		logger.info("email and phone number entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "email and phone number entered successfully");
	}


	public void enterIciciPaymentDetailsAndProceed(String Card,String cvv,String MMYY,String nameOnCard){
		waitFor(1);
		proceedWithEmailNPhone("6367357113","ensuredit@gmail.com");
		waitFor(1);
		waitElement(paymentPage.cardIl, 30);
		assertThat(paymentPage.cardIl.isDisplayed(), equalTo(true));
		paymentPage.cardIl.click();
		waitFor(1);
		waitElement(paymentPage.card_numberIl, 30);
		assertThat(paymentPage.card_numberIl.isDisplayed(), equalTo(true));
		paymentPage.card_numberIl.sendKeys(Card);
		assertThat(paymentPage.card_expiryIl.isDisplayed(), equalTo(true));
		paymentPage.card_expiryIl.sendKeys(MMYY);
		assertThat(paymentPage.card_nameIl.isDisplayed(), equalTo(true));
		paymentPage.card_nameIl.sendKeys(nameOnCard);
		assertThat(paymentPage.card_cvvIl.isDisplayed(), equalTo(true));
		paymentPage.card_cvvIl.sendKeys(cvv);
		waitElement(paymentPage.proceedIl, 5);
		assertThat(paymentPage.proceedIl.isDisplayed(), equalTo(true));
		paymentPage.proceedIl.click();
		waitFor(2);
		String parentWindow = driver.getWindowHandle();
		switchToChildWindow();
		waitElement(paymentPage.successIL, 5);
		assertThat(paymentPage.successIL.isDisplayed(), equalTo(true));
		paymentPage.successIL.click();
		waitFor(9);
		switchToTab(parentWindow);
		logger.info("ICICI payment Details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "ICICI payment Details entered successfully");
		logger.info("ICICI payment completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "ICICI payment completed successfully");
	}

	public void enterDigitPaymentDetailsAndProceed(String cardNum,String cvv,String mm,String yy,String nameOnCard){
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("card_number_iframe")));
		waitFor(1);
//		waitElement(paymentPage.cardNumDigit, 10);
//		assertThat(paymentPage.cardNumDigit.isDisplayed(), equalTo(true));
		paymentPage.cardNumDigit.sendKeys(cardNum);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("name_on_card_iframe")));
		waitFor(1);
		waitElement(paymentPage.nameOnCardDigit, 7);
		assertThat(paymentPage.nameOnCardDigit.isDisplayed(), equalTo(true));
		paymentPage.nameOnCardDigit.sendKeys(nameOnCard);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("card_exp_month_iframe")));
		waitFor(1);
		waitElement(paymentPage.expMonthDigit, 8);
		assertThat(paymentPage.expMonthDigit.isDisplayed(), equalTo(true));
		paymentPage.expMonthDigit.sendKeys(mm);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("card_exp_year_iframe")));
		waitFor(1);
		waitElement(paymentPage.expYearDigit, 8);
		assertThat(paymentPage.expYearDigit.isDisplayed(), equalTo(true));
		paymentPage.expYearDigit.sendKeys(yy);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("security_code_iframe")));
		waitFor(1);
		waitElement(paymentPage.cvvDigit, 7);
		assertThat(paymentPage.cvvDigit.isDisplayed(), equalTo(true));
		paymentPage.cvvDigit.sendKeys(cvv);
		driver.switchTo().defaultContent();
		waitFor(1);
		waitElement(paymentPage.proceedFuture, 8);
		assertThat(paymentPage.proceedFuture.isDisplayed(), equalTo(true));
		waitFor(1);
		paymentPage.proceedFuture.click();
		waitElement(paymentPage.otpFuture, 8);
		assertThat(paymentPage.otpFuture.isDisplayed(), equalTo(true));
		paymentPage.otpFuture.sendKeys("123456");
		waitElement(paymentPage.payFuture, 8);
		assertThat(paymentPage.payFuture.isDisplayed(), equalTo(true));
		paymentPage.payFuture.click();
		logger.info("Digit payment Details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit payment Details entered successfully");
		logger.info("Digit payment completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit payment completed successfully");

	}

	public void policyDownloadMessage(){
		waitElement(policyDownloadPage.insurerDownloadMessage, 10);
		assertThat(policyDownloadPage.insurerDownloadMessage.isDisplayed(),equalTo(true));
		if(policyDownloadPage.insurerDownloadMessage.getText().contains("hour")){
			assertThat(policyDownloadPage.insurerDownloadMessage.getText(),equalTo("Insurance Co. did not provide the policy PDF. It usually takes 1 hour. Please check after 1 hour in Earnings section."));
		}
		else if (policyDownloadPage.insurerDownloadMessage.getText().contains("support")){
		assertThat(policyDownloadPage.insurerDownloadMessage.getText(),equalTo("Insurance Company did not provide the Policy PDF. Please contact support to get the Policy PDF."));
		}else {
			assertThat(policyDownloadPage.insurerDownloadMessageRel.getText(),equalTo("Policy download already in process"));
		}
		logger.info("Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessage.getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessage.getText());
	}

	public void policyDownloadMessageReliance(){
		waitElement(policyDownloadPage.insurerDownloadMessageRel, 70);
		assertThat(policyDownloadPage.insurerDownloadMessageRel.isDisplayed(),equalTo(true));
		assertThat(policyDownloadPage.insurerDownloadMessageRel.getText(),equalTo("Policy download already in process"));
		logger.info("Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessageRel.getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.insurerDownloadMessageRel.getText());
	}


	public void enterAppointeeName(String aName){
		waitElement(proposalPage.appointeeName, 70);
		assertThat(proposalPage.appointeeName.isDisplayed(),equalTo(true));
		proposalPage.appointeeName.sendKeys(aName);
		logger.info("Appointee Name entered successfuly");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Appointee Name entered successfuly");
	}

	public void enterePreviousInsurerPincode(String phone){
		waitElement(proposalPage.previousInsurerPincode, 3);
		assertThat(proposalPage.previousInsurerPincode.isDisplayed(), equalTo(true));
		proposalPage.previousInsurerPincode.sendKeys(phone);
		logger.info("enter previousInsurerPincode successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter previousInsurerPincode successfully");
	}

	public void clickOnSendAnyway(){
		waitElement(paymentPage.sendAnyway,30);
		assertThat(paymentPage.sendAnyway.isDisplayed(), equalTo(true));
		paymentPage.sendAnyway.click();
	}

	public void isFutureGeneralPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.logoFuture, 30);
		assertThat(paymentPage.logoFuture.isDisplayed(), equalTo(true));
		assertThat(paymentPage.cardsFuture.isDisplayed(), equalTo(true));
		assertThat(paymentPage.choosePaymentOption.isDisplayed(), equalTo(true));
		logger.info("FutureGeneral PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "FutureGeneral PaymentPage verified successfully");
	}

	public void enterFuturePaymentDetailsAndProceed(String cardNum,String expMMYY,String cvv,String cardHoldername){
		paymentPage.cardsFuture.click();
		waitElement(paymentPage.cardNumberFuture,12);
		assertThat(paymentPage.cardNumberFuture.isDisplayed(), equalTo(true));
		paymentPage.cardNumberFuture.sendKeys(cardNum);
		waitElement(paymentPage.cardExpiryFuture,3);
		assertThat(paymentPage.cardExpiryFuture.isDisplayed(), equalTo(true));
		paymentPage.cardExpiryFuture.sendKeys(expMMYY);
		waitElement(paymentPage.cardCvvFuture,3);
		assertThat(paymentPage.cardCvvFuture.isDisplayed(), equalTo(true));
		paymentPage.cardCvvFuture.sendKeys(cvv);
		waitElement(paymentPage.cardOwnerNameFuture,3);
		assertThat(paymentPage.cardOwnerNameFuture.isDisplayed(), equalTo(true));
		paymentPage.cardOwnerNameFuture.sendKeys(cardHoldername);
		waitElement(paymentPage.proceedFuture,3);
		assertThat(paymentPage.proceedFuture.isDisplayed(), equalTo(true));
		paymentPage.proceedFuture.click();
		waitFor(1);
		waitElement(paymentPage.otpFuture,13);
		assertThat(paymentPage.otpFuture.isDisplayed(), equalTo(true));
		paymentPage.otpFuture.sendKeys("123456");
		waitFor(1);
		waitElement(paymentPage.payFuture,3);
		assertThat(paymentPage.payFuture.isDisplayed(), equalTo(true));
		paymentPage.payFuture.click();
		logger.info("FutureGeneral Payment details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "FutureGeneral Payment details entered successfully");
		waitFor(5);
	}

	public void selectAddOns(String  addonsText){
		enterTextAction(addonsText,1);
		logger.info("Select addons as "+addonsText+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Select addons as "+addonsText+" successfully");
	}

	public void isInsurerDisplayedOnPlanpage(){
		for(int i=0;i<40;i++){
			try {
				if (quotePage.addonsText.isDisplayed()){
					waitFor(3);
				}
				else {
					break;
				}
			}catch (Exception e){}

		}
	}

	public void waitingForPaymentPage(){
		for(int i=0;i<40;i++){
			try {
				if (proposalPage.reviewNsubmit.isDisplayed()){
					waitFor(3);
				}
				else {
					logger.info(driver.getCurrentUrl());
					ExtentTestManager.getTest().log(LogStatus.PASS, driver.getCurrentUrl());
					break;
				}
			}catch (Exception e){}

		}
	}

	public void scrollToText(String text){
		waitFor(1);
		WebElement element = driver.findElement(By.xpath("//*[contains(text(),'"+text+"')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		waitFor(2);
	}

	public void scrollToInsurer(String text){
		waitFor(1);
		WebElement element = driver.findElement(By.xpath("//*[contains(@data-locator,'"+text+"')]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
		waitFor(2);
	}

	public void inputValidation(String st){
	System.out.println(st);
	}































	public void readPdfContent(String pdfuri) throws IOException {
		ExtentTestManager.getTest().log(LogStatus.PASS, "pdfUrl "+pdfuri);
		URL pdfUrl = new URL(pdfuri);
		InputStream in = pdfUrl.openStream();
		BufferedInputStream fileparse = new BufferedInputStream(in);
		PDDocument doc=null;
		doc = PDDocument.load(fileparse);
		String content = new PDFTextStripper().getText(doc);
		logger.info(content);
		ExtentTestManager.getTest().log(LogStatus.PASS, content);
		doc.close();
	}

	public boolean isPleaseWaitDispaly(){
		try {
			waitElement(policyDownloadPage.pleaseWait, 3);
			assertThat(policyDownloadPage.pleaseWait.isDisplayed(),equalTo(true));
			return true;
		}catch (Exception e){
			return false;
		}
	}


	public void waitingForDownloadPlicy(){
		for(int i=0;i<30;i++){
				if (isPleaseWaitDispaly()){
					waitFor(3);
				}
				else {
					break;
				}

		}
	}

}
