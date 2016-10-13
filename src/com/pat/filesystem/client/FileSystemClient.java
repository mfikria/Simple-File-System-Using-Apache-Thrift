/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.client;

import com.pat.filesystem.services.FileAttribute;
import com.pat.filesystem.services.FileSystemHandler;
import com.pat.filesystem.services.FileSystemService;
import static com.sun.corba.se.impl.activation.ServerMain.logError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author mfikria
 */
public class FileSystemClient {
    private static String serverAddress;
    private static Integer serverPort;
    private static Boolean isRunning;
    private static Queue<String> listOfActions;
    private int fileChunkSize = 128;
    
    
    private static TProtocol protocol;
    private static TTransport transport;
    
    private static Scanner reader = new Scanner(System.in);
    
    public static void main(String[] args) throws TTransportException, IOException, TException {
        initialize();
        while(isRunning) {
            run();
        }
        terminate();
    }    
    
    public static void initialize() {
        isRunning = true;
        listOfActions = new LinkedList<String>();
        reader = new Scanner(System.in);
        System.out.print("Enter a server address: ");
        serverAddress = reader.nextLine();
        System.out.print("Enter a server port: ");
        serverPort = new Integer(reader.nextLine());
    }
    
    public static void execute(FileSystemService.Processor processor) throws TTransportException {
        
        transport =  new TSocket(serverAddress, serverPort);
        protocol = new TBinaryProtocol(transport);
        transport.open();
        
        FileSystemService.Client client = new FileSystemService.Client(protocol);
        String[] splitStr = listOfActions.remove().split("\\s+");
        switch (splitStr[0]) {
            case "dir": dirAction(client, splitStr[1]); 
                        break;
            case "createdir": createDirAction(client, splitStr[1], splitStr[2]); 
                        break;
//            case "getfile": getFileAction(client, splitStr[1], splitStr[2], splitStr[3]); 
//                        break;
//            case "putfile": putFileAction(client, splitStr[1], splitStr[2], splitStr[3]); 
//                        break;
//            case "exit": isRunning = false;
//                        break;
            default: System.out.println("Invalid Action.");
                    break;
        }
    }
    
    public static void run() throws TException {
        
        System.out.print(">> ");
        listOfActions.add(reader.nextLine());

        FileSystemHandler handler = new FileSystemHandler();
        FileSystemService.Processor processor = new FileSystemService.Processor(handler);
        execute(processor);
    }
    
    public static void terminate() {
        listOfActions.clear();
    }
    
    private static void dirAction(FileSystemService.Client client, String path) {
        
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            for (FileAttribute fileAttribute : client.dir(path)) {
                System.out.println(fileAttribute.getFileName());
                System.out.print("Size: " + fileAttribute.getSize());
                if(fileAttribute.isDirectory()) {
                    System.out.println(" items");
                } else {
                    System.out.println(" bytes");
                }
                System.out.println("Created Date: " +  formatter.parse(fileAttribute.getCreatedDate().replaceAll("Z$", "+0000")));
                System.out.println("Last Modified Date: " + formatter.parse(fileAttribute.getLastModifiedDate().replaceAll("Z$", "+0000")));
                System.out.println();
            }
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private static void createDirAction(FileSystemService.Client client, String path, String folderName) {
        try {
            if (client.createDir(path, folderName)) {
                System.out.println("Success: Directory \"" + folderName + "\" has been created.");
            } else {
                System.out.println("Error: Directory has been created before OR path is invalid.");
            }
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
//     private static void getFileAction(FileSystemService.Client client, String path, String fileName, String localPath) {
//         FileOutputStream fos = null;
//        try {
//                       
//            List<Byte> byteList = new ArrayList<Byte>();
//             try {
//                 byteList = client.getfile(path, fileName);
//             } catch (TException ex) {
//                 Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
//             }
//            
//            byte[] byteArray = new byte[byteList.size()];
//            for (int index = 0; index < byteList.size(); index++) {
//                byteArray[index] = byteList.get(index);
//            }
//            
//            File f = new File(localPath + fileName);
//
//            f.getParentFile().mkdirs(); 
//            f.createNewFile();
//            
//            fos = new FileOutputStream(localPath + fileName);
//            fos.write(byteArray);
//            fos.close();
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
//        }
//     }
//     
//     private static void putFileAction(FileSystemService.Client client, String path, String fileName, String localPath) {
//     FileInputStream targetInputStream = null;
//            List<Byte> al = new ArrayList<Byte>();
// 
//            File targetFile = new File(localPath + fileName);
//
//            byte[] targetByteStream = new byte[(int) targetFile.length()];
//
//            try {
//
//                    targetInputStream = new FileInputStream(targetFile);
//                    targetInputStream.read(targetByteStream);
//                    targetInputStream.close();
//
//  
//                    for(byte b : targetByteStream) {
//                        al.add(new Byte(b));
//                      }
//                    
//             boolean status = client.putfile(path, fileName, al);
//        
//            } catch (IOException e) {
//                    logError("Exception" + e);
//            }catch (TException ex) {
//             Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
//         }
//     }
}
