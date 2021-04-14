package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import cucumber.TestContext;
import dateTime.DateFunctions;
import io.cucumber.java.Scenario;

public class Utility {
	private static final String LOG_FILE_PATH = "target/logFiles";
	public static final String JSON_FILE_PATH = "target/JSON";
	public static final String RESOURCES_FOLDER = "src/main/resources";
	public static final String FEATURES_FOLDER ="src/test/resources/Features";

	
	public Utility() {
	}

	public Properties getProperties(Properties properties, String configFileName) throws Throwable {
		if (properties == null) {
			properties = new Properties();
			InputStream input = null;
			input = new FileInputStream(RESOURCES_FOLDER + "/Configs/Config" + configFileName + ".properties");
			properties.load(input);
		}
		return properties;
	}

	public void closeFailedTest(Throwable ExceptionVal, TestContext context) throws Throwable {

		// Take Screenshot
		TakeScreenShot(context);

		// Grab StackTrace & write to textfile with same rules as Screenshot
		DumpStackTrace(ExceptionVal);
		throw ExceptionVal;
	}

	private void DumpStackTrace(Throwable exceptionVal) throws IOException {
		// Create ExecutionDate is a format that can be appended to file name.
		Date TodayDate = new Date();
		String ExecutionDate = "";
		ExecutionDate = FormatDateTime(TodayDate, ExecutionDate);

		// Create New StackTrace.txt file
		FileWriter write = new FileWriter(
				LOG_FILE_PATH + File.separator + ExecutionDate + "_STACKTRACE.txt", false);
		PrintWriter print_line = new PrintWriter(write);
		exceptionVal.printStackTrace(print_line);
		print_line.close();
	}

	private void TakeScreenShot(TestContext context) throws IOException {
		WebDriver driver = context.getWebDriverManager().getDriver();
		Scenario scenario = context.getScenario();
		Date TodayDate = new Date();
		String ExecutionDate = "";
		ExecutionDate = FormatDateTime(TodayDate, ExecutionDate);

		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File targetFile = new File(LOG_FILE_PATH + File.separator + scenario.getName() + "_" + ExecutionDate + "_SCREEN.jpg");
		FileUtils.copyFile(scrFile, targetFile);

		// To get the screenshot embed in cucumber report
		scenario.log("Scenario failed : Screenshot captured and embeded in report.");
		scenario.attach(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png",
				"Failure Screenshot");

	}

	public String FormatDateTime(Date date, String FormatDate) {
		String DATE_FORMAT = "ddMMMyyyy_HH.mm";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return FormatDate = sdf.format(date);
	}

