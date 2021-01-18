package de.mthoma.jasic.web.controller;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.mthoma.jasic.data.database.DatabaseService;
import de.mthoma.jasic.data.entities.IndexEntry;

@Controller
public class IndexController {

	private static final String INDEX_URL = "/index";
	
	private static final String INDEX_PAGE = "index";
	
	private static final String CREATE_INDEX_ENTRY_URL = "/create_index_entry";
	
	@PostMapping(value = CREATE_INDEX_ENTRY_URL)
	public String createEntry(@ModelAttribute  IndexEntry indexEntry, Model model) {
		
		try {
			DatabaseService.DATABASE.writeIndexEntry(indexEntry);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("indexEntry", new IndexEntry());
		
		return INDEX_PAGE;
	}
	
	@GetMapping(value = INDEX_URL)
	public String index(Model model) {
		
		model.addAttribute("indexEntry", new IndexEntry());
		
		return INDEX_PAGE;
	}
}
