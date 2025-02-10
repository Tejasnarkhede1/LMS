package com.verifyBasePage;

import java.io.IOException;

import org.testng.annotations.Test;

import com.BasePage.BaseClass;
import com.BasePage.ConfigTermLoan;
import com.BasePage.ExcelDataProvider;
import com.BasePage.HomeLoanParameter;
import com.BasePage.StartLoanParameter;

public class VerifyLoanParameter extends BaseClass {
    
    @Test
    public void verifyBasePage() throws IOException {
       
        HomeLoanParameter hlp = new HomeLoanParameter();  // Pass the WebDriver instance
        
//        hlp.username("demo1"); 
//        hlp.password("Abcd@1243"); 
//        hlp.login(); 
        
        ExcelDataProvider edp = new ExcelDataProvider();
		edp.getData();
		
		String user = edp.getStringData("Sheet1", 0, 0);
		hlp.username(user);
		String pass = edp.getStringData("Sheet1", 1, 0);
		hlp.password(pass);
		hlp.login();
        
        ConfigTermLoan tl = new ConfigTermLoan();  // Config menu of Loan parameter
        tl.menu(); 
        ConfigTermLoan t2 = new ConfigTermLoan();    
        t2.config();
        ConfigTermLoan t3 = new ConfigTermLoan();
        t3.termloans();
        ConfigTermLoan t4 = new ConfigTermLoan();
        t4.loanparam();
        ConfigTermLoan t5 = new ConfigTermLoan();
        t5.create();
        
        StartLoanParameter p1 = new StartLoanParameter();
//        p1.startloanparameter();
    }
}
