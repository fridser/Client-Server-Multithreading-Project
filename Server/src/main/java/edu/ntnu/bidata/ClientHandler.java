package edu.ntnu.bidata;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket client;
  private final CalculatorLogic logic;

  public ClientHandler(Socket client) {
    this.client = client;
    this.logic = new CalculatorLogic();
  }

  @Override
  public void run() {
    try {
      byte[] input = client.getInputStream().readAllBytes();
      String message = new String(input);
      String output = String.valueOf(this.logic.handleCommand(message));
      OutputStream out = client.getOutputStream();
      out.write(output.getBytes());
      client.close();
    } catch (IOException e) {
      throw new RuntimeException("Not Implemented");
    }
  }
}
