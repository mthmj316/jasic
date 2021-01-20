package de.mthoma.jasic.data.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.eclipse.jdt.internal.compiler.codegen.CachedIndexEntry;

import com.fasterxml.jackson.annotation.OptBoolean;

import de.mthoma.jasic.data.entities.Chapter;
import de.mthoma.jasic.data.entities.IndexEntry;
import de.mthoma.jasic.data.entities.JasicDatabase;


public enum DatabaseService {

	DATABASE;

	private static final String DATABASE_PATH = System.getProperty("user.dir") + "/DATA/DATABASE.xml";

	private static final String LINE_SEPARATOR = System.lineSeparator();

	private static final String LINK_CONTENT_REPLACE_REGEX_TEMPLATE = "\\b%s\\b";
	
	private static final String LINK_CONTENT_REPLACEMENT_TEMPLATE = "<a href=\"javascript:showKeywordExplanation('%s');\">%s</a>";
	
	private static JasicDatabase database;

	static {
		try {
			unmarshall();
		} catch (JAXBException | IOException e) {
			e.printStackTrace();
		}
	}

	public List<Chapter> getSubChapter(final long parentId){

		List<Chapter> result = database.getChapters().stream().filter(
				chapter -> chapter.getParentId() == parentId).collect(Collectors.toList());

		return this.copyChapterList(result);
	}

	public DBActionState writeIndexEntry(final IndexEntry indexEntry) throws JAXBException {

		IndexEntry newIndexEntry = indexEntry.copy();
		newIndexEntry.setId(System.currentTimeMillis());

		indexEntry.setId(newIndexEntry.getId());

		database.getIndexEntries().add(newIndexEntry);

		marshall();

		return DBActionState.SUCCESS;
	}

	public DBActionState writeChapter(final Chapter chapter) throws JAXBException {

		Chapter newChapter = chapter.copy();		
		newChapter.setId(System.currentTimeMillis());

		chapter.setId(newChapter.getId());

		database.getChapters().add(newChapter);

		marshall();

		return DBActionState.SUCCESS;
	}

	public Chapter updateChapterContent(long id, String content) throws JAXBException {

		Chapter chapter = this.getOrgChapter(id);

		if(chapter != null) {

			chapter.setContent(content);
			marshall();

			return chapter.copy();
		}

		return chapter;
	}

	public Chapter getChapter(final long id) {

		Chapter result = getOrgChapter(id); 

		if(result != null) {

			result = result.copy();
			this.linkChapterContent(result);
		}
		

		return result;
	}

	private void linkChapterContent(final Chapter chapter) {
		
		if(chapter.getContent() == null || chapter.getContent().isEmpty()) {
			return;
		}
		
		String linkedContent = chapter.getContent();
		String capitalizedKeyword;
		String replacement = null;
		
		for(IndexEntry indexEntry : database.getIndexEntries()) {
			
			if(linkedContent.indexOf(indexEntry.getKeyword()) > -1) {
				
				replacement = String.format(LINK_CONTENT_REPLACEMENT_TEMPLATE, String.valueOf(indexEntry.getId()), indexEntry.getKeyword());
				linkedContent = linkedContent.replaceAll(String.format(LINK_CONTENT_REPLACE_REGEX_TEMPLATE, indexEntry.getKeyword()), replacement);
				
				if(!Character.isUpperCase(indexEntry.getKeyword().charAt(0))) {
					
					capitalizedKeyword = this.capitalize(indexEntry.getKeyword());
					
					if(linkedContent.indexOf(capitalizedKeyword) > -1) {
						
						replacement = String.format(LINK_CONTENT_REPLACEMENT_TEMPLATE, String.valueOf(indexEntry.getId()), capitalizedKeyword);
						linkedContent = linkedContent.replaceAll(String.format(LINK_CONTENT_REPLACE_REGEX_TEMPLATE, capitalizedKeyword), replacement);
					}
				}
			}
			
			
		}
		chapter.setLinkedContent(linkedContent);
	}
	
	private String capitalize(String word) {
		
		if(word == null || word.isEmpty()) {
			return word;
		}
		
		if(word.length() == 1) {
			return word.toUpperCase();
		}
		
		return new StringBuilder(word.substring(0, 1).toUpperCase()).append(word.substring(1)).toString();
	}

	private Chapter getOrgChapter(final long id) {

		Optional<Chapter> optional = database.getChapters().stream().filter(chapter -> chapter.getId() == id).findFirst();

		Chapter result = null; 

		if(optional.isPresent()) {

			result = optional.get();

			return result;
		}

		return null;
	}

	public IndexEntry getIndexEntry(final long id) {

		IndexEntry result = getOrgIndexEntry(id); 

		if(result != null) {

			result = result.copy();
		}

		return result;
	}

	private IndexEntry getOrgIndexEntry(final long id) {

		Optional<IndexEntry> optional = database.getIndexEntries().stream().filter(entry -> entry.getId() == id).findFirst();

		IndexEntry result = null; 

		if(optional.isPresent()) {

			result = optional.get();

			return result;
		}

		return null;
	}

	public List<IndexEntry> getAllIndexEntries(){

		Set<IndexEntry> sortedCopies = new TreeSet<IndexEntry>(this.copyIndexEntries(database.getIndexEntries()));

		return new ArrayList<>(sortedCopies);
	}

	private List<IndexEntry> copyIndexEntries(List<IndexEntry> originals){

		List<IndexEntry> copies = new ArrayList<IndexEntry>();
		originals.forEach(entry -> copies.add(entry.copy()));
		return copies;
	}

	public List<Chapter> getAllChapters(){

		return this.copyChapterList(database.getChapters());
	}

	private List<Chapter> copyChapterList(List<Chapter> originals){

		List<Chapter> copies = new ArrayList<Chapter>();
		originals.forEach(chapter -> copies.add(chapter.copy()));
		return copies;
	}

	public List<Chapter> getMainChapters(){

		return this.getSubChapter(0l);
	}

	private static void unmarshall() throws JAXBException, IOException {

		JAXBContext context = JAXBContext.newInstance(JasicDatabase.class);
		Unmarshaller um = context.createUnmarshaller();

		File dbFile = new File(DATABASE_PATH);
		if(!dbFile.exists()) {

			dbFile.getParentFile().mkdirs();
			dbFile.createNewFile();

			database = new JasicDatabase();
		} else {
			database = (JasicDatabase) um.unmarshal(new FileReader(DATABASE_PATH));
		}
	}

	private static void marshall() throws JAXBException {

		JAXBContext context = JAXBContext.newInstance(JasicDatabase.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		// Write to File
		m.marshal(database, new File(DATABASE_PATH));
	}
}
