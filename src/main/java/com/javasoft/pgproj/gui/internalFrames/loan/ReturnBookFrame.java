/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.loan;

import com.javasoft.pgproj.DTO.LoanBookDTO;
import com.javasoft.pgproj.Processor.LoanBookProcessor;
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
public class ReturnBookFrame extends ParentFrame {

    private JLabel bookISBNSearchLbl;
    private JLabel regNoSearchLbl;
    private JLabel loanDateLbl, dueDateLbl, bookNameDisplayLbl, memberNameDisplayLbl, bookNameLbl, memberNameLbl, returnDateLbl;
    private JTextField bookISBNSearchTxtField;
    private JTextField regNoSearchTxtField;
    private JLabel loanDateDisplayLbl, dueDateDisplayLbl, returnDateDisplayLbl;
    private JPanel detailsSearchPanel;
    private JPanel northPanel, centrePanel, southPanel;
    private JPanel centreWestPanel, centreEastPanel;
    private boolean isDetailsAvailable = false;
    private LoanBookDTO loanBookDTO;
    private LoanBookProcessor loanBookProcessor;

    public ReturnBookFrame() {
        super(ResourceUtil.getString("internalFrame_book_return"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Borrow.gif")));
        loanBookDTO = new LoanBookDTO();
        initLabels();
        initInputFields();
        populateDetailSearchPanel();

        populateCentrePanel();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        northPanel = new JPanel();
        northPanel.add(detailsSearchPanel);
        cp.add(BorderLayout.NORTH, northPanel);
        cp.add(BorderLayout.CENTER, centrePanel);
        cp.add(BorderLayout.SOUTH, southPanel());
    }

    private void populateDetailSearchPanel() {
        detailsSearchPanel = defaultSearchPanel(ResourceUtil.getString("button_Search"), 3, 2, 2, 2);
        detailsSearchPanel.add(bookISBNSearchLbl);
        detailsSearchPanel.add(regNoSearchLbl);
        detailsSearchPanel.add(bookISBNSearchTxtField);
        detailsSearchPanel.add(regNoSearchTxtField);
        detailsSearchPanel.add(new JLabel());
        getSearchButton().addActionListener(this);
        getSearchButton().setActionCommand(ResourceUtil.getString("button_Search"));
        detailsSearchPanel.add(getSearchButton());
    }

    private JPanel southPanel() {
        southPanel = defaultSouthPanel();
        getSaveButton().setText(ResourceUtil.getString("button_Return")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/edit.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());
        southPanel.add(getExitButton());
        return southPanel;
    }

    private void populateCentrePanel() {
        centrePanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_returnBook_returnBookDetails"));
        centreWestPanel = defaultCentrePanelContents(5, 1, 5, 5);

        centreWestPanel.add(bookNameLbl);
        centreWestPanel.add(memberNameLbl);
        centreWestPanel.add(loanDateLbl);
        centreWestPanel.add(dueDateLbl);
        centreWestPanel.add(returnDateLbl);


        centreEastPanel = defaultCentrePanelContents(5, 1, 5, 5);
        centreEastPanel.add(bookNameDisplayLbl);
        centreEastPanel.add(memberNameDisplayLbl);
        centreEastPanel.add(loanDateDisplayLbl);
        centreEastPanel.add(dueDateDisplayLbl);
        centreEastPanel.add(returnDateDisplayLbl);

        centrePanel.add(BorderLayout.WEST, centreWestPanel);
        centrePanel.add(BorderLayout.EAST, centreEastPanel);
    }

    private void initInputFields() {

        bookISBNSearchTxtField = new JTextField(20);
        bookISBNSearchTxtField.setFont(getFont());

        regNoSearchTxtField = new JTextField(20);
        regNoSearchTxtField.setFont(getFont());
    }

    private void initLabels() {

        bookISBNSearchLbl = new JLabel(ResourceUtil.getString("label_book_ISBN"));//
        bookISBNSearchLbl.setFont(getLabelFont());

        regNoSearchLbl = new JLabel(ResourceUtil.getString("label_member_regNo"));
        regNoSearchLbl.setFont(getLabelFont());

        loanDateLbl = new JLabel(ResourceUtil.getString("label_loanBook_loanDate"));
        loanDateLbl.setFont(getLabelFont());

        loanDateDisplayLbl = new JLabel();
        dueDateDisplayLbl = new JLabel();
       
        bookNameDisplayLbl = new JLabel();
        
        memberNameDisplayLbl = new JLabel();
        returnDateDisplayLbl = new JLabel();

        memberNameLbl = new JLabel(ResourceUtil.getString("label_member_fullName"));
        memberNameLbl.setFont(getLabelFont());

        returnDateLbl = new JLabel(ResourceUtil.getString("label_returnBook_returnDate"));
        returnDateLbl.setFont(getLabelFont());

        dueDateLbl = new JLabel(ResourceUtil.getString("label_loanBook_dueDate"));
        dueDateLbl.setFont(getLabelFont());

        bookNameLbl = new JLabel(ResourceUtil.getString("label_book_Name"));
        bookNameLbl.setFont(getLabelFont());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCmd = e.getActionCommand();
        if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("button_Search"))) {
            loanBookProcessor = new LoanBookProcessor();
            performLoanedBookSearch(loanBookProcessor);
        } else {
            if (isDetailsAvailable) {
                if (loanBookProcessor.returnLoanedBook(loanBookDTO)) {
                    String info =
                            "<html>" + ResourceUtil.getString("menuItem_book_returnBook_Success",
                            new Object[]{" Book [ " + WordUtils.capitalize(loanBookDTO.getBorrowedBook().getBook().getBookName()) + " ] "}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_returnBook"), JOptionPane.INFORMATION_MESSAGE);

                    MailUtil mailUtil = new MailUtil();
                    mailUtil.sendLoanedBookDetails(loanBookDTO);
                } else {
                    String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error", new Object[]{"Return Book <br/><br/>"}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_returnBook_error"), JOptionPane.ERROR_MESSAGE);
                }
                memberNameDisplayLbl.setText("");
                bookNameDisplayLbl.setText("");
                loanDateDisplayLbl.setText("");
                dueDateDisplayLbl.setText("");
                returnDateDisplayLbl.setText("");
            } else {
                showMessage(ResourceUtil.getString("msg_Global_IncompleteDetails_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
            }
        }

    }

    private void performLoanedBookSearch(LoanBookProcessor loanBookProcessor) {
        if (StringUtils.isBlank(bookISBNSearchTxtField.getText()) || StringUtils.isBlank(regNoSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("msg_Global_Error"),
                    JOptionPane.ERROR_MESSAGE);
        } else {

            Map<String, Object> qryParams = new HashMap<String, Object>();
            qryParams.put("book.bookISBN", bookISBNSearchTxtField.getText());
            qryParams.put("user.regNo", regNoSearchTxtField.getText());

            loanBookDTO.setBorrowedBook(loanBookProcessor.findBorrowedBook(qryParams));
            resetToDefault(detailsSearchPanel);
            //System.out.println("Loan Book :::: " + loanBookDTO.getBorrowedBook());
            if (loanBookDTO.getBorrowedBook() != null) {
                isDetailsAvailable = true;
                memberNameDisplayLbl.setText(loanBookDTO.getBorrowedBook().getUser().returnFullName());
                memberNameDisplayLbl.setFont(getLabelFont());

                bookNameDisplayLbl.setText(loanBookDTO.getBorrowedBook().getBook().getBookName());
                bookNameDisplayLbl.setFont(getLabelFont());

                loanDateDisplayLbl.setText(DateFormatUtils.format(loanBookDTO.getBorrowedBook().getBorrowedDate(), Constants.DATE_FORMAT_PATTERN));
                loanDateDisplayLbl.setFont(getLabelFont());

                dueDateDisplayLbl.setText(DateFormatUtils.format(loanBookDTO.getBorrowedBook().getDueDate(), Constants.DATE_FORMAT_PATTERN));
                dueDateDisplayLbl.setFont(getLabelFont());

                returnDateDisplayLbl.setText(DateFormatUtils.format(Calendar.getInstance(), Constants.DATE_FORMAT_PATTERN));
                returnDateDisplayLbl.setFont(getLabelFont());

            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);

            }
        }
    }
}
