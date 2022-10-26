import java.net.*;
import java.io.*;

public class MainServer {
    public static void main(String[] args) {
        int portNumber = Integer.parseInt(args[0]);

        try {
                ServerSocket serverSocket = new ServerSocket(portNumber);
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    PrintWriter out =
                            new PrintWriter(clientSocket.getOutputStream(), true);
                    BufferedReader in = new BufferedReader(
                            new  InputStreamReader(clientSocket.getInputStream()));

                    out.println("Bienvenido");

                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println(inputLine);
                        if (inputLine.equals("Bye."))
                            break;
                    }
                    clientSocket.close();
                }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
