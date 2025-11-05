package com.BasePage;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class StartLoanParameter extends BaseClass {
	
    @FindBy(id = "branchCode")
    private WebElement BRANCH;

    @FindBy(id = "prodCode")
    private WebElement PRODUCT;

    @FindBy(id = "schemeCode")
    private WebElement SCHEME;

    @FindBy(id = "copyParamN")
    private WebElement copypath;
	
	public StartLoanParameter(WebDriver driver){
		
		PageFactory.initElements(driver, this);      
	}
	

		public void branch() {
			BRANCH =driver.findElement(By.id("branchCode"));
			Select s = new Select(BRANCH);
//			s.selectByValue("101");        //for single branch selection
			
			List<WebElement> go = s.getOptions();
			
			for (int i = 0; i < go.size(); i++) {
			    String txt = go.get(i).getText();
			    go.get(i).click();
			    System.out.println(txt);
			    
		}
			
//		public void product() {
//			
//		}
	
	
  }
}