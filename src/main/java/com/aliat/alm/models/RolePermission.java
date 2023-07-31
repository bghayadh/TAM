package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ROLE_PERMISSIONS")
public class RolePermission {
	
	@Id
	@Column(name = "PERM_ID", nullable = false)
	private String permID;
	
	@Column(name = "SCREEN")
	private String screen;

	@Column(name = "VIEW_TYPE")
	private String viewType;
	
	@Column(name = "ROLE")
	private String role;

	@Column(name = "ROLE_LEVEL")
	private char roleLevel;
	
	@Column(name = "READ_PERM")
	private char readPerm;
	
	@Column(name = "WRITE_PERM")
	private char writePerm;
	
	@Column(name = "ADD_PERM")
	private char addPerm;
	
	@Column(name = "DELETE_PERM")
	private char delPerm;
	
	@Column(name = "SAVE_PERM")
	private char savePerm;
	
	@Column(name = "STATUS_PERM")
	private char statusPerm;
	
	@Column(name = "ACTION_PERM")
	private char actionPerm;
	
	@Column(name = "DOWNLOAD_PERM")
	private char downloadPerm;

	@Column(name = "EXPORT_PERM")
	private char exportPerm;
	
	@Column(name = "SECOND_LEVEL_PERM")
	private char secondLevelPerm;
	
	@Column(name = "FIRST_LEVEL_PERM")
	private char firstLevelPerm;
	
	public RolePermission() {	
	}



	public RolePermission(String permID, String screen, String viewType, String role, char roleLevel, char readPerm,
			char writePerm, char addPerm, char delPerm, char savePerm, char statusPerm, char actionPerm,
			char downloadPerm,char exportPerm,char secondLevelPerm,char firstLevelPerm) {
		super();
		this.permID = permID;
		this.screen = screen;
		this.viewType = viewType;
		this.role = role;
		this.roleLevel = roleLevel;
		this.readPerm = readPerm;
		this.writePerm = writePerm;
		this.addPerm = addPerm;
		this.delPerm = delPerm;
		this.savePerm = savePerm;
		this.statusPerm = statusPerm;
		this.actionPerm = actionPerm;
		this.downloadPerm = downloadPerm;
		this.exportPerm = exportPerm;
		this.firstLevelPerm = firstLevelPerm;
		this.secondLevelPerm = secondLevelPerm;
	}



	public String getPermID() {
		return permID;
	}



	public void setPermID(String permID) {
		this.permID = permID;
	}



	public String getScreen() {
		return screen;
	}



	public void setScreen(String screen) {
		this.screen = screen;
	}



	public String getViewType() {
		return viewType;
	}



	public void setViewType(String viewType) {
		this.viewType = viewType;
	}



	public String getRole() {
		return role;
	}



	public void setRole(String role) {
		this.role = role;
	}



	public char getRoleLevel() {
		return roleLevel;
	}



	public void setRoleLevel(char roleLevel) {
		this.roleLevel = roleLevel;
	}



	public char getReadPerm() {
		return readPerm;
	}



	public void setReadPerm(char readPerm) {
		this.readPerm = readPerm;
	}



	public char getWritePerm() {
		return writePerm;
	}



	public void setWritePerm(char writePerm) {
		this.writePerm = writePerm;
	}



	public char getAddPerm() {
		return addPerm;
	}



	public void setAddPerm(char addPerm) {
		this.addPerm = addPerm;
	}



	public char getDelPerm() {
		return delPerm;
	}



	public void setDelPerm(char delPerm) {
		this.delPerm = delPerm;
	}



	public char getSavePerm() {
		return savePerm;
	}



	public void setSavePerm(char savePerm) {
		this.savePerm = savePerm;
	}



	public char getStatusPerm() {
		return statusPerm;
	}



	public void setStatusPerm(char statusPerm) {
		this.statusPerm = statusPerm;
	}



	public char getAcctionPerm() {
		return actionPerm;
	}



	public void setActionPerm(char actionPerm) {
		this.actionPerm = actionPerm;
	}



	public char getDownloadPerm() {
		return downloadPerm;
	}



	public void setDownloadPerm(char downloadPerm) {
		this.downloadPerm = downloadPerm;
	}



	public char getExportPerm() {
		return exportPerm;
	}



	public void setExportPerm(char exportPerm) {
		this.exportPerm = exportPerm;
	}



	public char getSecondLevelPerm() {
		return secondLevelPerm;
	}



	public void setSecondLevelPerm(char secondLevelPerm) {
		this.secondLevelPerm = secondLevelPerm;
	}



	public char getFirstLevelPerm() {
		return firstLevelPerm;
	}



	public void setFirstLevelPerm(char firstLevelPerm) {
		this.firstLevelPerm = firstLevelPerm;
	}

	
	

	
}
	