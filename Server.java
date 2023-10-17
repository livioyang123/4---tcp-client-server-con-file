package com.example;

import java.io.*;
import java.net.*;

public class Server {
    public void avvioServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(6789);
            System.out.println("Server in attesa di connessioni...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread clientHandlerThread = new Thread(new ClientHandler(clientSocket));
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try {
            // stream di scrittura sul socket
            ObjectOutputStream writer = new ObjectOutputStream(clientSocket.getOutputStream());

            // stream di lettura dal file
            FileInputStream reader = new FileInputStream("C:\\Users\\39347\\Desktop\\tcp_4_inviofile_yang\\src\\main\\resources\\out\\img1.jpg");

            //leggo i byte dal file e li scrivo sul socket
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, lengthRead);
                //writer.flush();
            }

            System.out.println("File inviato.");

            //chiusura stream, socket e server
            writer.close();
            reader.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (clientSocket != null) {
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}