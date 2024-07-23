package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ATTACHMENT_UPLOAD")
public class AttachmentUpload {
	
	@Id
	@Column(name = "ID", nullable = false)
	private String id;
	
	@Column(name = "ATTACHMENT_NAME")
	private String attachmentName;

	@Column(name = "ATTACHMENT_PATH")
	private String attachmentPath;
	
	@Column(name = "ELEMENT_ID")
	private String elementID;

	public AttachmentUpload() {
		super();
	}

	public AttachmentUpload(String id, String attachmentName, String attachmentPath, String elementID) {
		super();
		this.id = id;
		this.attachmentName = attachmentName;
		this.attachmentPath = attachmentPath;
		this.elementID = elementID;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAttachmentName() {
		return attachmentName;
	}

	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	public String getAttachmentPath() {
		return attachmentPath;
	}

	public void setAttachmentPath(String attachmentPath) {
		this.attachmentPath = attachmentPath;
	}

	public String getElementID() {
		return elementID;
	}

	public void setElementID(String elementID) {
		this.elementID = elementID;
	}

	

}
