package com.amazon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class ReusableMethods extends Driver
{
	static Object[][] locator;
	static Object[][] data;
	static Object[][] matrix;
	static String locatorType;
	static String value;
	static String obj_Name;
	
	//Report data
		static BufferedWriter bw = null;
		static BufferedWriter bw1 = null;
		static String htmlname;
		static String objType;
		static String objName;
		static String TestData;
		static String rootPath;
		static int report;
		static Date cur_dt = null;
		static String filenamer;
		static String TestReport;
		int rowcnt;
		static String exeStatus = "True";
		static int iflag = 0;
		static int j = 1;
		static String fireFoxBrowser;
		static String chromeBrowser;
		static String result;
		static int intRowCount = 0;
		static String dataTablePath;
		static int i;
		static String browserName;

	/*
	 * Name of Method: setValue
	 * Brief Description: Assigns OR data read from excel 
	 * Arguments: row(Row number of a 2D array)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static void setValue(int row)
	{
		obj_Name=(String) locator[row][0];
		locatorType = (String) locator[row][1];
		value=(String) locator[row][2];
	}
	
	/*
	 * Name of Method: getBy
	 * Brief Description: Constructs By value
	 * Arguments: type(type of locator), value(value of locator)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static By getBy(String type, String value)
	{

		switch (type)
		{
		case "id":
			return By.id(value);
		case "xpath":
			return By.xpath(value);
		case "className":
			return By.className(value);
		case "name":
			return By.name(value);
		case "linkText":
			return By.linkText(value);
		case "partialLinkText":
			return By.partialLinkText(value);
		case "cssSelector":
			return By.cssSelector(value);
		case "tagName":
			return By.tagName(value);
		default:
			System.out.println("Unknown type");
			return null;

		}
	}
	
	/*
	 * Name of Method: readLocators
	 * Brief Description: Fetches locators data from object repository excel sheet
	 * Arguments: path(Path of the excel file to be read)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static void readLocators(String path)
	{
		//String path = "C:/Users/Seema/workspace/Selenium/src/testdata/SF_ObjectRepository.xls";
		locator=readExcel(path); 
	}
	
	/*
	 * Name of Method: readTestData
	 * Brief Description: Fetches test data from test data excel sheet
	 * Arguments: path(Path of the excel file to be read)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static  void readTestData(String path) throws InterruptedException
	{
		//String path = "C:/Users/Seema/workspace/Selenium/src/testdata/TestData_SalesForceLoginError.xls";
		data = readExcel(path);
	}
	
	/*
	 * Name of Method: readExcel
	 * Brief Description: Fetches data from excel sheet
	 * Arguments: path(Path of the excel file to be read)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static Object[][] readExcel(String path)
	{
		try
		{
			File file = new File(path);
			FileInputStream xf = new FileInputStream(file);
			HSSFWorkbook xwb = new HSSFWorkbook(xf);
			ArrayList<String> sNames = new ArrayList<String>();

			// retrieve all the sheet in a file
			for (int i=0; i<xwb.getNumberOfSheets(); i++) 
			{
				sNames.add( xwb.getSheetName(i) );
			}

			//Iterate through each sheet and retrieves the data and stores it in an arraylist
			for(String s: sNames)
			{
				HSSFSheet sheet = xwb.getSheet(s);
				int row = sheet.getLastRowNum()+1;
				int col = sheet.getRow(0).getLastCellNum();
				matrix = new Object[row][col];
				for(int i=0;i<row;i++)
				{
					for(int j=0;j<col;j++)
					{
						HSSFCell cell = sheet.getRow(i).getCell(j);
						if(cell!=null)
						{
							String value= cell.getStringCellValue();
							matrix[i][j]=value;

						}
					}
				}
				//print(data,row,col);
			}
			xwb.close();
		}
		catch(Exception e)
		{

		}
		return matrix;
	}

	/*
	 * Name of Method: writeResult
	 * Brief Description: Writs data into excel and chanes the color of cell based on result
	 * Arguments: path(Excel file path),sheetName(SheetName in that excel file),row(Row number),col(Column number)
	 * Created By: Seema
	 * Creation Date: 31 Aug 2017
	 * Last Modified Date: 31 Aug 2017
	 */
	public static void writeResult(String path, String sheetName, String result,String file_name, int row, int col)
	{
		try
		{
			System.out.println(result);
			File file = new File(path);
			FileInputStream fileIStream = new FileInputStream(file);
			HSSFWorkbook workbook = new HSSFWorkbook(fileIStream);
			HSSFSheet sheet = workbook.getSheet(sheetName);

			try
			{
				HSSFCell cell = null;


				//write result data
				HSSFRow r = sheet.getRow(row);
				cell = r.getCell(col);
				if (cell == null)
				{
					cell = r.createCell(col);
				}
				cell.setCellValue(result);
				System.out.println("Write confirmed: " + cell.getStringCellValue());

				//Cell background
				HSSFCellStyle mystyle = workbook.createCellStyle();
				mystyle = workbook.createCellStyle();


				if(result.equalsIgnoreCase("Pass"))
				{
					mystyle.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
			        mystyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
					
				}

				if(result.equalsIgnoreCase("Fail"))
				{
					mystyle.setFillForegroundColor(IndexedColors.RED.getIndex());
			        mystyle.setFillPattern(FillPatternType.SOLID_FOREGROUND); 
					
				}

				HSSFFont hlfont= workbook.createFont();
				hlfont.setUnderline(HSSFFont.U_SINGLE);
				hlfont.setColor(IndexedColors.BLUE.getIndex());
				mystyle.setFont(hlfont);
				cell.setCellStyle(mystyle);
					
				//adding hyper link address
				HSSFCreationHelper helper = workbook.getCreationHelper();
				HSSFHyperlink link = helper.createHyperlink(HyperlinkType.URL);
				link.setAddress(file_name);
				cell.setHyperlink(link);
				
			} 
			catch (Exception e)
			{
				System.out.println(e);
			}
			FileOutputStream fileOStream = new FileOutputStream(path);
			workbook.write(fileOStream);
			System.out.println("Written");
			workbook.close();
			fileOStream.close();
			fileIStream.close();
		} 
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	
	/*
	 * Name of Method: getAttributeValue
	 * Brief Description: Fetches value of HTML attribute "value"
	 * Arguments: ele_attribute(WebElement)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static String getAttributeValue(WebElement ele_attribute)
	{
		String actual = ele_attribute.getAttribute("value");
		return actual;
	}
	
	/*
	 * Name of Method: clear
	 * Brief Description: Clears text box
	 * Arguments: textBox(WebElement)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static void clear(WebElement textBox)
	{
		if(textBox.isDisplayed())
		{
			textBox.clear();
		}
	}
	
	/*
	 * Name of Method: verify
	 * Brief Description: Verifies text
	 * Arguments: expected(String),actual(String)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static String verify(String expected, String actual)
	{
		String result =null;
		if(actual.contains(expected))
		{
			result = "Pass";
			return result;
		}
		else
		{
			result = "Fail";
			return result;
		}
	}
	
	/*
	 * Name of Method: verifyDDValues
	 * Brief Description: Verifies DropDown Values
	 * Arguments: expected(Array of String),actual(Array of String)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static String verifyDDValues(String[] expected,String[] actual)
	{
		trimAll(expected);
		Arrays.sort(expected);
		trimAll(actual);
		Arrays.sort(actual);
		System.out.println("Sorted");
		System.out.println(Arrays.toString(actual));
		System.out.println(Arrays.toString(expected));
		if(expected.length==actual.length)
		{
			int i=0;
			result="Pass";
			while(i<actual.length)
			{
				if(!expected[i].contains(actual[i]))
				{
					result="Fail";
					System.out.println("Fail "+expected[i]+"!="+actual[i]);
					return result;
				}
				i++;
			}

		}
		else
		{
			result="Fail";
			System.out.println("Expected and Actual length not same");
			return result;
		}
		return result;
	}

	/**
	 * @param strings
	 */
	private static void trimAll(String[] strings)
	{
		for (int i =0 ; i< strings.length; i++) {
			strings[i] = strings[i].trim();
		}
	}
	
	/*
	 * Name of Method: getTxt
	 * Brief Description: Extracts text from an WebElement
	 * Arguments: textEle(WebElement)
	 * Created By: Seema
	 * Creation Date: 29Aug 2017
	 * Last Modified Date: 29 Aug 2017
	 */
	public static String getTxt(WebElement textEle)
	{
		String text=null;
		text = textEle.getText();
		return text;	
	}
	
	/*
	 * Name of Method: clickElement
	 * Brief Description: Clicks an element
	 * Arguments: obj(Button/Linktext),objName(Name of the object)
	 * Created By: Seema
	 * Creation Date: 23 Aug 2017
	 * Last Modified Date: 23 Aug 2017
	 */
	public static void clickElement(WebElement obj, String objName )
	{
		if(obj.isDisplayed())
		{
			if(obj.isEnabled())
			{
				obj.click();
				System.out.println(objName+" is clicked");
			}
		}
		else
		{
			System.out.println(objName+" is disabled");
		}

	}
	
	/*
	 * Name of Method: clickElement
	 * Brief Description: Clicks an element
	 * Arguments: obj(Button/Linktext),objName(Name of the object)
	 * Created By: Seema
	 * Creation Date: 09 Sep 2017
	 * Last Modified Date: 09 Sep 2017
	 */
	public static void clickElement2(WebElement obj, String objName)
	{
		//String s = "arguments["+ i +"].click();";
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		if(obj.isDisplayed() && obj.isEnabled())
		{
		executor.executeScript("arguments[4].click();", obj);
		}
		else
		{
			System.out.println(objName+" is disabled");
		}
	}
	
	/*
	 * Name of Method: enterText
	 * Brief Description: Enters text in textfield
	 * Arguments: obj(TextBox),textData(Value to be entered),objName(Name of the object)
	 * Created By: Seema
	 * Creation Date: 23 Aug 2017
	 * Last Modified Date: 23 Aug 2017
	 */
	public static void enterText(WebElement obj, String textData, String objName)
	{
		if(obj.isDisplayed())
		{
			obj.sendKeys(textData);
			System.out.println("Pass: "+textData+" is entered in "+objName);
		}
		else
		{
			System.out.println("Fail: "+objName+" is not displayed. Please check your application");
		}

	}
	
	public static String startReport(String scriptName, String ReportsPath,String browserName) throws IOException
	{
		j =0;
		String strResultPath = null;
		String testScriptName =scriptName;

		cur_dt = new Date(); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String strTimeStamp = dateFormat.format(cur_dt);

		if (ReportsPath == "") 
		{ 

			ReportsPath = "C:\\";
		}

		if (ReportsPath.endsWith("\\")) 
		{ 
			ReportsPath = ReportsPath + "\\";
		}

		strResultPath = ReportsPath + "Log" + "/" +testScriptName +"/"; 
		File f = new File(strResultPath);
		f.mkdirs();
		htmlname = strResultPath  + testScriptName + "_" + strTimeStamp 
				+ ".html";

		bw = new BufferedWriter(new FileWriter(htmlname));

		bw.write("<HTML><BODY><TABLE BORDER=0 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TABLE BORDER=0 BGCOLOR=BLACK CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TR><TD BGCOLOR=#66699 WIDTH=27%><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>Browser Name</B></FONT></TD><TD COLSPAN=6 BGCOLOR=#66699><FONT FACE=VERDANA COLOR=WHITE SIZE=2><B>"
				+ browserName + "</B></FONT></TD></TR>");
		bw.write("<HTML><BODY><TABLE BORDER=1 CELLPADDING=3 CELLSPACING=1 WIDTH=100%>");
		bw.write("<TR COLS=7><TD BGCOLOR=#BDBDBD WIDTH=3%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>SL No</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Step Name</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Execution Time</B></FONT></TD> "
				+ "<TD BGCOLOR=#BDBDBD WIDTH=10%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Status</B></FONT></TD>"
				+ "<TD BGCOLOR=#BDBDBD WIDTH=47%><FONT FACE=VERDANA COLOR=BLACK SIZE=2><B>Detail Report</B></FONT></TD></TR>");
		return htmlname;
	}

	/*
	 * Name of the Method: Update_Report
	 * Brief description : Updates HTML report with test results
	 * Arguments: Res_type:holds the response of test script,Action:Action performed,result:contains test results
	 * Created by: Automation team
	 * Creation date : July 17 2017
	 * last modified:  July 17 2017
	 */
	public static void Update_Report(String Res_type,String Action, String result,WebDriver dr) throws IOException {
		Date exec_time = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String str_time = dateFormat.format(exec_time);

		if (Res_type.startsWith("Pass")) {

			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (j++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+Action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ str_time
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ "Passed"
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2 COLOR = GREEN>"
					+ result + "</FONT></TD></TR>");

		} else if (Res_type.startsWith("Fail")) {
			//To generate report in only single file

			String ss1Path= screenshot(dr);
			exeStatus = "Failed";
			report = 1;
			bw.write("<TR COLS=7><TD BGCOLOR=#EEEEEE WIDTH=3%><FONT FACE=VERDANA SIZE=2>"
					+ (j++)
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+Action
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2>"
					+ str_time
					+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=10%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
					+ "<a href= "
					+ ss1Path

					+ "  style=\"color: #FF0000\"> Failed </a>"

						+ "</FONT></TD><TD BGCOLOR=#EEEEEE WIDTH=30%><FONT FACE=VERDANA SIZE=2 COLOR = RED>"
						+ result + "</FONT></TD></TR>");


		} 
	}

	/*
	 * Name of the Method: Update_Report
	 * Brief description : Updates HTML report with test results
	 * Arguments: Res_type:holds the response of test script,Action:Action performed,result:contains test results
	 * Created by: Automation team
	 * Creation date : July 17 2017
	 * last modified:  July 17 2017
	 */
	public static String screenshot(WebDriver dr) throws IOException{

		Date exec_time = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String str_time = dateFormat.format(exec_time);
		//String fileName = "C:/Users/Sreeram/Desktop/WorkDayScreenShots/ss.png";
		String  ss1Path = "C:/Users/Seema/Desktop/Report1/ScreenShots"+ str_time+".png";
		File scrFile = ((TakesScreenshot)dr).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(ss1Path));
		return ss1Path;
	}

}
