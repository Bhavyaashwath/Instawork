package com.insta.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass
{

    public static Properties config = null;
    public static WebDriver driver = null;
    public static String Driverpath;
    public static int Option = 0;
    public static DesiredCapabilities capabilities;
    public static boolean logout = true;
    public static String XLpath_controller = null;

    public static void initiateDriver() 
    {
       try{
           config = loadConfigFile("Config.properties");
           Driverpath = config.getProperty("driverpath");               
         	}
       catch (Exception e){

                      e.printStackTrace();
                      Assert.fail("Not able to intiateDriver function in BaseClass"+e.getMessage());
       						}
    }

    public static Properties loadConfigFile(String loadController) 
    {
        try {
            config = new Properties();
            String strFilePath= System.getProperty("user.dir")+"\\src\\main\\java\\com\\insta\\config\\" + loadController;
            System.out.println("BaseClass.loadConfigFile" +strFilePath);

            // Select the path of the Property file Config.properites
            if (isFilePresent(strFilePath)) 
            {
                FileInputStream fp = new FileInputStream(strFilePath);
                config.load(fp);
                Reporter.log("Data from Config properties are sucessfully loaded"+loadController);
                System.out.println( "Fetching data from the path"+ strFilePath + loadController);
            } 
            else 
            {

                System.out.println("Not able to fetch data from config property file:"+loadController);
                Reporter.log("config property file" +loadController +"loaded is failed");
            }
        	} 
        catch (IOException strInputOutputException) 
        	{
            System.out.println("IOException occured in loading config configuration file"+loadController);
            Reporter.log("IOException occured in loading config configuration file"+loadController);
        	} 
        catch (Exception e)
        	{
            e.printStackTrace();
            System.out.println(e.getMessage());
            Reporter.log("Some Exception occured in loading config properties file");
            Assert.fail("Some Exception occured in loading config properties file");
        	}
        return  config;
    }

    
    public static boolean isFilePresent(String strFilePath)
    {
        try{
            if((strFilePath).trim() == "")
            {
               Reporter.log("The file path is incorrect verify the file path");
               return false;
            }
            else
            {
                File file = new File(strFilePath);
                boolean exists = file.exists();

                if(exists)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            }
        catch(Exception e) 
        	{
            e.printStackTrace();
            System.out.println("Not able to find the path for fetching file");
            return false;
            }
    }
}
