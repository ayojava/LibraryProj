/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import com.javasoft.pgproj.status.ActivityStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author ayojava
 */
@Entity
@NamedQueries({
    @NamedQuery(name ="ActivityLog_findAllLogs",query="SELECT a from ActivityLog a "),
    @NamedQuery(name ="ActivityLog_findAllLogsCount",query="SELECT Count(a) from ActivityLog a ")
})
public class ActivityLog extends ActivityStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String entityID;
     
    private String entityName;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date eventDate;
    
    private int eventType;
    
    private String narrative;
    
    @ManyToOne
    private Member userIdentity;

    public Member getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(Member userIdentity) {
        this.userIdentity = userIdentity;
    }
    
    

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public int getEventType() {
        return eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

   
    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   public String formatDate(Date dateObj)
    {
        return DateFormatUtils.format(dateObj,"dd-MM-yyyy");
    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ActivityLog)) {
            return false;
        }
        ActivityLog other = (ActivityLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.ActivityLog[ id=" + id + " ]";
    }

    @Override
    public void setResource(String resource) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getResource() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void setStatus(int status) 
    {
        eventType=status;
        setNarrative(getStatusName(status));
    }

    @Override
    public int getStatus() 
    {
        return eventType;
    }
    
    public static ActivityLog newInstance(Serializable e,int status)
    {
        ActivityLog activityLog = new ActivityLog();
        activityLog.setEventDate(new Date());
        activityLog.setEntityName(e.getClass().getSimpleName());
        activityLog.setStatus(status);
        return activityLog;
    }
    
    public static void main(String [] args){
        System.out.println("" + ActivityLog.class.getSimpleName());
    }
}
