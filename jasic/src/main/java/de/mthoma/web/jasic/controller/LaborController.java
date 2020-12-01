package de.mthoma.web.jasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LaborController {

	@GetMapping(value = "/labor/inclined_plane")
	public String inclinedPlane(Model model) {
		return "labor/inclined_plane";
	}
}

