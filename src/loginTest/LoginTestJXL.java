package loginTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class LoginTestJXL {

	String[][] data = null;
	WebDriver driver;

	@DataProvider(name = "TestData")
	public String[][] FetchData() throws BiffException, IOException {
		data = FetchDataFromExcel();
		return data;
	}

	public String[][] FetchDataFromExcel() throws BiffException, IOException {
		FileInputStream excel = new FileInputStream("C:\\Users\\vviss\\Downloads\\TestData.xls");

		Workbook workbook = Workbook.getWorkbook(excel);

		Sheet sheet = workbook.getSheet(0);

		int rowCount = sheet.getRows();
		int columnCount = sheet.getColumns();

		String[][] testdata = new String[rowCount - 1][columnCount];

		for (int i = 1; i < rowCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				testdata[i - 1][j] = sheet.getCell(j, i).getContents();
			}
		}
		return testdata;
	}

	@BeforeTest
	public void OpenBrowser() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
	}

	@Test(dataProvider = "TestData")
	public void LoginTest(String uname, String pword) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2000));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));

		WebElement username = driver.findElement(By.name("username"));

		username.sendKeys(uname);

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys(pword);

		WebElement loginBtn = driver.findElement(By
				.cssSelector("button[class='oxd-button oxd-button--medium oxd-button--main orangehrm-login-button']"));
		loginBtn.click();

	}

	@AfterMethod
	public void NavigateBrowser() {
		driver.navigate().back();
		WebElement username = driver.findElement(By.name("username"));
		username.clear();
		WebElement password = driver.findElement(By.name("password"));
		password.clear();
	}
	
	
	
	@AfterTest
	public void CloseBrowser() {
		//driver.quit();
	}

}
