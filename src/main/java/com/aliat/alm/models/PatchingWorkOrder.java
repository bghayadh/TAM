package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "PATCHING_WORK_ORDER")
public class PatchingWorkOrder {

    @Id
    @Column(name = "PATCHING_ID", nullable = false)
    private String patchingId;

    @Column(name = "ASSIGNED_TO")
    private String assignedTo;

    @Column(name = "PLANED_EXECUTION_DATE")
    private Timestamp plannedExecutionDate;

    @Column(name = "ACTUAL_EXECUTION_DATE")
    private Timestamp actualExecutionDate;

    @Column(name = "PATCHING_STATUS")
    private String patchingStatus;

    @Column(name = "PATCHING_NOTE")
    private String patchingNote;

    @Column(name = "CREATED_DATE")
    private Timestamp createdDate;

    @Column(name = "LAST_MODIFIED_DATE")
    private Timestamp lastModifiedDate;

    public PatchingWorkOrder() {
    }

    public PatchingWorkOrder(
            String patchingId,
            String assignedTo,
            Timestamp plannedExecutionDate,
            Timestamp actualExecutionDate,
            String patchingStatus,
            String patchingNote,
            Timestamp createdDate,
            Timestamp lastModifiedDate) {

        this.patchingId = patchingId;
        this.assignedTo = assignedTo;
        this.plannedExecutionDate = plannedExecutionDate;
        this.actualExecutionDate = actualExecutionDate;
        this.patchingStatus = patchingStatus;
        this.patchingNote = patchingNote;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPatchingId() {
        return patchingId;
    }

    public void setPatchingId(String patchingId) {
        this.patchingId = patchingId;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public Timestamp getPlannedExecutionDate() {
        return plannedExecutionDate;
    }

    public void setPlannedExecutionDate(Timestamp plannedExecutionDate) {
        this.plannedExecutionDate = plannedExecutionDate;
    }

    public Timestamp getActualExecutionDate() {
        return actualExecutionDate;
    }

    public void setActualExecutionDate(Timestamp actualExecutionDate) {
        this.actualExecutionDate = actualExecutionDate;
    }

    public String getPatchingStatus() {
        return patchingStatus;
    }

    public void setPatchingStatus(String patchingStatus) {
        this.patchingStatus = patchingStatus;
    }

    public String getPatchingNote() {
        return patchingNote;
    }

    public void setPatchingNote(String patchingNote) {
        this.patchingNote = patchingNote;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
