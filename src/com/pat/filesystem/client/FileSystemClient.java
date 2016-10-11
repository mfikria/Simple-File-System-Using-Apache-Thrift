/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.client;

import com.pat.filesystem.server.FileSystemHandler;
import com.pat.filesystem.services.FileSystemService;
import static com.sun.corba.se.impl.activation.ServerMain.logError;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
    
    private static Scanner reader = new Scanner(System.in);
    
    public static void main(String[] args) throws TTransportException, IOException, TException {
        
        isRunning = true;
        listOfActions = new LinkedList<String>();
        reader = new Scanner(System.in);
        System.out.println("Enter a server address: ");
        serverAddress = reader.nextLine();
        System.out.println("Enter a server port: ");
        serverPort = new Integer(reader.nextLine());
        
        while(isRunning) {
            run();
        }
    }    
    
    public static void execute(FileSystemService.Processor processor) throws TTransportException {
        TTransport transport;

        transport = new TSocket(serverAddress, serverPort);
        transport.open();

        TProtocol protocol = new TBinaryProtocol(transport);
        FileSystemService.Client client = new FileSystemService.Client(protocol);
        String[] splitStr = listOfActions.remove().split("\\s+");
        switch (splitStr[0]) {
            case "dir": dirAction(client, splitStr[1]); 
                        break;
            case "createdir": createDirAction(client, splitStr[1], splitStr[2]); 
                        break;
            case "getfile": getFileAction(client, splitStr[1], splitStr[2], splitStr[3]); 
                        break;
            case "putfile": putFileAction(client, splitStr[1], splitStr[2], splitStr[3]); 
                        break;
            case "exit": isRunning = false;
                        break;
            default: System.out.println("Invalid Action.");
                    break;
            
        }
        listOfActions.clear();
    }
    
    public static void run() throws TException {
        
        System.out.print(">> ");
        listOfActions.add(reader.nextLine());

          FileSystemHandler handler = new FileSystemHandler();
          FileSystemService.Processor processor = new FileSystemService.Processor(handler);
          execute(processor);
    }
    
    private static void dirAction(FileSystemService.Client client, String path) {
        try {
            for (String temp : client.dir(path)) {
                System.out.println(temp);
            }
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private static void createDirAction(FileSystemService.Client client, String path, String folderName) {
        try {
            if (client.createdir(path, folderName)) {
                System.out.println("Directory \"" + folderName + "\" has been created.");
            }
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private static void getFileAction(FileSystemService.Client client, String path, String fileName, String localPath) {
         FileOutputStream fos = null;
        try {
                       
            List<Byte> byteList = new ArrayList<Byte>();
             try {
                 byteList = client.getfile(path, fileName);
             } catch (TException ex) {
                 Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
             }
            
            byte[] byteArray = new byte[byteList.size()];
            for (int index = 0; index < byteList.size(); index++) {
                byteArray[index] = byteList.get(index);
            }
            
            File f = new File(localPath + fileName);

            f.getParentFile().mkdirs(); 
            f.createNewFile();
            
            fos = new FileOutputStream(localPath + fileName);
            fos.write(byteArray);
            fos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
     private static void putFileAction(FileSystemService.Client client, String path, String fileName, String localPath) {
     FileInputStream targetInputStream = null;
            List<Byte> al = new ArrayList<Byte>();
 
            File targetFile = new File(localPath + fileName);

            byte[] targetByteStream = new byte[(int) targetFile.length()];

            try {

                    targetInputStream = new FileInputStream(targetFile);
                    targetInputStream.read(targetByteStream);
                    targetInputStream.close();

  
                    for(byte b : targetByteStream) {
                        al.add(new Byte(b));
                      }
                    
             boolean status = client.putfile(path, fileName, al);
        
            } catch (IOException e) {
                    logError("Exception" + e);
            }catch (TException ex) {
             Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
         }
     }
}
