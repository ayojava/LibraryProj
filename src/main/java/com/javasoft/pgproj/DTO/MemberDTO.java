/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DTO;

import com.javasoft.pgproj.entity.MemberAccount;
import com.javasoft.pgproj.entity.Member;

/**
 *
 * @author ayojava
 */
public class MemberDTO extends BaseDTO{   
    private Member userIdentity;
    private MemberAccount userAccount;
    public MemberDTO() {
        userIdentity = new Member();
        userAccount = new MemberAccount();
    }
    public MemberAccount getUserAccount() {
        return userAccount;
    }
    public Member getUserIdentity() {
        return userIdentity;
    }
    public void setUserAccount(MemberAccount userAccount) {
        this.userAccount = userAccount;
    }
    public void setUserIdentity(Member userIdentity) {
        this.userIdentity = userIdentity;
    }  
}
