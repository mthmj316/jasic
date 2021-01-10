package de.mthoma.jasic.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.mthoma.jasic.data.database.DatabaseService;
import de.mthoma.jasic.data.entities.Chapter;

@Controller
public class TableOfContentsController {
	
	@PostMapping("/perform_create_entry")
	public String performCreateEntry(@ModelAttribute  Chapter newChapter, Model model) {
		
		try {
			DatabaseService.TABLE_OF_CONTENT_TBL.write(newChapter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "table_of_contents/table_of_contents";
	}

	@GetMapping(value = "/table_of_contents/create_entry")
	public String createEntry(Model model) {
		
		model.addAttribute("chapters", DatabaseService.TABLE_OF_CONTENT_TBL.getAll());
		
		Chapter newChapter = new Chapter();
		model.addAttribute("newChapter", newChapter);
		
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
