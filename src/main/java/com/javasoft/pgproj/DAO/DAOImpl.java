/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DAO;

import com.javasoft.pgproj.entity.ActivityLog;
import com.javasoft.pgproj.entity.Member;
import java.io.Serializable;
import javax.persistence.EntityManager;

/**
 *
 * @author ayojava
 * @param <T>
 */
public class DAOImpl<T extends Serializable> extends AbstractDAO<T> 
{
    protected EntityManager em;

    public DAOImpl(Class claszz) {
        super(claszz);
    }

    public DAOImpl(Class claszz, EntityManager em, boolean extended) {
        super(claszz);
        this.em = em;
        this.extended = extended;
    }

    @Override
    public EntityManager getEntityManager() {
        if (em == null || !em.isOpen()) {
            EntityManager _em = super.createEntityManager();
            if (extended) {
                this.em = _em;
            }
            return _em;
        }
        return em;
    }

    public EntityManager getEntityManager(boolean extended) {
        this.extended = extended;
        return getEntityManager();
    }

    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
    
    public boolean isOpen() {
        return (em != null && em.isOpen());
    }
    
   
   
    
    public void saveActivityLog(Serializable ser,String entityID,int status,Member user,EntityManager em)
    {
        ActivityLog activityLog = ActivityLog.newInstance(ser,status);
        activityLog.setEntityID(entityID);
        activityLog.setUserIdentity(user);
        em.persist(activityLog);
    }
}
