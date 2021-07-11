package com.lampenwelt.qa.pages;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale.LanguageRange;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.util.TestUtil;

public class LandingPage extends TestBase {
	// private static final String String = null;

	TestUtil testUtil = new TestUtil();

	// Page Factory - OR:
	@FindBy(css = "#cmpbntyestxt")
	WebElement acceptCookie;

	@FindBy(xpath = "//div[@class='vex-close']")
	WebElement windowCloseProm;

	@FindBy(css = "#search")
	WebElement inputSearch;

	@FindBy(css = "div.product-name")
	WebElement getArtcleTitle;

	@FindBy(css = "#btnAddToCart")
	WebElement btnAddToCart;

	@FindBy(css = ".search-button")
	WebElement btnSearch;

	@FindBy(css = ".js-minicart-count")
	WebElement cartLabelCount;

	@FindBy(css = ".icon-trusted-shops")
	WebElement trustedIcon;

	@FindBy(css = ".cat-94")
	WebElement LEDMenu;

	@FindBy(css = "#header-account")
	WebElement headerAccount;

	@FindBy(css = ".button--uppercase")
	WebElement anmeldungButton;

	@FindBy(css = ".account-dropdown__new-customer-note > a:nth-child(1)")
	WebElement registerNow;

	@FindBy(css = "#email")
	WebElement inputEmail;

	@FindBy(css = "#pass")
	WebElement inputPassword;

	@FindBy(css = "#send2")
	WebElement signInBtn;

	@FindBy(css = ".customer-dob__datepicker-trigger")
	public static WebElement geburstagCalender;

	@FindBy(xpath = "//select[@class='pika-select pika-select-month']/option[@selected='selected']")
	public static WebElement calanderSelectedmonth;

	@FindBy(xpath = "//select[@class='pika-select pika-select-year']/option[@selected='selected'] ")
	public static WebElement calanderSelectedYear;

	@FindBy(css = "div.buttons-set:nth-child(1) > button:nth-child(1)")
	WebElement registerBtn;

	@FindBy(xpath = "//button[@class='pika-next']")
	public static WebElement calenderNext;

	@FindBy(xpath = "//button[@class='pika-prev']")
	public static WebElement calenderPrevious;

	@FindBy(xpath = "//table[@class='pika-table']//tbody//tr//td[not(contains(@class,'is-disabled')) and not(contains (@class,'is-empty'))]")
	public static WebElement calenderDates;

	// Initializing the Page Objects:
	public LandingPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	// Get page title
	public String getHomePageTitle() {
		return driver.getTitle();
	}

	// Verify article title
	public String getArticleTitle() {
		return getArtcleTitle.getText();
	}

	// Verify article cart count
	public String getCartArticleCount() throws InterruptedException {
		TestUtil.fluentWait(cartLabelCount);
		Thread.sleep(1000);
		return cartLabelCount.getText();
	}

	// Click on cart
	public void clickOnCart() {
		cartLabelCount.click();
	}

	// Click on trust iCon
	public void clickOnTrustIcon() {
		trustedIcon.click();
	}

	// Click go onlineshop

