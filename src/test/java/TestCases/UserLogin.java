package TestCases;

import org.testng.annotations.Test;

import Browser.BrowserSetup;
import Utilities.LoginPage;

public class UserLogin extends BrowserSetup{
	LoginPage logIN = new LoginPage();
	
	@Test(description = "Scenario 3: Verify Previous Users successfully loggedin")
	public void loginUser() throws InterruptedException{
		getDriver().get("http://localhost:3000/");
		Thread.sleep(1500);
		logIN.Userlogin("sakif4646@gmail.com", "sakif123");
	}

}
