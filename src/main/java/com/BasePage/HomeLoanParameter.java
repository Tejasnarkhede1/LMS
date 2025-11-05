package com.BasePage;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class HomeLoanParameter extends BaseClass{
//	static WebDriver driver;
	@FindBy(id  ="loginId")
    private WebElement USERNAME;
	
    @FindBy(id="uiPwd")
    private WebElement PASSWORD;
    
    @FindBy(id="userLogin")
    private WebElement LOGIN;
    
    @FindBy(xpath = "/html/body/div[3]/header/div[1]/div/div/div/div/a/div/div")
    public WebElement MENU; 
    
    public HomeLoanParameter(WebDriver driver) {
	    PageFactory.initElements(driver,this) ;

//    	USERNAME = driver.findElement(By.id("loginId")); 
//    	PASSWORD = driver.findElement(By.id("uiPwd"));
//    	LOGIN = driver.findElement(By.id("userLogin"));
    }
    
     // Modify this method to use the passed parameter 'text'
     public  void username(String user) {
         USERNAME.sendKeys(user);  // Use the provided 'text' parameter
     }

     public void password(String pass) {
         PASSWORD.sendKeys(pass);  
     }

     public void  login() {
         LOGIN.click(); 
     }
     
//     public void menu() {
//    	MENU=driver.findElement(By.xpath("//a[@data-original-title='Menu']"));
//     	MENU.click(); 
//     }
}