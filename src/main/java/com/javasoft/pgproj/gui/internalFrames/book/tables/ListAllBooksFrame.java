/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.book.tables;

import com.javasoft.pgproj.export.book.AllBooksExcelReport;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.gui.books.tableModel.AllBooksTableModel;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author ayojava
 */
public class ListAllBooksFrame extends ParentFrame {

    private JPanel southPanel;
    private JTable table;
    private AllBooksTableModel tableModel;
    private AllBooksExcelReport excelReport;

    public ListAllBooksFrame() {
        super(ResourceUtil.getString("internalFrame_book_listBook"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/List.gif")));
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, defaultExportPanel());
        cp.add(BorderLayout.CENTER, tableDisplayPanel());
        cp.add(BorderLayout.SOUTH, defaultBookListSouthPanel());
    }

    private JPanel defaultBookListSouthPanel() {
        southPanel = defaultSouthPanel();
        southPanel.add(getExitButton());
        return southPanel;
    }

    private JScrollPane tableDisplayPanel() {
        tableModel = new AllBooksTableModel();
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 400));
        table.setModel(tableModel);
        JScrollPane allTablePane = new JScrollPane();
        allTablePane.setViewportView(table);
        return allTablePane;
    }

    @Override
    public void actionPerformed(ActionEvent aev) {
        int selectedIndex = getExportOptionsComboBox().getSelectedIndex();
        if (selectedIndex == 0) {
            String info = "<html>" + ResourceUtil.getString("msg_Global_Export_Error") + "</html>";
            showMessage(info, ResourceUtil.getString("internalFrame_book_listBook"), JOptionPane.ERROR_MESSAGE);
        }
        if (aev.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("button_Export")) && selectedIndex == 1) {
            excelReport = new AllBooksExcelReport("LMS", tableModel.getBookList());
            excelReport.populateExcelSheet(false);
            String info =
                    "<html>" + ResourceUtil.getString("msg_Global_Export_Success") + "</html>";
            showMessage(info, ResourceUtil.getString("internalFrame_book_listBook"), JOptionPane.INFORMATION_MESSAGE);
        }
        excelReport.destroy();
    }
}
