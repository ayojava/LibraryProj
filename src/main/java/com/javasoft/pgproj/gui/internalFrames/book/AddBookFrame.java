/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.book;

import com.javasoft.pgproj.DTO.BookDTO;
import com.javasoft.pgproj.DTO.BookShelveDTO;
import com.javasoft.pgproj.Processor.BookProcessor;
import com.javasoft.pgproj.entity.Book;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.util.MailUtil;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.*;
import javax.swing.*;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author ayojava
 */
public class AddBookFrame extends ParentFrame {

    private JPanel bookCenterPanel, southPanel, bookCenterPanellabel, bookCenterPaneltxtField;
    private JLabel bookNameLabel, authorNameLabel, publisherNameLabel, shelveNameLabel;
    private JLabel isbnNo, bookPages, bookCopies, msglabel;
    private JTextField bookNameTxtField, authorNameTxtField, publisherNameTxtField;
    private JTextField isbnTxtField, pagesTxtField, copiesTxtField;
    private JComboBox shelveComboBox;
    private BookShelveDTO shelves;
    private Map<String, Long> shelveMap;
    BookDTO bookDTO = null;

    public AddBookFrame() {
        super(ResourceUtil.getString("internalFrame_book_addBook"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Add.gif")));
        initLabels();
        initInputFields();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initLabels() {
        bookNameLabel = new JLabel(ResourceUtil.getString("label_book_Name"));
        bookNameLabel.setFont(getLabelFont());
        authorNameLabel = new JLabel(ResourceUtil.getString("label_book_AuthorName"));
        authorNameLabel.setFont(getLabelFont());
        publisherNameLabel = new JLabel(ResourceUtil.getString("label_book_PublisherName"));
        publisherNameLabel.setFont(getLabelFont());
        shelveNameLabel = new JLabel(ResourceUtil.getString("label_book_ShelveName"));
        shelveNameLabel.setFont(getLabelFont());
        isbnNo = new JLabel(ResourceUtil.getString("label_book_ISBN"));
        isbnNo.setFont(getLabelFont());
        bookPages = new JLabel(ResourceUtil.getString("label_book_Pages"));
        bookPages.setFont(getLabelFont());
        bookCopies = new JLabel(ResourceUtil.getString("label_book_Copies"));
        bookCopies.setFont(getLabelFont());
    }

    private void initInputFields() {
        bookNameTxtField = new JTextField(30);
        bookNameTxtField.setFont(getFont());

        authorNameTxtField = new JTextField(30);
        authorNameTxtField.setFont(getFont());

        publisherNameTxtField = new JTextField(30);
        publisherNameTxtField.setFont(getFont());

        isbnTxtField = new JTextField(30);
        isbnTxtField.setFont(getFont());

        pagesTxtField = new JTextField(30);
        pagesTxtField.setFont(getFont());

        copiesTxtField = new JTextField(30);
        copiesTxtField.setFont(getFont());

        shelveComboBox = popoulateComboBox();
    }

    private void initComponents() {
        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, defaultHeaderPanel(ResourceUtil.getString("internalFrame_book_addBook_topPanel")));
        populateCentrePanel();
        cp.add(BorderLayout.CENTER, bookCenterPanel);
        cp.add(BorderLayout.SOUTH, defaultBookSouthPanel());
    }

    private void populateCentrePanel() {
        bookCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_book_addBook_centrePanelBorder"));
        bookCenterPanellabel = defaultCentrePanelContents(7, 1, 5, 5);

        bookCenterPanellabel.add(bookNameLabel);
        bookCenterPanellabel.add(authorNameLabel);
        bookCenterPanellabel.add(publisherNameLabel);
        bookCenterPanellabel.add(isbnNo);
        bookCenterPanellabel.add(bookPages);
        bookCenterPanellabel.add(bookCopies);
        bookCenterPanellabel.add(shelveNameLabel);

        bookCenterPanel.add(BorderLayout.WEST, bookCenterPanellabel);

        bookCenterPaneltxtField = defaultCentrePanelContents(7, 1, 5, 5);

        bookCenterPaneltxtField.add(bookNameTxtField);
        bookCenterPaneltxtField.add(authorNameTxtField);
        bookCenterPaneltxtField.add(publisherNameTxtField);
        bookCenterPaneltxtField.add(isbnTxtField);
        bookCenterPaneltxtField.add(pagesTxtField);
        bookCenterPaneltxtField.add(copiesTxtField);
        bookCenterPaneltxtField.add(shelveComboBox);

        bookCenterPanel.add(BorderLayout.EAST, bookCenterPaneltxtField);
    }

    private JComboBox popoulateComboBox() {
        shelves = new BookShelveDTO();
        shelveComboBox = new JComboBox();
        List<BookShelveDTO> shelveDTOs = shelves.getBookShelves();
        if (shelveDTOs == null || shelveDTOs.isEmpty()) {
            shelveComboBox.addItem(ResourceUtil.getString("selectItem_Global_None"));
            shelveComboBox.setEnabled(false);
        } else {
            shelveMap = new HashMap<String, Long>();
            String shelveSelect = ResourceUtil.getString("selectItem_Global_Item", " A Shelve ");
            shelveComboBox.addItem(shelveSelect);
            shelveMap.put(shelveSelect, 0L);
            for (Iterator<BookShelveDTO> it = shelveDTOs.iterator(); it.hasNext();) {
                BookShelveDTO shelveDTO = it.next();
                shelveComboBox.addItem(shelveDTO.getName());
                shelveMap.put(shelveDTO.getName(), shelveDTO.getId());
            }
        }
        return shelveComboBox;
    }

    private JPanel defaultBookSouthPanel() {
        southPanel = defaultSouthPanel();
        getSaveButton().setText(ResourceUtil.getString("button_book_AddBook")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/add.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());
        southPanel.add(getExitButton());
        return southPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        bookDTO = new BookDTO();
        BookProcessor bookProcessor = new BookProcessor();

        if (validateBook(bookProcessor)) {
            //save the object
            shelveMap.clear();

            if (bookProcessor.persistBook(bookDTO)) {
                String info =
                        "<html>" + ResourceUtil.getString("msg_Global_AddOperation_Success",
                        new Object[]{" Book [ " + WordUtils.capitalize(bookDTO.getBookObj().getBookName()) + " ] "}) + "</html>";
                showMessage(info, ResourceUtil.getString("menuItem_book_addBook_Success"), JOptionPane.INFORMATION_MESSAGE);
                //send a mail and show message box
                MailUtil mailUtil = new MailUtil();
                mailUtil.sendBookDetails(bookDTO, false);
            } else {
                String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error", new Object[]{"save <br/><br/>"}) + "</html>";
                showMessage(info, ResourceUtil.getString("menuItem_book_addBook_Error"), JOptionPane.ERROR_MESSAGE);
            }

        } else {
            showMessage(bookProcessor.buildErrorMessage(), ResourceUtil.getString("menuItem_book_addBook_Error"), JOptionPane.ERROR_MESSAGE);
        }
        resetToDefault(bookCenterPaneltxtField);
        // bookProcessor.destroy();
    }

    private boolean validateBook(BookProcessor bookProcessor) {
        Book bookObj = bookDTO.getBookObj();
        bookObj.setBookName(bookNameTxtField.getText());
        bookObj.setBookAuthor(authorNameTxtField.getText());
        bookObj.setPublisherName(publisherNameTxtField.getText());
        bookObj.setBookISBN(isbnTxtField.getText());
        bookDTO.setPages(pagesTxtField.getText());
        bookDTO.setCopies(copiesTxtField.getText());
        bookDTO.setShelveId(shelveMap.get(String.valueOf(shelveComboBox.getSelectedItem())));

        return bookProcessor.processBookObject(bookDTO).isValid();
    }
}
