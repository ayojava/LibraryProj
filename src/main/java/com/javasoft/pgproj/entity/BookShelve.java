/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author ayojava
 */
@Entity
@NamedQueries({
    @NamedQuery(name ="BookShelveDTO.findAllBookShelves",
        query="SELECT NEW com.javasoft.pgproj.DTO.BookShelveDTO (b.id , b.shelveName,b.shelveCode) from BookShelve b order by b.shelveName"),
    
    @NamedQuery(name ="BookShelve.findByBookISBN",query="Select b.bookShelve from Book b where b.bookISBN= :bookISBN "),
})
public class BookShelve implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(mappedBy = "bookShelve")
    private List<Book> shelveBooks;
    
    private String shelveCode;
    
    private String description;
    
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
    
    @JoinColumn(name="entityState", referencedColumnName="stateId",nullable=false)
    @OneToOne(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private EntityState entityState;
    
    private String shelveName;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
    public Date getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
        this.registeredDate = registeredDate;
    }

    public List<Book> getShelveBooks() {
        return shelveBooks;
    }

    public void setShelveBooks(List<Book> shelveBooks) {
        this.shelveBooks = shelveBooks;
    }

    public String getShelveCode() {
        return shelveCode;
    }

    public void setShelveCode(String shelveCode) {
        this.shelveCode = shelveCode;
    }

    public String getShelveName() {
        return shelveName;
    }

    public void setShelveName(String shelveName) {
        this.shelveName = shelveName;
    }

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof BookShelve)) {
            return false;
        }
        BookShelve other = (BookShelve) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.BookShelve[ id=" + id + " ]";
    }
    
}
