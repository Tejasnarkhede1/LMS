package com.verifyBasePage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.BasePage.BaseClass;
import com.BasePage.ConfigTermLoan;
import com.BasePage.ExcelDataProvider;
import com.BasePage.HomeLoanParameter;
import com.BasePage.LoanPage;

public class AppTest extends BaseClass {

    private static final Logger logger = LogManager.getLogger(AppTest.class);

    HomeLoanParameter loginPage;
    ConfigTermLoan configPage;
    ExcelDataProvider excel;
    LoanPage loanpage;

    @BeforeClass
    public void setUp() throws Exception {
        loginPage = new HomeLoanParameter(driver);
        configPage = new ConfigTermLoan(driver);
        loanpage = new LoanPage(driver);  // ✅ Initialized LoanPage

        excel = new ExcelDataProvider();
        excel.getData();
        logger.info("Excel data loaded successfully."); 
    }

    @Test(priority = 1)
    public void loginTest() {
        String username = excel.getStringData("Sheet1", 0, 0);
        String password = excel.getStringData("Sheet1", 0, 1);

        logger.info("Username: " + username);
        logger.info("Password: " + password);

        loginPage.username(username);
        loginPage.password(password);
        loginPage.login();

        logger.info("Login test executed.");
    }

    @Test(priority = 2, dependsOnMethods = "loginTest")
    public void navigateToLoanAccountCreation() throws InterruptedException {

        loanpage.navigateToLoanPage(); // Optional step if navigation is needed

        loanpage.fillLoanDetails();
        loanpage.selectCustomer();
        loanpage.selectProduct();
        loanpage.selectScheme();
        loanpage.loanDetails();
        loanpage.paymentMethod();
        loanpage.paymentFrequency();
        loanpage.m();
        loanpage.reacommondedDetails();
        loanpage.clickNext();
        loanpage.db();
        loanpage.clickNext();
        loanpage.clickNext2();
        loanpage.savebutton();     // ✅ Fixed
        loanpage.confirm();        // ✅ Optional, but important

        logger.info("Loan account creation flow completed successfully.");
    }
}
