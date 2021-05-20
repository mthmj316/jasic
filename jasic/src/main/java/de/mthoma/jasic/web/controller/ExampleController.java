package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

	public static final String KINEMATICS_RECTILINEAR_MOTION_PATH_URL = "/examples/mechanics/kinematics/rectilinear_motion_path";
	public static final String KINEMATICS_RECTILINEAR_MOTION_PATH_PAGE = "examples/mechanics/kinematics/rectilinear_motion_path";
	
	@GetMapping(value = KINEMATICS_RECTILINEAR_MOTION_PATH_URL)
	public String kinematicsRectilinearMotionPath() {
		
		return KINEMATICS_RECTILINEAR_MOTION_PATH_PAGE;
	}
}
