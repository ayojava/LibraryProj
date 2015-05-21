/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.members.tableModel;

import com.javasoft.pgproj.Processor.BaseProcessor;
import com.javasoft.pgproj.Processor.MemberProcessor;
import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.interfaces.Constants;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.AbstractTableModel;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 *
 * @author ayojava
 */
public class AllMembersTableModel extends AbstractTableModel{
    
    private int startPosition;
    private int counter = 0;
    private BaseProcessor processor;
    private EntityManager manager;
    private String headerList[] = {"S/N","FullName", "Reg No", "Email Address", "Gender",  "Phone No","Occupation","Registered Date"};
    private List<Member> memberList;
    
    public AllMembersTableModel(){
        processor = new MemberProcessor();
        startPosition = 0;
        manager = processor.getUserIdentityDAO().getEntityManager();//
        memberList= getMembers(startPosition, startPosition + 100);
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
         if ((rowIndex >= startPosition) && (rowIndex < (startPosition + 100))) {
            
        } else {
           memberList= getMembers(rowIndex, rowIndex + 100); 
           startPosition = rowIndex;
        }
         
        Member member = memberList.get(rowIndex - startPosition);
        Object columnValue = null;
        
        switch(columnIndex){
            case 0:
                columnValue =rowIndex+1;
                break;
            case 1:
                columnValue =member.returnFullName().toUpperCase();
                break;
            case 2:
                columnValue =member.getRegNo();
                break;
            case 3:
                columnValue =member.getEmailAddress();
                break;
            case 4:
                columnValue =member.getGender().equalsIgnoreCase(Constants.MALE) ?"MALE":"FEMALE";
                break;
            case 5:
                columnValue =member.getPhoneNumber();
                break;
            case 6:
                columnValue =member.getOccupation().toUpperCase();
                break;
             case 7:
                columnValue = DateFormatUtils.format(member.getUserAccount().getFirstAccessedDate(),"dd-MM-yyyy");
                break;
            default:
                columnValue = null;           
        }
        return columnValue;
    }
    
    @Override
    public int getRowCount() {
        return ((Long) manager.createNamedQuery("Member.findAllMembersCount").getSingleResult()).intValue();
    }

    @Override
    public int getColumnCount() {
        return headerList.length;
    }
    
    @Override
    public String getColumnName(int column){
        return headerList[column];
    }
    
    private List<Member> getMembers(int from, int to) {
        //System.out.println("numer of requests to the database " + counter++);
        Query query = manager.createNamedQuery("Member.findAllMembers").setMaxResults(to - from).setFirstResult(from);
        List<Member> resultList = query.getResultList();
        return resultList;
    }

    public List<Member> getMemberList() {
        return memberList;
    }
    
    
}
