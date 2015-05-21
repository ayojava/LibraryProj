/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.book;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.Processor.BookProcessor;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class ViewBookFrame extends ParentFrame {

    private JLabel bookNameSearchLbl, bookISBNSearchLbl;
    private JLabel bookNameLabel, authorNameLabel, publisherNameLabel, shelveNameLabel, isbnNo, bookPages, bookCopies, shelveNameLabel_;
    private JPanel bookCenterPanel, southPanel, bookSearchPanel, bookCenterPanellabel, bookCenterPaneltxtField;
    private JTextField bookNameSearchTxtField, bookISBNSearchTxtField;
    private JTextField bookNameLbl, authorNameLbl, publisherNameLbl, isbnLbl, pagesLbl, copiesLbl;
    private BookDTO bookDTO;

    public ViewBookFrame() {
        super(ResourceUtil.getString("internalFrame_book_viewBook"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/info.png")));
        initLabels();
        initInputLabelAndTextField();
        populateSearchPanel();
        initComponents();
        setVisible(true);
        pack();
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

    private void initInputLabelAndTextField() {
        bookNameSearchTxtField = new JTextField(20);
        bookNameSearchTxtField.setFont(getFont());

        bookISBNSearchTxtField = new JTextField(20);
        bookISBNSearchTxtField.setFont(getFont());

        bookNameLbl = new JTextField(20);
        bookNameLbl.setEditable(false);
        authorNameLbl = new JTextField(20);
        authorNameLbl.setEditable(false);
        publisherNameLbl = new JTextField(20);
        publisherNameLbl.setEditable(false);
        isbnLbl = new JTextField(20);
        isbnLbl.setEditable(false);
        pagesLbl = new JTextField(20);
        pagesLbl.setEditable(false);
        copiesLbl = new JTextField(20);
        copiesLbl.setEditable(false);
    }

    private void populateCentrePanel() {
        bookCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_book_viewBook"));
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
        bookCenterPaneltxtField.add(bookNameLbl);
        bookCenterPaneltxtField.add(authorNameLbl);
        bookCenterPaneltxtField.add(publisherNameLbl);
        bookCenterPaneltxtField.add(isbnLbl);
        bookCenterPaneltxtField.add(pagesLbl);
        bookCenterPaneltxtField.add(copiesLbl);
        bookCenterPanel.add(BorderLayout.EAST, bookCenterPaneltxtField);
    }

    private JPanel defaultBookSouthPanel() {
        southPanel = defaultSouthPanel();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        BookProcessor bookProcessor = new BookProcessor();
        performSearch(bookProcessor);
    }

    private void performSearch(BookProcessor bookProcessor) {
        if (StringUtils.isBlank(bookNameSearchTxtField.getText()) && StringUtils.isBlank(bookISBNSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("menuItem_book_viewBook_Error"), 
                    JOptionPane.ERROR_MESSAGE);
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
                bookNameLbl.setText(bookDTO.getBookObj().getBookName());
                authorNameLbl.setText(bookDTO.getBookObj().getBookAuthor());
                publisherNameLbl.setText(bookDTO.getBookObj().getPublisherName());
                isbnLbl.setText(bookDTO.getBookObj().getBookISBN());
                pagesLbl.setText(String.valueOf(bookDTO.getBookObj().getPages()));
                copiesLbl.setText(String.valueOf(bookDTO.getBookObj().getCopies()));
                shelveNameLabel_.setText(bookDTO.getName());
            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
                resetToDefault(bookCenterPaneltxtField);
            }
        }
    }
}
