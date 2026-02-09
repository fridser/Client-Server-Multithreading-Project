package edu.ntnu.bidata;

import java.io.*;
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

  //TODO: Remove sout statements when no longer necessary for debugging.
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
        handleClient(clientSocket);
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

  //TODO: Remove sout statements when no longer necessary for debugging.
  private void handleClient(Socket clientSocket) {
    try (clientSocket; BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

      writer.write("Welcome to the Calculator! \n" +
          "Write your operation in the format: var1 operation var2 \n" +
          "Operations available: \n" +
          "Addition: + \n" +
          "Subtraction: - \n" +
          "Multiplication: * \n" +
          "Division: /\n" +
          ">");
      writer.newLine();


      while (isOn && !clientSocket.isClosed()) {
        String message = reader.readLine(); // message is null if client abruptly disconnects
        System.out.println(message);
        if (message == null || message.equalsIgnoreCase("exit")) {
          break;
        }
        int result = calculator.handleCommand(message);
        System.out.println("Calculated: " + result);

        writer.write("Result: " + result);
        writer.newLine();
      }
    } catch (IOException e) {
      // Client disconnected abruptly (e.g., connection reset)
      System.out.println("Client disconnected: " + clientSocket.getInetAddress().getHostAddress());
    }
  }

}
