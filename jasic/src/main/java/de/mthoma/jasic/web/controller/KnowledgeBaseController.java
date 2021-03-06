package de.mthoma.jasic.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import de.mthoma.jasic.data.database.DatabaseService;
import de.mthoma.jasic.data.entities.Chapter;
import de.mthoma.jasic.data.entities.IndexEntry;

@Controller
public class KnowledgeBaseController extends JasicController{
	
	public static final String THEORY_UPDATE_ENTRY = "/theory/update_entry";
	private static final String PERFORM_CREATE_CHAPTER = "/perform_create_chapter";
	public static final String KNOWLEDGE_BASE_URL = "/theory/knowledge_base";
	private static final int SELECTED_CHAPTER_URI_PREFIX_LENGTH = 16;
	private static final String CHAPTERS = "chapters";
	private static final String THEORY_KNOWLEDGE_PAGE = "theory/knowledge_base";
	private static final String HEADER_TEMPLATE = "Physik Grundlagen: %s";
	
	public static final String INDEX_PAGE = "index";
	
	private long currentlySelectedChapter = 0l;

	
	@GetMapping(value = "/backFromIndex")
	public String backFromIndex(Model model) {
		
		this.setPageParameter(model, this.currentlySelectedChapter == 0, this.currentlySelectedChapter);
		
		return THEORY_KNOWLEDGE_PAGE;
	}
	
	@PostMapping(value = PERFORM_CREATE_CHAPTER, params = "cancel")
	public String performCancelCreateChapter(Model model) {
		
		this.setPageParameter(model, this.currentlySelectedChapter == 0, this.currentlySelectedChapter);
		
		return THEORY_KNOWLEDGE_PAGE;
	}
	
	@PostMapping(value = PERFORM_CREATE_CHAPTER, params = "create")
	public String performCreateChapter(@ModelAttribute  Chapter newChapter, Model model) {
		
		try {
			newChapter.setParentId(this.currentlySelectedChapter);
			DatabaseService.DATABASE.writeChapter(newChapter);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.setPageParameter(model, newChapter.getParentId() == 0, this.currentlySelectedChapter);
		
		return THEORY_KNOWLEDGE_PAGE;
	}
	
	@PostMapping(value = THEORY_UPDATE_ENTRY, params = "createIndexEntry")
	public String index(Model model) {
		
		model.addAttribute("indexEntry", new IndexEntry());
		model.addAttribute("index", DatabaseService.DATABASE.getAllIndexEntries());
		
		return INDEX_PAGE;
	}

	@PostMapping(value = THEORY_UPDATE_ENTRY, params = "createChapter")
	public String createEntry(Model model) {
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getAllChapters());
		
		Chapter newChapter = new Chapter();
		model.addAttribute("newChapter", newChapter);
		
		return "theory/create_entry";
	}
	
	@PostMapping(value = THEORY_UPDATE_ENTRY, params = "back")
	public String cancelUpdateUser(Model model) {
		
		if(this.currentlySelectedChapter == 0) {
			
			return super.redirect2MainPage();
		}
		
		Chapter chapter2Load = DatabaseService.DATABASE.getParentChapter(this.currentlySelectedChapter);
		
		if(chapter2Load == Chapter.NULL_CHAPTER) {
			return this.tableOfContents(model);
		} else {
			return this.applyPage(model, chapter2Load.getId());
		}
	}
	
	@ResponseBody
	@GetMapping(value = "/getKeywordExplanation/{id}")
	public String getKeywordExplanation(@PathVariable(value = "id") String id) {
		
		IndexEntry indexEntry = DatabaseService.DATABASE.getIndexEntry(Long.parseLong(id));
		
		return indexEntry.getExplanation();
	}
	
	@PostMapping(value = THEORY_UPDATE_ENTRY, params = "save")
	public String saveChange(@ModelAttribute  Chapter selectedChapter, Model model) {
		
		try {
			DatabaseService.DATABASE.updateChapterContent(this.currentlySelectedChapter, selectedChapter.getContent());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		this.setPageParameter(model, false, this.currentlySelectedChapter);
		return THEORY_KNOWLEDGE_PAGE;
	}

	@GetMapping("/getSubChapters4**")
	public String chapterSelected(Model model, HttpServletRequest request) {
		
		String requestUri = request.getRequestURI();
		
		long parentId = Long.parseLong(requestUri.substring(SELECTED_CHAPTER_URI_PREFIX_LENGTH));
		
		return this.applyPage(model, parentId);
	}
	
	private String applyPage(Model model, long pageId2Load) {
		
		this.currentlySelectedChapter = pageId2Load;
		
		this.setPageParameter(model, false, pageId2Load);
		
		return THEORY_KNOWLEDGE_PAGE;
	}
	
	
	private void setPageParameter(Model model, boolean isRoot, long chapterId) {
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getSubChapter(this.currentlySelectedChapter));
		model.addAttribute("isRoot", isRoot);
		
		Chapter chapter = DatabaseService.DATABASE.getChapter(chapterId);
		
		if(chapter != Chapter.NULL_CHAPTER) {
			model.addAttribute("selectedChapter", chapter);
		}
		
		String headerAppendix = chapter != Chapter.NULL_CHAPTER ? chapter.getChapterName() : "Übersicht";
		
		model.addAttribute("header", String.format(HEADER_TEMPLATE, headerAppendix));
	}
	
	@GetMapping(value = KNOWLEDGE_BASE_URL)
	public String tableOfContents(Model model) {
		
		this.currentlySelectedChapter = 0;
		
		model.addAttribute(CHAPTERS, DatabaseService.DATABASE.getMainChapters());
		model.addAttribute("header", String.format(HEADER_TEMPLATE, "Übersicht"));
		model.addAttribute("isRoot", true);
		
		return THEORY_KNOWLEDGE_PAGE;
	}
}
