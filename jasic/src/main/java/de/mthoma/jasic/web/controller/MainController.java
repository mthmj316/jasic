package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.mthoma.jasic.data.entities.IndexEntry;

@Controller
public class MainController {

	private static final String MAIN_URL = "/main";
	
	public static final String MAIN_PAGE = "main";
	
	private static final String PRACTICE_MAIN_PAGE = "practice/practice_main";
	
	private static final String ACTION_ENTER_APPLICATION = "enter_application";
	
	@GetMapping(value = MAIN_URL)
	public String main(Model model) {
		
		return MAIN_PAGE;
	}
	
	@PostMapping(value = ACTION_ENTER_APPLICATION, params = "theory")
	public String enterTheory(@ModelAttribute  IndexEntry indexEntry, Model model) {
		
		return "redirect:" + KnowledgeBaseController.KNOWLEDGE_BASE_URL;
	}
	
	@PostMapping(value = ACTION_ENTER_APPLICATION, params = "practice")
	public String enterPractice(@ModelAttribute  IndexEntry indexEntry, Model model) {
		
		return PRACTICE_MAIN_PAGE;
	}
}
