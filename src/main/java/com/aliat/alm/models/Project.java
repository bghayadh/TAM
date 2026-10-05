package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROJECT", schema = "DEMO")
public class Project {

	@Id
	@Column(name = "PROJECT_ID", length = 100)
	private String projectId;

	@Column(name = "PROJECT_NAME", length = 200)
	private String projectName;

	@Column(name = "PROJECT_LAYER", length = 100)
	private String projectLayer;

	public Project() {
		// required no-arg constructor for Hibernate
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectLayer() {
		return projectLayer;
	}

	public void setProjectLayer(String projectLayer) {
		this.projectLayer = projectLayer;
	}
}