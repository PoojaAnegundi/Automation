package com.automation.com.automation.testcases;

import static org.testng.Assert.assertEquals;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automation.page.repo.HomePage;
import com.automation.page.repo.LoginPage;

public class NewTest {


	private WebDriver driver;
	private String url = "http://automationpractice.com/index.php";
	private String itemToSearch = "blouse";
	private String invalidEmail = "abcd@abc.com";
	private String pwd = "Password@123";
	private String validEmail = "abcd@friend.com";
	private int increaseBy = 5;
	private int decreaseBy = 3;
	private String dressSize = "L";
	
//	Scenario 1: Search a product and validate that the search result 
//	is the correct product being displayed
	@Test
	public void SearchResultTC() {

		String title = driver.getTitle();
		System.out.print("Current page title is : " + title);
		HomePage homepge = new HomePage(this.driver);
		homepge.EnterItemNameToSearch(itemToSearch);
		homepge.ClickOnSearch();
		homepge.SearchResult();
		List<String> itemsList = homepge.ListOfSearchResult();
		if (itemsList.size()==1 && itemsList.get(0).toUpperCase().contains(itemToSearch.toUpperCase())) {
			System.out.println("Search results are matched");
			Assert.assertEquals(true, true);
		} else {
			System.out.println("Search results are not unique");
			Assert.fail("Search results are not unique");
		}

	}
//	Scenario 2: Scroll down from the homepage and add a product to cart and 
//	validate the addition of the product is successful to the cart.
	@Test
	public void AddToCartAndValidateTC() throws InterruptedException {

		String title = driver.getTitle();
		System.out.println("Current page title is : " + title);
		HomePage homepge = new HomePage(this.driver);
		homepge.EnterItemNameToSearch(itemToSearch);
		homepge.ClickOnSearch();
		homepge.SearchResult();
		List<String> itemsList = homepge.ListOfSearchResult();
		if (itemsList.size()==1 && itemsList.get(0).toUpperCase().contains(itemToSearch.toUpperCase())) {
			System.out.println("Search results are matched");
			Assert.assertEquals(true, true);
		} else {
			System.out.println("Search results are not unique");
			Assert.fail("Search results are not unique");
		}
		
		homepge.AddToCart();
		Assert.assertEquals(homepge.ValidateCartForItem(itemToSearch), true);

	}
	
//	Scenario 3 : Scroll down to a product to a mouse over and click on More, validate the resulting page has the correct information and
//	also the functionality in that page is working by validating increment and 
//	decrement of quantity, size and then Add to Cart
	@Test
	public void IncrementDecrementValidateTC() throws InterruptedException {

		String title = driver.getTitle();
		System.out.println("Current page title is : " + title);
		HomePage homepge = new HomePage(this.driver);
		homepge.EnterItemNameToSearch(itemToSearch);
		homepge.ClickOnSearch();
		homepge.SearchResult();
		homepge.ClickOnMoreLink();
		

		System.out.println("Initial Quantity: "+homepge.GetCurrentQuantity());
		int intialQuantity = homepge.GetCurrentQuantity();
		homepge.increaseValueBy(increaseBy);
		
		System.out.println("Quantity after increasing "+homepge.GetCurrentQuantity());
		
		homepge.decreaseValueBy(decreaseBy);
		System.out.println("Quantity after decreasing "+homepge.GetCurrentQuantity());
		
		Assert.assertEquals(intialQuantity+increaseBy-decreaseBy, homepge.GetCurrentQuantity());
		
		homepge.setDressSizeTo(dressSize);
		homepge.AddToCartInMore();
		Assert.assertEquals(homepge.ValidateCartForItem(itemToSearch), true);
		Assert.assertEquals(homepge.ValidateCartForQuantity(intialQuantity+increaseBy-decreaseBy), true);
		Assert.assertEquals(homepge.ValidateCartForSize(dressSize), true);

	}
	
	
	
	//Scenario 4: Create a login page failure validation
	@Test
	public void LoginValidationNegativeTC() throws Exception {
		String title = driver.getTitle();
		System.out.print("Current page title is : " + title);
		LoginPage lgnpge = new LoginPage(this.driver);
		Assert.assertEquals(lgnpge.ValidateLogin_Negative(this.invalidEmail, this.pwd), true);
	}
	
	//Scenario 4: Create a login page failure validation
	@Test
	public void LoginValidationPostiveTC() throws Exception {
		String title = driver.getTitle();
		System.out.print("Current page title is : " + title);
		LoginPage lgnpge = new LoginPage(this.driver);
		assertEquals(lgnpge.ValidateLogin_Positive(this.validEmail, this.pwd), true);;
	}
	
	
	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver","C:\\drivers\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		this.driver=driver;
		driver.manage().window().maximize();
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(20000, TimeUnit.MILLISECONDS);

	}

	@AfterMethod
	public void afterMethod() {
		System.out.println("Browser close");
		driver.quit();
	}

}
