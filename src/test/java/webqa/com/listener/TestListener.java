package webqa.com.listener;

import com.google.common.collect.Lists;
import com.relevantcodes.extentreports.LogStatus;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import webqa.com.base.BaseTest;
import webqa.com.report.ExtentReportManager;
import webqa.com.report.ExtentTestManager;

import java.util.Arrays;


public class TestListener extends BaseTest implements ITestListener{

	private final static Logger logger = LogManager.getLogger(TestListener.class);
	private boolean check= true;

	@Override
	public void onTestStart(ITestResult result) {
		logger.debug("Start Testing: " + getTestMethodName(result) + Arrays.toString(result.getParameters()));
		ExtentTestManager.startTest(getTestMethodName(result)+"-"+ Arrays.toString(result.getParameters()), "");
	}
	
	private String getTestMethodName(ITestResult iTestResult) {
		String className = iTestResult.getTestClass().getName();
		className = className.substring(className.lastIndexOf('.') + 1);
		return className + " - " + iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		logger.debug("Test: " + getTestMethodName(result) + Arrays.toString(result.getParameters()) + " has succeeded");		
		ExtentTestManager.getTest().log(LogStatus.PASS, "Test " + getTestMethodName(result) + " has passed");
		ExtentTestManager.endTest();
	}

	@Override
	public void onTestFailure(ITestResult result) {
		logger.debug("Test: " + getTestMethodName(result) + Arrays.toString(result.getParameters()) + " has failed");
		ExtentTestManager.getTest().log(LogStatus.FAIL, "Test " + getTestMethodName(result) + " has failed",
				result.getThrowable());
		ExtentTestManager.endTest();
		
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		logger.debug("Test: " + getTestMethodName(result) + Arrays.toString(result.getParameters()) + " has skipped");
		ExtentTestManager.getTest().log(LogStatus.SKIP, "Test " + getTestMethodName(result) + " has skipped");
		ExtentTestManager.endTest();
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		
	}

	// Before starting all tests, below method runs
		@Override
		public void onStart(ITestContext context) {
			logger.debug("Starting Listener - context: " + context.getName());
			logger.debug("context: " + context.getName() + " has the following groups Included: ");
			Lists.newArrayList(context.getIncludedGroups()).forEach(logger::debug);
			logger.debug("context: " + context.getName() + " has the following groups Excluded: ");
			Lists.newArrayList(context.getExcludedGroups()).forEach(logger::debug);
		}

		// After ending all tests, below method runs
		@Override
		public void onFinish(ITestContext context) {
			logger.debug("Finishing Listener - context: " + context.getName());
			ExtentReportManager.getReporter().flush();
			logger.debug("ExtentReportManager has flushed, file: " + ExtentReportManager.reportPath);
		}

}
