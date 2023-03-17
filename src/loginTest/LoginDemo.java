package loginTest;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginDemo {
	
	String[][] loginData = {
			{"Admin","admin123"},
			{"admin","admin"},
			{"Admin","admin"},
			{"admin1","admin123"}
	};
	
	
	@DataProvider(name="LoginData")
	public String[][] FetchLoginData()
	{
		return loginData;
	}
	
	@Test(dataProvider = "LoginData")
	public void LoginTestCase(String uname, String pword) throws InterruptedException{
		
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
	
		WebElement username = driver.findElement(By.name("username"));
			
		username.sendKeys(uname);
		
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(pword);
		
		WebElement loginBtn = driver.findElement(By.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']"));
		loginBtn.click();
	}

}
