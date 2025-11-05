package Utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;

import org.testng.ITestContext;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class Utility {
	public static void sendEmail(String subject, String htmlBody, String toEmails) {
		try {
			Properties config = Base.config;
			String fromEmail = config.getProperty("email.username");
			String password = config.getProperty("email.password");

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.office365.com");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.ssl.trust", "*");
			props.put("mail.smtp.ssl.checkserveridentity", "false");

	        // creates a new session with an authenticator
	        Authenticator auth = new Authenticator() {
	            public PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromEmail, password);
	            }
	        };
	        Session session = Session.getInstance(properties, auth);
	        
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmails));
			message.setSubject(subject, "UTF-8");
			message.setContent(htmlBody, "text/html; charset=utf-8");

			Transport.send(message);
			System.out.println("Email sent successfully to: " + toEmails);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -------- ExtentReport --------
	public static void initExtentReport(ITestContext context) {
		String suiteType = context.getCurrentXmlTest().getName();
		if (suiteType == null || suiteType.trim().isEmpty()) {
			List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
			suiteType = includedGroups.isEmpty() ? "Unknown" : String.join(", ", includedGroups);
		}
		try {
			String baseDir = System.getProperty("user.dir");
			String reportFolder = baseDir + File.separator + "target" + File.separator + "ExtentReports";
			new File(reportFolder).mkdirs();
			String dateFolder = new SimpleDateFormat("yyyyMMdd").format(new Date());
			String datedReportFolder = reportFolder + File.separator + dateFolder;
			new File(datedReportFolder).mkdirs();
			String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//			String dynamicReportPath = datedReportFolder + File.separator + "AutomationReport_" + timestamp + ".html";
//			String fixedReportPath = reportFolder + File.separator + "AutomationReport.html";
//			ExtentSparkReporter sparkDynamic = new ExtentSparkReporter(dynamicReportPath);
//			ExtentSparkReporter sparkFixed   = new ExtentSparkReporter(fixedReportPath);
			String dynamicReportPath = datedReportFolder + File.separator + "AutomationReport_" + timestamp + ".html";
			String fixedReportPath = reportFolder + File.separator + "AutomationReport.html";

			ExtentSparkReporter sparkDynamic = new ExtentSparkReporter(dynamicReportPath);
			ExtentSparkReporter sparkFixed = new ExtentSparkReporter(fixedReportPath);

			 // ✅ Optional configuration for better look and portability
	        sparkDynamic.config().setReportName("Automation Execution Report");
	        sparkDynamic.config().setDocumentTitle("APIM Test Report");
	        sparkDynamic.config().setEncoding("utf-8");
	        sparkDynamic.config().setTheme(com.aventstack.extentreports.reporter.configuration.Theme.STANDARD);
			
			extent = new ExtentReports();
			extent.attachReporter(sparkDynamic, sparkFixed);
			extent.setSystemInfo("Tester", "Tejas");
			extent.setSystemInfo("Environment", "SIT");
			extent.setSystemInfo("Suite", suiteType);
			System.out.println("Dynamic report created at: " + dynamicReportPath);
			System.out.println("Fixed report created at: " + fixedReportPath);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ExtentReports getExtent() {
		return extent;
	}

	public static void flushExtentReport() {
		if (extent != null) {
			extent.flush();
		}
	}

	public static ExtentTest startTest(String testName, String description) {
		ExtentTest extentTest = extent.createTest(testName, description);
		test.set(extentTest);
		return extentTest;
	}

	public static ExtentTest getTest() {
		return test.get();
	}

	public static void setTest(ExtentTest extentTest) {
		test.set(extentTest);
	}
	private static Map<String, ExtentTest> mainTestMap = new HashMap<>();
	public static ExtentTest getMainTest(ITestResult result) {
		String key = result.getTestClass().getName() + "." + result.getMethod().getMethodName();
		ExtentTest test = mainTestMap.get(key);
		if (test == null) {
			// Create main test node
			test = extent.createTest(result.getMethod().getMethodName());
			mainTestMap.put(key, test);
		}
		return test;
	}

	public static void sendExecutionSummary(ITestContext context) {
	    try {
	        int passed = 0, failed = 0, skipped = 0, totalRetries = 0;
	        int totalAttempts = 0;
	        Map<String, ITestResult> finalResults = new HashMap<>();
	        List<ITestResult> failedTests = new ArrayList<>();
	        List<ITestResult> allResults = new ArrayList<>();
	        allResults.addAll(context.getPassedTests().getAllResults());
	        allResults.addAll(context.getFailedTests().getAllResults());
	        allResults.addAll(context.getSkippedTests().getAllResults());

	        for (ITestResult r : allResults) {
	            totalAttempts++;
	            String key = r.getMethod().getQualifiedName();
	            ITestResult existing = finalResults.get(key);
	            if (existing == null || r.getEndMillis() > existing.getEndMillis()) {
	                if (existing != null) totalRetries++;
	                finalResults.put(key, r);
	            } else {
	                totalRetries++;
	            }
	        }

	        for (ITestResult r : finalResults.values()) {
	            switch (r.getStatus()) {
	                case ITestResult.SUCCESS: passed++; break;
	                case ITestResult.FAILURE: 
	                    failed++; 
	                    failedTests.add(r);
	                    break;
	                case ITestResult.SKIP: skipped++; break;
	            }
	        }

	        System.out.println("===============================================");
	        System.out.println("Suite Summary");
	        System.out.println("Total attempts: " + totalAttempts +
	                ", Total unique tests: " + finalResults.size() +
	                ", Passes: " + passed +
	                ", Failures: " + failed +
	                ", Skips: " + skipped +
	                ", Retries: " + totalRetries);
	        System.out.println("===============================================");

	        String env = Base.config.getProperty("env", "UNKNOWN").toUpperCase();

	        StringBuilder emailBody = new StringBuilder();
	        emailBody.append("<h2>")
	                 .append(env)
	                 .append(" APIM Test Execution Summary</h2>");
	        emailBody.append("<p><b>Run Date & Time:</b> ").append(new Date()).append("</p>");
	        emailBody.append("<table border='1' cellpadding='6' cellspacing='0' style='border-collapse:collapse; text-align:center;'>");
	        emailBody.append("<tr style='background-color:#f2f2f2;'>")
	                .append("<th>Total Attempts</th><th>Total Methods</th><th>Retries</th>")
	                .append("<th>Passed</th><th>Failed</th><th>Skipped</th>")
	                .append("</tr>");
	        emailBody.append("<tr>")
	                .append("<td>").append(totalAttempts).append("</td>")
	                .append("<td>").append(finalResults.size()).append("</td>")
	                .append("<td>").append(totalRetries).append("</td>")
	                .append("<td style='color:green; font-weight:bold;'>").append(passed).append("</td>")
	                .append("<td style='color:red; font-weight:bold;'>").append(failed).append("</td>")
	                .append("<td style='color:orange; font-weight:bold;'>").append(skipped).append("</td>")
	                .append("</tr>");
	        emailBody.append("</table><br>");

	        String reportUrl = "https://infrasofttech-my.sharepoint.com/:u:/g/personal/ashwinkumar_chorage_kiya_ai/EWU3VBJLnpxComEipvLd_tUBkoZXCGA9aWCSxsSXFtv1-A?e=ckLb9J";
//	        String reportUrl = "https://infrasofttech-my.sharepoint.com/:f:/g/personal/ashwinkumar_chorage_kiya_ai/Ej_5kNTiYTtEm1rczajLAFQB-S_BeOHgz_JE1k2a3BH82Q?e=fatPG1";
	        String linkText = "📄 Open " + env + " APIM Extent Report";
	        emailBody.append("<a href='").append(reportUrl).append("' ")
	                 .append("style='font-size:14px; font-weight:bold; text-decoration:none; color:#1a0dab;'>")
	                 .append(linkText).append("</a>");
	        
	        // Add failed tests details if any
	        if (!failedTests.isEmpty()) {
	            emailBody.append("<h3 style='color:red;'>Failed Tests Details:</h3>");
	            emailBody.append("<table border='1' cellpadding='8' cellspacing='0' style='border-collapse:collapse; width:100%;'>");
	            emailBody.append("<tr style='background-color:#ffebee;'>")
	                    .append("<th style='text-align:center; width:5%;'>S.No</th>")
	                    .append("<th style='text-align:left; width:30%;'>Test Name</th>")
	                    .append("<th style='text-align:left; width:65%;'>Failure Reason</th>")
	                    .append("</tr>");

	            int serialNo = 1;
	            for (ITestResult failedTest : failedTests) {
	                String testName = failedTest.getMethod().getMethodName();
	                String failureReason = getFailureReason(failedTest);
	                
	                emailBody.append("<tr>")
	                        .append("<td style='text-align:center; vertical-align:top; font-weight:bold;'>").append(serialNo).append("</td>")
	                        .append("<td style='vertical-align:top; font-weight:bold;'>").append(testName).append("</td>")
	                        .append("<td style='vertical-align:top;'>").append(failureReason).append("</td>")
	                        .append("</tr>");
	                serialNo++;
	            }
	            emailBody.append("</table><br>");
	        }

	        String recipientsProp = Base.config.getProperty("mail.recipients");
	        if (recipientsProp != null && !recipientsProp.isEmpty()) {
	            sendEmail(env + " APIM Automation Test Execution Report", emailBody.toString(), recipientsProp);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private static String getFailureReason(ITestResult result) {
	    try {
	        Throwable throwable = result.getThrowable();
	        if (throwable == null) {
	            return "Test failed without specific error details";
	        }

	        String originalMessage = throwable.getMessage();
	        if (originalMessage == null) {
	            originalMessage = throwable.getClass().getSimpleName();
	        }

	        // Extract element information from the message
	        String elementInfo = extractElementInfo(originalMessage);
	        String lowerMessage = originalMessage.toLowerCase();
	        String friendlyReason = "";

	        if (lowerMessage.contains("element not visible") || lowerMessage.contains("not found")) {
	            friendlyReason = "Required element" + elementInfo + " was not found on the page - possible UI change or loading issue";
	        } else if (lowerMessage.contains("timeout") || lowerMessage.contains("wait")) {
	            friendlyReason = "Page took too long to load" + elementInfo + " - possible performance issue";
	        } else if (lowerMessage.contains("click") && lowerMessage.contains("intercept")) {
	            friendlyReason = "Unable to click on element" + elementInfo + " - element may be blocked by another element";
	        } else if (lowerMessage.contains("stale element")) {
	            friendlyReason = "Page element" + elementInfo + " became outdated during test execution - page refresh occurred";
	        } else if (lowerMessage.contains("no such element")) {
	            friendlyReason = "Expected element" + elementInfo + " is missing from the page - possible UI change";
	        } else if (lowerMessage.contains("assertion") || lowerMessage.contains("expected")) {
	            friendlyReason = "Test validation failed" + elementInfo + " - actual result did not match expected result";
	        } else if (lowerMessage.contains("dropdown") || lowerMessage.contains("select")) {
	            friendlyReason = "Unable to select option from dropdown" + elementInfo + " - option may not be available";
	        } else if (lowerMessage.contains("response") && lowerMessage.contains("status")) {
	            friendlyReason = "API response returned error status - service may be unavailable";
	        } else if (lowerMessage.contains("connection") || lowerMessage.contains("network")) {
	            friendlyReason = "Network connectivity issue - unable to reach the application";
	        } else if (lowerMessage.contains("popup") || lowerMessage.contains("alert")) {
	            friendlyReason = "Unexpected popup or alert appeared during test execution";
	        } else if (lowerMessage.contains("login") || lowerMessage.contains("authentication")) {
	            friendlyReason = "Login or authentication failed - credentials may be invalid";
	        } else if (lowerMessage.contains("file") && (lowerMessage.contains("download") || lowerMessage.contains("upload"))) {
	            friendlyReason = "File operation failed - check file permissions and network connectivity";
	        } else if (lowerMessage.contains("date") || lowerMessage.contains("time")) {
	            friendlyReason = "Date/time validation failed - incorrect date format or value";
	        } else if (lowerMessage.contains("table") || lowerMessage.contains("record")) {
	            friendlyReason = "Expected data not found in table - data may not be loaded properly";
	        } else if (lowerMessage.contains("otp")) {
	            friendlyReason = "OTP operation failed - unable to fetch or enter OTP";
	        } else if (lowerMessage.contains("iframe") || lowerMessage.contains("frame")) {
	            friendlyReason = "Unable to switch to iframe - frame may not be available";
	        } else if (lowerMessage.contains("scroll")) {
	            friendlyReason = "Unable to scroll to element" + elementInfo + " - element may be outside viewport";
	        } else if (lowerMessage.contains("checkbox") || lowerMessage.contains("radio")) {
	            friendlyReason = "Unable to select checkbox/radio button" + elementInfo + " - element may be disabled";
	        } else if (lowerMessage.contains("sendkeys") || lowerMessage.contains("input")) {
	            friendlyReason = "Unable to enter text in field" + elementInfo + " - field may be readonly or disabled";
	        } else if (lowerMessage.contains("refresh") || lowerMessage.contains("reload")) {
	            friendlyReason = "Page refresh failed - browser may be unresponsive";
	        } else if (lowerMessage.contains("crash") || lowerMessage.contains("memory")) {
	            friendlyReason = "Browser crashed or ran out of memory - system resource issue";
	        } else {
	            friendlyReason = originalMessage.length() > 200 ? originalMessage.substring(0, 200) + "..." : originalMessage;
	        }

	        // Log to Extent Report
	        ExtentTest test = getTest();
	        if (test != null) {
	            test.fail("Test Failure Details: " + friendlyReason);
	        }

	        return friendlyReason;

	    } catch (Exception e) {
	        return "Unable to determine failure reason";
	    }
	}

	private static String extractElementInfo(String message) {
	    try {
	        // Extract By.xpath, By.id, By.css information
	        if (message.contains("By.xpath:")) {
	            String xpath = message.substring(message.indexOf("By.xpath:") + 9);
	            xpath = xpath.substring(0, xpath.indexOf("]") > 0 ? xpath.indexOf("]") : Math.min(xpath.length(), 50));
	            return " (xpath: " + xpath + ")";
	        } else if (message.contains("By.id:")) {
	            String id = message.substring(message.indexOf("By.id:") + 6);
	            id = id.substring(0, id.indexOf("]") > 0 ? id.indexOf("]") : Math.min(id.length(), 30));
	            return " (id: " + id + ")";
	        } else if (message.contains("By.css:")) {
	            String css = message.substring(message.indexOf("By.css:") + 7);
	            css = css.substring(0, css.indexOf("]") > 0 ? css.indexOf("]") : Math.min(css.length(), 40));
	            return " (css: " + css + ")";
	        }
	        return "";
	    } catch (Exception e) {
	        return "";
	    }
	}

}
