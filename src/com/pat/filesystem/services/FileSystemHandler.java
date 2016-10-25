/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.services;

import com.pat.filesystem.helpers.Helper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    
    private Helper helper;

    @Override
    public List<FileAttribute> dir(String path) throws TException {
       List<FileAttribute> fileList = new ArrayList<FileAttribute>();
       
       File dir = new File(path);
       String[] files = dir.list();
       if (files.length > 0) {
           for (String fileName : files) {
               try {
                   Path filePath = Paths.get(path, fileName);
                   FileAttribute fileAttribute = helper.getAttribute(filePath);  
                   fileAttribute.setFileName(fileName);
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
        // Initialization
        FileChunk fileChunk = null;
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        
        
        
        try {          
            // Create access file for streaming data
            File file = Paths.get(path, fileName).toFile();
            randomAccessFile = new RandomAccessFile(file, "r");
            fileChannel = randomAccessFile.getChannel();    
            long fileSize = randomAccessFile.getChannel().size();
            
            
            
            // Create temp buffer
            byte[] data = new byte[size];
            
            // Mapping file to memory
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, offset, size);
            
            if(mappedByteBuffer.hasRemaining()) {
                // Copy data to buffer
                mappedByteBuffer.get(data);
                
                // Create instance File Chunk
                fileChunk = new FileChunk();
                fileChunk.setData(data);
                fileChunk.setRemainingBytes(fileSize - offset - size);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                randomAccessFile.close();
                fileChannel.close();
            } catch (IOException ex) {
                Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return fileChunk;
    }

    @Override
    public boolean putFile(String path, String fileName, FileChunk fileChunk, long offset, int size) throws TException {
        RandomAccessFile randomAccessFile = null;
        FileChannel fileChannel = null;
        try {
            // Create access file for streaming data
            File file = Paths.get(path, fileName).toFile();
            file.createNewFile();
            
            randomAccessFile = new RandomAccessFile(file, "rw");
            fileChannel = randomAccessFile.getChannel();
            
            // Mapping file to memory
            MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, offset, size);
            mappedByteBuffer.put(fileChunk.getData(), 0, size);
            
            if(fileChunk.getRemainingBytes() <= 0) {
                return true;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                randomAccessFile.close();
                fileChannel.close();
            } catch (IOException ex) {
                Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public FileAttribute getFileAttribute(String path, String fileName) throws TException {
        try {
            return helper.getAttribute(Paths.get(path, fileName));
        } catch (IOException ex) {
            Logger.getLogger(FileSystemHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public boolean setFileAttribute(String path, String fileName, FileAttribute fileAttribute) throws TException {
        return helper.setAttribute(fileAttribute, Paths.get(path, fileName));
    }

    @Override
    public boolean isValidPath(String path) throws TException {
        return Files.isDirectory(Paths.get(path));
    }
}
