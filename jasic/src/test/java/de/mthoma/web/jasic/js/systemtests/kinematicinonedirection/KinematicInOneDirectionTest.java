package de.mthoma.web.jasic.js.systemtests.kinematicinonedirection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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
	@CsvFileSource(resources = "kinematicInOneDirection_onCalculateExtended_AlertTestData.csv", numLinesToSkip = 1)
	public void testOnCalculateExtendedAlert(String testcase, 
			String sFromT, String t1, String t2, String expected) {
		
		this.setInputAndCalculate(sFromT, t1, t2);
		
		Alert alert = ExpectedConditions.alertIsPresent().apply(DRIVER);
		
		assertNotNull("alert", alert);
		
		String actual = alert.getText();
		
		assertEquals(expected, actual);
		
		alert.dismiss();		
	}
	
	/**
	 * 
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "kinematicInOneDirection_onCalculateExtended_TestData.csv", numLinesToSkip = 1)
	public void testOnCalculateExtended(
			String testcase, String sFromT, String t1, String t2, 
			String mjaxSFromT, String mjaxST1, String mjaxST2, String mjaxDS, 
			String mjaxVFromT, String mjaxVT1, String mjaxVT2, String mjaxAV, 
			String mjaxAFromT, String mjaxAT1, String mjaxAT2, String mjaxAA) {
		
		this.setInputAndCalculate(sFromT, t1, t2);
		
		this.assertInnerText("way_function_mathjax", mjaxSFromT, "mjaxSFromT");		
		this.assertInnerText("way_for_t1", mjaxST1, "mjaxST1");		
		this.assertInnerText("way_for_t2", mjaxST2, "mjaxST2");		
		this.assertInnerText("way_between_t1_and_t2", mjaxDS, "mjaxDS");
		
		this.assertInnerText("speed_function_mathjax", mjaxVFromT, "mjaxVFromT");
		this.assertInnerText("speed_for_t1", mjaxVT1, "mjaxVT1");
		this.assertInnerText("speed_for_t2", mjaxVT2, "mjaxVT2");		
		this.assertInnerText("average_speed_between_t1_and_t2", mjaxAV, "mjaxAV");
		
		this.assertInnerText("acceleration_function_mathjax", mjaxAFromT, "mjaxAFromT");
		this.assertInnerText("acceleration_for_t1", mjaxAT1, "mjaxAT1");
		this.assertInnerText("acceleration_for_t2", mjaxAT2, "mjaxAT2");		
		this.assertInnerText("average_acceleration_between_t1_and_t2", mjaxAA, "mjaxAA");
	}
	
	private void assertInnerText(String tagId, String value, String msg) {
		
		String innerText = DRIVER.findElement(By.id(tagId)).getAttribute("innerText");
		
		if(!value.isEmpty()) {
			assertTrue(msg, !innerText.isEmpty());
		} else {
			assertEquals(value, innerText, msg);
		}
	}
	
	/**
	 * 
	 */
	@ParameterizedTest
	@CsvFileSource(resources = "kinematicInOneDirection_onCalculate_AlertTestData.csv", numLinesToSkip = 1)
	public void testOnCalculateAlert(String testcase, String s_t_input, String t_input, String errorMsg) {
		
		this.setInputAndCalculate(s_t_input, t_input, "");
		
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
		
		this.setInputAndCalculate(s_t_input, t_input, "");
		
		this.assertInnerText("way_function_mathjax", mathjax_s_t, "mathjax_s_t");		
		this.assertInnerText("way_for_t1", mathjax_s_t_result, "mathjax_s_t_result");
		this.assertInnerText("speed_function_mathjax", mathjax_v_t, "mathjax_v_t");
		this.assertInnerText("speed_for_t1", mathjax_v_t_result, "mathjax_v_t_result");
		this.assertInnerText("acceleration_function_mathjax", mathjax_a_t, "mathjax_a_t");
		this.assertInnerText("acceleration_for_t1", mathjax_a_t_result, "mathjax_a_t_result");
	}
	
	private void setInputAndCalculate(String s_t_input, String t1_input, String t2_input) {
		
		DRIVER.findElement(By.id("way_function_input")).clear();
		DRIVER.findElement(By.id("t1_value_input")).clear();
		DRIVER.findElement(By.id("t2_value_input")).clear();
		
		DRIVER.findElement(By.id("way_function_input")).sendKeys(s_t_input);
		DRIVER.findElement(By.id("t1_value_input")).sendKeys(t1_input);		
		DRIVER.findElement(By.id("t2_value_input")).sendKeys(t2_input);

		DRIVER.findElement(By.id("id_calculate_submit")).click();
	}
}
