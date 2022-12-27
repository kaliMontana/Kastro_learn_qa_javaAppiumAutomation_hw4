package tests;

import io.appium.java_client.TouchAction;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class WikipediaSwipeAndBackgroundTests extends TestBase {
	@Test
	public void swipeArticleTest() {
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

		waitForElementAndClick(
				By.id("org.wikipedia:id/view_page_title_text"),
				"Cannot find article title",
				15
		);

		swipeUp(2000);
		swipeUp(2000);
		swipeUp(2000);
		swipeUp(2000);
		swipeUp(2000);
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

	protected void swipeUp(int timeOfSwipe) {
		TouchAction action = new TouchAction(driver);
		Dimension size = driver.manage().window().getSize();
		int x = size.width / 2;
		int start_y = (int) (size.getHeight() * 0.8);
		int end_y = (int) (size.getHeight() * 0.2);

		action
				.press(x, start_y)
				.waitAction(timeOfSwipe)
				.moveTo(x, end_y)
				.release()
				.perform();
	}

	protected void swipeQuick() {
		swipeUp(200);
	}

	@Test
	public void swipeToArticleFooterTest() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				"Appium",
				"Cannot find search input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_title'][@text='Appium']"),
				"Cannot find 'Appium' article in search",
				5
		);

		waitForElementAndClick(
				By.id("org.wikipedia:id/view_page_title_text"),
				"Cannot find article title",
				15
		);

		swipeUpToFindElement(
				By.xpath("//*[@text='View page in browser']"),
				"Cannot find the end of the article",
				20
		);
	}

	protected void swipeUpToFindElement(By by, String error_message, int max_swipes) {
		int already_swiped = 0;
		while (driver.findElements(by).size() == 0) {

			if (already_swiped > max_swipes) {
				waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
				return;
			}

			swipeQuick();
			already_swiped++;
		}
	}

	@Test
	public void saveFirstArticleToMyList() {
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

		String name_of_folder = "Learning programming";

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

		waitForElementAndClick(
				By.xpath("//*[@text='" + name_of_folder + "']"),
				"Cannot find created folder",
				5
		);

		swipeElementToLeft(
				By.xpath("//*[@text='Java (programming language)']"),
				"Cannot find saved article"
		);

		waitForElementNotPresent(
				By.xpath("//*[@text='Java (programming language)']"),
				"Cannot delete saved article",
				5
		);
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

	@Test
	public void amountOfNotEmptySearchTest() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		String search_line = "Linking park discography";
		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				search_line,
				"Cannot find search input",
				5
		);

		String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
		waitForElementAndClick(
				By.xpath(search_result_locator),
				"Cannot find anythings by the request" + search_line,
				15
		);

		int amount_of_search_results = getAmountOfElements(
				By.xpath(search_result_locator)
		);

		Assert.assertTrue("We found too few results!",
				amount_of_search_results > 0
		);
	}

	private int getAmountOfElements(By by) {
		List elements = driver.findElements(by);
		return elements.size();
	}

	@Test
	public void amountOfEmptySearch() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		String search_line = "dsgffgdfgfd";
		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				search_line,
				"Cannot find search input",
				5
		);

		String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
		String empty_result_label = "//*[@text='No results found']";

		waitForElementPresent(
				By.xpath(empty_result_label),
				"Cannot find empty result label by request " + search_line,
				15
		);

		assertElementNotPresent(
				By.xpath(search_result_locator),
				"We've found some results by request " + search_line
		);
	}

	private void assertElementNotPresent(By by, String error_message) {
		int amount_of_elements = getAmountOfElements(by);
		if (amount_of_elements > 0) {
			String default_message = "An element '" + by.toString() + "' suppose to be not present";
			throw new AssertionError(default_message + " " + error_message);
		}
	}

	@Test
	public void changeScreenOrientationOnSearchResults() {
		waitForElementAndClick(
				By.xpath("//*[contains(@text, 'Search Wikipedia')]"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		String search_line = "Java";
		waitForElementAndSendKeys(
				By.xpath("//*[contains(@text, 'Search…')]"),
				search_line,
				"Cannot find search input",
				5
		);

		waitForElementAndClick(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
				"Cannot find 'Object-oriented programming language' topic searching by " + search_line,
				15
		);

		String title_before_Rotation = waitForElementAndGetAttribute(
				By.id("org.wikipedia:id/view_page_title_text"),
				"text",
				"Cannot find title of article",
				15
		);

		driver.rotate(ScreenOrientation.LANDSCAPE);

		String title_after_Rotation = waitForElementAndGetAttribute(
				By.id("org.wikipedia:id/view_page_title_text"),
				"text",
				"Cannot find title of article",
				15
		);

		Assert.assertEquals(
				"Article title have been changed after screen rotation",
				title_before_Rotation,
				title_after_Rotation
		);

		driver.rotate(ScreenOrientation.PORTRAIT);

		String title_after_second_Rotation = waitForElementAndGetAttribute(
				By.id("org.wikipedia:id/view_page_title_text"),
				"text",
				"Cannot find title of article",
				15
		);

		Assert.assertEquals(
				"Article title have been changed after screen rotation",
				title_before_Rotation,
				title_after_second_Rotation
		);
	}

	private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInseconds) {
		WebElement element = waitForElementPresent(by, error_message, timeoutInseconds);
		return element.getAttribute(attribute);
	}

	@Test
	public void checkSearchArticleInBackground() {
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

		waitForElementPresent(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
				"Cannot find 'Search wikipedia' input",
				5
		);

		driver.runAppInBackground(3);

		waitForElementPresent(
				By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
				"Cannot find article after returning from background",
				5
		);
	}
}
