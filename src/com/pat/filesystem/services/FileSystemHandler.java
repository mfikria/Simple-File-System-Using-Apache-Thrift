/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.services;

import com.pat.filesystem.helpers.Helper;
import com.pat.filesystem.services.FileSystemService;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
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
    
    Helper helper;

    @Override
    public List<FileAttribute> dir(String path) throws TException {
       List<FileAttribute> fileList = new ArrayList<FileAttribute>();
       
       File dir = new File(path);
       String[] files = dir.list();
       if (files.length > 0) {
           for (String fileName : files) {
               try {
                    FileAttribute fileAttribute = new FileAttribute();

                    Path filePath = Paths.get(path, fileName);

                    fileAttribute.setFileName(fileName);
                    fileAttribute.setDirectory((Boolean) Files.getAttribute(filePath, "basic:isDirectory", NOFOLLOW_LINKS)); 

                    if(fileAttribute.isDirectory()) {
                        fileAttribute.setSize(filePath.toFile().list().length);
                    } else {
                        fileAttribute.setSize((long) Files.getAttribute(filePath, "basic:size", NOFOLLOW_LINKS));
                    }
                    fileAttribute.setCreatedDate(Files.getAttribute(filePath, "basic:creationTime").toString());
                    fileAttribute.setLastModifiedDate(Files.getAttribute(filePath, "basic:lastModifiedTime").toString());
                    fileList.add(fileAttribute);
               } catch (IOException ex) {
                   Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
       }
      return fileList;
    }

    @Override
    public boolean createDir(String path, String dirName) throws TException {
       File parentDir = new File(path);
       if (!parentDir.exists()) {
           return false;
       }
       File file = new File(parentDir, dirName);
       if (file.exists()) {
           return false;
       } else return !(!file.exists() && !file.mkdirs());
    }

    @Override
    public FileChunk getBytes(String path, String fileName, long offset, int size) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean putfile(String path, String fileName, FileChunk fileChunk, long offset, int size) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   

    
}
