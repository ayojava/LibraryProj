/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.interfaces;

/**
 *
 * @author ayojava
 */
public interface Resource 
{    
    void setResource(String resource);

    String getResource();
    
    void setStatus(int status);
    
    public int getStatus();
}
