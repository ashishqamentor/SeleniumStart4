package datareading;

import static org.testng.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class datareadingDataprovider
{
	WebDriver w;
	@BeforeTest
	public void launch() throws Exception
	{
		
		FileInputStream fis = new FileInputStream("./Data/config.properties");
		Properties p = new Properties();
		p.load(fis);
		
		String enviormnet =p.getProperty("env");
		String browser = p.getProperty("browser");
		String plat =p.getProperty("platform");
		
		if(enviormnet.equalsIgnoreCase("remote"))
		{
			// when there is remote
			DesiredCapabilities cap = new DesiredCapabilities();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--incognito");
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			
			cap.setBrowserName(browser);
			cap.getBrowserVersion();
			URL url = new URL("http://localhost:4444/wd/hub");
			w = new RemoteWebDriver(url, cap);
			
		}
		else
		{
			//local
			ChromeOptions op = new ChromeOptions();
			op.addArguments("--incognito");
			w= new ChromeDriver(op);
			
		}
					
		w.manage().window().maximize();
		w.manage().deleteAllCookies();
		w.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));	
	}
	
	@Test(dataProvider = "userpass" )
	public void login(String user, String pass, String expected)
	{
		w.get("https://www.saucedemo.com/");
		w.findElement(By.cssSelector("#user-name")).sendKeys(user);
		w.findElement(By.cssSelector("#password")).sendKeys(pass);
		w.findElement(By.cssSelector("#login-button")).click();
		
		String actual = w.getCurrentUrl();	
		assertEquals(actual, expected);
	}
	
	
	@DataProvider(name = "userpass")  // dat return  2D array object 
	public Object[][] readData() throws Exception
	{
		FileInputStream fis = new FileInputStream("./Data/login.xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sh = wb.getSheetAt(0);
		int rowcount = sh.getLastRowNum();
		
		XSSFRow row = sh.getRow(0);   // int n =5;
		int columncount = row.getLastCellNum();
		Object obj[][] = new Object[rowcount][columncount];  //
		for(int i =0;i<rowcount;i++)
		{
			row =sh.getRow(i+1);  //1
			for(int j =0;j<columncount;j++) //0 1 
			{
				XSSFCell cell =row.getCell(j);  
				obj[i][j] = cell.getStringCellValue();   // obje[0][0]=standard_user
														// obj[0][1] = secret_sauce
														//obj[1][0] =locked_out_user
														//obj[1][1]=secret_sauce
			}
		}
		return obj;
	}
	
	@AfterTest
	public void terminate()
	{
		w.quit();
	}
	

}