	// Cookiesvisible
	public boolean cookieVisisble() {

		if (driver.findElements(By.cssSelector("#cmpbntyestxt")).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	// Mouse over
	public void moveMouseOverMenuItems(String TopMenuCategory, String SubCategoryName) throws InterruptedException {
		List<WebElement> listOfMenuElements = driver
				.findElements(By.xpath("//ol[@id='main-nav']//li[@class='level0 nav-2 parent']"));

		for (WebElement Element : listOfMenuElements) {

			String Category = Element.getText();
			System.out.println("Last category text finding" + Category);

			if ((Category).equalsIgnoreCase(TopMenuCategory)) {
				TestUtil.MouseOverTo(Element);
				break;
			}
		}

		List<WebElement> listOfSubMenuElements = driver
				.findElements(By.xpath("//li[@class='level1 nav-1-1 first parent type-0']//a[text()=' ']"));
		for (WebElement subElement : listOfSubMenuElements) {
			String SubCategory = subElement.getText();
			if ((SubCategory).equalsIgnoreCase(SubCategoryName)) {
				TestUtil.MouseOverTo(subElement);
				subElement.click();
				break;
			}
		}

	}

	// AcceptCookies
	public void acceptCookies() {
		acceptCookie.click();
	}

	// Search my article
	public void searchMyProduct(String articleNum) {

		TestUtil.fluentWait(btnSearch);
		inputSearch.sendKeys(articleNum);
		btnSearch.click();
	}

	// Click add to cart
	public void clickAddToCart() {
		btnAddToCart.click();
	}

	// SignIn from landing page

	public void signIn(String strUsername, String Password) {
		Actions action = new Actions(driver);
		TestUtil.MouseOverTo(headerAccount);
		TestUtil.MouseOverTo(anmeldungButton);
		anmeldungButton.click();
		TestUtil.fluentWait(inputEmail);
		inputEmail.sendKeys(strUsername);
		action.sendKeys(Keys.TAB);
		inputPassword.sendKeys(Password);
		signInBtn.click();

	}

	public void registerNewUser(String strDateOfBirth) throws Exception {

		TestUtil.MouseOverTo(headerAccount);
		TestUtil.MouseOverTo(registerNow);
		registerNow.click();
		TestUtil.fluentWait(registerBtn);

		LandingPage.selectDateFromDatePicker(strDateOfBirth);

	}

	public static void selectDateFromDatePicker(String strDate) throws Exception {

		LandingPage.geburstagCalender.click();
		Thread.sleep(1000);
		Calendar calendar = Calendar.getInstance();
		try {
			SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
			targetDateFormat.setLenient(false);
			Date formattedTargetDate = targetDateFormat.parse(strDate);
			calendar.setTime(formattedTargetDate);
		} catch (Exception e) {
			throw new Exception("Invalid date is provided, please check the input date!!");
		}

		int targetDay = calendar.get(Calendar.DAY_OF_MONTH);
		int targetMonth = calendar.get(Calendar.MONTH);
		int targetYear = calendar.get(Calendar.YEAR);

		String currentMonthSelected = calanderSelectedmonth.getText();
		String currentYearSelected = calanderSelectedYear.getText();

		if (currentMonthSelected.equalsIgnoreCase("Februar")) {
			String strCurrentMonthSelected = "Feb";

		} else if (currentMonthSelected.equalsIgnoreCase("Juni")) {
			String strCurrentMonthSelected = "Jun";

			String currentDateMonth = strCurrentMonthSelected + " " + currentYearSelected;

			calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(currentDateMonth));

			int currentMonth = calendar.get(Calendar.MONTH);
			int currentYear = calendar.get(Calendar.YEAR);

			// Select future date
			while (currentMonth < targetMonth || currentYear < targetYear) {
				calenderNext.click();
				currentMonthSelected = calanderSelectedmonth.getText();
				strCurrentMonthSelected = calanderSelectedYear.getText();
				currentDateMonth = currentMonthSelected + strCurrentMonthSelected;
				calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(currentDateMonth));
				currentMonth = calendar.get(Calendar.MONTH);
				currentYear = calendar.get(Calendar.YEAR);
				break;
			}

			// Select past date
			while (currentMonth > targetMonth || currentYear > targetYear) {
				calenderPrevious.click();
				currentMonthSelected = calanderSelectedmonth.getText();
				strCurrentMonthSelected = calanderSelectedYear.getText();
				currentDateMonth = currentMonthSelected + strCurrentMonthSelected;
				calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(currentDateMonth));
				currentMonth = calendar.get(Calendar.MONTH);
				currentYear = calendar.get(Calendar.YEAR);
				break;
			}

			if (currentMonth == targetMonth && currentYear == targetYear) {

				driver.findElement(By.xpath("//td[@data-day=" + targetDay + "]")).click();

			} else {
				throw new Exception("unable to select the date because of current and target dates mismatch");

			}

		}

//		
//		String currentYearSelected = calanderSelectedYear.getText();
//		
//		String currentDateMonth= currentMonthSelected+" "+currentYearSelected;

		// String currentMonthSelected = "June";

		// String currentYearSelected = "2003";

	}

}
