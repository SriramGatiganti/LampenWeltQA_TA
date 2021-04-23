package com.lampenwelt.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.lampenwelt.qa.base.TestBase;

public class LandingPage extends TestBase {

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
	public String getCartArticleCount() {
		return cartLabelCount.getText();
	}

	// Click on cart
	public void clickOnCart() {
		cartLabelCount.click();
	}

	// Cookiesvisible
	public boolean cookieVisisble() {
		if (driver.findElements(By.cssSelector("#cmpbntyestxt")).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	// AcceptCookies
	public void acceptCookies() {
		acceptCookie.click();
	}

	// Search my article
	public void searchMyProduct(String articleNum) {
		inputSearch.sendKeys(articleNum);
		btnSearch.click();
	}

	// Click add to cart
	public void clickAddToCart() {
		btnAddToCart.click();
	}

}
