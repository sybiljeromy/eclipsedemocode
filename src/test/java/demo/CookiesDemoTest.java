package demo;

import java.time.Duration;
import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class CookiesDemoTest {
	private WebDriver driver;
	public void setup() {
		driver=new ChromeDriver();
		driver.manage().window().maximize(); 
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
				
	}
	@AfterMethod(alwaysRun=true)
	public void teardown() {
		
	}
	@Test
	public void addAndDeleteCookies_demo() {
		driver.get("https://the-internet.herokuapp.com/");
		
		//Add cookie
		Cookie myCookie = new Cookie("trainerCookie","Mohan123");
		driver.manage().addCookie(myCookie);
		
		//verify cookie
		Cookie fetched=driver.manage().getCookieNamed("trainerCookie");
		Assert.assertNotNull(fetched,"Cookie was Not added!");
		Assert.assertEquals(fetched.getValue(),"Mohan123","Cookie value mismatch");
		System.out.println("Added cookie: "+fetched);
		
		//print cookie
		Set<Cookie> all = driver.manage().getCookies();
		System.out.println("Total cookies now: " +all.size());
		for(Cookie c: all) {
			System.out.println("Cookie->"+c.getName()+"="+c.getValue());
		}
			
			//Delete cookie by name
			driver.manage().deleteCookieNamed("trainerCookie");
			Cookie afterDelete = driver.manage().getCookieNamed("trainerCookie");
			Assert.assertNull(afterDelete,"Cookie was Not Deleted");
			System.out.println("Deleted Cookie");
			
			//delete all cookies
			driver.manage().deleteAllCookies();
			Assert.assertEquals(driver.manage().getCookies().size(), 0,"All cookies were not deleted !");
			System.out.println("Delete all cookies");
		}
		
	}
