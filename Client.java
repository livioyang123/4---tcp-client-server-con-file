package com.example;
import java.io.*;
import java.net.Socket;

public class Client {
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket miSocket;
    ObjectInputStream reader;
    String filePath = "C:\\Users\\39347\\Desktop\\tcp_4_inviofile_client_yang\\src\\main\\resources\\in\\img12.jpg";

    public Socket connetti() {
        System.out.println("CLIENT partito in esecuzione...");
        try {
            miSocket = new Socket(nomeServer, portaServer);
            reader = new ObjectInputStream(miSocket.getInputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione con il server!");
            System.exit(1);
        }
        return miSocket;
    }

    public void riceviFile() {
        try {
            // stream di scrittura su file
            FileOutputStream writer = new FileOutputStream(filePath);

            //leggo i byte dal socket e li scrivo sul file
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = reader.read(buffer)) > 0) {
                writer.write(buffer, 0, lengthRead);
                //writer.flush();
            }

            System.out.println("File ricevuto.");

            //chiusura stream e socket
            writer.close();
            reader.close();
            miSocket.close();
            System.out.println("File ricevuto con successo dal server.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione con il server!");
            System.exit(1);
        }
    }
}