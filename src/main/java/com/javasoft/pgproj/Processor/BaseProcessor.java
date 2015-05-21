/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.Processor;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.DTO.MemberDTO;
import com.javasoft.pgproj.entity.DAO.ActivityLogDAO;
import com.javasoft.pgproj.entity.DAO.BookDAO;
import com.javasoft.pgproj.entity.DAO.BorrowedBookDAO;
import com.javasoft.pgproj.entity.DAO.MemberDAO;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.interfaces.Validator;
import com.javasoft.pgproj.util.ErrorUtil;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;


/**
 *
 * @author ayojava
 */
public abstract class BaseProcessor implements Validator {
    private ErrorUtil errorUtil;
    private MemberDTO userDTO;
    private BookDTO bookDTO;
    private Pattern pattern;
    private Matcher matcher;
    public ErrorUtil getErrorUtil() {
        if (errorUtil == null) {
            errorUtil = new ErrorUtil();
        }
        return errorUtil;
    }
    public BookDTO getBookDTO() {
        return bookDTO;
    }
    public MemberDTO getUserDTO() {
        return userDTO;
    }
    public void setUserDTO(MemberDTO userDTO) {
        this.userDTO = userDTO;
    }
    public void setBookDTO(BookDTO bookDTO) {
        this.bookDTO = bookDTO;
    }
    protected abstract void destroy();
    public String buildErrorMessage() {
        Set<Map.Entry<String, String>> entrySet = getErrorUtil().entrySet();
        Iterator<Map.Entry<String, String>> iterator = entrySet.iterator();

        StringBuilder builder = new StringBuilder();
        builder.append("<html>Error while Validating Values <ul>");
        while (iterator.hasNext()) {
            Map.Entry<String, String> me = iterator.next();
            builder.append("<li>").append(WordUtils.capitalizeFully(me.getKey())).append("&nbsp;&nbsp;&nbsp;&nbsp").
                    append("<b>").append(me.getValue()).append("</b></li>");
        }
        builder.append("</ul></html>");
        getErrorUtil().clear();
        return builder.toString();
    }
    private BookDAO bookDAO;

    public BookDAO getBookDAO() {
        if (bookDAO == null) {
            bookDAO = new BookDAO(null, true);
        }
        return bookDAO;
    }
    private MemberDAO userIdentityDAO;
    public MemberDAO getUserIdentityDAO() {
        if (userIdentityDAO == null) {
            userIdentityDAO = new MemberDAO(null, true);
        }
        return userIdentityDAO;
    }
    private BorrowedBookDAO loanBookDAO;
    public BorrowedBookDAO getLoanBookDAO() {
        if (loanBookDAO == null) {
            loanBookDAO = new BorrowedBookDAO(null, true);
        }
        return loanBookDAO;
    }
    
    private ActivityLogDAO activityLogDAO;
    public ActivityLogDAO getActivityLogDAO(){
        if (activityLogDAO == null) {
            activityLogDAO = new ActivityLogDAO(null, true);
        }
        return activityLogDAO;
    }
    
    @Override
    public boolean validatePhoneNo(String number) {
        if (StringUtils.isNotBlank(number)) {
            pattern = Pattern.compile(Constants.PHONE_N0_PATTERN);
            matcher = pattern.matcher(number);
            return matcher.matches();
        }
        return false;
    }
    @Override
    public boolean validateEmailAddress(String emailAddress) {
        if (StringUtils.isNotBlank(emailAddress)) {
            pattern = Pattern.compile(Constants.EMAIL_PATTERN);
            matcher = pattern.matcher(emailAddress);
            return matcher.matches();
        }
        return false;
    }
}
