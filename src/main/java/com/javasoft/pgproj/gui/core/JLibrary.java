/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.core;

import com.javasoft.pgproj.gui.menus.MenuLibrary;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;

/**
 *
 * @author ayojava
 */
public class JLibrary extends JFrame implements ActionListener {

    private JDesktopPane desktop;
    private MenuLibrary menuLib;
    private DesktopPaneHandler panehandler;

    public JLibrary() {
        super(ResourceUtil.getString("display_title"));
        initComponents();
    }

    private void initComponents() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Toolkit kit = Toolkit.getDefaultToolkit();
        Image image = kit.getImage(ClassLoader.getSystemResource("images/frames/computer.gif"));
        setIconImage(image);

        menuLib = new MenuLibrary();
        setJMenuBar(menuLib);

        attachListener(menuLib.getFile_Exit());
        attachListener(menuLib.getFile_Export());
        attachListener(menuLib.getFile_print());

        attachListener(menuLib.getBk_addBook());
        attachListener(menuLib.getBk_availableBooks());
        attachListener(menuLib.getBk_editBook());
        attachListener(menuLib.getBk_viewBook());
        attachListener(menuLib.getBk_borrowedBooks());
        attachListener(menuLib.getBk_listAllBooks());

        attachListener(menuLib.getMb_addMember());
        attachListener(menuLib.getMb_viewMember());
        attachListener(menuLib.getMb_editMember());
        attachListener(menuLib.getMb_listMembers());

        attachListener(menuLib.getAbout());
        attachListener(menuLib.getBk_loanBook());
        attachListener(menuLib.getBk_returnBook());
        desktop = new JDesktopPane();
        panehandler = new DesktopPaneHandler();
        Container cp = getContentPane();
        desktop.setBackground(Color.GRAY);
        cp.add(desktop);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    private void attachListener(JMenuItem item) {
        item.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent aev) {
        panehandler.handleEvents(aev, desktop);
    }
}
