/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DAO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ayojava
 */
public interface DAO<T extends Serializable>
{
    void delete(T o);
    
    T getObject(long id);
        
    T update(T o);
    
    void save(T o);
    
    void update(String query);
    
    T find(Object id);
    
    List<T> findAll();
    
    List<T> findAll(int maxResults, int firstresult);
    
    int countAll();
    
}
