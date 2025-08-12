package Utilities;

import org.openqa.selenium.By;

public class DashbordPage extends Methods{
	public By NewBordicon = By.xpath("//h1[normalize-space()='Create new board']");
	public By Bordtitlefield = By.className("new-board-input");
	public By CreateBordbutton = By.xpath("//button[normalize-space()='Create board']");
	public By BordDashbord = By.name("board-title");
	public By Addlist_1_c = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]");//Add will be after space
	public By ListInput1 = By.xpath("//input[@placeholder='Enter list title...']");
	public By Addlist_button = By.xpath("//button[normalize-space()='Add list']");
	
	
	//public By Addlist_2_c = By.xpath("//div[contains(normalize-space(), 'Add a list')]");//Add will be after space
	public By ListInput2 = By.xpath("//input[@placeholder='Enter list title...']");
	//public By Addlist_button2 = By.xpath("//button[normalize-space()='Add list']");
	
	public By list2Option_c = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/button[1]/*[name()='svg'][1]");
	public By deleteList = By.cssSelector("[data-cy='delete-list']");

	
	public By Bordoption_c = By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/button[1]/*[name()='svg'][1]");
	public By deleteBoard = By.cssSelector("[data-cy='delete-board']");
	
	public By logout = By.xpath("//div[@data-cy='logged-user']");
	
	public void Dashbordoperation() throws InterruptedException{
		clickWaitElement(NewBordicon);
		FieldValue(Bordtitlefield, "Automated bord");
		clickElement(CreateBordbutton);
		Thread.sleep(3000);
		WaitElementVisible(BordDashbord);
		clickElement(Addlist_1_c);
		FieldValue(ListInput1, "list-1");
		takeScreenshot("Dashbord");
		Thread.sleep(3500);
		clickElement(Addlist_button);
		FieldValue(ListInput2, "list-2");
		clickElement(Addlist_button);
		Thread.sleep(2000);

		clickElement(list2Option_c);
		Thread.sleep(3000);
		takeScreenshot("list customize menu");
		clickWaitElement(deleteList);
		
		clickElement(Bordoption_c);
		Thread.sleep(2000);
		takeScreenshot("Bord menu");
		clickWaitElement(deleteBoard);
		takeScreenshot("Final dashbord");
		Thread.sleep(2000);
		clickElement(logout);
		Thread.sleep(1500);


		
	}

	

}
