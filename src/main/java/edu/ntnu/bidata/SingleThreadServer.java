package edu.ntnu.bidata;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleThreadServer {
  private final int port;
  private ServerSocket serverSocket;
  private CalculatorLogic calculator;
  private boolean isOn = false;

  public SingleThreadServer(int port) throws IllegalArgumentException {
    if (port < 0 || port > 65535) {
      throw new IllegalArgumentException("Port number must be between 0 and 65535.");
    }
    this.port = port;
    this.calculator = new CalculatorLogic();
  }

  public void run() {
    if (!isOn) {
      isOn = true;
    }

    try (ServerSocket ss = new ServerSocket(port)) {
      this.serverSocket = ss;


      while (isOn) {
        System.out.println("Server is listening on port " + port);
        Socket clientSocket = ss.accept();
        System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
      }
    } catch (
        IOException e) {
      if (isOn) {
        e.printStackTrace();
      }
    } finally {
      // try-with-resources already closed the socket; clear the field reference for clarity
      serverSocket = null;
    }
  }

}
