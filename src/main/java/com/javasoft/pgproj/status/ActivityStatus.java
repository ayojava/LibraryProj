/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.status;

import com.javasoft.pgproj.interfaces.ActivityResource;

/**
 *
 * @author ayojava
 */
public abstract class ActivityStatus implements ActivityResource
{
    public boolean isRegisterBook()
    {
        return getStatus() == ActivityResource.REGISTER_BOOK;
    }
    
    public void setRegisterBook()
    {
        setStatus(ActivityResource.REGISTER_BOOK);
    }
    
    public boolean isBorrowBook(){
        return getStatus()==ActivityResource.BORROW_BOOK;
    }
    
    public void setBorrowBook(){
        setStatus(ActivityResource.BORROW_BOOK);
    }
    
    public boolean isReturnBook(){
        return getStatus()==ActivityResource.RETURN_BOOK;
    }
    
    public void setReturnBook(){
        setStatus(ActivityResource.RETURN_BOOK);
    }
    
    public void setRegisterBookShelve()
    {
        setStatus(ActivityResource.REGISTER_SHELVE);
    }
    
    public boolean isRegisterBookShelve()
    {
        return getStatus() == ActivityResource.REGISTER_SHELVE;
    }
    
    public boolean isRegisterMember()
    {
        return getStatus() == ActivityResource.REGISTER_MEMBER;
    }
    
    public void setRegisterMember()
    {
        setStatus(ActivityResource.REGISTER_MEMBER);
    }
    
    public boolean isEditBook()
    {
        return getStatus() == ActivityResource.EDIT_BOOK;
    }
    
    public void setEditBook()
    {
        setStatus(ActivityResource.EDIT_BOOK);
    }
    
    public boolean isEditMember()
    {
        return getStatus() == ActivityResource.EDIT_MEMBER;
    }
    
    public void setEditMember()
    {
        setStatus(ActivityResource.EDIT_MEMBER);
    }
    
    public boolean isBookSearch()
    {
        return getStatus() == ActivityResource.BOOK_SEARCH;
    }
    
    public void setBookSearch()
    {
        setStatus(ActivityResource.BOOK_SEARCH);
    }
    
    public boolean isMemberSearch()
    {
        return getStatus() == ActivityResource.MEMBER_SEARCH;
    }
    
    public void setMemberSearch()
    {
        setStatus(ActivityResource.MEMBER_SEARCH);
    }
    
  
    public String getStatusName()
    {
        return getStatusName(getStatus());
    }
    
    public static String getStatusName(int status)
    {
        switch(status)
        {
            case BOOK_SEARCH:
                return "Book Search";
                
            case EDIT_BOOK:
                return "Edit Book";
                
            case EDIT_MEMBER:
                return "Edit Member";
                
            case MEMBER_SEARCH:
                return "Member Search";
                
            case REGISTER_BOOK:
                return "Register Book";
                
            case REGISTER_MEMBER:
                return "Register Member";
                
            case REGISTER_SHELVE:
                return "Register Shelve";
                
            case BORROW_BOOK:
                return "Borrow Book";
                
            case RETURN_BOOK:
                return "Return book";
                        
        }
        return null;
    }
}
