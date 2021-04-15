package common;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import cucumber.TestContext;

public class LoginPage extends Utility {
	
	/*
	 * Please enter your email id and password for outlook
	 */
	private static final String PASSWORD = "****";
	private static final String EMAIL_ID = "****";
	
	public LoginPage() {
		super();
	}

	public void userLogsIntoOutlookWithUsernameAndPassword(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		driver.get("https://outlook.live.com/owa/");
		waitForElementToBe(By.cssSelector("a[data-task='signin']"), "VISIBLE", driver, 5).get(0).click();
		
		WebElement email = waitForElementToBe(By.cssSelector("input[type='email']"), "VISIBLE", driver, 5).get(0);
		email.sendKeys(EMAIL_ID);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		WebElement password = waitForElementToBe(By.cssSelector("input[type='password']"), "VISIBLE", driver, 5).get(0);
		password.sendKeys(PASSWORD);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		
		waitForElementToBe(By.cssSelector("div#owaBranding_container"), "VISIBLE", driver, 5);
	}

	public void userIsOnOutlookLandingPage(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		Assert.assertEquals(driver.getCurrentUrl(),"https://outlook.live.com/mail/0/inbox");
	}

	public void userClicksOnComposeButton(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		waitForElementToBe(By.xpath("//button[.='New message']"), "VISIBLE", driver, 5).get(0).click();
	}

	public void userShouldBePresentedWithANewMessageFrame(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		Assert.assertNotNull(waitForElementToBe(By.cssSelector("div[data-skip-link-name='Skip to message'] div[aria-label='Content pane']"),"PRESENCE", driver, 15));
	}

	public void userClicksOnSendButton(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		driver.findElement(By.cssSelector("button[title^='Send']")).click();
	}

	public void userShouldBePromptedWithError(TestContext context, String errorString) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		WebElement error = waitForElementToBe(By.xpath("//div[i[@data-icon-name='ErrorBadge']]"), "PRESENCE", driver, 5).get(0);
		String actString = error.findElement(By.xpath(".//span")).getText();
		Assert.assertEquals(actString, errorString);
	}

	public void userPutsRecipientOfTheEmail(TestContext context, String recipientField) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		if (!recipientField.equals("To")) {
			WebElement button = driver.findElement(By.xpath("//span[.='" + recipientField + "']"));
			button.click();
		}
		inputByAriaLabel(recipientField, EMAIL_ID, driver);
	}

	public void userShouldGetADialog(TestContext context, String dialogText) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		WebElement dialog = waitForElementToBe(By.cssSelector("div[role='dialog'] div.ms-Dialog-header"),"PRESENCE", driver, 10).get(0);
		Assert.assertNotNull(dialog);
		Assert.assertEquals(dialog.getText(), dialogText);
	}

	public void userPutsOfTheEmail(TestContext context, String field) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		if(!field.contains("body"))
			inputByAriaLabel(field, getValueFromTestData(context, field), driver);
		else
			inputBodyByAriaLabel(field, getValueFromTestData(context, field), driver);
	}

	private void inputBodyByAriaLabel(String field, String value, WebDriver driver) {
			WebElement recipientEditBox = waitForElementToBe(By.cssSelector("div[aria-label='" + field + "']"),"VISIBLE", driver, 10).get(0);
			recipientEditBox.sendKeys(value);
			recipientEditBox.sendKeys(Keys.RETURN);
	}

	private String getValueFromTestData(TestContext context, String field) {
		int i=0;
		for (String j : context.TestData[0]) {
			if(j.equalsIgnoreCase(field))
				break;
			else
				i++;
		}
		return context.TestData[1][i];
	}

	public void userShouldBeAbleToSeeTheGivenSubjectAsTitleOfTheMessage(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		WebElement title = waitForElementToBe(By.xpath("//div[i[@data-icon-name='Edit']][button]"),"PRESENCE", driver, 10).get(0);
		Assert.assertEquals(title.getAttribute("title"), getValueFromTestData(context,"Add a subject"));
	}

	public void userShouldGetTheUnreadEmailWithSubjectAndBody(TestContext context) {
		WebDriver driver = context.getWebDriverManager().getDriver();
		WebElement unreadMessage = waitForElementToBe(By.cssSelector("div[aria-label^=Unread]"), "VISIBLE", driver, 5)
				.get(0);
		WebElement fromEmail = unreadMessage.findElement(By.xpath(".//span[@title]"));
		Assert.assertEquals(fromEmail.getAttribute("title"), EMAIL_ID);
		Assert.assertNotNull(unreadMessage
				.findElement(By.xpath(".//span[text()='" + getValueFromTestData(context, "Add a subject") + "']")));
		Assert.assertNotNull(unreadMessage
				.findElement(By.xpath(".//span[text()='" + getValueFromTestData(context, "Message body") + "']")));
	}

	public void userRefreshesThePage(TestContext context) throws InterruptedException {
		Thread.sleep(5000);
		WebDriver driver = context.getWebDriverManager().getDriver();
		driver.navigate().refresh();
	}

}
