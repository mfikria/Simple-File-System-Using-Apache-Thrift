/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.helpers;

import com.pat.filesystem.services.FileAttribute;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfikria
 */
public class Helper {
   
    public static boolean setAttribute(FileAttribute fileAttribute, Path filePath) {
        try {
            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            long millisLastModifiedDate = dateFormatter.parse(fileAttribute.getLastModifiedDate()).getTime();
            long millisCreatedDate = dateFormatter.parse(fileAttribute.getCreatedDate()).getTime();

            
            Files.setAttribute(filePath, "basic:lastModifiedTime", FileTime.fromMillis(millisLastModifiedDate), NOFOLLOW_LINKS);
            Files.setAttribute(filePath, "basic:creationTime", FileTime.fromMillis(millisCreatedDate), NOFOLLOW_LINKS);
                    
            return true;
        } catch (ParseException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return false;
       
    }
    
    public static FileAttribute getAttribute(Path filePath) throws IOException {
        FileAttribute fileAttribute = new FileAttribute();
        fileAttribute.setDirectory((Boolean) Files.getAttribute(filePath, "basic:isDirectory", NOFOLLOW_LINKS));
                   
        if(fileAttribute.isDirectory()) {
            fileAttribute.setSize(filePath.toFile().list().length);
        } else {
            fileAttribute.setSize((long) Files.getAttribute(filePath, "basic:size", NOFOLLOW_LINKS));
        }
        fileAttribute.setCreatedDate(Files.getAttribute(filePath, "basic:creationTime").toString());
        fileAttribute.setLastModifiedDate(Files.getAttribute(filePath, "basic:lastModifiedTime").toString());
        return fileAttribute;
    }
}
