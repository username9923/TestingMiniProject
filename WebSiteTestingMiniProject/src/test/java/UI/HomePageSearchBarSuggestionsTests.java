package UI;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;

public class HomePageSearchBarSuggestionsTests {
	String configFilePath = "src/test/java/config.properties";
	Properties config = new Properties();
	
	WebDriver driver;
	
	@BeforeTest
	public void setUp() {
		String configFilePath = "src/test/java/config.properties";
		Properties config = new Properties();
	}
	
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
	 * Test Case ID: TC_SB_017
	 */
	@Test
	public void testHomePageSearchBarSugestionsIsPresent() {
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys("cat");
		WebElement suggestionsMenu = null;
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			suggestionsMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ui-id-1\"]")));
			wait.until(ExpectedConditions.visibilityOf(suggestionsMenu));
		} catch (TimeoutException e) {
			System.out.println("Suggestions Timeout Exception");
		}
		assertTrue(suggestionsMenu.isDisplayed());
	}
	
	/*
	 * Test Case ID: TC_SB_018
	 */
	@Test
	public void testHomePageSearchBarSuggestionsToggling() {
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys("c");
		
		WebElement suggestionsMenu = null;
		System.out.println("mark 1");
		WebDriverWait wait = new WebDriverWait(driver, 10);
		try {
			suggestionsMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ui-id-1\"]")));
			wait.until(ExpectedConditions.visibilityOf(suggestionsMenu));
			System.out.println("got menu 1");
		} catch (TimeoutException e) {
			System.out.println("Suggestions Timeout Exception 1");
		}
		assertTrue(suggestionsMenu.isDisplayed());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[contains(text(), \"Hide eBay suggestions\")]")));
		WebElement hideSuggestions = driver.findElement(By.xpath("//*[contains(text(), \"Hide eBay suggestions\")]"));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[contains(text(), \"Hide eBay suggestions\")]"))));
		driver.findElement(By.xpath("//*[contains(text(), \"Hide eBay suggestions\")]")).click();

		assertFalse(suggestionsMenu.isDisplayed());
		
		WebElement showSuggestions = driver.findElement(By.xpath("//*[@id=\"ghAC-show\"]"));
		showSuggestions.click();
		try {
			wait.until(ExpectedConditions.visibilityOf(suggestionsMenu));
		} catch (TimeoutException e) {
			System.out.println("Suggestions Timeout Exception");
		}
		assertTrue(suggestionsMenu.isDisplayed());
	}

}
