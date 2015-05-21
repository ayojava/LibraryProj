/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.service;

import com.javasoft.pgproj.entity.BorrowedBook;
import com.javasoft.pgproj.entity.DAO.BorrowedBookDAO;
import com.javasoft.pgproj.util.MailUtil;
import com.javasoft.pgproj.util.PropertyUtil;
import java.util.Date;
import java.util.List;
import org.quartz.*;

/**
 *
 * @author ayojava
 */
public class BookService  implements Job {

    private static BookService bookService;

    public static BookService getInstance() {
        if (bookService == null) {
            bookService = new BookService();
        }
        return bookService;
    }

     private static BorrowedBookDAO loanBookDAO;

    public BorrowedBookDAO getLoanBookDAO() {
        if(loanBookDAO == null ) {
            loanBookDAO = new BorrowedBookDAO(null, true);
        }
        return loanBookDAO;
    }
    public boolean init(Scheduler scheduler) {
        boolean output = true;
        try {
            JobDetail jobDetail = new JobDetail("BookService", "BookServiceGroup", BookService.class);
            CronTrigger cronTrigger = new CronTrigger("BookServiceCronTrigger", "LibraryTriggerService");
            CronExpression cronExp = new CronExpression(PropertyUtil.getInstance().getCronExpression());
            cronTrigger.setCronExpression(cronExp);
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.start();
        } catch (Exception ex) {
            output = false;
            System.out.println("Exception " + ex);
        }
        return output;
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        List<BorrowedBook> borrowedBook = getLoanBookDAO().findUnreturnedBooks();
        if(borrowedBook.size() >0 ){
            MailUtil mail = new MailUtil();
            mail.sendOutstandingBooksDetails(borrowedBook);
            System.out.println("Time :::::::::: " + new Date());
        }
    }
//
//    @Override
//    protected void destroy() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}
