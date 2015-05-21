/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.books.tableModel;

import com.javasoft.pgproj.Processor.BaseProcessor;
import com.javasoft.pgproj.Processor.BookProcessor;
import com.javasoft.pgproj.entity.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ayojava
 */
public class AllBooksTableModel extends AbstractTableModel {

    private int startPosition;
    private int counter = 0;
    private BaseProcessor processor;
    private EntityManager manager;
    private String headerList[] = new String[]{"S/N","Name", "Author", "Publisher", "Code", "Shelve Name", "Registered Date"};
    private List<Book> bookList;

    public AllBooksTableModel() {
        processor = new BookProcessor();
        startPosition = 0;
        manager = processor.getBookDAO().getEntityManager();
        bookList = getBooks(startPosition, startPosition + 100);
    }

    @Override
    public int getRowCount() {
        return ((Long) manager.createNamedQuery("Book.findAllBooksCount").getSingleResult()).intValue();
    }

    @Override
    public int getColumnCount() {
        return headerList.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if ((rowIndex >= startPosition) && (rowIndex < (startPosition + 100))) {
            
        } else {
           bookList = getBooks(rowIndex, rowIndex + 100); 
           startPosition = rowIndex;
        }
        
        Book book = bookList.get(rowIndex - startPosition);
        Object columnValue = null;
        
        switch(columnIndex){
            case 0:
                columnValue =rowIndex +1;
                break;
            case 1:
                columnValue =book.getBookName();
                break;
            case 2:
                columnValue =book.getBookAuthor();
                break;
            case 3:
                columnValue =book.getPublisherName();
                break;
            case 4:
                columnValue =book.getBookCode();
                break;
            case 5:
                columnValue =book.getBookShelve().getShelveName();
                break;
            case 6:
                columnValue =book.formatDate();
                break;
            default:
                columnValue = null;           
        }
        return columnValue;
    }

    @Override
    public String getColumnName(int column){
        return headerList[column];
    }
    
    private List<Book> getBooks(int from, int to) {
        //System.out.println("numer of requests to the database " + counter++);
        Query query = manager.createNamedQuery("Book.findAllBooks").setMaxResults(to - from).setFirstResult(from);
        List<Book> resultList = query.getResultList();
        return resultList;
    }

    public List<Book> getBookList() {
        return bookList;
    }
    
    
}
