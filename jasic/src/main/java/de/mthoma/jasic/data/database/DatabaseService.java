package de.mthoma.jasic.data.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import de.mthoma.jasic.data.entities.Chapter;
import de.mthoma.jasic.data.entities.JasicDatabase;


public enum DatabaseService {
	
	TABLE_OF_CONTENT_TBL;
	
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
	
	public DBActionState write(Chapter chapter) throws JAXBException {
		
		chapter.setId(System.currentTimeMillis());
		
		database.getChapters().add(chapter);
		
		marshall();
		
		return DBActionState.SUCCESS;
	}

	public List<Chapter> getAll(){
		
		List<Chapter> chapters = new ArrayList<Chapter>();
		
		database.getChapters().forEach(chapter -> chapters.add(chapter.copy()));		
		
		return chapters;
	}
	
	public List<Chapter> getChapters(){
		
		List<Chapter> mainChapters = new ArrayList<Chapter>();
		
		return mainChapters;
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
