package utility;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchBarUtility {
	
	private WebDriver driver = null;
	
	public SearchBarUtility(WebDriver driver){
		this.driver = driver;
	}
	
	/*
	 * Sends keys to the ebay product search bar
	 * 
	 * @param searchTerm : search term to input into searchBar
	 * 
	 * @return: a WebElement used to interact with the search bar in later test
	 * methods
	 */
	public WebElement sendKeysToSearchBar(String searchTerm) {
		WebElement searchBar = driver.findElement(By.xpath("//*[@id=\"gh-ac\"]"));
		searchBar.sendKeys(searchTerm);

		return searchBar;
	}

	/*
	 * Sends keys to the ebay product search bar and submits with enter key
	 * 
	 * @param searchTerm : search term to input into searchBar
	 * 
	 * @return: a WebElement used to interact with the search bar in later test
	 * methods
	 */
	public WebElement searchAndSubmit_Enter(String searchTerm) {
		WebElement searchBar = sendKeysToSearchBar(searchTerm);
		searchBar.submit();

		return searchBar;
	}

	/*
	 * Sends keys to the ebay product search bar and submits by clicking
	 * confirmation button
	 * 
	 * @param searchTerm : search term to input into searchBar
	 * 
	 * @return: a WebElement used to interact with the search bar in later test
	 * methods
	 */
	public WebElement searchAndSubmit_ButtonClick(String searchTerm) {
		WebElement searchBar = sendKeysToSearchBar(searchTerm);
		WebElement searchButton = driver.findElement(By.xpath("//*[@id='gh-btn']"));
		searchButton.click();

		return searchBar;
	}

	/*
	 * Fetches the number of listings from the result count header in the results
	 * page
	 * 
	 * @return: result count in integer format
	 */
	public Integer parseResultCountHeader() {
		WebElement resultsHeader = driver.findElement(By.xpath(
				"//*[@id='srp-river-results-SEARCH_STATUS_MODEL_V2-w0']/div[2]/div[1]/div[1]/h1[contains (text(),'results')]"));
		Integer numberOfListings = Integer.parseInt(resultsHeader.getText().replaceAll(" results", ""));

		return numberOfListings;
	}
	
	// Waits for page to be in a ready state
	public void waitForLoad(WebDriver driver) {
		ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			}
		};
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(pageLoadCondition);
	}
}
