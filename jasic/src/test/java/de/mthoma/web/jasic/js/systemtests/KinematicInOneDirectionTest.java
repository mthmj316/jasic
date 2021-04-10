package de.mthoma.web.jasic.js.systemtests;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.springframework.boot.test.context.SpringBootTest;

import de.mthoma.web.jasic.testutils.SeleniumCore;
import de.mthoma.web.jasic.testutils.TestUrls;


@SpringBootTest(classes={de.mthoma.jasic.web.JasicApplication.class})
public class KinematicInOneDirectionTest {

	private final static WebDriver DRIVER = SeleniumCore.getChromeDriver();

	@BeforeAll
	public static void initialSetup() {
		DRIVER.manage().window().maximize();
		DRIVER.get(TestUrls.MAIN);
		DRIVER.findElement(By.id("id_practice_submit")).click();
		DRIVER.findElement(By.xpath("//a[@href='/practice/kinematic_in_one_direction']")).click();
	}

	@AfterAll 
	public static void finalCleanup() {
		DRIVER.close();
	}
	
	/**
	 * 
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "kinematicInOneDirection_onCalculate_AlertTestData.csv", numLinesToSkip = 1)
	public void testOnCalculateAlert(String testcase, String s_t_input, String t_input, String errorMsg) {
		
		this.setInputAndCalculate(s_t_input, t_input);
		
		Alert alert = ExpectedConditions.alertIsPresent().apply(DRIVER);
		
		assertNotNull("alert", alert);
		
		String actual = alert.getText();
		
		assertEquals(errorMsg, actual);
		
		alert.dismiss();		
	}
	
	/**
	 * 
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "kinematicInOneDirection_onCalculate_TestData.csv", numLinesToSkip = 1)
	public void testOnCalculate(
			String testcase, String s_t_input, String t_input, 
			String mathjax_s_t, String mathjax_s_t_result,
			String mathjax_v_t, String mathjax_v_t_result, 
			String mathjax_a_t, String mathjax_a_t_result) {
		
		this.setInputAndCalculate(s_t_input, t_input);
		
		String innerText = DRIVER.findElement(By.id("way_function_mathjax")).getAttribute("innerText");

		assertEquals(mathjax_s_t.substring(2, 3), innerText.substring(0, 1), "mathjax_s_t");
		
		innerText = DRIVER.findElement(By.id("way_for_time_value_input")).getAttribute("innerText");

		assertEquals(mathjax_s_t_result.substring(2, 3), innerText.substring(0, 1), "mathjax_s_t_result");
		
		innerText = DRIVER.findElement(By.id("speed_function_mathjax")).getAttribute("innerText");

		assertEquals(mathjax_v_t.substring(2, 3), innerText.substring(0, 1), "mathjax_v_t");
		
		innerText = DRIVER.findElement(By.id("speed_for_time_value_input")).getAttribute("innerText");

		assertEquals(mathjax_v_t_result.substring(2, 3), innerText.substring(0, 1), "mathjax_v_t_result");
		
		innerText = DRIVER.findElement(By.id("acceleration_function_mathjax")).getAttribute("innerText");

		assertEquals(mathjax_a_t.substring(2, 3), innerText.substring(0, 1), "mathjax_a_t");
		
		innerText = DRIVER.findElement(By.id("acceleration_for_time_value_input")).getAttribute("innerText");

		assertEquals(mathjax_a_t_result.substring(2, 3), innerText.substring(0, 1), "mathjax_a_t_result");
	}
	
	private void setInputAndCalculate(String s_t_input, String t_input) {

		
		DRIVER.findElement(By.id("way_function_input")).clear();
		DRIVER.findElement(By.id("time_value_input")).clear();
		
		DRIVER.findElement(By.id("way_function_input")).sendKeys(s_t_input);
		DRIVER.findElement(By.id("time_value_input")).sendKeys(t_input);

		DRIVER.findElement(By.id("id_calculate_submit")).click();
	}
}
