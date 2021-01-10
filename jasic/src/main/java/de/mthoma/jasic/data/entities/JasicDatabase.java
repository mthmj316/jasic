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
	
	@XmlElementWrapper(name = "Chapters")
		 
	@XmlElement(name = "Chapter")
	private List<Chapter> chapters = new ArrayList<Chapter>();

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JasicDatabase [chapters=");
		builder.append(chapters);
		builder.append("]");
		return builder.toString();
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
}
