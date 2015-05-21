/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DAO;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author ayojava
 */
public abstract class AbstractDAO<T extends Serializable> implements DAO<T> {

    private Class<T> entityClass;
    //private static EntityManagerFactory emf;
    protected boolean extended;

    public AbstractDAO(Class<T> domainClass) {
        this.entityClass = domainClass;
        //emf = Persistence.createEntityManagerFactory("pms1.2PU");
    }

    public Class<T> getEntityClass() {
        return entityClass;
    }

    public abstract EntityManager getEntityManager();

    EntityManager createEntityManager() {
        return DBConfig.getEntityManagerFactory().createEntityManager();
    }

    private T transactDML(T object, int transaction) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            switch (transaction) {
                case 0: {
                    em.remove(object);
                    break;
                }
                case 1: {
                    object = em.merge(object);
                    break;
                }
                case 2: {
                    em.persist(object);
                    break;
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
        return object;
    }

    

    @Override
    public void delete(T object) {
        transactDML(object, 0);
    }

    @Override
    public T update(T object) {
        return transactDML(object, 1);
    }

    @Override
    public void save(T object) {
        transactDML(object, 2);
    }

    @Override
    public void update(String query) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Query q = em.createQuery(query);
            q.executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

    @Override
    public T getObject(long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

    public List<T> namedQueryList(String queryName,Map<String, Object> map) 
    {
        Query query = getEntityManager().createNamedQuery(queryName);
        Set<String> parameters = null;
        if (map != null && !map.isEmpty()) 
        {
            parameters = map.keySet();
            Iterator<String> iter = parameters.iterator();
            while (iter.hasNext()) 
            {
              String param = iter.next();  
              query.setParameter(param, map.get(param));
            }
        }
        return query.getResultList();
    }
    
    public T getSingleResult(String queryName,Map<String, Object> map) 
    {
        Query query = getEntityManager().createNamedQuery(queryName);
        Set<String> parameters = null;
        if (map != null && !map.isEmpty()) 
        {
            parameters = map.keySet();
            Iterator<String> iter = parameters.iterator();
            while (iter.hasNext()) 
            {
              String param = iter.next();  
              query.setParameter(param, map.get(param));
            }
        }
        return (T) query.getSingleResult();
    }
    
    public Long getEntityCount(String queryName,Map<String, Object> map){
    
        Query query = getEntityManager().createNamedQuery(queryName);
        Set<String> parameters = null;
        if (map != null && !map.isEmpty()) 
        {
            parameters = map.keySet();
            Iterator<String> iter = parameters.iterator();
            while (iter.hasNext()) 
            {
              String param = iter.next();  
              query.setParameter(param, map.get(param));
            }
        }
         return ((Long) query.getSingleResult());
    }
    
            
            //((Long) q.getSingleResult()).intValue();
    public String buildQuery(Map<String, Object> map)
    {
       String qryString="";
       Set<String> parameters = null;
       int i = 0;
        if (map != null && !map.isEmpty()) 
        {
            parameters = map.keySet();
            Iterator<String> iter = parameters.iterator();
            while (iter.hasNext()) 
            {
                String param = iter.next();
                if (i++ == 0) 
                {
                    qryString = qryString + " where ";
                } 
                else 
                {
                    qryString = qryString + " and ";
                }
                qryString = qryString + "o." + param + "='" +map.get(param)+"'" ;
            }          
        }
        
       return qryString;
    }
    
      
    Query getQuery(Map<String, Object> map, EntityManager em) 
    {
        Set<String> parameters = null;
        int i = 0;
        Query q = null;
        //System.out.println("" + map);
        String query = "select o from " + entityClass.getName() + " as o";
        if (map != null && !map.isEmpty()) {
            parameters = map.keySet();
            Iterator<String> iter = parameters.iterator();
            while (iter.hasNext()) {
                String param = iter.next();
                if (i++ == 0) {
                    query = query + " where ";
                } else {
                    query = query + " and ";
                }
                query = query + "o." + param + "='" +map.get(param)+"'" ;
            }
        }
       
        System.out.println("generated Query :::::: " + query);
        q = em.createQuery(query);
        return q;
    }

    public T find(Object id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(entityClass, id);
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

    public List<T> findAll() {
        EntityManager em = getEntityManager();
        try {
            Query query = getQuery(null, em);
            return query != null ? query.getResultList() : null;
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

    public List<T> findAll(int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query query = getQuery(null, em);
            if (query != null) {
                query.setMaxResults(maxResults);
                query.setFirstResult(firstResult);
                return query.getResultList();
            } else {
                return null;
            }
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

    @Override
    public int countAll() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from " + entityClass.getName() + " as o");
            return ((Long) q.getSingleResult()).intValue();
        } catch (Exception e) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            return 0;
        } finally {
            if (!extended) {
                em.close();
            }
        }
    }

    public T singleQuery(String sql) {
        EntityManager em = getEntityManager();
        try {
            Query qry = em.createQuery(sql);
            return qry != null?(T) qry.getSingleResult() : null;
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended && em != null) {
                em.close();
            }
        }
    }

   public List<T> listQuery(String sql) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery(sql);
            return query != null ? query.getResultList() : null;
        } catch (Exception e) {
            if (!extended && em != null) {
                em.getTransaction().rollback();
            }
            return null;
        } finally {
            if (!extended  && em != null ) {
                em.close();
            }
        }
    }
    
}

