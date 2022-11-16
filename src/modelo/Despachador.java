package modelo;

import gui.Ventana;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Despachador extends Thread {
    private PrintWriter out;
    private BufferedReader in;
    private String tipo = "lector";
    private Socket socket;
    public Ventana gui = null;
    public ArrayList<Despachador> escritores;
    private String nickname;

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

            String[] datos = inputLine.split(":");

            if (gui != null) {  // Cliente
                if (datos[0].equals("login")) {
                    gui.usuarios.setText(datos[1].replaceAll(",", "\n"));
                } else if (datos[0].equals("msg")) {
                    gui.salida.append(datos[1] + "\n");
                }
            }

            if (gui == null) {  // Servidor
                if (datos[0].equals("login")) {
                    escritores.get(escritores.size()-1).nickname = datos[1];
                    String listaNickNames = "";

                    for (Despachador e: escritores) {
                        listaNickNames += e.nickname + ",";
                    }

                    for (Despachador e: escritores) {
                        e.send("login:"+listaNickNames);
                    }
                } else if (datos[0].equals("msg")) {
                    for (Despachador e: escritores) {
                        e.send(inputLine);
                    }
                }
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

    public void send(String inputLine)
    {
        try {
            System.out.println("Enviando: " + inputLine);
            out.println(inputLine);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
