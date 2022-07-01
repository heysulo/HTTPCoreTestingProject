package client;

import client.helpers.RequestMethods;
import client.helpers.TestPayloads;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ClosesConnectionWhileReceivingResponseHeaders {

    private String host = "localhost";
    private int port = 8290;

    public static void main(String[] args) {

        ClosesConnectionWhileReceivingResponseHeaders client = new ClosesConnectionWhileReceivingResponseHeaders();
//        for (int i = 0; i < 1000; i++) {
        client.run();
//        }
    }

    ClosesConnectionWhileReceivingResponseHeaders() {

    }

    ClosesConnectionWhileReceivingResponseHeaders(String host, int port) {

        this.host = host;
        this.port = port;
    }

    // Start to run the server
    public void run() {

        try {
            // Create socket
            Socket socket = new Socket(this.host, this.port);
            socket.setSendBufferSize(12000);

            System.out.println("client started");
            new ClosesConnectionWhileReceivingResponseHeaders.ClientThread(socket).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thread handling the socket to server
    static class ClientThread extends Thread {

        private Socket socket = null;

        ClientThread(Socket socket) {

            this.socket = socket;
        }

        public void run() {

            try {
                // Start handling application content
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
                // Write data

                String payload = TestPayloads.FULL_PAYLOAD;
                RequestMethods method = RequestMethods.PUT;

                printWriter.print(method + " /test HTTP/1.1\r\n");
                printWriter.print("Accept: application/json\r\n");
                printWriter.print("Connection: keep-alive\r\n");
                printWriter.print("Content-Type: application/json\r\n");
                if (!method.equals(RequestMethods.GET)) {
                    printWriter.print("Content-Length: " + payload.length() + "\r\n");
                }

                printWriter.print("\r\n");
                if (!method.equals(RequestMethods.GET)) {
                    printWriter.print(payload);
                }
                printWriter.flush();
                String line = null;
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    i++;
                    System.out.println("Inut : " + line);
                    if (i == 5) {
                        // close the socket while receiving the response
                        socket.close();
                        System.exit(-1);
                    }
                }
                printWriter.close();
                bufferedReader.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
