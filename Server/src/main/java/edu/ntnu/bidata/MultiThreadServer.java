package edu.ntnu.bidata;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class MultiThreadServer {
        public static void main(String[] args) throws IOException {
            int port = 5000;
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Multi-threaded Server is running on port " + port);

            while (true) {
                // The main thread ONLY handles incoming connections.
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected. Spawning a worker thread...");

                // Start a new thread for each client (one thread per client).
                new Thread(new ClientHandler(clientSocket)).start();
            }
        }
    }

    class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) { this.socket = socket; }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                // This specific thread might block on readLine(),
                // but the Main Thread is already back at accept() for the next client.
                String request = in.readLine();

                // Uncomment the line below to simulate a 2-second processing time
//                 Thread.sleep(2000);


                String result = CalculatorLogic.processRequest(request);
                out.println(result);

            } catch (IOException e) {
                System.err.println("Handler error: " + e.getMessage());
            }
        }
    }