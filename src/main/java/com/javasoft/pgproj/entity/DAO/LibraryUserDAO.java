/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.entity.Member;
import javax.persistence.EntityManager;

/**
 *
 * @author ayojava
 */
public class LibraryUserDAO<T extends Member> extends DAOImpl<T>  
{
    public LibraryUserDAO() {
        super(Member.class);
    }

    public LibraryUserDAO(EntityManager em, boolean extended) {
        super(Member.class, em, extended);
    }

    protected LibraryUserDAO(Class clazz) {
        super(clazz);
    }

    protected LibraryUserDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }
    
}
