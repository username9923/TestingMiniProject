package searchBar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertNotEquals;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SearchBarTest2 {
	String configFilePath = "src/test/java/config.properties";
	Properties config = new Properties();
	WebDriver driver = null;
	
	@BeforeMethod
	public void beforeMethod() throws FileNotFoundException, IOException {
		config.load(new FileInputStream(configFilePath));
		System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriver"));
		driver = new ChromeDriver();
		
		driver.get("https://www.ebay.com.au/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
	
	/*
	 * Test Case ID: TC_SB_010
	 */
	@Test
	public void TestSearchWhenSellersExists() {
		String searchTerm = "Headphones";
		String searchURL = "https://www.ebay.com.au/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw=" + searchTerm + "&_sacat=0";
		driver.navigate().to(searchURL);
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertNotEquals("0 results",resultsElement.getText());
	}
	
	/*
	 * Test Case ID: TC_SB_011
	 */
	@Test
	public void TestSearchWhenNoSellersExists() {
		String searchTerm = "ahfousefalsclasunelffalj";
		String searchURL = "https://www.ebay.com.au/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw=" + searchTerm + "&_sacat=0";
		driver.navigate().to(searchURL);
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertEquals("0 results",resultsElement.getText());	
	}
	
	/*
	 * Test Case ID: TC_SB_012
	 */
	@Test 
	public void TestSearchAfterBackFunction() {
		driver.navigate().to("https://www.ebay.com.au/help/home");
		driver.navigate().back();
		String searchTerm = "Headphones";
		driver.findElement(By.xpath("//*[@id=\"gh-ac\"]")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).click();
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertThat(resultsElement.getText(),is(not("0 results")));
	}
	
	/*
	 * Test Case ID: TC_SB_013
	 */
	@Test 
	public void TestSearchAfterForwardFunction() {
		driver.navigate().to("https://www.ebay.com.au/help/home");
		driver.navigate().to("https://www.ebay.com.au");
		driver.navigate().back();
		driver.navigate().forward();
		String searchTerm = "Headphones";
		driver.findElement(By.xpath("//*[@id=\"gh-ac\"]")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).click();
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertThat(resultsElement.getText(),is(not("0 results")));
	}
	
	/*
	 * Test Case ID: TC_SB_014
	 */
	@Test
	public void TestNavigateToListingThroughImage() {
		String searchTerm = "Headphones";
		driver.findElement(By.xpath("//*[@id=\"gh-ac\"]")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).click();
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertThat(resultsElement.getText(),is(not("0 results")));
		driver.findElement(By.xpath("//*[@id=\"srp-river-results-listing1\"]/div/div[1]/div/a[1]/div")).click();
		driver.findElement(By.xpath("//*[@id=\"srp-river-results-listing1\"]/div/div[1]/div/a[1]/div")).click();
		assertThat(driver.getCurrentUrl(), containsString("https://www.ebay.com.au/itm/"));
	}
	
	/*
	 * Test Case ID: TC_SB_015
	 */
	@Test
	public void TestNavigateToListingThroughText() {
		String searchTerm = "Headphones";
		driver.findElement(By.xpath("//*[@id=\"gh-ac\"]")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).click();
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		assertThat(resultsElement.getText(),is(not("0 results")));
		driver.findElement(By.xpath("//*[@id=\"srp-river-results-listing1\"]/div/div[2]/a/h3")).click();
		driver.findElement(By.xpath("//*[@id=\"srp-river-results-listing1\"]/div/div[2]/a/h3")).click();
		assertThat(driver.getCurrentUrl(), containsString("https://www.ebay.com.au/itm/"));
	}
	
	/*
	 * Test Case ID: TC_SB_016
	 */
	@Test
	public void TestSearchResultsEqualsNumberOfListings() {
		String searchTerm = "1/700 Watef Linw Beries Nj.360 Japaeese Nady liyht cruyser Sbigeru";
		driver.findElement(By.xpath("//*[@id=\"gh-ac\"]")).sendKeys(searchTerm);
		driver.findElement(By.xpath("//*[@id=\"gh-btn\"]")).click();
		WebElement resultsElement = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		String stringNumberOfResults = resultsElement.getText();
		stringNumberOfResults = stringNumberOfResults.replaceAll("\\D+", "");
		int intNumberOfResults = Integer.parseInt(stringNumberOfResults);
		
		List<WebElement> elementList = driver.findElements(By.xpath("//*[@id=\"srp-river-results\"]/ul/li[@class=\"s-item\"]"));
		int elementsOnPage = elementList.size();
		
		assertThat(intNumberOfResults,is(elementsOnPage));
	}
	
}
