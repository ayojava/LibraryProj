/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ayojava
 */
public class PropertyUtil {

    private static PropertyUtil propUtil;
    private Properties mailConfig;
    private String xmlFilePath;
    private String mailServer,mailSender,mailPassword;
    private String cronExpression;
    private boolean debug;
    private String maven_Path;

    private PropertyUtil() {
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public static PropertyUtil getInstance() {
        if (propUtil == null) {
            propUtil = new PropertyUtil();
        }
        return propUtil;
    }

    public String getXmlFilePath() {
        return xmlFilePath;
    }

    public String getMailServer() {
        return mailServer;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getMailSender() {
        return mailSender;
    }

    
    public void loadConfig() throws IOException {
        Properties prop = new Properties();
        String value;
        //prop.load(new FileInputStream(new File("config/lbs.properties")));
        maven_Path="src" + File.separator  +"main" + File.separator  +"resources";
        File lbs = new File(maven_Path + File.separator  +"config" + File.separator  +"lbs.properties");
        
        prop.load(new FileInputStream(lbs));
        //prop.load(new FileInputStream(new File("config" + File.separator  +"lbs.properties")));
        System.out.println("reading Config");

        value = prop.getProperty("xmlfilePath");
        if (StringUtils.isNotBlank(value)) {
            xmlFilePath = value;
        }

        value = prop.getProperty("mailServer");
        if (StringUtils.isNotBlank(value)) {
            mailServer = value;
        }

        value = prop.getProperty("mailSender");
        if (StringUtils.isNotBlank(value)) {
            mailSender = value;
        }
        value = prop.getProperty("mailPassword");
        if (StringUtils.isNotBlank(value)) {
            mailPassword = value;
        }
        
        value = prop.getProperty("cronExp");
        if (StringUtils.isNotBlank(value)) {
            cronExpression = value;
        }
        //System.out.println("" + mailSender + "----" + mailPassword + "------------" + mailServer);
    }

    public Properties getMailMessagesProperties() {
        if (mailConfig == null) {
            //maven_Path + File.separator  +"config" + File.separator  +"lbs.properties"
            mailConfig = loadConfig(maven_Path + File.separator  +"config" + File.separator  +"mail-message.properties");
            //mailConfig = loadConfig("src\\config\\mail-message.properties");
        }
        return mailConfig;
    }

    private Properties loadConfig(String filePath) {
        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream(new File(filePath)));
            return prop;
        } catch (IOException ex) {
            System.out.println("Exception ::: " + ex);
        }
        return null;
    }
}
