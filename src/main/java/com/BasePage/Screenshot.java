package com.BasePage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot extends BaseClass {

    // Corrected method name and improved screenshot saving logic
    public static void getScreenshot(WebDriver driver) throws IOException {

        // Take a screenshot and store it as a File object
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Get current date and time to create a unique filename
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String formattedDate = dateFormat.format(date);

        // Define the destination file path with the formatted date in the filename
        File destinationFile = new File(System.getProperty("user.dir") + "\\TestData\\screenshot_" + formattedDate + ".png");

        // Copy the screenshot file to the destination path
        File screenshotDestination = new File(destinationFile.getAbsolutePath());
        screenshotFile.renameTo(screenshotDestination);

        // Optionally, print confirmation or log the screenshot path
        System.out.println("Screenshot saved to: " + screenshotDestination.getAbsolutePath());
    }
}
