package seleniumStart;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNgDemo 
{

	WebDriver w;
	@BeforeMethod(groups = {"regression","sanity"})
	public void launch()
	{
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--incognito");
		w= new ChromeDriver(op);
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
	}
	
	@Test(priority = 2,enabled = false)
	public void navigationEx()
	{
		w.get("https://rahulshettyacademy.com/seleniumPractise/#/");   //1 
		w.navigate().to("https://demoqa.com/automation-practice-form");  //2
		w.navigate().back();
		
		System.out.println(   w.getTitle() );
		w.navigate().forward();
		System.out.println(   w.getTitle() );
		w.navigate().refresh();
	}
	
	@Parameters({"op","op1"})
	@Test(priority = 1,groups = "sanity")
	public void selectEx(String opt,String opt1)
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		WebElement ele = w.findElement(By.xpath("//select[@id= 'dropdown-class-example']"));
		Select s = new Select(ele);
		s.selectByIndex(2);
		s.selectByValue(opt);
		s.selectByVisibleText(opt1);
	}
	
	
	@Parameters({"name"})
	@Test(priority = 3,groups = "regression")
	public void alertEx(String nam)
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		w.findElement(By.cssSelector("#name")).sendKeys(nam);
		w.findElement(By.cssSelector("input[id='confirmbtn']")).click();
		Alert  a = w.switchTo().alert();
		String text = a.getText();
		System.out.println(text);
		a.accept(); //yes,approve, accept
		
		//a.dismiss(); //no ,disapprove,deny ,cancle
		
	}
	
	
	@AfterMethod(groups = {"regression","sanity"})
	public void terminate()
	{
		w.quit();
	}
	

}
