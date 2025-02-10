package com.BasePage;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class StartLoanParameter extends BaseClass {
	
	private WebElement BRANCH;
	private WebElement PRODUCT;
	private WebElement SCHEME ;
	private WebElement copypath;
	
	public StartLoanParameter() {
		BRANCH =driver.findElement(By.id("branchCode"));
		Select s = new Select(BRANCH);
//		s.selectByValue("101");        //for single branch selection
		
		List<WebElement> go = s.getOptions();
		
		for (int i = 0; i < go.size(); i++) {
		    String txt = go.get(i).getText();
		    go.get(i).click();
		    System.out.println(txt);               //for multiple branches selection
//		    if (txt.equals("101 - BURDWAN MAIN BRANCH")) {
//		        go.get(i).click();  // Select the option with value 101
//
//		    }
		}
		
		PRODUCT = driver.findElement(By.id("prodCode"));
		Select s1 = new Select(PRODUCT);
		s1.selectByVisibleText("102-housing T loan");
		
		SCHEME = driver.findElement(By.id("schemeCode"));
		Select s2 = new Select(SCHEME);
		s2.selectByVisibleText("102-housing T loan");
		
		copypath = driver.findElement(By.id("copyParamN"));
		copypath.click();
	}
	
//   public void startloanparameter() {
//	   
//	   BRANCH.click();
//    }
	
	
}
