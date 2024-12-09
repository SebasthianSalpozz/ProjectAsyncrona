package org.example;

import java.io.*;
import java.net.Socket;

public class SocketProducer {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println("topic message");
            System.out.println("Message send to server");
        }
    }
}
