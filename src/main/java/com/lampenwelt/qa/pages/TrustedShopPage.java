package com.lampenwelt.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.lampenwelt.qa.base.TestBase;
import com.lampenwelt.qa.util.TestUtil;

public class TrustedShopPage extends TestBase {
	TestUtil testUtil = new TestUtil();

	// Page Factory - OR:
	@FindBy(css = "#uc-btn-accept-banner")
	WebElement acceptTrustCoookie;
	
	@FindBy(css = "a.button")
	WebElement zumOnline;
	
	@FindBy(css = "div.d-lg-block:nth-child(1) > ratings-summary:nth-child(1) > div:nth-child(1) > h2:nth-child(1)")
	WebElement getLampenReviewTitle;
	
	@FindBy(css = "div.d-lg-block:nth-child(1) > ratings-summary:nth-child(1) > div:nth-child(2) > div:nth-child(1) > div:nth-child(1) > div:nth-child(1)")
	WebElement getRating;
	
	

	


	// Initializing the Page Objects:
	public TrustedShopPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	//Accept trusted shop cookies
		public void acceptTrustedCookies() {
			acceptTrustCoookie.click();
		}
		
	//Click on go to online		
		public void clickZumOnline() {
			TestUtil.fluentWait(zumOnline);
			zumOnline.click();
		}
		
	//Get text	
		public String  getLampenTextTitle() {
			return getLampenReviewTitle.getText();
	
		}
		
	//Get text
		public String getReviewRating() {
			return getRating.getText();
		}
		
		
		




}
