package com.BasePage;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;
    public static ExtentReports extent;
    public static ExtentSparkReporter sparkReporter;
    public static ExtentTest test;
    public static String reportPath;

    @BeforeSuite
    public void setUpReport() {
        // Define the path for the report
        reportPath = System.getProperty("user.dir") + "/reports/AutomationReport.html";
        
        // Initialize the Spark Reporter
        sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setDocumentTitle("Automation Test Report");
        sparkReporter.config().setReportName("Kiya.ai CBS Test Automation");
        sparkReporter.config().setTheme(Theme.DARK);

        // Initialize Extent Reports and attach the reporter
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        // Add system information to the report
        extent.setSystemInfo("Tester", "Tejas Narkhede");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Browser", "Chrome");
    }
    
    @BeforeTest
    public void browserLaunch() {
        // Set up the driver based on the default browser choice
        String browser = "chrome";  // You can change this to "firefox" or use an environment variable if needed
        
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        // Launch the browser and maximize window
        driver.get("http://172.21.0.94:8898/Kiya.aiCBS-10.2.0/LoginPage?tid=139&lang=en");
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void getResult(ITestResult result) throws IOException {
        // Create a new test entry in the report for each test method
        test = extent.createTest(result.getMethod().getMethodName());

        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test Case FAILED: " + result.getName());
            test.log(Status.FAIL, "Failure Reason: " + result.getThrowable());

            // Take screenshot and attach it to the report
            // This line will now work correctly
            String screenshotPath = Screenshot.getScreenshot(driver, result.getName());
            test.addScreenCaptureFromPath(screenshotPath, "Failed Test Screenshot");

        } else if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, "Test Case PASSED: " + result.getName());
            // Optionally, take a screenshot for passed tests
            // String screenshotPath = Screenshot.getScreenshot(driver, result.getName());
            // test.addScreenCaptureFromPath(screenshotPath, "Passed Test Screenshot");

        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test Case SKIPPED: " + result.getName());
        }
    }

    @AfterSuite
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
        
        // Writes the test information to the HTML report
        extent.flush();
        
        // Send the report via email (assuming EmailSender class exists and is configured)
        System.out.println("Report generation complete. Path: " + reportPath);
        //EmailSender.sendEmailWithAttachment(reportPath);
    }
}