package com.lampenwelt.qa.base;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 60;
	public static Logger log;

	String driverPath = "./Drivers\\";

	public TestBase() {
		// Configuration of Config file
		try {
			
			log = Logger.getLogger(TestBase.class);			
			prop = new Properties();
			FileInputStream ip = new FileInputStream(
					System.getProperty("user.dir") + "/src/main/java/com/lampenwelt/qa/config/config.properties");
			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// instantiation of webdriver
	public void StartBrowser(String browserName, String platformName, String versionName, Method name)
			throws InterruptedException, MalformedURLException {
		
		log.info("****************************** Setup the browser from the base class  *****************************************");
		System.out.println("Driver Started");
		String LOCALORCOULD = prop.getProperty("platform_type");
		String SL_USERNAME = prop.getProperty("username");
		String SL_ACCESS_KEY = prop.getProperty("access_key");

		if (LOCALORCOULD.equals("Local_chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver();
		} else if (LOCALORCOULD.equals("Local_firfox")) {
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();

		} else if (LOCALORCOULD.equals("Sauce_cloud")) {

			System.out.println("browser name is : " + browserName);
			String methodName = name.getName();
			String hubURL = "https://" + SL_USERNAME + ":" + SL_ACCESS_KEY
					+ "@ondemand.eu-central-1.saucelabs.com:443/wd/hub";

			if (browserName.equals("chrome")) {
				WebDriverManager.chromedriver().setup();
				// Defined mutable capabilities for Saucelab configuration
				MutableCapabilities sauceOpts = new MutableCapabilities();
				sauceOpts.setCapability("name", methodName);
				sauceOpts.setCapability("seleniumVersion", "3.141.59");
				sauceOpts.setCapability("username", SL_USERNAME);
				sauceOpts.setCapability("accessKey", SL_ACCESS_KEY);

				// Define Desired capabilities
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("sauce:options", sauceOpts);
				dc.setCapability("browserVersion", versionName);
				dc.setCapability("platformName", platformName);
				dc.setCapability("browserName", "chrome");
				driver = new RemoteWebDriver(new URL(hubURL), dc);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if (browserName.equals("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				// Defined mutable capabilities for Saucelab configuration
				MutableCapabilities sauceOpts = new MutableCapabilities();
				sauceOpts.setCapability("name", methodName);
				sauceOpts.setCapability("seleniumVersion", "3.141.59");
				sauceOpts.setCapability("username", SL_USERNAME);
				sauceOpts.setCapability("accessKey", SL_ACCESS_KEY);
				// Define Desired capabilities
				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("sauce:options", sauceOpts);
				dc.setCapability("browserVersion", versionName);
				dc.setCapability("platformName", platformName);
				dc.setCapability("browserName", "firefox");
				driver = new RemoteWebDriver(new URL(hubURL), dc);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			} else if (browserName.equals("safari")) {
				MutableCapabilities sauceOptions = new MutableCapabilities();
				SafariOptions browserOptions = new SafariOptions();
				sauceOptions.setCapability("name", methodName);
				browserOptions.setCapability("platformName", platformName);
				browserOptions.setCapability("browserVersion", versionName);
				browserOptions.setCapability("sauce:options", sauceOptions);
				driver = new RemoteWebDriver(new URL(hubURL), browserOptions);
				driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			}


		} else if (LOCALORCOULD.equals("docker")) {

			String dockerHubURL = "http://localhost:4545/wd/hub";

			// Defined desired capabilities for docker
			DesiredCapabilities cap = new DesiredCapabilities();

			if (browserName.equals("chrome")) {
				cap.setBrowserName(BrowserType.CHROME);
			} else if (browserName.equals("firefox")) {
				cap.setBrowserName(BrowserType.FIREFOX);
			}

			try {
				driver = new RemoteWebDriver(new URL(dockerHubURL), cap);
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(TestBase.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(TestBase.IMPLICIT_WAIT, TimeUnit.SECONDS);
		driver.get(prop.getProperty("url"));
	}

	// Quite webdriver
	public void CloseDriver() {
		System.out.println("Driver Closed");
		driver.manage().deleteAllCookies();
		driver.quit();
	}

}
