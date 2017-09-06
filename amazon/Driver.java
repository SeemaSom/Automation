package com.amazon;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver
{

	static WebDriver driver;
	static String fileName;

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		String path = "C:/Users/Seema/Desktop/AmazonData/Amazon_TestSuite.xls";
		Object[][] recData = ReusableMethods.readExcel(path);
		String result;

		for(int i=1;i<recData.length;i++)
		{
			try
			{
				Object execute = recData[i][1];
				if(execute.toString().equalsIgnoreCase("y"))
				{
					Object ch_Browser = recData[i][3];
					if(ch_Browser.toString().equalsIgnoreCase("Y"))
					{
						System.setProperty("webdriver.chrome.driver", "C:/Users/Seema/Downloads/chromedriver.exe");
						driver = new ChromeDriver(); 
						Object testCase = recData[i][2];
						Method tc = AutomationScripts_Amazon.class.getMethod(testCase.toString());
						result=  (String) tc.invoke(tc);
						ReusableMethods.writeResult(path,"Sheet1",result, ReusableMethods.htmlname,i,3);
					}

					Object ff_Browser = recData[i][4];
					if(ff_Browser.toString().equalsIgnoreCase("Y"))
					{
						System.setProperty("webdriver.gecko.driver", "C:/Users/Seema/Downloads/geckodriver-v0.18.0-win64/geckodriver.exe");
						driver = new FirefoxDriver();
						Object testCase = recData[i][2];
						Method tc = AutomationScripts_Amazon.class.getMethod(testCase.toString());
						result=  (String) tc.invoke(tc);
						ReusableMethods.writeResult(path,"Sheet1",result, ReusableMethods.htmlname,i,4);
					}

					Object ie_Browser = recData[i][5];
					if(ie_Browser.toString().equalsIgnoreCase("Y"))
					{
						System.setProperty("webdriver.edge.driver", "C:/Users/Seema/Downloads/MicrosoftWebDriver.exe");
						/*DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();

						ieCapabilities.setCapability("nativeEvents", false);    
						ieCapabilities.setCapability("unexpectedAlertBehaviour", "accept");
						ieCapabilities.setCapability("ignoreProtectedModeSettings", true);
						ieCapabilities.setCapability("disable-popup-blocking", true);
						ieCapabilities.setCapability("enablePersistentHover", true);

						driver = new InternetExplorerDriver(ieCapabilities);*/
						driver = new EdgeDriver();
						Object testCase = recData[i][2];
						Method tc = AutomationScripts_Amazon.class.getMethod(testCase.toString());
						result=  (String) tc.invoke(tc);
						ReusableMethods.writeResult(path,"Sheet1",result, ReusableMethods.htmlname,i,5);
					}

				}
			}
			catch(Exception e)
			{
				System.out.println(e);
			}
		}

	}

}
