/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames;

import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author ayojava
 */
public class ParentFrame extends JInternalFrame implements ActionListener {

    private JPanel northPanel;
    private JPanel centerPanel, centrePanelContents;
    private JPanel searchPanel;
    private JPanel southPanel, exportPanel;
    private JButton exitButton, saveButton, printButton, exportButton, searchButton,searchButton_;
    private JComboBox exportOptionsComboBox;
    private Font labelFont;
    private Font textFieldFont;
    private LayoutManager manager;

    protected ParentFrame(String title) {
        super(title, true, true, false, true);
        initDefaults();
    }

    protected ParentFrame(String title, boolean resizable, boolean closable, boolean maximizable, boolean iconifiable) {
        super(title, resizable, closable, maximizable, iconifiable);
        initDefaults();
    }

    private void initDefaults() {
        labelFont = new Font("Tahoma", Font.BOLD, 11);

        textFieldFont = new Font("Tahoma", Font.PLAIN, 11);
        exitButton = new JButton(ResourceUtil.getString("button_Exit"));// add an exit icon
        exitButton.setFont(labelFont);
        exitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        saveButton = new JButton(); //Gives a default overridable button
        searchButton = new JButton(ResourceUtil.getString("button_Search"),
                new ImageIcon(ClassLoader.getSystemResource("images/buttons/search.gif")));
        searchButton_ = new JButton(ResourceUtil.getString("button_Search"),
                new ImageIcon(ClassLoader.getSystemResource("images/buttons/search.gif")));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public JButton getSearchButton_() {
        return searchButton_;
    }

    public void setSearchButton_(JButton searchButton_) {
        this.searchButton_ = searchButton_;
    }

    
    protected JPanel defaultHeaderPanel(String displayInfo) {
        Font headerFont = new Font("Tahoma", Font.BOLD, 14);
        manager = new FlowLayout(FlowLayout.CENTER);
        northPanel = new JPanel();
        northPanel.setLayout(manager);
        JLabel northLabel = new JLabel(displayInfo);
        northLabel.setFont(headerFont);
        northPanel.add(northLabel);
        return northPanel;
    }

    protected JPanel defaultCentrePanel(String borderTitle) {
        manager = new BorderLayout(2, 2);
        centerPanel = new JPanel(manager);
        centerPanel.setBorder(BorderFactory.createTitledBorder(borderTitle));
        return centerPanel;
    }

    protected JPanel defaultCentrePanelContents(int rows, int cols, int hgap, int vgap) {
        centrePanelContents = new JPanel();
        centrePanelContents.setLayout(new GridLayout(rows, cols, hgap, vgap));
        return centrePanelContents;
    }

    protected JPanel defaultSouthPanel() {
        // Font southPanelFont = new Font("Tahoma", Font.BOLD, 11);
        manager = new FlowLayout(FlowLayout.RIGHT);
        southPanel = new JPanel(manager);
        southPanel.setBorder(BorderFactory.createEtchedBorder());
        //southPanel.add(exitButton);
        return southPanel;
    }

    protected JPanel defaultSearchPanel(String borderTitle, int rows, int cols, int hgap, int vgap) {
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(rows, cols, hgap, vgap));
        searchPanel.setBorder(BorderFactory.createTitledBorder(borderTitle));
        return searchPanel;
    }

    protected JPanel defaultExportPanel() {
        manager = new FlowLayout(FlowLayout.RIGHT, 6, 6);
        exportPanel = new JPanel(manager);
        exportPanel.setBorder(BorderFactory.createEtchedBorder());

        printButton = new JButton(ResourceUtil.getString("button_Print"), 
                new ImageIcon(ClassLoader.getSystemResource("images/buttons/print.gif")));
        exportOptionsComboBox = populateExportOptions();
        exportButton = new JButton(ResourceUtil.getString("button_Export"));// Add icon
        
        exportButton.addActionListener(this);
        printButton.addActionListener(this);

        exportPanel.add(exportOptionsComboBox);
        exportPanel.add(exportButton);
        //exportPanel.add(printButton);
        return exportPanel;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public Font getTextFieldFont() {
        return textFieldFont;
    }

    public LayoutManager getManager() {
        return manager;
    }

    public void setManager(LayoutManager manager) {
        this.manager = manager;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void setSearchButton(JButton searchButton) {
        this.searchButton = searchButton;
    }

    
    private JComboBox populateExportOptions() {
        exportOptionsComboBox = new JComboBox();
        exportOptionsComboBox.addItem(ResourceUtil.getString("selectItem_Global_Item", "Export Option"));
        exportOptionsComboBox.addItem(ResourceUtil.getString("selectItem_Global_excelItem"));
        //exportOptionsComboBox.addItem(ResourceUtil.getString("selectItem_Global_pdfItem"));
        return exportOptionsComboBox;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    protected void showMessage(String message, String title, int msgType) {
        JLabel msglabel = new JLabel(message);
        msglabel.setFont(new Font("serif", Font.PLAIN, 14));
        JOptionPane.showMessageDialog(this, msglabel, title, msgType);
    }

    protected void resetToDefault(JPanel panel) {
        Component[] components = panel.getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JTextField) {
                ((JTextField) components[i]).setText(null);
            } 
            else if (components[i] instanceof JComboBox) {
                ((JComboBox) components[i]).setSelectedIndex(0);
            }
            else if (components[i] instanceof JRadioButton){
                ((JRadioButton) components[i]).setSelected(false);
            }
//            else if (components[i] instanceof JLabel){
//                ((JLabel) components[i]).setText(null);
//            }
        }
    }

    public JButton getExportButton() {
        return exportButton;
    }

    public JButton getPrintButton() {
        return printButton;
    }

    public JComboBox getExportOptionsComboBox() {
        return exportOptionsComboBox;
    }
    
    
}
