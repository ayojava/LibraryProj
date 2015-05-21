/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.internalFrames.help;

import com.javasoft.pgproj.gui.internalFrames.ParentFrame;
import com.javasoft.pgproj.util.ResourceUtil;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author ayojava
 */
public class AboutFrame extends ParentFrame {

    private JLabel iconLabel, displayLabel;

    private JPanel southPanel, northPanel;

    public AboutFrame() {
        super(ResourceUtil.getString("internalFrame_help_aboutLMS"), false, true, false, true);
        setFrameIcon(new ImageIcon(ClassLoader.getSystemResource("images/frames/About.png")));
        initLabels();
        initComponents();
        setVisible(true);
        pack();
    }

    private void initComponents() {
        Container cp = getContentPane();
        cp.add(BorderLayout.NORTH, aboutNorthPanel());
        cp.add(BorderLayout.SOUTH, aboutSouthPanel());
    }

    private void initLabels() {
        iconLabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("images/labels/lpu.jpeg")));
//        iconLabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("images/labels/unilag.png")));
//        String htmlText ="<html>"
//                        + "<li>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" LIBRARY MANAGEMENT SYSTEM Version 1.0 </li>"
//                        + "<li>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" Designed by: " +"</li>"
//		        + "<li><p>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" ILORI AYODEJI OLUWASEUN</li></br>"
//                        + "<li><p>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" PGD COMPUTER SCIENCE </li>"
//                        + "<li><p>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" MATRIC NO : 109074076</li>"
//                        + "<li><p>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" UNIVERSITY OF LAGOS, LAGOS.</li>"
//		        + "<li><p>" +"&nbsp;&nbsp;&nbsp;&nbsp" +" Copyright<font size=\"2\"> "  
//                        + "&nbsp;&nbsp;"+"&copy;" +"&nbsp;&nbsp" +"</font>September  2012</li>"
//                        + "</html>";

        String htmlText = "<html>"
                + "<li>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " LIBRARY MANAGEMENT SYSTEM Version 1.0 </li>"
                + "<li>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " Designed by: " + "</li>"
                + "<li><p>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " ILORI AYODEJI OLUWASEUN</li></br>"
                + "<li><p>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " Masters Of Computer Applications </li>"
                + "<li><p>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " REG NO : 11309321</li>"
                + "<li><p>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " LPU , INDIA.</li>"
                + "<li><p>" + "&nbsp;&nbsp;&nbsp;&nbsp" + " Copyright<font size=\"2\"> "
                + "&nbsp;&nbsp;" + "&copy;" + "&nbsp;&nbsp" + "</font>November  2015</li>"
                + "</html>";

        displayLabel = new JLabel(htmlText);
        displayLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));
    }

    private JPanel aboutNorthPanel() {
        northPanel = new JPanel();
        northPanel.add(iconLabel);
        northPanel.add(displayLabel);
        return northPanel;
    }

    private JPanel aboutSouthPanel() {
        southPanel = defaultSouthPanel();
        southPanel.add(getExitButton());
        return southPanel;
    }
}
