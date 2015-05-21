/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.loan;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.DTO.LoanBookDTO;
import com.javasoft.pgproj.DTO.MemberDTO;
import com.javasoft.pgproj.Processor.BookProcessor;
import com.javasoft.pgproj.Processor.LoanBookProcessor;
import com.javasoft.pgproj.Processor.MemberProcessor;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.util.MailUtil;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author ayojava
 */
public class LoanBookFrame extends ParentFrame {

    private JLabel bookNameSearchLbl, bookISBNSearchLbl;
    private JLabel regNoSearchLbl, lastNameSearchLbl;
    private JLabel loanDateLbl, dueDateLbl, bookNameDisplayLbl, memberNameDisplayLbl, bookNameLbl, memberNameLbl;
    private JTextField bookNameSearchTxtField, bookISBNSearchTxtField;
    private JTextField regNoSearchTxtField, lastNameSearchTxtField;
    private JLabel loanDateDisplayLbl, dueDateDisplayLbl;
    private JPanel bookSearchPanel, memberSearchPanel;
    private JPanel northPanel, centrePanel, southPanel;
    private JPanel centreWestPanel, centreEastPanel;
    private BookDTO bookDTO;
    private MemberDTO memberDTO;
    private LoanBookDTO loanBookDTO;
    private boolean isBookAvailable = false;
    private boolean isMemberAvailable = false;
    private LoanBookProcessor loanBookProcessor;

