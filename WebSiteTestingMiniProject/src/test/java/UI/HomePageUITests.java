package UI;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import utility.SearchBarUtility;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.Runtime;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class HomePageUITests {
	String configFilePath = "src/test/java/config.properties";
	Properties config = new Properties();
	WebDriver driver = null;
	SearchBarUtility sbu = null;


	@BeforeTest
	public void beforeTest() throws FileNotFoundException, IOException {
		config.load(new FileInputStream(configFilePath));
		System.setProperty("webdriver.chrome.driver", config.getProperty("chromeDriver"));
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = new ChromeDriver();
		driver.get("https://www.ebay.com.au/");
		sbu = new SearchBarUtility(driver);

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void AfterMethod() {
		driver.quit();
	}

	/*
	 * Test Case ID: TC_UI_001
	 */
	@Test
	public void testHomePageCarouselSliderNextFunction() {
		
		// driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]/div/div[@aria-hidden=\"false\"]"));
		WebElement sliderElements1 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style1 = sliderElements1.getAttribute("style");

		// WebElement currentSliderElement =
		// sliderElements.findElement(By.cssSelector("[aria-hidden=false]"));

		driver.findElement(By.xpath("//*[@id=\"s2-4[0]\"]")).click();

		WebElement SliderElement2 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));

		String style2 = SliderElement2.getAttribute("style");

		assertNotSame(style1, style2);

		driver.findElement(By.xpath("//*[@id=\"s2-1[0]\"]")).click();
		WebElement sliderElements3 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style3 = sliderElements3.getAttribute("style");
		assertNotEquals(style3, style2);
		
		driver.findElement(By.id("ElementID")).clear();
	
	}

	/*
	 * Test Case ID: TC_UI_002
	 */
	@Test
	public void testHomePageCarouselSliderPauseFunction() throws InterruptedException {
		WebElement sliderElements1 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style1 = sliderElements1.getAttribute("style");
		driver.findElement(By.xpath("//*[@id=\"s2\"]/div/button[2]")).click();

		Thread.sleep(5000);

		WebElement sliderElements2 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style2 = sliderElements2.getAttribute("style");
		assertEquals(style1, style2);

		driver.findElement(By.xpath("//*[@id=\"s2\"]/div/button[1]")).click();

		Thread.sleep(6000);

		WebElement sliderElements3 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style3 = sliderElements3.getAttribute("style");
		assertNotEquals(style2, style3);
	}

	/*
	 * Test Case ID: TC_UI_003
	 */
	@Test
	public void testHomePageCategoryDropDownToggle() {
		WebElement categoryDropdownButton = driver.findElement(By.xpath("//*[@id=\"gh\"]/table/tbody/tr/td[2]"));
		WebElement categoryDropdownTable = driver.findElement(By.xpath("//*[@id=\"gh-sbc-o\"]"));
		String displayBeforeClick = categoryDropdownTable.getCssValue("display");
		// assertThat(styleBeforeClick, is(equalTo("display: none;")));
		assertEquals("none", displayBeforeClick);
		sbu.waitForLoad(driver);
		categoryDropdownButton.click();
		String displayAfterClick = categoryDropdownTable.getCssValue("display");
		assertEquals("block", displayAfterClick);

		categoryDropdownButton.click();
		displayAfterClick = categoryDropdownTable.getCssValue("display");
		assertEquals("none", displayAfterClick);

	}

	/*
	 * Test Case ID: TC_UI_004	 */
	@Test
	public void testHomePageCategoryDropDownNavigation() {
		WebElement categoryDropdownButton = driver.findElement(By.xpath("//*[@id=\"gh\"]/table/tbody/tr/td[2]"));
		WebElement categoryDropdownTable = driver.findElement(By.xpath("//*[@id=\"gh-sbc-o\"]"));
		sbu.waitForLoad(driver);
		categoryDropdownButton.click();
		// Click Collectables
		driver.findElement(By.xpath("//*[@id=\"gh-sbc\"]/tbody/tr/td[1]/h3[1]/a")).click();

		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);

		assertThat(pageTitle, containsString("Collectables"));
	}

	/*
	 * Test Case ID: TC_UI_005
	 */
	@Test
	public void testHomePageHoverDropDownToggle() throws InterruptedException {

		WebElement HoverCategoryLabel = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]"));
		WebElement HoverCategoryFlyout = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]"));
		assertFalse(HoverCategoryFlyout.isDisplayed());
		
		sbu.waitForLoad(driver);

		Actions builder = new Actions(driver);
		builder.moveToElement(HoverCategoryLabel).build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 5);
		// wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]/div[1]/div[1]/ul/li[1]/a")));
		try {
			wait.until(ExpectedConditions.visibilityOf(HoverCategoryFlyout));
		} catch (TimeoutException e) {
			System.out.println("timeout exception");
		}
		
		assertTrue(HoverCategoryFlyout.isDisplayed());

		builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"gh-logo\"]"))).build().perform();

		try {
			wait.until(ExpectedConditions.invisibilityOf(HoverCategoryFlyout));
		} catch (TimeoutException e) {
			System.out.println("timeout exception");
		}

		assertFalse(HoverCategoryFlyout.isDisplayed());

	}

	/*
	 * Test Case ID: TC_UI_006
	 */
	@Test
	public void testHomePageHoverDropDownNavigation() throws InterruptedException {
		WebElement HoverCategoryLabel = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]"));
		WebElement HoverCategoryFlyout = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]"));
		WebElement LinkInFlyout = driver
				.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]/div[1]/div[1]/ul/li[1]/a"));
		assertFalse(HoverCategoryFlyout.isDisplayed());
		
		sbu.waitForLoad(driver);

		Actions builder = new Actions(driver);
		builder.moveToElement(HoverCategoryLabel).build().perform();

		WebDriverWait wait = new WebDriverWait(driver, 5);

		try {
			wait.until(ExpectedConditions.elementToBeClickable(LinkInFlyout));
		} catch (TimeoutException e) {
			System.out.println("timeout exception");
		}

		assertTrue(HoverCategoryFlyout.isDisplayed());

		LinkInFlyout.click();

		String pageTitle = driver.getTitle();

		assertThat(pageTitle, containsString("Women's Clothing"));
	}

}
