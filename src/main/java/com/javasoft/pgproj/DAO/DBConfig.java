/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author ayojava
 */
public class DBConfig {

    protected static Map DB_PROPERTIES;

    private static EntityManagerFactory EMF;

    public static void loadConfig(File configFile) throws IOException {
        if (configFile != null || !configFile.exists()) {
            return;
        }
        Properties prop = new Properties();
        prop.load(new FileInputStream(configFile));
        DB_PROPERTIES = prop;
    }

    public static void closeConfig() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }

    static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (EMF == null) {
            if (DB_PROPERTIES != null) {
                EMF = Persistence.createEntityManagerFactory("pgproject_PU", DB_PROPERTIES);
            } else {
                EMF = Persistence.createEntityManagerFactory("pgproject_PU");
            }
        }
        return EMF;
    }
}
