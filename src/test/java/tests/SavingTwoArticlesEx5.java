package tests;

import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SavingTwoArticlesEx5 extends TestBase {
	@Test
	public void saveTwoArticlesToMyListTest() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				"Barcelona",
				"Cannot find search input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='City in Catalonia, Spain']"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		waitForElementAndClick(
				By.id("org.wikipedia:id/view_page_title_text"),
				"Cannot find article title",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.ImageView[@content-desc='More options']"),
				"Cannot find button to open article options",
				5
		);

		waitForElementPresent(
				By.xpath("//*[@text='Add to reading list']"),
				"Cannot find option to add article to reading list",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='Add to reading list']"),
				"Cannot find option to add article to reading list",
				5
		);

		waitForElementAndClick(
				By.id("org.wikipedia:id/onboarding_button"),
				"Cannot find 'Got it' tip overlay",
				5
		);

		waitForElementAndClear(
				By.id("org.wikipedia:id/text_input"),
				"Cannot find input to set name of articles folder",
				5
		);

		String name_of_folder = "Doing the homework";
		waitForElementAndSendKeys(
				By.id("org.wikipedia:id/text_input"),
				name_of_folder,
				"Cannot put text into articles folder input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='OK']"),
				"Cannot press 'OK' button",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
				"Cannot close article, cannot find X link",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
				"Cannot find navigation button to my list",
				5
		);

		waitForElementPresent(
				By.xpath("//*[@text='" + name_of_folder + "']"),
				"Cannot find created folder",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.FrameLayout[@content-desc='Explore']"),
				"Cannot find icon 'Explore' to click",
				5
		);

		//Is this began steps to second article
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		String searched_word_Moscow = "Moscow";
		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				searched_word_Moscow,
				"Cannot find search input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Capital and most populous city of Russia']"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		waitForElementAndClick(
				By.id("org.wikipedia:id/view_page_title_text"),
				"Cannot find article title",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.ImageView[@content-desc='More options']"),
				"Cannot find button to open article options",
				5
		);

		waitForElementPresent(
				By.xpath("//*[@text='Add to reading list']"),
				"Cannot find option to add article to reading list",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='Add to reading list']"),
				"Cannot find option to add article to reading list",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='" + name_of_folder + "']"),
				"Cannot find option to add article to reading list",
				15
		);

		waitForElementAndClick(
				By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
				"Cannot close article, cannot find X link",
				5
		);

		waitForElementAndClick(
				By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
				"Cannot find navigation button to my list",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='" + name_of_folder + "']"),
				"Cannot find option to add article to reading list",
				15
		);

		String article_text_about_Barcelona = "city in Catalonia, Spain";
		swipeElementToLeft(
				By.xpath("//*[@text='" + article_text_about_Barcelona + "']"),
				"Cannot find saved article"
		);

		waitForElementNotPresent(
				By.xpath("//*[@text='" + article_text_about_Barcelona + "']"),
				"Cannot delete saved article",
				5
		);

		waitForElementPresent(
				By.xpath("//*[@text='" + searched_word_Moscow + "']"),
				"Cannot find the second saved article",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@text='" + searched_word_Moscow + "']"),
				"Cannot find the second saved article",
				5
		);

		String article_text_about_Moscow = "Capital and largest city of Russia";
		String article_subtitle = waitForElementAndGetAttribute(
				By.id("org.wikipedia:id/view_page_subtitle_text"),
				"text",
				"Cannot find subtitle of article",
				15
		);
		Assert.assertEquals(
				"Titles are not the same",
				article_text_about_Moscow,
				article_subtitle
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

	private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
		WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
		element.clear();
		return element;
	}

	protected void swipeElementToLeft(By by, String error_message) {
		WebElement element = waitForElementPresent(
				by,
				error_message,
				10);

		int left_x = element.getLocation().getX();
		int right_x = left_x + element.getSize().getWidth();
		int upper_y = element.getLocation().getY();
		int lower_y = upper_y + element.getSize().getHeight();
		int middle_y = (upper_y + lower_y) / 2;

		TouchAction action = new TouchAction(driver);
		action
				.press(right_x, middle_y)
				.waitAction(500)
				.moveTo(left_x, middle_y)
				.release()
				.perform();
	}

	private boolean waitForElementNotPresent(By by, String errorMessage, long timeoutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		wait.withMessage(errorMessage + "\n");
		return wait.until(
				ExpectedConditions.invisibilityOfElementLocated(by)
		);
	}

	private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInseconds) {
		WebElement element = waitForElementPresent(by, error_message, timeoutInseconds);
		return element.getAttribute(attribute);
	}
}
