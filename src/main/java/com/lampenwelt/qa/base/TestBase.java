package com.lampenwelt.qa.base;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class TestBase {
	public static WebDriver driver;
	public static Properties prop;
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static long IMPLICIT_WAIT = 60;
	String driverPath = "./Drivers\\";

	public TestBase() {
		// Configuration of Config file
		try {
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
	public void StartBrowser() throws InterruptedException {
		System.out.println("Driver Started");
		String browserName = prop.getProperty("browser");

		if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browserName.equals("remote")) {
			DesiredCapabilities dc = DesiredCapabilities.firefox();
			dc.setBrowserName("firefox");
			/** URL is the selenium hub URL here **/
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), dc);
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
