package de.mthoma.jasic.data.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="JasicDatabase")
@XmlAccessorType(XmlAccessType.FIELD)
public class JasicDatabase {
	
	@XmlElementWrapper(name = "Index")		 
	@XmlElement(name = "IndexEntry")
	private List<IndexEntry> indexEntries = new ArrayList<>();
	
	@XmlElementWrapper(name = "Chapters")		 
	@XmlElement(name = "Chapter")
	private List<Chapter> chapters = new ArrayList<>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JasicDatabase [indexEntries=");
		builder.append(indexEntries);
		builder.append(", chapters=");
		builder.append(chapters);
		builder.append("]");
		return builder.toString();
	}
	
	public List<IndexEntry> getIndexEntries() {
		return indexEntries;
	}

	public void setIndexEntries(List<IndexEntry> indexEntries) {
		this.indexEntries = indexEntries;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
}
