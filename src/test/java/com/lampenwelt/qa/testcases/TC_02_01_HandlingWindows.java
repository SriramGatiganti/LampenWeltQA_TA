package com.lampenwelt.qa.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.pages.CartPage;
import com.lampenwelt.qa.pages.LandingPage;
import com.lampenwelt.qa.pages.TrustedShopPage;
import com.lampenwelt.qa.util.TestUtil;
import static com.lampenwelt.qa.extentreport.ExtentTestManager.reporterLog;

public class TC_02_01_HandlingWindows extends TestBase {
	LandingPage landingPage;
	CartPage cartPage;
	TestUtil testUtil;
	TrustedShopPage trustedShopPage;
	String sheetName = "add_cart";
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/lampenwelt/qa/testdata/TestData.xlsx";

	@BeforeMethod
	@Parameters({ "browser", "platform", "version" })
	public void setUp(String browserName, String platformName, String versionName, Method name)
			throws InterruptedException, MalformedURLException {
		StartBrowser(browserName, platformName, versionName, name);
		log.info("****************************** Start settingup the browser  *****************************************");
		landingPage = new LandingPage();
		cartPage = new CartPage();
		testUtil = new TestUtil();
		trustedShopPage = new TrustedShopPage();

	}

	@DataProvider
	public Object[][] getArticleTestData() throws Exception {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	@Test(priority = 1, enabled = false)
	public void HandleSingleTabbenWindow() throws InterruptedException {
		log.info("****************************** starting HandleSingleTabbenWindow test case *****************************************");

		// Select skip signin
		driver.findElement(By.cssSelector("#btn2")).click();

		// Swicth to windows
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		// Thread.sleep(1000);
		driver.findElement(
				By.cssSelector("li.dropdown:nth-child(4) > ul:nth-child(3) > li:nth-child(2) > a:nth-child(1)"))
				.click();

		int size = driver.findElements(By.cssSelector("li.active:nth-child(1) > a:nth-child(1)")).size();
		if (size == 0) {
			TestUtil.switchToFrame("aswift_2");
			TestUtil.switchToFrame("ad_iframe");
			driver.findElement(By.id("dismiss-button")).click();
			driver.switchTo().defaultContent();
		}
		reporterLog("Verify the switch between the wondows");
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		System.out.println("Window name before switching--" + driver.getTitle());
		driver.findElement(By.cssSelector("button.btn-info:nth-child(1)")).click();
		TestUtil.switchToWindow(1);
		reporterLog("Verify the switch to first window");
		System.out.println("Window name after switching to chaild--" + driver.getTitle());
		TestUtil.switchToWindow(0);
		System.out.println("Window name after switching to parent--" + driver.getTitle());
		reporterLog("Verify the switch back to default window");
		log.info("****************************** Ending HandleSingleTabbenWindow test case *****************************************");

	}

	@Test(priority = 2, enabled = false)
	public void HandleSeparateWindow() throws InterruptedException {
		
		log.info("****************************** Staring the  HandleSeparateWindow test case *****************************************");

		// Select skip signin
		driver.findElement(By.cssSelector("#btn2")).click();

		// Swicth to windows
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		Thread.sleep(1000);
		driver.findElement(
				By.cssSelector("li.dropdown:nth-child(4) > ul:nth-child(3) > li:nth-child(2) > a:nth-child(1)"))
				.click();
		int size = driver.findElements(By.cssSelector("li.active:nth-child(1) > a:nth-child(1)")).size();
		if (size == 0) {
			TestUtil.switchToFrame("aswift_2");
			TestUtil.switchToFrame("ad_iframe");
			driver.findElement(By.id("dismiss-button")).click();
			driver.switchTo().defaultContent();
		}
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		System.out.println("Window name before switching--" + driver.getTitle());
		driver.findElement(By.cssSelector("ul.nav-tabs > li:nth-child(2) > a:nth-child(1)")).click();

		driver.findElement(By.cssSelector(".btn-primary")).click();
		TestUtil.switchToWindow(1);
		System.out.println("Window name after switching to chaild--" + driver.getTitle());
		TestUtil.switchToWindow(0);
		System.out.println("Window name after switching to parent--" + driver.getTitle());

	}

	@Test(priority = 3, enabled = false)
	public void HandleSeparateMultiWindow() throws InterruptedException {

		// Select skip signin
		driver.findElement(By.cssSelector("#btn2")).click();

		// Swicth to windows
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		Thread.sleep(1000);
		driver.findElement(
				By.cssSelector("li.dropdown:nth-child(4) > ul:nth-child(3) > li:nth-child(2) > a:nth-child(1)"))
				.click();
		int size = driver.findElements(By.cssSelector("li.active:nth-child(1) > a:nth-child(1)")).size();
		if (size == 0) {
			TestUtil.switchToFrame("aswift_2");
			TestUtil.switchToFrame("ad_iframe");
			driver.findElement(By.id("dismiss-button")).click();
			driver.switchTo().defaultContent();
		}
		driver.findElement(By.cssSelector("li.dropdown:nth-child(4) > a:nth-child(1)")).click();
		System.out.println("Window name before switching--" + driver.getTitle());
		driver.findElement(By.cssSelector("ul.nav-tabs > li:nth-child(3) > a:nth-child(1)")).click();
		driver.findElement(By.cssSelector("#Multiple > button")).click();
		TestUtil.switchToWindow(1);
		System.out.println("Window name after switching to first child--" + driver.getTitle());
		TestUtil.switchToWindow(2);
		System.out.println("Window name after switching to second child--" + driver.getTitle());
		driver.findElement(By.cssSelector("#btn2")).click();
		TestUtil.switchToWindow(0);
		System.out.println("Window name after switching to parent-" + driver.getTitle());

	}

	@Test(priority = 4, enabled = true)
	public void HandleLampenWeltWindow() throws InterruptedException {
		log.info("****************************** Staring the  HandleLampenWeltWindow test case *****************************************");
		// click on trust icom
		landingPage.clickOnTrustIcon();
		Thread.sleep(1000);
		TestUtil.switchToWindow(1);
		reporterLog("Verify switch to default window");
		System.out.println("Window name after switching to trusted sites--" + driver.getTitle());
		Thread.sleep(1000);
		trustedShopPage.clickZumOnline();
		reporterLog("Verify switch to ZumOnline");
		Thread.sleep(1000);
		TestUtil.switchToWindow(0);
		System.out.println("Window name after switching back to lampenWelt--" + driver.getTitle());
		reporterLog("Verify switch back to default window");
		log.info("****************************** Ending the  HandleLampenWeltWindow test case *****************************************");
	}

	@Test(priority = 5, enabled = true)
	public void HandleLampenWeltWindowWithTitle() throws InterruptedException, IOException {
		
		log.info("****************************** Start the  HandleLampenWeltWindowWithTitle test case *****************************************");

		// click on trust icon
		landingPage.clickOnTrustIcon();
		Thread.sleep(1000);
		TestUtil.switchToWindowTitle("Bewertungen zu lampenwelt.de | Lesen Sie 94.222 Bewertungen zu lampenwelt.de");
		reporterLog(
				"Verify switchwitch to' Bewertungen zu lampenwelt.de | Lesen Sie 94.222 Bewertungen zu lampenwelt.de'");

		System.out.println("Window name after switching to trusted sites--" + driver.getTitle());
		// Thread.sleep(1000);
		trustedShopPage.clickZumOnline();
		String LampenTitle = trustedShopPage.getLampenTextTitle();

		if (LampenTitle.equalsIgnoreCase("Bewertungen für lampenwelt.de")) {
			String strRating = trustedShopPage.getReviewRating();
			System.out.println("LampenWelt Rating---" + strRating);
			TestUtil.setData(sheetName, 18, strRating);
		}

		// trustedShopPage.clickZumOnline();
		TestUtil.switchToWindowTitle("Lampen & Leuchten für Ihr Zuhause | Lampenwelt.de");
		Thread.sleep(1000);
		System.out.println("Window name after switching back to lampenWelt--" + driver.getTitle());
		int strFrameCount = TestUtil.findtheNumberOfFrames();
		System.out.println("Number of frames on lampenWelt page--" + strFrameCount);
		Assert.assertEquals(driver.getTitle(), "Lampen & Leuchten für Ihr Zuhause | Lampenwelt.de");
		reporterLog("Verify the title after switching back" + driver.getTitle());
		log.info("****************************** Ending the  HandleLampenWeltWindowWithTitle test case *****************************************");
		
	}

	@AfterMethod
	public void tearDown() {
		CloseDriver();
	}

}
