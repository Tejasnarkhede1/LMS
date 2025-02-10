package com.BasePage;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentListenerClass implements ITestListener {

    ExtentSparkReporter esr;
    ExtentReports er;
    ExtentTest test;

    // This method will be invoked before any test starts
    public void onStart(ITestContext context) {
        // Setup the Extent report
        esr = new ExtentSparkReporter("ExtentReportamazon.html");
        er = new ExtentReports();
        er.attachReporter(esr);
        
        // Add system info
        er.setSystemInfo("Machine", "TPC1");
        er.setSystemInfo("OS", "Windows 11");
        er.setSystemInfo("Host", "monika");
        er.setSystemInfo("Browser", "chrome");

        // Configure report appearance
        esr.config().setDocumentTitle("Extent Report File");
        esr.config().setReportName("Test Report");

        // Dynamically set the theme based on a condition
        String themePreference = "DARK";  // Example of dynamically setting the theme
        if ("DARK".equalsIgnoreCase(themePreference)) {
            esr.config().setTheme(com.aventstack.extentreports.ExtentSparkReporter.Theme.DARK);  // Dark theme
        } else {
            esr.config().setTheme(com.aventstack.extentreports.ExtentSparkReporter.Theme.LIGHT);  // Light theme
        }
        
        // Configure timestamp format
        esr.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
    }

    // This method is invoked after the test finishes
    public void onFinish(ITestContext context) {
        er.flush();  // Flush the report to the output file
    }

    // This method will be invoked whenever a test passes
    public void onTestSuccess(ITestResult result) {
        test = er.createTest(result.getMethod().getMethodName());
        test.log(Status.PASS, "Test Passed");
    }

    // This method will be invoked whenever a test fails
    public void onTestFailure(ITestResult result) {
        test = er.createTest(result.getMethod().getMethodName());
        test.log(Status.FAIL, "Test Failed");
        test.log(Status.FAIL, result.getThrowable());
    }

    // This method will be invoked whenever a test is skipped
    public void onTestSkipped(ITestResult result) {
        test = er.createTest(result.getMethod().getMethodName());
        test.log(Status.SKIP, "Test Skipped");
    }

    // This method will be invoked before a test starts
    public void onTestStart(ITestResult result) {
        test = er.createTest(result.getMethod().getMethodName());
    }
}
