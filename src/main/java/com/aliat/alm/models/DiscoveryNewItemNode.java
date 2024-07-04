package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DISCOVERY_NEW_ITEM_NODE")
public class DiscoveryNewItemNode {
    @Id
    @Column(name = "DNI_NODE", nullable = false)
    private String dniNode;

    @Column(name = "FROM_NODE_ID")
    private String fromNodeId;

    @Column(name = "TO_NODE_ID")
    private String toNodeId;

    @Column(name = "FROM_NODE_NAME")
    private String fromNodeName;

    @Column(name = "TO_NODE_NAME")
    private String toNodeName;

    @Column(name = "FROM_NODE_TYPE")
    private String fromNodeType;

    @Column(name = "TO_NODE_TYPE")
    private String toNodeType;

    @Column(name = "DNI_ID")
    private String dniId;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFIED_DATE")
    private Timestamp lastModifiedDate;

    public DiscoveryNewItemNode() {
    }

    public DiscoveryNewItemNode(String dniNode, String fromNodeId, String toNodeId, String fromNodeName, String toNodeName,
            String fromNodeType, String toNodeType, String dniId, Timestamp creationDate, Timestamp lastModifiedDate) {
        super();
        this.dniNode = dniNode;
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.fromNodeName = fromNodeName;
        this.toNodeName = toNodeName;
        this.fromNodeType = fromNodeType;
        this.toNodeType = toNodeType;
        this.dniId = dniId;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

   
    public String getDniNode() {
        return dniNode;
    }

    public void setDniNode(String dniNode) {
        this.dniNode = dniNode;
    }

    public String getFromNodeId() {
        return fromNodeId;
    }

    public void setFromNodeId(String fromNodeId) {
        this.fromNodeId = fromNodeId;
    }

    public String getToNodeId() {
        return toNodeId;
    }

    public void setToNodeId(String toNodeId) {
        this.toNodeId = toNodeId;
    }

    public String getFromNodeName() {
        return fromNodeName;
    }

    public void setFromNodeName(String fromNodeName) {
        this.fromNodeName = fromNodeName;
    }

    public String getToNodeName() {
        return toNodeName;
    }

    public void setToNodeName(String toNodeName) {
        this.toNodeName = toNodeName;
    }

    public String getFromNodeType() {
        return fromNodeType;
    }

    public void setFromNodeType(String fromNodeType) {
        this.fromNodeType = fromNodeType;
    }

    public String getToNodeType() {
        return toNodeType;
    }

    public void setToNodeType(String toNodeType) {
        this.toNodeType = toNodeType;
    }

    public String getDniId() {
        return dniId;
    }

    public void setDniId(String dniId) {
        this.dniId = dniId;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
