/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javasoft.pgproj.util;

import java.io.File;

/**
 *
 * @author ayojava
 */
public  class FileUtil 
{
    public static File createFile(String dir, String fileName)
    {
        File parentDest = dir != null ?new File(dir): new File(".");
        if(!parentDest.exists()){
            parentDest.mkdir();
        }
        return new File(parentDest,fileName);
    }
}
