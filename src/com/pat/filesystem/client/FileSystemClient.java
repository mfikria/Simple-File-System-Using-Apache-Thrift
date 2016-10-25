/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.client;

import com.pat.filesystem.services.FileAttribute;
import com.pat.filesystem.services.FileChunk;
import com.pat.filesystem.services.FileSystemHandler;
import com.pat.filesystem.services.FileSystemService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import static java.lang.Math.abs;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
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
    private static int fileChunkSize = 4096;
    
    
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
        transport =  new TFramedTransport(new TSocket(serverAddress, serverPort));
        protocol = new TBinaryProtocol(transport);
        transport.open();
        
        String[] splitStr = listOfActions.remove().split("\\s+");
        switch (splitStr[0]) {
            case "dir": 
                if (splitStr.length >= 2) {
                    if(!validatePath(splitStr[1])) {
                        System.out.println("Error: Path in server is not valid");
                        break;
                    }
                    dirAction(splitStr[1]);
                } else {
                    System.out.println("Error: arguments is not completed");
                }
                break;
            case "createdir":
                if (splitStr.length >= 3) {
                    if(!validatePath(splitStr[1])) {
                        System.out.println("Error: Path in server is not valid");
                        break;
                    }
                    createDirAction(splitStr[1], splitStr[2]); 
                } else {
                    System.out.println("Error: arguments is not completed");
                }
                break;
            case "getfile":
                if (splitStr.length >= 4) {
                    if(!validatePath(splitStr[1])) {
                        System.out.println("Error: Path in server is not valid");
                        break;
                    }
                    if(!validateLocalPath(splitStr[3])) {
                        System.out.println("Error: Path in local is not valid");
                        break;
                    }
                    getFileAction(splitStr[1], splitStr[2], splitStr[3]);
                } else {
                    System.out.println("Error: arguments is not completed");
                }
                break;
            case "putfile":
                if (splitStr.length >= 4) {
                    if(!validatePath(splitStr[1])) {
                        System.out.println("Error: Path in server is not valid");
                        break;
                    }
                    if(!validateLocalPath(splitStr[3])) {
                        System.out.println("Error: Path in local is not valid");
                        break;
                    }
                    putFileAction(splitStr[1], splitStr[2], splitStr[3]);
                } else {
                    System.out.println("Error: arguments is not completed");
                }
                break;
            case "exit": isRunning = false;
                        break;
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
    
    private static void dirAction(String path) {
        
        try {            
            FileSystemService.Client client = new FileSystemService.Client(protocol);
            

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            
            int iterator = 0;
            
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
                
                iterator++;
            }
            
            if (iterator == 0) {
                System.out.println("/");
            }
            
        } catch (TTransportException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private static void createDirAction(String path, String folderName) {
        try {            
            FileSystemService.Client client = new FileSystemService.Client(protocol);
            
            if (client.createDir(path, folderName)) {
                System.out.println("Success: Directory \"" + folderName + "\" has been created.");
            } else {
                System.out.println("Error: Directory has been created before OR path is invalid.");
            }
            
        } catch (TTransportException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
     
    private static void getFileAction(String path, String fileName, String localPath) {
        
        // Create client instance
        FileSystemService.Client client = new FileSystemService.Client(protocol);
        
        // Create file instance
        File newFile = Paths.get(localPath, fileName).toFile();
            
        // Initialization
        long offset = 0;
        boolean again = true;
        long startTime = System.nanoTime();
        FileAttribute fileAttribute = null;
        int size = fileChunkSize;
        
        try {
            
            // Create new file in local
            newFile.createNewFile();
            
            FileOutputStream fileOutputStream = new FileOutputStream(newFile);
            FileChannel fileChannel = fileOutputStream.getChannel();
            
            // Get File Attribute first
            fileAttribute = client.getFileAttribute(path, fileName);
            long fileSize = fileAttribute.getSize();
            long remainingBytes = fileSize;
            
            // Handle small file
            if (fileSize < size) {
                size = abs((int) fileSize);
            }
                
            do {
                // Get File Chunk from server
                FileChunk newFileChunk = client.getBytes(path, fileName, offset, size);
                
                // Streaming File Chunk to local file
                fileChannel.write(newFileChunk.data);
                
                // Terminate streaming when no remaining bytes
                remainingBytes = newFileChunk.getRemainingBytes();
                again = (remainingBytes > 0);
                
                if (again) {
                    // Update Offset
                    offset += size;
                    
                    if(remainingBytes < size) {
                        size = abs((int) remainingBytes);
                    }
                }
            } while (again);
            
            if (!again) {
                long endTime = System.nanoTime();
                System.out.println("Success: File \"" + fileName + "\" has been transfered from \"" + path + "\" to \"" + localPath + "\".");
                float duration = (endTime - startTime) / 1000000;
                System.out.println("Transfer time: " + duration + " ms");
                System.out.println("File Size: " + fileSize + " bytes");
                long transferedBytes = fileSize - remainingBytes;
                System.out.println("Bytes Transfered: " + transferedBytes + " bytes");
            }
        } catch (TTransportException | IOException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: Problem occured while tranfering bytes.");
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: Problem occured while tranfering bytes.");
        }
        
        
    }
    
    private static void putFileAction(String path, String fileName, String localPath) {
        
        // Create client instance
        FileSystemService.Client client = new FileSystemService.Client(protocol);
        
        // Initialize local variables
        boolean again = true;
        long offset = 0;
        int size = fileChunkSize;
        long remainingBytes;
        long startTime = System.nanoTime();
        
        try {
            // Create access file for streaming data
            File file = Paths.get(localPath, fileName).toFile();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            
            // Initialize remaining bytes with file size
            long fileSize = randomAccessFile.getChannel().size();
            remainingBytes = fileSize;
            
            if (fileSize < Long.MAX_VALUE) {
                // Mapping file to memory
                MappedByteBuffer mappedByteBuffer = randomAccessFile.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, fileSize);
//                mappedByteBuffer.load();
                do {                  
                    // Copy data to buffer
                    if(size > mappedByteBuffer.remaining()) {
                        size = mappedByteBuffer.remaining();
                    }
                    byte[] data = new byte[size];
                    mappedByteBuffer.get(data);

                    // Create instance File Chunk
                    FileChunk fileChunk = new FileChunk();
                    fileChunk.setData(data);
                    fileChunk.setRemainingBytes(fileSize - offset - size);
                    
                    remainingBytes -= size;
                    again = !client.putFile(path, fileName, fileChunk, offset, size);
                    
                    if(again) {
                        // Update Offset & remainingBytes
                       
                         offset += size;
                        // Handle for overflow buffer in mappedByteBuffer
                    }
                } while(again && mappedByteBuffer.hasRemaining());
                
                if(!again) {
                    long endTime = System.nanoTime();
                    float duration = (endTime - startTime) / 1000000;
                    System.out.println("Success: File \"" + fileName + "\" has been transfered from \"" + localPath + "\" to \"" + path + "\".");
                    System.out.println("Transfer time: " + duration + " ms");
                    System.out.println("File Size: " + fileSize + " bytes");
                    long transferedBytes = fileSize - remainingBytes;
                    System.out.println("Bytes Transfered: " + transferedBytes + " bytes");
                }
                
            } else {
                System.out.println("Error: File size is above limit! Cannot process.");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: Problem occured while tranfering bytes.");
        } catch (IOException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: Problem occured while tranfering bytes.");
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error: Problem occured while tranfering bytes.");
        }
    }
    
    private static boolean validatePath (String path) {
        try {
            // Create client instance
            FileSystemService.Client client = new FileSystemService.Client(protocol);
            
            return client.isValidPath(path);
        } catch (TException ex) {
            Logger.getLogger(FileSystemClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    private static boolean validateLocalPath (String path) {
        return Files.isDirectory(Paths.get(path));
    }
}
