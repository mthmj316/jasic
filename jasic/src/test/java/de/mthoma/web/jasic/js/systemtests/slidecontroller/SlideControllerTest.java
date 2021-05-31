package de.mthoma.web.jasic.js.systemtests.slidecontroller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;

import de.mthoma.web.jasic.testutils.SeleniumCore;
import de.mthoma.web.jasic.testutils.TestUrls;


@SpringBootTest(classes={de.mthoma.jasic.web.JasicApplication.class})
public class SlideControllerTest {

	private final static WebDriver DRIVER = SeleniumCore.getChromeDriver();

	private static final String CLICK_PATH = "mechanics|mechanics_kinematics|mechanics_kinematics_rectilinear_motion";
	private static final String INITIAL_SLIDE_ID = "slide_0";
	private static final int SLIDE_COUNT = 5;
	private static final String SLIDE_CONTAINER_ID = "slide_container";
	
	@BeforeAll
	public static void initialSetup() {
		DRIVER.manage().window().maximize();
		DRIVER.get(TestUrls.MAIN);		
	}

	@AfterAll 
	public static void finalCleanup() {
		DRIVER.close();
	}
	
	@Test
	public void testSlideNavigation() {
		
		//Click through to slide show
		for(String clickPathElement : CLICK_PATH.split(Pattern.quote("|"))) {
			DRIVER.findElement(By.id(clickPathElement)).click();
		}
		
		//Get Slide container
		WebElement slideContainer = DRIVER.findElement(By.id(SLIDE_CONTAINER_ID));
		assertNotNull(slideContainer);
		
		List<WebElement> childElements = slideContainer.findElements(By.xpath("*"));
		
		assertEquals(SLIDE_COUNT, childElements.size());
		
		//Check if initial slide is visible and all others are invisible
		this.checkSlideVisibility(childElements, INITIAL_SLIDE_ID);
		
		//Check next button
		for(int i = 1; i <= SLIDE_COUNT; i++) {
			//i == 5 means that the index must be visible again.			

			DRIVER.findElement(By.id("page_flip_btn_right")).click();
			
			this.checkSlideVisibility(slideContainer.findElements(By.xpath("*")), "slide_" + (i == SLIDE_COUNT ? 0 : i));
		}
		
		//Slide show should be at slide_0 (index)

		//Check previous button
		for(int i = (SLIDE_COUNT - 1); i >= 0; i--) {

			DRIVER.findElement(By.id("page_flip_btn_left")).click();
			
			this.checkSlideVisibility(slideContainer.findElements(By.xpath("*")), "slide_" + i);
		}	
	}
	
	private void checkSlideVisibility(List<WebElement> childElements, String idOfVisibleSlide) {
		
		String id;
		String display;
		for(WebElement childElement : childElements) {
			
			id = childElement.getAttribute("id");
			display = childElement.getCssValue("display");
			
			if(idOfVisibleSlide.equalsIgnoreCase(id)) {
				
				assertEquals("block", display, "Check " + idOfVisibleSlide + " failed");
				
			} else {
				
				assertEquals("none", display, "Check " + idOfVisibleSlide + " failed");
			}
		}
	}
}
