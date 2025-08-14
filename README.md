# Web-Automation_Local_host_Project

## Project Overview

This project demonstrates automation testing of a web application hosted locally, using Selenium and the Page Object Model (POM) architecture. The goal is to streamline key workflows, improve reliability, and provide maintainable automation scripts for your local web-based application.

## Target/Goals

1. **Signup**
2. **Log out**
3. **Log in**
4. **Create a Board**
5. **Add two List**
6. **Delete one List**
7. **Delete Board**
8. **Log out**

## Scenario

### Scenario 1: Signup, Log out, Log in
- Automate user registration using the signup page.
- Verify successful signup and log out.
- Log in with the newly created user credentials.

### Scenario 2: Board and List Management
- Create a new board after logging in.
- Add two lists to the created board.
- Delete one of the lists to verify deletion functionality.

### Scenario 3: Board Deletion and Log out
- Delete the previously created board.
- Log out to complete the workflow.

## Prerequisites

- Download and set up your local web application (ensure it's running on localhost).

## Test Workflow

### Test Structure

- Tests are organized by feature or module using packages.
- Each package contains classes for page objects and test cases.
- Utility classes provide common methods for element interaction, waits, screenshots, etc.  
Overview:  
![Project Preview](https://drive.google.com/uc?export=view&id=1PYRJxnwmt6or5MFVG5c6ncb5BQhbU2_l)


#### Environment Setup

1. **pom.xml**: Set dependencies for Selenium, TestNG, WebDriverManager, and Allure reporting.
2. **BrowserSetup**: A dedicated class for launching browsers (Chrome, Edge, Firefox).

Example pom.xml dependencies:
```xml

  <dependencies>
      <!-- https://mvnrepository.com/artifact/org.testng/testng -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.6.1</version>
    <scope>test</scope>
</dependency>
<!-- https://mvnrepository.com/artifact/org.seleniumhq.selenium/selenium-java -->
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.25.0</version>
</dependency>
<!-- https://mvnrepository.com/artifact/io.github.bonigarcia/webdrivermanager -->
<dependency>
    <groupId>io.github.bonigarcia</groupId>
    <artifactId>webdrivermanager</artifactId>
    <version>5.9.2</version>
</dependency>

<!-- https://mvnrepository.com/artifact/io.qameta.allure/allure-testng -->
<dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-testng</artifactId>
    <version>2.26.0</version>
</dependency>

        <!-- https://mvnrepository.com/artifact/com.aventstack/extentreports -->
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>5.1.2</version>
</dependency>
</dependencies>
```

## BrowserControl/Setup

---

Create **BrowserSetup** class under `Browser` package.
Handles browser initialization for Chrome, Edge, and Firefox using **WebDriverManager**.
Uses `ThreadLocal` for parallel tests, reads `-Dbrowser` (default: Chrome), maximizes window at start, and quits after the suite.

```ruby
package Browser;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserSetup {
	private static String BrowserName = System.getProperty("browser", "chrome");
	private static final ThreadLocal<WebDriver> DRIVER_LOCAL = new ThreadLocal<>();

	public static WebDriver getDriver() {
		return DRIVER_LOCAL.get();
	}
	public static void setDriver(WebDriver driver) {
		BrowserSetup.DRIVER_LOCAL.set(driver);
	}
	public static WebDriver getBrowser(String BrowserName) {
		switch (BrowserName.toLowerCase()) {
		case "chrome":
			ChromeOptions option1 = new ChromeOptions();
			option1.addArguments("--remote-allow-origins=*");
			WebDriverManager.chromedriver().setup();
			return new ChromeDriver(option1);
		case "edge":
			EdgeOptions option2 = new EdgeOptions();
			option2.addArguments("--remote-allow-origins=*");
			WebDriverManager.edgedriver().setup();
			return new EdgeDriver(option2);
		case "firefox":
			FirefoxOptions option3 = new FirefoxOptions();
			option3.addArguments("--remote-allow-origins=*");
			WebDriverManager.firefoxdriver().setup();
			return new FirefoxDriver(option3);
		default:
			throw new RuntimeException("Browser Not found");
		}
	}
	@BeforeSuite
	public static synchronized void setBrowser() {
		WebDriver webDriver = getBrowser(BrowserName);
		webDriver.manage().window().maximize();
		setDriver(webDriver);
	}
	@AfterSuite
	public static synchronized void quitBrowser() {
		getDriver().quit();
	}
}
```

## Page Object model

### Methods

Create a **Utilities** package with a reusable **Methods** class.
These helpers make tests concise and parallel-friendly (via centralized access to `getDriver()`).

##### Methods and Usages:

* Retrieve a single `WebElement` by locator.

```ruby
public WebElement getElement(By locator) {
    return getDriver().findElement(locator);
}
```

* Click an element found by locator.

```ruby
public void clickElement(By locator) {
    getElement(locator).click();
}
```

* Type text into an input field.

```ruby
public void FieldValue(By locator, String text) {
    getElement(locator).sendKeys(text);
}
```

* Wait until an element is visible (explicit wait, 20s).

```ruby
public void WaitElementVisible(By locator) {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
    wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
}
```

* Get the visible text of an element.

```ruby
public String getText(By locator) {
    WebElement element = getElement(locator);
    return element.getText();
}
```

* Wait for visibility, then click (useful for dynamic UIs).

```ruby
public void clickWaitElement(By locator) {
    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(20));
    WebElement waitelement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    waitelement.click();
}
```

* Validate current page title matches an expected string (logs to console).

```ruby
public void CurrentPagetitleMatch(String title) {
    String currentTitle = getDriver().getTitle();
    if (currentTitle.equals(title)) {
        System.out.println("Current page title matches: " + currentTitle);
    } else {
        System.out.println("Current page title does not match. Expected: " + title + ", but got: " + currentTitle);
    }
}
```

* Hover over an element and click (menus/tooltips, etc.).

```ruby
public void HoverAndClick(By locator) {
    Actions action = new Actions(getDriver());
    action.moveToElement(getElement(locator)).click().perform();
}
```

* Take a screenshot and attach to **Allure**.

```ruby
public void takeScreenshot(String name) {
    Allure.addAttachment(
        name,
        new ByteArrayInputStream(((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES))
    );
}
```
### Scenario Replication Classes

### Targeted Scenario:  New User Sign-Up Procedure

**PackageName:** Utilities
**ClassName:** SignUpPage
**Overview of this class:**

* The `SignUpPage` class contains the locators and actions required to perform a **new user registration** on the web app.
* This class extends the `Methods` class, inheriting reusable utilities for interaction, waits, screenshots, and validations.
* The sign-up flow opens the login menu, follows the sign-up link, fills email & password, creates the account, verifies success, and logs out.

Here, the class stores the **XPaths/IDs** used to drive the scenario.
*These selectors are captured from the web app (e.g., browser DevTools).*

```java
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
```

---

### Test Run of New User Sign-Up Procedure
---
**PackageName:** TestCases
**ClassName:** SignUp
**Overview of this TestCase class:**

* The scenario is implemented in `NewUserSignUp`, which navigates to the local app and calls `CreateAccount` from `SignUpPage`.
* The helper method performs the entire sign-up sequence and attaches screenshots for Allure reporting.

```java
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
		
		// change every time when needed
		signup.CreateAccount("mrsaakif@gmail.com", "ifarmer007");
	}
}
```
---
