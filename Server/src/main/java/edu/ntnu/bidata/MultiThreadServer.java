package edu.ntnu.bidata;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadServer {
    private final ServerSocket serverSocket;
    private volatile boolean running = false;

    public MultiThreadServer(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void start() {
        running = true;
        while (running) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("New client connected: " + client.getInetAddress().getHostAddress());
                Thread thread = new Thread(new ClientHandler(client));
                thread.start();
            } catch (IOException e) {
                running = false;
                throw new RuntimeException("Not implemented yet");
            }
        }
    }
}