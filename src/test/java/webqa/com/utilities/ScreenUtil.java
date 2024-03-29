package webqa.com.utilities;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class ScreenUtil {
    private final static Logger logger = LogManager.getLogger(ScreenUtil.class);


    public static void captureScreenshot(WebDriver driver, final String path,
                                         final String methodName ) {
        TakesScreenshot scr=((TakesScreenshot)driver);
        File dsf = scr.getScreenshotAs(OutputType.FILE);
        String fileName = "Method_" + methodName + ".jpg";
        File newFile = new File(path + fileName);
        try {
            FileUtils.copyFile(dsf, newFile);
        } catch (IOException e) {
            logger.error("Error generating the screenshot", e);
        }
    }


    public static String getBase64Screenshot( WebDriver driver ) {
        String base64Screenshot = "data:image/png;base64,"
                + ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
        return base64Screenshot;
    }

    public static void takeScreenShotAsImage( WebDriver driver, String fileWithPath ) {
        TakesScreenshot scrShot = ((TakesScreenshot) driver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);
        try {
            FileUtils.copyFile(SrcFile, DestFile);
        } catch (IOException e) {
            logger.error("Error generating the screenshot", e);
            e.printStackTrace();
        }
    }


}
