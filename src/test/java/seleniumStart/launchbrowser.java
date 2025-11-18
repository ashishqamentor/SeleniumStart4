package seleniumStart;

import java.time.Duration;
import java.util.Scanner;

import org.jspecify.annotations.Nullable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class launchbrowser 
{
	public WebDriver w;  //null
	
	// launch browser from use choice  ex user enter chrome , edge
	public void launch(String browsername)
	{
		if(browsername.equalsIgnoreCase("chrome"))
		{
			ChromeOptions op = new ChromeOptions();
			op.addArguments("--incognito");
			w = new ChromeDriver(op);
			
		}
		if(browsername.equalsIgnoreCase("edge"))
		{
			 EdgeOptions op = new EdgeOptions();
			 op.addArguments("-inprivate");
			 w = new EdgeDriver(op);
			
		}if(browsername.equalsIgnoreCase("firefox"))
		{
			FirefoxOptions op = new FirefoxOptions();
			op.addArguments("inprivate");
			 w = new FirefoxDriver(op);
			
		}
		
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
		
	public void openURL(String url) 
	{
		w.get(url);
		String title = w.getTitle();
		String currenturl = w.getCurrentUrl();
		String source = w.getPageSource();
		
		
		System.out.println(title);
		System.out.println(currenturl);
		System.out.println(source);
		
		//w.close(); // close current working window
		w.quit(); // all brpwser open window
		
		
	}
	
	
	public WebElement waitforBy(By b, WebDriver w2)
	{
		WebDriverWait wait = new WebDriverWait(w2, Duration.ofSeconds(10));
		WebElement e = wait.until(  ExpectedConditions.visibilityOfElementLocated(b));
	
		return e;
	}
	
	
	public static void main(String[] args) 
	{
		launchbrowser l  = new launchbrowser();
		
		System.out.println("enter your choice browser");
		Scanner sc = new Scanner(System.in);
		String browsername = sc.next();
		
		l.launch(browsername);
		l.openURL("https://rahulshettyacademy.com/AutomationPractice/"); 
		

	}



	

}
