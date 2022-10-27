import modelo.Despachador;

import java.net.*;
import java.io.*;

public class MainClient {
    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try {
            Socket kkSocket = new Socket(hostName, portNumber);

            Despachador lector = new Despachador(kkSocket, "lector");
            lector.start();

            Despachador escritor = new Despachador(kkSocket, "escritor");
            escritor.start();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
