package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PracticeController extends JasicController{

	private static final String KINEMATIC_IN_ONE_DIRECTION_URL = "/practice/kinematic_in_one_direction";
	
	private static final String KINEMATIC_IN_ONE_DIRECTION_PAGE = "practice/kinematic_in_one_direction";
	
	public static final String PRACTICE_MAIN = "/practice_main";
	
	private static final String BACK = "back";
	
	private static final String BACK_TO_PRACTICE_MAIN = "back2PracticeMain";
	
	private static final String ACTION_KINEMATIC_IN_ONE_DIRECTION = "/kinematic_in_one_direction";

	public static final String PRACTICE_MAIN_PAGE = "practice/practice_main";
	
	@PostMapping(value = ACTION_KINEMATIC_IN_ONE_DIRECTION, params = BACK_TO_PRACTICE_MAIN)
	public String onActionKinematicInOnDirection(Model model) {
		
		return PRACTICE_MAIN_PAGE;
	}
	
	@GetMapping(value = KINEMATIC_IN_ONE_DIRECTION_URL)
	public String getKinematicInOneDirectionPage(Model model) {
		
		return KINEMATIC_IN_ONE_DIRECTION_PAGE;
	}
	
	@PostMapping(value = PRACTICE_MAIN, params = BACK)
	public String back2Main(Model model) {
		
		return super.redirect2MainPage();
	}
}
