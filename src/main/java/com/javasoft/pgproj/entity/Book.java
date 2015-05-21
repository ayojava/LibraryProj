/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import com.javasoft.pgproj.interfaces.EntityResource;
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
    @NamedQuery(name ="Book.findBookBy_BookName_BookISBN",query="SELECT COUNT(b) from Book b where b.bookName=:bookName AND b.bookISBN=:bookISBN"),
    @NamedQuery(name ="Book.findBook_BookISBN",query="SELECT b from Book b where b.bookISBN=:bookISBN"),
    @NamedQuery(name ="Book.findAllBooks",query="SELECT b from Book b"),
    @NamedQuery(name ="Book.findAllBooksCount",query="SELECT Count(b) from Book b"),
    @NamedQuery(name ="Book.findAvailableBooks",query="SELECT b from Book b where b.entityState.status="+EntityResource.AVAILABLE+""),
    @NamedQuery(name ="Book.findAvailableBooksCount",query="SELECT Count(b) from Book b where b.entityState.status="+EntityResource.AVAILABLE+"")
})
public class Book implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String bookName;
	
    private String bookCode;

    private Integer pages;

    @Transient
    private Integer copies;

    private String bookAuthor;
    
    private String publisherName;
    
    private String bookISBN;
    
    @Temporal(TemporalType.DATE)
    private Date registeredDate;
    
    @ManyToOne(fetch= FetchType.EAGER)
    private BookShelve bookShelve;

    @JoinColumn(name="entityState", referencedColumnName="stateId",nullable=false)
    @OneToOne(fetch= FetchType.EAGER,cascade= CascadeType.ALL)
    private EntityState entityState;

    public EntityState getEntityState() {
        return entityState;
    }

    public void setEntityState(EntityState entityState) {
        this.entityState = entityState;
    }
        
    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookCode() {
        return bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookISBN() {
        return bookISBN;
    }

    public void setBookISBN(String bookISBN) {
        this.bookISBN = bookISBN;
    }

    public BookShelve getBookShelve() {
        return bookShelve;
    }

    public void setBookShelve(BookShelve bookShelve) {
        this.bookShelve = bookShelve;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
    }

    public Date getRegisteredDate() {
            return registeredDate;
    }

    public void setRegisteredDate(Date registeredDate) {
            this.registeredDate = registeredDate;
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
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.Book[ id=" + id + " ]";
    }
    
    
    @Override
    public Book clone()
    {
        Book book = new Book();
        book.setPages(getPages());
        book.setBookAuthor(getBookAuthor());
        book.setRegisteredDate(new Date());
        book.setBookISBN(getBookISBN());
        book.setPublisherName(getPublisherName());
        book.setBookName(getBookName());
        return book;
    }
    
    public Book cloneBook(Book book)
    {
        setPages(book.getPages());
        setBookAuthor(book.getBookAuthor());
        setBookName(book.getBookName());
        setPublisherName(book.getPublisherName());
        return this;
    }
    
    public String formatDate()
    {
        return DateFormatUtils.format(registeredDate,"dd-MM-yyyy");
    }
}
