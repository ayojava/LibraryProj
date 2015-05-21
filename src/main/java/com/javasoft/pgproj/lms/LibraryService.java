/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.lms;

import com.javasoft.pgproj.entity.ActivityLog;
import com.javasoft.pgproj.entity.BookShelve;
import com.javasoft.pgproj.entity.DAO.BookShelveDAO;
import com.javasoft.pgproj.entity.DAO.LibraryUserDAO;
import com.javasoft.pgproj.entity.EntityState;
import com.javasoft.pgproj.entity.Member;
import com.javasoft.pgproj.gui.core.JLibrary;
import com.javasoft.pgproj.gui.splash.SplashFrame;
import com.javasoft.pgproj.interfaces.Constants;
import com.javasoft.pgproj.service.BookService;
import com.javasoft.pgproj.util.PropertyUtil;
import com.javasoft.pgproj.util.ResourceUtil;
import com.javasoft.pgproj.util.XmlUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author ayojava
 */
public class LibraryService {

    private static LibraryService libService;
    private BookShelveDAO bookShelveDAO;
    private Member systemUser;

    private LibraryService() {
    }

    public static LibraryService getInstance() {
        if (libService == null) {
            libService = new LibraryService();
        }
        return libService;
    }

    public boolean startApplication() {
        try {
            loadLookAndFeel();
            loadInstances();
            loadSplashFrame();
            loadSystemValues();

            if (systemUser != null) {
                System.out.println("System User Found");
                //populateDatabase();
                //startservices();
                displayApplication();
            } else {
                System.out.println("No System User Found");
            }
            return true;
        } catch (Exception ex) {
            System.out.println("Error During StartUp ::::" + ex);
            System.exit(0);
            return false;
        }
    }

    private void loadLookAndFeel() throws Exception {
        UIManager.LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();

        for (int i = 0; i < installedLookAndFeels.length; i++) {
            if ("Nimbus".equals(installedLookAndFeels[i].getName())) {
                UIManager.setLookAndFeel(installedLookAndFeels[i].getClassName());
                break;
            }
        }

    }

    private void loadInstances() throws IOException {
        ResourceUtil.getInstance();
        PropertyUtil.getInstance().loadConfig();
        XmlUtil.getInstance().readAllStartUpConfig();
    }

    private void loadSplashFrame() {
        SplashFrame splash = new SplashFrame();
        splash.showSplash(8000);
    }

    private void displayApplication() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JLibrary();
                frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            }
        });
    }

    private void populateDatabase() throws Exception {
        XmlUtil instance = XmlUtil.getInstance();
        List<BookShelve> bookShelves = instance.getBookShelves();

        EntityManager em = null;
        EntityTransaction transaction = null;
        try {
            em = getBookShelveDAO().getEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            System.out.println(" Initialising Database ");

            for (BookShelve shelve : bookShelves) {
                EntityState es = EntityState.newInstance(systemUser, shelve);
                es.setActive();
                shelve.setEntityState(es);
                em.persist(shelve);
                em.refresh(shelve);
                getBookShelveDAO().saveActivityLog(shelve, String.valueOf(shelve.getId()), ActivityLog.REGISTER_SHELVE, systemUser, em);
            }

            transaction.commit();
        } catch (Exception ex) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw ex;
        } finally {
            getBookShelveDAO().close();
        }

    }

    private void loadSystemValues() {
        Map<String, Object> map = new HashMap<>();
        map.put("sysAdmin", Constants.GLOBAL_NO); //maybe if i have time to distinguish btw sysadmin & systemuser
        map.put("sysAccount", Constants.GLOBAL_YES);
        systemUser = (Member) getLibraryUserDAO().getSingleResult("UserIdentity.findSystemUser", map);
    }

    public Member getSystemUser() {
        return systemUser;
    }

    private BookShelveDAO getBookShelveDAO() {
        if (bookShelveDAO == null) {
            bookShelveDAO = new BookShelveDAO(null, true);
        }
        return bookShelveDAO;
    }
    private LibraryUserDAO libraryUserDAO;

    public LibraryUserDAO getLibraryUserDAO() {
        if (libraryUserDAO == null) {
            libraryUserDAO = new LibraryUserDAO(null, true);
        }
        return libraryUserDAO;
    }

    private void startservices() throws SchedulerException {
        SchedulerFactory sf = new StdSchedulerFactory();
        Scheduler sched = sf.getScheduler();
        BookService.getInstance().init(sched);
    }
}
