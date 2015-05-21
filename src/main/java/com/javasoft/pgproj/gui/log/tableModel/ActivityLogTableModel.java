/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.log.tableModel;

import com.javasoft.pgproj.Processor.ActivityLogProcessor;
import com.javasoft.pgproj.Processor.BaseProcessor;
import com.javasoft.pgproj.entity.ActivityLog;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ayojava
 */
public class ActivityLogTableModel extends AbstractTableModel{
    private int startPosition;
    private int counter = 0;
    private BaseProcessor processor;
    private EntityManager manager;
    private String headerList[] = new String[]{"S/N","Entity Name", "Narrative","Member Name", "Date" };
    private List<ActivityLog> activityLogList;
    
    public ActivityLogTableModel() {
        processor = new ActivityLogProcessor();
        startPosition = 0;
        manager = processor.getBookDAO().getEntityManager();
        activityLogList = getLogs(startPosition, startPosition + 100);
    }
    
    @Override
    public int getRowCount() {
        return ((Long) manager.createNamedQuery("ActivityLog_findAllLogsCount").getSingleResult()).intValue();
    }

    @Override
    public int getColumnCount() {
        return headerList.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {

        if ((rowIndex >= startPosition) && (rowIndex < (startPosition + 100))) {
            
        } else {
           activityLogList = getLogs(rowIndex, rowIndex + 100); 
           startPosition = rowIndex;
        }
        
        ActivityLog activityLog = activityLogList.get(rowIndex - startPosition);
        Object columnValue = null;
        
        switch(columnIndex){
            case 0:
                columnValue =rowIndex +1;
                break;
            case 1:
                columnValue =activityLog.getEntityName();
                break;
            case 2:
                columnValue =activityLog.getNarrative();
                break;
            case 3:
                columnValue =activityLog.getUserIdentity().getFirstName();
                break;
            case 4:
                columnValue =activityLog.formatDate(activityLog.getEventDate());
                break;
            default:
                columnValue = null;           
        }
        return columnValue;
    }

    @Override
    public String getColumnName(int column){
        return headerList[column];
    }
     private List<ActivityLog> getLogs(int from, int to) {
        //System.out.println("numer of requests to the database " + counter++);
        Query query = manager.createNamedQuery("ActivityLog_findAllLogs").setMaxResults(to - from).setFirstResult(from);
        List<ActivityLog> resultList = query.getResultList();
        return resultList;
    }
}
