package com.lampenwelt.qa.extentreport;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.lampenwelt.qa.base.TestBase;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import static com.lampenwelt.qa.extentreport.ExtentManager.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestNGListener extends TestBase implements ITestListener {

	@SuppressWarnings("unused")
	private static long endTime;

	private static void setStartTime(long startTime) {
	}

	private static void setEndTime(long endTime) {
		TestNGListener.endTime = endTime;
	}

	@Override
	public synchronized void onStart(ITestContext context) {
	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		setStartTime(context.getStartDate().getTime());
		setEndTime(context.getEndDate().getTime());
	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		System.out.println("--------- Executing :- " + getSimpleMethodName(result) + " ---------");
		ExtentTestManager.createTest(result.getName(), result.getMethod().getDescription());
		ExtentTestManager.setCategoryName(getSimpleClassName(result));
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {
		ExtentTestManager.getTest().assignCategory(getSimpleClassName(result));
		addExtentLabelToTest(result);
		ExtentTestManager.endTest();
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {
		ExtentTestManager.getTest().assignCategory(getSimpleClassName(result));
		ExtentTestManager.getTest().log(Status.FAIL, result.getName() + " Test is failed" + result.getThrowable());
		try {
			ExtentTestManager.getTest().fail("<br><font color= red>" + "Screenshot of Web" + "</font></b>",
					MediaEntityBuilder.createScreenCaptureFromBase64String(takeScreenshot(getSimpleMethodName(result)))
							.build());
		} catch (IOException e) {
			e.printStackTrace();
		}
		addExtentLabelToTest(result);
		ExtentTestManager.endTest();
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {
		ExtentTestManager.getTest().log(Status.SKIP, result.getName() + " Test is Skipped" + result.getThrowable());
	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	private synchronized String getSimpleClassName(ITestResult result) {
		return result.getMethod().getRealClass().getSimpleName();
	}

	private synchronized String getSimpleMethodName(ITestResult result) {
		return result.getName();
	}

	private synchronized void addExtentLabelToTest(ITestResult result) {
		if (result.getStatus() == ITestResult.SUCCESS)
			ExtentTestManager.getTest().pass(MarkupHelper.createLabel("Test Passed", ExtentColor.GREEN));
		else if (result.getStatus() == ITestResult.FAILURE) {
			ExtentTestManager.getTest().fail(MarkupHelper.createLabel("Test Failed", ExtentColor.RED));
		} else
			ExtentTestManager.getTest().skip(MarkupHelper.createLabel("Test Skipped", ExtentColor.ORANGE));
	}

	@SuppressWarnings("resource")
	private static synchronized String takeScreenshot(String methodName) throws IOException {
		DateFormat dateFormat = new SimpleDateFormat("MMM_dd_yyyy_HH_mm_ss_SSS");
		Date date = new Date();
		String dateName = dateFormat.format(date);
		String filePathExtent = OUTPUT_FOLDER_SCREENSHOTS + "Web" + methodName + "_" + dateName + ".png";
		String filePath = ExtentManager.getReportBaseDirectory()+ filePathExtent;

		String encodedBase64 = null;
		try {
			File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			FileInputStream fileInputStreamReader;
			fileInputStreamReader = new FileInputStream(screenshotFile);
			byte[] bytes = new byte[(int) screenshotFile.length()];
			fileInputStreamReader.read(bytes);
			encodedBase64 = Base64.encodeBase64String(bytes);
			FileUtils.copyFile(screenshotFile, new File(filePath));
		} catch (IOException e) {
			e.getStackTrace();
			Reporter.log("Failed To Take screenshot " + e, true);
		}
		return encodedBase64;
	}

}