package com.lampenwelt.qa.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.lampenwelt.qa.base.TestBase;

public class AccountOverviewPage extends TestBase {

	// Page Factory - OR:
	@FindBy(xpath = "//div[@class='dashboard-item__content']//p")
	WebElement contactInfo;

	@FindBy(css = "div.dashboard__item:nth-child(2) > div:nth-child(2)")
	WebElement billingAddress;

	@FindBy(css = "div.dashboard__item:nth-child(3) > div:nth-child(2) > address:nth-child(1)")
	WebElement deliveryAddress;

	// Initializing the Page Objects:
	public AccountOverviewPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	// Get item price
	public String getContactInformation() {
		return contactInfo.getText();
	}

	public String getBillingAddress() {
		return billingAddress.getText();
	}

	public String getShippingAddress() {
		return deliveryAddress.getText();
	}

}
