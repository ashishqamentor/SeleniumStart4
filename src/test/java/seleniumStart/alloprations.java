package seleniumStart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class alloprations {

	WebDriver w;
	public void launch()
	{
		ChromeOptions op = new ChromeOptions();
		op.addArguments("--incognito");
		w= new ChromeDriver(op);
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
	}
	
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

	public void locator()
	{
		w.get("https://www.saucedemo.com/");
		w.findElement(By.id("user-name")).sendKeys("standard_user");
		w.findElement(By.name("password")).sendKeys("secret_sauce");
		w.findElement(By.className("submit-button")).click();
	}
	
	public void selectEx()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		WebElement ele = w.findElement(By.xpath("//select[@id= 'dropdown-class-example']"));
		Select s = new Select(ele);
		s.selectByIndex(2);
		s.selectByValue("option3");
		s.selectByVisibleText("Option1");
	}
	
	public void alertEx()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		w.findElement(By.cssSelector("#name")).sendKeys("ashish");
		w.findElement(By.cssSelector("input[id='confirmbtn']")).click();
		Alert  a = w.switchTo().alert();
		String text = a.getText();
		System.out.println(text);
		a.accept(); //yes,approve, accept
		
		//a.dismiss(); //no ,disapprove,deny ,cancle
		
	}
	
	public void actionex()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		
		JavascriptExecutor js = (JavascriptExecutor) w;
        js.executeScript("window.scrollBy(0, 1000);");
		
		WebElement btn = w.findElement(By.xpath("//button[contains(@id ,'mousehover')]"));
		Actions a = new Actions(w);
		a.moveToElement(btn).build().perform();
		
		WebElement top = w.findElement(By.xpath("//a[contains(text(),'Top')]"));
        js.executeScript("arguments[0].click();", top);
		
		//a.moveToElement(top).click().build().perform();
		System.out.println(w.getCurrentUrl());   // #top
		
		a.moveToElement(btn).build().perform();
		WebElement reload =w.findElement(By.xpath("//a[contains(text(),'Reload')]"));
		a.moveToElement(reload).click().build().perform();
		System.out.println(w.getCurrentUrl());  
		
		
	}
	
	public void dragndrop()
	{
		w.get("https://demoqa.com/droppable");
		WebElement dragable = w.findElement(By.cssSelector("#draggable"));
		WebElement dropable = w.findElement(By.xpath("//p[contains(text(),'Drop here')]"));
		
		 Actions a = new Actions(w);
		a.dragAndDrop(dragable, dropable).build().perform();
			
	}
	
	public void fileupload()
	{
		w.get("https://demoqa.com/automation-practice-form");
		JavascriptExecutor js = (JavascriptExecutor) w;
        js.executeScript("window.scrollBy(0, 1000);");
		w.findElement(By.cssSelector("#uploadPicture")).sendKeys("C:\\Users\\koush\\OneDrive\\Desktop\\doc.txt");
		
	}

	public void fileuploadRobot() throws Exception
	{
		w.get("https://www.foundit.in/create_account.html");
		w.findElement(By.xpath("//div[contains(text(),'Upload Resume')]")).click();
		Thread.sleep(2000);
		w.findElement(By.xpath("//div[@class='uploadBtnCont']")).click();
		
		
		//Copy File Path to Clipboard
		String filePath = "C:\\Users\\koush\\OneDrive\\Desktop\\doc.txt";

		StringSelection ss = new StringSelection(filePath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);

		Robot robot = new Robot();
		robot.delay(1000);

		// Press CTRL + V
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);

		robot.delay(500);

		// Press ENTER
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	
	}
	
	public static void main(String[] args) throws Exception
	{
		alloprations o = new alloprations();
		o.launch();
		//o.navigationEx();
		//o.locator();
		//o.selectEx();
		//o.alertEx();
		//o.actionex();
		//o.dragndrop();
		//o.fileupload();
		o.fileuploadRobot();
	}

}
