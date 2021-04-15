package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModuleTestController {

	@GetMapping(value = "/module_test.html")
	public String createEntry(Model model) {
		return "module_test.html";
	}

}
