package com.academy.techcenture.ecommerce.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HomePage extends BasePage{

    public HomePage(WebDriver driver) {
       super(driver);
    }

    //web elements
    @FindBy(xpath = "//a[@class='login']")
    protected WebElement signInLink;

    @FindBy(linkText = "Contact us")
    protected WebElement contactUsLink;

    @FindBy(className = "logout")
    protected WebElement signOutLink;

    @FindBy(id = "search_query_top")
    protected WebElement searchInputBox;

    @FindBy(xpath = "//button[contains(@class,'button-search')]")
    protected WebElement searchBtn;

    @FindBy(xpath = "//section[@id='social_block']//li")
    protected List<WebElement> socialLinks;

    @FindBy(xpath = "//h4[.='Follow us']")
    protected WebElement followUsLink;

    @FindBy(xpath = "//div[@id='block_top_menu']/ul/li/a")
    protected List<WebElement> topMenuTabs;

    @FindBy(xpath = "//ul[contains(@class,'submenu-container')]/li/ul/li/a")
    protected List<WebElement> womenProducts;

    @FindBy(xpath = "(//ul[contains(@class,'submenu-container')])[2]/li/a")
    protected List<WebElement> typesOfDresses;

    @FindBy(xpath = "//div[@class='ac_results']//li[1]")
    protected WebElement dropdownFirstItem;

    public void clickSingInLink() throws InterruptedException {
        assertTrue(signInLink.isEnabled(), "Sign in link was not displayed");
        Thread.sleep(3000);
        wait.until(ExpectedConditions.elementToBeClickable(signInLink));
        signInLink.click();
        wait.until(ExpectedConditions.titleIs("Login - My Store"));
    }

    public void clickContactUsLink(){
        assertTrue(contactUsLink.isDisplayed(), "Contact us link is not displayed");
        contactUsLink.click();
        System.out.println("clicking contact us link");
    }

    public void signOut(){

        if (signOutLink.isDisplayed()){
            signOutLink.click();
      //      wait.until(ExpectedConditions.titleIs("Login - My Store"));
        }
    }

    public void verifySocialNetworks() throws InterruptedException {

        //1. we can scroll down to Follow us web elment
        //2. we can scroll down till the end of the page

        JavascriptExecutor js = (JavascriptExecutor) driver;
        //scroll to followUsLink
        js.executeScript("arguments[0].scrollIntoView();", followUsLink);

        //scrolls to bottom of page
  //      js.executeScript("window.scrollTo(0, document.body.scrollHeight)");

        String mainWindow = driver.getWindowHandle(); //this contains the id of the main page

        for (int i = 0; i < socialLinks.size(); i++) {

            socialLinks.get(i).click(); //click on each social link

            Set<String> windowHandles = driver.getWindowHandles();
            for(String current : windowHandles ){
                if (!current.equals(mainWindow)){
                    driver.switchTo().window(current);
                    break;
                }
            }

            String currentUrl = driver.getCurrentUrl(); //current session id on the social network page

            Thread.sleep(2000);

            if (currentUrl.contains("facebook.com")){
               assertEquals(driver.getTitle(), "Selenium Framework | Facebook", "Facebook titles not matching");
            }
            else if(currentUrl.contains("twitter.com")){
                wait.until(ExpectedConditions.titleIs("Selenium Framework (@seleniumfrmwrk) / Twitter"));
                assertEquals(driver.getTitle(), "Selenium Framework (@seleniumfrmwrk) / Twitter", "Twitter titles not matching");
            }
            else if(currentUrl.contains("youtube.com")){
                assertEquals(driver.getTitle(), "Selenium Framework - YouTube", "Youtube titles not matching");
            }else if(currentUrl.contains("google.com")){
                assertEquals(driver.getTitle(), "Sign in - Google Accounts", "Google titles not matching");
            }

           driver.close(); //close the current active tab
            driver.switchTo().window(mainWindow); //switch the focus back to home page (automationpractice.com)

        }
    }

    public void verifyProductTabs(Map<String,String> data) throws InterruptedException {

        for (int i = 0; i < topMenuTabs.size(); i++) {

            WebElement tab = topMenuTabs.get(i);

            Actions actions = new Actions(driver);

            actions.moveToElement(tab).build().perform();

            Thread.sleep(3000);
            switch (tab.getText().toUpperCase()){
                case "WOMEN":
                    String[] women = data.get("women").split(",");
                    for (int j = 0; j < women.length; j++) {
                        assertEquals(womenProducts.get(i).getText().toLowerCase(), women[i]);
                    }
                    break;
                case "DRESSES":
                    String[] dresses = data.get("dresses").split(",");
                    for (int j = 0; j < dresses.length; j++) {
                        assertEquals(typesOfDresses.get(i).getText().toLowerCase(), dresses[i]);
                    }
                    break;
                case "T-SHIRTS":
                    break;
            }

        }
    }

    public void searchProduct(Map<String,String> data) {
        searchInputBox.sendKeys(data.get("Name"));
        wait.until(ExpectedConditions.visibilityOf(dropdownFirstItem));
        dropdownFirstItem.click();
    }
}
















