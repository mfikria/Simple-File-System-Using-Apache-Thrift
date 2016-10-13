/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.services;

import com.pat.filesystem.helpers.Helper;
import com.pat.filesystem.services.FileSystemService;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        FileChunk fileChunk = null;
        try {
            fileChunk = new FileChunk();
            File file = Paths.get(path, fileName).toFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            
            long remaining = randomAccessFile.getChannel().size() - offset;
            if(remaining < size) {
                size = (int) remaining;
            }
            
            byte[] data = new byte[size];
            
            MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, offset, size);
            if(mappedByteBuffer.hasRemaining()) {
                mappedByteBuffer.get(data);
            }
            
            fileChunk.setData(data);
            fileChunk.setRemaining(remaining - size);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileChunk;
    }

    @Override
    public boolean putfile(String path, String fileName, FileChunk fileChunk, long offset, int size) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isFileInUse(String path, String fileName) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FileAttribute getFileAttribute(String path, String fileName) throws TException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
