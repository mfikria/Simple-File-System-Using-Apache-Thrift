/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.server;

import com.pat.filesystem.services.FileSystemHandler;
import com.pat.filesystem.services.FileSystemService;
import java.util.Scanner;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author mfikria
 */
public class FileSystemServer {
    private static Integer serverPort;
    
    private static final Scanner reader = new Scanner(System.in);
    
    public static void main(String[] args) {
        run(new FileSystemService.Processor<>(new FileSystemHandler()));
    }
 
    public static void initialize() {
        System.out.print("Enter a server port: ");
        serverPort = new Integer(reader.nextLine());
    }
    
    public static void run(FileSystemService.Processor<FileSystemHandler> processor) {
        initialize();
        
        try {
            TNonblockingServerTransport serverTransport = new TNonblockingServerSocket(serverPort);
            TServer server = new TNonblockingServer(
            new TNonblockingServer.Args(serverTransport).processor(processor));
            System.out.println("Server is running on port " + serverPort + "..."); 
            server.serve(); 
        } catch (TTransportException e) {
        }
    }
}
