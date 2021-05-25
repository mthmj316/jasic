package de.mthoma.jasic.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	private static final String INDEX_REFERENCE_SYSTEM_URL = "/index/reference_system";
	private static final String INDEX_REFERENCE_SYSTEM_PAGE = "index/reference_system";
	
	@GetMapping(value = INDEX_REFERENCE_SYSTEM_URL)
	public String indexReferenceSystem(Model model) {
		
		return INDEX_REFERENCE_SYSTEM_PAGE;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

////	private static final String INDEX_URL = "/index";
//	
//	private static final String CREATE_INDEX_ENTRY_URL = "/create_index_entry";
//	
//	@PostMapping(value = CREATE_INDEX_ENTRY_URL, params = "back")
//	public String back2KnowlegeBase(Model model) {
//		
//		//return new ModelAndView("redirect:/backFromIndex"); 
//		return "redirect:/backFromIndex"; 
//	}
//	
//	@PostMapping(value = CREATE_INDEX_ENTRY_URL, params = "save")
//	public String createEntry(@ModelAttribute  IndexEntry indexEntry, Model model) {
//		
//		try {
//			DatabaseService.DATABASE.writeIndexEntry(indexEntry);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
//		
//		model.addAttribute("indexEntry", new IndexEntry());
//		model.addAttribute("index", DatabaseService.DATABASE.getAllIndexEntries());
//		
//		return KnowledgeBaseController.INDEX_PAGE;
//	}
//	
//	@GetMapping(value = "/getKeywordExplanation4**")
//	public String loadIndexEntry(Model model, HttpServletRequest request) {
//		
//		String requestUri = request.getRequestURI();
//		
//		long id = Long.parseLong(requestUri.substring(23));
//		
//		IndexEntry entry2Load = DatabaseService.DATABASE.getIndexEntry(id);
//		
//		if(entry2Load == null) entry2Load = new IndexEntry();
//		
//		model.addAttribute("indexEntry", entry2Load);
//		model.addAttribute("index", DatabaseService.DATABASE.getAllIndexEntries());
//		
//		return KnowledgeBaseController.INDEX_PAGE;
//	
//	}
}
