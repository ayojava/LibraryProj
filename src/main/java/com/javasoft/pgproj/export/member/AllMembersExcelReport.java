/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.export.member;

import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.export.ExcelProcessor;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.status.EntityStatus;
import com.javasoft.pgproj.util.FileUtil;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.mail.util.ByteArrayDataSource;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author ayojava
 */
public class AllMembersExcelReport extends ExcelProcessor {

    private String[] columnHeaders = {"S/N","FULLNAME", "REG NO", "EMAIL ADDRESS", "GENDER", "PHONE NO", "OCCUPATION", "REGISTERED DATE","STATUS"};
    private String[] SheetHeaders = {"LIST OF LIBRARY MEMBERS"};
    private List<Member> allMembers;
    private ByteArrayOutputStream outStream;

    public AllMembersExcelReport(String sheetName, List<Member> allMembers) {
        setCurrentSheet(sheetName);
        this.allMembers = allMembers;
    }

    @Override
    public void populateExcelSheet(boolean sendEmail) {
        outStream = new ByteArrayOutputStream();
        populateSheetHeaders(SheetHeaders);
        populateColumnHeaders(columnHeaders);
        try {
            writeExcelData();
            if(sendEmail){
            sendFileAsAttachment(outStream);
            }
            
        } catch (Exception ex) {
            System.out.println("Exception ::: " + ex);
        }
        finally{
            try {
                outStream.close();
            } catch (IOException ex) {
                System.out.println("Exception in Finally Block" + ex);
            }
        }
    }

    private void sendFileAsAttachment(ByteArrayOutputStream outStream) {
        ByteArrayDataSource byteArrayStream = new ByteArrayDataSource(outStream.toByteArray(),"application/vnd.ms-excel");
        
    }
    
    public void writeExcelData() throws IOException {
        if (allMembers == null || allMembers.isEmpty()) {
            return;
        }
       
        int currentPosition = getRowPosition();
        int no = 1;
        for (Member member : allMembers) {
            if (member == null) {
                continue;
            }
            
            String[] rowValues = {
                String.valueOf(no),member.returnFullName(), member.getRegNo(), member.getEmailAddress(),
                member.getGender().equalsIgnoreCase(Constants.MALE) ? "MALE" : "FEMALE",
                member.getPhoneNumber(), member.getOccupation(), DateFormatUtils.format(member.getUserAccount().getFirstAccessedDate(),Constants.DATE_FORMAT_PATTERN ),
                EntityStatus.getStatusName(member.getUserAccount().getEntityState().getStatus())
            };

            Row row = getCurrentSheet().createRow(currentPosition);
            for (int i = 0; i < rowValues.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellStyle(getCellContentsStyle());
                cell.setCellValue(rowValues[i]);
            }
            currentPosition++;
            no++;
        }

        writeTo(outStream);
         
        FileOutputStream fileStream = new FileOutputStream(FileUtil.createFile(null, "LMS_Members_" + DateFormatUtils.format(new Date(), "dd-MM-yyyy")+".xls"));
        outStream.writeTo(fileStream);
        outStream.flush();
        if(fileStream != null){
            fileStream.close();
        }
        
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    
}
