package de.mthoma.web.jasic.entities;

//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//@Entity
//@Table(name = "operator")
public class Chapter {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
//	@JsonIgnore
	private Long id;

//  @NotBlank(message="operator Name cannot be empty")
//	@Column(name = "operator_name", nullable = false)
	private String chapterName;
	
	private Long parentId;

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Chapter [id=");
		builder.append(id);
		builder.append(", chapterName=");
		builder.append(chapterName);
		builder.append(", parentId=");
		builder.append(parentId);
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

}