package edu.ntnu.bidata;

import java.net.InetAddress;

public class Main {
  public static void main(String[] args) {
    try {
      int port = 1238;
      InetAddress address = InetAddress.getByName("127.0.0.1");
//      MultiThreadServer server = new MultiThreadServer(port, address);
     SingleThreadServer server = new SingleThreadServer(port);
      server.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}