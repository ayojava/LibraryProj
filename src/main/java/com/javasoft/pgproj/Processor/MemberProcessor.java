/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.Processor;

import com.javasoft.pgproj.DTO.MemberDTO;
import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.util.ResourceUtil;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author ayojava
 */
public class MemberProcessor extends BaseProcessor{

    public MemberDTO processMemberObject(MemberDTO memberDTO,boolean isNew){
    
        if(memberDTO == null || memberDTO.getUserIdentity() == null){
            setUserDTO(new MemberDTO());
            queueUserError("Error ",ResourceUtil.getString("msg_Global_Error"));
            return getUserDTO();
        }
        setUserDTO(memberDTO);
        getUserDTO().setValid(true);
        Member identity = memberDTO.getUserIdentity();
        validateTitle(identity.getTitle(),identity.getFirstName(),identity.getLastName(),identity.getGender(),isNew? "":identity.getRegNo());
        validateEmail(identity.getEmailAddress());
        validatePhoneNumber(identity.getPhoneNumber());
        validateOccupation(identity.getOccupation());
        validateAddress(identity.getHouseAddress());
        return getUserDTO();
    }
    
    private void validateTitle(String title,String firstName, String lastName, String gender,String regNo)
    {
        if(title.equalsIgnoreCase(ResourceUtil.getString("selectItem_Global_Item"," Title "))){
             queueUserError(ResourceUtil.getString("label_member_title"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
        
        if(StringUtils.isBlank(firstName)){
            queueUserError(ResourceUtil.getString("label_member_firstName"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
        
        if(StringUtils.isBlank(lastName)){
            queueUserError(ResourceUtil.getString("label_member_firstName"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
        
        if(StringUtils.isBlank(gender)){
            queueUserError(ResourceUtil.getString("label_member_gender"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
        
        if(StringUtils.isNotBlank(firstName) && StringUtils.isNotBlank(lastName) && StringUtils.isNotBlank(gender) && StringUtils.isNotBlank(regNo) ){
            //search the database
            if(getUserIdentityDAO().confirmUser(firstName, lastName, gender,regNo)){
               queueUserError(ResourceUtil.getString("label_member_fullName"),ResourceUtil.getString("menuItem_member_addMember_MemberNameError")); 
            }
        }
    }
       
    private void queueUserError(String label, String errorMsg)
    {
        getErrorUtil().queue(label, errorMsg);
        getUserDTO().setValid(false);
    }
    
    @Override
    public void destroy() {
        setUserDTO(null);
    }

    private void validateEmail(String emailAddress) {
        if(!validateEmailAddress(emailAddress)){
             queueUserError(ResourceUtil.getString("label_member_emailAddress"),ResourceUtil.getString("menuItem_member_addMember_EmailError"));
        }
    }

    private void validatePhoneNumber(String phoneNumber) {
        if(!validatePhoneNo(phoneNumber)){
           queueUserError(ResourceUtil.getString("label_member_phoneNo"),ResourceUtil.getString("menuItem_member_addMember_phoneNoError")); 
        }
        
    }

    private void validateOccupation(String occupation) {
        if(StringUtils.isBlank(occupation)){
            queueUserError(ResourceUtil.getString("label_member_occupation"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
    }

    private void validateAddress(String houseAddress) {
        String []address = houseAddress.split(Constants.SUBSTRING_XTER);
        boolean isAllBlank= true;
        for(int i= 0; i < address.length ; i ++){
            isAllBlank = StringUtils.isNotBlank(address[i]) ? false : true;
        }
        if(isAllBlank){
            queueUserError(ResourceUtil.getString("label_member_address"),ResourceUtil.getString("msg_Global_requiredMsg"));
        }
    }
    
    public boolean persistMember(MemberDTO memberDTO){
        return getUserIdentityDAO().persistMember(memberDTO.getUserIdentity(),memberDTO.getUserAccount());
    }
    
     public Member findMember(Map<String,Object> qryParams){
         return getUserIdentityDAO().findMember(qryParams);
     }
     
     public boolean updateMember(MemberDTO memberDTO){
         return getUserIdentityDAO().updateMember(memberDTO.getUserIdentity());
     }
}
