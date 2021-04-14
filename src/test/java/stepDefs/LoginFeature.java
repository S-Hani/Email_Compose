package stepDefs;

import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import common.LoginPage;
import cucumber.TestContext;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginFeature {

	static Properties properties;
	static LoginPage login;
	static int sleep;
	TestContext context;
	private static Logger Log = LogManager.getLogger();

	public LoginFeature(TestContext context) {
		this.context = context;
		login = new LoginPage();
	}

	@Before
	public void setUp(Scenario scenario) {
		context.setScenario(scenario);
		Log.info("####### Test execution started - " + scenario.getName() + " #######");
	}

	@When("^User reads the testdata from \"(.*?)\" sheet$")
	public void userReadsTheTestDataFromSheet(String sheetName) throws Throwable {
		try {
			String filename = "ComposeEmail.xls";
			login.userReadTheTestDataSheetFromFile(sheetName, filename, context);
			Log.info("User reads the test data from " + sheetName + " sheet");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Given("^User logs into Outlook with username and password$")
	public void userLogsIntoOutlookWithUsernameAndPassword() throws Throwable {
		try {
			login.userLogsIntoOutlookWithUsernameAndPassword(context);
			Log.info("User logs into Gmail with username and password");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Given("^User is on Outlook landing page$")
	public void userIsOnOutlookLandingPage() throws Throwable {
		try {
			login.userIsOnOutlookLandingPage(context);
			Log.info("User is on Gmail landing page");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@When("^User clicks on Compose button$")
	public void userClicksOnComposeButton() throws Throwable {
		try {
			login.userClicksOnComposeButton(context);
			Log.info("User clicks on Compose button");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Then("^User should be presented with a New Message frame$")
	public void userShouldBePresentedWithANewMessageFrame() throws Throwable {
		try {
			login.userShouldBePresentedWithANewMessageFrame(context);
			Log.info("User should be presented with a New Message frame");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Given("^User is on New Message frame$")
	public void userIsOnNewMessageFrame() throws Throwable {
		try {
			login.userShouldBePresentedWithANewMessageFrame(context);
			Log.info("User is on New Message frame");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@And("^User clicks on Send button$")
	public void userClicksOnSendButton() throws Throwable {
		try {
			login.userClicksOnSendButton(context);
			Log.info("User clicks on Send button");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@And("^User refreshes the page$")
	public void userRefreshesThePage() throws Throwable {
		try {
			login.userRefreshesThePage(context);
			Log.info("User clicks on Send button");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Then("^User should be prompted with \"([^\"]*)\" error$")
	public void userShouldBePromptedWithError(String errorString) throws Throwable {
		try {
			login.userShouldBePromptedWithError(context, errorString);
			Log.info("User should be prompted with " + errorString + " error");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@When("^User puts recipient of the email in \"([^\"]*)\" field$")
	public void userPutsRecipientOfTheEmailInField(String recipientField) throws Throwable {
		try {
			login.userPutsRecipientOfTheEmail(context, recipientField);
			Log.info("User puts recipient of the email in " + recipientField + " field");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@When("^User should get a \"([^\"]*)\" dialog$")
	public void userShouldGetADialog(String dialogName) throws Throwable {
		try {
			login.userShouldGetADialog(context, dialogName);
			Log.info("User should get a " + dialogName + " dialog");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@When("^User puts \"([^\"]*)\" of the email$")
	public void userPutsOfTheEmail(String field) throws Throwable {
		try {
			login.userPutsOfTheEmail(context, field);
			Log.info("User puts " + field + " of the email");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Then("^User should be able to see the given subject as title of the message$")
	public void userShouldBeAbleToSeeTheGivenSubjectAsTitleOfTheMessage() throws Throwable {
		try {
			login.userShouldBeAbleToSeeTheGivenSubjectAsTitleOfTheMessage(context);
			Log.info("User should be able to see the given subject on the screen");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@Then("^User should get the unread email with subject and body$")
	public void userShouldGetTheUnreadEmailWithSubjectAndBody() throws Throwable {
		try {
			login.userShouldGetTheUnreadEmailWithSubjectAndBody(context);
			Log.info("User should get the unread email with subject and body");
		} catch (Exception | AssertionError e) {
			login.closeFailedTest(e, context);
		}
	}

	@After
	public void tearDown() {
		context.getWebDriverManager().closeDriver();
	}
}
