/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.Processor;

import com.javasoft.pgproj.DTO.LoanBookDTO;
import com.javasoft.pgproj.entity.BorrowedBook;
import java.util.Map;

/**
 *
 * @author ayojava
 */
public class LoanBookProcessor extends BaseProcessor{

    public boolean persistLoanedBook(LoanBookDTO loanBookDTO){
        return getLoanBookDAO().persistBorrowedBook(loanBookDTO);
    }
    
    public BorrowedBook findBorrowedBook(Map<String,Object> qryParams){
        return getLoanBookDAO().findBorrowedBook(qryParams);
    }
    
    public boolean returnLoanedBook(LoanBookDTO loanBookDTO){
        return getLoanBookDAO().returnLoanedBook(loanBookDTO);
    }
    @Override
    protected void destroy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
