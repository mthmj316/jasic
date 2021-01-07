package de.mthoma.web.jasic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.mthoma.web.jasic.entities.Chapter;

@Controller
public class TableOfContentsController {
	
//	@RequestMapping(value="perfrom_create_entry", method = RequestMethod.POST)
	@PostMapping("/perform_create_entry")
	public String performCreateEntry(@ModelAttribute  Chapter selectParent, Model model) {
		//@RequestParam(value="idNumber") String idNumber , @RequestParam(value="applicationId") String applicationId
		
		System.out.println("Chapter parent: " + selectParent);
		
		return "table_of_contents/table_of_contents";
	}

	@GetMapping(value = "/table_of_contents/create_entry")
	public String createEntry(Model model) {
		
		List<Chapter> chapters =  new ArrayList<>();
		
		Chapter chapter = new Chapter();
		chapter.setId(1l);
		chapter.setChapterName("number one");
		chapters.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(2l);
		chapter.setChapterName("number two");
		chapters.add(chapter);
		
		chapter = new Chapter();
		chapter.setId(3l);
		chapter.setChapterName("number three");
		chapters.add(chapter);
		
		model.addAttribute("chapters", chapters);
		
		
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
