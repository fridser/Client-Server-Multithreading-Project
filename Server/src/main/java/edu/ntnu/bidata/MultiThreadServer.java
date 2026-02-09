package edu.ntnu.bidata;

public class MultiThreadServer {
  private ServerSocket serverSocket;
  private volatile boolean running = false;

  public MultiThreadServer(InetAddress address, int port) throws IOException {
    serverSocket = new ServerSocket(address,port);
  }


  public void start() {
    running = true;
    while (running) {
      try {
        Socket client = serverSocket.accept();
        Thread thread = new Thread();
      }
    }
  }
}
