package de.mthoma.jasic.data.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement(name="Chapter")
@XmlAccessorType(XmlAccessType.FIELD)
public class Chapter {
	
	@XmlTransient
	public static final Chapter NULL_CHAPTER = new Chapter();
	
	@XmlAttribute(name="Id")
	private Long id;

	@XmlAttribute(name="ParentId")
	private Long parentId;

	@XmlElement(name="ChapterName")
	private String chapterName;
	
	@XmlElement(name = "Content")
	private String content;
	
	@XmlTransient
	private String linkedContent;

	public String getContent() {
		return content;
	}

	public String getLinkedContent() {
		return linkedContent;
	}

	public void setLinkedContent(String linkedContent) {
		this.linkedContent = linkedContent;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chapter [id=");
		builder.append(id);
		builder.append(", parentId=");
		builder.append(parentId);
		builder.append(", chapterName=");
		builder.append(chapterName);
		builder.append(", content=");
		builder.append(content);
		builder.append(", linkedContent=");
		builder.append(linkedContent);
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
		copy.setLinkedContent(this.linkedContent);
		
		return copy;
	}
}