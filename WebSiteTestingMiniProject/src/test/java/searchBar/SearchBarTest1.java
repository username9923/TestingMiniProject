package searchBar;

import org.testng.annotations.AfterMethod;
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

import utility.SearchBarUtility;

public class SearchBarTest1 {
	String configFilePath = "src/test/java/config.properties";
	Properties config = new Properties();
	WebDriver driver = null;
	SearchBarUtility sbu = null;

	@BeforeTest
	public void setUp() throws FileNotFoundException, IOException {
		config.load(new FileInputStream(configFilePath));
		System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriver"));
	}

	@BeforeMethod
	public void beforeMethod() {
		String webAddress = "http://www.ebay.com.au";
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.get(webAddress);
		sbu = new SearchBarUtility(driver);
	}
	
	@AfterMethod
	public void methodTearDown() {
		driver.quit();
	}
	
	@AfterTest
	public void tearDown() {

	}
	/*
	 * Test Case ID: TC_SB_001
	 */
	@Test(enabled = true)
	public void search_for_product_with_at_least_one_listing_enter_key() {
		String searchTerm = "Nvidia GTX 1080Ti";
		sbu.searchAndSubmit_Enter(searchTerm);
		
		
		Integer numberofListings = sbu.parseResultCountHeader();

		assertTrue(numberofListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_002
	 */
	@Test(enabled = true)
	public void search_for_product_with_at_least_one_listing_button_click() {
		String searchTerm = "Nvidia GTX 1080Ti";
		sbu.searchAndSubmit_ButtonClick(searchTerm);
		
		
		Integer numberOfListings = sbu.parseResultCountHeader();

		assertTrue(numberOfListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_003
	 */
	@Test(enabled = true)
	public void search_for_product_with_no_listings_enter_key() {
		String searchTerm = "siudoad";
		sbu.searchAndSubmit_Enter(searchTerm);
		
		
		Integer numberOfListings = sbu.parseResultCountHeader();

		assertTrue(numberOfListings == 0);
	}

	/*
	 * Test Case ID: TC_SB_004
	 */
	@Test(enabled = true)
	public void search_for_product_with_no_listings_button_click() {
		String searchTerm = "siudoad";
		sbu.searchAndSubmit_ButtonClick(searchTerm);
		
		
		Integer numberOfListings = sbu.parseResultCountHeader();

		assertTrue(numberOfListings == 0);
	}

	/*
	 * Test Case ID: TC_SB_005
	 */
	@Test(enabled = true)
	public void consecutive_search() {
		String searchTerm1 = "GTX 960";
		sbu.searchAndSubmit_Enter(searchTerm1);
		
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		String searchTerm2 = "GTX 1080Ti";
		searchBar.clear();
		searchBar.sendKeys(searchTerm2);
		searchBar.submit();		
		Integer numberOfListings = sbu.parseResultCountHeader();

		assertTrue(numberOfListings > 0);
	}

	/*
	 * Test Case ID: TC_SB_006
	 */
	@Test(enabled = true)
	public void expensive_search_with_short_search_term_submit_with_enter() {
		String shortSearchTerm = "a";
		sbu.searchAndSubmit_Enter(shortSearchTerm);
		
		
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
		

		String expected_error_message_substring = "The request contains expensive keywords";
		WebElement error_message_element = driver.findElement(By.xpath("//div[@class='s-error']/div[1]/p"));
		
		assertTrue(error_message_element.getText().contains(expected_error_message_substring));
	}
}
