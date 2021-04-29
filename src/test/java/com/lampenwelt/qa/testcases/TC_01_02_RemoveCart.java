package com.lampenwelt.qa.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.pages.CartPage;
import com.lampenwelt.qa.pages.LandingPage;
import com.lampenwelt.qa.util.TestUtil;

public class TC_01_02_RemoveCart extends TestBase {
	LandingPage landingPage;
	CartPage cartPage;
	TestUtil testUtil;
	String sheetName = "remove_cart";

	@BeforeMethod
	public void setUp() throws InterruptedException {
		StartBrowser();
		landingPage = new LandingPage();
		cartPage = new CartPage();
	}

	@DataProvider
	public Object[][] getArticleTestData() {
		Object data[][] = TestUtil.getTestData(sheetName);
		return data;
	}

	// *****************************************************************************************************************
	//
	// * Project : Lampenwelt QA
	// * TestCase: Verification of price validation by removing the product from
	// cart
	// * Author : Sriram Gatiganti
	//
	// *****************************************************************************************************************
	/*
	 * Test Steps : 1.Verify I am on the landing/home page of the shop portal
	 * 2.Accept all cookies if present 3.Search for first article and add to cart
	 * 4.Search for second article and add to cart 5.Search for third article and
	 * add to cart 6.Verify the article count at cart label 7.Go to the cart page
	 * 8.Verify the Sub-total as expected before remove the item 9.Verify the
	 * Shipping cost as expected before remove the item 10.Verify the Total cost as
	 * expected before remove the item 11.Remove the first item from the cart
	 * 12.Verify the Sub-total as expected after removing the article 13.Verify the
	 * Shipping cost as expected after cart update 14.Verify the Total cost as
	 * expected after cart update
	 */
	@Test(priority = 1, dataProvider = "getArticleTestData")
	public void RemoveItemFromCart(String Pagetitle, String ArticleNumber1, String ArticleName1, String ArticleNumber2,
			String ArticleName2, String ArticleNumber3, String ArticleName3, String ArtcleCount,
			String SubtotalBeforeRemove, String ShippingCostBeforeRemove, String TotalAmountBeforeRemove,
			String SubtotalAfterRemove, String ShippingCostAfterRemove, String TotalAmountAfterRemove)
			throws InterruptedException {

		// 1.Verify I am on the landing/home page of the shop portal
		String homePageTitle = landingPage.getHomePageTitle();
		Assert.assertEquals(homePageTitle, Pagetitle);

		// 2.Accept all cookies if present
		boolean blnCookieVisisble = landingPage.cookieVisisble();
		if (blnCookieVisisble == true) {
			landingPage.acceptCookies();
			System.out.println("Cookies bar presented and accepted----->" + blnCookieVisisble);
		} else if (blnCookieVisisble == false) {
			System.out.println("Cookie bar not presented----->" + blnCookieVisisble);
		}

		// 3.Search for first article and add to cart
		landingPage.searchMyProduct(ArticleNumber1);
		String strMyFirstArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMyFirstArticle, ArticleName1);
		landingPage.clickAddToCart();
		Thread.sleep(1000);

		// 4.Search for second article and add to cart
		landingPage.searchMyProduct(ArticleNumber2);
		String strMySecondArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMySecondArticle, ArticleName2);
		landingPage.clickAddToCart();
		Thread.sleep(1000);

		// 5.Search for third article and add to cart
		landingPage.searchMyProduct(ArticleNumber3);
		String strMyThirdArticle = landingPage.getArticleTitle();
		Assert.assertEquals(strMyThirdArticle, ArticleName3);
		landingPage.clickAddToCart();
		Thread.sleep(1000);

		// 6.Verify the article count at cart label
		String strArtcleCount = landingPage.getCartArticleCount();
		Assert.assertEquals(strArtcleCount, ArtcleCount);

		// 7.Go to the cart page
		landingPage.clickOnCart();

		// 8.Verify the Sub-total as expected before remove the item
		String strNetAmtBfrRmvItem = cartPage.getNetCost();
		System.out.println("Subtotal amount before removing item from cart---->" + strNetAmtBfrRmvItem);
		Assert.assertEquals(strNetAmtBfrRmvItem, SubtotalBeforeRemove);

		// 9.Verify the Shipping cost as expected before remove the item
		String strShippingAmtBfrRmvItem = cartPage.getShippingCost();
		System.out.println("Shipping amount before removing item from cart----->" + strShippingAmtBfrRmvItem);
		Assert.assertEquals(strShippingAmtBfrRmvItem, ShippingCostBeforeRemove);

		// 10.Verify the Total cost as expected before remove the item
		String strTotalAmtBfrRmvItem = cartPage.getTotalCost();
		System.out.println("Total amount before removing item from cart----->" + strTotalAmtBfrRmvItem);
		Assert.assertEquals(strTotalAmtBfrRmvItem, TotalAmountBeforeRemove);

		// 11.Remove the first item from the cart
		cartPage.removeFirstArticle();

		// 12.Verify the Sub-total as expected after removing the article
		String strNetAmtAftRmvItem = cartPage.getNetCost();
		System.out.println("Subtotal amount after removing the item from cart----->" + strNetAmtAftRmvItem);
		Assert.assertEquals(strNetAmtAftRmvItem, SubtotalAfterRemove);

		// 13.Verify the Shipping cost as expected after cart update
		String strShippingAmtAftRmvItem = cartPage.getShippingCost();
		System.out.println("Shipping amount after removing the item from cart------->" + strShippingAmtAftRmvItem);
		Assert.assertEquals(strShippingAmtAftRmvItem, ShippingCostAfterRemove);

		// 14.Verify the Total cost as expected after cart update
		String strTotalAmtAftRmvItem = cartPage.getTotalCost();
		System.out.println("Total amount after removing the item from cart------->" + strTotalAmtAftRmvItem);
		Assert.assertEquals(strTotalAmtAftRmvItem, TotalAmountAfterRemove);
	}

	@AfterMethod
	public void tearDown() {
		CloseDriver();
	}
}
