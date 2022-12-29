package tests;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AssertTitleEx6 extends TestBase {
	@Test
	public void checkElementPresent() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				"Java",
				"Cannot find search input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		assertElementPresent(
				By.id("org.wikipedia:id/view_page_title_text"),
				"resourceId",
				"Not found element 'Title'",
				15
		);
	}


	private WebElement waitForElementPresent(By by, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.presenceOfElementLocated(by)
		);
	}

	private WebElement waitForElementAndClick(By by, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
		element.click();
		return element;
	}

	private WebElement waitForElementAndSendKeys(By by, String value, String errorMessage, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, errorMessage, timeoutInSeconds);
		element.sendKeys(value);
		return element;
	}

	private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		return element.getAttribute(attribute);
	}

	private void assertElementPresent(By by, String attribute, String error_message, long timeoutIntSeconds) {
		String attribute_value = waitForElementAndGetAttribute(
				by,
				attribute,
				error_message,
				timeoutIntSeconds
		);
		System.out.println("attribute_value " + attribute_value);
		if (!attribute_value.contains("_title_")) {
			String default_message = "Element 'Title' by '" + by.toString() + "' suppose to be present. \n";
			throw new AssertionError(default_message + " " + error_message);
		}
	}
}
