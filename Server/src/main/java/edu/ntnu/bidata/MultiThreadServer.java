package edu.ntnu.bidata;

public class MultiThreadServer {
  private ServerSocket serverSocket;

  public MultiThreadServer(InetAddress address, int port) throws IOException {
    serverSocket = new ServerSocket(address,port);
  }
}
