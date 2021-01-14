package de.mthoma.jasic.data.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.fasterxml.jackson.annotation.OptBoolean;

import de.mthoma.jasic.data.entities.Chapter;
import de.mthoma.jasic.data.entities.JasicDatabase;


public enum DatabaseService {
	
	KNOWLEDGE_BASE_TBL;
	
	private static final String DATABASE_PATH = System.getProperty("user.dir") + "/DATA/DATABASE.xml";
	
	private static JasicDatabase database;
	
	static {
		try {
			unmarshall();
		} catch (JAXBException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Chapter> getSubChapter(final long parentId){
		
		List<Chapter> result = database.getChapters().stream().filter(
				chapter -> chapter.getParentId() == parentId).collect(Collectors.toList());
		
		return this.copyList(result);
	}
	
	public DBActionState write(final Chapter chapter) throws JAXBException {
		
		Chapter newChapter = chapter.copy();		
		newChapter.setId(System.currentTimeMillis());
		
		chapter.setId(newChapter.getId());
		
		database.getChapters().add(newChapter);
		
		marshall();
		
		return DBActionState.SUCCESS;
	}
	
	public Chapter updateContent(long id, String content) throws JAXBException {
		
		Chapter chapter = this.getOrg(id);
		
		if(chapter != null) {
			
			chapter.setContent(content);
			marshall();
			
			return chapter.copy();
		}
		
		return chapter;
	}
	
	public Chapter get(final long id) {
		
		Chapter result = getOrg(id); 
		
		if(result != null) {
		
			result = result.copy();
		}
		
		return result;
	}
	
	private Chapter getOrg(final long id) {
		
		Optional<Chapter> optional = database.getChapters().stream().filter(chapter -> chapter.getId() == id).findFirst();
		
		Chapter result = null; 
		
		if(optional.isPresent()) {
		
			result = optional.get();
			
			return result;
		}
		
		return null;
	}

	public List<Chapter> getAll(){
		
		return this.copyList(database.getChapters());
	}
	
	private List<Chapter> copyList(List<Chapter> originals){
		
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
