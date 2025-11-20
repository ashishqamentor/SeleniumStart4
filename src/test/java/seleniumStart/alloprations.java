package seleniumStart;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.xml.xpath.XPath;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

public class alloprations extends launchbrowser {

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

	public void dynamicDropdown(String country) throws InterruptedException
	{
		w.get("https://rahulshettyacademy.com/dropdownsPractise/");
		w.findElement(By.xpath("//input[contains(@id, 'autosuggest' )]")).sendKeys("China");
		
		List<WebElement> list = w.findElements(By.xpath("//li[@role='presentation']/a")); //[ webele 1,ele2 ,ele3,..]
		for(WebElement temp :list)
		{
			String countryname = temp.getText();
			System.out.println(countryname);
			if(countryname.equalsIgnoreCase(country))
			{
				Thread.sleep(2000);
				temp.click();
				break;
			}
		}
	}
	
	public void multiplewindow()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		String  originalwindow= w.getWindowHandle();   //dfgfdht34534646fhdf
		System.out.println(w.getTitle());
		w.findElement(By.cssSelector("#opentab")).click();   // 2
		Set<String> allwindow=   w.getWindowHandles();
		
		for(String temp :allwindow)
		{
			System.out.println(temp);   //
			  if(!originalwindow.equalsIgnoreCase(temp))     
			  {
				  w.switchTo().window(temp);
				  System.out.println(w.getTitle());
				  
				  
				  w.switchTo().window(originalwindow);
				  System.out.println(w.getTitle());
				//  w.close();
				  break;
				  
			  }			
		}
		w.quit();
		
	}
	
	/*
	 * opon  https://rahulshettyacademy.com/AutomationPractice/ site  ,
	 * open new window
	 * make list of menu and click on  "about us' from menu
	 * 
	*/

	public void table()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		List<WebElement> courselist = w.findElements(By.xpath("//table[@id='product']/tbody/tr/td[2]"));
		
		for(WebElement temp : courselist)
		{
			String course = temp.getText();
			System.out.println(course);
			if(course.equalsIgnoreCase("Master Selenium Automation in simple Python Language"))
			{
				String price = temp.findElement(By.xpath("following-sibling ::td")).getText();
				System.out.println(price);
				break;
			}
						
		}
		
	}
	
	/*
	 * get all details of from table for rolaldo
	 */
	
	public void frameEx()
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		System.out.println(w.getTitle());
		WebElement frame = w.findElement(By.cssSelector("#courses-iframe"));
		w.switchTo().frame(frame);
		System.out.println(w.getTitle());
		
		List<WebElement> itemsinframe = w.findElements(By.xpath("//div[@role='region']/following-sibling::div/nav/div/div/div[2]/a"));
		for(WebElement temp :itemsinframe)
		{
			String menu = temp.getText();
			System.out.println(menu);
		}
	}
	
	public void takescreenshot() throws Exception
	{
		w.get("https://rahulshettyacademy.com/AutomationPractice/");
		System.out.println(w.getTitle());
		
		TakesScreenshot tc = (TakesScreenshot) w;
		File src = tc.getScreenshotAs(OutputType.FILE);
		File dest  = new File("./Screenshot/test.png");
		Files.copy(src, dest);
		
	}
		
	public void waitEx()
	{
		w.get("https://www.hyrtutorials.com/p/waits-demo.html");
		w.findElement(By.id("btn1")).click();
		w.findElement(By.id("txt1")).sendKeys("ashish");
	}
	
	public void waitEx1()
	{
		w.get("https://www.hyrtutorials.com/p/waits-demo.html");
		w.findElement(By.id("btn1")).click();
	
		By b = By.id("txt1");		
		waitforBy(b,w).sendKeys("ashish");
				
		//WebDriverWait wait = new WebDriverWait(w, Duration.ofSeconds(10));
		//wait.until(  ExpectedConditions.visibilityOfElementLocated(b)).sendKeys("ashish");
	}
	
	public void fulentwait()
	{
		w.get("https://www.hyrtutorials.com/p/waits-demo.html");
		w.findElement(By.id("btn1")).click();
	//	w.findElement(By.id("txt1")).sendKeys("ashish");
		
		Wait<WebDriver> wait = new FluentWait<>(w)
				.withTimeout(Duration.ofSeconds(10))
				.pollingEvery(Duration.ofMillis(250))
				.ignoring(NoSuchElementException.class)
				.ignoring(ElementClickInterceptedException.class);
		
		By b = By.id("txt1");
		wait.until(ExpectedConditions.visibilityOfElementLocated(b)).sendKeys("ashish");
	}
	
  	public static void main(String[] args) 
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
		//o.fileuploadRobot();
		//o.dynamicDropdown("Taiwan, Province of China");
		//o.multiplewindow();
		//o.table();
		//o.frameEx();
		//o.takescreenshot();
		//o.waitEx();
		o.waitEx1();
		//o.fulentwait();

	}

}
