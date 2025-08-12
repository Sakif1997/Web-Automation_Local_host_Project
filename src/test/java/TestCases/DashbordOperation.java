package TestCases;


import org.testng.annotations.Test;

import Browser.BrowserSetup;
import Utilities.DashbordPage;

public class DashbordOperation extends BrowserSetup {
	DashbordPage dbpage = new DashbordPage();
	UserLogin log_in = new UserLogin();
	//log_in.loginUser();
	@Test(description = "Scenario 3: Verify Successful login\n"
		    + "Scenario 4: Verify user can create a Bord\n"
		    + "Scenario 5: Verify user can make list\n"
		    + "Scenario 6: Verify user can delete list\n"
		    + "Scenario 7: Verify user can delete bord\n"
		    + "Scenario 8: Verify user can log out")
	public void DashbordOperate() throws InterruptedException{
		log_in.loginUser();
		dbpage.Dashbordoperation();
		
	}

}
