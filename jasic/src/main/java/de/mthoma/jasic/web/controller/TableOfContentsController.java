package de.mthoma.jasic.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.mthoma.jasic.data.entities.Chapter;

@Controller
public class TableOfContentsController {
	
	@PostMapping("/perform_create_entry")
	public String performCreateEntry(@ModelAttribute  Chapter selectedChapter, Model model) {
		
		System.out.println("Chapter parent: " + selectedChapter);
		
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		
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
		
		Chapter selectedChapter = new Chapter();
		model.addAttribute("selectedChapter", selectedChapter);
		
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
