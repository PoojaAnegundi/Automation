package com.automation.page.repo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {

	WebDriver driver;

	public LoginPage(WebDriver driver){
		this.driver = driver;

	}

	public void EnterEmailId(String email) {
		WebElement input_Email = driver.findElement(By.xpath("//input[@id='email']"));
		input_Email.click();
		input_Email.sendKeys(email);
		System.out.println("Email id is entered as: "+ email);
	}
	
	public void EnterPassword(String password) {
		WebElement input_pwd = driver.findElement(By.xpath("//input[@id='passwd']"));
		input_pwd.click();
		input_pwd.sendKeys(password);
	}
	
	public void ClickOnSubmit() {
		WebElement button_submit = driver.findElement(By.xpath("//button[@id='SubmitLogin']"));
		button_submit.click();
		System.out.println("Click on Submit button");
	}
	
	public boolean ValidateLogin_Negative(String email, String pwd) throws Exception{
		HomePage homepge = new HomePage(this.driver);
		homepge.ClickOnSignIn();
		EnterEmailId(email);
		EnterPassword(pwd);
		ClickOnSubmit();
		
		WebElement errorTxt = driver.findElement(By.xpath("//div/p[contains(text(),'There is 1 error')]"));
//		WebElement SignOutLink = driver.findElement(By.xpath("(//a[contains(text(),'Sign out')])[1]"));
		WebElement failureReason = driver.findElement(By.xpath("//div/p[contains(text(),'There is 1 error')]/following-sibling::ol/li"));
		Thread.sleep(5000);
		System.out.println(errorTxt.getText());
		System.out.println(failureReason.getText());
		if (errorTxt.isEnabled()) {
			System.out.println("Login failed with email id: "+email);
			System.out.println("Login failed with failure reason as: "+failureReason.getText());
			return true;
		}
		return false;
	}
	
	public boolean ValidateLogin_Positive(String email, String pwd) throws Exception{
		HomePage homepge = new HomePage(this.driver);
		homepge.ClickOnSignIn();
		EnterEmailId(email);
		EnterPassword(pwd);
		ClickOnSubmit();
		
		WebElement SignOutLink = driver.findElement(By.xpath("(//a[contains(text(),'Sign out')])[1]"));
		Thread.sleep(5000);
		if (SignOutLink.isEnabled()) {
			System.out.println("Login Success with email id: "+email);
			return true;
		}
		return false;
	}




}
