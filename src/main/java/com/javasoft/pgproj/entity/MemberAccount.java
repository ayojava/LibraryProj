/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ayojava
 */
@Entity
public class MemberAccount implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date firstAccessedDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastAccessedDate;
    
    private String sysAdmin;
    
    private String sysAccount; // Y or N
    
    @JoinColumn(name="entityState", referencedColumnName="stateId",nullable=false)
    @OneToOne(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private EntityState entityState;
    

    public String getSysAccount() {
        return sysAccount;
    }

    public void setSysAccount(String sysAccount) {
        this.sysAccount = sysAccount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }

    public Date getFirstAccessedDate() {
        return firstAccessedDate;
    }

    public void setFirstAccessedDate(Date firstAccessedDate) {
        this.firstAccessedDate = firstAccessedDate;
    }

    public Date getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(Date lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public String getSysAdmin() {
        return sysAdmin;
    }

    public void setSysAdmin(String sysAdmin) {
        this.sysAdmin = sysAdmin;
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
        if (!(object instanceof MemberAccount)) {
            return false;
        }
        MemberAccount other = (MemberAccount) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.UserAccount[ id=" + id + " ]";
    }
    
}
