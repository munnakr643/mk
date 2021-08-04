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
import java.time.Month;
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
		waitElement(loginPage.getUserId(), 3);
		assertThat(loginPage.getUserId().isDisplayed(), equalTo(true));
		loginPage.getUserId().sendKeys(readconfig.getUsername());
		logger.info("userID " + readconfig.getUsername() + " Enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "userID " + readconfig.getUsername() + " Enter successfully");
	}

	public void clickOnProceedBtn() {
		waitElement(loginPage.getProceed(), 3);
		assertThat(loginPage.getProceed().isDisplayed(), equalTo(true));
		loginPage.getProceed().click();
		logger.info("proceed btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "proceed btn clicked successfully");
	}

	public void actionOnProceed(){
		enterTextAction("Proceed",1);
		logger.info("proceed btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "proceed btn clicked successfully");
	}
	

	public void enterOtp()  {
		waitElement(loginPage.getOtpbox1(), 20);
		assertThat(loginPage.getOtpbox1().isDisplayed(), equalTo(true));
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
		waitElement(homePage.getBrokerLogo(), 30);
		assertThat(homePage.getBrokerLogo().isDisplayed(), equalTo(true));
		assertThat(homePage.getLeads().isDisplayed(), equalTo(true));
		assertThat(homePage.getProfile().isDisplayed(), equalTo(true));
		assertThat(homePage.getGenerateQuote().isDisplayed(), equalTo(true));
		logger.info("Homepage screen is appearing");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Homepage screen is appearing");
		waitFor(1);
	}

	public void selectTwoWheeler() {
		waitElement(quotePage.getTwoWheeler(), 3);
		assertThat(quotePage.getTwoWheeler().isDisplayed(), equalTo(true));
		quotePage.getTwoWheeler().click();
		logger.info("select two wheeler successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select two wheeler successfully");
	}

	public void selectFourWheeler() {
		waitElement(quotePage.getCarInsurance(), 3);
		assertThat(quotePage.getCarInsurance().isDisplayed(), equalTo(true));
		quotePage.getCarInsurance().click();
		logger.info("select Four wheeler successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select Four wheeler successfully");
	}

	public void enterCustomerName(String custrName) {
		waitElement(quotePage.getCustomerName(), 3);
		assertThat(quotePage.getCustomerName().isDisplayed(), equalTo(true));
		quotePage.getCustomerName().sendKeys(custrName);
		logger.info("customerName "+custrName+" entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "customerName "+custrName+" entered successfully");
	}

		public void clickOnNextBtn() {
		waitFor(1);
			waitElement(quotePage.getNextbtn(), 3);
			assertThat(quotePage.getNextbtn().isDisplayed(), equalTo(true));
			quotePage.getNextbtn().click();
			logger.info("Next Btn btn clicked successfully");
			ExtentTestManager.getTest().log(LogStatus.PASS, "Next Btn btn clicked successfully");
		}

	public void clickOnNextButton() {
		waitFor(1);
		waitElement(quotePage.getNextbutton(), 3);
		assertThat(quotePage.getNextbutton().isDisplayed(), equalTo(true));
		quotePage.getNextbutton().click();
		logger.info("Next Btn btn clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Next Btn btn clicked successfully");
	}

	public void enterMMVDetails(String make,String model,String variant) {
		waitElement(quotePage.getSearchButton(), 3);
		assertThat(quotePage.getSearchButton().isDisplayed(), equalTo(true));
		quotePage.getSearchButton().click();
		waitElement(quotePage.getInputBar(), 3);
		assertThat(quotePage.getInputBar().isDisplayed(), equalTo(true));
		quotePage.getInputBar().sendKeys(make);
		waitElement(quotePage.getFirstRow(), 3);
		assertThat(quotePage.getFirstRow().isDisplayed(), equalTo(true));
		if (make.equalsIgnoreCase(quotePage.getFirstRow().getText())){
			quotePage.getFirstRow().click();
		}else {
			quotePage.getSecondRow().click();
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
		waitElement(quotePage.getModelRow1(), 3);
		assertThat(quotePage.getModelRow1().isDisplayed(), equalTo(true));
		if (model.equalsIgnoreCase(quotePage.getModelRow1().getText())){
			quotePage.getModelRow1().click();
		}else {
			quotePage.getModelRow2().click();
		}
		logger.info("model selected as "+model+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "model selected as "+model+" successfully");
		waitElement(quotePage.getPetrol(), 3);
		assertThat(quotePage.getPetrol().isDisplayed(), equalTo(true));
		quotePage.getPetrol().click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitFor(1);
		driver.findElement(By.xpath("//div[contains(text(),'"+variant+"')]")).click();
		logger.info("variant selected as "+variant+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "variant selected as "+variant+" successfully");
	}

	public void selectMMV() {
		waitElement(quotePage.getHonda(), 3);
		assertThat(quotePage.getHonda().isDisplayed(), equalTo(true));
		quotePage.getHonda().click();
		logger.info("select honda successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select honda successfully");
		waitElement(quotePage.getActiva(), 3);
		assertThat(quotePage.getActiva().isDisplayed(), equalTo(true));
		quotePage.getActiva().click();
		logger.info("select activa successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select activa successfully");
		waitElement(quotePage.getPetrol(), 3);
		assertThat(quotePage.getPetrol().isDisplayed(), equalTo(true));
		quotePage.getPetrol().click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitElement(quotePage.get_3G109cc(), 3);
		assertThat(quotePage.get_3G109cc().isDisplayed(), equalTo(true));
		quotePage.get_3G109cc().click();
		logger.info("select 3G109cc successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select 3G109cc successfully");
	}

	public void select4wMMV() {
		waitElement(quotePage.getHonda4w(), 3);
		assertThat(quotePage.getHonda4w().isDisplayed(), equalTo(true));
		quotePage.getHonda4w().click();
		logger.info("select honda successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select honda successfully");
		waitElement(quotePage.getHondaCity(), 3);
		assertThat(quotePage.getHondaCity().isDisplayed(), equalTo(true));
		quotePage.getHondaCity().click();
		logger.info("select City successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select City successfully");
		waitElement(quotePage.getPetrol(), 3);
		assertThat(quotePage.getPetrol().isDisplayed(), equalTo(true));
		quotePage.getPetrol().click();
		logger.info("select petrol successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select petrol successfully");
		waitElement(quotePage.get_1point3EXI1343CC(), 3);
		assertThat(quotePage.get_1point3EXI1343CC().isDisplayed(), equalTo(true));
		quotePage.get_1point3EXI1343CC().click();
		logger.info("select 3G109cc successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select 1.3Exi successfully");
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
		waitElement(quotePage.getMumbai(), 3);
		assertThat(quotePage.getMumbai().isDisplayed(), equalTo(true));
		quotePage.getMumbai().click();
		logger.info("select mumbai successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select mumbai successfully");
		waitFor(1);
		waitElement(quotePage.getMumbai(), 3);
		assertThat(quotePage.getMumbai().isDisplayed(), equalTo(true));
		quotePage.getCity().sendKeys(" ");
		waitFor(1);
		waitElement(quotePage.getMumbaiSouth(), 3);
		assertThat(quotePage.getMumbaiSouth().isDisplayed(), equalTo(true));
		quotePage.getMumbaiSouth().click();
		logger.info("select mumbaiSouth successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select mumbaiSouth successfully");
	}

	private static int getMonthNumber(String monthName) {
		return Month.valueOf(monthName.toUpperCase()).getValue();
	}

	public void selectRegistrationDate(String year){

	}

	public void selectRegistrationDate(int Year,int Month,int Day) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Day);
		cal.add(Calendar.MONTH, -Month);
		cal.add(Calendar.YEAR, -Year);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yr = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		String yrs = yr.format(date);
		String mon = month.format(date);
		String dd = day.format(date);
		if (dd.charAt(0)=='0'){
			dd = dd.substring(1);
		}
		String date1 = format1.format(date);
		logger.info("Registration date selected as "+date1+" successfully");
		waitFor(1);
		//clickOnChooseDate();
		waitElement(quotePage.getChooseDate(), 3);
		assertThat(quotePage.getChooseDate().isDisplayed(), equalTo(true));
		quotePage.getChooseDate().click();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+yrs+"']")).click();
		waitFor(1);
		waitElement(quotePage.getCalendarMonth(), 3);
		assertThat(quotePage.getCalendarMonth().isDisplayed(), equalTo(true));
		String ccMonth=quotePage.getCalendarMonth().getText();
		int selectMonth=Integer.parseInt(mon);

		int currentMonth=getMonthNumber(ccMonth);
		if(selectMonth<currentMonth){
			for (int i=1;i<=currentMonth-selectMonth;i++){
				prevMonthAction();
			}
		}else {
			for (int i=1;i<=selectMonth-currentMonth;i++){
				nextMonthAction();
			}
		}
		waitFor(1);
		waitElement(quotePage.getDate16(), 3);
		assertThat(quotePage.getDate16().isDisplayed(), equalTo(true));
		chooseDateUsingAction(dd);
		logger.info("Registration date selected as "+date1+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Registration date selected as  "+date1+" successfully");
	}

	public void selectNewVehicle(){
		waitElementForClickable(quotePage.getNewVehicle(), 3);
		assertThat(quotePage.getNewVehicle().isDisplayed(), equalTo(true));
		quotePage.getNewVehicle().click();
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

	public void selectExpiryDate2(int Year,int Month,int Day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, Day);
		cal.add(Calendar.MONTH, Month);
		cal.add(Calendar.YEAR, Year);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yr = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		String yrs = yr.format(date);
		String mon = month.format(date);
		String dd = day.format(date);
		if (dd.charAt(0)=='0'){
			dd = dd.substring(1);
		}
		String date1 = format1.format(date);
		logger.info("Policy Expiry date selected as "+date1+" successfully");
		waitFor(1);
		clickOnChooseDate2(4);
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+yrs+"']")).click();
		waitFor(1);
		String ccMonth=driver.findElement(By.xpath("//h6[@data-mui-test='calendar-month-text']")).getText();
		//String ccMonth=quotePage.getCalendarMonth().getText();
		int selectMonth=Integer.parseInt(mon);

		int currentMonth=getMonthNumber(ccMonth);
		if(selectMonth<currentMonth){
			for (int i=1;i<=currentMonth-selectMonth;i++){
				prevMonthAction();
			}
		}else {
			for (int i=1;i<=selectMonth-currentMonth;i++){
				nextMonthAction();
			}
		}
		waitFor(1);
		chooseDateUsingAction(dd);
		logger.info("Policy Expiry date selected as "+date1+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Policy Expiry date selected as  "+date1+" successfully");

	}

	public void selectExpiryDate(int Year,int Month,int Day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, Day);
		cal.add(Calendar.MONTH, Month);
		cal.add(Calendar.YEAR, Year);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yr = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		String yrs = yr.format(date);
		String mon = month.format(date);
		String dd = day.format(date);
		if (dd.charAt(0)=='0'){
			dd = dd.substring(1);
		}
		String date1 = format1.format(date);
		logger.info("Policy Expiry date selected as "+date1+" successfully");
		waitFor(1);
		clickOnChooseDate();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+yrs+"']")).click();
		waitFor(1);
		String ccMonth=driver.findElement(By.xpath("//h6[@data-mui-test='calendar-month-text']")).getText();
		//String ccMonth=quotePage.getCalendarMonth().getText();
		int selectMonth=Integer.parseInt(mon);

		int currentMonth=getMonthNumber(ccMonth);
		if(selectMonth<currentMonth){
			for (int i=1;i<=currentMonth-selectMonth;i++){
				prevMonthAction();
			}
		}else {
			for (int i=1;i<=selectMonth-currentMonth;i++){
				nextMonthAction();
			}
		}
		waitFor(1);
		chooseDateUsingAction(dd);
		logger.info("Policy Expiry date selected as "+date1+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Policy Expiry date selected as  "+date1+" successfully");

	}

	public void selectDOB(String year) {
		waitElement(quotePage.getChooseDate(), 3);
		assertThat(quotePage.getChooseDate().isDisplayed(), equalTo(true));
		quotePage.getChooseDate().click();
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
		waitElement(quotePage.getRollOver(), 3);
		assertThat(quotePage.getRollOver().isDisplayed(), equalTo(true));
		quotePage.getRollOver().click();
		logger.info("prev policy expiry selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry selected successfully");
	}

	public void selectPolicyExpiry(String polExpiry){
		waitElement(quotePage.getRollOver(), 3);
		assertThat(quotePage.getRollOver().isDisplayed(), equalTo(true));
		assertThat(quotePage.getBreakin45().isDisplayed(), equalTo(true));
		assertThat(quotePage.getBreakin90().isDisplayed(), equalTo(true));
		assertThat(quotePage.getDontKnowPPExpDate().isDisplayed(), equalTo(true));
		if (polExpiry.contains("Don’t")){
			quotePage.getDontKnowPPExpDate().click();
		}else if (polExpiry.contains("more")){
			quotePage.getBreakin90().click();
		}else {
			quotePage.getBreakin45().click();
		}
		logger.info("prev policy expiry selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry selected successfully");
	}

	public void expiredWithin90days() {
		waitElement(quotePage.getBreakin45(), 3);
		assertThat(quotePage.getBreakin45().isDisplayed(), equalTo(true));
		quotePage.getBreakin45().click();
		logger.info("prev policy expiry date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry date selected successfully");
	}

	public void expiredMore90days() {
		waitElement(quotePage.getBreakin90(), 3);
		assertThat(quotePage.getBreakin90().isDisplayed(), equalTo(true));
		quotePage.getBreakin90().click();
		logger.info("prev policy expiry date selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy expiry date selected successfully");
	}

	public void backBtnPlanDetailsPage() {
		waitElement(quotePage.getPlanBackBtn(), 3);
		assertThat(quotePage.getPlanBackBtn().isDisplayed(), equalTo(true));
		quotePage.getPlanBackBtn().click();
		logger.info("clicked on planBackBtn successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, " clicked on planBackBtn successfully");
	}

	public void prevPolicyClaim(String Yes_No) {
		waitElement(quotePage.getYes(), 3);
		assertThat(quotePage.getYes().isDisplayed(), equalTo(true));
		if(Yes_No.equalsIgnoreCase("Yes")){
			quotePage.getYes().click();
		}else {
			quotePage.getNo().click();
		}
		logger.info("prev policy claimed selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy claimed selected successfully");
	}

	public void prevNcbPercent(String ncbPercetage) {
		waitElement(quotePage.getNcb35(), 5);
		assertThat(quotePage.getNcb35().isDisplayed(), equalTo(true));
		waitFor(1);
		if (ncbPercetage.contains("remember"))
		{waitElement(quotePage.getNcb35(), 5);
			assertThat(quotePage.getNcb35().isDisplayed(), equalTo(true));
			quotePage.getDontRememberNcb().click(); }
		else {
		driver.findElement(By.xpath("//div[text()='"+ncbPercetage+"']")).click();}
		logger.info("Prev NCB "+ncbPercetage+"% selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Prev NCB  "+ncbPercetage+"% selected successfully");
	}

	public void selectPrevInsurer() {
		waitElement(quotePage.getAcko(), 3);
		assertThat(quotePage.getAcko().isDisplayed(), equalTo(true));
		quotePage.getAcko().click();
		logger.info("prev policy insurer selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy insurer selected successfully");
	}

	public void selectPrevPolType(String polType) {
		waitElement(quotePage.getComp1yr(), 5);
		assertThat(quotePage.getComp1yr().isDisplayed(), equalTo(true));
		if (polType.equalsIgnoreCase("saod")){
			quotePage.getSaod().click();}
		else if (polType.equalsIgnoreCase("Bundle")){
			quotePage.getBundle1plus5().click();
		}else if (polType.equalsIgnoreCase("5yrTP")){
			quotePage.getTp5yr().click();
		}else if (polType.equalsIgnoreCase("1yrTP")){
			quotePage.getTp1yr().click();
		}else {
			quotePage.getComp1yr().click();
		}
		logger.info("prev policy Type selected as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy Type selected as "+polType+" successfully");
	}

	public void select4wPrevPolType(String polType) {
		waitElement(quotePage.getComp1yr(), 5);
		assertThat(quotePage.getComp1yr().isDisplayed(), equalTo(true));
		if (polType.equalsIgnoreCase("saod")){
			quotePage.getSaod().click();}
		else if (polType.equalsIgnoreCase("Bundle")){
			quotePage.getBundle1plus3().click();
		}else if (polType.equalsIgnoreCase("3yrTP")){
			quotePage.getTp3yr().click();
		}else if (polType.equalsIgnoreCase("1yrTP")){
			quotePage.getTp1yr().click();
		}else {
			quotePage.getComp1yr().click();
		}
		logger.info("prev policy Type selected as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "prev policy Type selected as "+polType+" successfully");

	}

	public void clickOngenerateQuote() {
		waitElement(quotePage.getGenerateQuote(), 13);
		assertThat(quotePage.getGenerateQuote().isDisplayed(), equalTo(true));
		quotePage.getGenerateQuote().click();
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
				WebElement e = driver.findElement(By.xpath("//span[text()='17']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}

	public void chooseDateUsingAction(String Date) {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//span[text()='"+Date+"']"));
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

	public void prevMonthAction() {
		new FluentWait<>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//button[@aria-label='previous month']"));
				Actions action = new Actions(driver);
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
		waitElement(proposalPage.getDate(),3);
		assertThat(proposalPage.getDate().isDisplayed(), equalTo(true));
		proposalPage.getDate().sendKeys(date1);
	}

	public void enterDate(String ddmmyyyy){
		waitElement(proposalPage.getDate(),3);
		assertThat(proposalPage.getDate().isDisplayed(), equalTo(true));
		proposalPage.getDate().sendKeys(ddmmyyyy);
	}


	public void isSelectedPolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("saod")|polType.equalsIgnoreCase("Bundle")|polType.equalsIgnoreCase("3yrTP")|polType.equalsIgnoreCase("5yrTP")){
			waitElement(quotePage.getPolTypeOD(), 30);
			assertThat(quotePage.getPolTypeOD().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolTypeOD().isDisplayed(), equalTo(true));
		}else if (polType.equalsIgnoreCase("1yrTP")){
			waitElement(quotePage.getPolTypeTp(), 120);
			assertThat(quotePage.getPolTypeTp().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolTypeTp().isDisplayed(), equalTo(true));
		}else {
			waitElement(quotePage.getPolTypeComp(), 60);
			assertThat(quotePage.getPolTypeComp().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolTypeComp().isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void isNewVehiclePolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("5TP")){
			waitElement(quotePage.getPolType5TP(), 70);
			assertThat(quotePage.getPolType5TP().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolType5TP().isDisplayed(), equalTo(true));
		} else {
			waitElement(quotePage.getInsPolType1OD_5TP(), 3);
			assertThat(quotePage.getInsPolType1OD_5TP().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolType1OD_5TP().isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void is4wNewVehiclePolTypeDisplayed(String polType) {
		waitFor(1);
		if (polType.equalsIgnoreCase("3TP")){
			waitElement(quotePage.getPolType3TP(), 70);
			assertThat(quotePage.getPolType3TP().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolType3TP().isDisplayed(), equalTo(true));
		} else {
			waitElement(quotePage.getInsPolType1OD_3TP(), 3);
			assertThat(quotePage.getInsPolType1OD_3TP().isDisplayed(), equalTo(true));
			assertThat(quotePage.getInsPolType1OD_3TP().isDisplayed(), equalTo(true));
		}
		logger.info("selected policy Type appearing as "+polType+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected policy Type appearing as "+polType+" successfully");
	}

	public void changePolicyTypeForNewVehicle(String changeTo){
		waitElement(quotePage.getPolTypePlanPage(),3);
		assertThat(quotePage.getPolTypePlanPage().isDisplayed(), equalTo(true));
		quotePage.getPolTypePlanPage().click();
		waitElement(quotePage.getApply(),3);
		assertThat(quotePage.getApply().isDisplayed(), equalTo(true));
		assertThat(quotePage.get_1OD_5TP().isDisplayed(), equalTo(true));
		assertThat(quotePage.get_5TP().isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("5TP")){
			quotePage.get_5TP().click();
			waitFor(1.5);
			waitElement(quotePage.getApply(),3);
			quotePage.getApply().click();
		}else {
			quotePage.getPlanBackBtn().click();}
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void change4wPolicyTypeForNewVehicle(String changeTo){
		waitElement(quotePage.getPolTypePlanPage(),3);
		assertThat(quotePage.getPolTypePlanPage().isDisplayed(), equalTo(true));
		quotePage.getPolTypePlanPage().click();
		waitElement(quotePage.getApply(),3);
		assertThat(quotePage.getApply().isDisplayed(), equalTo(true));
		assertThat(quotePage.get_1OD_3TP().isDisplayed(), equalTo(true));
		assertThat(quotePage.get_3TP().isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("3TP")){
			quotePage.get_3TP().click();
			waitElement(quotePage.getApply(),3);
			quotePage.getApply().click();
		}else {
			quotePage.getBackBtnAddonPage().click(); }
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void changePolicyType(String changeTo){
		waitElement(quotePage.getPolTypePlanPage(),3);
		assertThat(quotePage.getPolTypePlanPage().isDisplayed(), equalTo(true));
		quotePage.getPolTypePlanPage().click();
		waitElement(quotePage.getApply(),3);
		assertThat(quotePage.getApply().isDisplayed(), equalTo(true));
		assertThat(quotePage.getComprehensive().isDisplayed(), equalTo(true));
		assertThat(quotePage.getThirdParty().isDisplayed(), equalTo(true));
		if(changeTo.equalsIgnoreCase("TP")){
			quotePage.getThirdParty().click();
		}else {
			quotePage.getComprehensive().click(); }
		waitElement(quotePage.getApply(),3);
		quotePage.getApply().click();
		logger.info("policy Type change as "+changeTo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "policy Type change as "+changeTo+" successfully");
		waitFor(5);
	}

	public void isPlanPageDisplayed(){
		waitElement(quotePage.getViewDetails(), 120);
		waitElement(quotePage.getMotorProfile(), 3);
		assertThat(quotePage.getMotorProfile().isDisplayed(), equalTo(true));
		assertThat(quotePage.getPolTypePlanPage().isDisplayed(), equalTo(true));
		assertThat(quotePage.getIdv().isDisplayed(), equalTo(true));
		assertThat(quotePage.getBestMatch().isDisplayed(), equalTo(true));
		assertThat(quotePage.getAddOns().isDisplayed(), equalTo(true));
		logger.info("verify appearance of plan page successfully");
		logger.info("Quote Name - "+quotePage.getMotorProfile().getText());
		logger.info(driver.getCurrentUrl());
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify appearance of plan page successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Quote Name - "+quotePage.getMotorProfile().getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, driver.getCurrentUrl());
	}


	public void clickOnViewDetailsDigit(){
		waitElement(quotePage.getDigitViewDetails(), 3);
		assertThat(quotePage.getDigitViewDetails().isDisplayed(), equalTo(true));
		quotePage.getDigitViewDetails().click();
		logger.info("clicked on Digit view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Digit view Details successfully");

	}

	public void clickOnViewDetailsReliance(){
		waitElement(quotePage.getRelianceViewDetails(), 3);
		assertThat(quotePage.getRelianceViewDetails().isDisplayed(), equalTo(true));
		quotePage.getRelianceViewDetails().click();
		logger.info("clicked on Reliance view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Reliance view Details successfully");

	}

	public void clickOnBuyNow(){
		waitElement(quotePage.getBuyNow(), 3);
		assertThat(quotePage.getBuyNow().isDisplayed(), equalTo(true));
		quotePage.getBuyNow().click();
		logger.info("clicked on Buy now successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Buy Now successfully");

	}

	public void clickOnViewDetailsHdfcErgo(){
		waitElement(quotePage.getHdfcViewDetails(), 3);
		assertThat(quotePage.getHdfcViewDetails().isDisplayed(), equalTo(true));
		quotePage.getHdfcViewDetails().click();
		logger.info("clicked on HdfcErgo view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on HdfcErgo view Details successfully");
	}

	public void clickBuyNowHdfcErgo(){
		waitElement(quotePage.getHdfcBuyNow(), 3);
		assertThat(quotePage.getHdfcBuyNow().isDisplayed(), equalTo(true));
		quotePage.getHdfcBuyNow().click();
		logger.info("clicked on HdfcErgo BuyNow successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on HdfcErgo BuyNow successfully");
	}

	public void clickBuyNowIcici(){
		waitElement(quotePage.getIciciBuyNow(), 3);
		assertThat(quotePage.getIciciBuyNow().isDisplayed(), equalTo(true));
		quotePage.getIciciBuyNow().click();
		logger.info("clicked on icici BuyNow successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on icici BuyNow successfully");
	}

	public void clickOnViewDetailsTataAig(){
		waitElement(quotePage.getTataViewDetails(), 3);
		assertThat(quotePage.getTataViewDetails().isDisplayed(), equalTo(true));
		quotePage.getTataViewDetails().click();
		logger.info("clicked on TataAig view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on TataAig view Details successfully");

	}

	public void clickOnViewDetailsIciciL(){
		waitElement(quotePage.getIciciViewDetails(), 3);
		assertThat(quotePage.getIciciViewDetails().isDisplayed(), equalTo(true));
		quotePage.getIciciViewDetails().click();
		logger.info("clicked on ICICI Lombard view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on ICICI Lombard view Details successfully");
	}

	public void clickOnViewDetailsBajaj(){
		waitElement(quotePage.getBajajViewDetails(), 3);
		assertThat(quotePage.getBajajViewDetails().isDisplayed(), equalTo(true));
		quotePage.getBajajViewDetails().click();
		logger.info("clicked on Bajaj Allianz view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on Bajaj Allianz view Details successfully");

	}

	public void clickOnViewDetailsfutureGeneral(){
		waitElement(quotePage.getFutureViewDetails(), 3);
		assertThat(quotePage.getFutureViewDetails().isDisplayed(), equalTo(true));
		quotePage.getFutureViewDetails().click();
		logger.info("clicked on futureGeneral view Details successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on futureGeneral view Details successfully");

	}

	public void getSubtotal(){
		waitElement(quotePage.getSubTotal(), 3);
		assertThat(quotePage.getSubTotal().isDisplayed(), equalTo(true));
		logger.info("verify subTotal "+quotePage.getSubTotal().getText()+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify subTotal "+quotePage.getSubTotal().getText()+" successfully");
	}

	public String getSubTotalWith18percentGst(){
		waitElement(quotePage.getSubTotal(), 3);
		assertThat(quotePage.getSubTotal().isDisplayed(), equalTo(true));
		String subTotalAmt=quotePage.getSubTotal().getText().replaceAll("₹", "").replaceAll(",","").trim();
		float stAmt=Float.parseFloat(subTotalAmt);
		float gst18=(stAmt*18)/100;
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(0);
		return	df.format(stAmt+gst18).replaceAll(",","");
	}

	public String get18percentGstAmt(){
		waitElement(quotePage.getSubTotal(), 3);
		assertThat(quotePage.getSubTotal().isDisplayed(), equalTo(true));
		String subTotalAmt=quotePage.getSubTotal().getText().replaceAll("₹", "").replaceAll(",","").trim();
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
		waitElement(quotePage.getGst18(), 3);
		assertThat(quotePage.getGst18().isDisplayed(), equalTo(true));
		String gstAmt=quotePage.getGst18().getText().replaceAll("₹", "").replaceAll(",","").trim();
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
		waitElement(quotePage.getPremiumAmt(), 3);
		assertThat(quotePage.getPremiumAmt().isDisplayed(), equalTo(true));
		String totalAmt=quotePage.getPremiumAmt().getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(totalAmt, anyOf(is(calAmount), is(calvalue1),is(calvalue2)));
		logger.info("verify premium amount "+calAmount+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount "+calAmount+" successfully");
	}

	public String getPremiumAmount(){
		waitElement(quotePage.getPremiumAnnuallyAmt(), 3);
		assertThat(quotePage.getPremiumAnnuallyAmt().isDisplayed(), equalTo(true));
		String totalAmt=quotePage.getPremiumAnnuallyAmt().getText();
		String premiumAmt=totalAmt.replaceAll("₹", "").replaceAll(",","").trim();
		logger.info("verify premium amount "+totalAmt+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount "+totalAmt+" successfully");
		return premiumAmt;
	}

	public String getIdvAmount(){
		waitElement(quotePage.getIdvAmt(), 3);
		assertThat(quotePage.getIdvAmt().isDisplayed(), equalTo(true));
		String idvAmt=quotePage.getIdvAmt().getText().replaceAll("₹", "").replaceAll(",","").trim();
		logger.info("verify idv amount "+idvAmt+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify idv amount "+idvAmt+" successfully");
		return idvAmt;
	}

	public void isTotalPremiumEqualTo(String premium){
		waitElement(paymentPage.getPremiumTotalPaymentPage(), 3);
		assertThat(paymentPage.getPremiumTotalPaymentPage().isDisplayed(), equalTo(true));
		String totalPremium=paymentPage.getPremiumTotalPaymentPage().getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(totalPremium, equalTo(premium));
		logger.info("verify premium amount on payment page "+totalPremium+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify premium amount on payment page "+totalPremium+" successfully");
	}

	public void isIDVEqualTo(String idvAmt){
		waitElement(paymentPage.getIdvValue(), 3);
		assertThat(paymentPage.getIdvValue().isDisplayed(), equalTo(true));
		String idvAmount=paymentPage.getIdvValue().getText().replaceAll("₹", "").replaceAll(",","").trim();
		assertThat(idvAmount, equalTo(idvAmt));
		logger.info("verify IDV amount on payment page "+idvAmount+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "verify IDV amount on payment page "+idvAmount+" successfully");
	}

	public void selectSalutation(String Salutation){
		waitElement(proposalPage.getMrHdfc(), 3);
		assertThat(proposalPage.getMrHdfc().isDisplayed(), equalTo(true));
		if (Salutation.equalsIgnoreCase("MS")){
			proposalPage.getMsHdfc().click();
		}else if (Salutation.equalsIgnoreCase("MRS")){
			proposalPage.getMrsHdfc().click();
		}else if (Salutation.equalsIgnoreCase("M_S")){
			proposalPage.getM_sHdfc().click();
		}else {
			proposalPage.getMrHdfc().click();}
		logger.info("salution selected as "+Salutation+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "salution selected "+Salutation+" successfully");
	}

	public void selectTitle(String Title){
		waitElement(proposalPage.getMr(), 3);
		assertThat(proposalPage.getMr().isDisplayed(), equalTo(true));
		if (Title.equalsIgnoreCase("Ms")){
			proposalPage.getMs().click();
		}else if (Title.equalsIgnoreCase("Mrs")){
			proposalPage.getMrs().click();
		}else if (Title.equalsIgnoreCase("M_S")){
			proposalPage.getM_s().click();
		}else if (Title.equalsIgnoreCase("Dr")){
			proposalPage.getDr().click();
		}else if (Title.equalsIgnoreCase("Miss")){
			proposalPage.getMiss().click();
		}else {
			proposalPage.getMr().click();}
		logger.info("Title selected as "+Title+ "successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Title selected as "+Title+"successfully");
	}

	public String enterFirstName(String firstName){
		waitElement(proposalPage.getFName(), 3);
		assertThat(proposalPage.getFName().isDisplayed(), equalTo(true));
		proposalPage.getFName().sendKeys(firstName);
		logger.info("First Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "First Name enter successfully");
		return firstName;
	}

	public void enterMiddleName(String midName){
		waitElement(proposalPage.getMName(), 3);
		assertThat(proposalPage.getMName().isDisplayed(), equalTo(true));
		proposalPage.getMName().sendKeys(midName);
		logger.info("Middle Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Middle Name enter successfully");
	}


	public void enterLastName(String lastName){
		waitElement(proposalPage.getLname(), 3);
		assertThat(proposalPage.getLname().isDisplayed(), equalTo(true));
		proposalPage.getLname().sendKeys(lastName);
		logger.info("Last Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Last Name enter successfully");
	}

	public void enterNomineeFirstName(String firstName){
		waitElement(proposalPage.getNomineefName(), 5);
		assertThat(proposalPage.getNomineefName().isDisplayed(), equalTo(true));
		proposalPage.getNomineefName().sendKeys(firstName);
		logger.info("First Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "First Name enter successfully");
	}

	public void enterNomineeMiddleName(String midName){
		waitElement(proposalPage.getNomineemName(), 3);
		assertThat(proposalPage.getNomineemName().isDisplayed(), equalTo(true));
		proposalPage.getNomineemName().sendKeys(midName);
		logger.info("Middle Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Middle Name enter successfully");
	}


	public void enterNomineeLastName(String lastName){
		waitElement(proposalPage.getNomineelname(), 3);
		assertThat(proposalPage.getNomineelname().isDisplayed(), equalTo(true));
		proposalPage.getNomineelname().sendKeys(lastName);
		logger.info("Last Name enter successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Last Name enter successfully");
	}

	public void selectGenderUpperCase(String gender){
		waitElement(proposalPage.getMaleHdfc(), 3);
		assertThat(proposalPage.getMaleHdfc().isDisplayed(), equalTo(true));
		if (gender.equalsIgnoreCase("FEMALE")){
			proposalPage.getFemaleHdfc().click();
		}else {
			proposalPage.getMaleHdfc().click();}
		logger.info("gender selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "gender selected successfully");
	}


	public void selectGender(String gender){
		waitElement(proposalPage.getMale(), 3);
		assertThat(proposalPage.getMale().isDisplayed(), equalTo(true));
		if (gender.equalsIgnoreCase("Female")){
			proposalPage.getFemale().click();
		}else {
			proposalPage.getMale().click();}
		logger.info("gender selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "gender selected successfully");
	}

	public void enterEmailId(String email){
		waitElement(proposalPage.getEmail(), 3);
		assertThat(proposalPage.getEmail().isDisplayed(), equalTo(true));
		proposalPage.getEmail().sendKeys(email);
		logger.info("enter email id successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter email id successfully");
	}

	public void enterPhone(String phone){
		waitElement(proposalPage.getPhone(), 3);
		assertThat(proposalPage.getPhone().isDisplayed(), equalTo(true));
		proposalPage.getPhone().sendKeys(phone);
		logger.info("enter phone successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter phone successfully");
	}

	public void enterPermAddress(String address){
		waitElement(proposalPage.getAddress(), 3);
		assertThat(proposalPage.getAddress().isDisplayed(), equalTo(true));
		proposalPage.getAddress().sendKeys(address);
		logger.info("enter address successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter address successfully");
	}

	public void enterNomineeAddress(String address){
		waitElement(proposalPage.getNomineeAddress(), 3);
		assertThat(proposalPage.getNomineeAddress().isDisplayed(), equalTo(true));
		proposalPage.getNomineeAddress().sendKeys(address);
		logger.info("enter address successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter address successfully");
	}

	public void enterPincode(String phone){
		waitElement(proposalPage.getPincode(), 3);
		assertThat(proposalPage.getPincode().isDisplayed(), equalTo(true));
		proposalPage.getPincode().sendKeys(phone);
		logger.info("enter pincode successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter pincode successfully");
	}

	public void selectAsSame(){
		waitElement(proposalPage.getSameAsPermAdd(), 3);
		assertThat(proposalPage.getSameAsPermAdd().isDisplayed(), equalTo(true));
		proposalPage.getSameAsPermAdd().click();
		logger.info("click as Same Address successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click as Same Address successfully");
	}

	public void enterNomineeName(String NomineeName){
		waitElement(proposalPage.getNomineeName(), 3);
		assertThat(proposalPage.getNomineeName().isDisplayed(), equalTo(true));
		proposalPage.getNomineeName().sendKeys(NomineeName);
		logger.info("enter NomineeName successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter NomineeName successfully");
	}

	public void enterNomineeAge(String NomineeAge){
		waitElement(proposalPage.getNomineeAge(), 3);
		assertThat(proposalPage.getNomineeAge().isDisplayed(), equalTo(true));
		proposalPage.getNomineeAge().sendKeys(NomineeAge);
		logger.info("enter NomineeAge successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter NomineeAge successfully");
	}

	public void openDropDownBox(){
		waitFor(3);
		waitElement(proposalPage.getListbox(), 3);
		assertThat(proposalPage.getListbox().isDisplayed(), equalTo(true));
		proposalPage.getListbox().click();
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
		waitElement(proposalPage.getVehicleLicenseNumber(), 6);
		assertThat(proposalPage.getVehicleLicenseNumber().isDisplayed(), equalTo(true));
		proposalPage.getVehicleLicenseNumber().sendKeys(vehicleNum);
		logger.info("enter vehicleNumer as "+vehicleNum+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter vehicleNumer AS "+vehicleNum+" successfully");
	}


	public void enterChassisNumber(String ChassisNumber){
		waitElement(proposalPage.getVehicleChassisNumber(), 3);
		assertThat(proposalPage.getVehicleChassisNumber().isDisplayed(), equalTo(true));
		proposalPage.getVehicleChassisNumber().sendKeys(ChassisNumber);
		logger.info("enter ChassisNumber as "+ChassisNumber+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter ChassisNumber as "+ChassisNumber+" successfully");
	}


	public void enterEngineNumber(String EngineNumber){
		waitElement(proposalPage.getVehicleEngineNumber(), 3);
		assertThat(proposalPage.getVehicleEngineNumber().isDisplayed(), equalTo(true));
		proposalPage.getVehicleEngineNumber().sendKeys(EngineNumber);
		logger.info("enter EngineNumber as "+EngineNumber+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter EngineNumber as "+EngineNumber+" successfully");
	}

	public void enterPrevPolicyNum(String prevPolicyNum){
		waitElement(proposalPage.getPreviousPolicyNumber(), 3);
		assertThat(proposalPage.getPreviousPolicyNumber().isDisplayed(), equalTo(true));
		proposalPage.getPreviousPolicyNumber().sendKeys(prevPolicyNum);
		logger.info("enter previousPolicyNumber successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter previousPolicyNumber successfully");
	}

	public void clickOnReviewNSubmit(){
		waitFor(1);
		((JavascriptExecutor) driver)
				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		waitFor(2);
		waitElement(proposalPage.getReviewNsubmit(), 13);
		assertThat(proposalPage.getReviewNsubmit().isDisplayed(), equalTo(true));
		proposalPage.getReviewNsubmit().click();
		logger.info("clicked on reviewNsubmit successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on reviewNsubmit successfully");
	}


	public void selectMaritalStatus(String maritalStatus){
		waitElement(proposalPage.getSingle(), 3);
		assertThat(proposalPage.getSingle().isDisplayed(), equalTo(true));
		if (maritalStatus.equalsIgnoreCase("Married")){
			proposalPage.getMarried().click();
		}else if (maritalStatus.equalsIgnoreCase("Divorcee")){
			proposalPage.getDivorcee().click();}
		else if (maritalStatus.equalsIgnoreCase("Widow")){
			proposalPage.getWidow().click(); }
		else if (maritalStatus.equalsIgnoreCase("Widower")){
			proposalPage.getWidower().click();}
		else {
			proposalPage.getSingle().click();}
		logger.info("marital status selected as "+maritalStatus+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "marital status selected as "+maritalStatus+" successfully");
	}

	public void enterAddressLine1(String addressLine1){
		waitElement(proposalPage.getAddressLine1(), 3);
		assertThat(proposalPage.getAddressLine1().isDisplayed(), equalTo(true));
		proposalPage.getAddressLine1().sendKeys(addressLine1);
		logger.info("enter addressLine1 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine1 successfully");
	}

	public void enterAddressLine2(String addressLine2){
		waitElement(proposalPage.getAddressLine2(), 3);
		assertThat(proposalPage.getAddressLine2().isDisplayed(), equalTo(true));
		proposalPage.getAddressLine2().sendKeys(addressLine2);
		logger.info("enter addressLine2 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine2 successfully");
	}

	public void enterAddressLine1FL(String addressLine1){
//		Actions act=new Actions(driver);
//		act.sendKeys(driver.findElement(By.xpath("//input[contains(@id,'ddressLine1')]")),addressLine1).build().perform();
		waitElement(proposalPage.getPreviousInsurerAddressLine1(), 3);
		assertThat(proposalPage.getPreviousInsurerAddressLine1().isDisplayed(), equalTo(true));
		proposalPage.getPreviousInsurerAddressLine1().sendKeys(addressLine1);
		logger.info("enter addressLine1 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine1 successfully");
	}

	public void enterAddressLine2FL(String addressLine2){
//		Actions act=new Actions(driver);
//		act.sendKeys(driver.findElement(By.xpath("//input[contains(@id,'ddressLine2')]")),addressLine2).build().perform();
		waitElement(proposalPage.getPreviousInsurerAddressLine2(), 3);
		assertThat(proposalPage.getPreviousInsurerAddressLine2().isDisplayed(), equalTo(true));
		proposalPage.getPreviousInsurerAddressLine2().sendKeys(addressLine2);
		logger.info("enter addressLine2 successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter addressLine2 successfully");
	}

	public void enterAddressLine3(String addressLine3){
		waitElement(proposalPage.getAddressLine3(), 3);
		assertThat(proposalPage.getAddressLine3().isDisplayed(), equalTo(true));
		proposalPage.getAddressLine3().sendKeys(addressLine3);
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
		waitElement(proposalPage.getSameAsCommunication(), 3);
		assertThat(proposalPage.getSameAsCommunication().isDisplayed(), equalTo(true));
		proposalPage.getSameAsCommunication().click();
		logger.info("Same As Communication Address selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Same As Communication Address selected successfully");
	}

	public void selectSameAsCommAddForRegnAdd(){
		waitElement(proposalPage.getSameAsCommRegAdd(), 3);
		assertThat(proposalPage.getSameAsCommRegAdd().isDisplayed(), equalTo(true));
		proposalPage.getSameAsCommRegAdd().click();
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

	}

	public void selectManufactureDate(int Year,int Month,int Day){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -Day);
		cal.add(Calendar.MONTH, -Month);
		cal.add(Calendar.YEAR, -Year);
		Date date = cal.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat yr = new SimpleDateFormat("yyyy");
		SimpleDateFormat month = new SimpleDateFormat("MM");
		SimpleDateFormat day = new SimpleDateFormat("dd");
		String yrs = yr.format(date);
		String mon = month.format(date);
		String dd = day.format(date);
		if (dd.charAt(0)=='0'){
			dd = dd.substring(1);
		}
		String date1 = format1.format(date);
		logger.info("Registration date selected as "+date1+" successfully");
		waitFor(1);
		clickOnChooseDate();
		waitFor(1);
		driver.findElement(By.xpath("//button[text()='"+yrs+"']")).click();
		waitFor(1);
		String ccMonth=driver.findElement(By.xpath("//h6[@data-mui-test='calendar-month-text']")).getText();
		//String ccMonth=quotePage.getCalendarMonth().getText();
		int selectMonth=Integer.parseInt(mon);

		int currentMonth=getMonthNumber(ccMonth);
		if(selectMonth<currentMonth){
			for (int i=1;i<=currentMonth-selectMonth;i++){
				prevMonthAction();
			}
		}else {
			for (int i=1;i<=selectMonth-currentMonth;i++){
				nextMonthAction();
			}
		}
		waitFor(1);
		chooseDateUsingAction(dd);
		logger.info("manufacturing date selected as "+date1+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "manufacturing date selected as  "+date1+" successfully");

	}

	public void selcectPrevPolExpDateBreakin(String year){
		selectPrevMonthDate(year);
		logger.info("select manufacturing date successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "select manufacturing date successfully");
	}

	public void isVehicleHypothecated(String yesNo){
		waitElement(proposalPage.getYes2(), 3);
		assertThat(proposalPage.getYes2().isDisplayed(), equalTo(true));
		if (yesNo.equalsIgnoreCase("Yes")){
			proposalPage.getYes2().click();
		}else {
			proposalPage.getNo2().click();
		}
		logger.info("selected as "+yesNo+" successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected as "+yesNo+" successfully");
		waitFor(1);
		scrollUpDown(0,200);
	}


	public void selectYesNo(String yesNo){
		waitElement(proposalPage.getYes2Tata(), 3);
		assertThat(proposalPage.getYes2Tata().isDisplayed(), equalTo(true));
		if (yesNo.equalsIgnoreCase("Yes")){
			proposalPage.getYes2Tata().click();
		}else {
			proposalPage.getNo2Tata().click();
		}
		logger.info("selected as "+yesNo+"successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "selected as "+yesNo+"successfully");
	}

	public void clockOnAgree(){
		waitElement(proposalPage.getAgree(), 3);
		assertThat(proposalPage.getAgree().isDisplayed(), equalTo(true));
		proposalPage.getAgree().click();
		logger.info("Same As Communication Address selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Same As Communication Address selected successfully");
	}

	public void enterFinancerNameTata(String name){
		waitFor(3);
		waitElement(proposalPage.getFinancerName(), 20);
		assertThat(proposalPage.getFinancerName().isDisplayed(), equalTo(true));
		proposalPage.getFinancerName().click();
		proposalPage.getFinancerName().sendKeys(name);
		waitFor(1);
		proposalPage.getFinancerName().sendKeys(Keys.DOWN,Keys.ENTER);
		logger.info("financerName selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "financerName selected successfully");
	}

	public void enterFinancerAddressTata(String address){
		waitFor(1);
		waitElement(proposalPage.getFinancierAddress(), 3);
		assertThat(proposalPage.getFinancierAddress().isDisplayed(), equalTo(true));
		proposalPage.getFinancierAddress().sendKeys(address);
		logger.info("financer Address entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "financer Address entered successfully");
	}

	public void selectComprehensiveForProposal(){
		waitElement(proposalPage.getComprehensive(), 3);
		assertThat(proposalPage.getComprehensive().isDisplayed(), equalTo(true));
		proposalPage.getComprehensive().click();
		logger.info("comprehensive selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "comprehensive selected successfully");
	}

	public void selectThirdpartyForProposal(){
		waitElement(proposalPage.getThirdparty(), 3);
		assertThat(proposalPage.getThirdparty().isDisplayed(), equalTo(true));
		proposalPage.getThirdparty().click();
		logger.info("thirdparty selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "thirdparty selected successfully");
	}

	public void isPaymentPageDisplayed(){
		waitingForPaymentPage();
		waitElement(paymentPage.getCpPolTypePaymentPage(), 70);
		assertThat(paymentPage.getCompanyLogo().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getBackHome().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getMakePayment().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCopyPaymentLink().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getProposalCreated().isDisplayed(), equalTo(true));
		logger.info("PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentPage verified successfully");
	}

	public void isBreakingPaymentPageDisplayed(){
		waitingForPaymentPage();
		waitElement(paymentPage.getCpPolTypePaymentPage(), 70);
		assertThat(paymentPage.getCompanyLogo().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getMakePayment().isDisplayed(), equalTo(true));
		logger.info("breaking inspection PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "breaking inspection PaymentPage verified successfully");
	}

	public void clickOnMakePayment(){
		waitElement(paymentPage.getMakePayment(), 20);
		assertThat(paymentPage.getMakePayment().isDisplayed(), equalTo(true));
		paymentPage.getMakePayment().click();
		logger.info("clicked on makePayment successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on makePayment successfully");
	}

	public void isDigitPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getMockPay(), 30);
		assertThat(paymentPage.getMockPay().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getWallet().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getNetbanking().isDisplayed(), equalTo(true));
		logger.info("Digit PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit PaymentPage verified successfully");
	}

	public void isTataAigPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getTataLogo(), 30);
		assertThat(paymentPage.getTataLogo().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getPayAmtTata().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCardTata().isDisplayed(), equalTo(true));
		logger.info("TATA Aig PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "TATA Aig PaymentPage verified successfully");
	}


	public void isBajajPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getBajajLogo(), 30);
		assertThat(paymentPage.getBajajLogo().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCreditDebitBajaj().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getPayAmtBajaj().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getNetBankingBajaj().isDisplayed(), equalTo(true));
		logger.info("Bajaj PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Bajaj PaymentPage verified successfully");
	}

	public void isICICIPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getLogoIl(), 30);
		assertThat(paymentPage.getEmail().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getPhoneIl().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getProceedIl().isDisplayed(), equalTo(true));
		logger.info("ICICI L PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "ICICI L PaymentPage verified successfully");
	}

	public void isReliancePaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getCreditCardRel(), 30);
		assertThat(paymentPage.getRelianceLogo().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCreditCardRel().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getDebitCardsRel().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getNetBankingRel().isDisplayed(), equalTo(true));
		logger.info("Reliance PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Reliance PaymentPage verified successfully");
	}

	public void clickOnMockPayment(){
		waitFor(2);
		waitElement(paymentPage.getMockPay(), 3);
		assertThat(paymentPage.getMockPay().isDisplayed(), equalTo(true));
		paymentPage.getMockPay().click();
		logger.info("clicked on mock payment successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "clicked on mock payment successfully");
	}

	public void isPaymentSuccessPageDisplayed(){
		waitElement(paymentPage.getPaymentSuccess(), 70);
		assertThat(paymentPage.getPaymentSuccess().isDisplayed(), equalTo(true));
		waitFor(2);
		driver.navigate().refresh();
		logger.info("PaymentSuccessPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentSuccessPage verified successfully");
	}

	public void isPaymentSuccessPageDisplayedForFL(){
		waitElement(paymentPage.getPaymentSuccess(), 70);
		assertThat(paymentPage.getPaymentSuccess().isDisplayed(), equalTo(true));
		waitFor(2);
		driver.navigate().refresh();
		logger.info("PaymentSuccessPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "PaymentSuccessPage verified successfully");
	}

	public void isPolicyDownloadPageDisplayed(){
		waitElement(policyDownloadPage.getBackHome(), 70);
		assertThat(policyDownloadPage.getSuccessPage().isDisplayed(), equalTo(true));
		assertThat(policyDownloadPage.getDownloadPolicy().isDisplayed(), equalTo(true));
		logger.info("DownloadPageDisplayed verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "DownloadPageDisplayed successfully");
	}

	public void clickOnDownloadPolicy(){
		waitElement(policyDownloadPage.getDownloadPolicy(), 30);
		assertThat(policyDownloadPage.getDownloadPolicy().isDisplayed(), equalTo(true));
		waitElementForClickable(policyDownloadPage.getDownloadPolicy(),30);
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
		waitElement(paymentPage.getOk(), 30);
		assertThat(paymentPage.getOk().isDisplayed(), equalTo(true));
		paymentPage.getOk().click();
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
		waitElement(proposalPage.getListbox3(), 30);
		assertThat(proposalPage.getListbox3().isDisplayed(), equalTo(true));
		proposalPage.getListbox3().click();
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
		waitElement(proposalPage.getTpPolicyNumber(), 30);
		assertThat(proposalPage.getTpPolicyNumber().isDisplayed(), equalTo(true));
		proposalPage.getTpPolicyNumber().sendKeys(tpPolNum);
		logger.info("tpPolicyNumber entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "tpPolicyNumber entered successfully");
	}


	public void enterHdfcPaymentDetails(String cardNumber,String cvv){
		waitElement(paymentPage.getSendAnyway(),30);
		assertThat(paymentPage.getSendAnyway().isDisplayed(), equalTo(true));
		paymentPage.getSendAnyway().click();
		waitElement(paymentPage.getAmt(),30);
		assertThat(paymentPage.getAmt().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCreditDebitHdfc().isDisplayed(), equalTo(true));
		paymentPage.getCreditDebitHdfc().click();
		assertThat(paymentPage.getPayHdfc().isDisplayed(), equalTo(true));
		paymentPage.getPayHdfc().click();
		waitElement(paymentPage.getPaymentGatewayTest(),30);
		assertThat(paymentPage.getPaymentGatewayTest().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getSubmit().isDisplayed(), equalTo(true));
		paymentPage.getSubmit().click();
		waitFor(2);
		waitElement(paymentPage.getTxtCardNo(),30);
		assertThat(paymentPage.getTxtCardNo().isDisplayed(), equalTo(true));
		paymentPage.getTxtCardNo().sendKeys(cardNumber);
		assertThat(paymentPage.getCvvHdfc().isDisplayed(), equalTo(true));
		paymentPage.getCvvHdfc().sendKeys(cvv);
		waitFor(1);
		actionOnSubmit();
		waitFor(2);
		waitElement(paymentPage.getPaymentSuccess(),70);
		assertThat(paymentPage.getPaymentSuccess().isDisplayed(), equalTo(true));
		logger.info("HDFC Payment Details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "HDFC Payment Details entered successfully");
	}

	public void proceedReliancePayment(){
		waitElement(paymentPage.getNetBankingRel(),30);
		assertThat(paymentPage.getNetBankingRel().isDisplayed(), equalTo(true));
		paymentPage.getNetBankingRel().click();
		waitElement(paymentPage.getSelecteBankRel(),10);
		assertThat(paymentPage.getSelecteBankRel().isDisplayed(), equalTo(true));
		Select bank=new Select(paymentPage.getSelecteBankRel());
		bank.selectByVisibleText("AvenuesTest");
		waitElement(paymentPage.getMakePaymentRel(),30);
		assertThat(paymentPage.getMakePaymentRel().isDisplayed(), equalTo(true));
		paymentPage.getMakePaymentRel().click();
		//enterTextAction("Make Payment",2);
		waitElement(paymentPage.getReturnToMerchantSite(),8);
		assertThat(paymentPage.getReturnToMerchantSite().isDisplayed(), equalTo(true));
		paymentPage.getReturnToMerchantSite().click();
		waitFor(3);
		waitElement(paymentPage.getSendAnyway(),10);
		assertThat(paymentPage.getSendAnyway().isDisplayed(), equalTo(true));
		paymentPage.getSendAnyway().click();
		waitFor(2);
		logger.info("Reliance Payment has been completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Reliance Payment has been completed successfully");
	}

	public void proceedBajajPayment(){
		waitElement(paymentPage.getNetBankingBajaj(),30);
		assertThat(paymentPage.getNetBankingBajaj().isDisplayed(), equalTo(true));
		paymentPage.getNetBankingBajaj().click();
		waitElement(paymentPage.getBajajPayByHDFC(),10);
		assertThat(paymentPage.getBajajPayByHDFC().isDisplayed(), equalTo(true));
		paymentPage.getBajajPayByHDFC().click();
		waitElement(paymentPage.getMakePaymentBajaj(),3);
		assertThat(paymentPage.getMakePaymentBajaj().isDisplayed(), equalTo(true));
		paymentPage.getMakePaymentBajaj().click();
		waitFor(9);
		enterTextAction("Make Payment",1);
		waitElement(paymentPage.getReturnToMerchantSite(),8);
		assertThat(paymentPage.getReturnToMerchantSite().isDisplayed(), equalTo(true));
		paymentPage.getReturnToMerchantSite().click();
		waitFor(3);
		waitElement(paymentPage.getSendAnyway(),10);
		assertThat(paymentPage.getSendAnyway().isDisplayed(), equalTo(true));
		paymentPage.getSendAnyway().click();
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
		waitElement(policyDownloadPage.getInsurerDownloadMessage(), 70);
		assertThat(policyDownloadPage.getInsurerDownloadMessage().isDisplayed(),equalTo(true));
		assertThat(policyDownloadPage.getInsurerDownloadMessage().getText(),equalTo("Insurance Co. did not provide the policy PDF. It usually takes 1 hour. Please check after 1 hour in Earnings section."));
		logger.info("Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessage().getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessage().getText());
	}

	public boolean isPolicyDownloadMessageDispalyed(){
		try {
			waitElement(policyDownloadPage.getInsurerDownloadMessage(), 3);
			assertThat(policyDownloadPage.getInsurerDownloadMessage().isDisplayed(),equalTo(true));
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public boolean isPolicyDownloadMessageDispalyedReliance(){
		try {
			waitElement(policyDownloadPage.getInsurerDownloadMessageRel(), 70);
			assertThat(policyDownloadPage.getInsurerDownloadMessageRel().isDisplayed(),equalTo(true));
			return true;
		}catch (Exception e){
			return false;
		}
	}

	public void enterPrevTpAddress(String tpAddress){
		waitElement(proposalPage.getPrevTpAddress(), 30);
		assertThat(proposalPage.getPrevTpAddress().isDisplayed(), equalTo(true));
		proposalPage.getPrevTpAddress().sendKeys(tpAddress);
		logger.info("tpAddress entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "tpAddress entered successfully");
	}

	public void enterPrevPolicyClaim(String claim){
		waitElement(proposalPage.getPervClaims(), 30);
		assertThat(proposalPage.getPervClaims().isDisplayed(), equalTo(true));
		proposalPage.getPervClaims().sendKeys(claim);
		logger.info("pervClaims entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "pervClaims entered successfully");
	}

	public void enterPrevTpTenure(String tenure){
		waitElement(proposalPage.getPrevTenure(), 30);
		assertThat(proposalPage.getPrevTenure().isDisplayed(), equalTo(true));
		proposalPage.getPrevTenure().sendKeys(tenure);
		logger.info("pervClaims entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "pervClaims entered successfully");
	}

	public void browserProceedForNotSecureLink(){
		waitElement(paymentPage.getBrowserAdvanced(), 30);
		assertThat(paymentPage.getBrowserAdvanced().isDisplayed(), equalTo(true));
		paymentPage.getBrowserAdvanced().click();
		waitElement(paymentPage.getBrowserProceed(), 30);
		assertThat(paymentPage.getBrowserProceed().isDisplayed(), equalTo(true));
		paymentPage.getBrowserProceed().click();
		logger.info("click on advanced and proceed successfully for not secure link");
		ExtentTestManager.getTest().log(LogStatus.PASS, "click on advanced and proceed successfully for not secure link");
	}


	public void proceedWithEmailNPhone(String Phone,String email){
		waitFor(1);
		waitElement(paymentPage.getPhoneIl(), 30);
		assertThat(paymentPage.getPhoneIl().isDisplayed(), equalTo(true));
		paymentPage.getPhoneIl().sendKeys(Phone);
		assertThat(paymentPage.getEmail().isDisplayed(), equalTo(true));
		paymentPage.getEmail().sendKeys(email);
		assertThat(paymentPage.getProceedIl().isDisplayed(), equalTo(true));
		paymentPage.getProceedIl().click();
		logger.info("email and phone number entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "email and phone number entered successfully");
	}


	public void enterIciciPaymentDetailsAndProceed(String Card,String cvv,String MMYY,String nameOnCard){
		waitFor(1);
		proceedWithEmailNPhone("6367357113","ensuredit@gmail.com");
		waitFor(1);
		waitElement(paymentPage.getCardIl(), 30);
		assertThat(paymentPage.getCardIl().isDisplayed(), equalTo(true));
		paymentPage.getCardIl().click();
		waitFor(1);
		waitElement(paymentPage.getCard_numberIl(), 30);
		assertThat(paymentPage.getCard_numberIl().isDisplayed(), equalTo(true));
		paymentPage.getCard_numberIl().sendKeys(Card);
		assertThat(paymentPage.getCard_expiryIl().isDisplayed(), equalTo(true));
		paymentPage.getCard_expiryIl().sendKeys(MMYY);
		assertThat(paymentPage.getCard_nameIl().isDisplayed(), equalTo(true));
		paymentPage.getCard_nameIl().sendKeys(nameOnCard);
		assertThat(paymentPage.getCard_cvvIl().isDisplayed(), equalTo(true));
		paymentPage.getCard_cvvIl().sendKeys(cvv);
		waitElement(paymentPage.getProceedIl(), 5);
		assertThat(paymentPage.getProceedIl().isDisplayed(), equalTo(true));
		paymentPage.getProceedIl().click();
		waitFor(2);
		String parentWindow = driver.getWindowHandle();
		switchToChildWindow();
		waitElement(paymentPage.getSuccessIL(), 5);
		assertThat(paymentPage.getSuccessIL().isDisplayed(), equalTo(true));
		paymentPage.getSuccessIL().click();
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
		waitElement(paymentPage.getCardNumDigit(), 10);
		assertThat(paymentPage.getCardNumDigit().isDisplayed(), equalTo(true));
		paymentPage.getCardNumDigit().sendKeys(cardNum);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("name_on_card_iframe")));
		waitFor(1);
		waitElement(paymentPage.getNameOnCardDigit(), 7);
		assertThat(paymentPage.getNameOnCardDigit().isDisplayed(), equalTo(true));
		paymentPage.getNameOnCardDigit().sendKeys(nameOnCard);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("card_exp_month_iframe")));
		waitFor(1);
		waitElement(paymentPage.getExpMonthDigit(), 8);
		assertThat(paymentPage.getExpMonthDigit().isDisplayed(), equalTo(true));
		paymentPage.getExpMonthDigit().sendKeys(mm);
		waitFor(1);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("card_exp_year_iframe")));
		waitFor(1);
		waitElement(paymentPage.getExpYearDigit(), 8);
		assertThat(paymentPage.getExpYearDigit().isDisplayed(), equalTo(true));
		paymentPage.getExpYearDigit().sendKeys(yy);
		waitFor(1);
		driver.switchTo().defaultContent();
		waitFor(1);
		driver.switchTo().frame(driver.findElement(By.className("security_code_iframe")));
		waitFor(1);
		waitElement(paymentPage.getCvvDigit(), 7);
		assertThat(paymentPage.getCvvDigit().isDisplayed(), equalTo(true));
		paymentPage.getCvvDigit().sendKeys(cvv);
		waitFor(1);
		driver.switchTo().defaultContent();
		waitFor(1);
		waitElement(paymentPage.getProceedFuture(), 8);
		assertThat(paymentPage.getProceedFuture().isDisplayed(), equalTo(true));
		waitFor(1);
		paymentPage.getProceedFuture().click();
		waitElement(paymentPage.getOtpFuture(), 8);
		assertThat(paymentPage.getOtpFuture().isDisplayed(), equalTo(true));
		paymentPage.getOtpFuture().sendKeys("123456");
		waitElement(paymentPage.getPayFuture(), 8);
		assertThat(paymentPage.getPayFuture().isDisplayed(), equalTo(true));
		paymentPage.getPayFuture().click();
		logger.info("Digit payment Details entered successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit payment Details entered successfully");
		logger.info("Digit payment completed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Digit payment completed successfully");

	}

	public void policyDownloadMessage(){
		waitElement(policyDownloadPage.getInsurerDownloadMessage(), 10);
		assertThat(policyDownloadPage.getInsurerDownloadMessage().isDisplayed(),equalTo(true));
		if(policyDownloadPage.getInsurerDownloadMessage().getText().contains("hour")){
			assertThat(policyDownloadPage.getInsurerDownloadMessage().getText(),equalTo("Insurance Co. did not provide the policy PDF. It usually takes 1 hour. Please check after 1 hour in Earnings section."));
		}
		else if (policyDownloadPage.getInsurerDownloadMessage().getText().contains("support")){
		assertThat(policyDownloadPage.getInsurerDownloadMessage().getText(),equalTo("Insurance Company did not provide the Policy PDF. Please contact support to get the Policy PDF."));
		}else {
			assertThat(policyDownloadPage.getInsurerDownloadMessageRel().getText(),equalTo("Policy download already in process"));
		}
		logger.info("Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessage().getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessage().getText());
	}

	public void policyDownloadMessageReliance(){
		waitElement(policyDownloadPage.getInsurerDownloadMessageRel(), 70);
		assertThat(policyDownloadPage.getInsurerDownloadMessageRel().isDisplayed(),equalTo(true));
		assertThat(policyDownloadPage.getInsurerDownloadMessageRel().getText(),equalTo("Policy download already in process"));
		logger.info("Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessageRel().getText());
		ExtentTestManager.getTest().log(LogStatus.PASS, "Insurer download message displaying as "+policyDownloadPage.getInsurerDownloadMessageRel().getText());
	}


	public void enterAppointeeName(String aName){
		waitElement(proposalPage.getAppointeeName(), 70);
		assertThat(proposalPage.getAppointeeName().isDisplayed(),equalTo(true));
		proposalPage.getAppointeeName().sendKeys(aName);
		logger.info("Appointee Name entered successfuly");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Appointee Name entered successfuly");
	}

	public void enterePreviousInsurerPincode(String phone){
		waitElement(proposalPage.getPreviousInsurerPincode(), 3);
		assertThat(proposalPage.getPreviousInsurerPincode().isDisplayed(), equalTo(true));
		proposalPage.getPreviousInsurerPincode().sendKeys(phone);
		logger.info("enter previousInsurerPincode successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "enter previousInsurerPincode successfully");
	}

	public void clickOnSendAnyway(){
		waitElement(paymentPage.getSendAnyway(),30);
		assertThat(paymentPage.getSendAnyway().isDisplayed(), equalTo(true));
		paymentPage.getSendAnyway().click();
	}

	public void isFutureGeneralPaymentPageDisplayed(){
		waitFor(1);
		waitElement(paymentPage.getLogoFuture(), 30);
		assertThat(paymentPage.getLogoFuture().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getCardsFuture().isDisplayed(), equalTo(true));
		assertThat(paymentPage.getChoosePaymentOption().isDisplayed(), equalTo(true));
		logger.info("FutureGeneral PaymentPage verified successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "FutureGeneral PaymentPage verified successfully");
	}

	public void enterFuturePaymentDetailsAndProceed(String cardNum,String expMMYY,String cvv,String cardHoldername){
		paymentPage.getCardsFuture().click();
		waitElement(paymentPage.getCardNumberFuture(),12);
		assertThat(paymentPage.getCardNumberFuture().isDisplayed(), equalTo(true));
		paymentPage.getCardNumberFuture().sendKeys(cardNum);
		waitElement(paymentPage.getCardExpiryFuture(),3);
		assertThat(paymentPage.getCardExpiryFuture().isDisplayed(), equalTo(true));
		paymentPage.getCardExpiryFuture().sendKeys(expMMYY);
		waitElement(paymentPage.getCardCvvFuture(),3);
		assertThat(paymentPage.getCardCvvFuture().isDisplayed(), equalTo(true));
		paymentPage.getCardCvvFuture().sendKeys(cvv);
		waitElement(paymentPage.getCardOwnerNameFuture(),3);
		assertThat(paymentPage.getCardOwnerNameFuture().isDisplayed(), equalTo(true));
		paymentPage.getCardOwnerNameFuture().sendKeys(cardHoldername);
		waitElement(paymentPage.getProceedFuture(),3);
		assertThat(paymentPage.getProceedFuture().isDisplayed(), equalTo(true));
		paymentPage.getProceedFuture().click();
		waitFor(1);
		waitElement(paymentPage.getOtpFuture(),13);
		assertThat(paymentPage.getOtpFuture().isDisplayed(), equalTo(true));
		paymentPage.getOtpFuture().sendKeys("123456");
		waitFor(1);
		waitElement(paymentPage.getPayFuture(),3);
		assertThat(paymentPage.getPayFuture().isDisplayed(), equalTo(true));
		paymentPage.getPayFuture().click();
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
				if (proposalPage.getReviewNsubmit().isDisplayed()){
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
			waitElement(policyDownloadPage.getPleaseWait(), 3);
			assertThat(policyDownloadPage.getPleaseWait().isDisplayed(),equalTo(true));
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
