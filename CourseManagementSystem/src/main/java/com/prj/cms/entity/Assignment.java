package com.prj.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@Table(name = "assignment", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Assignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "course_id", nullable = false)
	private int courseId;
	@Column(name = "assignment_name", nullable = false)
	private String assignmentName;

	@Column(name = "assignment_desc", nullable = false)
	private String assignmentDescription;

	@Column(name = "due_date")
	private String dueDate;

	public Assignment() {

	}

	public String getAssignmentDescription() {
		return assignmentDescription;
	}

	public void setAssignmentDescription(String assignmentDescription) {
		this.assignmentDescription = assignmentDescription;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssignmentName() {
		return assignmentName;
	}

	public void setAssignmentName(String assignmentName) {
		this.assignmentName = assignmentName;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public Assignment(int id, int courseId, String assignmentName, String assignmentDescription, String dueDate) {
		super();
		this.id = id;
		this.courseId = courseId;
		this.assignmentName = assignmentName;
		this.assignmentDescription = assignmentDescription;
		this.dueDate = dueDate;
	}

	@Override
	public String toString() {
		return "Assignment [id=" + id + ", courseId=" + courseId + ", assignmentName=" + assignmentName
				+ ", assignmentDescription=" + assignmentDescription + ", dueDate=" + dueDate + "]";
	}

}
