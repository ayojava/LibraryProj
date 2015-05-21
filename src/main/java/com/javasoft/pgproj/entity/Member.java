/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity;

import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.util.PasswordUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author ayojava
 */
@Entity
@NamedQueries({
    @NamedQuery(name ="UserIdentity.findSystemUser",
        query="SELECT u From Member u where u.userAccount.sysAdmin=:sysAdmin AND u.userAccount.sysAccount=:sysAccount"),
    
    @NamedQuery(name ="UserIdentity.findUser_name_gender",
        query="SELECT COUNT(u) From Member u where u.firstName=:firstName AND u.lastName=:lastName AND u.gender=:gender"),
    
    @NamedQuery(name ="UserIdentity.findUser_name_gender_regNo",
        
        query="SELECT COUNT(u) From Member u where u.firstName=:firstName AND u.lastName=:lastName AND u.gender=:gender AND "
                + " u.regNo !=:regNo"),
    
    @NamedQuery(name ="Member.findAllMembersCount",
        query="SELECT COUNT(u) From Member u where u.userAccount.sysAccount !='" + Constants.GLOBAL_YES+"'"),
    
    @NamedQuery(name ="Member.findAllMembers", query="SELECT u From Member u where u.userAccount.sysAccount !='" + Constants.GLOBAL_YES+"'"),
    
    @NamedQuery(name ="Member.findMemberByRegNo", query="SELECT u From Member u where u.regNo=:regNo"),
    
})

public class Member implements Serializable 
{
        
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    
    private String title;
    
    private String firstName;
    
    private String lastName;
    
    private String password;
       
    private String regNo;
   
    private String emailAddress;
    
    private String gender;
    
    private String phoneNumber;
    
    @Column(length=500)
    private String houseAddress;
    
    private String occupation;
    
    @OneToMany(mappedBy = "userIdentity",cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    private List<ActivityLog> activityLogs;
    
    @OneToMany(mappedBy="createdBy")
    private List<EntityState> createdEntityStateList;
    
    @OneToOne(fetch= FetchType.LAZY,cascade= CascadeType.ALL)
    private MemberAccount userAccount;
    
    public String returnFullName(){
        return title + " " + firstName + " " + lastName ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    
   
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<ActivityLog> getActivityLogs() {
        return activityLogs;
    }

    public void setActivityLogs(List<ActivityLog> activityLogs) {
        this.activityLogs = activityLogs;
    }

    public List<EntityState> getCreatedEntityStateList() {
        return createdEntityStateList;
    }

    public void setCreatedEntityStateList(List<EntityState> createdEntityStateList) {
        this.createdEntityStateList = createdEntityStateList;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
      

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

   

    public MemberAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(MemberAccount userAccount) {
        this.userAccount = userAccount;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    public void cloneMember(Member memberObj){
        setEmailAddress(memberObj.getEmailAddress());
        setFirstName(memberObj.getFirstName());
        setGender(memberObj.getGender());
        setHouseAddress(memberObj.getHouseAddress());
        setLastName(memberObj.getLastName());
        setOccupation(memberObj.getOccupation());
        setPhoneNumber(memberObj.getPhoneNumber());
        setTitle(memberObj.getTitle());
    
    }
    
    @Override
    public String toString() {
        return "com.javasoft.pgproj.entity.UserIdentity[ id=" + userId + " ]";
    }
    
    @Transient
    private String plainPassword;

    public String getPlainPassword() 
    {
        return PasswordUtil.decrypt(getPassword());
    }

    public void setPlainPassword(String plainPassword) 
    {
        //this.plainPassword = plainPassword;
        setPassword(PasswordUtil.encrypt(plainPassword));
    }
    
    
}
