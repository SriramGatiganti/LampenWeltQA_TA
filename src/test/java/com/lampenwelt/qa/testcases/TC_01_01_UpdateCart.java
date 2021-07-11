package com.lampenwelt.qa.testcases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import org.testng.Assert;

import org.testng.annotations.AfterMethod;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.lampenwelt.qa.util.TestUtil;

import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.pages.AccountOverviewPage;
import com.lampenwelt.qa.pages.CartPage;
import com.lampenwelt.qa.pages.LandingPage;
import static com.lampenwelt.qa.extentreport.ExtentTestManager.reporterLog;

public class TC_01_01_UpdateCart extends TestBase {

	LandingPage landingPage;	
	AccountOverviewPage accountInfoPage;
	CartPage cartPage;
	TestUtil testUtil;
	String sheetName = "update_cart";

	@BeforeMethod
	@Parameters({ "browser", "platform", "version" })
	public void setUp(String browserName, String platformName, String versionName, Method name)
			throws InterruptedException, MalformedURLException {
		StartBrowser(browserName, platformName, versionName, name);
		log.info("****************************** Start settingup the browser  *****************************************");
		reporterLog("Open browser with with the app");
		landingPage = new LandingPage();
		accountInfoPage = new AccountOverviewPage();
		cartPage = new CartPage();
		testUtil = new TestUtil();

	}

	@DataProvider
	public Object[][] getArticleTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	// *****************************************************************************************************************
	//
	// * Project :Lampenwelt QA
	// * TestCase:Verification of price validation by updating the quantity in the
	// cart
	// * Author : Sriram Gatiganti
	//
	// *****************************************************************************************************************
	/*
	 * Test Steps : 1.Verify I am on the landing/home page of the shop portal
	 * 2.Accept all cookies if present 3.Search for first article and add to cart
	 * 4.Search for second article and add to cart 5.Search for third article and
	 * add to cart 6.Verify the article count at cart label 7.Go to the cart page
	 * 8.Verify the Item price expected before cart update 9.Verify the Sub-total as
	 * expected before cart update 10.Verify the Shipping cost as expected before
	 * quantity update 11.Verify the Total cost as expected before cart update
	 * 12.Update the quantity of my first product 13.Verify the Item price as
	 * expected after cart update 14.Verify the Sub-total as expected after cart
	 * update 15.Verify the Shipping cost as expected after cart update 16.Verify
	 * the Total cost as expected after the cart update
	 */
	@Test(priority = 1, dataProvider = "getArticleTestData", enabled = true)
	public void UpdateItemQty(String Pagetitle, String ArticleNumber1, String ArticleName1, String ArticleNumber2,
			String ArticleName2, String ArticleNumber3, String ArticleName3, String ArtcleCount,
			String ItemPriceBeforeUpdate, String SubtotalBeforeUpdate, String ShippingCostBeforeUpdate,
			String TotalAmountBeforeUpdate, String UpdateQty, String ItemPriceAfterUpdate, String SubtotalAfterUpdate,
			String ShippingCostAfterUpdate, String TotalAmountAfterUpdate, String Status, String ContactInfo)
			throws InterruptedException, IOException {

		log.info("****************************** starting price update test case *****************************************");
		// 1.Verify I am on the landing/home page of the lampenwelt shop portal
		String homePageTitle = landingPage.getHomePageTitle();
		reporterLog("Verify the page title"+homePageTitle);

		// 2.Accept all cookies if present
		boolean blnCookieVisisble = landingPage.cookieVisisble();
		if (blnCookieVisisble == true) {
			landingPage.acceptCookies();
			System.out.println("Cookies bar presented and accepted----->" + blnCookieVisisble);
		} else if (blnCookieVisisble == false) {
			System.out.println("Cookie bar not presented----->" + blnCookieVisisble);
		}
		reporterLog("Verify the cookies bar presented "+blnCookieVisisble);

		// 3.Search for first article and add to cart
		landingPage.searchMyProduct(ArticleNumber1);
		String strMyFirstArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMyFirstArticle, ArticleName1);
		reporterLog("Verify the first artical number");
		landingPage.clickAddToCart();
		

