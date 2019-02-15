/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Melnikov
 */
public class SeleniumTest {
    
    private static WebDriver driver;
    
    public SeleniumTest() {
    }
    
    @BeforeClass // Выполняется один раз при запуске теста
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/Ptvr16WebLibrary/");
    }
    
    
    @AfterClass 
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
        
    }
    
    @Before 
    public void setUp() {
    }
    
    @After 
    public void tearDown() {
    }

    public void enterTest(){
        System.out.println("Вход");
        WebElement el = driver.findElement(By.id("showLogin"));
         el.click();
         el = driver.findElement(By.id("login"));
         el.sendKeys("TestLogin");
         el = driver.findElement(By.id("password"));
         el.sendKeys("TestPassword");
         el = driver.findElement(By.id("btnEnter"));
         el.click();
    }
     @Test
     public void webAppTest() {
            registration();
            enterTest();
         WebElement el = driver.findElement(By.id("info"));
         assertEquals("Привет TestName, Вы вошли", el.getText());
         System.out.println("Вход произведен");
            addNewBookTest();
             deleteUser();
         System.out.println("Выход");
         el = driver.findElement(By.id("logout"));
         el.click();
         el = driver.findElement(By.id("info"));
         assertEquals("Вы вышли", el.getText());
         System.out.println("Выход произведен");
         System.out.println("Проверка входа");
            enterTest();
         el = driver.findElement(By.id("info"));
         assertEquals("Нет такого пользователя", el.getText());
         System.out.println("Неудачный вход");
     }
     
     public void registration() {
         WebElement el = driver.findElement(By.id("showLogin"));
         el.click();
         el = driver.findElement(By.id("showRegistration"));
         el.click();
         System.out.println("Начало регистрации");
         el = driver.findElement(By.name("name"));
         el.sendKeys("TestName");
         el= driver.findElement(By.name("surname"));
         el.sendKeys("TestSurname");
         el= driver.findElement(By.name("email"));
         el.sendKeys("TestEmail");
         el= driver.findElement(By.name("login"));
         el.sendKeys("TestLogin");
         el= driver.findElement(By.name("password1"));
         el.sendKeys("TestPassword");
         el= driver.findElement(By.name("password2"));
         el.sendKeys("TestPassword");
         el= driver.findElement(By.id("btnReg"));
         el.click();
         System.out.println("Регистрация закончена");
     }
     
     public void addNewBookTest() {
         System.out.println("Добавление тестовой книги");
         WebElement el = driver.findElement(By.id("showAddNewBook"));
         el.click();
         el = driver.findElement(By.name("name"));
         el.sendKeys("TestBoook");
         el = driver.findElement(By.name("author"));
         el.sendKeys("TestAuthor");
         el = driver.findElement(By.name("isbn"));
         el.sendKeys("TestIsbn");
         el = driver.findElement(By.name("count"));
         el.sendKeys("10");
         el = driver.findElement(By.id("btnAdd"));
         el.click();
         el = driver.findElement(By.id("info"));
         assertEquals("Новая книга добавлена", el.getText());
         System.out.println("Книга добавлена");
         deleteBook();
         
     }
     public void deleteBook() {
         System.out.println("Удаление книги");
         WebElement el = driver.findElement(By.id("deleteBook"));
         el.click();
         el = driver.findElement(By.id("info"));
         assertEquals("тестовая книга удалена", el.getText());
         System.out.println("Книга удалена");
     }
     public void deleteUser() {
         System.out.println("Удаление пользователя");
         WebElement el = driver.findElement(By.id("deleteUser"));
         el.click();
         el = driver.findElement(By.id("info"));
         assertEquals("тестовый пользователь удален", el.getText());
         System.out.println("Пользователь удален");
     }
     
}
