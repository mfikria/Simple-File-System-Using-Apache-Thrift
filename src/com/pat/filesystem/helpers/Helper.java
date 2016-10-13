/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.helpers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfikria
 */
public class Helper {
    
    public byte[] convertToByteArray(List<Byte> byteList) {
        byte[] byteArray = new byte[byteList.size()];
            for (int i = 0; i < byteList.size(); i++) {
                byteArray[i] = byteList.get(i);
            }
        return byteArray;
    }
    
    public List<Byte> convertToByteList(byte[] byteArray) {
        List<Byte> byteList = new ArrayList<Byte>();
        for(byte b : byteArray) {
            byteList.add(new Byte(b));
        }
        return byteList;
    }
    
    public File getFileFromPath(String folderPath, String fileName) {
        if (folderPath.charAt(folderPath.length() - 1) != '/') {
            folderPath = folderPath.concat("/");
        }
        
        File file = new File(folderPath + fileName);
        
        if(file.exists() && file.isFile()) {
            return file;
        }
        return null;
    }
    
    public List<Byte> convertToByteList(File file) {
        FileInputStream byteStream = null;
        byte[] byteArray = new byte[(int) file.length()];
        
        try {
                       
            byteStream = new FileInputStream(file);
            byteStream.read(byteArray);
            byteStream.close();
                
            } catch (FileNotFoundException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return convertToByteList(byteArray);
    }
    
    public boolean streamDataFile(File file, List<Byte> data) {
        FileOutputStream fos;
        fos = null;
            
        try {        
            file.getParentFile().mkdirs();
            file.createNewFile();
            
            fos = new FileOutputStream(file);
            fos.write(convertToByteArray(data));
            fos.close();
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return true;
    }
}
