package edu.ntnu.bidata;

import java.io.*;
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
    BufferedReader in = null;
    BufferedWriter out = null;
    try {
      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

      String message;
      // Read until the client closes the connection (readLine returns null)
      while ((message = in.readLine()) != null) {
        String output = String.valueOf(this.logic.handleCommand(message));
        out.write(output);
        out.newLine();
        out.flush();
        System.out.println("Calculated: " + output + " From: " + message);
      }

      System.out.println("Client closed: " + client.getInetAddress().getHostAddress());
    } catch (IOException e) {
      System.err.println("I/O error with client " + client.getInetAddress().getHostAddress() + ": " + e.getMessage());
    } finally {
      // Clean up resources
      try {
        if (in != null) in.close();
      } catch (IOException ignored) {}
      try {
        if (out != null) out.close();
      } catch (IOException ignored) {}
      try {
        if (!client.isClosed()) client.close();
      } catch (IOException ignored) {}
    }
  }
}
