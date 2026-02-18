package edu.ntnu.bidata;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.io.*;
import java.net.*;

/**
 * Client that can send arithmetic operations to the server.
 * Supports running multiple simultaneous clients for performance testing.
 */
public class Client {
        public static void main(String[] args) {
            int numberOfClients = 10;
            Thread[] threads = new Thread[numberOfClients];

            long startTime = System.currentTimeMillis();

            for (int i = 0; i < numberOfClients; i++) {
                final int clientId = i;
                threads[i] = new Thread(() -> {
                    try (Socket socket = new Socket("localhost", 5000);
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                        // Send: "Number1 Number2 Operator"
                        out.println((10 + clientId) + " " + 5 + " A");
                        String response = in.readLine();
                        System.out.println("Client " + clientId + " received: " + response);

                    } catch (IOException e) {
                        System.err.println("Client " + clientId + " failed: " + e.getMessage());
                    }
                });
                threads[i].start();
            }

            //Wait for all 10 threads to finish to measure total execution time.
            for (Thread t : threads) {
                try { t.join(); } catch (InterruptedException e) {}
            }

            long endTime = System.currentTimeMillis();
            System.out.println("=====================================");
            System.out.println("Total Execution Time: " + (endTime - startTime) + " ms");
            System.out.println("=====================================");
        }
    }