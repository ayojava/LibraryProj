/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.interfaces;

/**
 *
 * @author ayojava
 */
public interface ActivityResource extends Resource
{
    public static final int REGISTER_BOOK = 0;
	
    public static final int REGISTER_MEMBER = 1;

    public static final int EDIT_BOOK = 2;

    public static final int EDIT_MEMBER = 3;

    public static final int BOOK_SEARCH = 4;

    public static final int MEMBER_SEARCH = 5;
    
    public static final int REGISTER_SHELVE = 6;
    
    public static final int BORROW_BOOK = 7;
    
    public static final int RETURN_BOOK = 8;
}
