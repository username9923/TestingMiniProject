package tests.josh;

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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterClass;

import java.lang.Runtime;

public class HomePageUITests {
	WebDriver driver;

	@BeforeTest
	public void beforeTest() {

		System.out.println("--Before Test--");

	}

	@BeforeMethod
	public void beforeMethod() {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\695136\\Software\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.ebay.com.au/");
		System.out.println("--Before Method--");
	}

	@AfterMethod
	public void AfterMethod() {
		driver.quit();
		System.out.println("--After Method--");
	}

	@BeforeClass
	public void beforeClass() {
		System.out.println("--Before Class--");
	}

	@AfterClass
	public void AfterClass() {
		System.out.println("--After Class--");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("--After Test--");
	}

	@Test
	public void testHomePageLogoIsPresent() {
		By locator = By.xpath("//*[@id=\"gh-logo\"]");
		boolean isPresent = isElementPresent(driver, locator);
		assertTrue(isPresent);
	}

	@Test
	public void testHomePageSearchBarIsPresent() {
		By locator = By.xpath("//*[@id=\"gh-ac\"]");
		boolean isPresent = isElementPresent(driver, locator);
		assertTrue(isPresent);
	}

	@Test
	public void testHomePageSearchButtonIsPresent() {
		By locator = By.xpath("//*[@id=\"gh-btn\"]");
		boolean isPresent = isElementPresent(driver, locator);
		assertTrue(isPresent);
	}

	@Test
	public void testHomePageCarouselSliderIsPresent() {
		By locator = By.xpath("//*[@id=\"s2-carouselContainer\"]/div");
		boolean isPresent = isElementPresent(driver, locator);
		assertTrue(isPresent);
	}

	@Test
	public void testHomePageCarouselSliderNextFunction() {

		// driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]/div/div[@aria-hidden=\"false\"]"));
		WebElement sliderElements1 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style1 = sliderElements1.getAttribute("style");

		// WebElement currentSliderElement =
		// sliderElements.findElement(By.cssSelector("[aria-hidden=false]"));

		System.out.println("got the first");

		driver.findElement(By.xpath("//*[@id=\"s2-4[0]\"]")).click();

		WebElement SliderElement2 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));

		String style2 = SliderElement2.getAttribute("style");

		assertNotSame(style1, style2);

		driver.findElement(By.xpath("//*[@id=\"s2-1[0]\"]")).click();

		WebElement sliderElements3 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style3 = sliderElements3.getAttribute("style");
		assertNotEquals(style3, style2);
	}

	@Test
	public void testHomePageCarouselSliderPauseFunction() {
		WebElement sliderElements1 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style1 = sliderElements1.getAttribute("style");
		driver.findElement(By.xpath("//*[@id=\"s2\"]/div/button[2]")).click();
		System.out.println("clicked pause");

		WebDriverWait wait = new WebDriverWait(driver, 5);
		try {
			// waits until timeout, as sliderElements1 will never be invisible
			wait.until(ExpectedConditions.invisibilityOf(sliderElements1));
		} catch (TimeoutException e) {
			System.out.println("Timeout exception ( expected)");
		}

		WebElement sliderElements2 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style2 = sliderElements2.getAttribute("style");
		assertEquals(style1, style2);

		driver.findElement(By.xpath("//*[@id=\"s2\"]/div/button[1]")).click();
		System.out.println("clicked play");

		try {
			// waits until timeout, as sliderElements1 will never be invisible
			wait.until(ExpectedConditions.invisibilityOf(sliderElements1));
		} catch (TimeoutException e) {
			System.out.println("Timeout exception ( expected)");
		}

		WebElement sliderElements3 = driver.findElement(By.xpath("//*[@id=\"s2-carouselContainer\"]"));
		String style3 = sliderElements3.getAttribute("style");
		assertNotEquals(style2, style3);
	}

	@Test
	public void testHomePageCategoryDropDownToggle() {
		WebElement categoryDropdownButton = driver.findElement(By.xpath("//*[@id=\"gh\"]/table/tbody/tr/td[2]"));
		WebElement categoryDropdownTable = driver.findElement(By.xpath("//*[@id=\"gh-sbc-o\"]"));
		String displayBeforeClick = categoryDropdownTable.getCssValue("display");
		// assertThat(styleBeforeClick, is(equalTo("display: none;")));
		assertEquals("none", displayBeforeClick);

		categoryDropdownButton.click();
		String displayAfterClick = categoryDropdownTable.getCssValue("display");
		assertEquals("block", displayAfterClick);

		categoryDropdownButton.click();
		displayAfterClick = categoryDropdownTable.getCssValue("display");
		assertEquals("none", displayAfterClick);

	}

	@Test
	public void testHomePageCategoryDropDownNavigation() {
		WebElement categoryDropdownButton = driver.findElement(By.xpath("//*[@id=\"gh\"]/table/tbody/tr/td[2]"));
		WebElement categoryDropdownTable = driver.findElement(By.xpath("//*[@id=\"gh-sbc-o\"]"));
		categoryDropdownButton.click();
		// Click Collectables
		driver.findElement(By.xpath("//*[@id=\"gh-sbc\"]/tbody/tr/td[1]/h3[1]/a")).click();

		String pageTitle = driver.getTitle();
		System.out.println(pageTitle);

		assertThat(pageTitle, containsString("Collectables"));
	}

	@Test
	public void testHomePageHoverDropDownToggle() {
		/*
		 * WebElement web_Element_To_Be_Hovered =
		 * webDriver.findElement(By.cssSelector(selector_For_Web_Element_To_Be_Hovered))
		 * ; Actions builder = new Actions(getDriver());
		 * builder.moveToElement(web_Element_To_Be_Hovered).build().perform();
		 * 
		 * WebDriverWait wait = new WebDriverWait(driver, 5);
		 * wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(
		 * selector_For_Element_To_Be_Click_After_Hover)));
		 * driver.findElement(By.cssSelector(
		 * selector_For_Element_To_Be_Click_After_Hover)).click();
		 */

		WebElement HoverCategoryLabel = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]"));
		WebElement HoverCategoryFlyout = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]"));
		assertFalse(HoverCategoryFlyout.isDisplayed());

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

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		builder.moveToElement(driver.findElement(By.xpath("//*[@id=\"gh-logo\"]")));

		try {
			wait.until(ExpectedConditions.invisibilityOf(HoverCategoryFlyout));
		} catch (TimeoutException e) {
			System.out.println("timeout exception");
		}

		assertFalse(HoverCategoryFlyout.isDisplayed());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testHomePageHoverDropDownNavigation() {
		WebElement HoverCategoryLabel = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]"));
		WebElement HoverCategoryFlyout = driver.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]"));
		WebElement LinkInFlyout = driver
				.findElement(By.xpath("//*[@id=\"s0-container\"]/li[3]/div[2]/div[1]/div[1]/ul/li[1]/a"));
		assertFalse(HoverCategoryFlyout.isDisplayed());

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
		System.out.println(pageTitle);

		assertThat(pageTitle, containsString("Women's Clothing"));

	}

	public boolean isElementPresent(WebDriver driver, By locator) {
		try {
			driver.findElement(locator);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
