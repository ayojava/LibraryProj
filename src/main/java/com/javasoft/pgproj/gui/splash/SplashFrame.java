/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.gui.splash;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author ayojava
 */
public class SplashFrame {

    public void showSplash(int duration) {
        JWindow splash = new JWindow();
        JPanel content = (JPanel) splash.getContentPane();

        // set the window's bounds, centering the window
        int width = 508;
        int height = 340;
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        System.out.println("Screen Width :::: " + screen.width);
        System.out.println("Screen Height :::: " + screen.height);
        int x = (screen.width - width) / 2;
        int y = (screen.height - height) / 2;
        splash.setBounds(x, y, width, height);

        // build the splash screen
        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("images/splash/splash.jpg"));
        JLabel label = new JLabel(icon);
        JLabel copyrt = new JLabel("", JLabel.CENTER);
        copyrt.setFont(new Font("Tahoma", Font.BOLD, 10));
        //copyrt.setBorder(BorderFactory.createEtchedBorder());
        content.setBackground(Color.LIGHT_GRAY);
        //content.setBackground(new Color(232, 232, 228));
        content.add(label, BorderLayout.CENTER);
        content.add(copyrt, BorderLayout.SOUTH);
        content.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        //add a lower border to say loading application
        // display it
        splash.setVisible(true);

        try {
            Thread.sleep(duration);
        } catch (Exception e) {

        }

        splash.setVisible(false);
    }
    
    public static void main(String [] args){
        SplashFrame splash = new SplashFrame();
        splash.showSplash(5000);
    }
}
