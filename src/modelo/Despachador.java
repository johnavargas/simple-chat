package modelo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Despachador extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private String tipo = "lector";
    private Socket socket;

    public Despachador(Socket socket, String tipo) {
        try {
            this.in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.socket = socket;
            this.tipo = tipo;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void run(){
        try {
            if (tipo.equals("lector")) {
                leer();
            } else {
                escribir();
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void leer() throws IOException  {
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Recibido: " + inputLine);
            if (inputLine.equals("Bye.")) {
                out.println(inputLine);
                socket.close();
                System.out.println("Cerrando conexion ");
                break;
            }
        }
    }

    private void escribir() throws IOException {

        BufferedReader stdIn =
                new BufferedReader(new InputStreamReader(System.in));

        String inputLine = stdIn.readLine();
        while (inputLine != null) {
            System.out.println("Enviado: " + inputLine);
            out.println(inputLine);

            if (inputLine.equals("Bye.")) {
                socket.close();
                break;
            }

            inputLine = stdIn.readLine();
        }
    }
}
