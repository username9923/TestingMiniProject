package searchBar;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class SearchBarTest {
	
	WebDriver driver = null;
	String url = new String();
	
	@BeforeTest
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\695078\\Documents\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@BeforeMethod
	public void beforeMethod() {
		String webAddress = "http://www.ebay.com.au";
		driver.get(webAddress);
	}
	
	@Test(enabled = true)
	public void TC_SB_001()
	{	
		String searchTerm = "Nvidia GTX 1080Ti";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		WebElement resultsHeader = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		Integer NumberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		assertTrue(NumberOfListings > 0);
	}
	
	@Test(enabled = true)
	public void TC_SB_002()
	{	
		String searchTerm = "Nvidia GTX 1080Ti";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='gh-btn']"));
		searchButton.click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		WebElement resultsHeader = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		Integer NumberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		assertTrue(NumberOfListings > 0);
	}
	
	@Test(enabled = true)
	public void TC_SB_003()
	{	
		String searchTerm = "siudoad";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		WebElement resultsHeader = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		Integer NumberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		assertTrue(NumberOfListings == 0);
	}
	
	@Test(enabled = true)
	public void TC_SB_004()
	{	
		String searchTerm = "siudoad";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='gh-btn']"));
		searchButton.click();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		WebElement resultsHeader = driver.findElement(By.xpath("//*[@id=\"srp-river-results-SEARCH_STATUS_MODEL_V2-w0\"]/div[2]/div[1]/div[1]/h1"));
		Integer NumberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		assertTrue(NumberOfListings == 0);
	}
	
	@Test(enabled = true)
	public void TC_SB_005()
	{	
		String searchTerm1 = "GTX 960";
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm1);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
				
		searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		String searchTerm2 = "GTX 1080Ti";
		searchBar.clear();
		searchBar.sendKeys(searchTerm2);
		searchBar.submit();
		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS) ;
		
		WebElement resultsHeader = driver.findElement(By.xpath("//*[@id='srp-river-results-SEARCH_STATUS_MODEL_V2-w0']/div[2]/div[1]/div[1]/h1[contains(text(),'results')]"));
		Integer NumberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));
		
		assertTrue(NumberOfListings > 0);
	}

	@AfterTest
	public void tearDown() {
		driver.quit();
	}
}
