/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.interfaces;

/**
 *
 * @author ayojava
 */
public interface EntityResource extends Resource
{
    public static final int ACTIVE = 0;
	
    public static final int INACTIVE = 1;
        
    public static final int LOCKED = 2;
    
    public static final int NEW = 3;
    
    public static final int AVAILABLE = 4;
    
    public static final int BORROWED = 5;
    
    public static final int RETURNED = 6;
}
