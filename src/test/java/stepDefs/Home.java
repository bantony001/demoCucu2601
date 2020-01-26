package stepDefs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

/**
 * Hello world!
 *
 */
public class Home 
{
    WebDriver driver;
    JavascriptExecutor js;
	String productName="";
	String userName = "";
	String password = "";
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException, IOException {	
    	System.out.println("hi");		

		Class.forName("com.mysql.jdbc.Driver");
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/amazon","root","admin");
    	Statement stmt = con.createStatement();
    	ResultSet rs = stmt.executeQuery("select * from products where productid = 2;");
    	while(rs.next()) {
    		productName = rs.getString("productName");
    	}
    	con.close();
    	System.out.println(productName);		
    	System.out.println("hello");	    	
        File fi = new File("D:\\Softwares\\eclipse-workspace\\demoTest2601\\src\\test\\resources\\testdata.xlsx");
    	FileInputStream fis = new FileInputStream(fi);
    	XSSFWorkbook wb = new XSSFWorkbook(fis);
    	XSSFSheet ws = wb.getSheet("Sheet1");
    	userName = ws.getRow(0).getCell(0).getStringCellValue();
    	password = ws.getRow(1).getCell(0).getStringCellValue();  
	}
	
	@Given("^the user opens the amazon url$")
	public void the_user_opens_the_amazon_url() throws Throwable {

        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "\\src\\test\\resources\\chromedriver.exe");
        driver = new ChromeDriver();            	
        driver.get("https://www.amazon.in/");
        driver.manage().window().maximize();
	}

	@When("^the user searches for the product$")
	public void the_user_searches_for_the_product() throws Throwable {
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
        driver.findElement(By.xpath("//input[@type='submit' and @class='nav-input']")).click();
	}
	
	@When("^the product result appears$")
	public void the_product_result_appears() throws Throwable {
        js = (JavascriptExecutor)driver;
        js.executeScript("window.scrollBy(0,200)");
	}
	
	@When("^the user clicks on the product$")
	public void the_user_clicks_on_the_product() throws Throwable {
        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[@class='a-size-base-plus a-color-base a-text-normal' and contains(text(),'Parachute Advansed Crème Oil, 300 ml')]")).click(); 
	}
	
	@When("^the user adds the product to the cart$")
	public void the_user_adds_the_product_to_the_cart() throws Throwable {
        String parentWindow = driver.getWindowHandle();
        Set<String> allWindows = driver.getWindowHandles();
        for(String currWindow: allWindows) {
	        if(!parentWindow.equals(currWindow)) {
	        	driver.switchTo().window(currWindow);
	        	break;
	        }
    	}
        Thread.sleep(5000);
        js.executeScript("window.scrollBy(0,200)");
        driver.findElement(By.xpath("//input[@id='add-to-cart-button']")).click();
        Thread.sleep(3000);
	}
	
	@Then("^the login page appears$")
	public void the_login_page_appears() throws Throwable {
        driver.findElement(By.xpath("//a[@id='hlb-ptc-btn-native']")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("ap_email")).sendKeys(userName);
        driver.findElement(By.id("continue")).click();
        driver.findElement(By.id("ap_password")).sendKeys(password);   
        driver.findElement(By.id("signInSubmit")).click();
        Thread.sleep(5000);
	}
}
