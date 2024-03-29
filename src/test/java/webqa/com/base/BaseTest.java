package webqa.com.base;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import webqa.com.pageScreen.Website;
import webqa.com.pageScreen.HomePage;
import webqa.com.pageScreen.LoginPage;
import webqa.com.report.ExtentTestManager;
import webqa.com.utilities.ReadConfig;
import webqa.com.utilities.ScreenUtil;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BaseTest {

	ReadConfig readconfig = new ReadConfig();

	public String baseURL = readconfig.getApplicationURL();
	public String salesURL = readconfig.getSalesLoginUrl();
	public String username = readconfig.getUsername();
	public String password = readconfig.getPassword();
	public static WebDriver driver;

	protected static HomePage homePage;
	protected static LoginPage loginPage;
	protected static Website website;


	public static Logger logger = LogManager.getLogger(BaseTest.class);

	@Parameters("browser")
	@BeforeMethod(alwaysRun = true)
	public void setup(String br) {

		logger = Logger.getLogger("Test");
		PropertyConfigurator.configure("./src/main/resources/log4j.properties");
		logger.info("---------------- Start Logging ----------------");
		logger.info("BaseBrowserClass: Setting up web browser");
		if (br.equals("chrome")) {
			io.github.bonigarcia.wdm.WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--incognito");
			options.addArguments("window-size= 1400,800");
//			options.addArguments("---headless");
			driver = new ChromeDriver(options);
		} else if (br.equals("firefox")) {
			io.github.bonigarcia.wdm.WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
		} else if (br.equals("safari")) {
			io.github.bonigarcia.wdm.WebDriverManager.safaridriver().setup();
			driver = new SafariDriver();
		}

		homePage = new HomePage(driver);
		loginPage = new LoginPage(driver);
		website = new Website(driver);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

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
	public void waitElement(WebElement element,int timer){
		waitForElement(element,Duration.ofSeconds(timer));
	}

	public void waitForElement(WebElement element, Duration timer) {
		WebDriverWait wait = new WebDriverWait(driver, timer);
		wait.until(ExpectedConditions.visibilityOf(element));
	}

	public void waitElementForClickable(WebElement element, Duration timer) {
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
	public void scrollToText(String text){
		WebElement ele=driver.findElement(By.xpath("//*[text()='"+text+"']"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", ele);
		waitFor(2);
	}

	public void clickByCoordinate(int xCoordinate,int YCoordinate) {
		Actions action = new Actions(driver);
		action.moveByOffset(xCoordinate,YCoordinate).doubleClick().perform();

	}

	public void scrollToButtom(){
		waitFor(1);
//		((JavascriptExecutor) driver)
//				.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		driver.findElement(By.tagName("body")).sendKeys(Keys.END);
		waitFor(1);
	}


	public void scrollToTop()  {
		waitFor(1);
		driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
		waitFor(1);
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

	public void switchToTab(int i ) {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		waitFor(1);
			driver.switchTo().window(tabs.get(i));
		logger.info("parent window selected successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "parent window selected successfully");
	}

	public void launchUrl(String url) {
		driver.get(url);
		logger.info("url "+url+" lunch successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "url "+url+" lunch successfully");
	}

	public void closeCurrentTab() {
		waitFor(1);
		driver.close();
		logger.info("current tab closed successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "current tab closed successfully");
	}

	public void actionWithText(String text) {
		waitFor(1);
		new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2))
				.ignoring(StaleElementReferenceException.class).until(new Function() {
			@Override
			public Object apply(Object arg0) {
				WebElement e = driver.findElement(By.xpath("//*[text()='"+text+"']"));
				Actions action = new Actions(driver);
				action.moveToElement(e).click().perform();
				return true;
			}
		});
	}


	public void clickOnNextButton() {
		waitElement(loginPage.getCrmNext(), 3);
		assertThat(loginPage.getCrmNext().isDisplayed(), equalTo(true));
		loginPage.getCrmNext().click();
		logger.info("Next button clicked successfully");
		ExtentTestManager.getTest().log(LogStatus.PASS, "Next button clicked successfully");
	}




















}
