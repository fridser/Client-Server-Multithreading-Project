package edu.ntnu.bidata;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SingleThreadServer {
    private final int port;
    private ServerSocketChannel serverSocket;
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
    public void start() {
        if (!isOn) {
            isOn = true;
        }

        try (ServerSocketChannel ss = ServerSocketChannel.open()
        .bind(new java.net.InetSocketAddress(port));
             final Selector selector = Selector.open()) {
            this.serverSocket = ss;
            ss.configureBlocking(false);
            ss.register(selector, SelectionKey.OP_ACCEPT);

            while (isOn) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        SocketChannel clientSocket = ss.accept();
                        clientSocket.configureBlocking(false);
                        clientSocket.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        handleClient(client);
                    }
                }
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
    private void handleClient(SocketChannel channel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        channel.read(buffer);

        String data = new String(buffer.array()).trim();

        if (data.length() > 0) {
            System.out.println("Received command: " + data);
            String result = calculator.handleCommand(data);
            System.out.println("Sending result: " + result);
            result = result + "\n";
            ByteBuffer responseBuffer = ByteBuffer.wrap(result.getBytes());
            channel.write(responseBuffer);

        }
    }

}