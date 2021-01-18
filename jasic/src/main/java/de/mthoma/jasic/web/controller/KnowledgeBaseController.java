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
public class KnowledgeBaseController {
	
	private static final String KNOWLEDGE_BASE_UPDATE_ENTRY = "/knowledge_base/update_entry";
	private static final String PERFORM_CREATE_CHAPTER = "/perform_create_chapter";
	private static final String KNOWLEDGE_BASE_ROOT = "/knowledge_base/knowledge_base";
	private static final String KNOWLEDGE_CREATE_ENTRY = "/knowledge_base/create_chapter";
	private static final int SELECTED_CHAPTER_URI_PREFIX_LENGTH = 16;
	private static final String CHAPTERS = "chapters";
	private static final String KNOWLEDGE_BASE_KNOWLEDGE_BASE = "knowledge_base/knowledge_base";
	private static final String HEADER_TEMPLATE = "Physik Grundlagen: %s";
	
	private long currentlySelectedChapter = 0l;
	
	@PostMapping(value = KNOWLEDGE_BASE_UPDATE_ENTRY)
	public String saveChange(@ModelAttribute  Chapter selectedChapter, Model model) {
		
		try {
			DatabaseService.DATABASE.updateChapterContent(this.currentlySelectedChapter, selectedChapter.getContent());
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setPageParameter(model, false, this.currentlySelectedChapter);
		return KNOWLEDGE_BASE_KNOWLEDGE_BASE;
	}

	@GetMapping("/getSubChapters4**")
	public String chapterSelected(Model model, HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		long parentId = Long.parseLong(requestUri.substring(SELECTED_CHAPTER_URI_PREFIX_LENGTH));
		
		this.currentlySelectedChapter = parentId;
		
		this.setPageParameter(model, false, parentId);
		
		return KNOWLEDGE_BASE_KNOWLEDGE_BASE;
	}
	
	@PostMapping(PERFORM_CREATE_CHAPTER)
	public String performCreateEntry(@ModelAttribute  Chapter newChapter, Model model) {
		
		try {
			newChapter.setParentId(this.currentlySelectedChapter);
			DatabaseService.DATABASE.writeChapter(newChapter);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setPageParameter(model, newChapter.getParentId() == 0, this.currentlySelectedChapter);
		
		return KNOWLEDGE_BASE_KNOWLEDGE_BASE;
	}
	
	private void setPageParameter(Model model, boolean isRoot, long parentId) {
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getSubChapter(this.currentlySelectedChapter));
		model.addAttribute("isRoot", isRoot);
		
		Chapter parent = DatabaseService.DATABASE.get(parentId);
		model.addAttribute("selectedChapter", parent);
		
		String headerAppendix = parent != null ? parent.getChapterName() : "Übersicht";
		
		model.addAttribute("header", String.format(HEADER_TEMPLATE, headerAppendix));
	}

	@PostMapping(value = KNOWLEDGE_CREATE_ENTRY)
	public String createEntry(Model model) {
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getAllChapters());
		
		Chapter newChapter = new Chapter();
		model.addAttribute("newChapter", newChapter);
		
		return "knowledge_base/create_entry";
	}
	
	@GetMapping(value = KNOWLEDGE_BASE_ROOT)
	public String tableOfContents(Model model) {
		
		this.currentlySelectedChapter = 0;
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getMainChapters());
		model.addAttribute("header", String.format(HEADER_TEMPLATE, "Übersicht"));
		model.addAttribute("isRoot", true);
		
		return KNOWLEDGE_BASE_KNOWLEDGE_BASE;
	}
}
