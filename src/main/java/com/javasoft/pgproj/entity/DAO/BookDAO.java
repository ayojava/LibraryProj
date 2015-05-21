/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.entity.*;
import com.javasoft.pgproj.lms.LibraryService;
import com.javasoft.pgproj.status.EntityStatus;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StrBuilder;

/**
 *
 * @author ayojava
 */
public class BookDAO<T extends Book> extends DAOImpl<T>  
{
    public BookDAO() 
    {
        super(Book.class);
    }

    public BookDAO(EntityManager em, boolean extended) {
        super(Book.class, em, extended);
    }

    protected BookDAO(Class clazz) {
        super(clazz);
    }

    protected BookDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }
    
   
    public boolean findByISBN_Name(String bookISBN, String bookName)
    {      
        Query qry = getEntityManager().createNamedQuery("Book.findBookBy_BookName_BookISBN");
        qry.setParameter("bookName", bookName);
        qry.setParameter("bookISBN", bookISBN);
        Long size = (Long) qry.getSingleResult();
        return (size > 0 )? true : false;
    }
        
    
    
     public boolean persistBook(BookDTO bookDTO) 
    {
        Book bookObj = bookDTO.getBookObj();
        //bookObj.setCopies();
        bookObj.setPages(Integer.parseInt(bookDTO.getPages()));
        boolean outcome= true;
        EntityTransaction transaction = null;
        try
        {
            em = getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            BookShelve shelve = em.find(BookShelve.class, bookDTO.getShelveId());
            String bookCode= RandomStringUtils.randomAlphabetic(3).toUpperCase();
            //bookDTO.setName(shelve.getShelveName());
            for (int i = 0; i < Integer.parseInt(bookDTO.getCopies()) ; i++)
            {
                StrBuilder strBuilder = new StrBuilder();
                int j = i +1;
                Book book = bookObj.clone();
                EntityState es = EntityState.newInstance(LibraryService.getInstance().getSystemUser(), book);
                es.setAvailable();
                book.setEntityState(es);
                book.setBookCode(bookCode + strBuilder.appendFixedWidthPadLeft(j, 3, '0'));
                book.setBookShelve(shelve); 
                em.persist(book);
                em.refresh(book);
                if(i == 0){
                 bookDTO.setBookObj(book);   
                }
                saveActivityLog(book, String.valueOf(book.getId()), ActivityLog.REGISTER_BOOK, LibraryService.getInstance().getSystemUser(), em);              
            }
            transaction.commit();
            
        }
        catch(Exception ex)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            outcome= false;
            System.out.println("Exception  ::: " + ex);
        }
        finally
        {
            close();
            //destroy();
        }
        return outcome;
    }
    
    
    public boolean updateBook(BookDTO bookDTO)
    {
        Book bookObj = bookDTO.getBookObj();
        
        boolean outcome= true;
        EntityTransaction transaction = null;
        
        try
        {
            em = getEntityManager();
          
            List<Book> bookList = em.createNamedQuery("Book.findBook_BookISBN").setParameter("bookISBN", bookObj.getBookISBN()).getResultList();
            String bookCode=null;
            int j;
            
            transaction = em.getTransaction();
            transaction.begin();
            
            if(bookList != null && !bookList.isEmpty())
            {
                j = bookList.size() + 1;
                for(Book book : bookList)
                {
                    bookCode = book.getBookCode();
                    em.merge(book.cloneBook(bookObj));
                    saveActivityLog(book, String.valueOf(book.getId()), ActivityLog.EDIT_BOOK,LibraryService.getInstance().getSystemUser(), em); 
                } 
                
                int bookdiff = bookObj.getCopies();
                bookCode = bookCode == null ? "":bookCode.substring(0, 3);
                if(bookdiff > 0)
                {
                    BookShelve shelve = (BookShelve) em.createNamedQuery("BookShelve.findByBookISBN").setParameter("bookISBN", bookObj.getBookISBN()).getSingleResult();
                    
                    for(int i = 0;i < bookdiff ; i++)
                    {
                        StrBuilder strBuilder = new StrBuilder();
                        Book book = bookObj.clone();
                        EntityState es = EntityState.newInstance(LibraryService.getInstance().getSystemUser(), book);
                        es.setAvailable();
                        book.setEntityState(es);
                        book.setBookCode(bookCode + strBuilder.appendFixedWidthPadLeft(j, 3, '0'));
                        book.setBookShelve(shelve); 
                        em.persist(book);
                        em.refresh(book);
                        saveActivityLog(book, String.valueOf(book.getId()), ActivityLog.REGISTER_BOOK,LibraryService.getInstance().getSystemUser(), em); 
                        j++;
                    }
                }
               
            }
            transaction.commit();
            
        }
        catch(Exception ex)
        {
            if(transaction != null)
            {
                transaction.rollback();
            }
            outcome= false;
            System.out.println("Exception  ::: " + ex);
        }
        finally
        {
            close();
            //destroy();
        }
        return outcome;
        
    }
    
    
    public BookDTO findBookDTO(Map<String,Object> qryParams)
    { 
        String qryString =QueryHandler.BookDTO_findBooks +  buildQuery(qryParams)+ " GROUP BY o.bookISBN";
        Query qry = getEntityManager().createQuery(qryString);
        return (BookDTO)qry.getSingleResult();
    }
    
    public Book findAvailableBook(Map<String,Object> qryParams)
    { 
        qryParams.put("entityState.status", EntityStatus.AVAILABLE);
        String qryString =QueryHandler.Book_findBooks +  buildQuery(qryParams);
        Query qry = getEntityManager().createQuery(qryString);
        List<Book> availableBks = qry.getResultList();
        if(availableBks != null && availableBks.size() >0 ){
            return availableBks.get(0);
        }
        return null;
    }
}
