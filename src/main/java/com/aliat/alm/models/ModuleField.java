package com.aliat.alm.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MODULE_FIELDS")
public class ModuleField {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "SCREEN_TABLE")
    private String screenTable;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "FIELD_INDEX")
    private String fieldIndex;

    @Column(name = "FIELD_VALUES")
    private String fieldValues;

    @Column(name = "FIELD_LEVEL")
    private String fieldLevel;

    @Column(name = "READ_ONLY")
    private String readOnly;

    @Column(name = "FIELD_TYPE")
    private String fieldType;
    
    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFICATION_DATE")
    private Timestamp lastModificationDate;


    // Default constructor
    public ModuleField() {
        super();
    }

    // Parameterized constructor
    public ModuleField(String id, String screenName, String screenTable, String fieldName, String fieldIndex,
    		String fieldValues, String fieldLevel, String readOnly, String fieldType,Timestamp creationDate, Timestamp lastModificationDate) {
        this.id = id;
        this.screenName = screenName;
        this.screenTable = screenTable;
        this.fieldName = fieldName;
        this.fieldIndex = fieldIndex;
        this.fieldValues = fieldValues;
        this.fieldLevel = fieldLevel;
        this.readOnly = readOnly;
        this.fieldType = fieldType;
        this.creationDate=creationDate;
        this.lastModificationDate=lastModificationDate;
    }

    public Timestamp getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getLastModificationDate() {
		return lastModificationDate;
	}

	public void setLastModificationDate(Timestamp lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	// Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getScreenTable() {
        return screenTable;
    }

    public void setScreenTable(String screenTable) {
        this.screenTable = screenTable;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldIndex() {
        return fieldIndex;
    }

    public void setFieldIndex(String fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

    public String getFieldLevel() {
        return fieldLevel;
    }

    public void setFieldLevel(String fieldLevel) {
        this.fieldLevel = fieldLevel;
    }

    public String getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String readOnly) {
        this.readOnly = readOnly;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
