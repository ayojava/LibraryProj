/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import com.javasoft.pgproj.status.EntityStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author ayojava
 */
@Entity
public class EntityState extends EntityStatus implements Serializable 
{
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long stateId;
    
    private String entityName;
    
    private Integer status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date createDate;
      
    @OneToOne(mappedBy = "entityState",cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private MemberAccount userAccount;
    
    @JoinColumn(name="createdBy",referencedColumnName="userId")
    @ManyToOne(fetch= FetchType.LAZY)
    private Member createdBy;
    
    @OneToOne(mappedBy = "entityState",cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private Book book;
    
    @OneToOne(mappedBy = "entityState",cascade= CascadeType.ALL, fetch= FetchType.LAZY)
    private BookShelve bookShelve;

    public BookShelve getBookShelve() {
        return bookShelve;
    }

    public void setBookShelve(BookShelve bookShelve) {
        this.bookShelve = bookShelve;
    }

    public Member getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Member createdBy) {
        this.createdBy = createdBy;
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }
    
    

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

       
    public MemberAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(MemberAccount userAccount) {
        this.userAccount = userAccount;
    }
        
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (stateId != null ? stateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EntityState)) {
            return false;
        }
        EntityState other = (EntityState) object;
        if ((this.stateId == null && other.stateId != null) || (this.stateId != null && !this.stateId.equals(other.stateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.EntityState[ id=" + stateId + " ]";
    }
    
    
//    public static EntityState newInstance()
//    {
//        EntityState entityState = new EntityState();
//        entityState.setCreateDate(new Date());
//        entityState.setCreatedBy(LibraryService.getInstance().getSystemUser());
//        return entityState;
//    }
//    
//    public static EntityState newInstance(Serializable ser)
//    {
//        EntityState entityState = new EntityState();
//        entityState.setCreateDate(new Date());
//        entityState.setCreatedBy(LibraryService.getInstance().getSystemUser());
//        entityState.setEntityName(ser.getClass().getSimpleName());
//        return entityState;
//    }
//    
    public static EntityState newInstance(Member userId,Serializable ser) 
    {
        EntityState entityState = new EntityState();
        entityState.setCreateDate(new Date());
        entityState.setCreatedBy(userId);
        entityState.setEntityName(ser.getClass().getSimpleName());
        return entityState;
    }

    @Override
    public void setResource(String resource) {
        
    }

    @Override
    public String getResource() {
       return "";
    }

    @Override
    public void setStatus(int status) 
    {
        this.status = status;
    }

    @Override
    public int getStatus() 
    {
        return status;
    }
    
}
