package assignment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.google.common.io.Files;

public class sausedemo 
{
	WebDriver w;
	
	@BeforeTest
	public void launch() throws Exception
	{
		FileInputStream fis = new FileInputStream("./Data/config.properties");
		Properties p = new Properties();
		p.load(fis);
		String browser =p.getProperty("browser");
		String env =p.getProperty("env");
		URL url = new URL("http://localhost:4444/wd/hub");
		
		if(browser.equalsIgnoreCase("chrome"))
		{
			ChromeOptions op = new ChromeOptions();
			op.addArguments("--incognito");
			if(env.equalsIgnoreCase("remote"))
			{
				op.setCapability("browserName",browser);
				w= new RemoteWebDriver(url, op);
			}
			else
			{
				w= new ChromeDriver(op);
			}
			
		}
		if(browser.equalsIgnoreCase("MicrosoftEdge"))
		{
			EdgeOptions op = new EdgeOptions();
			op.addArguments("inprivate");
			if(env.equalsIgnoreCase("remote"))
			{
				op.setCapability("browserName",browser);
				w= new RemoteWebDriver(url, op);
			}
			else
			{
				w= new EdgeDriver(op);
			}
			
		}
	
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		
	}
	
	@Test
	public void additem() throws Exception
	{
		w.get("https://www.saucedemo.com/");
		w.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
		w.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
		w.findElement(By.cssSelector("#login-button")).click();
		
		FileInputStream fis = new FileInputStream("./Data/login.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(1);
		int rowcount = sh.getLastRowNum();
		for(int i=0;i<rowcount;i++)
		{
			XSSFRow row = sh.getRow(i+1);
			XSSFCell cell = row.getCell(0);
			String product = cell.getStringCellValue().trim();
			
			List<WebElement> list = w.findElements(By.xpath("//div[@class='inventory_item']"));
			for(WebElement temp :list)
			{
				String prodcutEle = temp.findElement(By.xpath("div[2]/div/a")).getText().trim();
				String price = temp.findElement(By.xpath("div[2]/div[2]/div")).getText().trim();						
				XSSFCell pricecell = row.createCell(1);
				if(product.contains(prodcutEle))
				{
					pricecell.setCellValue(price);
					temp.findElement(By.xpath("div[2]/div[2]/button")).click();
					FileOutputStream fos = new FileOutputStream("./Data/login.xlsx");
					wb.write(fos);
					break;
				}
			}			
		}//for
		
		wb.close();
		
		basket();
		screenshot();
		
	}

	public void screenshot() throws Exception {
		// TODO Auto-generated method stub
	
		TakesScreenshot tc = (TakesScreenshot)w;
		File src = tc.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshot/confirm.png");
		Files.copy(src, dest);
	}

	public void basket() 
	{
		w.findElement(By.cssSelector(".shopping_cart_link")).click();
		List<WebElement> list = w.findElements(By.xpath("//div[@class='cart_item_label']"));
		{
			for(WebElement temp :list)
			{
				String item = temp.findElement(By.xpath("a")).getText().trim();
				System.out.println(item);
			}
		}
		
		w.findElement(By.cssSelector("#checkout")).click();
		w.findElement(By.cssSelector("#first-name")).sendKeys("ashish");
		w.findElement(By.cssSelector("#last-name")).sendKeys("yekhande");
		w.findElement(By.cssSelector("#postal-code")).sendKeys("421005");
		w.findElement(By.cssSelector("#continue")).click();
		w.findElement(By.cssSelector("#finish")).click();
		
	}
	

	public String exceldatRead() throws Exception
	{
		FileInputStream fis = new FileInputStream("./Data/login.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(1);
		int rowcount = sh.getLastRowNum();
		
		String prodlist ="";
		for(int i =0;i<rowcount;i++)
		{
			XSSFRow row = sh.getRow(i+1);
			XSSFCell cell = row.getCell(0);
			String product = cell.getStringCellValue().trim();
			prodlist =prodlist+ product+ ",";       // creating , seperated list of all items and then using it further
			
		}
		return prodlist;
		
	}
	
	@Test
	public void additemMethod2() throws Exception
	{
		w.get("https://www.saucedemo.com/");
		w.findElement(By.cssSelector("#user-name")).sendKeys("standard_user");
		w.findElement(By.cssSelector("#password")).sendKeys("secret_sauce");
		w.findElement(By.cssSelector("#login-button")).click();
		
		// making array of product splitting them with ,
		String productlist =exceldatRead();
		String productsArry[] = productlist.split(",");
		
		for(String product :productsArry)
		{
			List<WebElement> list = w.findElements(By.xpath("//div[@class='inventory_item']"));
			for(WebElement temp :list)
			{
				String prodcutEle = temp.findElement(By.xpath("div[2]/div/a")).getText().trim();
				if(product.contains(prodcutEle))
				{
					temp.findElement(By.xpath("div[2]/div[2]/button")).click();
				}
			}			
		}//for
			
		basket();
		screenshot();
}
	@AfterTest
	public void terninate()
	{
		w.quit();
	}
}
