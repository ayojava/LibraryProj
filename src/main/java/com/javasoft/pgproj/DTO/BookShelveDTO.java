/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DTO;

import com.javasoft.pgproj.entity.DAO.BookShelveDAO;
import java.util.List;

/**
 *
 * @author ayojava contains validation logic for BookShelve Entity and retrieve
 */
public class BookShelveDTO extends BaseDTO {
    private transient BookShelveDAO bookShelveDao;
    public BookShelveDTO(Long Id, String name, String value) {
        setId(Id);
        setName(name);
        setValue(value);
    }
    public BookShelveDTO() {
    }
    public List<BookShelveDTO> getBookShelves() {
        return getBookShelveDAO().findAllBookShelves();
    }
    public BookShelveDAO getBookShelveDAO() {
        if (bookShelveDao == null) {
            bookShelveDao = new BookShelveDAO(null, false);
        }
        return bookShelveDao;
    }
}
