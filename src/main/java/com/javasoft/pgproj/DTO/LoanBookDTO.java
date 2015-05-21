/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DTO;

import com.javasoft.pgproj.entity.BorrowedBook;

/**
 *
 * @author ayojava
 */
public class LoanBookDTO extends BaseDTO {
    private BorrowedBook borrowedBook;
    public LoanBookDTO() {
        borrowedBook = new BorrowedBook();
    }
    public BorrowedBook getBorrowedBook() {
        return borrowedBook;
    }
    public void setBorrowedBook(BorrowedBook borrowedBook) {
        this.borrowedBook = borrowedBook;
    }   
}
