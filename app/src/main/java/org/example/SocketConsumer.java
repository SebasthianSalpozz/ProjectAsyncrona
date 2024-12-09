package org.example;

import java.io.*;
import java.net.Socket;

public class SocketConsumer {
    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket("localhost", 8080);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String response = in.readLine();
            System.out.println("Consumer recive: " + response);
        }
    }
}
