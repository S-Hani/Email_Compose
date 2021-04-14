package testRunner;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;

import common.Utility;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import reports.CucumberReports;

@CucumberOptions(
dryRun = false,
plugin = {
		"json:" + Utility.JSON_FILE_PATH + "/Test_Report.json",
		},
features = {
		Utility.FEATURES_FOLDER,
		},
glue = {
		"stepDefs"
		},
tags = "@tag",
monochrome = true

)

public class TestNGTestRunner extends AbstractTestNGCucumberTests {

	@Override
	@DataProvider(parallel = true)
	public Object[][] scenarios() {
		return super.scenarios();
	}

	@AfterSuite
	public static void generateReport() {
		try {
			String strJsonFile = System.getProperty("user.dir") + "/target/JSON/Test_Report.json";
			CucumberReports.generate("target//cucumber//", strJsonFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}