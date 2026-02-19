package demo;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class Selenium4ActionsDemoTest {
	private WebDriver driver; 

    private WebDriverWait wait; 

    private Actions actions; 
    @Test
    public void setup() {
    	driver = new ChromeDriver();             // Selenium Manager auto-handles driver 
        driver.manage().window().maximize(); 
        wait = new WebDriverWait(driver, Duration.ofSeconds(10)); 
        actions = new Actions(driver);           // Selenium 4 W3C Actions under the hood 
    } 
    // 1) HOVER (moveToElement) + pause(Duration)  [W3C stable hover] 
    @Test 
    public void demo_hoverAction() { 
        driver.get("https://the-internet.herokuapp.com/hovers"); 
        WebElement firstFigure = driver.findElements(By.cssSelector("#content > div > div:nth-child(4)")).get(0); 
        // moveToElement triggers hover, pause makes it visible clearly for trainees 
        actions.moveToElement(firstFigure) 
                .pause(Duration.ofMillis(400)) 
                .perform(); 
        WebElement caption = firstFigure.findElement(By.cssSelector("#content > div > div:nth-child(4) > div > h5")); 
        wait.until(ExpectedConditions.visibilityOf(caption)); 
        System.out.println("Hover text = " + caption.getText()); 
        Assert.assertTrue(caption.getText().toLowerCase().contains("user"), 
                "Caption should show user text after hover"); 
    } 
 //    2) RIGHT CLICK (contextClick)  [W3C pointer action] 
    @Test 
    public void demo_rightClickAction() { 
        driver.get("https://the-internet.herokuapp.com/context_menu"); 
        WebElement box = driver.findElement(By.id("hot-spot")); 
        actions.contextClick(box) 
                .pause(Duration.ofMillis(200)) 
                .perform(); 
        Alert alert = wait.until(ExpectedConditions.alertIsPresent()); 
        System.out.println("Alert text = " + alert.getText()); 
        alert.accept(); 
        Assert.assertTrue(true, "Right click worked and alert was handled"); 
    } 
  //  3) KEYBOARD ACTIONS (keyDown/keyUp/sendKeys)  [W3C keyboard sequence] 
    @Test 
    public void demo_keyboard_shiftTyping() { 
        driver.get("https://the-internet.herokuapp.com/forgot_password"); 
        WebElement email = driver.findElement(By.id("email")); 
        email.click(); 
        // Hold SHIFT -> type abc -> becomes ABC (easy fresher demo) 
        actions.keyDown(Keys.SHIFT) 
                .sendKeys("abc") 
                .keyUp(Keys.SHIFT) 
                .perform(); 
        String typed = email.getAttribute("value"); 
        System.out.println("Typed value = " + typed); 
        Assert.assertEquals(typed, "ABC"); 
    } 
   // 4) DRAG & DROP (stable demo using jQuery UI) 

    @Test 
    public void demo_dragAndDrop() { 
        driver.get("https://jqueryui.com/droppable/"); 
        // Page has iframe -> switch into it 
        WebElement frame = driver.findElement(By.cssSelector(".demo-frame")); 
        driver.switchTo().frame(frame); 
        WebElement source = driver.findElement(By.id("draggable")); 
        WebElement target = driver.findElement(By.id("droppable")); 
        actions.dragAndDrop(source, target) 
                .pause(Duration.ofMillis(300)) 
                .perform(); 
        String droppedText = target.getText(); 
        System.out.println("Droppable text = " + droppedText); 
        Assert.assertTrue(droppedText.toLowerCase().contains("dropped")); 
        driver.switchTo().defaultContent(); 
    } 
  //  5) SLIDER (clickAndHold + moveByOffset + release) 
    @Test 
    public void demo_sliderMove() { 
        driver.get("https://the-internet.herokuapp.com/horizontal_slider"); 
        WebElement slider = driver.findElement(By.cssSelector("input[type='range']")); 
        WebElement range = driver.findElement(By.id("range")); 
        String before = range.getText(); 
        System.out.println("Slider before = " + before); 
        // Move slider a bit to the right (pixels). W3C actions make this more consistent. 
        actions.clickAndHold(slider) 
                .pause(Duration.ofMillis(200)) 
                .moveByOffset(60, 0) 
                .release() 
                .perform(); 
        String after = range.getText(); 
        System.out.println("Slider after = " + after); 
        Assert.assertNotEquals(after, before, "Slider value should change after action"); 
    } 
//    6) SCROLL (new in Selenium 4 Actions) - Wheel input support 
    @Test 
    public void demo_scrollByAmount() { 
        driver.get("https://the-internet.herokuapp.com/infinite_scroll"); 
        int before = driver.findElements(By.cssSelector(".jscroll-added")).size(); 
        System.out.println("Paragraph blocks before scroll = " + before); 

        // Selenium 4 Actions can scroll using wheel input 
        actions.scrollByAmount(0, 900).pause(Duration.ofMillis(600)).perform(); 
        actions.scrollByAmount(0, 900).pause(Duration.ofMillis(600)).perform(); 
        int after = driver.findElements(By.cssSelector(".jscroll-added")).size(); 
        System.out.println("Paragraph blocks after scroll = " + after); 
        Assert.assertTrue(after >= before, "After scroll, content should not reduce"); 

    } 
    @AfterMethod(alwaysRun = true) 
    public void tearDown() { 
        if (driver != null) driver.quit(); 
    } 
    	 	
    }
