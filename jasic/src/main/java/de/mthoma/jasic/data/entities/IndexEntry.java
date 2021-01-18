package de.mthoma.jasic.data.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="IndexEntry")
@XmlAccessorType(XmlAccessType.FIELD)
public class IndexEntry implements Comparable<IndexEntry>{

	@XmlAttribute(name="Id")
	private Long id;
	
	@XmlElement(name="Keyword")
	private String keyword;
	
	@XmlElement(name="Explanation")
	private String explanation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("IndexEntry [id=");
		builder.append(id);
		builder.append(", keyword=");
		builder.append(keyword);
		builder.append(", explanation=");
		builder.append(explanation);
		builder.append("]");
		return builder.toString();
	}
	
	public IndexEntry copy() {
		
		IndexEntry copy = new IndexEntry();
		copy.setId(this.id);
		copy.setExplanation(this.explanation);
		copy.setKeyword(this.keyword);
		
		return copy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndexEntry other = (IndexEntry) obj;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		return true;
	}

	@Override
	public int compareTo(IndexEntry entry) {
		
		if(entry == null) {
			return 1;
		}
		
		if(this.keyword != null && entry.keyword != null) {
			return this.keyword.compareTo(entry.keyword);
		} else if (this.keyword == null && entry.keyword == null) {
			return 0;
		} else if (this.keyword == null){
			return -1;
		} else if (entry.keyword == null) {
			return 1;
		}
		
		return 0;
	}
}
