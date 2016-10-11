/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.services;

import com.pat.filesystem.client.FileSystemClient;
import static com.sun.corba.se.impl.activation.ServerMain.logError;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author mfikria
 */
public class FileSystemHandler implements FileSystemService.Iface {

    @Override
    public List<String> dir(String server_path) throws TException {
        List<String> list = new ArrayList<String>();
        File dir = new File(server_path);
        String[] files = dir.list();
        if (files.length == 0) {
            list.add("/");
        } else {
            for (String aFile : files) {
                list.add(aFile);
            }
        }
       return list;
    }

    @Override
    public boolean createdir(String server_path, String dir_name) throws TException {
        final File homeDir = new File(server_path);
        if (!homeDir.exists()) {
            try {
                throw new IOException("Path \"" + homeDir.getAbsolutePath() + " does not exist");
            } catch (IOException ex) {
                Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        final File dir = new File(homeDir, dir_name);
        if (!dir.exists() && !dir.mkdirs()) {
            try {
                throw new IOException("Unable to create " + dir.getAbsolutePath());
            } catch (IOException ex) {
                Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        return true;
    }

    @Override
    public List<Byte> getfile(String server_path, String file_name) throws TException {

            FileInputStream targetInputStream = null;
            List<Byte> al = new ArrayList<Byte>();
 
            File targetFile = new File(server_path + file_name);

            byte[] targetByteStream = new byte[(int) targetFile.length()];

            try {

                    targetInputStream = new FileInputStream(targetFile);
                    targetInputStream.read(targetByteStream);
                    targetInputStream.close();

  
                    for(byte b : targetByteStream) {
                        al.add(new Byte(b));
                      }
                    
                    return al;

            } catch (IOException e) {
                    logError("Exception" + e);
            }
        return null;
    }

    @Override
    public boolean putfile(String server_path, String file_name, List<Byte> data) throws TException {
        try {
            FileOutputStream fos = null;
            List<Byte> byteList = new ArrayList<Byte>();
            
            byteList = data;
            
            
            byte[] byteArray = new byte[byteList.size()];
            for (int index = 0; index < byteList.size(); index++) {
                byteArray[index] = byteList.get(index);
            }
            
            File f = new File(server_path + file_name);
            
            f.getParentFile().mkdirs(); 
            f.createNewFile();
            
            fos = new FileOutputStream(server_path + file_name);
            fos.write(byteArray);
            fos.close();
            return true;
        } catch (IOException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    
}
