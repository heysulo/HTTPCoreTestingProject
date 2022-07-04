package client;

import client.helpers.RequestMethods;
import client.helpers.TestPayloads;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class ClientSendsInvalidChunksHTTPS {

    private String host = "localhost";
    private int port = 8253;

    public static void main(String[] args) {

        ClientSendsInvalidChunksHTTPS client = new ClientSendsInvalidChunksHTTPS();
//        for (int i = 0; i < 1000; i++) {
        client.run();
//        }
    }

    ClientSendsInvalidChunksHTTPS() {

    }

    ClientSendsInvalidChunksHTTPS(String host, int port) {

        this.host = host;
        this.port = port;
    }

    // Create the and initialize the SSLContext
    private SSLContext createSSLContext() {

        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(
                            "/Users/sulochana/Documents/WSO2/bin/test/wso2mi-4.0.0/repository/resources/security/wso2carbon.jks"),
                    "wso2carbon".toCharArray());

            // Create key manager
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, "wso2carbon".toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();

            // Create trust manager
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();

            // Initialize SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(km, tm, null);

            return sslContext;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    // Start to run the server
    public void run() {

        SSLContext sslContext = this.createSSLContext();

        try {
            // Create socket factory
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            // Create socket
            SSLSocket sslSocket = (SSLSocket) sslSocketFactory.createSocket(this.host, this.port);

            System.out.println("SSL client started");
            new ClientThread(sslSocket).start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Thread handling the socket to server
    static class ClientThread extends Thread {

        private SSLSocket sslSocket = null;

        ClientThread(SSLSocket sslSocket) {

            this.sslSocket = sslSocket;
        }

        public void run() {

            sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());

            try {
                // Start handshake
                sslSocket.startHandshake();

                // Get session after the connection is established
                SSLSession sslSession = sslSocket.getSession();

                System.out.println("SSLSession :");
                System.out.println("\tProtocol : " + sslSession.getProtocol());
                System.out.println("\tCipher suite : " + sslSession.getCipherSuite());

                // Start handling application content
                InputStream inputStream = sslSocket.getInputStream();
                OutputStream outputStream = sslSocket.getOutputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                PrintStream printWriter = new PrintStream(outputStream);
                // Write data

                String payload = TestPayloads.SMALL_PAYLOAD;;
                RequestMethods method = RequestMethods.POST;

                printWriter.print(method + " /test HTTP/1.1\r\n");
                printWriter.print("Accept: application/json\r\n");
                printWriter.print("Connection: keep-alive\r\n");
                printWriter.print("Transfer-Encoding: chunked\n");
                printWriter
                        .print("Authorization: Bearer eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkEiLCJraWQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZ19SUzI1NiIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiY1o4Q2xHUE9rblBkZFo1eEpNZl9CX2k3VFBRYSIsIm5iZiI6MTY1MjIxNjQ5NiwiYXpwIjoiY1o4Q2xHUE9rblBkZFo1eEpNZl9CX2k3VFBRYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1MjIyMDA5NiwiaWF0IjoxNjUyMjE2NDk2LCJqdGkiOiI0ZjliYjkzNy1iNzU4LTQ5MWEtYTM0ZC00Y2MyOTI2OWFhNzIifQ.tru_XsD-kGj2-Eaxsij4f55kM21LsDTKE7voW7SGhcZ2EllVJJBZdL7y_L8Jwv1tMWbfm_i5iHgtrnLrXJY3zUItpOU6IT04oBrFhiI2n4AWC138TeZvJXmH8W2ZAz2vddGpHogtvUwP5Ga_DT43Rtnh0PyTXySOlZQvL6LjR0oiWqjJaIMuuIohsNgRhVdjN8AgSeF2pb_h9jVJStkcK5eIHoom2ZZQeqr0EgtkJgCnft-Z143_83_KUe3pyAU4pzYYhVMTjYPKXIVhx56Z-HSt7UHCe2f1cu_viAyff-LzNcfpyBfj2u5rzTiYlfLtnKVM8ilS7b8hmH307oXb4A\r\n");
                printWriter
                        .print("DB-ID: eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0d0Y2Ym8tVXRuTFg4VTVoSjNPUkVhQlVVcDB2SVpqMTVUU2NVNUpKcmU4In0.eyJqdGkiOiJlYzdkZGIxNC1hMGMxLTRhMzItYWFmNC05MDdlMDk4OTQxN2YiLCJleHAiOjE2NTA3MjQ3MzksIm5iZiI6MTY1MDY4ODczOSwiaWF0IjoxNjUwNjg4NzM5LCJpc3MiOiJodHRwczovL2VpZHAtdWF0LmRlLmRiLmNvbS9hdXRoL3JlYWxtcy9nbG9iYWwiLCJhdWQiOiIxMTUwOTUtMV9MZW5kaW5nU2VydmljZUxheWVyIiwic3ViIjoiZmI4OTZmMjEtYzQwZi00YjUzLWE4YWMtODVhZTRmNjcxMmJlIiwidHlwIjoiRWlkcC1BdXRoeiIsImF6cCI6IjExNTA5NS0xX0xlbmRpbmdTZXJ2aWNlTGF5ZXIiLCJuYXJpZCI6IjExNTA5NS0xIiwibGVnaXRpbWF0aW9ucyI6W3siaWQiOiJBMjMwMDgiLCJndm8iOlsiUEE3L1BBUlROIiwiUEE3L1NFQSJdLCJsZ19uYXJpZCI6IjExNTA5NS0xIiwibGVnaV9hdXRoIjpbImxnOlBBNy9QQVJUTiIsImxnOlBBNy9TRUEiXSwiaWF0IjoxNjUwNjg4NzM5fV0sImRibGVnaWlkIjoiQTIzMDA4IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYTIzMDA4IiwibWVtYmVyc2hpcHMiOltdfQ.TbU0ygbWdXQWtegC-tF7Dzl6uYECLSL1mMHQ43ls74g29W4SlAMQcruQVcydF69mSd0vbruTaRvrEG7CwyAIlFF8cYbRs62eQ6BDIim6WhFa0tOmLPRZ63gNGyVcpCbQisXjtzeFDYO6bq0eToTY_dntMkp6lsMXmgwOCVGXg1yopQnsl7XqrfRkZbwukeWBTQ3lbJYIkEIjqrDC1nU1fr9qwN6r2ntp71dGnqsiy6sZRQvlCKLlZSZ_NfWGuz4s-yxd9DFhIcSsvfSUhTuSZThJfw3_CCOSBTWB6Q4r0O9lHetwjI2h6-7DX2WZK_zl61nem1h1rd-EkcIjVU7uxg\r\n");
                printWriter.print("Content-Type: application/json\r\n");
                printWriter.print("\r\n");

                InputStream stream = new ByteArrayInputStream(payload.getBytes(StandardCharsets.UTF_8));

                int chunkSize = 50;
                int count;
                byte[] buffer = new byte[chunkSize];

                while ((count = stream.read(buffer)) > 0) {
                    printWriter.print(count + "\r\n");
                    printWriter.write(buffer, 0, count);
                    printWriter.print("\r\n");
//                    printWriter.flush();
                }

                printWriter.print("0" + "\r\n");
//                printWriter.print(CRLF);
                printWriter.flush();

                String line = null;
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    i++;
                    System.out.println("Inut : " + line);
                    if (line.trim().equals("0")) {
                        break;
                    }
                }
                printWriter.close();
                bufferedReader.close();
                sslSocket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}