package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.sql.Timestamp;

@Entity
@Table(name = "MODULE_SCREEN")
public class ModuleScreen {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "SCREEN_TABLE")
    private String screenTable;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFICATION_DATE")
    private Timestamp lastModificationDate;

    // Default constructor
    public ModuleScreen() {
        super();
    }

    // Parameterized constructor
    public ModuleScreen(String id, String screenName, String screenTable, Timestamp creationDate,
    		Timestamp lastModificationDate) {
        this.id = id;
        this.screenName = screenName;
        this.screenTable = screenTable;
        this.creationDate = creationDate;
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
}
