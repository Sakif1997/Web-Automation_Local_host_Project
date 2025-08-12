package Utilities;

import org.openqa.selenium.By;

public class SignUpPage extends Methods {
	public By LoginButton = By.xpath("//div[@data-cy='login-menu']");
	public By SignUplinktext = By.xpath("//a[contains(text(),'Don’t have an account? Sign up here.')]");
	public By EmailField = By.name("email");
	public By PasswordField= By.name("password");
	public By Create_Account_button = By.xpath("//button[normalize-space()='Create account']");
	public By Dashbord = By.xpath("//h1[normalize-space()='My Boards']");
	public By logout = By.xpath("/html[1]/body[1]/div[1]/nav[1]/div[1]/div[2]/div[1]");
	
	public void CreateAccount(String Email, String Pass) throws InterruptedException{
		WaitElementVisible(LoginButton);
		clickElement(LoginButton);
		WaitElementVisible(SignUplinktext);
		Thread.sleep(2000);
		clickElement(SignUplinktext);
		CurrentPagetitleMatch("Demo Project Board");
		WaitElementVisible(EmailField);
		FieldValue(EmailField, Email);
		FieldValue(PasswordField, Pass);
		takeScreenshot("SignUp Page");
		Thread.sleep(2000);
		clickElement(Create_Account_button);
		Thread.sleep(2000);
		takeScreenshot("Success message visible");
		clickElement(logout);
		Thread.sleep(2000);	
	}
}
