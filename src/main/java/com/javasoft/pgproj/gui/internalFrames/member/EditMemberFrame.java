/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.member;

import com.javasoft.pgproj.DTO.MemberDTO;
import com.javasoft.pgproj.Processor.MemberProcessor;
import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.util.MailUtil;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
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
public class EditMemberFrame extends ParentFrame {

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
    private String regNo;

    public EditMemberFrame() {

        super(ResourceUtil.getString("internalFrame_member_editMember"), false, true, false, true);
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
        getSaveButton().setText(ResourceUtil.getString("button_member_UpdateMember")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/edit.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());
        southPanel.add(getExitButton());
        return southPanel;
    }

    private void populateCentrePanel() {
        genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        memberCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_member_editMember_centrePanelBorder"));
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
        regNoSearchLbl.setFont(getLabelFont());
        lastNameSearchLbl = new JLabel(ResourceUtil.getString("label_member_lastName"));
        lastNameSearchLbl.setFont(getLabelFont());

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
        firstNameTxtField = new JTextField(30);
        firstNameTxtField.setFont(getFont());
        
        lastNameTxtField = new JTextField(30);
        lastNameTxtField.setFont(getFont());
        emailTxtField = new JTextField(30);
        emailTxtField.setFont(getFont());
        phoneNoTxtField = new JTextField(30);
        phoneNoTxtField.setFont(getFont());
        occupationTxtField = new JTextField(30);
        occupationTxtField.setFont(getFont());
        addressTxtField = new JTextField(30);
        addressTxtField.setFont(getFont());
        address1TxtField = new JTextField(30);
        address1TxtField.setFont(getFont());
        address2TxtField = new JTextField(30);
        address2TxtField.setFont(getFont());

        maleRadioButton = new JRadioButton(ResourceUtil.getString("label_member_gender_Male"), false);
        femaleRadioButton = new JRadioButton(ResourceUtil.getString("label_member_gender_Female"), false);
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        maleRadioButton.setActionCommand(Constants.MALE);
        femaleRadioButton.setActionCommand(Constants.FEMALE);
        /*
        firstNameTxtField.setEditable(false);
        lastNameTxtField.setEditable(false);
        titleComboBox.setEnabled(false);
        maleRadioButton.setEnabled(false);
        femaleRadioButton.setEnabled(false);
        */
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
        } else {
            if (validateMember(memberProcessor)) {
                if (memberProcessor.updateMember(memberDTO)) {
                    String info = "<html>" + ResourceUtil.getString("msg_Global_EditOperation_Success",
                            new Object[]{" Member [ " + WordUtils.capitalize(memberDTO.getUserIdentity().returnFullName()) + " ] "}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_member_editMember_Success"), JOptionPane.INFORMATION_MESSAGE);
                    //send email
                    //MailUtil mailUtil = new MailUtil();
                    //mailUtil.sendMemberRegistrationDetails(memberDTO.getUserIdentity(),true);
                } else {
                    String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error", new Object[]{"save <br/><br/>"}) + "</html>";
                    showMessage(info, ResourceUtil.getString("menuItem_member_editMember_Error"), JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        resetToDefault(memberCenterPanelTxtField);   
        resetToDefault(memberSearchPanel);
    }

    private boolean validateMember(MemberProcessor memberProcessor) {
        try{
        Member userIdentity = memberDTO.getUserIdentity();
        userIdentity.setTitle(String.valueOf(titleComboBox.getSelectedItem()));
        userIdentity.setFirstName(firstNameTxtField.getText());
        userIdentity.setLastName(lastNameTxtField.getText());
        userIdentity.setEmailAddress(emailTxtField.getText());
        userIdentity.setPhoneNumber(phoneNoTxtField.getText());
        userIdentity.setOccupation(occupationTxtField.getText());
        userIdentity.setHouseAddress(addressTxtField.getText() + Constants.SUBSTRING_XTER + address1TxtField.getText() + Constants.SUBSTRING_XTER + address2TxtField.getText());
        userIdentity.setGender(genderGroup.getSelection() != null ? genderGroup.getSelection().getActionCommand() : "");
        userIdentity.setRegNo(regNo);
        return memberProcessor.processMemberObject(memberDTO, false).isValid();
        }
        catch(Exception ex){
            return false;
        }
    }

    private void performSearch(MemberProcessor memberProcessor) {

        if (StringUtils.isBlank(regNoSearchTxtField.getText()) || StringUtils.isBlank(lastNameSearchTxtField.getText())) {
            showMessage(ResourceUtil.getString("msg_Global_Search_Error"), ResourceUtil.getString("menuItem_member_editMember_Error"),
                    JOptionPane.ERROR_MESSAGE);
        } else {

            Map<String, Object> qryParams = new HashMap<>();
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
                System.out.println("=========================== Found a Member =========================");
                System.out.println("Title ::: " + memberDTO.getUserIdentity().getTitle());
                System.out.println("First Name ::: " + memberDTO.getUserIdentity().getFirstName());
                System.out.println("Last Name ::: " + memberDTO.getUserIdentity().getLastName());
                System.out.println("Email ::: " + memberDTO.getUserIdentity().getEmailAddress());
                System.out.println("Phone No ::: " + memberDTO.getUserIdentity().getPhoneNumber());
                System.out.println("Occupation ::: " + memberDTO.getUserIdentity().getOccupation());
                System.out.println("Gender ::: " + memberDTO.getUserIdentity().getGender());
                System.out.println("Mobile No ::: " + memberDTO.getUserIdentity().getPhoneNumber());
                System.out.println("Address ::: " + memberDTO.getUserIdentity().getHouseAddress());
                
                regNo = memberDTO.getUserIdentity().getRegNo();
                titleComboBox.setSelectedItem(memberDTO.getUserIdentity().getTitle());
                firstNameTxtField.setText(memberDTO.getUserIdentity().getFirstName());
                lastNameTxtField.setText(memberDTO.getUserIdentity().getLastName());
                emailTxtField.setText(memberDTO.getUserIdentity().getEmailAddress());
                phoneNoTxtField.setText(memberDTO.getUserIdentity().getPhoneNumber());
                occupationTxtField.setText(memberDTO.getUserIdentity().getOccupation());

                if (memberDTO.getUserIdentity().getGender().equalsIgnoreCase(Constants.MALE)) {
                    maleRadioButton.setSelected(true);
                } else {
                    femaleRadioButton.setSelected(true);
                }

                String houseAddress = memberDTO.getUserIdentity().getHouseAddress();
                String[] addressVal = (houseAddress != null && !houseAddress.isEmpty()) ? houseAddress.split(Constants.SUBSTRING_XTER) : null;

                if (addressVal != null) {
                    int length = addressVal.length;

                    if (length > 0 || length == 0) {
                        addressTxtField.setText(addressVal[0]);
                    }
                    if (length > 1) {
                        address1TxtField.setText(addressVal[1]);
                    }
                    if (length > 2) {
                        address2TxtField.setText(addressVal[2]);
                    }
                }

            } else {
                System.out.println("=========================== Found No  Member =========================");
                showMessage(ResourceUtil.getString("msg_Global_NoRecords_Error"), ResourceUtil.getString("msg_Global_Error"), JOptionPane.ERROR_MESSAGE);

            }
        }


    }
}
