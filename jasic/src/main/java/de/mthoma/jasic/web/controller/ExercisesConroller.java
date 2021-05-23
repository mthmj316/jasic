package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExercisesConroller {
	                                                        
	public static final String NO_EXERCISES_AVAILABLE_URL = "/exercises/no_exercises_available";
	public static final String NO_EXERCISES_AVAILABLE_PAGE = "exercises/no_exercises_available";
	
	@GetMapping(value = NO_EXERCISES_AVAILABLE_URL)
	public String noExercisesAvailable() {
		
		return NO_EXERCISES_AVAILABLE_PAGE;
	}
}
