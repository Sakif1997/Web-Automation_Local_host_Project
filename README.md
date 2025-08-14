# Web-Automation_Local_host_Project

## Project Overview

This project demonstrates automation testing of a web application hosted locally, using Selenium and the Page Object Model (POM) architecture. The goal is to streamline key workflows, improve reliability, and provide maintainable automation scripts for local web-based application.

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

### Scenario 1: Signup, Log out
- Automate user registration using the signup page.
- Verify successful signup and log out.

### Scenario 2: Board and List Management
- Create a new board after logging in.
- Add two lists to the created board.
- Delete one of the lists to verify deletion functionality.

### Scenario 3: Board Deletion and Log out
- Delete the previously created board.
- Log out to complete the workflow.
---
## 📋 Overview

This project demonstrates complete automation of a loacl hosted web application using the Page Object Model (POM) approach. The automation covers real-device execution, multiple scenario flows, parallel testing, and detailed reporting.

- **Web App Code Automation Video Overview(code base):**  
  [Watch the app demo](https://drive.google.com/file/d/1LjbAz4XkuESfJC0FCVzJT9Bo6h39boOW/view?usp=drive_link)

- **Signup, Log out Scenario automation run video**  
  [View Scenario 1,2 test execution](https://drive.google.com/file/d/1h0Y_p7QoAsjCp1bTy6gqurK-vzz0_EbW/view?usp=drive_link)

- **Login, Board add, list add, list delete, Board delete and logout automation run video**  
  [See scenario 3 to 8 in action](https://drive.google.com/file/d/1IjiEj6iNC-wtEdZUiMx5sEGlL0L5rIG9/view?usp=drive_link)

- **TestNG Parallel Test Execution:**  
  [Parallel execution video](https://drive.google.com/file/d/1X0HFgh1Vv421VOepreXpOLQOtLpfNt2G/view?usp=drive_link)

**Check Report:**  
  [Live report link](https://localhost3000project.netlify.app/)  

---

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
Here’s the **package name → class name** route mapping for all the classes:

---

### **Browser Setup**

* **Package:** `Browser`  
  **Class:** `BrowserSetup`  

---

### **Utilities (Page Objects / Methods)**

* **Package:** `Utilities`  
  **Class:** `Methods`  
  **Class:** `SignUpPage`  
  **Class:** `LoginPage`  
  **Class:** `DashbordPage`  

---

### **Test Cases**

* **Package:** `TestCases`  
  **Class:** `SignUp`  
  **Class:** `UserLogin`  
  **Class:** `DashbordOperation`  

---

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
Test result image:  
![Result SignUp](https://drive.google.com/uc?export=view&id=1CSIsYN3alPn_8WyPJh9zTdtuYscyILvb)  


---
### Scenario Replication Classes

### Targeted Scenario:  Existing User Login Procedure

**PackageName:** Utilities
**ClassName:** LoginPage
**Overview of this class:**

* The `LoginPage` class holds locators and actions for performing **existing user login** in the web application.
* It extends the `Methods` class, reusing utility methods for clicking, typing, waits, and screenshots.
* The login process clicks the login menu, enters credentials, submits the form, and verifies the dashboard is displayed.

Here, all selectors (XPaths and element names) are gathered from the application’s UI inspection tools.

```java
package Utilities;

import org.openqa.selenium.By;

public class LoginPage extends Methods {
	public By LoginButton = By.xpath("//div[@data-cy='login-menu']");
	public By Email = By.name("email");
	public By Password = By.name("password");
	public By loginbutton = By.xpath("//button[normalize-space()='Log in']");
	public By DashbordPage = By.xpath("//h1[normalize-space()='My Boards']");
	
	public void Userlogin(String email, String password) throws InterruptedException {
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
```

### Test Run of Existing User Login Procedure
---
**PackageName:** TestCases
**ClassName:** UserLogin
**Overview of this TestCase class:**

* The `loginUser` method navigates to the local app, then calls `Userlogin` from the `LoginPage` class.
* It verifies that an existing user can log in with valid credentials and land on the dashboard.

```java
package TestCases;

import org.testng.annotations.Test;

import Browser.BrowserSetup;
import Utilities.LoginPage;

public class UserLogin extends BrowserSetup {
	LoginPage logIN = new LoginPage();
	
	@Test(description = "Scenario 3: Verify Previous Users successfully loggedin")
	public void loginUser() throws InterruptedException {
		getDriver().get("http://localhost:3000/");
		Thread.sleep(1500);
		logIN.Userlogin("sakif4646@gmail.com", "sakif123");
	}
}
``` 

---
### Scenario Replication Classes

### Targeted Scenario: Board & List Management Workflow (Post-Login)

**PackageName:** Utilities
**ClassName:** DashbordPage
**Overview of this class:**

* The `DashbordPage` class stores locators and actions to **create a board**, **add lists**, **delete a list**, **delete a board**, and **log out**.
* It extends the `Methods` class to reuse waits, clicks, typing, screenshots, and click-then-wait helpers.
* Selectors (XPaths/CSS) are captured from the web app UI (e.g., DevTools); naming aligns with the app (note: code uses “Bord” in identifiers).

```java
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
```

---

### Test Run of Board & List Management Workflow

**PackageName:** TestCases
**ClassName:** DashbordOperation
**Overview of this TestCase class:**

* The `DashbordOperate` test first **logs in** using `UserLogin.loginUser()`, then executes `DashbordPage.Dashbordoperation()` to validate board creation, list creation, list deletion, board deletion, and logout—capturing screenshots for Allure along the way.

```java
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
```
Test result image:  
![Dashbord Operation result](https://drive.google.com/uc?export=view&id=1Rd_iksWccZ4iRMj6otwZ674aUyyV6oMW) 
 


## Allure Report Creation

To create an allure report, first set dependency in the pom.xml file.  
```ruby
        <dependency>
				<groupId>io.qameta.allure</groupId>
				<artifactId>allure-testng</artifactId>
				<version>2.19.0</version>
		</dependency>
```
* Create some methods for allure report (like allure ScreenShot) which is added already
```ruby
public void takeScreenshot(String name) {
		Allure.addAttachment(name, new ByteArrayInputStream(((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.BYTES)));
	}
```
     
* then run the testing.xml file  
![Project Preview](https://drive.google.com/uc?export=view&id=134oM0oA8RurANJRF1kXkBZZiWY3iTJO0)  

* then refresh the whole package and see a "allure-results" file created under Maven Dependencies  
-after runing the testng.xml file and refresh the whole package allure reasult appear

### Allure Graph
The Allure Graph provides a visual summary of all executed test cases, showing passed, failed, and skipped scenarios for quick analysis.

![Allure Graph](https://drive.google.com/uc?export=view&id=1qe6cmF24OxsqiJ_0v34eh5yYVYIevxO3)

---

### Test Case Report – Signup Scenario
This report details the automated **Signup** process, including each step, validations, and screenshots for better traceability.

![Signup Test Report](https://drive.google.com/uc?export=view&id=134YJV43K8X8rQPhwTkFTVpjJDpK-lfsw)

---

### Test Case Report – Login, Board, and List Operations
This combined report covers:
1. User Login  
2. Board Creation  
3. List Addition  
4. List Deletion  
5. Board Deletion  
6. Logout Operation  

**Part 1**  
![Board & List Operations - Part 1](https://drive.google.com/uc?export=view&id=1GE7qMRv3s4veuUP5rmjP-jMElkRwm-KA)  

**Part 2**  
![Board & List Operations - Part 2](https://drive.google.com/uc?export=view&id=13-kT7zzxq03M54o5KqkiOUt4JEfssKcH)  
### 🗂️ Allure Report Features

- **Step-by-step execution details** with screenshots for each step.
- **Test status indicators:** Passed, Failed, Skipped.
- **Error tracebacks and logs** for failed cases.
- **Visual graphs** for test distribution and trends.
- **Easy navigation** between scenarios and steps.

For complete details and interactive test results, visit the [Allure report](https://localhost3000project.netlify.app/).

