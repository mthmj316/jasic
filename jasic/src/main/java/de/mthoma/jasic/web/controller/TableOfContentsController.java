package de.mthoma.jasic.web.controller;

import javax.servlet.http.HttpServletRequest;
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
	
	private static final int SELECTED_CHAPTER_URI_PREFIX_LENGTH = 16;
	private static final String CHAPTERS = "chapters";
	private static final String TABLE_OF_CONTENTS_TABLE_OF_CONTENTS = "table_of_contents/table_of_contents";
	private static final String HEADER_TEMPLATE = "Physik Grundlagen: %s";
	
	private long currentlySelectedChapter = 0l;
	
	@PostMapping(value = "/table_of_contents/create_entry", params="save")
	public void saveChange(@ModelAttribute  Chapter selectedChapter, HttpServletRequest request) {
		System.out.println("selectedChapter: " + selectedChapter);
	}

	@GetMapping("/getSubChapters4**")
	public String chapterSelected(Model model, HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		long parentId = Long.parseLong(requestUri.substring(SELECTED_CHAPTER_URI_PREFIX_LENGTH));
		
		this.currentlySelectedChapter = parentId;
		
		this.setPageParameter(model, false, parentId);
		
		return TABLE_OF_CONTENTS_TABLE_OF_CONTENTS;
	}
	
	@PostMapping("/perform_create_entry")
	public String performCreateEntry(@ModelAttribute  Chapter newChapter, Model model) {
		
		try {
			newChapter.setParentId(this.currentlySelectedChapter);
			DatabaseService.TABLE_OF_CONTENT_TBL.write(newChapter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setPageParameter(model, newChapter.getParentId() == 0, this.currentlySelectedChapter);
		
		return TABLE_OF_CONTENTS_TABLE_OF_CONTENTS;
	}
	
	private void setPageParameter(Model model, boolean isRoot, long parentId) {
		
		model.addAttribute(CHAPTERS, DatabaseService.TABLE_OF_CONTENT_TBL.getSubChapter(this.currentlySelectedChapter));
		model.addAttribute("isRoot", isRoot);
		
		Chapter parent = DatabaseService.TABLE_OF_CONTENT_TBL.get(parentId);
		model.addAttribute("selectedChapter", parent);
		model.addAttribute("header", String.format(HEADER_TEMPLATE, parent.getChapterName()));
	}

	@PostMapping(value = "/table_of_contents/create_entry", params="create")
	public String createEntry(Model model) {
		
		model.addAttribute(CHAPTERS, DatabaseService.TABLE_OF_CONTENT_TBL.getAll());
		
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
		
		this.currentlySelectedChapter = 0;
		
		model.addAttribute(CHAPTERS, DatabaseService.TABLE_OF_CONTENT_TBL.getMainChapters());
		model.addAttribute("header", String.format(HEADER_TEMPLATE, "Übersicht"));
		model.addAttribute("isRoot", true);
		
		return TABLE_OF_CONTENTS_TABLE_OF_CONTENTS;
	}
}
