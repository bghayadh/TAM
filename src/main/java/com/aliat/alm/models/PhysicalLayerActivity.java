package com.aliat.alm.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PHYSICAL_LAYER_ACTIVITIES")
public class PhysicalLayerActivity {
    
    @Id
    @Column(name = "PHY_ACT_ID", nullable = false)
    private String phyActID;
    
    @Column(name = "SCREEN_NAME")
    private String screenName;
    
    @Column(name = "ELEMENT_ID")
    private String elementID;
    
    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "USER_IP")
    private String userIP;
    
    @Column(name = "ACTIVITY_DATE")
    private Timestamp activityDate;
    
    @Column(name = "ACTIVITY_DESCRIPTION")
    private String activityDescription;
    
    public PhysicalLayerActivity() {
        super();
        
    }

    public PhysicalLayerActivity(String phyActID, String screenName, String elementID, String username, String userIP,
            Timestamp activityDate, String activityDescription) {
        super();
        this.phyActID = phyActID;
        this.screenName = screenName;
        this.elementID = elementID;
        this.username = username;
        this.userIP = userIP;
        this.activityDate = activityDate;
        this.activityDescription = activityDescription;
    }

    public String getPhyActID() {
        return phyActID;
    }

    public void setPhyActID(String phyActID) {
        this.phyActID = phyActID;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getElementID() {
        return elementID;
    }

    public void setElementID(String elementID) {
        this.elementID = elementID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserIP() {
        return userIP;
    }

    public void setUserIP(String userIP) {
        this.userIP = userIP;
    }

    public Timestamp getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Timestamp activityDate) {
        this.activityDate = activityDate;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }
}
