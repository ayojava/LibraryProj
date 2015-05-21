/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.interfaces;

/**
 *
 * @author ayojava
 */
public interface Validator 
{
    public boolean validatePhoneNo(String number);
    
    public boolean validateEmailAddress(String emailAddress);
}
