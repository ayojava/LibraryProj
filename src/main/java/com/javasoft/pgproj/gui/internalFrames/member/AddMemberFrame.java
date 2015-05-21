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
import javax.swing.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.WordUtils;

/**
 *
 * @author ayojava
 */
public class AddMemberFrame extends ParentFrame {

    private JPanel memberCenterPanel,southPanel,memberCenterPanelLabel,memberCenterPanelTxtField, genderPanel;
    
    private JLabel titleLabel , firstNameLabel,lastNameLabel,emailLabel, genderLabel;
    
    private JLabel phoneNoLabel,addressLabel,occupationLabel, address1Label, address2Label;
    
    private JTextField firstNameTxtField ,lastNameTxtField , emailTxtField;
    
    private JTextField phoneNoTxtField,occupationTxtField, address1TxtField, address2TxtField, addressTxtField;
        
    private JComboBox titleComboBox;  
    
    private JRadioButton maleRadioButton, femaleRadioButton;
    
    private ButtonGroup genderGroup ;
    
    private MemberDTO memberDTO;
            
    public AddMemberFrame() {
        
        super(ResourceUtil.getString("internalFrame_member_addMember"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/Add.gif")));
        genderGroup = new ButtonGroup();
        initLabels();
        initInputFields();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        
        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, defaultHeaderPanel(ResourceUtil.getString("internalFrame_member_addMember_topPanel")));
        populateCentrePanel();
        cp.add(BorderLayout.CENTER,memberCenterPanel);
        cp.add(BorderLayout.SOUTH, defaultMemberSouthPanel());
    }
    
    private void populateCentrePanel()
    {
        genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        memberCenterPanel = defaultCentrePanel(ResourceUtil.getString("internalFrame_member_addMember_centrePanelBorder")); 
        memberCenterPanelLabel= defaultCentrePanelContents(10,1,5,5);
        
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
        
        memberCenterPanelTxtField= defaultCentrePanelContents(10,1,5,5);
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
    
    private void initLabels() {
        
        titleLabel= new JLabel(ResourceUtil.getString("label_member_title"));
        titleLabel.setFont(getLabelFont());
        firstNameLabel= new JLabel(ResourceUtil.getString("label_member_firstName"));
        firstNameLabel.setFont(getLabelFont());
        lastNameLabel= new JLabel(ResourceUtil.getString("label_member_lastName"));
        lastNameLabel.setFont(getLabelFont());
        emailLabel= new JLabel(ResourceUtil.getString("label_member_emailAddress"));
        emailLabel.setFont(getLabelFont());
        genderLabel= new JLabel(ResourceUtil.getString("label_member_gender"));
        genderLabel.setFont(getLabelFont());
        phoneNoLabel= new JLabel(ResourceUtil.getString("label_member_phoneNo"));
        phoneNoLabel.setFont(getLabelFont());
        occupationLabel= new JLabel(ResourceUtil.getString("label_member_occupation"));
        occupationLabel.setFont(getLabelFont());
        addressLabel= new JLabel(ResourceUtil.getString("label_member_address"));
        addressLabel.setFont(getLabelFont()); 
        address1Label= new JLabel("");
        address2Label= new JLabel("");
    }
    
    private void initInputFields(){
        
        titleComboBox = popoulateTitleComboBox();
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
        addressTxtField= new JTextField(30);
        addressTxtField.setFont(getFont());
        address1TxtField= new JTextField(30);
        address1TxtField.setFont(getFont());
        address2TxtField= new JTextField(30);
        address2TxtField.setFont(getFont());
        
        maleRadioButton = new JRadioButton(ResourceUtil.getString("label_member_gender_Male"),false);
        femaleRadioButton= new JRadioButton(ResourceUtil.getString("label_member_gender_Female"),false);
        genderGroup.add(maleRadioButton);
        genderGroup.add(femaleRadioButton);
        maleRadioButton.setActionCommand(Constants.MALE);
        femaleRadioButton.setActionCommand(Constants.FEMALE);
        
    }
    
    private JComboBox popoulateTitleComboBox(){
        titleComboBox = new JComboBox();
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Item"," Title ")); 
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Mr"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Mrs"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Miss"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Prof"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Dr"));
        titleComboBox.addItem(ResourceUtil.getString("selectItem_Global_Title_Other"));
        return titleComboBox;
    }
    
    private JPanel defaultMemberSouthPanel (){
        southPanel = defaultSouthPanel();
        getSaveButton().setText(ResourceUtil.getString("button_member_AddMember")); // Add ICon
        getSaveButton().setIcon(new ImageIcon(ClassLoader.getSystemResource("images/buttons/add.png")));
        getSaveButton().addActionListener(this);
        southPanel.add(getSaveButton());  
        southPanel.add(getExitButton());
        return southPanel;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {      
        memberDTO = new  MemberDTO();
        MemberProcessor memberProcessor = new MemberProcessor();
        
        if(validateMember(memberProcessor)){
            
            if(memberProcessor.persistMember(memberDTO)){
            String info = "<html>" + ResourceUtil.getString("msg_Global_AddOperation_Success",
                        new Object[]{" Member [ " + WordUtils.capitalize(memberDTO.getUserIdentity().returnFullName()) + " ] "}) + "</html>";
                showMessage(info, ResourceUtil.getString("menuItem_book_addMember_Success"), JOptionPane.INFORMATION_MESSAGE);
                //
                /*send email
                MailUtil mailUtil = new MailUtil();
                mailUtil.sendMemberRegistrationDetails(memberDTO.getUserIdentity(),false);
                */
            }
            else{
                String info = "<html>" + ResourceUtil.getString("msg_Global_Operation_Error",new Object[]{"save <br/><br/>"}) + "</html>";
                showMessage(info, ResourceUtil.getString("menuItem_member_addMember_Error"), JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            showMessage(memberProcessor.buildErrorMessage(), ResourceUtil.getString("menuItem_member_addMember_Error"), JOptionPane.ERROR_MESSAGE);
        }
        resetToDefault(memberCenterPanelTxtField);  
        memberProcessor.destroy();
    }
    
    private boolean validateMember(MemberProcessor memberProcessor) {
        Member userIdentity = memberDTO.getUserIdentity();
        userIdentity.setTitle(String.valueOf(titleComboBox.getSelectedItem()));
        userIdentity.setFirstName(firstNameTxtField.getText());
        userIdentity.setLastName(lastNameTxtField.getText());
        userIdentity.setEmailAddress(emailTxtField.getText());
        userIdentity.setPhoneNumber(phoneNoTxtField.getText());
        userIdentity.setOccupation(occupationTxtField.getText());
        userIdentity.setHouseAddress(addressTxtField.getText() + Constants.SUBSTRING_XTER + address1TxtField.getText() + Constants.SUBSTRING_XTER + address2TxtField.getText());
        userIdentity.setGender(genderGroup.getSelection() != null ? genderGroup.getSelection().getActionCommand() : "");
        userIdentity.setRegNo(userIdentity.getFirstName().isEmpty() ? "" : "LIB" + RandomStringUtils.random(3, false, true));
        return memberProcessor.processMemberObject(memberDTO, true).isValid();
    }
}
