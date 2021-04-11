package de.mthoma.jasic.web.controller;

public abstract class JasicController {
	
	protected String redirect2MainPage() {
		
		return this.createRedirectString(MainController.MAIN_URL);
	}
	
	/**
	 * Creates for the given pageName the corresponding redirect string.
	 * @param pageName {@link String} name of the page.
	 * @return String
	 */
	protected String createRedirectString(String pageName) {
		
		return "redirect:" + pageName;
	}
}
