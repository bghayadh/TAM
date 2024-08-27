package com.aliat.alm.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "ROLE_PERMISSION_EXCEPTION")
public class RolePermissionException {

    @Id
    @Column(name = "ID", nullable = false)
    private String id;

    @Column(name = "SCREEN_NAME")
    private String screenName;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "FIELD_NAME")
    private String fieldName;

    @Column(name = "FIELD_VALUE")
    private String fieldValue;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFICATION_DATE")
    private Timestamp lastModificationDate;

    public RolePermissionException() {   
    }

    public RolePermissionException(String id, String screenName, String action, String role, String fieldName, 
                                   String fieldValue, Timestamp creationDate, Timestamp lastModificationDate) {
        this.id = id;
        this.screenName = screenName;
        this.action = action;
        this.role = role;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
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
