package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.mthoma.jasic.data.entities.IndexEntry;

@Controller
public class PracticeController {

	private static final String KINEMATIC_IN_ONE_DIRECTION_URL = "/practice/kinematic_in_one_direction";
	
	private static final String KINEMATIC_IN_ONE_DIRECTION_PAGE = "practice/kinematic_in_one_direction";
	
	private static final String PRACTICE_MAIN = "/practice_main";
	
	private static final String BACK = "back";
	
	
	@GetMapping(value = KINEMATIC_IN_ONE_DIRECTION_URL)
	public String getKinematicInOneDirectionPage(Model model) {
		
		return KINEMATIC_IN_ONE_DIRECTION_PAGE;
	}
	
	@PostMapping(value = PRACTICE_MAIN, params = BACK)
	public String back2Main(@ModelAttribute  IndexEntry indexEntry, Model model) {
		
		return "redirect:" + MainController.MAIN_PAGE;
	}
}
