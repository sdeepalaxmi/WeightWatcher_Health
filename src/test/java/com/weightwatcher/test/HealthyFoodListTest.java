package com.weightwatcher.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


public class HealthyFoodListTest {
  WebDriver driver = null;
  private static final String URL = "src/test/resources/file/HealthyFoodItems.html";

  @BeforeTest
  public void setup() throws InterruptedException, NullPointerException, FileNotFoundException {
    System.setProperty("webdriver.gecko.driver", "src/test/resources/driver/geckodriver");
    driver = new FirefoxDriver();
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    String urlPath = new File(URL).getAbsolutePath();
    driver.get("file://" + urlPath);
    Thread.sleep(1000);
  }

  /*
   * Display nth food item
   */
  @Test
  public void displayNthItemTest()
      throws NullPointerException, NoSuchElementException, TimeoutException, ElementNotVisibleException,
             StaleElementReferenceException {
    ArrayList<Integer> nthItems = new ArrayList();
    nthItems.add(3);
    nthItems.add(5);
    ArrayList<String> foodItems = fetchNthFoodItems(driver, nthItems);
    Assert.assertNotNull(foodItems, "Food items are empty");
    System.out.println("Nth Food item names ");
    System.out.println("**********************");
    for (int idx = 0; idx < foodItems.size(); idx++) {
      System.out.println(nthItems.get(idx) + " - Food item - " + foodItems.get(idx));
    }
  }

  /*
   * list all the food items and serving description
   */
  @Test
  public void listFoodItemsTest()
      throws NullPointerException, NoSuchElementException, TimeoutException, ElementNotVisibleException,
             StaleElementReferenceException {
    HashMap<String, String> foodMap = listFoodItems(driver);
    Assert.assertNotNull(foodMap, "Food items are empty");
    System.out.println("\n*********************************");
    System.out.println("Food Name \t Serving Description");
    System.out.println("*********************************");
    for (Map.Entry<String, String> entry : foodMap.entrySet()) {
      System.out.println(entry.getKey() + " --> " + entry.getValue());
    }
    System.out.println("*********************************");
  }

  @AfterTest
  public void teardown() {
    driver.close();
  }

  /**
   * List the Nth food items
   * @param driver
   * @param list
   * @return ArrayList
   * @throws NullPointerException
   * @throws NoSuchElementException
   * @throws TimeoutException
   * @throws ElementNotVisibleException
   * @throws StaleElementReferenceException
   */
  private ArrayList<String> fetchNthFoodItems(WebDriver driver, ArrayList<Integer> list)
      throws NullPointerException, NoSuchElementException, TimeoutException, ElementNotVisibleException,
             StaleElementReferenceException {
    if (driver == null || list == null || list.size() == 0) {
      throw new NullPointerException("Input is Null. Please check the data");
    }
    ArrayList<String> arrFoodItems = new ArrayList<>();
    for (int nthItem : list) {
      String locator = String.format("(//span[@class='title ng-binding'])[%d]", nthItem);
      WebElement nthElement = driver.findElement(By.xpath(locator));
      arrFoodItems.add(nthElement.getText());
    }
    return arrFoodItems;
  }

  /**
   * List all the food names with serving description
   * @param driver
   * @return HashMap
   * @throws NullPointerException
   * @throws NoSuchElementException
   * @throws TimeoutException
   * @throws ElementNotVisibleException
   * @throws StaleElementReferenceException
   */
  private HashMap<String, String> listFoodItems(WebDriver driver)
      throws NullPointerException, NoSuchElementException, TimeoutException, ElementNotVisibleException,
             StaleElementReferenceException {
    if (driver == null) {
      throw new NullPointerException("Input is Null. Please check the data");
    }
    HashMap<String, String> map = new LinkedHashMap<>();
    List<WebElement> foodName = driver.findElements(By.cssSelector(".title.ng-binding"));
    List<WebElement> foodServing = driver.findElements(By.cssSelector(".description.ng-binding"));
    for (int idx = 0; idx < foodName.size(); idx++) {
      map.put(foodName.get(idx).getText(), foodServing.get(idx).getText());
    }
    return map;
  }
}
