package Utilities;

import org.openqa.selenium.By;

public class LoginPage extends Methods{
	public By LoginButton = By.xpath("//div[@data-cy='login-menu']");
	public By Email = By.name("email");
	public By Password =By.name("password");
	public By loginbutton =By.xpath("//button[normalize-space()='Log in']");
	public By DashbordPage =By.xpath("//h1[normalize-space()='My Boards']");
	
	public void Userlogin(String email, String password) throws InterruptedException{
		clickElement(LoginButton);
		Thread.sleep(1000);
		WaitElementVisible(Email);
		FieldValue(Email, email);
		FieldValue(Password, password);
		Thread.sleep(2000);
		takeScreenshot("login Credentials");
		clickElement(loginbutton);
		WaitElementVisible(DashbordPage);
		Thread.sleep(1500);
		takeScreenshot("Dashbord");

	}

	

}
