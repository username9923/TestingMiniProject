package SeleniumTestCases;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class ExampleTestCase {
	WebDriver driver = null;
	String url = new String();
	
	@BeforeClass
	public void beforeClass() {
	}

	@AfterClass
	public void afterClass() {
	}

	@BeforeTest
	public void beforeTest() { 
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\695078\\Documents\\Drivers\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

	@Test
	public void Example1() {
		String webAddress = "http://www.ebay.com.au";
		driver.get(webAddress);
	}

}
