package tests.josh;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

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
	WebDriver driver;
	
	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\695136\\Software\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.ebay.com.au/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
	
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
		
//		WebElement hideSuggestions = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"ui-id-1\"]/li[last()]")));
//		wait.until(ExpectedConditions.visibilityOf(hideSuggestions));
//		hideSuggestions.click();
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
