package com.lampenwelt.qa.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.lampenwelt.qa.base.TestBase;


public class TestUtil extends TestBase{
	
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;
	public static String TESTDATA_SHEET_PATH = System.getProperty("user.dir")
			+ "/src/main/java/com/lampenwelt/qa/testdata/TestData.xlsx";
	static Workbook book;
	static Sheet sheet;
	
	//LandingPage landingPage = new LandingPage();
	

	public static Object[][] getTestData(String sheetName) {
		FileInputStream file = null;
		try {
			file = new FileInputStream(TESTDATA_SHEET_PATH);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sheet = book.getSheet(sheetName);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				try {
					data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
					System.out.println(data[i][k]);
				} catch (Throwable e) {
					System.out.println("Finding the execption root cause-----"+e.getMessage());
				} finally {
					System.out.println("Finally the code completion for file closing-----");
				}
			}
		}
		return data;
	}

	public static void setData(String sheetName, int rowNumber, String value) throws IOException {
		// Create an object of File class to open xlsx file

		try {
			File file = new File(TESTDATA_SHEET_PATH);

			// Create an object of FileInputStream class to read excel file
			FileInputStream inputStream = new FileInputStream(file);

			// Creating workbook instance that refers to .xls file
			XSSFWorkbook wb = new XSSFWorkbook(inputStream);

			// Creating a Sheet object using the sheet Name
			XSSFSheet sheet = wb.getSheet(sheetName);

			// get all rows in the sheet
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

			// iterate over all the rows in Excel and put data in the form.
			for (int i = 1; i <= rowCount; i++) {
				// create a new cell in the row at index 6
				XSSFCell cell = sheet.getRow(i).createCell(rowNumber);
				cell.setCellValue(value);
			}
			FileOutputStream outputStream = new FileOutputStream(TESTDATA_SHEET_PATH);
			wb.write(outputStream);
			outputStream.close();
			inputStream.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}

	}

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}
	
	// Switch to window based on index
	public static void switchToWindow(int index) {
		String windowId = null;
		Set<String> windowlist = driver.getWindowHandles();
		Iterator<String> it = windowlist.iterator();
		for (int i = 0; i <= index; i++) {
			windowId = it.next();
		}
		driver.switchTo().window(windowId);
	}
	
	
	//Find the number of frame on the website	
	public static int findtheNumberOfFrames() {
		List<WebElement> frames = driver.findElements(By.tagName("iframe"));
		return frames.size();
	}
	
	// Switch to window based on title
	public static void switchToWindowTitle(String title) {
		
		Set<String> windows = driver.getWindowHandles();
		for (String window : windows) {
			if (driver.switchTo().window(window).getTitle().equalsIgnoreCase(title)) {
				System.out.println("Switched to window with title:" + window);
			}
		}
	}
	
	//Switch to frame by id
	public static void switchToFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}
	
	//Switch to frame by name
	public static void switchToFrameByName(String frameName) {
		driver.switchTo().frame(frameName);
	}
	
	//Explicit wait 
	public static void explicitwait(WebElement waitElement) {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOf(waitElement));
	}
	
	//Fluent wait
	public static void fluentWait(WebElement waitElement) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(20))
				.pollingEvery(Duration.ofMillis(500)).ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(waitElement));

	}
	
	
	//Action Class for mouse over
	public static void MouseOverTo(WebElement MenuElement) {
		Actions action = new Actions(driver);
		action.moveToElement(MenuElement).build().perform();	
	}
	
	//Action Class for double click
	public static void doubleClick(WebElement DoubleClickElement) {
		Actions action = new Actions(driver);
		action.contextClick(DoubleClickElement);
	}
	
	
	//Pick the Date from date picker	
//	public static void selectDateFromDatePicker(String strDate) throws Exception {
//		
//		LandingPage.geburstagCalender.click();
//		Thread.sleep(1000);
//		Calendar calendar = Calendar.getInstance();
//		try {
//			SimpleDateFormat targetDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
//			targetDateFormat.setLenient(false);
//			Date formattedTargetDate = targetDateFormat.parse(strDate);
//			calendar.setTime(formattedTargetDate);
//		} catch (Exception e) {
//			throw new Exception("Invalid date is provided, please check the input date!!");
//		}
//		
//		int targetDay=calendar.get(Calendar.DAY_OF_MONTH);
//		int targetMonth=calendar.get(Calendar.MONTH);
//		int targetYear=calendar.get(Calendar.YEAR);
//		
//		String strCurrentMonthYear=LandingPage.monthYear.getText();		
//		calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(strCurrentMonthYear));
//		
//		int currentMonth = calendar.get(Calendar.MONTH);
//		int currentYear = calendar.get(Calendar.YEAR);
//		
//		
//		//Select future date
//		
//		while(currentMonth < targetMonth || currentYear < targetYear) {			
//			LandingPage.calenderNext.click();			
//			String currentDate = LandingPage.monthYear.getText();			
//			calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(currentDate));
//			currentMonth = calendar.get(Calendar.MONTH);
//			currentYear = calendar.get(Calendar.YEAR);
//			break;
//		}
//		
//		//Select past date
//		while(currentMonth < targetMonth || currentYear < targetYear) {			
//			LandingPage.calenderPrevious.click();		
//			String currentDate = LandingPage.monthYear.getText();			
//			calendar.setTime(new SimpleDateFormat("MMM yyyy").parse(currentDate));
//			currentMonth = calendar.get(Calendar.MONTH);
//			currentYear = calendar.get(Calendar.YEAR);
//			break;
//		}
//		
//		if(currentMonth==targetMonth && currentYear==targetYear ) {
//			
//			driver.findElement(By.xpath("//td[@data-day="+targetDay+"]"));
//			
//		}else {
//			throw new Exception("unable to select the date because of current and target dates mismatch");
//			
//		}	
//	}
	
}










