/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.lms;

/**
 *
 * @author ayojava
 */
public class LMS {

    public static void main(String[] args) {
        boolean startApplication = LibraryService.getInstance().startApplication();
    }
    // Generate the tables
    // comment populateDatabase(); method
    // Run SQL
    // INSERT INTO pgproject.EntityState VALUES(1,CURRENT_TIMESTAMP(),'UserIdentity',0,NULL);
    // INSERT INTO pgproject.MemberAccount VALUES(1,CURRENT_TIMESTAMP(),CURRENT_TIMESTAMP(),'Y','N',1);
    // INSERT INTO pgproject.Member VALUES(1,'ayojava@gmail.com','SYSTEM','M','','SYSTEM','SYSTEM','SYS001','0000000','ADM001','',1);
    // UPDATE pgproject.EntityState e SET createdBy = 1 where stateid=1;
    // uncomment populateDatabase(); method
    // run project
}
