/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DTO;

import com.javasoft.pgproj.entity.Book;

/**
 *
 * @author ayojava
 */
//EntityDTO handles validation logic
public class BookDTO extends BaseDTO {
    private Book bookObj;
    private Long shelveId;
    public BookDTO() {
        bookObj = new Book();// validate the book Object before allowing save
    }
    public BookDTO(String bookName, String bookAuthor, String publisherName, String shelveName, String isbn, 
            int pages, String bookCode, Long copies) {
        bookObj = new Book();
        bookObj.setBookName(bookName);
        bookObj.setBookAuthor(bookAuthor);
        bookObj.setPublisherName(publisherName);
        bookObj.setBookISBN(isbn);
        bookObj.setPages(pages);
        bookObj.setBookCode(bookCode);
        bookObj.setCopies(Integer.parseInt(String.valueOf(copies)));
        setName(shelveName);
    }
    public Book getBookObj() {
        return bookObj;
    }
    public void setBookObj(Book bookObj) {
        this.bookObj = bookObj;
    }
    public Long getShelveId() {
        return shelveId;
    }
    public void setShelveId(Long shelveId) {
        this.shelveId = shelveId;
    }
}
