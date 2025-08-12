package Utilities;

import static Browser.BrowserSetup.getDriver;

import java.io.ByteArrayInputStream;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.qameta.allure.Allure;

public class Methods {
	public WebElement getElement(By locator) {
	//import static Wppool.AssignmentWP.BrowserSetup.getDriver;
	return getDriver().findElement(locator);//driver = getDriver();
	}

	//for click webelement
	public void clickElement(By locator) {
		getElement(locator).click();
	}
	
	//for giving text input
	public void FieldValue(By locator,String text)
	{
		getElement(locator).sendKeys(text);
	}
	
	//Explicit wait 
	public void WaitElementVisible(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	//get text for webelement
	public String getText(By locator) {
		WebElement element =getElement(locator);
		return element.getText();
		
	}
	public void clickWaitElement(By locator) {
		WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
		WebElement waitelement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		waitelement.click();
	}
	public void CurrentPagetitleMatch(String title) {
		String currentTitle = getDriver().getTitle();
		if (currentTitle.equals(title)) {
			System.out.println("Current page title matches: " + currentTitle);
		} else {
			System.out.println("Current page title does not match. Expected: " + title + ", but got: " + currentTitle);
		}
	}
	public void HoverAndClick(By locator) {
	    Actions action = new Actions(getDriver());
	    action.moveToElement(getElement(locator)).click().perform();
	}
	public void takeScreenshot(String name) {
		Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES)));
	}
	

}
