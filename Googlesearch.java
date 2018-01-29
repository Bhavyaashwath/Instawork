package com.insta.scripts;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.insta.utils.BaseClass;

public class Googlesearch 
{
	private static WebDriver driver = null;
	private static WebElement element = null;
	
		@BeforeTest
		@Parameters("browser")
		public void setup(String browser) throws Exception
		{
			BaseClass.initiateDriver();

			if(browser.equalsIgnoreCase("firefox"))
			{

				driver = new FirefoxDriver();
			}
			else if(browser.equalsIgnoreCase("chrome"))
			{
				System.setProperty("webdriver.chrome.driver",BaseClass.Driverpath);
			
				driver = new ChromeDriver();
			}
			else
			{
				
				throw new Exception("Browser is not correct");
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		}
		
		@Test
		public void testParameterWithXML() throws InterruptedException
		{
			// create a scanner so we can read the command-line input
		    @SuppressWarnings("resource")
			Scanner scanner = new Scanner (new InputStreamReader(System.in));

		    //  Enter search text
		    System.out.print("Enter your search text \n");

		    // get their input as a String
		    String keyword = scanner.nextLine();

		    // prompt user to Enter the website to search
		    System.out.print("Enter the targeted site \n");
		    
		    // get their input as a String
		    String websiteName = scanner.nextLine();
		    
		    // prompt user to Enter the number of pages to search
		    System.out.print("Enter Total number of pages to search \n");

		    // get the page number as an int
		    int pageNo = scanner.nextInt();
		    
		   	Boolean found = false;
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get("http://www.google.com");

			element = driver.findElement(By.id("lst-ib"));
			element.sendKeys(keyword);
			element.sendKeys(Keys.RETURN);
			int page = 0;
			while (!found && page<=pageNo)
			{
			try{

				List<WebElement> list = driver.findElements(By.className("_Rm"));
				page++;
				for(int i=0;i<=list.size();i++)
				{
					String link = list.get(i).getText();
					if(link.contains(websiteName))
					{
						System.out.println("Website Found at Page" + page);
						found = true;
						fnHighlightMe(driver,list.get(i));
						break;
					}
				}
				}
			catch (Exception e)
				{
					System.out.println(e);
				}
			if (!found)
			{
				try {
			         driver.findElement(By.xpath(".//*[@id='pnnext']/span[2]")).click();
			         Thread.sleep(10000);
					}
				catch (Exception e)
					{
					   System.out.println(e);
					}
			}
			}		
		}
		
		public static void fnHighlightMe(WebDriver driver,WebElement element) throws InterruptedException
		{
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView();", element);
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element, "color: red; border: 3px solid red;");
			Thread.sleep(1000);
		}
}
	

