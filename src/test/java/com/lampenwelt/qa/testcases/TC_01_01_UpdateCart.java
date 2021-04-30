package com.lampenwelt.qa.testcases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.lampenwelt.qa.util.TestUtil;
import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.pages.CartPage;
import com.lampenwelt.qa.pages.LandingPage;

public class TC_01_01_UpdateCart extends TestBase {

	LandingPage landingPage;
	CartPage cartPage;
	TestUtil testUtil;
	String sheetName = "update_cart";
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/lampenwelt/qa/testdata/TestData.xlsx";

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
	@Test(priority = 1, dataProvider = "getArticleTestData")
	public void UpdateItemQty(String Pagetitle, String ArticleNumber1, String ArticleName1, String ArticleNumber2,
			String ArticleName2, String ArticleNumber3, String ArticleName3, String ArtcleCount,
			String ItemPriceBeforeUpdate, String SubtotalBeforeUpdate, String ShippingCostBeforeUpdate,
			String TotalAmountBeforeUpdate, String UpdateQty, String ItemPriceAfterUpdate, String SubtotalAfterUpdate,
			String ShippingCostAfterUpdate, String TotalAmountAfterUpdate) throws InterruptedException, IOException {

		// 1.Verify I am on the landing/home page of the lampenwelt shop portal
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
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

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

		// 8.Verify the Item price expected before cart update
		String strItemPriceBfrUpdate = cartPage.getItemPrice();
		System.out.println("Item price before upadte qty from cart---->" + strItemPriceBfrUpdate);
		Assert.assertEquals(strItemPriceBfrUpdate, ItemPriceBeforeUpdate);

		// 9.Verify the Subtotal as expected before cart update
		String strNetAmtBfrUpdate = cartPage.getNetCost();
		System.out.println("Subtotal amount before upadte qty from cart---->" + strNetAmtBfrUpdate);
		Assert.assertEquals(strNetAmtBfrUpdate, SubtotalBeforeUpdate);

		// 10.Verify the Shipping cost as expected before quantity update
		String strShippingAmtBfrUpdate = cartPage.getShippingCost();
		System.out.println("Shipping amount before update qty from cart----->" + strShippingAmtBfrUpdate);
		Assert.assertEquals(strShippingAmtBfrUpdate, ShippingCostBeforeUpdate);

		// 11.Verify the Total cost as expected before cart update
		String strTotalAmtBfrUpdate = cartPage.getTotalCost();
		System.out.println("Total amount before update qty from cart----->" + strTotalAmtBfrUpdate);
		Assert.assertEquals(strTotalAmtBfrUpdate, TotalAmountBeforeUpdate);

		// 12.Update the quantity of my first product
		cartPage.selectQty(UpdateQty);

		// 13.Verify the Item price as expected after cart update
		String strItmPriceAftUpdate = cartPage.getItemPrice();
		System.out.println("Item proce after upadte qty from cart----->" + strItmPriceAftUpdate);
		Assert.assertEquals(strItmPriceAftUpdate, ItemPriceAfterUpdate);

		// 14.Verify the Sub-total as expected after cart update
		String strNetAmtAftUpdate = cartPage.getNetCost();
		System.out.println("Subtotal amount after upadte qty from cart----->" + strNetAmtAftUpdate);
		Assert.assertEquals(strNetAmtAftUpdate, SubtotalAfterUpdate);

		// 15.Verify the Shipping cost as expected after cart update
		String strShippingAmtAftUpdate = cartPage.getShippingCost();
		System.out.println("Shipping amount after update qty from cart------>" + strShippingAmtAftUpdate);
		Assert.assertEquals(strShippingAmtAftUpdate, ShippingCostAfterUpdate);

		// 16.Verify the Total cost as expected after cart update
		String strTotalAmtAftUpdate = cartPage.getTotalCost();
		System.out.println("Total amount after update qty from cart------->" + strTotalAmtAftUpdate);
		Assert.assertEquals(strTotalAmtAftUpdate, TotalAmountAfterUpdate);
		

		//Create an object of File class to open xlsx file
	    File file = new File(TESTDATA_SHEET_PATH);
	    
	    //Create an object of FileInputStream class to read excel file
	    FileInputStream inputStream = new FileInputStream(file);
	    
	    //Creating workbook instance that refers to .xls file
	    XSSFWorkbook wb=new XSSFWorkbook(inputStream);
	    
	    //Creating a Sheet object using the sheet Name
	    XSSFSheet sheet=wb.getSheet("update_cart");
	    
	    //get all rows in the sheet
	    int rowCount=sheet.getLastRowNum()-sheet.getFirstRowNum();
	    
	    //iterate over all the rows in Excel and put data in the form.
	    for(int i=1;i<=rowCount;i++) {
	         
	        //create a new cell in the row at index 6
	        XSSFCell cell = sheet.getRow(i).createCell(17);
	        cell.setCellValue("PASS");
	        // Write the data back in the Excel file
	        FileOutputStream outputStream = new FileOutputStream("E:\\Interviews\\Interview Challanges\\LampenWelt\\Lampenwelt_QA_TA\\LampenWeltQA_TA\\src\\main\\java\\com\\lampenwelt\\qa\\testdata\\TestData.xlsx");
	        wb.write(outputStream);
	        outputStream.close();
	       
	    	}
	    //Close the workbook
	    
	}

	@AfterMethod
	public void tearDown() {
		CloseDriver();
	}

}
