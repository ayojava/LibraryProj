/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author ayojava
 */
public class ResourceUtil {

    private static final String BUNDLE_NAME = "com.javasoft.pgproj.properties.Display";
    //private static final String BUNDLE_NAME_IN = "com.javasoft.pgproj.properties.Display_en_IN";
    private static ResourceBundle RESOURCE_BUNDLE;
    //private static Locale defaultLoc ;

    private ResourceUtil() {
    }

    public static void getInstance() {
        //defaultLoc = new Locale("en","IN");
        //RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,defaultLoc);
        RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    }

    public static String getString(String key) {
        if (key == null || key.isEmpty()) {
            return "";
        }
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }

    public static String getString(String key, Object... params) {
        if (key == null || key.isEmpty() || params == null) {
            return "";
        }

        try {
            MessageFormat format = new MessageFormat(RESOURCE_BUNDLE.getString(key));
            return format.format(params);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
