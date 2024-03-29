package webqa.com.execution;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

import java.io.IOException;
import java.util.List;


public class Application {

	private final static Logger logger = LogManager.getLogger(Application.class);

	/**
	 * This will initiate test execution based on properties of device.yml &
	 * testng.xml
	 * 
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		List<XmlSuite> suites;
		String xmlSuiteFile = null;
		try {
				xmlSuiteFile = System.getProperty("xmlSuiteFile", "Testng.xml");
			
			if (StringUtils.isNotEmpty(xmlSuiteFile)) {
				suites = new Parser(xmlSuiteFile).parseToList();
				TestNG testNGRun = new TestNG();
				testNGRun.setXmlSuites(suites);
				testNGRun.run();
			} else {
				logger.error("Error starting the application. testNG.xml file is not supplied. Stopping the app.");
			}

		} catch (Exception e) {
			logger.error("Error in starting the application." +
					" please check", e);
		}
	}
}
