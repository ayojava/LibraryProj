/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.DTO.BookShelveDTO;
import com.javasoft.pgproj.entity.BookShelve;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ayojava
 */
public class BookShelveDAO<T extends BookShelve> extends DAOImpl<T> 
{public BookShelveDAO() {
        super(BookShelve.class);
    }

    public BookShelveDAO(EntityManager em, boolean extended) {
        super(BookShelve.class, em, extended);
    }

    protected BookShelveDAO(Class clazz) {
        super(clazz);
    }

    protected BookShelveDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }
    
    
    public List<BookShelveDTO> findAllBookShelves()
    {
        Query query = getEntityManager().createNamedQuery("BookShelveDTO.findAllBookShelves");
        return query.getResultList();
    }

}
