/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.book;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.Processor.BookProcessor;
import com.javasoft.pgproj.entity.Book;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.util.MailUtil;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author ayojava
 */
public class EditBookFrame extends ParentFrame {

    private JPanel bookCenterPanel, southPanel, bookSearchPanel, bookCenterPanellabel, bookCenterPaneltxtField;
    private JLabel bookNameSearchLbl, bookISBNSearchLbl;
    private JTextField bookNameSearchTxtField, bookISBNSearchTxtField;
    private JLabel bookNameLabel, authorNameLabel, publisherNameLabel, shelveNameLabel, isbnNo, bookPages, bookCopies, shelveNameLabel_;
    private JTextField bookNameTxtField, authorNameTxtField, publisherNameTxtField;
    private JTextField isbnTxtField, pagesTxtField, copiesTxtField;
    private BookDTO bookDTO;

    public EditBookFrame() {
        super(ResourceUtil.getString("internalFrame_book_editBook"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Edit.gif")));
        initLabels();
        initInputFields();
        populateSearchPanel();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initLabels() {
        bookNameSearchLbl = new JLabel(ResourceUtil.getString("label_book_Name"));//
        bookNameSearchLbl.setFont(getLabelFont());

        bookISBNSearchLbl = new JLabel(ResourceUtil.getString("label_book_ISBN"));//
        bookISBNSearchLbl.setFont(getLabelFont());

        bookNameLabel = new JLabel(ResourceUtil.getString("label_book_Name"));
        bookNameLabel.setFont(getLabelFont());
        authorNameLabel = new JLabel(ResourceUtil.getString("label_book_AuthorName"));
        authorNameLabel.setFont(getLabelFont());
        publisherNameLabel = new JLabel(ResourceUtil.getString("label_book_PublisherName"));
        publisherNameLabel.setFont(getLabelFont());
        shelveNameLabel = new JLabel(ResourceUtil.getString("label_book_ShelveName"));
        shelveNameLabel.setFont(getLabelFont());

        shelveNameLabel_ = new JLabel();

        isbnNo = new JLabel(ResourceUtil.getString("label_book_ISBN"));
        isbnNo.setFont(getLabelFont());
        bookPages = new JLabel(ResourceUtil.getString("label_book_Pages"));
        bookPages.setFont(getLabelFont());
        bookCopies = new JLabel(ResourceUtil.getString("label_book_Copies"));//Allow adding only
        bookCopies.setFont(getLabelFont());


    }

    private void populateCentrePanel() {
        bookCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_book_editBook"));
        bookCenterPanellabel = defaultCentrePanelContents(7, 1, 5, 5);

        bookCenterPanellabel.add(shelveNameLabel);
        bookCenterPanellabel.add(bookNameLabel);
        bookCenterPanellabel.add(authorNameLabel);
        bookCenterPanellabel.add(publisherNameLabel);
        bookCenterPanellabel.add(isbnNo);
        bookCenterPanellabel.add(bookPages);
        bookCenterPanellabel.add(bookCopies);


        bookCenterPanel.add(BorderLayout.WEST, bookCenterPanellabel);

        bookCenterPaneltxtField = defaultCentrePanelContents(7, 1, 5, 5);

        bookCenterPaneltxtField.add(shelveNameLabel_);
        bookCenterPaneltxtField.add(bookNameTxtField);
        bookCenterPaneltxtField.add(authorNameTxtField);
        bookCenterPaneltxtField.add(publisherNameTxtField);
        bookCenterPaneltxtField.add(isbnTxtField);
        bookCenterPaneltxtField.add(pagesTxtField);
        bookCenterPaneltxtField.add(copiesTxtField);


        bookCenterPanel.add(BorderLayout.EAST, bookCenterPaneltxtField);
    }

    private void initInputFields() {
        bookNameSearchTxtField = new JTextField(20);
        bookNameSearchTxtField.setFont(getFont());

        bookISBNSearchTxtField = new JTextField(20);
        bookISBNSearchTxtField.setFont(getFont());

        bookNameTxtField = new JTextField(30);
        bookNameTxtField.setFont(getFont());

        authorNameTxtField = new JTextField(30);
        authorNameTxtField.setFont(getFont());

        publisherNameTxtField = new JTextField(30);
        publisherNameTxtField.setFont(getFont());

        isbnTxtField = new JTextField(30);
        isbnTxtField.setFont(getFont());
        isbnTxtField.setEditable(false);

        pagesTxtField = new JTextField(30);
        pagesTxtField.setFont(getFont());

        copiesTxtField = new JTextField(30);
        copiesTxtField.setFont(getFont());
    }

    private void initComponents() {
        Container cp = getContentPane();
        JPanel northEditPanel = new JPanel();
        northEditPanel.add(bookSearchPanel);
        cp.add(BorderLayout.NORTH, northEditPanel);
        populateCentrePanel();
        cp.add(BorderLayout.CENTER, bookCenterPanel);
        cp.add(BorderLayout.SOUTH, defaultBookSouthPanel());
    }

    private JPanel defaultBookSouthPanel() {
        southPanel = defaultSouthPanel();
        getSaveButton().setText(ResourceUtil.getString("button_book_UpdateBook")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/edit.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());
        southPanel.add(getExitButton());
        return southPanel;
    }

    private void populateSearchPanel() {
        bookSearchPanel = defaultSearchPanel(ResourceUtil.getString("selectItem_Global_Searchby"), 3, 2, 2, 2);
        bookSearchPanel.add(bookNameSearchLbl);
        bookSearchPanel.add(bookISBNSearchLbl);
        bookSearchPanel.add(bookNameSearchTxtField);
        bookSearchPanel.add(bookISBNSearchTxtField);
        bookSearchPanel.add(new JLabel());
        getSearchButton().addActionListener(this);
        bookSearchPanel.add(getSearchButton());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BookProcessor bookProcessor = new BookProcessor();

        String actionCmd = e.getActionCommand();
        if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("button_Search"))) {
            performSearch(bookProcessor);
        } else {
            if (validateBook(bookProcessor)) {
                Book book = bookDTO.getBookObj();

                if (bookProcessor.updateBook(bookDTO)) {
                    String info =
                            "<html>" + ResourceUtil.getString("msg_Global_EditOperation_Success",
                            new Object[]{" Book [ " + WordUtils.capitalize(bookDTO.getBookObj().getBookName()) + " ] "}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_editBook_Success"), JOptionPane.INFORMATION_MESSAGE);

                    MailUtil mailUtil = new MailUtil();
                    mailUtil.sendBookDetails(bookDTO, true);
                } else {
                    String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error", new Object[]{"Update <br/><br/>"}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_book_editBook_Error"), JOptionPane.ERROR_MESSAGE);
                }

            } else {
                showMessage(bookProcessor.buildErrorMessage(), ResourceUtil.getString("menuItem_book_editBook_Error"), JOptionPane.ERROR_MESSAGE);
            }
            resetToDefault(bookCenterPaneltxtField);
            resetToDefault(bookSearchPanel);
        }
    }

