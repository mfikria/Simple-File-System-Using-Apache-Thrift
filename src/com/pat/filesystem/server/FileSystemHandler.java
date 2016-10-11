/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.server;

import com.pat.filesystem.helpers.Helper;
import com.pat.filesystem.services.FileSystemService;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author mfikria
 */
public class FileSystemHandler implements FileSystemService.Iface {
    
    private static Helper helper;
    
    public FileSystemHandler() {
        helper = new Helper();
    }

    @Override
    public List<String> dir(String server_path) throws TException {
        List<String> fileList = new ArrayList<String>();
        
        File dir = new File(server_path);
        String[] files = dir.list();
        if (files.length == 0) {
            fileList.add("/");
        } else {
            for (String file : files) {
                fileList.add(file);
            }
        }
       return fileList;
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
    public List<Byte> getfile(String serverPath, String fileName) throws TException {
        
        File file = helper.getFileFromPath(serverPath, fileName);
        
        return helper.convertToByteList(file);
    }

    @Override
    public boolean putfile(String serverPath, String fileName, List<Byte> data) throws TException {
        File file = helper.getFileFromPath(serverPath, fileName);
        if (file != null) {
            return helper.streamDataFile(file, data);
        } else {
            return false;
        }
    }

    
}
