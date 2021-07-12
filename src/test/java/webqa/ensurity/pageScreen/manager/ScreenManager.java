package webqa.ensurity.pageScreen.manager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;


/**
 * 
 *
 */

public abstract class ScreenManager {


	public ScreenManager(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 * @return title of the screen
	 */
	protected abstract String getTitle();

}
