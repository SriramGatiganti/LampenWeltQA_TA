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

public class TC_01_03_AddCart extends TestBase {
	LandingPage landingPage;
	CartPage cartPage;
	TestUtil testUtil;
	String sheetName = "add_cart";

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
	// * Project :Lampenwelt QA
	// * TestCase:Verification of price validation by adding the new product in the cart
	// * Author : Sriram Gatiganti
	//
	// *****************************************************************************************************************
	/*
	 * Test Steps :
	 * 1.Verify I am on the landing/home page of the shop portal
	 * 2.Accept all cookies if present	
	 * 3.Search for first article and add to cart
	 * 4.Search for second article and add to cart
	 * 5.Search for third article and add to cart
	 * 6.Verify the article count at cart label
	 * 7.Go to the cart page
	 * 8.Verify the Sub-total as expected before cart update
	 * 9.Verify the Shipping cost as expected before quantity update
	 * 10.Verify the Total cost as expected before cart update
	 * 11.Add new product to the cart
	 * 12.Go to the cart page
	 * 13.Verify the article count at cart label after adding new product
	 * 14.Verify the Sub-total as expected after adding new product in the cart
	 * 15.Verify the Shipping cost as expected after after adding new product in the
	 * 16.Verify the Total cost as expected after after adding new product in the
	 */
	@Test(priority = 1, dataProvider = "getArticleTestData")
	public void AddNewItemToCard(String Pagetitle, String ArticleNumber1, String ArticleName1, String ArticleNumber2,
			String ArticleName2, String ArticleNumber3, String ArticleName3, String ArticleNumber4, String ArticleName4,
			String ArtcleCount, String UpdatedArtcleCount, String SubtotalBeforeAdd, String ShippingCostBeforeAdd,
			String TotalAmountBeforeAdd,String SubtotalAfterAdd, String ShippingCostAfterAdd,
			String TotalAmountAfterAdd) throws InterruptedException {

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

		// 8.Verify the Sub-total as expected as before add the product to cart
		String strNetAmtBfrUpdate = cartPage.getNetCost();
		System.out.println("Subtotal amount before add the product to cart---->" + strNetAmtBfrUpdate);
		Assert.assertEquals(strNetAmtBfrUpdate, SubtotalBeforeAdd);

		// 9.Verify the Shipping cost as expected before add the product to cart
		String strShippingAmtBfrUpdate = cartPage.getShippingCost();
		System.out.println("Shipping amount before add the product to cart----->" + strShippingAmtBfrUpdate);
		Assert.assertEquals(strShippingAmtBfrUpdate, ShippingCostBeforeAdd);

		// 10.Verify the Total cost as expected before add the product to cart
		String strTotalAmtBfrUpdate = cartPage.getTotalCost();
		System.out.println("Total amount before add the product to cart----->" + strTotalAmtBfrUpdate);
		Assert.assertEquals(strTotalAmtBfrUpdate, TotalAmountBeforeAdd);

		// 11.Add new product to the cart
		landingPage.searchMyProduct(ArticleNumber4);
		landingPage.clickAddToCart();
		Thread.sleep(1000);

		// 12.Go to the cart page
		landingPage.clickOnCart();
		
		// 13.Verify the article count at cart label after adding new product
		String strArtcleCount1 = landingPage.getCartArticleCount();
		Assert.assertEquals(strArtcleCount1, UpdatedArtcleCount);
			
		// 14.Verify the Sub-total as expected after adding new product in the cart
		String strNetAmtAftAdd = cartPage.getNetCost();
		System.out.println("Subtotal amount after adding the new product in cart----->" + strNetAmtAftAdd);
		Assert.assertEquals(strNetAmtAftAdd, SubtotalAfterAdd);

		// 15.Verify the Shipping cost as expected after after adding new product in the
		String strShippingAmtAftAdd = cartPage.getShippingCost();
		System.out.println("Shipping amount after adding the new product in cart------->" + strShippingAmtAftAdd);
		Assert.assertEquals(strShippingAmtAftAdd, ShippingCostAfterAdd);

		// 16.Verify the Total cost as expected after after adding new product in the
		String strTotalAmtAftAdd = cartPage.getTotalCost();
		System.out.println("Total amount adding the new product in cart------->" + strTotalAmtAftAdd);
		Assert.assertEquals(strTotalAmtAftAdd, TotalAmountAfterAdd);

	}

	@AfterMethod
	public void tearDown() {
		CloseDriver();
	}

}
