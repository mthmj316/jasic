package de.mthoma.web.jasic.testutils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public final class SeleniumCore {

	private static final String WEB_DRIVER_FIRRFOX = "webdriver.gecko.driver";
	private static final String WEB_DRIVER_FIREFOX_PATH = "C:\\Users\\mthoma\\Documents\\SeleniumWebDriver\\geckodriver.exe";
	
	private static final String WEB_DRIVER_CHROME = "webdriver.chrome.driver";
	private static final String WEB_DRIVER_CHROME_PATH = "C:\\Users\\mthoma\\Documents\\SeleniumWebDriver\\chromedriver.exe";

	private SeleniumCore() {
	}
	
	/**
	 * Returns the web driver for Chrome.
	 * Before the driver is created it is checked if the web driver system property
	 * is set, and if not it'll set it.
	 * @return {@link WebDriver}
	 */
	public static WebDriver getChromeDriver() {
				
		setWebDriverSystemProperty(WEB_DRIVER_CHROME, WEB_DRIVER_CHROME_PATH);
		
		return new ChromeDriver();
	}

	/**
	 * Returns the web driver for Firefox (gecko).
	 * Before the driver is created it is checked if the web driver system property
	 * is set, and if not it'll set it.
	 * @return {@link WebDriver}
	 */
	public static WebDriver getFirefoxDriver() {
				
		setWebDriverSystemProperty(WEB_DRIVER_FIRRFOX, WEB_DRIVER_FIREFOX_PATH);
		
		return new FirefoxDriver();
	}

	/*
	 * Sets the web driver system property if not already done.
	 */
	private static void setWebDriverSystemProperty(String webDriver, String webDriverLocation) {
		
		if(!isWebDriverSystemPropertySet(webDriver, webDriverLocation)) {
			System.setProperty(webDriver, webDriverLocation);
		}	
	}

	/*
	 * Return true if the web driver system property is set.
	 */
	private static boolean isWebDriverSystemPropertySet(String webDriver, String webDriverLocation) {
		return webDriverLocation.equals(System.getProperty(webDriver));
	}
}
