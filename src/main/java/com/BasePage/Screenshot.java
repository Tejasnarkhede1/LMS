package com.BasePage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils; // It's common to use Apache Commons IO for this
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshot {
	

    public static String getScreenshot(WebDriver driver, String screenshotName) throws IOException {
        // Create a timestamp to make the filename unique
        String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        // Take the screenshot
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        // Define the destination path and filename
        String destinationPath = System.getProperty("user.dir") + "/screenshots/" + screenshotName + "_" + timestamp + ".png";
        File destinationFile = new File(destinationPath);

        // Ensure the directory exists
        destinationFile.getParentFile().mkdirs();

        // Copy the screenshot file to the destination
        FileUtils.copyFile(screenshotFile, destinationFile);
        
        // Return the absolute path of the new screenshot file
        return destinationFile.getAbsolutePath();
    }
}