package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NODE_PASSIVE")
public class NodePassive {

    @Id
    @Column(name = "PASSIVE_PK", nullable = false)
    private String passivePk;

	@Column(name = "UNIQUE_NODE_ID")
    private String nodeId;

    @Column(name = "NODE_NAME")
    private String nodeName;

    @Column(name = "SITE_TYPE")
    private String siteType;

    @Column(name = "SWAP")
    private String swap;

    @Column(name = "SWAP_DATE")
    private String swapDate;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "CIRCLE_ID")
    private String circleId;

    @Column(name = "DISCOVERY_DATE")
    private Timestamp discoveryDate;

    @Column(name = "LAST_SHOWN_DATE")
    private Timestamp lastShownDate;

    @Column(name = "CREATION_DATE")
    private Timestamp creationDate;

    @Column(name = "LAST_MODIFIED_DATE")
    private Timestamp lastModifiedDate;

    public NodePassive() {
       
    }

  
    public NodePassive(String passivePk, String nodeId, String nodeName, String siteType, String swap, String swapDate,
            String status, String circleId, Timestamp discoveryDate, Timestamp lastShownDate, Timestamp creationDate, Timestamp lastModifiedDate) {
        this.passivePk = passivePk;
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.siteType = siteType;
        this.swap = swap;
        this.swapDate = swapDate;
        this.status = status;
        this.circleId = circleId;
        this.discoveryDate = discoveryDate;
        this.lastShownDate = lastShownDate;
        this.creationDate = creationDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPassivePk() {
 		return passivePk;
 	}


 	public void setPassivePk(String passivePk) {
 		this.passivePk = passivePk;
 	}


 	public String getNodeId() {
 		return nodeId;
 	}


 	public void setNodeId(String nodeId) {
 		this.nodeId = nodeId;
 	}


 	public String getNodeName() {
 		return nodeName;
 	}


 	public void setNodeName(String nodeName) {
 		this.nodeName = nodeName;
 	}


 	public String getSiteType() {
 		return siteType;
 	}


 	public void setSiteType(String siteType) {
 		this.siteType = siteType;
 	}


 	public String getSwap() {
 		return swap;
 	}


 	public void setSwap(String swap) {
 		this.swap = swap;
 	}


 	public String getSwapDate() {
 		return swapDate;
 	}


 	public void setSwapDate(String swapDate) {
 		this.swapDate = swapDate;
 	}


 	public String getStatus() {
 		return status;
 	}


 	public void setStatus(String status) {
 		this.status = status;
 	}


 	public String getCircleId() {
 		return circleId;
 	}


 	public void setCircleId(String circleId) {
 		this.circleId = circleId;
 	}


 	public Timestamp getDiscoveryDate() {
 		return discoveryDate;
 	}


 	public void setDiscoveryDate(Timestamp discoveryDate) {
 		this.discoveryDate = discoveryDate;
 	}


 	public Timestamp getLastShownDate() {
 		return lastShownDate;
 	}


 	public void setLastShownDate(Timestamp lastShownDate) {
 		this.lastShownDate = lastShownDate;
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
