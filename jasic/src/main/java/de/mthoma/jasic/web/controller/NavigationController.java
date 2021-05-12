package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController extends JasicController{
	
	private static final String NO_SUBJECT_AVAILABLE_URL = "/no_subject_available";	
	private static final String NO_SUBJECT_AVAILABLE_PAGE = "no_subject_available";
	
	private static final String KINEMATICS_IN_ONE_DIRECTION_URL = "/mechanics/kinematics/in_one_direction";
	private static final String KINEMATICS_IN_ONE_DIRECTION_PAGE = "mechanics/kinematics/in_one_direction";
	
	private static final String KINEMATICS_INTRODUCTION_URL = "/mechanics/kinematics/introduction";
	private static final String KINEMATICS_INTRODUCTION_PAGE = "mechanics/kinematics/introduction";
	
	private static final String MECHANICS_INTRODUCTION_URL = "/mechanics/introduction";
	private static final String MECHANICS_INTRODUCTION_PAGE = "mechanics/introduction";
	
//	private static final String MAIN_PAGE = "main";
	
	@GetMapping(value = KINEMATICS_INTRODUCTION_URL)
	public String kinematicsIntroductionlePage(Model model) {
		
		return KINEMATICS_INTRODUCTION_PAGE;
	}
	
	@GetMapping(value = MECHANICS_INTRODUCTION_URL)
	public String mechanicsIntroductionlePage(Model model) {
		
		return MECHANICS_INTRODUCTION_PAGE;
	}
	
	@GetMapping(value = NO_SUBJECT_AVAILABLE_URL)
	public String noSubjectAvailablePage(Model model) {
		
		return NO_SUBJECT_AVAILABLE_PAGE;
	}
	
	@GetMapping(value = KINEMATICS_IN_ONE_DIRECTION_URL)
	public String kinematicsInOneDirection(Model model) {
		
		return KINEMATICS_IN_ONE_DIRECTION_PAGE;
	}
}
