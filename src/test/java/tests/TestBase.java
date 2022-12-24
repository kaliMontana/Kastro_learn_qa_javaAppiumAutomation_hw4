package tests;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class TestBase {
	public AppiumDriver driver;


	@Before
	public void setup() throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();

		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("deviceName", "and80");
		capabilities.setCapability("platformVersion", "8.0");
		capabilities.setCapability("automationName", "Appium");
		capabilities.setCapability("appPackage", "org.wikipedia");
		capabilities.setCapability("appActivity", "org.wikipedia.main.MainActivity");
		capabilities.setCapability("app", "D:\\learn_qa\\Kastro_learn_qa_javaAppiumAutomation_hw2\\apks\\org.wikipedia.apk");

		driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), capabilities);
	}

	@After
	public void tearsDown() {
		driver.quit();
	}
}
