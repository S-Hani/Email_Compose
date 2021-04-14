package reports;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.OperatingSystem;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.json.support.Status;
import net.masterthought.cucumber.presentation.PresentationMode;
import zips.ZipUtility;

public class CucumberReports {
//	private static Logger Log = LogManager.getLogger(CucumberReports.class.getName());

	public static void generate(String folderPath, String strJsonFile) throws Exception {

		String buildNumber = System.getenv("BUILD_NUMBER");
		String projectName = System.getenv("JOB_NAME");
		if (projectName == null) {
			projectName = "eclipseRun";
			buildNumber = "1";
		} else {
			String dir = folderPath + "/../LogFiles" ;
			String zipFile = folderPath + "/../FailureLogs.zip";
			if (new File(dir).exists()) {
				ZipUtility.zip(dir, zipFile);
//				Log.info("EaG_Log folder is zipped, and can be found at " + zipFile);
			}
		}
		File reportOutputDirectory = new File(folderPath);
		List<String> jsonFiles = new ArrayList<>();
		jsonFiles.add(strJsonFile);

		Configuration configuration = new Configuration(reportOutputDirectory, projectName);
		// optional configuration - check javadoc for details
		configuration.addPresentationModes(PresentationMode.RUN_WITH_JENKINS);
		// do not make scenario failed when step has status SKIPPED
		configuration.setNotFailingStatuses(Collections.singleton(Status.SKIPPED));
		configuration.setBuildNumber(buildNumber);
		// addidtional metadata presented on main page

		ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
		reportBuilder.generateReports();

		if (!projectName.equals("eclipseRun")) { // for disabling capture screenshot for local runs
			if (System.getProperty("os.name").toLowerCase().contains("windows")) {
				WebDriverManager.chromedriver().operatingSystem(OperatingSystem.WIN).setup();
			} else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
				WebDriverManager.chromedriver().operatingSystem(OperatingSystem.LINUX).setup();
			}
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-fullscreen");
			options.setAcceptInsecureCerts(true);
			options.setExperimentalOption("useAutomationExtension", false);
			WebDriver driver = new ChromeDriver(options);
			driver.get(System.getProperty("user.dir")
					+ "\\target\\cucumber\\cucumber-html-reports\\overview-features.html");
			driver.manage().window().fullscreen();
			driver.findElement(By.tagName("body")).sendKeys(Keys.PAGE_DOWN);
			Thread.sleep(3000);
			File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File targetFile = new File("target//Text_SCREEN.jpg");
			FileUtils.copyFile(scrFile, targetFile);
			driver.quit();
		}
	}
}
