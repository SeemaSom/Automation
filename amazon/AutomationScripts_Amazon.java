package com.amazon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.remote.RemoteWebDriver;

public class AutomationScripts_Amazon extends ReusableMethods
{

	public static String TestCase_1() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);

		startReport("Amazon_TestCase_1","C:/Users/Seema/Desktop/AmazonReport/",browserName);
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase1.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String searchItem = (String) data[i][1];
				WebDriverWait wait = new WebDriverWait(driver, 10);

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate search text box and enter text
				setValue(1);
				By by_SearchBox = getBy(locatorType,value);
				WebElement ele_SearchBox = driver.findElement(by_SearchBox);
				clear(ele_SearchBox);
				enterText(ele_SearchBox,searchItem,obj_Name);

				//Locate search button and click
				setValue(2);
				By by_SearchButton = getBy(locatorType,value);
				WebElement click_SearchButton = driver.findElement(by_SearchButton);
				clickElement(click_SearchButton,objName);

				Thread.sleep(5000);

				//Validate show result
				setValue(3);
				By by_ResultList = getBy(locatorType,value);
				WebElement ele_ResultList = wait.until(ExpectedConditions.visibilityOfElementLocated(by_ResultList));
				String val1 = ele_ResultList.getText();
				Assert.assertTrue(val1.contains("results for"));

				//Locate product and click
				setValue(4);
				By by_Product = getBy(locatorType,value);
				WebElement click_Product = driver.findElement(by_Product);
				clickElement(click_Product,obj_Name);

				Thread.sleep(3000);

				//Validate navigation to product page
				String val2 = driver.getTitle();
				Assert.assertTrue(val2.contains("Amazon.com: Apple iPhone 6"));

				//Locate and click Add to cart button
				setValue(5);
				By by_AddtoCart = getBy(locatorType,value);
				WebElement click_AddtoCart = driver.findElement(by_AddtoCart);
				clickElement(click_AddtoCart,obj_Name);

				Thread.sleep(3000);

				//Click on No Thanks button for addtional offer
				try
				{
					setValue(7);
					By by_NoThanks = getBy(locatorType,value);
					WebElement click_NoThanks = driver.findElement(by_NoThanks);
					if(click_NoThanks.isDisplayed() && click_NoThanks.isEnabled())
					{
						clickElement(click_NoThanks,obj_Name);
					}
				}
				catch(Exception e)
				{
					System.out.println(e);
				}
				Thread.sleep(3000);

				//Validate no. of item in cart
				setValue(8);
				By by_CartItems = getBy(locatorType,value);
				String val3 = driver.findElement(by_CartItems).getText();
				result = verify("1",val3);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_1",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_1",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_2() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);

		startReport("Amazon_TestCase_2","C:/Users/Seema/Desktop/AmazonReport/",browserName);

		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase2.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String text =  (String) data[i][1];
				String[] data = text.split("5");
				SortedSet< String > expectedSet = new TreeSet< String >();

				for(String s : data) {
					expectedSet.add(s.trim());
				}


				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate and verify if department is available
				setValue(9);
				By by_Department = getBy(locatorType,value);
				WebElement ele_Department = driver.findElement(by_Department);
				Actions action = new Actions(driver);
				action.moveToElement(ele_Department).build().perform();
				String val1 = ele_Department.getText();
				Assert.assertEquals(val1, "Departments");

				Thread.sleep(5000);

				//Locate and verify department DD Values
				setValue(10);
				By by_DeptDDValues = getBy(locatorType,value);
				List<WebElement> list = driver.findElements(by_DeptDDValues);
				System.out.println("list size: "+list.size());
				SortedSet<String> actualValues = new TreeSet<>();

				for(WebElement w: list)
				{
					String s = w.getText();
					actualValues.add(s);
				}

				//Locate new value in departments
				setValue(36);
				By by_DeptNew = getBy(locatorType,value);
				actualValues.add(driver.findElement(by_DeptNew).getText());

				for (String s: expectedSet) {
					System.out.print(s + ",");

				}
				System.out.println("");

				for (String s: actualValues) {
					System.out.print(s + ",");
				}

				System.out.println("");


				if (expectedSet.containsAll(actualValues))
				{
					System.out.print("expectedSet.containsAll(actualValues)");
					result="Pass";
				} 
				else
				{
					System.out.print("Expected not equals actual");
					result="Fail";
				}

				//Locate and verify Your Amazon.com
				setValue(11);
				By by_Link1 = getBy(locatorType,value);
				WebElement ele_Link1 = driver.findElement(by_Link1);
				if(ele_Link1.isDisplayed() && ele_Link1.isEnabled())
				{
					String val2 = ele_Link1.getText();
					Assert.assertEquals(val2, "Your Amazon.com");
				}

				//Locate and verify Today's Deals
				setValue(12);
				By by_Link2 = getBy(locatorType,value);
				WebElement ele_Link2 = driver.findElement(by_Link2);
				if(ele_Link2.isDisplayed() && ele_Link2.isEnabled())
				{
					String val3 = ele_Link2.getText();
					Assert.assertEquals(val3, "Today's Deals");
					//result=verify("Today's Deals",val3);
				}

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_2",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_2",  "Verification Failed",driver);
				}
				driver.close();	
			}
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_3() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);

		startReport("Amazon_TestCase_3","C:/Users/Seema/Desktop/AmazonReport/",browserName);

		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase3.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String text =  (String) data[i][1];
				String[] data = text.split("5");
				SortedSet< String > expectedSet = new TreeSet< String >();

				for(String s : data) {
					expectedSet.add(s.trim());
				}

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate and verify if department is available
				setValue(9);
				By by_Department = getBy(locatorType,value);
				WebElement ele_Department = driver.findElement(by_Department);
				Actions action = new Actions(driver);
				action.moveToElement(ele_Department).build().perform();
				String val1 = ele_Department.getText();
				Assert.assertEquals(val1, "Departments");

				Thread.sleep(5000);

				//Locate and verify department DD Values
				setValue(10);
				By by_DeptDDValues = getBy(locatorType,value);
				List<WebElement> list = driver.findElements(by_DeptDDValues);
				System.out.println("list size: "+list.size());
				SortedSet<String> actualValues = new TreeSet<>();

				for(WebElement w: list)
				{
					String s = w.getText();
					actualValues.add(s);
				}

				//Locate new value in departments
				setValue(36);
				By by_DeptNew = getBy(locatorType,value);
				actualValues.add(driver.findElement(by_DeptNew).getText());

				for (String s: expectedSet) {
					System.out.print(s + ",");

				}
				System.out.println("");

				for (String s: actualValues) {
					System.out.print(s + ",");
				}

				System.out.println("");


				if (expectedSet.containsAll(actualValues))
				{
					System.out.print("expectedSet.containsAll(actualValues)");
					result="Pass";
				} 
				else
				{
					System.out.print("Expected not equals actual");
					result="Fail";
				}

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_3",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_3",  "Verification Failed",driver);
				}
				driver.close();	
			}
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_4() throws IOException
	{
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);
		startReport("Amazon_TestCase_4","C:/Users/Seema/Desktop/AmazonReport/",browserName);

		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase4.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String text =  (String) data[i][1];
				String[] expected = text.split("5");

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}
				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate and mouse Over on Accounts and List
				setValue(13);
				By by_ACCList = getBy(locatorType,value);
				WebElement ele_ACCList = driver.findElement(by_ACCList);
				Actions action = new Actions(driver);
				action.moveToElement(ele_ACCList).build().perform();

				Thread.sleep(5000);

				//Locate and verify Account and List DD Values
				setValue(14);
				By by_ACCListDDValues = getBy(locatorType,value);
				List<WebElement> list = driver.findElements(by_ACCListDDValues);
				ArrayList<String> ddValues = new ArrayList<String>();
				for(WebElement w : list)
				{
					String s = w.getText();
					for(String str:s.split("\n"))
					{
						ddValues.add(str);
					}
				}
				String[] values = new String[ddValues.size()];
				values = ddValues.toArray(values);
				System.out.println(Arrays.toString(values));
				System.out.println(Arrays.toString(expected));
				result = verifyDDValues(expected,values);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_4",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_4",  "Verification Failed",driver);
				}
				driver.close();	
			}
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_5() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);

		startReport("Amazon_TestCase_5","C:/Users/Seema/Desktop/AmazonReport/",browserName);

		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase5.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String text =  (String) data[i][1];
				String selectValue = (String) data[i][2];
				String[] data = text.split("5");
				SortedSet< String > expectedSet = new TreeSet< String >();

				for(String s : data) {
					expectedSet.add(s.trim());
				}


				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate and Click All in main page
				setValue(15);
				By by_All = getBy(locatorType,value);
				WebElement ele_All = driver.findElement(by_All);
				Select select = new Select(ele_All);
				List<WebElement> list = select.getOptions();
				System.out.println("list size: "+list.size());
				SortedSet<String> actualValues = new TreeSet<>();

				for(WebElement w: list)
				{
					String s = w.getText();
					actualValues.add(s);
				}

				Thread.sleep(5000);

				for (String s: expectedSet) {
					System.out.print(s + ",");

				}
				System.out.println("");

				for (String s: actualValues) {
					System.out.print(s + ",");
				}

				System.out.println("");

				//verify All DD Values
				if (expectedSet.containsAll(actualValues))
				{
					System.out.print("expectedSet.containsAll(actualValues)");
					result="Pass";
				} 
				else
				{
					System.out.print("Expected not equals actual");
					result="Fail";
				}

				//Select Clothing, Shoes & Jewelry from drop down
				setValue(15);
				By by_All1 = getBy(locatorType,value);
				WebElement ele_All1 = driver.findElement(by_All1);
				clickElement(ele_All1,obj_Name);
				Thread.sleep(3000);
				select.selectByValue(selectValue);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_5",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_5",  "Verification Failed",driver);
				}
				driver.close();	
			}
			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}


	public static String TestCase_6() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);
		startReport("Amazon_TestCase_6","C:/Users/Seema/Desktop/AmazonReport/",browserName);
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase6.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String searchItem = (String) data[i][1];


				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate search text box and enter text
				setValue(1);
				By by_SearchBox = getBy(locatorType,value);
				WebElement ele_SearchBox = driver.findElement(by_SearchBox);
				clear(ele_SearchBox);
				enterText(ele_SearchBox,searchItem,obj_Name);

				//Locate search button and click
				setValue(2);
				By by_SearchButton = getBy(locatorType,value);
				WebElement click_SearchButton = driver.findElement(by_SearchButton);
				clickElement(click_SearchButton,obj_Name);

				Thread.sleep(3000);

				//Validate show result
				setValue(3);
				By by_ResultList = getBy(locatorType,value);
				WebElement ele_ResultList = driver.findElement(by_ResultList);
				String val1 = ele_ResultList.getText();
				Assert.assertTrue(val1.contains("results for"));

				//Locate product and click
				setValue(16);
				By by_Product = getBy(locatorType,value);
				WebElement click_Product = driver.findElement(by_Product);
				clickElement(click_Product,obj_Name);

				Thread.sleep(3000);

				//Validate navigation to product page
				String val2 = driver.getTitle();
				Assert.assertTrue(val2.contains("Amazon.com: Apple iPhone 6S"));

				//Locate and click Add to cart button
				setValue(17);
				By by_AddtoCart = getBy(locatorType,value);
				WebElement click_AddtoCart = driver.findElement(by_AddtoCart);
				clickElement(click_AddtoCart,obj_Name);

				Thread.sleep(3000);

				//Locate and click no thanks


				//Locate and click on cart button
				setValue(18);
				By by_CartButton = getBy(locatorType,value);
				WebElement click_CartButton = driver.findElement(by_CartButton);
				clickElement(click_CartButton,obj_Name);

				Thread.sleep(3000);

				//Locate and click on delete
				setValue(19);
				By by_Delete = getBy(locatorType,value);
				WebElement click_Delete = driver.findElement(by_Delete);
				clickElement(click_Delete,obj_Name);

				Thread.sleep(3000);

				//Locate and click on cart button
				setValue(18);
				By by_CartButton2 = getBy(locatorType,value);
				WebElement click_CartButton2 = driver.findElement(by_CartButton2);
				clickElement(click_CartButton2,obj_Name);

				Thread.sleep(3000);

				//get actual result
				setValue(20);
				By by_Msg = getBy(locatorType,value);
				WebElement ele_Msg = driver.findElement(by_Msg);
				String actual = ele_Msg.getText();

				//Verify actual result
				result=verify("Your Shopping Cart is empty.", actual);


				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_6",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_6",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_7() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);
		startReport("Amazon_TestCase_7","C:/Users/Seema/Desktop/AmazonReport/",browserName);
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase7.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String text = (String) data[i][1];
				String[] expected = text.split("5");


				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate help and click
				setValue(21);
				By by_Help = getBy(locatorType,value);
				WebElement click_Help = driver.findElement(by_Help);
				clickElement(click_Help,obj_Name);

				Thread.sleep(3000);

				//Locate help header Message
				setValue(22);
				By by_Header = getBy(locatorType,value);
				WebElement ele_Header = driver.findElement(by_Header);
				String val1 = ele_Header.getText();
				Assert.assertTrue(val1.contains("Hello. What can we help you with?"));

				//Locate and Verify link text
				setValue(23);
				By by_HelpLinks = getBy(locatorType,value);
				List<WebElement> list = driver.findElements(by_HelpLinks);
				String[] actual = new String[list.size()];
				int index=0;
				for(WebElement w: list)
				{
					String s = w.getText();
					actual[index++]=s;
				}

				System.out.println(list.size());
				System.out.println(Arrays.toString(actual));
				System.out.println(Arrays.toString(expected));

				result = verifyDDValues(expected,actual);

				//Locate and validate text box header
				setValue(24);
				By by_BoxHeader = getBy(locatorType,value);
				WebElement ele_BoxHeader = driver.findElement(by_BoxHeader);
				String headerText = ele_BoxHeader.getText();

				Assert.assertTrue(headerText.contains("Find more solutions"));

				//Locate and verify if textBox is displayed
				setValue(25);
				By by_TextBox = getBy(locatorType,value);
				WebElement ele_TextBox = driver.findElement(by_TextBox);
				if(ele_TextBox.isDisplayed() && ele_TextBox.isEnabled())
				{
					System.out.println("Text Box is displayed");
				}

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_7",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_7",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}


	public static String TestCase_8() throws IOException
	{
		//Get browser Name
		Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
		String browserName = cap.getBrowserName().toLowerCase();
		System.out.println(browserName);
		startReport("Amazon_TestCase_8","C:/Users/Seema/Desktop/AmazonReport/",browserName);
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase8.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String searchData = (String) data[i][1];
				String quantity = (String) data[i][2];

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				if(browserName.equals("chrome"))
				{
					driver.manage().window().maximize();
				}

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate search text box and enter text
				setValue(1);
				By by_SearchBox = getBy(locatorType,value);
				WebElement ele_SearchBox = driver.findElement(by_SearchBox);
				clear(ele_SearchBox);
				enterText(ele_SearchBox,searchData,obj_Name);

				//Locate search button and click
				setValue(2);
				By by_SearchButton = getBy(locatorType,value);
				WebElement click_SearchButton = driver.findElement(by_SearchButton);
				clickElement(click_SearchButton,obj_Name);

				Thread.sleep(3000);

				//Validate show result
				setValue(3);
				By by_ResultList = getBy(locatorType,value);
				WebElement ele_ResultList = driver.findElement(by_ResultList);
				String val1 = ele_ResultList.getText();
				Assert.assertTrue(val1.contains("results for"));

				//Locate and click product
				setValue(26);
				By by_Product = getBy(locatorType,value);
				WebElement click_Product = driver.findElement(by_Product);
				clickElement(click_Product,obj_Name);

				Thread.sleep(3000);

				//Validate navigation to product page
				String val2 = driver.getTitle();
				Assert.assertTrue(val2.contains("Head First Java, 2nd Edition"));

				//Locate and click quantity button
				setValue(27);
				By by_Quantity = getBy(locatorType,value);
				WebElement click_Quantity = driver.findElement(by_Quantity);
				clickElement(click_Quantity,obj_Name);

				//Select from drop down
				setValue(28);
				By by_QuantityDD = getBy(locatorType,value);
				List<WebElement> ele_DD = driver.findElements(by_QuantityDD);
				for(int val=0;val<=Integer.parseInt(quantity);val++)
				{
					if(val==Integer.parseInt(quantity))
					{
						WebElement ele = ele_DD.get(val);
						ele.click();
					}
				}

				Thread.sleep(3000);

				//Locate and click Add to cart button
				setValue(29);
				By by_AddtoCart = getBy(locatorType,value);
				WebElement click_AddtoCart = driver.findElement(by_AddtoCart);
				clickElement(click_AddtoCart,obj_Name);

				Thread.sleep(3000);

				//Locate and validate quantity
				setValue(8);
				By by_Items = getBy(locatorType,value);
				WebElement ele_Items = driver.findElement(by_Items);
				String items = ele_Items.getText();
				int qntyt = Integer.parseInt(quantity)+1;
				quantity=Integer.toString(qntyt);
				result=verify(quantity, items);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_8",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_8",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_9() throws IOException
	{
		startReport("Amazon_TestCase_9","C:/Users/Seema/Desktop/AmazonReport/","Chrome");
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase9.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String searchData = (String) data[i][1];
				String quantity = (String) data[i][2];
				String updateQuantity = (String) data[i][3];

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				//driver.manage().window().maximize();

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate search text box and enter text
				setValue(1);
				By by_SearchBox = getBy(locatorType,value);
				WebElement ele_SearchBox = driver.findElement(by_SearchBox);
				clear(ele_SearchBox);
				enterText(ele_SearchBox,searchData,obj_Name);

				//Locate search button and click
				setValue(2);
				By by_SearchButton = getBy(locatorType,value);
				WebElement click_SearchButton = driver.findElement(by_SearchButton);
				clickElement(click_SearchButton,obj_Name);

				Thread.sleep(3000);

				//Validate show result
				setValue(3);
				By by_ResultList = getBy(locatorType,value);
				WebElement ele_ResultList = driver.findElement(by_ResultList);
				String val1 = ele_ResultList.getText();
				Assert.assertTrue(val1.contains("results for"));

				//Locate and click product
				setValue(26);
				By by_Product = getBy(locatorType,value);
				WebElement click_Product = driver.findElement(by_Product);
				clickElement(click_Product,obj_Name);

				Thread.sleep(3000);

				//Validate navigation to product page
				String val2 = driver.getTitle();
				Assert.assertTrue(val2.contains("Head First Java, 2nd Edition"));

				//Locate and click quantity button
				setValue(27);
				By by_Quantity = getBy(locatorType,value);
				WebElement click_Quantity = driver.findElement(by_Quantity);
				clickElement(click_Quantity,obj_Name);

				//Select from drop down
				setValue(28);
				By by_QuantityDD = getBy(locatorType,value);
				List<WebElement> ele_DD = driver.findElements(by_QuantityDD);
				for(int val=0;val<=Integer.parseInt(quantity);val++)
				{
					if(val==Integer.parseInt(quantity))
					{
						WebElement ele = ele_DD.get(val);
						ele.click();
					}
				}

				Thread.sleep(3000);

				//Locate and click Add to cart button
				setValue(29);
				By by_AddtoCart = getBy(locatorType,value);
				WebElement click_AddtoCart = driver.findElement(by_AddtoCart);
				clickElement(click_AddtoCart,obj_Name);

				Thread.sleep(3000);

				//Locate and validate quantity
				setValue(8);
				By by_Items = getBy(locatorType,value);
				WebElement ele_Items = driver.findElement(by_Items);
				String items = ele_Items.getText();
				int qntyt = Integer.parseInt(quantity)+1;
				quantity=Integer.toString(qntyt);
				Assert.assertTrue(items.contains(quantity));

				//Locate and click on cart button
				setValue(18);
				By by_Cart = getBy(locatorType,value);
				WebElement click_Cart = driver.findElement(by_Cart);
				clickElement(click_Cart,obj_Name);

				Thread.sleep(4000);

				//Locate and click quantity
				setValue(30);
				By by_QuantityCart = getBy(locatorType,value);
				WebElement click_QuantityCart = driver.findElement(by_QuantityCart);
				clickElement(click_QuantityCart,obj_Name);

				//Locate and select quantity
				setValue(31);
				By by_QuantityDD2 = getBy(locatorType,value);
				List<WebElement> ele_DD2 = driver.findElements(by_QuantityDD2);
				for(int val=0;val<=Integer.parseInt(updateQuantity);val++)
				{
					if(val==Integer.parseInt(updateQuantity))
					{
						WebElement ele = ele_DD2.get(val);
						ele.click();
					}
				}

				Thread.sleep(3000);

				//Validate quantity
				setValue(34);
				By by_SubTotal = getBy(locatorType,value);
				WebElement ele_SubTotal = driver.findElement(by_SubTotal);
				String updatedQty= ele_SubTotal.getText();
				int n = Integer.parseInt(updateQuantity)+1;
				updateQuantity = Integer.toString(n);
				Assert.assertTrue(updatedQty.contains(updateQuantity));

				//Locate and click save for later
				setValue(32);
				By by_SaveLater = getBy(locatorType,value);
				WebElement click_SaveLater = driver.findElement(by_SaveLater);
				clickElement(click_SaveLater,obj_Name);

				Thread.sleep(3000);

				//Locate and validate Message
				setValue(33);
				By by_SaveMsg = getBy(locatorType,value);
				WebElement ele_SaveMsg = driver.findElement(by_SaveMsg);
				String actual = ele_SaveMsg.getText();

				result=verify("has been moved to Save for Later.",actual);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_9",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_9",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}

	public static String TestCase_10() throws IOException
	{
		startReport("Amazon_TestCase_10","C:/Users/Seema/Desktop/AmazonReport/","Chrome");
		try
		{
			String orPath = "C:/Users/Seema/Desktop/AmazonData/ObjectRepository.xls";
			String tdPath = "C:/Users/Seema/Desktop/AmazonData/TD_TestCase10.xls";
			readLocators(orPath);
			readTestData(tdPath);
			for(int i=1;i<data.length;i++)
			{
				String url = (String) data[i][0];
				String searchData = (String) data[i][1];

				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

				//Launch application
				driver.get(url);
				//driver.manage().window().maximize();

				Thread.sleep(5000);

				//Validate application launch
				Assert.assertEquals(driver.getTitle(), "Amazon.com: Online Shopping for Electronics, Apparel, Computers, Books, DVDs & more");

				//Locate search text box and enter text
				setValue(1);
				By by_SearchBox = getBy(locatorType,value);
				WebElement ele_SearchBox = driver.findElement(by_SearchBox);
				clear(ele_SearchBox);
				enterText(ele_SearchBox,searchData,obj_Name);

				//Locate and verify drop down value
				setValue(35);
				By by_Suggetsions = getBy(locatorType,value);
				List<WebElement> ele_Suggetsions = driver.findElements(by_Suggetsions);
				String actual = null;
				for(WebElement w: ele_Suggetsions)
				{
					actual=w.getText();
				}

				result=verify("iphone",actual);

				//Send result for print
				if(result.equals("Pass"))
				{
					Update_Report( result, "Amazon_TestCase_10",  "Execution Completed",driver);
				}
				else
				{
					Update_Report( result, "Amazon_TestCase_10",  "Verification Failed",driver);
				}
				driver.close();
			}

			bw.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		return result;
	}



}
