package com.automation.page.repo;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

	WebDriver driver;

	public HomePage(WebDriver driver){
		this.driver = driver;

	}

	public void EnterItemNameToSearch(String itemName) {
		WebElement input_Search = driver.findElement(By.xpath("//input[@id='search_query_top']"));
		input_Search.click();
		input_Search.sendKeys(itemName);
		System.out.println("Item "+itemName+ "is entered");

	}
	public void AddToCart() {
		WebElement link_AddToCart = driver.findElement(By.xpath("//a[@title='Add to cart']"));
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        ((JavascriptExecutor) driver).executeScript(mouseOverScript,link_AddToCart);
		
		link_AddToCart.click();
		WebElement link_Proceed = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
		link_Proceed.click();
	}
	
	public void AddToCartInMore() {
		WebElement link_AddToCart = driver.findElement(By.xpath("//button[@name='Submit']/span[contains(text(),'Add to cart')]"));
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        ((JavascriptExecutor) driver).executeScript(mouseOverScript,link_AddToCart);
		link_AddToCart.click();
		WebElement link_Proceed = driver.findElement(By.xpath("//a[@title='Proceed to checkout']"));
		link_Proceed.click();
	}
	
	public boolean ValidateCartForItem(String item) throws InterruptedException {
		WebElement link_Cart = driver.findElement(By.xpath("//a[@title='View my shopping cart']"));
		link_Cart.click();
		Thread.sleep(3000);
		System.out.println("Navigated to Cart");
		WebElement itemDesc = driver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr/td[@class='cart_description']/p/a"));
		System.out.println(itemDesc.getText());
		if (itemDesc.getText().toUpperCase().trim().contains(item.toUpperCase().trim())) {
			return true;
		}
		return false;
	}
	
	public boolean ValidateCartForQuantity(int value) throws InterruptedException {

		WebElement itemQty = driver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr/td[@class='cart_quantity text-center']/input[@type='text']"));
		System.out.println(itemQty.getAttribute("value"));
		int Quant = Integer.parseInt(itemQty.getAttribute("value"));
		if (Quant==value) {
			return true;
		}
		return false;
	}
	
	public boolean ValidateCartForSize(String item) throws InterruptedException {

		WebElement itemDesc = driver.findElement(By.xpath("//table[@id='cart_summary']/tbody/tr/td[@class='cart_description']/small[2]"));
		
		String[] descSplit = itemDesc.getText().split(",");
		String[] SizeDetails =  descSplit[1].split(":");
		System.out.println(itemDesc.getText());
		if (item.equals(SizeDetails[1].trim())) {
			return true;
		}
		return false;
	}
	
	
	public void ClickOnSignIn() {
		WebElement link_SignIn = driver.findElement(By.xpath("//a[contains(text(),'Sign in')]"));
		link_SignIn.click();
		System.out.println("Clicked on SignIn link");

	}

	public void ClickOnSearch() {
		WebElement button_Search = driver.findElement(By.xpath("//button[@name='submit_search']"));
		button_Search.click();
		System.out.println("Clicked on Search icon");

	}

	public int SearchResult() {
		List<WebElement> link_Product = driver.findElements(By.xpath("//div[@class='right-block']/h5/a[@class='product-name']"));
		if (link_Product.size() ==0) {
			System.out.println("No Search results found.");
		} else if (link_Product.size() ==1) {
			System.out.println("Single results found.");
		} else{
			System.out.println("Multiple results found.");
		}
		return link_Product.size();

	}
	
	public void ClickOnMoreLink() throws InterruptedException {
		Thread.sleep(3000);
		WebElement link_More = driver.findElement(By.xpath("//div[@class='right-block']/div/a/span[contains(text(),'More')]"));
		String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover',true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
        ((JavascriptExecutor) driver).executeScript(mouseOverScript,link_More);
        link_More.click();
	}
	
	public void increaseValueBy(int value) throws InterruptedException {
		System.out.println("Current Quantity is "+GetCurrentQuantity());
		WebElement btn_increase = driver.findElement(By.xpath("//i[@class='icon-plus']"));
		for (int i = 1; i <= value; i++) {
			Thread.sleep(1000);
			btn_increase.click();
		}
	}
	
	public void setDressSizeTo(String value) throws InterruptedException {
		WebElement SizeDrpdwn = driver.findElement(By.xpath("//select[@id='group_1']"));
		SizeDrpdwn.click();
		Select Size = new Select(SizeDrpdwn);
		List<WebElement> allOptions = Size.getOptions();
		
		for (int i = 0; i < allOptions.size(); i++) {
			if (allOptions.get(i).getText().trim().equals(value)) {
				allOptions.get(i).click();
			}
		}
		
		SizeDrpdwn.click();
		
	}
	
	public void decreaseValueBy(int value) throws InterruptedException {
		System.out.println("Current Quantity is "+GetCurrentQuantity());
		WebElement btn_decrease = driver.findElement(By.xpath("//i[@class='icon-minus']"));
		for (int i = 1; i <= value; i++) {
			Thread.sleep(1000);
			btn_decrease.click();
		}
	}
	
	public int GetCurrentQuantity() {
		String CurrQuantity;
		WebElement link_More = driver.findElement(By.xpath("//input[@id='quantity_wanted']"));
		CurrQuantity = link_More.getAttribute("value");
		
		int inum = Integer.parseInt(CurrQuantity);
		return inum;
	}

	public List<String> ListOfSearchResult() {
		ArrayList<String> item = new ArrayList<String>() ;
		String itemName;
		List<WebElement> link_Product = driver.findElements(By.xpath("//div[@class='right-block']/h5/a[@class='product-name']"));
		System.out.println(link_Product.get(0).getText());
		for (int i = 0; i < link_Product.size(); i++) {
			itemName = link_Product.get(i).getText();
			item.add(itemName);
		}
		return item;

	}



}
