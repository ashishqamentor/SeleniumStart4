package datareading;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.Duration;

import org.apache.logging.log4j.message.ReusableMessage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class datareadingExcel
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
	
	
	@Test
	public void login() throws Exception
	{
		FileInputStream fis = new FileInputStream("./Data/login.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(0);
		
		int rowcount = sh.getLastRowNum();
		
		for(int i =0 ;i<rowcount;i++)
		{
			XSSFRow row = sh.getRow(i+1);
			XSSFCell usercell = row.getCell(0);
			XSSFCell passcell = row.getCell(1);
			
			String user = usercell.getStringCellValue();
			String password = passcell.getStringCellValue();
			
			w.get("https://www.saucedemo.com/");
			w.findElement(By.cssSelector("#user-name")).sendKeys(user);
			w.findElement(By.cssSelector("#password")).sendKeys(password);
			w.findElement(By.cssSelector("#login-button")).click();
			
			String actual = w.getCurrentUrl();
			String expected = "https://www.saucedemo.com/inventory.html";
			
			XSSFCell result = row.createCell(2);
			FileOutputStream fos = new FileOutputStream("./Data/login.xlsx");
			if(expected.equalsIgnoreCase(actual))
			{
				System.out.println("pass");
			   	result.setCellValue("pass");
				wb.write(fos);
			}
			
			else
			{
				System.out.println("fail");
				result.setCellValue("fail");
				wb.write(fos);
			}
			
			fos.close();
			
		}	
		
	}
	
	
	
	
	
	
	
	
	
	
}
