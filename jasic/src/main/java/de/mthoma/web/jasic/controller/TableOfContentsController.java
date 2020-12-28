package de.mthoma.web.jasic.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import de.mthoma.web.jasic.entities.Chapter;

@Controller
public class TableOfContentsController {

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
