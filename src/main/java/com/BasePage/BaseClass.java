package com.BasePage;

import java.io.IOException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    public static WebDriver driver;

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
//        driver.get("https://www.facebook.com/");
        driver.get("http://172.21.0.39:7999//Kiya.aiCBS-10.2.0//LoginPage?tid=139&lang=en");

        driver.manage().window().maximize();
    }

    @AfterMethod
    public void failedScreenshot(ITestResult result) throws IOException {
        // Check if the test failed or passed, and take a screenshot accordingly
        if (result.getStatus() == ITestResult.FAILURE) {
            Screenshot.getScreenshot(driver);  // Assuming Screenshot is a custom class for capturing screenshots
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            Screenshot.getScreenshot(driver);  // Taking a screenshot for successful tests (optional)
        }
    }
}
