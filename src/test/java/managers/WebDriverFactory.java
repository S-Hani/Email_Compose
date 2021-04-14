package managers;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import common.Utility;
import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriverFactory {
	private static final String DEFAULT_BROWSER = "Chrome";
	private static final String DESIRED_OS = "win32";
	private static final String CHROME_VERSION = "88";
	private static final String EDGE_VERSION = "88";
	private static final String FIREFOX_VERSION = "85";
	private WebDriver driver;

	public WebDriverFactory() {
	}

	public WebDriver getDriver() {
		String isOffLine = System.getProperty("IsOffline");
		boolean checkOffline = (isOffLine == null) ? false : Boolean.parseBoolean(isOffLine);
		if (driver == null)
			driver = createDriver(checkOffline);
		return driver;
	}

	private WebDriver createDriver(boolean isOffline) {
		String jenkinsBrowser = System.getProperty("jenkinsBrowser");
		String Browser = (jenkinsBrowser == null) ? DEFAULT_BROWSER : jenkinsBrowser;
		switch (Browser.toUpperCase()) {
		case "CHROME":
			setChromeDriver(isOffline);
			break;
		case "FIREFOX":
			setFirefoxDriver(isOffline);
			break;
		case "EDGE":
			setEdgeDriver(isOffline);
			break;
		}

		return driver;
	}

	private void setChromeDriver(boolean isOffline) {
		if (isOffline)
			System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY, Utility.RESOURCES_FOLDER
					+ "/DriverBinaries/chromedriver_" + DESIRED_OS + "_v" + CHROME_VERSION + ".exe");
		else
			WebDriverManager.chromedriver().setup();
		ChromeOptions option = new ChromeOptions();
		option.setAcceptInsecureCerts(true);
		String ProxyPort = System.getProperty("proxyPort");
		if (ProxyPort != null)
			if(!ProxyPort.contains("$")) {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("proxy", getProxy(ProxyPort));
			option.merge(capabilities);
		}
		driver = new ChromeDriver(option);
		driver.manage().window().maximize();
//		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	private void setFirefoxDriver(boolean isOffline) {
		if (isOffline)
			System.setProperty(GeckoDriverService.GECKO_DRIVER_EXE_PROPERTY, Utility.RESOURCES_FOLDER
					+ "/DriverBinaries/geckodriver_" + DESIRED_OS + "_v" + FIREFOX_VERSION + ".exe");
		else
			WebDriverManager.firefoxdriver().setup();
		FirefoxOptions option = new FirefoxOptions();
		option.setAcceptInsecureCerts(true);
		String ProxyPort = System.getProperty("proxyPort");
		if (ProxyPort != null )
			if(!ProxyPort.contains("$")) {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			capabilities.setCapability("proxy", getProxy(ProxyPort));
			option.merge(capabilities);
		}
		driver = new FirefoxDriver(option);
		driver.manage().window().maximize();
//		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	private void setEdgeDriver(boolean isOffline) {
		if (isOffline)
			System.setProperty(EdgeDriverService.EDGE_DRIVER_EXE_PROPERTY, Utility.RESOURCES_FOLDER
					+ "/DriverBinaries/msedgedriver_" + DESIRED_OS + "_v" + EDGE_VERSION + ".exe");
		else
			WebDriverManager.edgedriver().setup();
		EdgeOptions option = new EdgeOptions();
		option.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		String ProxyPort = System.getProperty("proxyPort");
		if (ProxyPort != null )
			if(!ProxyPort.contains("$")) {
			DesiredCapabilities capabilities = DesiredCapabilities.edge();
			capabilities.setCapability("proxy", getProxy(ProxyPort));
			option.merge(capabilities);
		}
		driver = new EdgeDriver(option);
		driver.manage().window().maximize();
//		driver.manage().window().setSize(new Dimension(1920, 1080));
	}

	public void closeDriver() {
//		driver.close();
		driver.quit();
	}

	public Proxy getProxy(String portno) {
		Proxy proxy = new Proxy();
		proxy.setHttpProxy("127.0.0.1:" + portno);
		proxy.setSslProxy("127.0.0.1:" + portno);
		return proxy;
	}
}
