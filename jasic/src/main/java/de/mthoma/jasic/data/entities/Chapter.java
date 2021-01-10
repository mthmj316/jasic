package de.mthoma.jasic.data.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Chapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chapter {
	
	@XmlAttribute(name="Id")
	private Long id;

	@XmlAttribute(name="ParentId")
	private Long parentId;

	@XmlElement(name="ChapterName")
	private String chapterName;
	
	@XmlElement(name = "Content")
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chapter [id=");
		builder.append(id);
		builder.append(", chapterName=");
		builder.append(chapterName);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", content=");
		builder.append(content);
		builder.append("]");
		return builder.toString();
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChapterName() {
		return chapterName;
	}

	public void setChapterName(String operatorName) {
		this.chapterName = operatorName;
	}

	public Chapter copy() {
		
		Chapter copy = new Chapter();
		copy.setChapterName(this.chapterName);
		copy.setContent(this.content);
		copy.setId(this.id);
		copy.setParentId(this.parentId);
		
		return copy;
	}
}