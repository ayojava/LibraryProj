/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.book.tables;

import com.javasoft.pgproj.gui.books.tableModel.AllBooksTableModel;
import com.javasoft.pgproj.gui.books.tableModel.AvailableBooksTableModel;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author ayojava
 */
public class ListAvailableBooksFrame extends ParentFrame{
    private JPanel southPanel;
    
    private JTable table;
    
    private AvailableBooksTableModel tableModel;
    
    //private AllBooksExcelReport excelReport;
        
    public ListAvailableBooksFrame()
    {
        super(ResourceUtil.getString("internalFrame_book_listAvailableBook"),false,true,false,true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/List.gif")));
        initComponents();
        setVisible(true);
        pack();
    }
      
    private void initComponents() 
    {
        Container cp = getContentPane();  
        //cp.add(BorderLayout.NORTH,defaultExportPanel() );
        cp.add(BorderLayout.CENTER,tableDisplayPanel() );
        cp.add(BorderLayout.SOUTH, defaultBookListSouthPanel());
    }
    
    private JPanel defaultBookListSouthPanel ()
    {
        southPanel = defaultSouthPanel();  
        southPanel.add(getExitButton());
        return southPanel;
    }
    
    private JScrollPane tableDisplayPanel(){
        tableModel = new AvailableBooksTableModel();
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 400));
        table.setModel(tableModel);
        JScrollPane allTablePane = new JScrollPane ();
        allTablePane.setViewportView(table);
        return allTablePane;
    }
}
