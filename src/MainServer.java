import modelo.Despachador;

import java.net.*;
import java.io.*;

public class MainServer {
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);

        try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                while (true) {
                    Socket clientSocket = serverSocket.accept();

                    Despachador lector = new Despachador(clientSocket, "lector");
                    lector.start();

                    Despachador escritor = new Despachador(clientSocket, "escritor");
                    escritor.start();

                    //clientSocket.close();
                }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
