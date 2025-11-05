package com.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ConfigTermLoan extends BaseClass{

	@FindBy(xpath  ="//html/body/div[3]/header/div[1]/div//div/a")	
	private WebElement MENU;
    
	@FindBy(id  ="loginId")	
    private WebElement CONFIG;
    
	@FindBy(id  ="loginId")	
    private WebElement TERMLOANS;
	
    private WebElement LOANPARM;
    private WebElement CREATE;
//    private WebElement BRANCH;
//    private WebElement BRANCH_SEARCH;
	
	public ConfigTermLoan(WebDriver driver) {
		
		PageFactory.initElements(driver, this);
//		MENU =driver.findElement(By.xpath("//a[@data-original-title='Menu']"));
//		CONFIG =driver.findElement(By.id("Configuration"));
//		TERMLOANS = driver.findElement(By.id("termloanConfig"));
//		LOANPARM = driver.findElement(By.id("PARAMSLOAN"));  
//		CREATE = driver.findElement(By.className("action-container"));
//		BRANCH = driver.findElement(By.className("select2-selection__placeholder"));
//		BRANCH_SEARCH = driver.findElement(By.className("select2-branchCode-result-3rmd-101"));
	}
	
    public void menu() {
    	MENU.click();  
    }
    public void config() {
    	CONFIG.click();
    }
    public void termloans() {
    	TERMLOANS.click();
    }  
    public void loanparam() {
    	LOANPARM.click();  
    }
    public void create() { 
    	CREATE = driver.findElement(By.id("addButton"));
    	CREATE.click();    
    } 
}
