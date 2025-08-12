package TestCases;

import org.testng.annotations.Test;

import Browser.BrowserSetup;
import Utilities.SignUpPage;

public class SignUp extends BrowserSetup{
	SignUpPage signup = new SignUpPage();
	
	
	@Test(description = "Sceanrio 1,2: Verify a new user account is created successfully")
	public void NewUserSignUp() throws InterruptedException{
		getDriver().get("http://localhost:3000");
		Thread.sleep(1500);
		
		//change everytime when needed
		signup.CreateAccount("mrkif@gmail.com", "ifarmer007");
	}

}
