package edu.ntnu.bidata;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Client that can send arithmetic operations to the server.
 * Supports running multiple simultaneous clients for performance testing.
 */
public class Client {
    private final String host;
    private final int port;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * Send a single command to the server and return the result.
     */
    public String sendCommand(String command) throws IOException {
        try (
                Socket socket = new Socket(host, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()))
        ) {
            out.println(command);
            return in.readLine();
        }
    }

    /**
     * Run multiple simultaneous clients for performance testing.
     *
     * @param numClients Number of simultaneous clients
     * @param commands List of commands to send
     * @return Total time in milliseconds
     */
    public static long runPerformanceTest(String host, int port, int numClients,
                                          List<String> commands) {
        System.out.println("\n=== Performance Test ===");
        System.out.println("Server: " + host + ":" + port);
        System.out.println("Number of clients: " + numClients);
        System.out.println("Commands per client: " + commands.size());
        System.out.println("Total requests: " + (numClients * commands.size()));
        System.out.println("Starting test...\n");

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(numClients);
        AtomicLong totalResponseTime = new AtomicLong(0);
        List<Thread> threads = new ArrayList<>();

        long startTime = System.currentTimeMillis();

        // Create all client threads
        for (int i = 0; i < numClients; i++) {
            final int clientId = i + 1;
            Thread thread = new Thread(() -> {
                try {
                    // Wait for start signal
                    startLatch.await();

                    Client client = new Client(host, port);
                    long clientStartTime = System.currentTimeMillis();

                    for (String command : commands) {
                        try {
                            String result = client.sendCommand(command);
                            System.out.println("Client #" + clientId + ": " + command + " = " + result);
                        } catch (IOException e) {
                            System.err.println("Client #" + clientId + " error: " + e.getMessage());
                        }
                    }

                    long clientEndTime = System.currentTimeMillis();
                    totalResponseTime.addAndGet(clientEndTime - clientStartTime);

                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    doneLatch.countDown();
                }
            });
            threads.add(thread);
            thread.start();
        }

        // Start all clients simultaneously
        startLatch.countDown();

        // Wait for all clients to finish
        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("\n=== Results ===");
        System.out.println("Total execution time: " + totalTime + " ms");
        System.out.println("Average response time per client: " +
                (totalResponseTime.get() / numClients) + " ms");
        System.out.println("Throughput: " +
                ((numClients * commands.size() * 1000.0) / totalTime) + " requests/sec");

        return totalTime;
    }

    public static void main(String[] args) {
        // Parse arguments
        String host = "127.0.0.1";
        int port = 1238;
        int numClients = 10;

        if (args.length >= 1) {
            port = Integer.parseInt(args[0]);
        }
        if (args.length >= 2) {
            numClients = Integer.parseInt(args[1]);
        }
        if (args.length >= 3) {
            host = args[2];
        }

        // Prepare test commands
        List<String> commands = new ArrayList<>();
        commands.add("10 + 5");
        commands.add("20 - 8");
        commands.add("6 * 7");
        commands.add("100 / 4");
        commands.add("15 + 25");
        commands.add("50 - 30");
        commands.add("8 * 9");
        commands.add("144 / 12");
        commands.add("99 + 1");
        commands.add("1000 - 500");

        // Run performance test
        runPerformanceTest(host, port, numClients, commands);
    }
}