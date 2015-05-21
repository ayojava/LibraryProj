/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import java.util.HashMap;

/**
 *
 * @author ayojava
 */
public class ErrorUtil extends HashMap<String, String>
{
    public void queue(String label,String error)
    {
        //System.out.println(label + " - " + error);
        this.put(label, error);
    }
}
