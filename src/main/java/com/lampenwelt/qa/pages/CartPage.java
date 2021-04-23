package com.lampenwelt.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.lampenwelt.qa.base.TestBase;

public class CartPage extends TestBase {

	// Page Factory - OR:
	@FindBy(css = ".total-subtotal > td:nth-child(2)")
	WebElement subAmt;

	@FindBy(css = ".value")
	WebElement shippingAmt;

	@FindBy(css = ".total-grand_total > td:nth-child(2)")
	WebElement totalAmt;

	@FindBy(css = "div.cart-products-list__item:nth-child(2) > div:nth-child(2) > div:nth-child(6) > a:nth-child(1)")
	WebElement removeItem;

	@FindBy(css = "div.cart-products-list__item:nth-child(2) > div:nth-child(2) > div:nth-child(4) > span:nth-child(1) > div:nth-child(1) > p:nth-child(1) > span:nth-child(1)")
	WebElement itemPrice;

	// Initializing the Page Objects:
	public CartPage() {
		PageFactory.initElements(driver, this);
	}

	// Actions:

	// Get item price
	public String getItemPrice() {
		return itemPrice.getText();
	}

	// Get Net amount
	public String getNetCost() {
		return subAmt.getText();
	}

	// Get shipping amount
	public String getShippingCost() {
		return shippingAmt.getText();
	}

	// Get total amount
	public String getTotalCost() {
		return totalAmt.getText();
	}

	// Remove the item from the cart
	public void removeFirstArticle() {
		removeItem.click();
	}

	// Select quantity from list value
	public void selectQty(String value) {
		Select select = new Select(driver.findElement(By.cssSelector(
				"div.cart-products-list__item:nth-child(2) > div:nth-child(2) > div:nth-child(3) > select:nth-child(1)")));
		select.selectByVisibleText(value);

	}

}
