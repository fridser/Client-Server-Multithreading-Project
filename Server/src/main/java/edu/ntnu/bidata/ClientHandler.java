package edu.ntnu.bidata;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
  private final Socket client;
  private final CalculatorLogic calculator;

  public ClientHandler(Socket client) {
    this.client = client;
    this.calculator = new CalculatorLogic();
  }

  @Override
  public void run() {
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {

      String message;
      while ((message = reader.readLine()) != null) {
        System.out.println("Received: " + message);

        String result = calculator.handleCommand(message);

        writer.write(result);
        writer.newLine();
        writer.flush();

        System.out.println("Sent result: " + result);
      }
    } catch (IOException e) {
      System.out.println("Client disconnected: " + client.getInetAddress().getHostAddress());
    } finally {
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
