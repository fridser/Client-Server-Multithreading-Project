package edu.ntnu.bidata;

import java.net.InetAddress;

public class Main {
  public static void main(String[] args) {
    try {
      int port = 1238;
      //MultiThreadServer server = new MultiThreadServer(port);
      SingleThreadServer server = new SingleThreadServer(port);
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}