	public List<WebElement> waitForElementToBe(By locator, String expectedCondition, WebDriver driver,
			int valueOfDuration) {
		try {
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(valueOfDuration))
					.pollingEvery(Duration.ofSeconds(1));

			switch (expectedCondition) {
			case ("PRESENCE"):
				wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
				return driver.findElements(locator);
			case ("CLICKABLE"):
				wait.until(ExpectedConditions.elementToBeClickable(locator));
				return driver.findElements(locator);
			case ("VISIBLE"):
				wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
				return driver.findElements(locator);
			case ("SELECTED"):
				wait.until(ExpectedConditions.elementToBeSelected(locator));
				return driver.findElements(locator);
			case ("INVISIBLE"):
				wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
				return null;
			}

		} catch (ElementNotVisibleException e) {
			List<WebElement> els = driver.findElements(locator);
			if (els.isEmpty())
				return null;
			else
				return getDisplayedElements(els).isEmpty() ? null : getDisplayedElements(els);
		} catch (TimeoutException e) {
			List<WebElement> els = driver.findElements(locator);
			if (els.isEmpty())
				return null;
			else
				return getDisplayedElements(els).isEmpty() ? null : getDisplayedElements(els);
		} catch (NoSuchElementException e) {
			return null;
		} catch (Exception e) {
			throw (e);
		}
		return null;
	}

	public void selectUsername(String username, WebDriver driver, String name) {
		WebElement DrpdwnSelectUser = driver.findElement(By.name(name));
		Select selectUserName = new Select(DrpdwnSelectUser);
		selectUserName.selectByVisibleText(username);
	}

	public void selectPassword(String password, WebDriver driver) {
		WebElement WebElementPassword = driver.findElement(By.name("password"));
		WebElementPassword.sendKeys(password);
	}

	public WebElement getDisplayedElement(List<WebElement> elements) {
		try {
			for (WebElement element : elements) {
				if (element.isDisplayed()) {
					return element;
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public List<WebElement> getDisplayedElements(List<WebElement> elements) {
		List<WebElement> displayedWebElements = new ArrayList<WebElement>();
		try {
			for (WebElement element : elements) {
				if (element.isDisplayed()) {
					displayedWebElements.add(element);
				}
			}
		} catch (Exception e) {
			throw (e);
		}
		return displayedWebElements;
	}

	public void scrollToElementVisible(WebDriver driver, WebElement Element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		// This will scroll the page till the element is found
		js.executeScript("arguments[0].scrollIntoView();", Element);
	}

	public void clickUsingJavaScript(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}

	public void clickElement(By locator, WebDriver driver, WebElement parent) {
		waitForElementToBe(locator, "VISIBLE", driver, 10);
		List<WebElement> elements = parent.findElements(locator);
		getDisplayedElement(elements).click();
	}

	public void userReadTheTestDataSheetFromFile( String sheetName, String filename, TestContext context)
			throws Exception {
		// Create an object of File class to open xlsx file
		File file = new File(RESOURCES_FOLDER + "/TestDataFiles/" + filename);
		// Create an object of FileInputStream class to read excel file
		FileInputStream inputStream = new FileInputStream(file);
		Workbook wb = new HSSFWorkbook(inputStream);

		// Read sheet inside the workbook by its name
		Sheet sheet = wb.getSheet(sheetName);
		wb.close();
		// To get the last row of the sheet
		int firstRow = sheet.getFirstRowNum();
		int lstRow = sheet.getLastRowNum();
		DataFormatter formatter = new DataFormatter();
		int num = 0;
		for (num = 0; num <= lstRow; num++) {
			String val = formatter.formatCellValue(sheet.getRow(num).getCell(0));
			if (val.trim().length() == 0)
				break;
		}
		lstRow = num;

		// Find number of rows in excel file and setting up the array size
		int rowCount = lstRow - firstRow;
		context.TestData = new String[lstRow][sheet.getRow(sheet.getFirstRowNum()).getLastCellNum()];

		// Create a loop over all the rows of excel file to read it
		for (int i = 0; i < rowCount; i++) {
			Row row = sheet.getRow(i);
			// Create a loop to print cell values in a row
			for (int j = 0; j < row.getLastCellNum(); j++) {
				// Store data in array
				if (formatter.formatCellValue(row.getCell(j)).length() != 0)
					context.TestData[i][j] = formatter.formatCellValue(row.getCell(j)).trim();
			}
		}
	}
	
	public String inputText(WebDriver driver, WebElement elField, String value) {
		if (elField.getAttribute("data-componentid").contains("conm-timefield")) {
			elField.clear();
			Actions act = new Actions(driver);
			act.sendKeys(elField, value).build().perform();
			elField.sendKeys(Keys.TAB);
		} else if (!value.isEmpty() && elField.getAttribute("readonly") == null) {
			try {
				clearField(driver, elField);
				elField.sendKeys(value);
				elField.sendKeys(Keys.TAB);
			} catch (ElementNotVisibleException e) {
				elField.sendKeys(value);
				elField.sendKeys(Keys.TAB);
			}
		}
		return elField.getAttribute("value");
	}
	
	public void clearField(WebDriver driver, WebElement element) {
		try {
			JavascriptExecutor executor = (JavascriptExecutor) driver;
			driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
			WebElement parentElement = element.findElement(By.xpath(".."));
			WebElement clearElmenet = parentElement.findElement(By.cssSelector("div.x-cleartrigger"));
			if (clearElmenet.isDisplayed()) {
				executor.executeScript("arguments[0].click();", clearElmenet);
			}
			executor.executeScript("arguments[0].click();", element);
			element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (NoSuchElementException e) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
			element.sendKeys(Keys.DELETE);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} catch (ElementNotVisibleException e) {
			element.sendKeys(Keys.chord(Keys.CONTROL, "a") + Keys.DELETE);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
	}
	
	public String returnFieldValue(String fieldValue, Properties properties) throws Exception {
		switch (fieldValue) {
		case "TodayDate":
			return DateFunctions.GetTodayDate();
		case "CurrentTime":
			return DateFunctions.getCurrentTime();
		case "Blank":
			return "";
		default:
			String value = properties.getProperty(fieldValue);
			if(value != null)
				return value;
			else if(fieldValue.contains("Autogenerated")) {
				if(fieldValue.matches("AutogeneratedChar[0-9]+")) {
					return Utility.generateRandom("Char", Integer.parseInt(StringUtils.substringAfter(fieldValue, "AutogeneratedChar")));
				}
				else if(fieldValue.matches("AutogeneratedNumber[0-9]+")) {
					return Utility.generateRandom("Alphanumeric",Integer.parseInt(StringUtils.substringAfter(fieldValue, "AutogeneratedNumber")));
				}
				else if(fieldValue.matches("AutogeneratedAlphanumeric[0-9]+")) {
					return Utility.generateRandom("Number", Integer.parseInt(StringUtils.substringAfter(fieldValue, "AutogeneratedAlphanumeric")));
				}
			}
			return DateFunctions.getTheAdjustTimeDateFromConstant(fieldValue);
		}
	}

	public static String generateRandom(String type, int length) {
		String chars = "";
		switch(type) {
		case "Char":
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
			break;
		case "Alphanumeric":
			chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
			break;
		case "Number":
			chars = "1234567890";
			break;
		}
		String name = "";
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			char temp = chars.charAt(random.nextInt(chars.length() - 1));
			name = name + temp;
		}
		return name;
	}

	public void clickElementByActionsClass(WebDriver driver, By xpath) {
			List<WebElement> el = waitForElementToBe(xpath, "VISIBLE", driver, 10);
			Assert.assertTrue(el != null, "No element found.");
			// WebElement el = driver.findElement(locator);
			Actions actClick = new Actions(driver);
			actClick.click(el.get(0)).build().perform();
	}
	
	public String getTimeStamp() {
		SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy HH:mm:ss z");
		f.setTimeZone(TimeZone.getTimeZone("Europe/London"));
		return f.format(GregorianCalendar.getInstance().getTime());
	}
	
	public void inputByAriaLabel(String field, String value, WebDriver driver) {
		WebElement recipientEditBox = waitForElementToBe(By.cssSelector("input[aria-label='" + field + "']"),"VISIBLE", driver, 10).get(0);
		recipientEditBox.sendKeys(value);
		recipientEditBox.sendKeys(Keys.RETURN);
	}
}
