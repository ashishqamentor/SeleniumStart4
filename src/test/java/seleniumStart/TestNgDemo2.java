package seleniumStart;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNgDemo2 
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

	@Test(priority = 1,groups = "sanity")
	public void dragndrop()
	{
		w.get("https://demoqa.com/droppable");
		WebElement dragable = w.findElement(By.cssSelector("#draggable"));
		WebElement dropable = w.findElement(By.xpath("//p[contains(text(),'Drop here')]"));
		
		 Actions a = new Actions(w);
		a.dragAndDrop(dragable, dropable).build().perform();
			
	}
	

	@Test(priority = 2)
	public void fileupload()
	{
		w.get("https://demoqa.com/automation-practice-form");
		JavascriptExecutor js = (JavascriptExecutor) w;
        js.executeScript("window.scrollBy(0, 1000);");
		w.findElement(By.cssSelector("#uploadPicture")).sendKeys("C:\\Users\\koush\\OneDrive\\Desktop\\doc.txt");
		
	}
	
	@AfterMethod(groups = {"regression","sanity"})
	public void terminate()
	{
		w.quit();
	}
	
	
}
