/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.menus;

import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.Font;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author ayojava
 */
public class MenuLibrary extends JMenuBar
{
    private JMenu fileMenu,bookMenu, reportsMenu , memberMenu, helpMenu;
    
    private JMenu bookReportsMenu,memberReportsMenu;
    
    private JMenuItem file_print,file_Export,file_Exit;
    
    private JMenuItem bk_addBook,bk_editBook,bk_viewBook,bk_borrowedBooks,bk_availableBooks,bk_listAllBooks,bk_loanBook,bk_returnBook;
    
    private JMenuItem mb_addMember,mb_editMember,mb_viewMember,mb_listMembers;
    
    private JMenuItem about;
    
    private Font f;
    
    public MenuLibrary()
    {       
        initMenus();
        initMenuItems();
        addItemsToMenus();
    }

    private void initMenus() 
    {
        fileMenu = new JMenu(ResourceUtil.getString("menu_File")); 
        add(fileMenu);
        
        bookMenu = new JMenu(ResourceUtil.getString("menu_Book"));
        add(bookMenu);
        
        memberMenu = new JMenu(ResourceUtil.getString("menu_Member"));
        add(memberMenu);
        
        reportsMenu = new JMenu(ResourceUtil.getString("menu_Reports"));
        add(reportsMenu);
                
        helpMenu = new JMenu(ResourceUtil.getString("menu_Help"));
        add(helpMenu);
        
        bookReportsMenu = new JMenu(ResourceUtil.getString("menu_BookReports"));
        add(bookReportsMenu);
        
        memberReportsMenu = new JMenu(ResourceUtil.getString("menu_MemberReports"));
        add(memberReportsMenu);
        
        fileMenu.setMnemonic('f');
        bookMenu.setMnemonic('b');
        reportsMenu.setMnemonic('r');
        memberMenu.setMnemonic('m');
        helpMenu.setMnemonic('h');
    }

    private void initMenuItems() 
    {
        file_print = new JMenuItem(ResourceUtil.getString("menuItem_file_Print"), null);
        file_Export = new JMenuItem(ResourceUtil.getString("menuItem_file_Export"), null);
        file_Exit = new JMenuItem(ResourceUtil.getString("menuItem_file_Exit"), null);
        
        bk_addBook = new JMenuItem(ResourceUtil.getString("menuItem_book_addBook"), null);
        bk_editBook = new JMenuItem(ResourceUtil.getString("menuItem_book_editBook"), null);
        bk_viewBook = new JMenuItem(ResourceUtil.getString("menuItem_book_viewBook"), null);
        
        bk_listAllBooks = new JMenuItem(ResourceUtil.getString("menuItem_book_listAllBooks"), null);
        bk_borrowedBooks = new JMenuItem(ResourceUtil.getString("menuItem_book_listBorrowedBooks"), null);
        bk_availableBooks = new JMenuItem(ResourceUtil.getString("menuItem_book_listAvailableBooks"), null);
        
        mb_addMember = new JMenuItem(ResourceUtil.getString("menuItem_member_addMember"), null);
        mb_editMember = new JMenuItem(ResourceUtil.getString("menuItem_member_editMember"), null);
        mb_viewMember = new JMenuItem(ResourceUtil.getString("menuItem_member_viewMember"), null);
        mb_listMembers = new JMenuItem(ResourceUtil.getString("menuItem_member_listMembers"), null); 
        
        bk_loanBook = new JMenuItem(ResourceUtil.getString("menuItem_book_loanBook"), null);
        bk_returnBook = new JMenuItem(ResourceUtil.getString("menuItem_book_returnBook"), null);
        
        about = new JMenuItem(ResourceUtil.getString("menuItem_file_About"), null);
    }

    private void addItemsToMenus()
    {
        //fileMenu.add(file_print);
        fileMenu.add(file_Export);
        fileMenu.add(file_Exit);
        
        bookMenu.add(bk_addBook);
        bookMenu.add(bk_editBook);
        bookMenu.add(bk_viewBook);
        bookMenu.addSeparator();
        bookMenu.add(bk_loanBook);
        bookMenu.add(bk_returnBook);
        
        bookReportsMenu.add(bk_availableBooks);
        bookReportsMenu.add(bk_borrowedBooks);
        bookReportsMenu.add(bk_listAllBooks);
        
        memberReportsMenu.add(mb_listMembers);
        
        memberMenu.add(mb_addMember);
        memberMenu.add(mb_editMember);
        memberMenu.add(mb_viewMember);
        
        reportsMenu.add(bookReportsMenu);
        reportsMenu.addSeparator();
        reportsMenu.add(memberReportsMenu);
        
        helpMenu.add(about);
    }
    
    public JMenuItem getFile_Exit() {
        return file_Exit;
    }

    public JMenuItem getFile_Export() {
        return file_Export;
    }

    public JMenuItem getFile_print() {
        return file_print;
    }

    public JMenuItem getBk_addBook() {
        return bk_addBook;
    }

    public JMenuItem getBk_editBook() {
        return bk_editBook;
    }

   
    public JMenuItem getMb_addMember() {
        return mb_addMember;
    }

    public JMenuItem getMb_editMember() {
        return mb_editMember;
    }

    public JMenuItem getMb_listMembers() {
        return mb_listMembers;
    }

    public JMenuItem getBk_availableBooks() {
        return bk_availableBooks;
    }

    public JMenuItem getBk_borrowedBooks() {
        return bk_borrowedBooks;
    }

    public JMenuItem getBk_listAllBooks() {
        return bk_listAllBooks;
    }

    public JMenuItem getBk_viewBook() {
        return bk_viewBook;
    }

    public JMenuItem getMb_viewMember() {
        return mb_viewMember;
    }

    public JMenuItem getAbout() {
        return about;
    }

    public JMenuItem getBk_loanBook() {
        return bk_loanBook;
    }

    public JMenuItem getBk_returnBook() {
        return bk_returnBook;
    }

    
    
}
