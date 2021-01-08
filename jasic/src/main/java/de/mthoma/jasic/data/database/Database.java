package de.mthoma.jasic.data.database;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.FileUtils;

import de.mthoma.jasic.data.entities.Chapter;


public enum Database {
	
	TABLE_OF_CONTENT_TBL {
		
		public DBActionState write(Chapter chapter) {
			return null;
		}
	
		public List<Chapter> getAll(){
			return null;
		}
	};
	
	private static final String DATABASE_PATH = System.getProperty("user.dir") + "/DATA/DATABASE.xml";
	
	private String getDBContent() throws IOException {
		File file = new File("data.txt");
	    return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
	}
}
