package de.mthoma.web.jasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TableOfContents {

	@GetMapping(value = "/table_of_contents/create_entry")
	public String createEntry(Model model) {
		return "table_of_contents/create_entry";
	}
	
	@GetMapping(value = "/table_of_contents/modify_entry")
	public String modifyEntry(Model model) {
		return "table_of_contents/modify_entry";
	}
	
	@GetMapping(value = "/table_of_contents/table_of_contents")
	public String tableOfContents(Model model) {
		return "table_of_contents/table_of_contents";
	}
}