    private boolean validateBook(BookProcessor bookProcessor) {
        try {
            Book bookObj = new Book();
            bookObj.setBookName(bookNameTxtField.getText());
            bookObj.setBookAuthor(authorNameTxtField.getText());
            bookObj.setPublisherName(publisherNameTxtField.getText());
            bookObj.setBookISBN(bookDTO.getBookObj().getBookISBN());
            bookDTO.setPages(pagesTxtField.getText());
            bookDTO.setCopies(copiesTxtField.getText());
            return bookProcessor.updateBookObject(bookDTO, bookObj).isValid();
        } catch (Exception ex) {
            return false;
        }
    }

    private void performSearch(BookProcessor bookProcessor) {
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

            bookDTO = bookProcessor.findBookDTO(qryParams);

            System.out.println("" + bookDTO);

            if (bookDTO != null) {
                bookNameTxtField.setText(bookDTO.getBookObj().getBookName());
                authorNameTxtField.setText(bookDTO.getBookObj().getBookAuthor());
                publisherNameTxtField.setText(bookDTO.getBookObj().getPublisherName());
                isbnTxtField.setText(bookDTO.getBookObj().getBookISBN());
                pagesTxtField.setText(String.valueOf(bookDTO.getBookObj().getPages()));
                copiesTxtField.setText(String.valueOf(bookDTO.getBookObj().getCopies()));
                shelveNameLabel_.setText(bookDTO.getName());
            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
                resetToDefault(bookCenterPaneltxtField);
            }
        }
    }
}