		// 4.Search for second article and add to cart
		landingPage.searchMyProduct(ArticleNumber2);
		String strMySecondArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMySecondArticle, ArticleName2);
		reporterLog("Verify the second artical number");
		landingPage.clickAddToCart();

		// 5.Search for third article and add to cart
		landingPage.searchMyProduct(ArticleNumber3);
		String strMyThirdArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMyThirdArticle, ArticleName3);
		reporterLog("Verify the third artical number");
		landingPage.clickAddToCart();
		Thread.sleep(1000);

		// 6.Verify the article count at cart label
		String strArtcleCount = landingPage.getCartArticleCount();
		Assert.assertEquals(strArtcleCount, ArtcleCount);
		reporterLog("Verify the artical count");

		// 7.Go to the cart page
		landingPage.clickOnCart();

		// 8.Verify the Item price expected before cart update
		String strItemPriceBfrUpdate = cartPage.getItemPrice();
		System.out.println("Item price before upadte qty from cart---->" + strItemPriceBfrUpdate);
		Assert.assertEquals(strItemPriceBfrUpdate, ItemPriceBeforeUpdate);
		reporterLog("Verify the Item price before update");

		// 9.Verify the Subtotal as expected before cart update
		String strNetAmtBfrUpdate = cartPage.getNetCost();
		System.out.println("Subtotal amount before upadte qty from cart---->" + strNetAmtBfrUpdate);
		Assert.assertEquals(strNetAmtBfrUpdate, SubtotalBeforeUpdate);
		reporterLog("Verify the Item sub total before update");

		// 10.Verify the Shipping cost as expected before quantity update
		String strShippingAmtBfrUpdate = cartPage.getShippingCost();
		System.out.println("Shipping amount before update qty from cart----->" + strShippingAmtBfrUpdate);
		Assert.assertEquals(strShippingAmtBfrUpdate, ShippingCostBeforeUpdate);
		reporterLog("Verify the shipping before update");

		// 11.Verify the Total cost as expected before cart update
		String strTotalAmtBfrUpdate = cartPage.getTotalCost();
		System.out.println("Total amount before update qty from cart----->" + strTotalAmtBfrUpdate);
		Assert.assertEquals(strTotalAmtBfrUpdate, TotalAmountBeforeUpdate);
		reporterLog("Verify the total amount before update");

		// 12.Update the quantity of my first product
		cartPage.selectQty(UpdateQty);

		// 13.Verify the Item price as expected after cart update
		String strItmPriceAftUpdate = cartPage.getItemPrice();
		System.out.println("Item proce after upadte qty from cart----->" + strItmPriceAftUpdate);
		Assert.assertEquals(strItmPriceAftUpdate, ItemPriceAfterUpdate);
		reporterLog("Verify the Item price after  update");

		// 14.Verify the Sub-total as expected after cart update
		String strNetAmtAftUpdate = cartPage.getNetCost();
		System.out.println("Subtotal amount after upadte qty from cart----->" + strNetAmtAftUpdate);
		Assert.assertEquals(strNetAmtAftUpdate, SubtotalAfterUpdate);
		reporterLog("Verify the Item sub total after  update");

		// 15.Verify the Shipping cost as expected after cart update
		String strShippingAmtAftUpdate = cartPage.getShippingCost();
		System.out.println("Shipping amount after update qty from cart------>" + strShippingAmtAftUpdate);
		Assert.assertEquals(strShippingAmtAftUpdate, ShippingCostAfterUpdate);
		reporterLog("Verify the Item shipping amount after  update");

		// 16.Verify the Total cost as expected after cart update
		String strTotalAmtAftUpdate = cartPage.getTotalCost();
		System.out.println("Total amount after update qty from cart------->" + strTotalAmtAftUpdate);
		reporterLog("Verify the Item total amount after  update");

		// 17.Set the status in the excel file
		if ((strTotalAmtAftUpdate).equals(TotalAmountAfterUpdate)) {
			TestUtil.setData(sheetName, 17, "PASS");
		} else {
			TestUtil.setData(sheetName, 17, "FAIL");
		}
		Assert.assertEquals(strTotalAmtAftUpdate, TotalAmountAfterUpdate);
		log.info("****************************** ending the price update test case *****************************************");

	}

	@Test(priority = 1, enabled = false)
	public void MouseOverEvents() throws Exception {

		// 1.Verify I am on the landing/home page of the lampenwelt shop portal
		String homePageTitle = landingPage.getHomePageTitle();
		reporterLog("Verify the homepage totle"+homePageTitle);

		// 2.Accept all cookies if present
		boolean blnCookieVisisble = landingPage.cookieVisisble();
		if (blnCookieVisisble == true) {
			landingPage.acceptCookies();
		} else if (blnCookieVisisble == false) {
			System.out.println("Cookie bar not presented----->" + blnCookieVisisble);
		}
		reporterLog("Verify the cookies bar presented "+blnCookieVisisble);

		// Click on mover over menu
		landingPage.moveMouseOverMenuItems("SALE %", "Büro & Gewerbe");
		Thread.sleep(1000);
		landingPage.moveMouseOverMenuItems("Smart Home", "Q-Smart-Home");
		Thread.sleep(1000);
		landingPage.moveMouseOverMenuItems("", "Vorschaltgeräte");
		Thread.sleep(10000);
	}

	@Test(priority = 2, dataProvider = "getArticleTestData", enabled = false)
	public void SignIn(String Pagetitle, String ArticleNumber1, String ArticleName1, String ArticleNumber2,
			String ArticleName2, String ArticleNumber3, String ArticleName3, String ArtcleCount,
			String ItemPriceBeforeUpdate, String SubtotalBeforeUpdate, String ShippingCostBeforeUpdate,
			String TotalAmountBeforeUpdate, String UpdateQty, String ItemPriceAfterUpdate, String SubtotalAfterUpdate,
			String ShippingCostAfterUpdate, String TotalAmountAfterUpdate, String Status, String ContactInfo)
			throws InterruptedException, IOException {

		// 1.Verify I am on the landing/home page of the lampenwelt shop portal
		String homePageTitle = landingPage.getHomePageTitle();
		reporterLog("Verify the homepage totle"+homePageTitle);

		// 2.Accept all cookies if present
		boolean blnCookieVisisble = landingPage.cookieVisisble();
		if (blnCookieVisisble == true) {
			landingPage.acceptCookies();
			System.out.println("Cookies bar presented and accepted----->" + blnCookieVisisble);
		} else if (blnCookieVisisble == false) {
			System.out.println("Cookie bar not presented----->" + blnCookieVisisble);
		}
		reporterLog("Verify the cookies bar presented "+blnCookieVisisble);

		// Click on mover over menu
		landingPage.signIn("sriram.gatiganti@gmail.com", "Rit06ec18@1");
		String strContactInfo = accountInfoPage.getContactInformation();

		// TestUtil.setData(sheetName, 18, strContactInfo);
		reporterLog("Conact Info is ---" + strContactInfo);
		reporterLog("Conact Info from Excel ---" + ContactInfo);
		// Assert.assertEquals(ContactInfo,strContactInfo);
		String StrBillingAddress = accountInfoPage.getBillingAddress();
		reporterLog("Billing Info is ---" + StrBillingAddress);
		String StrDeliveryAddress = accountInfoPage.getShippingAddress();
		reporterLog("Delivery Info is ---" + StrDeliveryAddress);

	}
	
	@Test(priority = 1, enabled = false)
	public void RegistrationWithDaePiker() throws Exception {

		// 1.Verify I am on the landing/home page of the lampenwelt shop portal
		String homePageTitle = landingPage.getHomePageTitle();
		reporterLog("Verify the homepage totle"+homePageTitle);

		// 2.Accept all cookies if present
		boolean blnCookieVisisble = landingPage.cookieVisisble();
		if (blnCookieVisisble == true) {
			landingPage.acceptCookies();
			System.out.println("Cookies bar presented and accepted----->" + blnCookieVisisble);
		} else if (blnCookieVisisble == false) {
			System.out.println("Cookie bar not presented----->" + blnCookieVisisble);
		}
		reporterLog("Verify the cookies bar presented "+blnCookieVisisble);

		//register new user
		landingPage.registerNewUser("17/Jun/1989");
	}

	@AfterMethod
	public void tearDown() {
		CloseDriver();
	}

}
