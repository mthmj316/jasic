package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

	public static final String KINEMATICS_RECTILINEAR_MOTION_PATH_URL = "/examples/mechanics/kinematics/rectilinear_motion_path";
	public static final String KINEMATICS_RECTILINEAR_MOTION_PATH_PAGE = "examples/mechanics/kinematics/rectilinear_motion_path";
	
	public static final String KINEMATICS_RECTILINEAR_MOTION_AVERAGE_SPEED_URL = "/examples/mechanics/kinematics/rectilinear_motion_average_speed";
	public static final String KINEMATICS_RECTILINEAR_MOTION_AVERAGE_SPEED_PAGE = "examples/mechanics/kinematics/rectilinear_motion_average_speed";
	
	@GetMapping(value = KINEMATICS_RECTILINEAR_MOTION_AVERAGE_SPEED_URL)
	public String kinematicsRectilinearMotionAverageSpeed() {
		
		return KINEMATICS_RECTILINEAR_MOTION_AVERAGE_SPEED_PAGE;
	}
	
	@GetMapping(value = KINEMATICS_RECTILINEAR_MOTION_PATH_URL)
	public String kinematicsRectilinearMotionPath() {
		
		return KINEMATICS_RECTILINEAR_MOTION_PATH_PAGE;
	}
}
