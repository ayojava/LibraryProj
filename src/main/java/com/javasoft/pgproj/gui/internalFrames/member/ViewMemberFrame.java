/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.member;

import com.javasoft.pgproj.DTO.MemberDTO;
import com.javasoft.pgproj.Processor.MemberProcessor;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class ViewMemberFrame extends ParentFrame{
    
     private JPanel memberCenterPanel, southPanel, memberSearchPanel, memberCenterPanelLabel, memberCenterPanelTxtField, genderPanel;
    private JLabel regNoSearchLbl, lastNameSearchLbl;
    private JTextField regNoSearchTxtField, lastNameSearchTxtField;
    private JButton searchButton;
    private JLabel titleLabel, firstNameLabel, lastNameLabel, emailLabel, genderLabel;
    private JLabel phoneNoLabel, addressLabel, occupationLabel, address1Label, address2Label;
    private JTextField firstNameTxtField, lastNameTxtField, emailTxtField;
    private JTextField phoneNoTxtField, occupationTxtField, address1TxtField, address2TxtField, addressTxtField;
    private JComboBox titleComboBox;
    private JRadioButton maleRadioButton, femaleRadioButton;
    private ButtonGroup genderGroup;
    private MemberDTO memberDTO;

    public ViewMemberFrame() {

        super(ResourceUtil.getString("internalFrame_member_viewMember"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Edit.gif")));
        genderGroup = new ButtonGroup();
        initLabels();
        initInputFields();
        populateSearchPanel();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        JPanel northEditPanel = new JPanel();
        northEditPanel.add(memberSearchPanel);
        cp.add(BorderLayout.NORTH, northEditPanel);
        populateCentrePanel();
        cp.add(BorderLayout.CENTER, memberCenterPanel);
        cp.add(BorderLayout.SOUTH, defaultMemberSouthPanel());
    }

    private JPanel defaultMemberSouthPanel() {
        southPanel = defaultSouthPanel();
        southPanel.add(getExitButton());
        return southPanel;
    }

    private void populateCentrePanel() {
        genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        memberCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_member_viewMember_centrePanelBorder"));
        memberCenterPanelLabel = defaultCentrePanelContents(10, 1, 5, 5);

        memberCenterPanelLabel.add(titleLabel);
        memberCenterPanelLabel.add(firstNameLabel);
        memberCenterPanelLabel.add(lastNameLabel);
        memberCenterPanelLabel.add(genderLabel);
        memberCenterPanelLabel.add(emailLabel);
        memberCenterPanelLabel.add(phoneNoLabel);
        memberCenterPanelLabel.add(occupationLabel);
        memberCenterPanelLabel.add(addressLabel);
        memberCenterPanelLabel.add(address1Label);
        memberCenterPanelLabel.add(address2Label);

        memberCenterPanel.add(BorderLayout.WEST, memberCenterPanelLabel);

        memberCenterPanelTxtField = defaultCentrePanelContents(10, 1, 5, 5);
        memberCenterPanelTxtField.add(titleComboBox);
        memberCenterPanelTxtField.add(firstNameTxtField);
        memberCenterPanelTxtField.add(lastNameTxtField);
        genderPanel.add(maleRadioButton);
        genderPanel.add(femaleRadioButton);
        memberCenterPanelTxtField.add(genderPanel);
        memberCenterPanelTxtField.add(emailTxtField);
        memberCenterPanelTxtField.add(phoneNoTxtField);
        memberCenterPanelTxtField.add(occupationTxtField);
        memberCenterPanelTxtField.add(addressTxtField);
        memberCenterPanelTxtField.add(address1TxtField);
        memberCenterPanelTxtField.add(address2TxtField);
        memberCenterPanel.add(BorderLayout.EAST, memberCenterPanelTxtField);
    }

    private void populateSearchPanel() {

        memberSearchPanel = defaultSearchPanel(ResourceUtil.getString("selectItem_Global_Searchby"), 3, 2, 2, 2);
        memberSearchPanel.add(regNoSearchLbl);
        memberSearchPanel.add(lastNameSearchLbl);
        memberSearchPanel.add(regNoSearchTxtField);
        memberSearchPanel.add(lastNameSearchTxtField);
        memberSearchPanel.add(new JLabel());
        getSearchButton().addActionListener(this);
        memberSearchPanel.add(getSearchButton());
    }

    private void initLabels() {
        regNoSearchLbl = new JLabel(ResourceUtil.getString("label_member_regNo"));
        lastNameSearchLbl = new JLabel(ResourceUtil.getString("label_member_lastName"));
        titleLabel = new JLabel(ResourceUtil.getString("label_member_title"));
        titleLabel.setFont(getLabelFont());
        firstNameLabel = new JLabel(ResourceUtil.getString("label_member_firstName"));
        firstNameLabel.setFont(getLabelFont());
        lastNameLabel = new JLabel(ResourceUtil.getString("label_member_lastName"));
        lastNameLabel.setFont(getLabelFont());
        emailLabel = new JLabel(ResourceUtil.getString("label_member_emailAddress"));
        emailLabel.setFont(getLabelFont());
        genderLabel = new JLabel(ResourceUtil.getString("label_member_gender"));
        genderLabel.setFont(getLabelFont());
        phoneNoLabel = new JLabel(ResourceUtil.getString("label_member_phoneNo"));
        phoneNoLabel.setFont(getLabelFont());
        occupationLabel = new JLabel(ResourceUtil.getString("label_member_occupation"));
        occupationLabel.setFont(getLabelFont());
        addressLabel = new JLabel(ResourceUtil.getString("label_member_address"));
        addressLabel.setFont(getLabelFont());
        address1Label = new JLabel("");
        address2Label = new JLabel("");
        searchButton = new JButton(ResourceUtil.getString("button_Search"));
    }

    private void initInputFields() {

        titleComboBox = popoulateTitleComboBox();
        regNoSearchTxtField = new JTextField(20);
        regNoSearchTxtField.setFont(getFont());
        lastNameSearchTxtField = new JTextField(20);
        lastNameSearchTxtField.setFont(getFont());


        titleComboBox.setFont(getFont());
        firstNameTxtField = disableTextField();
        lastNameTxtField = disableTextField();
        emailTxtField =disableTextField();
        phoneNoTxtField =disableTextField();
        occupationTxtField =disableTextField();
        addressTxtField = disableTextField();
        address1TxtField =disableTextField();
        address2TxtField = disableTextField();

        maleRadioButton = new JRadioButton(ResourceUtil.getString("label_member_gender_Male"), false);
        femaleRadioButton = new JRadioButton(ResourceUtil.getString("label_member_gender_Female"), false);
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        maleRadioButton.setActionCommand(Constants.MALE);
        femaleRadioButton.setActionCommand(Constants.FEMALE);

    }

    private JTextField disableTextField()
    {
        JTextField txtField = new JTextField(30);
        txtField.setFont(getFont());
        txtField.setEditable(false);
        return txtField;
    }
    private JComboBox popoulateTitleComboBox() {
        titleComboBox = new JComboBox();
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Item", " Title "));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Mr"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Mrs"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Miss"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Prof"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Dr"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Other"));
        return titleComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MemberProcessor memberProcessor = new MemberProcessor();
        String actionCmd = e.getActionCommand();

        if (actionCmd.equalsIgnoreCase(ResourceUtil.getString("button_Search"))) {
            performSearch(memberProcessor);
        }
    }

    private void performSearch(MemberProcessor memberProcessor) {

        if (StringUtils.isBlank(regNoSearchTxtField.getText()) && StringUtils.isBlank(lastNameSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("menuItem_member_viewMember_Error"),
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
            resetToDefault(memberCenterPanelTxtField);
            
            if (memberDTO.getUserIdentity() != null) {
                titleComboBox.setSelectedItem(memberDTO.getUserIdentity().getTitle());
                firstNameTxtField.setText(memberDTO.getUserIdentity().getFirstName());
                lastNameTxtField.setText(memberDTO.getUserIdentity().getLastName());
                emailTxtField.setText(memberDTO.getUserIdentity().getEmailAddress());
                phoneNoTxtField.setText(memberDTO.getUserIdentity().getPhoneNumber());
                occupationTxtField.setText(memberDTO.getUserIdentity().getOccupation());
                
                if (memberDTO.getUserIdentity().getGender().equalsIgnoreCase(Constants.MALE)) {
                    maleRadioButton.setSelected(true);
                } 
                else {
                    femaleRadioButton.setSelected(true);
                }
                
                String houseAddress = memberDTO.getUserIdentity().getHouseAddress();
                String[] addressVal= (houseAddress!= null && !houseAddress.isEmpty()) ?houseAddress.split(Constants.SUBSTRING_XTER) : null;
                
                if(addressVal != null){
                    int length = addressVal.length;
                    
                    if (length > 0 || length==0){
                        addressTxtField.setText(addressVal[0]);
                    }
                    if (length > 1 ){
                        address1TxtField.setText(addressVal[1]);
                    }
                    if (length > 2 ){
                        address2TxtField.setText(addressVal[2]);
                    }
                }

            } else {
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);
                
            }
        }


    }
}
