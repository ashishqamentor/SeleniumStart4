package datareading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;


public class datareadProperty 
{

@Test
	public void launch() throws Exception
	{
		FileInputStream fis = new FileInputStream("./config.properties");
		Properties p = new Properties();
		p.load(fis);
		
		String browser = p.getProperty("browser");
		String mode = p.getProperty("mode");
		String env = p.getProperty("env");
		String  secure = p.getProperty("private");
		
		ChromeOptions op = new ChromeOptions();
		op.addArguments(secure);
		WebDriver w = new ChromeDriver(op);
		w.get("https://rahulshettyacademy.com/seleniumPractise/#/");
				
		
		
		
	}
	
	
}
