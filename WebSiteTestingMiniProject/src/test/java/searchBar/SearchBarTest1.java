package searchBar;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchBarTest {
	String configFilePath = "src/test/java/config.properties";
	Properties config = new Properties();

	WebDriver driver = null;
	String url = new String();

	/*
	 * Sends keys to the ebay product search bar
	 * 
	 * @returns: a WebElement used to interact with the search bar in later
	 * test methods
	 */
	public WebElement sendKeysToSearchBar(String searchTerm) {
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);

		return searchBar;
	}
	
	/*
	 * Sends keys to the ebay product search bar and submits with enter key
	 * 
	 * @returns: a WebElement used to interact with the search bar in later
	 * test methods
	 */
	public WebElement searchAndSubmit_Enter(String searchTerm) {
		WebElement searchBar = sendKeysToSearchBar(searchTerm);
		searchBar.submit();
		
		return searchBar;
	}
	
	/*
	 * Sends keys to the ebay product search bar and submits by clicking confirmation button
	 * 
	 * @returns: a WebElement used to interact with the search bar in later
	 * test methods
	 */
	public WebElement searchAndSubmit_ButtonClick(String searchTerm) {
		WebElement searchBar = sendKeysToSearchBar(searchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='gh-btn']"));
		searchButton.click();
		
		return searchBar;
	}
	
	/*
	 * Fetches the number of listings from the result count header in the results page
	 * @returns: result count in integer format
	 */
	public Integer parseResultCountHeader() {
		WebElement resultsHeader = driver.findElement(
				By.xpath("//*[@id='srp-river-results-SEARCH_STATUS_MODEL_V2-w0']/div[2]/div[1]/div[1]/h1[contains (text(),'results')]"));
		Integer numberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		return numberOfListings;
	}
	
	@BeforeTest
	public void setUp() throws FileNotFoundException, IOException {
		config.load(new FileInputStream(configFilePath));

		System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriver"));
		driver = new ChromeDriver();
	}

	@BeforeMethod
	public void beforeMethod() {
		String webAddress = "http://www.ebay.com.au";
		driver.get(webAddress);
	}

	/*
	 * Test Case ID: TC_SB_001
	 */
	@Test(enabled = true)
	public void search_for_product_with_at_least_one_listing_enter_key() {
		String searchTerm = "Nvidia GTX 1080Ti";
		searchAndSubmit_Enter(searchTerm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Integer numberofListings = parseResultCountHeader();

		assertTrue(numberofListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_002
	 */
	@Test(enabled = true)
	public void search_for_product_with_at_least_one_listing_button_click() {
		String searchTerm = "Nvidia GTX 1080Ti";
		searchAndSubmit_ButtonClick(searchTerm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);		
		Integer numberOfListings = parseResultCountHeader();

		assertTrue(numberOfListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_003
	 */
	@Test(enabled = true)
	public void search_for_product_with_no_listings_enter_key() {
		String searchTerm = "siudoad";
		searchAndSubmit_Enter(searchTerm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Integer numberOfListings = parseResultCountHeader();

		assertTrue(numberOfListings == 0);
	}

	/*
	 * Test Case ID: TC_SB_004
	 */
	@Test(enabled = true)
	public void search_for_product_with_no_listings_button_click() {
		String searchTerm = "siudoad";
		searchAndSubmit_ButtonClick(searchTerm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Integer numberOfListings = parseResultCountHeader();

		assertTrue(numberOfListings == 0);
	}

	/*
	 * Test Case ID: TC_SB_005
	 */
	@Test(enabled = true)
	public void consecutive_search() {
		String searchTerm1 = "GTX 960";
		searchAndSubmit_Enter(searchTerm1);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		String searchTerm2 = "GTX 1080Ti";
		searchBar.clear();
		searchBar.sendKeys(searchTerm2);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		Integer numberOfListings = parseResultCountHeader();

		assertTrue(numberOfListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_006
	 */
	@Test(enabled = true)
	public void expensive_search_with_short_search_term_submit_with_enter() {
		String shortSearchTerm = "a";
		searchAndSubmit_Enter(shortSearchTerm);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String expected_error_message_substring = "The request contains expensive keywords";
		WebElement error_message_element = driver.findElement(By.xpath("//div[@class='s-error']/div[1]/p"));
		assertTrue(error_message_element.getText().contains(expected_error_message_substring));
	}

	/*
	 * Test Case ID: TC_SB_007
	 */
	@Test(enabled = true)
	public void expensive_search_with_long_search_term_submit_with_enter() {

		String LongSearchTerm = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));

		// use JavascriptExecutor to send large amount of text to searchBar faster
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", searchBar, LongSearchTerm);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String expected_error_message_substring = "The request contains expensive keywords";
		WebElement error_message_element = driver.findElement(By.xpath("//div[@class='s-error']/div[1]/p"));
		assertTrue(error_message_element.getText().contains(expected_error_message_substring));
	}

	/*
	 * Test Case ID: TC_SB_008
	 */
	@Test(enabled = true)
	public void expensive_search_with_long_search_term_submit_with_button_click() {

		String LongSearchTerm = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));

		// use JavascriptExecutor to send large amount of text to searchBar faster
		((JavascriptExecutor) driver).executeScript("arguments[0].value = arguments[1];", searchBar, LongSearchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='gh-btn']"));
		searchButton.click();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		String expected_error_message_substring = "The request contains expensive keywords";
		WebElement error_message_element = driver.findElement(By.xpath("//div[@class='s-error']/div[1]/p"));
		assertTrue(error_message_element.getText().contains(expected_error_message_substring));
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
