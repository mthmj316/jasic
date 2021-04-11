package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController extends JasicController{

	private static final String THEORY = "theory";

	public static final String PRACTICE = "practice";

	public static final String MAIN_URL = "/main";
	
	public static final String MAIN_PAGE = "main";
	
	public static final String ACTION_ENTER_APPLICATION = "enter_application";
	
	@GetMapping(value = MAIN_URL)
	public String main(Model model) {
		
		return MAIN_PAGE;
	}
	
	@PostMapping(value = ACTION_ENTER_APPLICATION, params = THEORY)
	public String enterTheory(Model model) {
		
		return super.createRedirectString(KnowledgeBaseController.KNOWLEDGE_BASE_URL);
	}
	
	@PostMapping(value = ACTION_ENTER_APPLICATION, params = PRACTICE)
	public String enterPractice(Model model) {
		
		return PracticeController.PRACTICE_MAIN_PAGE;
	}
}
