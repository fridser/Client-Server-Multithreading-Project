package edu.ntnu.bidata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class SingleThreadServer {
        public static void main(String[] args) throws IOException {
            int port = 5000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Single-threaded Server is running on port " + port);

            while (true) {
                // BLOCKING POINT 1: accept() - The thread waits (WAITING state) for a connection.
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");

                    // BLOCKING POINT 2: readLine() - The thread waits for data from the network.
                    String request = in.readLine();

                    // Uncomment the line below to simulate a 2-second processing time
//                     Thread.sleep(2000);

                    String result = CalculatorLogic.processRequest(request);
                    out.println(result);

                    System.out.println("Request processed: " + request + " -> " + result);
                    // The connection is closed, and only NOW the server can call accept() again.
                } catch (Exception e) {
                    System.err.println("Server error: " + e.getMessage());
                }
            }
        }
    }