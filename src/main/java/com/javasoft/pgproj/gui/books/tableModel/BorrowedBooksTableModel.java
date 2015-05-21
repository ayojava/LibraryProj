/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.books.tableModel;

import com.javasoft.pgproj.Processor.BaseProcessor;
import com.javasoft.pgproj.Processor.LoanBookProcessor;
import com.javasoft.pgproj.entity.BorrowedBook;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ayojava
 */
public class BorrowedBooksTableModel extends AbstractTableModel{
    private int startPosition;
    private int counter = 0;
    private BaseProcessor processor;
    private EntityManager manager;
    private String headerList[] = new String[]{"S/N","Book Name", "Shelve Name", "Borrowed By ", "Borrowed Date", "Return Date"};
    private List<BorrowedBook> borrowedBookList;
    
    public BorrowedBooksTableModel() {
        processor = new LoanBookProcessor();
        startPosition = 0;
        manager = processor.getLoanBookDAO().getEntityManager();
        borrowedBookList = getBorrowedBooks(startPosition, startPosition + 100);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if ((rowIndex >= startPosition) && (rowIndex < (startPosition + 100))) {
            
        } else {
           borrowedBookList = getBorrowedBooks(rowIndex, rowIndex + 100); 
           startPosition = rowIndex;
        }
        
        BorrowedBook borrowedBook = borrowedBookList.get(rowIndex - startPosition);
        Object columnValue = null;
        
        switch(columnIndex){
             case 0:
                columnValue =rowIndex +1;
                break;
            case 1:
                columnValue =borrowedBook.getBook().getBookName();
                break;
            case 2:
                columnValue =borrowedBook.getBook().getBookShelve().getShelveName();
                break;
            case 3:
                columnValue =borrowedBook.getUser().returnFullName();
                break;
            case 4:
                columnValue =borrowedBook.formatDate(borrowedBook.getBorrowedDate());
                break;
            case 5:
                columnValue =borrowedBook.formatDate(borrowedBook.getDueDate());
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
    
    @Override
    public int getRowCount() {
        return ((Long) manager.createNamedQuery("BorrowedBook_findBorrowedBookCount").getSingleResult()).intValue();
    }

    @Override
    public int getColumnCount() {
        return headerList.length;
    }
    
    private List<BorrowedBook> getBorrowedBooks(int from, int to) {
        //System.out.println("numer of requests to the database " + counter++);
        Query query = manager.createNamedQuery("BorrowedBook_findAllBorrowedBook").setMaxResults(to - from).setFirstResult(from);
        List<BorrowedBook> resultList = query.getResultList();
        return resultList;
    }
}
