/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.entity.ActivityLog;
import javax.persistence.EntityManager;

/**
 *
 * @author ayojava
 */
public class ActivityLogDAO<T extends ActivityLog> extends DAOImpl<T>  {
    
    public ActivityLogDAO() {
        super(ActivityLog.class);
    }

    public ActivityLogDAO(EntityManager em, boolean extended) {
        super(ActivityLog.class, em, extended);
    }

    protected ActivityLogDAO(Class clazz) {
        super(clazz);
    }

    protected ActivityLogDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }
}
