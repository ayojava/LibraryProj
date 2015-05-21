/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

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
    @NamedQuery(name ="BorrowedBook_findOutstandings",query="SELECT b from BorrowedBook b where b.dueDate < CURRENT_DATE"),
    @NamedQuery(name ="BorrowedBook_findAllBorrowedBook",query="SELECT b from BorrowedBook b "),
    @NamedQuery(name ="BorrowedBook_findBorrowedBookCount",query="SELECT Count(b) from BorrowedBook b ")
})
public class BorrowedBook implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne()
    private Book book;
    
    @ManyToOne
    private Member user;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date borrowedDate;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dueDate;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date returnedDate;
    
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(Date returnedDate) {
        this.returnedDate = returnedDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
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
        if (!(object instanceof BorrowedBook)) {
            return false;
        }
        BorrowedBook other = (BorrowedBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.BorrowedBook[ id=" + id + " ]";
    }
    
    public String formatDate(Date dateObj)
    {
        return DateFormatUtils.format(dateObj,"dd-MM-yyyy");
    }
}
