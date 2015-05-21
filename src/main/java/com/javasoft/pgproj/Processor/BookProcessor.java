/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.Processor;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.entity.Book;
import com.javasoft.pgproj.util.ResourceUtil;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;


/**
 *
 * @author ayojava
 */
public class BookProcessor extends BaseProcessor {
    public BookDTO processBookObject(BookDTO book) {
        if (book == null || book.getBookObj() == null) {
            setBookDTO(new BookDTO());
            queueBookError("Error ", ResourceUtil.getString("msg_Global_Error"));
            return getBookDTO();
        }
        setBookDTO(book);
        getBookDTO().setValid(true);
        Book bookObj = getBookDTO().getBookObj();
        validateBookAuthor(bookObj);
        //validateBookCode(bookObj,bookDTO.getShelveName());
        validateBookShelve(this.getBookDTO());
        validateBookISBN_Name(bookObj, true);
        validatePublisherName(bookObj);
        validateCopies(getBookDTO().getCopies());
        validatePages(getBookDTO().getPages());
        return getBookDTO();
    }

    public BookDTO updateBookObject(BookDTO bookDTO, Book newBook) {
        setBookDTO(bookDTO);
        getBookDTO().setValid(true);
        validateBookAuthor(newBook);
        validateBookISBN_Name(newBook, false);
        validatePublisherName(newBook);
        validatePages(bookDTO.getPages(), newBook);
        calculateDiff(bookDTO);
        if (getBookDTO().isValid()) {
            newBook.setCopies(Integer.valueOf(bookDTO.getCopies()));
            getBookDTO().setBookObj(newBook);
        }
        return this.getBookDTO();
    }

    private void calculateDiff(BookDTO bookDTO) {
        String copies = bookDTO.getCopies();
        if (!NumberUtils.isDigits(copies)) {
            queueBookError(ResourceUtil.getString("label_book_Copies"), ResourceUtil.getString("msg_Global_Number_Error"));
        } else {
            int oldCopies = bookDTO.getBookObj().getCopies();
            int newCopies = Integer.parseInt(copies);
            System.out.println("Old Copies  " + oldCopies);
            System.out.println("New Copies  " + newCopies);
            if (newCopies < oldCopies) {
                queueBookError(ResourceUtil.getString("label_book_Copies"), ResourceUtil.getString("menuItem_book_editBook_BookCopiesError"));
            } else if (newCopies > oldCopies) {
                bookDTO.setCopies(String.valueOf(newCopies - oldCopies));
            } else {
                bookDTO.setCopies(String.valueOf(0));
            }
        }
    }
    private void validateCopies(String copies) {
        if (!NumberUtils.isDigits(copies)) {
            queueBookError(ResourceUtil.getString("label_book_Copies"), ResourceUtil.getString("msg_Global_Number_Error"));
        }
    }
    private void validatePages(String pages) {
        if (!NumberUtils.isDigits(pages)) {
            queueBookError(ResourceUtil.getString("label_book_Pages"), ResourceUtil.getString("msg_Global_Number_Error"));
        }
    }
    private void validatePages(String pages, Book newBook) {
        if (!NumberUtils.isDigits(pages)) {
            queueBookError(ResourceUtil.getString("label_book_Pages"), ResourceUtil.getString("msg_Global_Number_Error"));
        } else {
            newBook.setPages(Integer.parseInt(pages));
        }
    }
    private void validatePublisherName(Book bookObj) {
        if (StringUtils.isBlank(bookObj.getPublisherName())) {
            queueBookError(ResourceUtil.getString("label_book_PublisherName"), ResourceUtil.getString("msg_Global_requiredMsg"));
        }
    }
    private void validateBookISBN_Name(Book bookObj, boolean isNew) {
        if (StringUtils.isBlank(bookObj.getBookISBN())) {
            queueBookError(ResourceUtil.getString("label_book_ISBN"), ResourceUtil.getString("msg_Global_requiredMsg"));
        }

        if (StringUtils.isBlank(bookObj.getBookName())) {
            queueBookError(ResourceUtil.getString("label_book_Name"), ResourceUtil.getString("msg_Global_requiredMsg"));
        }

        if (StringUtils.isNotBlank(bookObj.getBookName()) && StringUtils.isNotBlank(bookObj.getBookISBN()) && isNew) {
            if (getBookDAO().findByISBN_Name(bookObj.getBookName(), bookObj.getBookISBN())) {
                Object[] array = {bookObj.getBookName()};
                queueBookError(ResourceUtil.getString("label_book_Name"), ResourceUtil.getString("menuItem_book_addBook_BookNameError", array));
            }
        }
    }
    private void validateBookAuthor(Book bookObj) {
        if (StringUtils.isBlank(bookObj.getBookAuthor())) {
            queueBookError(ResourceUtil.getString("label_book_AuthorName"), ResourceUtil.getString("msg_Global_requiredMsg"));
        }
    }
    private void validateBookShelve(BookDTO bookDTO) {
        if (bookDTO.getShelveId() == 0) {
            queueBookError(ResourceUtil.getString("label_book_ShelveName"), ResourceUtil.getString("msg_Global_requiredMsg"));
        }
    }
    private void queueBookError(String label, String errorMsg) {
        getErrorUtil().queue(label, errorMsg);
        getBookDTO().setValid(false);
    }
    public boolean persistBook(BookDTO bookDTO) {
        return getBookDAO().persistBook(bookDTO);
    }
    public BookDTO findBookDTO(Map<String, Object> qryParams) {
        return getBookDAO().findBookDTO(qryParams);
    }
    public boolean updateBook(BookDTO bookDTO) {
        return getBookDAO().updateBook(bookDTO);
    }
    public Book findAvailableBook(Map<String, Object> qryParams) {
        return getBookDAO().findAvailableBook(qryParams);
    }
    @Override
    public void destroy() {
        setBookDTO(null);

    }
}
