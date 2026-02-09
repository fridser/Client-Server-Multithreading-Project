package edu.ntnu.bidata;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class MultiThreadServer {
  private final ServerSocket serverSocket;
  private volatile boolean running = false;

  public MultiThreadServer(int port,InetAddress address) throws IOException {
    serverSocket = new ServerSocket(port, 50, address);
  }

  public void start() {
    running = true;
    while (running) {
      try {
        Socket client = serverSocket.accept();
        Thread thread = new Thread(new ClientHandler(client));
        thread.start();
      } catch (IOException e) {
        throw new RuntimeException("Not implemented yet");
      }
    }
  }
}