    public LoanBookFrame() {
        super(ResourceUtil.getString("internalFrame_book_loan"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Borrow.gif")));
        loanBookDTO = new LoanBookDTO();
        initLabels();
        initInputFields();
        populateMemberSearchPanel();
        populateBookSearchPanel();
        populateCentrePanel();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        northPanel = new JPanel(new BorderLayout(3, 3));
        northPanel.add(bookSearchPanel, BorderLayout.NORTH);
        northPanel.add(memberSearchPanel, BorderLayout.SOUTH);
        cp.add(BorderLayout.NORTH, northPanel);
        cp.add(BorderLayout.CENTER, centrePanel);
        cp.add(BorderLayout.SOUTH, southPanel());
    }

    private JPanel southPanel() {
        southPanel = defaultSouthPanel();
        getSaveButton().setText(ResourceUtil.getString("button_Save")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/add.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());
        southPanel.add(getExitButton());
        return southPanel;
    }

    private void populateCentrePanel() {

        centrePanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_loanBook_loanDetails"));
        centreWestPanel = defaultCentrePanelContents(4, 1, 5, 5);

        centreWestPanel.add(bookNameLbl);
        centreWestPanel.add(memberNameLbl);
        centreWestPanel.add(loanDateLbl);
        centreWestPanel.add(dueDateLbl);

        centreEastPanel = defaultCentrePanelContents(4, 1, 5, 5);
        centreEastPanel.add(bookNameDisplayLbl);
        centreEastPanel.add(memberNameDisplayLbl);
        centreEastPanel.add(loanDateDisplayLbl);
        centreEastPanel.add(dueDateDisplayLbl);

        centrePanel.add(BorderLayout.WEST, centreWestPanel);
        centrePanel.add(BorderLayout.EAST, centreEastPanel);
    }

    private void populateMemberSearchPanel() {
        memberSearchPanel = defaultSearchPanel(ResourceUtil.getString("selectItem_Global_FindMember"), 3, 2, 2, 2);
        memberSearchPanel.add(regNoSearchLbl);
        memberSearchPanel.add(lastNameSearchLbl);
        memberSearchPanel.add(regNoSearchTxtField);
        memberSearchPanel.add(lastNameSearchTxtField);
        memberSearchPanel.add(new JLabel());
        getSearchButton_().addActionListener(this);
        getSearchButton_().setActionCommand(ResourceUtil.getString("selectItem_Global_FindMember"));
        memberSearchPanel.add(getSearchButton_());
        //memberSearchPanel.add(new JButton ("Test"));
    }

    private void populateBookSearchPanel() {

        bookSearchPanel = defaultSearchPanel(ResourceUtil.getString("selectItem_Global_FindBook"), 3, 2, 2, 2);
        bookSearchPanel.add(bookNameSearchLbl);
        bookSearchPanel.add(bookISBNSearchLbl);
        bookSearchPanel.add(bookNameSearchTxtField);
        bookSearchPanel.add(bookISBNSearchTxtField);
        bookSearchPanel.add(new JLabel());
        getSearchButton().addActionListener(this);
        getSearchButton().setActionCommand(ResourceUtil.getString("selectItem_Global_FindBook"));
        bookSearchPanel.add(getSearchButton());
    }

    private void initInputFields() {
        bookNameSearchTxtField = new JTextField(20);
        bookNameSearchTxtField.setFont(getFont());

        bookISBNSearchTxtField = new JTextField(20);
        bookISBNSearchTxtField.setFont(getFont());

        regNoSearchTxtField = new JTextField(20);
        regNoSearchTxtField.setFont(getFont());

        lastNameSearchTxtField = new JTextField(20);
        lastNameSearchTxtField.setFont(getFont());
    }

    private void initLabels() {

        bookNameSearchLbl = new JLabel(ResourceUtil.getString("label_book_Name"));//
        bookNameSearchLbl.setFont(getLabelFont());
        bookISBNSearchLbl = new JLabel(ResourceUtil.getString("label_book_ISBN"));//
        bookISBNSearchLbl.setFont(getLabelFont());

        regNoSearchLbl = new JLabel(ResourceUtil.getString("label_member_regNo"));
        regNoSearchLbl.setFont(getLabelFont());
        lastNameSearchLbl = new JLabel(ResourceUtil.getString("label_member_lastName"));
        lastNameSearchLbl.setFont(getLabelFont());

        loanDateLbl = new JLabel(ResourceUtil.getString("label_loanBook_loanDate"));
        loanDateLbl.setFont(getLabelFont());

        dueDateLbl = new JLabel(ResourceUtil.getString("label_loanBook_dueDate"));
        dueDateLbl.setFont(getLabelFont());

        loanDateDisplayLbl = new JLabel();
        //loanDateDisplayLbl.setFont(getFont());

        dueDateDisplayLbl = new JLabel();
        //returnDateDisplayLbl.setFont(getFont());

        bookNameDisplayLbl = new JLabel();
        //bookNameDisplayLbl.setFont(getFont());

        memberNameDisplayLbl = new JLabel();
        //memberNameDisplayLbl.setFont(getFont());

        memberNameLbl = new JLabel(ResourceUtil.getString("label_member_fullName"));
        memberNameLbl.setFont(getLabelFont());

        bookNameLbl = new JLabel(ResourceUtil.getString("label_book_Name"));
        bookNameLbl.setFont(getLabelFont());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String actionCmd = e.getActionCommand();
        if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("selectItem_Global_FindBook"))) {
            BookProcessor bookProcessor = new BookProcessor();
            performBookSearch(bookProcessor);

        } else if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("selectItem_Global_FindMember"))) {
            MemberProcessor memberProcessor = new MemberProcessor();
            performMemberSearch(memberProcessor);

        } else if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("button_Save"))) {
            if (isBookAvailable && isMemberAvailable) {
                loanBookDTO.getBorrowedBook().setBook(bookDTO.getBookObj());
                loanBookDTO.getBorrowedBook().setUser(memberDTO.getUserIdentity());
                loanBookProcessor = new LoanBookProcessor();

                if (loanBookProcessor.persistLoanedBook(loanBookDTO)) {
                    String info =
                            "<html>" + ResourceUtil.getString("menuItem_book_loanBook_Success",
                            new Object[]{" Book [ " + WordUtils.capitalize(bookDTO.getBookObj().getBookName()) + " ] "}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_loanBook"), JOptionPane.INFORMATION_MESSAGE);
                    /*
                    MailUtil mailUtil = new MailUtil();
                    mailUtil.sendLoanedBookDetails(loanBookDTO);
                    */
                } else {
                    String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error", new Object[]{"Loan Book <br/><br/>"}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_loanBook_error"), JOptionPane.ERROR_MESSAGE);
                }
                memberNameDisplayLbl.setText("");
                bookNameDisplayLbl.setText("");
                loanDateDisplayLbl.setText("");
                dueDateDisplayLbl.setText("");
                isBookAvailable = false;
                isMemberAvailable= false;
                loanBookDTO= new LoanBookDTO();
            } else {
                showMessage(ResourceUtil.getString("msg_Global_IncompleteDetails_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
            }
        }

        if (isBookAvailable && isMemberAvailable) {

            Calendar cal = Calendar.getInstance();
            loanDateDisplayLbl.setText(DateFormatUtils.format(cal, Constants.DATE_FORMAT_PATTERN));
            loanDateDisplayLbl.setFont(getLabelFont());
            loanBookDTO.getBorrowedBook().setBorrowedDate(cal.getTime());

            cal.add(Calendar.DAY_OF_MONTH, Integer.parseInt(Constants.LOAN_DATE));
            dueDateDisplayLbl.setText(DateFormatUtils.format(cal, Constants.DATE_FORMAT_PATTERN));
            dueDateDisplayLbl.setFont(getLabelFont());
            loanBookDTO.getBorrowedBook().setDueDate(cal.getTime());
        }

    }

    private void performMemberSearch(MemberProcessor memberProcessor) {
        if (StringUtils.isBlank(regNoSearchTxtField.getText()) && StringUtils.isBlank(lastNameSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("menuItem_book_editMember_Error"),
                    JOptionPane.ERROR_MESSAGE);
        } else {

            Map<String, Object> qryParams = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(regNoSearchTxtField.getText())) {
                qryParams.put("regNo", regNoSearchTxtField.getText());
            }
            if (StringUtils.isNotBlank(lastNameSearchTxtField.getText())) {
                qryParams.put("lastName", lastNameSearchTxtField.getText());
            }
            memberDTO = new MemberDTO();
            memberDTO.setUserIdentity(memberProcessor.findMember(qryParams));
            resetToDefault(memberSearchPanel);

            if (memberDTO.getUserIdentity() != null) {
                memberNameDisplayLbl.setText(memberDTO.getUserIdentity().returnFullName());
                memberNameDisplayLbl.setFont(getLabelFont());
                isMemberAvailable = true;
            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);

            }
        }
    }

    private void performBookSearch(BookProcessor bookProcessor) {
        if (StringUtils.isBlank(bookNameSearchTxtField.getText()) && StringUtils.isBlank(bookISBNSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("menuItem_book_editBook_Error"), JOptionPane.ERROR_MESSAGE);
        } else {
            Map<String, Object> qryParams = new HashMap<String, Object>();
            if (StringUtils.isNotBlank(bookNameSearchTxtField.getText())) {
                qryParams.put("bookName", bookNameSearchTxtField.getText());
            }
            if (StringUtils.isNotBlank(bookISBNSearchTxtField.getText())) {
                qryParams.put("bookISBN", bookISBNSearchTxtField.getText());
            }
            bookDTO = new BookDTO();
            bookDTO.setBookObj(bookProcessor.findAvailableBook(qryParams));
            resetToDefault(bookSearchPanel);
            if (bookDTO.getBookObj() != null) {
                bookNameDisplayLbl.setText(bookDTO.getBookObj().getBookName());
                bookNameDisplayLbl.setFont(getLabelFont());
                isBookAvailable = true;
            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
