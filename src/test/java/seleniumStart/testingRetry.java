package seleniumStart;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class testingRetry 
{

	WebDriver w;
	@BeforeTest
	public void launch()
	{
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--incognito");
		w= new ChromeDriver(op);
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
	}
	
	@Test(retryAnalyzer = retry.class)
	public void selectEx()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		WebElement ele = w.findElement(By.xpath("//select[@id= 'dropdown-class-example']"));
		Select s = new Select(ele);
		s.selectByIndex(1);
		s.selectByValue("option3");
		s.selectByVisibleText("Option1");
		assertEquals(true,false);
		
	}
	
	@AfterTest
	public void terminate()
	{
		w.quit();
	}
	
	
	
	
	
	
}
