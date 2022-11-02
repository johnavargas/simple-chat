import gui.Ventana;
import modelo.Despachador;

import java.net.*;
import java.io.*;

public class MainClient {
    public static void main(String[] args) {
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        Ventana gui = new Ventana();
        gui.setVisible(true);

        try {
            Socket kkSocket = new Socket(hostName, portNumber);

            Despachador lector = new Despachador(kkSocket, "lector");
            lector.gui = gui;
            lector.start();

            Despachador escritor = new Despachador(kkSocket, "escritor");
            escritor.gui = gui;
            gui.despachador = escritor;
            escritor.start();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
