/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.entity.DAO;

import com.javasoft.pgproj.DAO.DAOImpl;
import com.javasoft.pgproj.entity.*;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.lms.LibraryService;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class MemberDAO<T extends Member> extends DAOImpl<T>  {
    
    public MemberDAO() 
    {
        super(Member.class);
    }

    public MemberDAO(EntityManager em, boolean extended) {
        super(Member.class, em, extended);
    }

    protected MemberDAO(Class clazz) {
        super(clazz);
    }

    protected MemberDAO(Class clazz, EntityManager em, boolean extended) {
        super(clazz, em, extended);
    }
    
    public boolean confirmUser(String firstName, String lastName, String gender,String regNo){
        String namedQry="UserIdentity.findUser_name_gender";
        Map<String, Object> qryMap = new HashMap<String, Object>();
        qryMap.put("firstName", firstName);
        qryMap.put("lastName", lastName);
        qryMap.put("gender", gender);
        
        if(StringUtils.isNotBlank(regNo)){
            qryMap.put("regNo", regNo);
            namedQry="UserIdentity.findUser_name_gender_regNo";
        }
        
        Long size = (Long) getEntityCount(namedQry,qryMap);
        return (size > 0 )? true : false;
    }
    public boolean updateMember(Member userIdentity){
        boolean outcome= true;
        EntityTransaction transaction = null;
        Member systemUser = LibraryService.getInstance().getSystemUser();
        
        try{
            em = getEntityManager();
            transaction = em.getTransaction();
            transaction.begin(); 
     
            Member memberObj = (Member)em.createNamedQuery("Member.findMemberByRegNo").setParameter("regNo", userIdentity.getRegNo()).getSingleResult();
//            EntityState es = EntityState.newInstance(systemUser, memberObj.getUserAccount());
//            es.setNew();
//            memberObj.getUserAccount().setEntityState(es);
            memberObj.cloneMember(userIdentity);
            System.out.println("New Name :::: " + memberObj.getFirstName());
            em.merge(memberObj);
            //em.refresh(memberObj);
            saveActivityLog(memberObj, String.valueOf(memberObj.getUserId()), ActivityLog.EDIT_MEMBER,systemUser, em); 
            transaction.commit();
        }
        catch(Exception ex){
            if(transaction != null){
                 transaction.rollback();
            }
            outcome= false;
            System.out.println("Exception  ::: " + ex);
        }
        finally{
            close();
        }
        return outcome;    
    }
    
    public boolean persistMember(Member userIdentity, MemberAccount userAccount){
       
        boolean outcome= true;
        EntityTransaction transaction = null;
        Member systemUser = LibraryService.getInstance().getSystemUser();
        try{
            em = getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            
            EntityState es = EntityState.newInstance(systemUser, userAccount);
            es.setNew();
            userAccount.setEntityState(es);
            userAccount.setFirstAccessedDate(new Date());
            userAccount.setSysAccount(Constants.GLOBAL_NO);
            userAccount.setSysAdmin(Constants.GLOBAL_NO);
            userIdentity.setUserAccount(userAccount);
            em.persist(userIdentity);
            em.refresh(userIdentity);
            saveActivityLog(userIdentity, String.valueOf(userIdentity.getUserId()), ActivityLog.REGISTER_MEMBER,systemUser, em); 
            transaction.commit();
        }
        catch(Exception ex){
            if(transaction != null){
                 transaction.rollback();
            }
            outcome= false;
            System.out.println("Exception  ::: " + ex);
        }
        finally{
            close();
        }
        return outcome;       
    }
    
    public Member findMember(Map<String,Object> qryParams){
        
        String qryString =QueryHandler.Member_findMember +  buildQuery(qryParams) + " AND o.userAccount.sysAccount !='" + Constants.GLOBAL_YES+"'";
        Query qry = getEntityManager().createQuery(qryString);
        return (Member)qry.getSingleResult();
    }
    
    
}
