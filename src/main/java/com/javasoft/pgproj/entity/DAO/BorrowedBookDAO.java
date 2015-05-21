/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.DTO.LoanBookDTO;
import com.javasoft.pgproj.entity.*;
import com.javasoft.pgproj.lms.LibraryService;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

/**
 *
 * @author ayojava
 */
public class BorrowedBookDAO<T extends BorrowedBook> extends DAOImpl<T> {

    public BorrowedBookDAO() {
        super(BorrowedBook.class);
    }

    public BorrowedBookDAO(EntityManager em, boolean extended) {
        super(BorrowedBook.class, em, extended);
    }

    protected BorrowedBookDAO(Class clazz) {
        super(clazz);
    }

    protected BorrowedBookDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }

    public BorrowedBook findBorrowedBook(Map<String,Object> qryParams){
        String qryString =QueryHandler.LoanedBook_findBook +  buildQuery(qryParams);
        //System.out.println(">>>>>>>>>>>>> " + qryString);
        Query qry = getEntityManager().createQuery(qryString);
        List<BorrowedBook> borrowedBooks = qry.getResultList();
        if(borrowedBooks != null && borrowedBooks.size() >0 ){
            return borrowedBooks.get(0);
        }
        return null;
    }
    
    public List<BorrowedBook> findUnreturnedBooks(){
    Query qry = getEntityManager().createNamedQuery("BorrowedBook_findOutstandings");
    return qry.getResultList();
    }
    
    public boolean returnLoanedBook(LoanBookDTO loanBookDTO){
        boolean outcome = true;
        EntityTransaction transaction = null;
        Member systemUser = LibraryService.getInstance().getSystemUser();
        BorrowedBook borrowedBook = loanBookDTO.getBorrowedBook();
        try {
            em = getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            Book bookObj =em.find(Book.class, borrowedBook.getBook().getId());
            EntityState es = EntityState.newInstance(systemUser, bookObj);
            es.setAvailable();
            bookObj.setEntityState(es);
            em.merge(bookObj);
            em.remove(borrowedBook);
            saveActivityLog(bookObj, String.valueOf(bookObj.getId()), ActivityLog.RETURN_BOOK,systemUser, em); 
            
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            outcome = false;
            System.out.println("Exception  ::: " + ex);
        } finally {
            close();
            //destroy();
        }
        return outcome;
    }
    
    public boolean persistBorrowedBook(LoanBookDTO loanBookDTO) {
        
        boolean outcome = true;
        EntityTransaction transaction = null;
        Member systemUser = LibraryService.getInstance().getSystemUser();
        BorrowedBook borrowedBook = loanBookDTO.getBorrowedBook();
        try {
            em = getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            Book bookObj =em.find(Book.class, borrowedBook.getBook().getId());
            EntityState es = EntityState.newInstance(systemUser, bookObj);
            es.setBorrowed();
            bookObj.setEntityState(es);
            em.merge(bookObj);
            em.persist(borrowedBook);
            saveActivityLog(bookObj, String.valueOf(bookObj.getId()), ActivityLog.BORROW_BOOK,systemUser, em); 
            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            outcome = false;
            System.out.println("Exception  ::: " + ex);
        } finally {
            close();
            //destroy();
        }
        return outcome;
    }
}
