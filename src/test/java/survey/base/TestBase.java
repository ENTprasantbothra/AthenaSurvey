package survey.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	/*
	 * webdriver
	 * properties
	 * mail
	 * logs
	 * extent reports
	 * db
	 * excel
	*/
	
	public static WebDriver driver;
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public static FileInputStream fis;

	@BeforeSuite
	public void setUp() {
		if(driver == null) {
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\test\\resources\\properties\\Config.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				config.load(fis);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				OR.load(fis);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(config.getProperty("browser").equalsIgnoreCase("firefox")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			}
			else if(config.getProperty("browser").equalsIgnoreCase("chrome")){
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();	
			}
			driver.get(config.getProperty("testsiteurl"));
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implict-wait")), TimeUnit.SECONDS);
		    driver.findElement(By.id(OR.getProperty("username"))).sendKeys(config.getProperty("username"));
		    driver.findElement(By.id(OR.getProperty("password"))).sendKeys(config.getProperty("pass"));
		    driver.findElement(By.id(OR.getProperty("btn-login"))).click();
		
		}
	}
	
	
	@AfterSuite
	public void tearDown() {
		
		if(driver!=null) {
			driver.quit();
		}
	}
}
