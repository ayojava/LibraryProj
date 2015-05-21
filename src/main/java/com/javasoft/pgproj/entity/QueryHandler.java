/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

/**
 *
 * @author ayojava
 */

public class QueryHandler 
{
       
    public final static String BookDTO_findBooks="SELECT NEW com.javasoft.pgproj.DTO.BookDTO (o.bookName , o.bookAuthor,o.publisherName,o.bookShelve.shelveName,"
        + " o.bookISBN,o.pages,o.bookCode,COUNT(o.bookISBN)) from Book o  ";
    
    public final static String Book_findBooks="Select o from Book o ";
    
    public final static String LoanedBook_findBook="Select o from BorrowedBook o ";
    
    public final static String Member_findMember="SELECT o FROM Member o";
    
   // public final static String BookShelve_findBookShelve="Select b.bookShelve from Book b ";
    
    //QueryHandler.BookShelve_findBookShelve

   
    
}
