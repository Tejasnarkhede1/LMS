package com.BasePage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
public class LoanPage {

    WebDriver driver;

    JavascriptExecutor js;
 
    public LoanPage(WebDriver driver) {

        this.driver = driver;

        this.js = (JavascriptExecutor) driver;

    }
 
    public void navigateToLoanPage() throws InterruptedException {

        driver.findElement(By.xpath("/html/body/div[3]/header/div[1]/div/div/div/div/a/div")).click();

        Thread.sleep(500);

        driver.findElement(By.id("termLoans")).click();

        Thread.sleep(1000);

        driver.findElement(By.id("LOANLIMIT")).click();

    }
 
    public void fillLoanDetails() throws InterruptedException {

        driver.findElement(By.id("addButton")).click();

        Thread.sleep(500);

    }
 
    public void selectCustomer() {

        driver.findElement(By.id("custAcctNumberF2")).click();

        driver.findElement(By.xpath("//table[@id='dt-solrCustomerData']//input[@id='memberNameSearch']"))

              .sendKeys("aman");

        driver.findElement(By.xpath("/html/body/div[12]/form/div/div[97]/div[2]/div/fieldset/div/div[3]/div/div/div/div/div/div/div/div/table/tbody/tr[1]")).click();

    }
 
    public void selectProduct() throws InterruptedException {

        driver.findElement(By.id("productCodeF2")).click();

        Thread.sleep(1000);

       WebElement mr = driver.findElement(By.xpath("//table[@id='dt-prdSearchdata']//tr[td[contains(text(),'Auto Loan')]]"));

       mr.click();
 
      

    }
 
    public void selectScheme() throws InterruptedException {

        driver.findElement(By.id("schemeCodeF2")).click();

        Thread.sleep(1000);

        WebElement schemeOption = driver.findElement(By.xpath("/html/body/div[12]/form/div/div[62]/div[2]/div/fieldset/div/div/div/div/div/div/div/div/div/div/div/div[2]/div/div/table/tbody/tr/td[2]"));

        js.executeScript("arguments[0].click();", schemeOption);

        Thread.sleep(500);

        js.executeScript("scroll(0, 1500)");

    }
 
    public void loanDetails() {

        WebElement loanAmount = driver.findElement(By.id("loanAmount_txt"));

        loanAmount.clear();

        loanAmount.sendKeys("100000");
 
        WebElement loanTerm = driver.findElement(By.id("loanTerm"));

        loanTerm.clear();

        loanTerm.sendKeys("12");

    }
 
    public void paymentMethod() {

        Select paymentMethod = new Select(driver.findElement(By.id("loanPaymentMethod")));

        paymentMethod.selectByIndex(1);

    }
 
    public void paymentFrequency() {

        Select paymentFrequency = new Select(driver.findElement(By.id("frequency")));

        paymentFrequency.selectByIndex(5);


     // Step 1: Click the date input field to open the calendar

        driver.findElement(By.name("disbursementDateStr")).click();  // Replace with actual ID
 
        // Step 2: Select Year

        Select yearSelect = new Select(driver.findElement(By.className("ui-datepicker-year")));

        yearSelect.selectByVisibleText("2025");
 
        // Step 3: Select Month (e.g., August = index 7, January = 0)

        Select monthSelect = new Select(driver.findElement(By.className("ui-datepicker-month")));

        monthSelect.selectByVisibleText("Jan");
 
        // Step 4: Click on the specific date (e.g., 18)

        driver.findElement(By.xpath("//a[text()='30']")).click();
 
    }
 
    

    public void m() {

        driver.findElement(By.id("noOfInstallments")).click();

        js.executeScript("scroll(0, 1000)");

    }

    public void reacommondedDetails() {

        Select recommendingUser = new Select(driver.findElement(By.id("recommendingUsercode")));

        recommendingUser.selectByIndex(5);
 
        driver.findElement(By.id("recommendingAuthority")).sendKeys("rytidndfj");
 
        Select sanctioningAuthority = new Select(driver.findElement(By.id("sanctioningAuthorityUser")));

        sanctioningAuthority.selectByIndex(5);
 
        WebElement supervisorInput = driver.findElement(By.xpath("/html/body/div[2]/main/div/div[1]/div/div/div[2]/div[2]/div[1]/div/div/fieldset/div[3]/div[1]/fieldset[3]/div[2]/div/div/div[4]/div/div/input"));

        supervisorInput.sendKeys("trafdugyg");
 
        driver.findElement(By.id("loanOfficer")).sendKeys("gvhbjkjhg");
 
        js.executeScript("scroll(0, 1800)");

    }
 
    public void clickNext() {

        WebElement nextButton = driver.findElement(By.id("nextBtn"));

        new Actions(driver).click(nextButton).perform();

    }
 
    public void db() {

        js.executeScript("document.body.style.zoom='80%'");
 
        driver.findElement(By.xpath("//input[@type='radio' and contains(@class, 'btn-ml')]")).click();
 
        Select payableMethod = new Select(driver.findElement(By.id("payableMethod")));

        payableMethod.selectByValue("5");
 
        driver.findElement(By.id("addDisbRecord")).click();

        driver.findElement(By.id("modalClose2")).click();

        js.executeScript("scroll(0, 700)");

    }


    public void clickNext2() throws InterruptedException {

        driver.findElement(By.xpath("//button[@id='nextBtn']\r\n"

        		+ "")).click();

        js.executeScript("scroll(0, 900)");

        for (int i = 0; i < 5; i++) {

            driver.findElement(By.id("nextBtn")).click();

            Thread.sleep(500);

            js.executeScript("scroll(0, 900)");

        }}

        public void savebutton() {

    		WebElement save = driver.findElement(By.xpath("//button[@class=\"button cnf-btn\"]"));
 
    		save.click();

    	}

    	public void confirm() {

    		driver.findElement(By.xpath("//a[@id=\"submitForm\"]")).click();
 
        }

    }

 
