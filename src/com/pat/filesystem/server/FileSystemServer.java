/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pat.filesystem.server;

import com.pat.filesystem.services.FileSystemHandler;
import com.pat.filesystem.services.FileSystemService;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 *
 * @author mfikria
 */
public class FileSystemServer {
     public static void StartsimpleServer(FileSystemService.Processor<FileSystemHandler> processor) {
  try {
   TServerTransport serverTransport = new TServerSocket(9090);
   TServer server = new TThreadPoolServer(
     new TThreadPoolServer.Args(serverTransport).processor(processor));
  
   System.out.println("Starting the server...");
   server.serve();
  } catch (Exception e) {
   e.printStackTrace();
  }
 }
 
 public static void main(String[] args) {
  StartsimpleServer(new FileSystemService.Processor<FileSystemHandler>(new FileSystemHandler()));
 }
}
