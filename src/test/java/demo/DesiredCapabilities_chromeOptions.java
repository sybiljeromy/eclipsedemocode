package demo;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class DesiredCapabilities_chromeOptions {
	@Test
	public void localCapabilities_modernOptions_demo() {
		ChromeOptions options = new ChromeOptions();{
			options.addArguments("--start-maximized");
			options.setAcceptInsecureCerts(true);
			WebDriver driver = new ChromeDriver(options);
			try {
				driver.get("https://the-internet.herokuapp.com/");
				Capabilities caps=((HasCapabilities)driver).getCapabilities();
				System.out.println("Browser: "+caps.getBrowserName());
				System.out.println("Browser version: "+caps.getBrowserVersion());
				System.out.println("Platform: "+caps.getPlatformName());
				
				
			}finally {
				//driver.quit();
			}
			
		}
	}

}
