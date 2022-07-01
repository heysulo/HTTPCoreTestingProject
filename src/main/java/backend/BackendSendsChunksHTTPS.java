package backend;

import org.apache.commons.io.FileUtils;
import util.Utils;

import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class BackendSendsChunksHTTPS {

    public static final String CRLF = "\r\n";

    public static void main(String[] args) throws Exception {
        File file = Utils.getFile("payload-large.json");
        String content = FileUtils.readFileToString(file, "UTF-8");

//        String content = "{\"Hello\":\"World\"}";

        try {
            // Create a ServerSocket to listen on that port.
            System.setProperty("javax.net.ssl.keyStore", "/Users/apple/.wum3/products/wso2mi/4.0.0/wso2mi-4.0.0_http_core_testing/repository/resources/security/wso2carbon.jks");
            System.setProperty("javax.net.ssl.keyStorePassword", "wso2carbon");
            ServerSocketFactory ssf = SSLServerSocketFactory.getDefault();
            ServerSocket ss = ssf.createServerSocket(7005);
            System.out.println("SSL Server Started!");

            // Now enter an infinite loop, waiting for & handling connections.
            do {
                Socket client = ss.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                StringBuilder sb = new StringBuilder();
                char[] buf = new char[10];
                StringBuilder outt = new StringBuilder();
                int read = in.read(buf);
                System.out.println("entering loop");
                while (read > 0) {
                    try {
                        read = in.read(buf);
                        outt.append(buf, 0, read);
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("out of loop");
                PrintStream out = new PrintStream(client.getOutputStream());

                // Start sending our reply, using the HTTP 1.1 protocol
                //out.print(0 + "\r\n");
                out.print("HTTP/1.1 200 OK\r\n"); // Version & status code
                out.print("Access-Control-Expose-Headers:\r\n");
                out.print("Access-Control-Allow-Origin: *\r\n");
                out.print("X-Correlation-ID: 9f22c69b-6673-4326-8aff-0c0c097cd3c0\r\n");
                out.print(
                        "Access-Control-Allow-Headers: authorization,Access-Control-Allow-Origin,Content-Type," +
                                "SOAPAction,apikey,testKey,Authorization\r\n");
                out.print("Content-Type: application/json\r\n");
                out.print("Date: Tue, 14 Dec 2021 08:15:17 GMT\r\n");
                out.print("Transfer-Encoding: chunked\r\n");
                ; // The type of data
                out.print("Connection: keep-alive\r\n");
                out.print("\r\n");

                InputStream stream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));

                int chunkSize = 50;
                int count;
                byte[] buffer = new byte[chunkSize];

                while ((count = stream.read(buffer)) > 0) {
                    out.printf("%x" + "\r\n", count);
                    out.write(buffer, 0, count);
                    out.print(CRLF);
                }

                out.print("0" + CRLF);
                out.print(CRLF);
                out.flush();

                out.close();
                in.close();
                client.close();
            } while (true); // Now loop again, waiting for the next connection
        }
        // If anything goes wrong, print an error message
        catch (Exception e) {
            System.err.println(e);
            System.err.println("Usage: java HttpMirror <port>");
        }
    }
}
