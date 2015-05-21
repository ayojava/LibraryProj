/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.member.tables;

import com.javasoft.pgproj.export.member.AllMembersExcelReport;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.gui.members.tableModel.AllMembersTableModel;
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
public class ListAllMembersFrame extends ParentFrame {

    private JPanel southPanel;
    private JTable table;
    private AllMembersTableModel tableModel;
    private AllMembersExcelReport excelReport;

    public ListAllMembersFrame() {
        super(ResourceUtil.getString("internalFrame_member_listMember"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/List.gif")));
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, defaultExportPanel());
        cp.add(BorderLayout.CENTER, tableDisplayPanel());
        cp.add(BorderLayout.SOUTH, defaultMemberListSouthPanel());
    }
    
     private JPanel defaultMemberListSouthPanel ()
    {
        southPanel = defaultSouthPanel();  
        southPanel.add(getExitButton());
        return southPanel;
    }
    
    private JScrollPane tableDisplayPanel(){
        tableModel = new AllMembersTableModel();
        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(850, 400));
        table.setModel(tableModel);
        table.setColumnSelectionAllowed(false);
        table.setRowSelectionAllowed(true);
        JScrollPane allTablePane = new JScrollPane ();
        allTablePane.setViewportView(table);
        return allTablePane;
    }
    
    @Override
    public void actionPerformed(ActionEvent aev) {
        int selectedIndex = getExportOptionsComboBox().getSelectedIndex();
        if(selectedIndex == 0){
            String info = "<html>" + ResourceUtil.getString("msg_Global_Export_Error") + "</html>";
            showMessage(info, ResourceUtil.getString("internalFrame_member_listMember"), JOptionPane.ERROR_MESSAGE);
        }
        if(aev.getActionCommand().equalsIgnoreCase(ResourceUtil.getString("button_Export")) && selectedIndex==1){  
            excelReport = new AllMembersExcelReport("LMS", tableModel.getMemberList());
            excelReport.populateExcelSheet(false);
            String info = 
                        "<html>" + ResourceUtil.getString("msg_Global_Export_Success") + "</html>";
                    showMessage(info, ResourceUtil.getString("internalFrame_member_listMember"), JOptionPane.INFORMATION_MESSAGE);
        }
        excelReport.destroy();
    }
}
