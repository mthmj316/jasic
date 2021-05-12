package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavigationController extends JasicController{
	
	private static final String NO_SUBJECT_AVAILABLE_URL = "/no_subject_available";
	
	private static final String NO_SUBJECT_AVAILABLE_PAGE = "no_subject_available";
	
	private static final String MAIN_PAGE = "main";
	
	@GetMapping(value = NO_SUBJECT_AVAILABLE_URL)
	public String getNoSubjectAvailablePage(Model model) {
		
		return NO_SUBJECT_AVAILABLE_PAGE;
	}
}
