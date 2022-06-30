package client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class ClosesConnectionWhileReceivingResponseHeadersHTTPS {

    private String host = "localhost";
    private int port = 8253;

    public static void main(String[] args) {

        ClosesConnectionWhileReceivingResponseHeadersHTTPS client = new ClosesConnectionWhileReceivingResponseHeadersHTTPS();
        for (int i = 0; i < 1000; i++) {
            client.run();
        }
    }

    ClosesConnectionWhileReceivingResponseHeadersHTTPS() {

    }

    ClosesConnectionWhileReceivingResponseHeadersHTTPS(String host, int port) {

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
                PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
                // Write data

                String payload = "[\n" +
                        "    {\n" +
                        "      \"id\": \"2489651045\",\n" +
                        "      \"type\": \"CreateEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 665991,\n" +
                        "        \"login\": \"petroav\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/petroav\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/665991?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28688495,\n" +
                        "        \"name\": \"petroav/6.828\",\n" +
                        "        \"url\": \"https://api.github.com/repos/petroav/6.828\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"ref\": \"master\",\n" +
                        "        \"ref_type\": \"branch\",\n" +
                        "        \"master_branch\": \"master\",\n" +
                        "        \"description\": \"Solution to homework and assignments from MIT's 6.828 (Operating Systems Engineering). Done in my spare time.\",\n" +
                        "        \"pusher_type\": \"user\"\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:00Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651051\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 3854017,\n" +
                        "        \"login\": \"rspt\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/rspt\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/3854017?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28671719,\n" +
                        "        \"name\": \"rspt/rspt-theme\",\n" +
                        "        \"url\": \"https://api.github.com/repos/rspt/rspt-theme\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863970,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"6b089eb4a43f728f0a594388092f480f2ecacfcd\",\n" +
                        "        \"before\": \"437c03652caa0bc4a7554b18d5c0a394c2f3d326\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"6b089eb4a43f728f0a594388092f480f2ecacfcd\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"5c682c2d1ec4073e277f9ba9f4bdf07e5794dabe@rspt.ch\",\n" +
                        "              \"name\": \"rspt\"\n" +
                        "            },\n" +
                        "            \"message\": \"Fix main header height on mobile\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/rspt/rspt-theme/commits/6b089eb4a43f728f0a594388092f480f2ecacfcd\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:01Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651053\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 6339799,\n" +
                        "        \"login\": \"izuzero\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/izuzero\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/6339799?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28270952,\n" +
                        "        \"name\": \"izuzero/xe-module-ajaxboard\",\n" +
                        "        \"url\": \"https://api.github.com/repos/izuzero/xe-module-ajaxboard\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863972,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/develop\",\n" +
                        "        \"head\": \"ec819b9df4fe612bb35bf562f96810bf991f9975\",\n" +
                        "        \"before\": \"590433109f221a96cf19ea7a7d9a43ca333e3b3e\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"ec819b9df4fe612bb35bf562f96810bf991f9975\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"df05f55543db3c62cf64f7438018ec37f3605d3c@gmail.com\",\n" +
                        "              \"name\": \"Eunsoo Lee\"\n" +
                        "            },\n" +
                        "            \"message\": \"20 sample\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/izuzero/xe-module-ajaxboard/commits/ec819b9df4fe612bb35bf562f96810bf991f9975\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:01Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651057\",\n" +
                        "      \"type\": \"WatchEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 6894991,\n" +
                        "        \"login\": \"SametSisartenep\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/SametSisartenep\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/6894991?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 2871998,\n" +
                        "        \"name\": \"visionmedia/debug\",\n" +
                        "        \"url\": \"https://api.github.com/repos/visionmedia/debug\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"action\": \"started\"\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:03Z\",\n" +
                        "      \"org\": {\n" +
                        "        \"id\": 9285252,\n" +
                        "        \"login\": \"visionmedia\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/orgs/visionmedia\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/9285252?\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651062\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 485033,\n" +
                        "        \"login\": \"winterbe\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/winterbe\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/485033?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28593843,\n" +
                        "        \"name\": \"winterbe/streamjs\",\n" +
                        "        \"url\": \"https://api.github.com/repos/winterbe/streamjs\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863975,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"15b303203be31bd295bc831075da8f74b99b3981\",\n" +
                        "        \"before\": \"0fef99f604154ccfe1d2fcd0aadeffb5c58e43ff\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"15b303203be31bd295bc831075da8f74b99b3981\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"52a47bffd52d9cea1ee1362f2bd0c5f87fac9262@googlemail.com\",\n" +
                        "              \"name\": \"Benjamin Winterberg\"\n" +
                        "            },\n" +
                        "            \"message\": \"Add comparator support for min, max operations\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/winterbe/streamjs/commits/15b303203be31bd295bc831075da8f74b99b3981\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:03Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651063\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 4319954,\n" +
                        "        \"login\": \"hermanwahyudi\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/hermanwahyudi\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/4319954?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 27826205,\n" +
                        "        \"name\": \"hermanwahyudi/selenium\",\n" +
                        "        \"url\": \"https://api.github.com/repos/hermanwahyudi/selenium\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863976,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 0,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"1b58dd4c4e14ea9cf5212b981774bd448a266c3c\",\n" +
                        "        \"before\": \"20b10e3a605bd177efff62f1130943774ac07bf3\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"1b58dd4c4e14ea9cf5212b981774bd448a266c3c\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"2bb20d8a71fb7adbc1d6239cc9ff4130f26819dc@gmail.com\",\n" +
                        "              \"name\": \"Herman\"\n" +
                        "            },\n" +
                        "            \"message\": \"Update README.md\",\n" +
                        "            \"distinct\": false,\n" +
                        "            \"url\": \"https://api.github.com/repos/hermanwahyudi/selenium/commits/1b58dd4c4e14ea9cf5212b981774bd448a266c3c\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:03Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651064\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 2881602,\n" +
                        "        \"login\": \"jdilt\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/jdilt\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/2881602?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28682546,\n" +
                        "        \"name\": \"jdilt/jdilt.github.io\",\n" +
                        "        \"url\": \"https://api.github.com/repos/jdilt/jdilt.github.io\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863977,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\",\n" +
                        "        \"before\": \"8515c4a9efb40332659e4389821a73800ce6a4bf\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"3e9bbe622d800410f1d4d0a4bb92004e147f1b1e@163.com\",\n" +
                        "              \"name\": \"jdilt\"\n" +
                        "            },\n" +
                        "            \"message\": \"refine index page and about page\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/jdilt/jdilt.github.io/commits/d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:03Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651066\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 3495129,\n" +
                        "        \"login\": \"sundaymtn\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/sundaymtn\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/3495129?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 24147122,\n" +
                        "        \"name\": \"sundaymtn/waterline\",\n" +
                        "        \"url\": \"https://api.github.com/repos/sundaymtn/waterline\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863979,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\",\n" +
                        "        \"before\": \"a7dba8faf22d2f342b7398ff76bfd10a30106191\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"7fbc091194a9488bfb16868527a7c3a8ba469dba@gmail.com\",\n" +
                        "              \"name\": \"Seth Carter\"\n" +
                        "            },\n" +
                        "            \"message\": \"Thu Jan  1 10:00:02 EST 2015\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/sundaymtn/waterline/commits/2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:04Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651067\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 10363514,\n" +
                        "        \"login\": \"zhouzhi2015\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/zhouzhi2015\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/10363514?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 28686619,\n" +
                        "        \"name\": \"zhouzhi2015/temp\",\n" +
                        "        \"url\": \"https://api.github.com/repos/zhouzhi2015/temp\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863980,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"22019c081480435bb7d6e629766f2204c6c219bd\",\n" +
                        "        \"before\": \"d5926ef8c6a8a43724f8dc94007c3c5a918391c3\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"22019c081480435bb7d6e629766f2204c6c219bd\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"421c4f4cb8c7fe07ea1166286558dc42a56cf3a7\",\n" +
                        "              \"name\": \"1184795629@qq.com\"\n" +
                        "            },\n" +
                        "            \"message\": \"测测\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/zhouzhi2015/temp/commits/22019c081480435bb7d6e629766f2204c6c219bd\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:04Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651071\",\n" +
                        "      \"type\": \"ReleaseEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 7659931,\n" +
                        "        \"login\": \"petrkutalek\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 20029610,\n" +
                        "        \"name\": \"petrkutalek/png2pos\",\n" +
                        "        \"url\": \"https://api.github.com/repos/petrkutalek/png2pos\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"action\": \"published\",\n" +
                        "        \"release\": {\n" +
                        "          \"url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/818676\",\n" +
                        "          \"assets_url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/818676/assets\",\n" +
                        "          \"upload_url\": \"https://uploads.github.com/repos/petrkutalek/png2pos/releases/818676/assets{?name}\",\n" +
                        "          \"html_url\": \"https://github.com/petrkutalek/png2pos/releases/tag/v1.5.4\",\n" +
                        "          \"id\": 818676,\n" +
                        "          \"tag_name\": \"v1.5.4\",\n" +
                        "          \"target_commitish\": \"master\",\n" +
                        "          \"name\": \"\",\n" +
                        "          \"draft\": false,\n" +
                        "          \"author\": {\n" +
                        "            \"login\": \"petrkutalek\",\n" +
                        "            \"id\": 7659931,\n" +
                        "            \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?v=3\",\n" +
                        "            \"gravatar_id\": \"\",\n" +
                        "            \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "            \"html_url\": \"https://github.com/petrkutalek\",\n" +
                        "            \"followers_url\": \"https://api.github.com/users/petrkutalek/followers\",\n" +
                        "            \"following_url\": \"https://api.github.com/users/petrkutalek/following{/other_user}\",\n" +
                        "            \"gists_url\": \"https://api.github.com/users/petrkutalek/gists{/gist_id}\",\n" +
                        "            \"starred_url\": \"https://api.github.com/users/petrkutalek/starred{/owner}{/repo}\",\n" +
                        "            \"subscriptions_url\": \"https://api.github.com/users/petrkutalek/subscriptions\",\n" +
                        "            \"organizations_url\": \"https://api.github.com/users/petrkutalek/orgs\",\n" +
                        "            \"repos_url\": \"https://api.github.com/users/petrkutalek/repos\",\n" +
                        "            \"events_url\": \"https://api.github.com/users/petrkutalek/events{/privacy}\",\n" +
                        "            \"received_events_url\": \"https://api.github.com/users/petrkutalek/received_events\",\n" +
                        "            \"type\": \"User\",\n" +
                        "            \"site_admin\": false\n" +
                        "          },\n" +
                        "          \"prerelease\": false,\n" +
                        "          \"created_at\": \"2015-01-01T14:56:44Z\",\n" +
                        "          \"published_at\": \"2015-01-01T15:00:05Z\",\n" +
                        "          \"assets\": [\n" +
                        "            {\n" +
                        "              \"url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/assets/362298\",\n" +
                        "              \"id\": 362298,\n" +
                        "              \"name\": \"png2pos-v1.5.4-linux.zip\",\n" +
                        "              \"label\": null,\n" +
                        "              \"uploader\": {\n" +
                        "                \"login\": \"petrkutalek\",\n" +
                        "                \"id\": 7659931,\n" +
                        "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?v=3\",\n" +
                        "                \"gravatar_id\": \"\",\n" +
                        "                \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "                \"html_url\": \"https://github.com/petrkutalek\",\n" +
                        "                \"followers_url\": \"https://api.github.com/users/petrkutalek/followers\",\n" +
                        "                \"following_url\": \"https://api.github.com/users/petrkutalek/following{/other_user}\",\n" +
                        "                \"gists_url\": \"https://api.github.com/users/petrkutalek/gists{/gist_id}\",\n" +
                        "                \"starred_url\": \"https://api.github.com/users/petrkutalek/starred{/owner}{/repo}\",\n" +
                        "                \"subscriptions_url\": \"https://api.github.com/users/petrkutalek/subscriptions\",\n" +
                        "                \"organizations_url\": \"https://api.github.com/users/petrkutalek/orgs\",\n" +
                        "                \"repos_url\": \"https://api.github.com/users/petrkutalek/repos\",\n" +
                        "                \"events_url\": \"https://api.github.com/users/petrkutalek/events{/privacy}\",\n" +
                        "                \"received_events_url\": \"https://api.github.com/users/petrkutalek/received_events\",\n" +
                        "                \"type\": \"User\",\n" +
                        "                \"site_admin\": false\n" +
                        "              },\n" +
                        "              \"content_type\": \"application/zip\",\n" +
                        "              \"state\": \"uploaded\",\n" +
                        "              \"size\": 37781,\n" +
                        "              \"download_count\": 0,\n" +
                        "              \"created_at\": \"2015-01-01T14:59:22Z\",\n" +
                        "              \"updated_at\": \"2015-01-01T14:59:23Z\",\n" +
                        "              \"browser_download_url\": \"https://github.com/petrkutalek/png2pos/releases/download/v1.5.4/png2pos-v1.5.4-linux.zip\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/assets/362297\",\n" +
                        "              \"id\": 362297,\n" +
                        "              \"name\": \"png2pos-v1.5.4-linux.zip.asc\",\n" +
                        "              \"label\": null,\n" +
                        "              \"uploader\": {\n" +
                        "                \"login\": \"petrkutalek\",\n" +
                        "                \"id\": 7659931,\n" +
                        "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?v=3\",\n" +
                        "                \"gravatar_id\": \"\",\n" +
                        "                \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "                \"html_url\": \"https://github.com/petrkutalek\",\n" +
                        "                \"followers_url\": \"https://api.github.com/users/petrkutalek/followers\",\n" +
                        "                \"following_url\": \"https://api.github.com/users/petrkutalek/following{/other_user}\",\n" +
                        "                \"gists_url\": \"https://api.github.com/users/petrkutalek/gists{/gist_id}\",\n" +
                        "                \"starred_url\": \"https://api.github.com/users/petrkutalek/starred{/owner}{/repo}\",\n" +
                        "                \"subscriptions_url\": \"https://api.github.com/users/petrkutalek/subscriptions\",\n" +
                        "                \"organizations_url\": \"https://api.github.com/users/petrkutalek/orgs\",\n" +
                        "                \"repos_url\": \"https://api.github.com/users/petrkutalek/repos\",\n" +
                        "                \"events_url\": \"https://api.github.com/users/petrkutalek/events{/privacy}\",\n" +
                        "                \"received_events_url\": \"https://api.github.com/users/petrkutalek/received_events\",\n" +
                        "                \"type\": \"User\",\n" +
                        "                \"site_admin\": false\n" +
                        "              },\n" +
                        "              \"content_type\": \"text/plain\",\n" +
                        "              \"state\": \"uploaded\",\n" +
                        "              \"size\": 495,\n" +
                        "              \"download_count\": 0,\n" +
                        "              \"created_at\": \"2015-01-01T14:59:21Z\",\n" +
                        "              \"updated_at\": \"2015-01-01T14:59:22Z\",\n" +
                        "              \"browser_download_url\": \"https://github.com/petrkutalek/png2pos/releases/download/v1.5.4/png2pos-v1.5.4-linux.zip.asc\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/assets/362299\",\n" +
                        "              \"id\": 362299,\n" +
                        "              \"name\": \"png2pos-v1.5.4-osx.zip\",\n" +
                        "              \"label\": null,\n" +
                        "              \"uploader\": {\n" +
                        "                \"login\": \"petrkutalek\",\n" +
                        "                \"id\": 7659931,\n" +
                        "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?v=3\",\n" +
                        "                \"gravatar_id\": \"\",\n" +
                        "                \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "                \"html_url\": \"https://github.com/petrkutalek\",\n" +
                        "                \"followers_url\": \"https://api.github.com/users/petrkutalek/followers\",\n" +
                        "                \"following_url\": \"https://api.github.com/users/petrkutalek/following{/other_user}\",\n" +
                        "                \"gists_url\": \"https://api.github.com/users/petrkutalek/gists{/gist_id}\",\n" +
                        "                \"starred_url\": \"https://api.github.com/users/petrkutalek/starred{/owner}{/repo}\",\n" +
                        "                \"subscriptions_url\": \"https://api.github.com/users/petrkutalek/subscriptions\",\n" +
                        "                \"organizations_url\": \"https://api.github.com/users/petrkutalek/orgs\",\n" +
                        "                \"repos_url\": \"https://api.github.com/users/petrkutalek/repos\",\n" +
                        "                \"events_url\": \"https://api.github.com/users/petrkutalek/events{/privacy}\",\n" +
                        "                \"received_events_url\": \"https://api.github.com/users/petrkutalek/received_events\",\n" +
                        "                \"type\": \"User\",\n" +
                        "                \"site_admin\": false\n" +
                        "              },\n" +
                        "              \"content_type\": \"application/zip\",\n" +
                        "              \"state\": \"uploaded\",\n" +
                        "              \"size\": 27891,\n" +
                        "              \"download_count\": 0,\n" +
                        "              \"created_at\": \"2015-01-01T14:59:30Z\",\n" +
                        "              \"updated_at\": \"2015-01-01T14:59:32Z\",\n" +
                        "              \"browser_download_url\": \"https://github.com/petrkutalek/png2pos/releases/download/v1.5.4/png2pos-v1.5.4-osx.zip\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "              \"url\": \"https://api.github.com/repos/petrkutalek/png2pos/releases/assets/362300\",\n" +
                        "              \"id\": 362300,\n" +
                        "              \"name\": \"png2pos-v1.5.4-osx.zip.asc\",\n" +
                        "              \"label\": null,\n" +
                        "              \"uploader\": {\n" +
                        "                \"login\": \"petrkutalek\",\n" +
                        "                \"id\": 7659931,\n" +
                        "                \"avatar_url\": \"https://avatars.githubusercontent.com/u/7659931?v=3\",\n" +
                        "                \"gravatar_id\": \"\",\n" +
                        "                \"url\": \"https://api.github.com/users/petrkutalek\",\n" +
                        "                \"html_url\": \"https://github.com/petrkutalek\",\n" +
                        "                \"followers_url\": \"https://api.github.com/users/petrkutalek/followers\",\n" +
                        "                \"following_url\": \"https://api.github.com/users/petrkutalek/following{/other_user}\",\n" +
                        "                \"gists_url\": \"https://api.github.com/users/petrkutalek/gists{/gist_id}\",\n" +
                        "                \"starred_url\": \"https://api.github.com/users/petrkutalek/starred{/owner}{/repo}\",\n" +
                        "                \"subscriptions_url\": \"https://api.github.com/users/petrkutalek/subscriptions\",\n" +
                        "                \"organizations_url\": \"https://api.github.com/users/petrkutalek/orgs\",\n" +
                        "                \"repos_url\": \"https://api.github.com/users/petrkutalek/repos\",\n" +
                        "                \"events_url\": \"https://api.github.com/users/petrkutalek/events{/privacy}\",\n" +
                        "                \"received_events_url\": \"https://api.github.com/users/petrkutalek/received_events\",\n" +
                        "                \"type\": \"User\",\n" +
                        "                \"site_admin\": false\n" +
                        "              },\n" +
                        "              \"content_type\": \"text/plain\",\n" +
                        "              \"state\": \"uploaded\",\n" +
                        "              \"size\": 495,\n" +
                        "              \"download_count\": 0,\n" +
                        "              \"created_at\": \"2015-01-01T14:59:30Z\",\n" +
                        "              \"updated_at\": \"2015-01-01T14:59:33Z\",\n" +
                        "              \"browser_download_url\": \"https://github.com/petrkutalek/png2pos/releases/download/v1.5.4/png2pos-v1.5.4-osx.zip.asc\"\n" +
                        "            }\n" +
                        "          ],\n" +
                        "          \"tarball_url\": \"https://api.github.com/repos/petrkutalek/png2pos/tarball/v1.5.4\",\n" +
                        "          \"zipball_url\": \"https://api.github.com/repos/petrkutalek/png2pos/zipball/v1.5.4\",\n" +
                        "          \"body\": \"\"\n" +
                        "        }\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:05Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651077\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 4070158,\n" +
                        "        \"login\": \"caleb-eades\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/caleb-eades\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/4070158?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 20469468,\n" +
                        "        \"name\": \"caleb-eades/MinecraftServers\",\n" +
                        "        \"url\": \"https://api.github.com/repos/caleb-eades/MinecraftServers\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863983,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"6ea9a1f5b0b3c4204272a5fe2587a5ee146c3a49\",\n" +
                        "        \"before\": \"8e94c95939b8f7db4c085da258698f07ae2b9cf3\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"6ea9a1f5b0b3c4204272a5fe2587a5ee146c3a49\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"5bbfe2c07a3ef0b22b72711a2edf1c023f6433c5@gmail.com\",\n" +
                        "              \"name\": \"caleb-eades\"\n" +
                        "            },\n" +
                        "            \"message\": \"Auto Snapshot Server State\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/caleb-eades/MinecraftServers/commits/6ea9a1f5b0b3c4204272a5fe2587a5ee146c3a49\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:05Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651078\",\n" +
                        "      \"type\": \"WatchEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 285289,\n" +
                        "        \"login\": \"comcxx11\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/comcxx11\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/285289?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 5569958,\n" +
                        "        \"name\": \"phpsysinfo/phpsysinfo\",\n" +
                        "        \"url\": \"https://api.github.com/repos/phpsysinfo/phpsysinfo\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"action\": \"started\"\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:05Z\",\n" +
                        "      \"org\": {\n" +
                        "        \"id\": 6797923,\n" +
                        "        \"login\": \"phpsysinfo\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/orgs/phpsysinfo\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/6797923?\"\n" +
                        "      }\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651080\",\n" +
                        "      \"type\": \"WatchEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 1757814,\n" +
                        "        \"login\": \"Soufien\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/Soufien\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/1757814?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 25873041,\n" +
                        "        \"name\": \"wasabeef/awesome-android-libraries\",\n" +
                        "        \"url\": \"https://api.github.com/repos/wasabeef/awesome-android-libraries\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"action\": \"started\"\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:05Z\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "      \"id\": \"2489651083\",\n" +
                        "      \"type\": \"PushEvent\",\n" +
                        "      \"actor\": {\n" +
                        "        \"id\": 9538449,\n" +
                        "        \"login\": \"hcremers\",\n" +
                        "        \"gravatar_id\": \"\",\n" +
                        "        \"url\": \"https://api.github.com/users/hcremers\",\n" +
                        "        \"avatar_url\": \"https://avatars.githubusercontent.com/u/9538449?\"\n" +
                        "      },\n" +
                        "      \"repo\": {\n" +
                        "        \"id\": 26101634,\n" +
                        "        \"name\": \"ktgw0316/LightZone-l10n-nl\",\n" +
                        "        \"url\": \"https://api.github.com/repos/ktgw0316/LightZone-l10n-nl\"\n" +
                        "      },\n" +
                        "      \"payload\": {\n" +
                        "        \"push_id\": 536863987,\n" +
                        "        \"size\": 1,\n" +
                        "        \"distinct_size\": 1,\n" +
                        "        \"ref\": \"refs/heads/master\",\n" +
                        "        \"head\": \"0fca01b12e6a8a1c537842d4831906d1eb4a277e\",\n" +
                        "        \"before\": \"fe610605ba48a87ee7c9bcf1a8a8db5f51bc4b58\",\n" +
                        "        \"commits\": [\n" +
                        "          {\n" +
                        "            \"sha\": \"0fca01b12e6a8a1c537842d4831906d1eb4a277e\",\n" +
                        "            \"author\": {\n" +
                        "              \"email\": \"8800578b51f022c8d8adb9606a8b3db4fedbdac6@192.168.0.167\",\n" +
                        "              \"name\": \"hans\"\n" +
                        "            },\n" +
                        "            \"message\": \"Translated by hcremers\",\n" +
                        "            \"distinct\": true,\n" +
                        "            \"url\": \"https://api.github.com/repos/ktgw0316/LightZone-l10n-nl/commits/0fca01b12e6a8a1c537842d4831906d1eb4a277e\"\n" +
                        "          }\n" +
                        "        ]\n" +
                        "      },\n" +
                        "      \"public\": true,\n" +
                        "      \"created_at\": \"2015-01-01T15:00:05Z\"\n" +
                        "    }\n" +
                        "  ]";

                String payloadSmall = "{\"generalData\":{\"partnerClassification\":\"employedPerson\"," +
                        "\"contexts\":[\"customer\"]},\"checkData\":{\"name\":\"Samsa\",\"firstName\":\"Gregor\",\"birthDate\":\"1963-02-25\",\"nationality\":\"DEU\",\"economicArea\":\"\",\"countryOfResidence\":0,\"industrySectors\":{\"affiliations\":[{}]}}}";

                printWriter.print("POST /test HTTP/1.1\r\n");
                printWriter.print("Accept: application/json\r\n");
                printWriter.print("Connection: keep-alive\r\n");
                printWriter
                        .print("Authorization: Bearer eyJ4NXQiOiJOVGRtWmpNNFpEazNOalkwWXpjNU1tWm1PRGd3TVRFM01XWXdOREU1TVdSbFpEZzROemM0WkEiLCJraWQiOiJNell4TW1Ga09HWXdNV0kwWldObU5EY3hOR1l3WW1NNFpUQTNNV0kyTkRBelpHUXpOR00wWkdSbE5qSmtPREZrWkRSaU9URmtNV0ZoTXpVMlpHVmxOZ19SUzI1NiIsImFsZyI6IlJTMjU2In0.eyJzdWIiOiJhZG1pbiIsImF1dCI6IkFQUExJQ0FUSU9OIiwiYXVkIjoiY1o4Q2xHUE9rblBkZFo1eEpNZl9CX2k3VFBRYSIsIm5iZiI6MTY1MjIxNjQ5NiwiYXpwIjoiY1o4Q2xHUE9rblBkZFo1eEpNZl9CX2k3VFBRYSIsInNjb3BlIjoiZGVmYXVsdCIsImlzcyI6Imh0dHBzOlwvXC9sb2NhbGhvc3Q6OTQ0M1wvb2F1dGgyXC90b2tlbiIsImV4cCI6MTY1MjIyMDA5NiwiaWF0IjoxNjUyMjE2NDk2LCJqdGkiOiI0ZjliYjkzNy1iNzU4LTQ5MWEtYTM0ZC00Y2MyOTI2OWFhNzIifQ.tru_XsD-kGj2-Eaxsij4f55kM21LsDTKE7voW7SGhcZ2EllVJJBZdL7y_L8Jwv1tMWbfm_i5iHgtrnLrXJY3zUItpOU6IT04oBrFhiI2n4AWC138TeZvJXmH8W2ZAz2vddGpHogtvUwP5Ga_DT43Rtnh0PyTXySOlZQvL6LjR0oiWqjJaIMuuIohsNgRhVdjN8AgSeF2pb_h9jVJStkcK5eIHoom2ZZQeqr0EgtkJgCnft-Z143_83_KUe3pyAU4pzYYhVMTjYPKXIVhx56Z-HSt7UHCe2f1cu_viAyff-LzNcfpyBfj2u5rzTiYlfLtnKVM8ilS7b8hmH307oXb4A\r\n");
                printWriter
                        .print("DB-ID: eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICI0d0Y2Ym8tVXRuTFg4VTVoSjNPUkVhQlVVcDB2SVpqMTVUU2NVNUpKcmU4In0.eyJqdGkiOiJlYzdkZGIxNC1hMGMxLTRhMzItYWFmNC05MDdlMDk4OTQxN2YiLCJleHAiOjE2NTA3MjQ3MzksIm5iZiI6MTY1MDY4ODczOSwiaWF0IjoxNjUwNjg4NzM5LCJpc3MiOiJodHRwczovL2VpZHAtdWF0LmRlLmRiLmNvbS9hdXRoL3JlYWxtcy9nbG9iYWwiLCJhdWQiOiIxMTUwOTUtMV9MZW5kaW5nU2VydmljZUxheWVyIiwic3ViIjoiZmI4OTZmMjEtYzQwZi00YjUzLWE4YWMtODVhZTRmNjcxMmJlIiwidHlwIjoiRWlkcC1BdXRoeiIsImF6cCI6IjExNTA5NS0xX0xlbmRpbmdTZXJ2aWNlTGF5ZXIiLCJuYXJpZCI6IjExNTA5NS0xIiwibGVnaXRpbWF0aW9ucyI6W3siaWQiOiJBMjMwMDgiLCJndm8iOlsiUEE3L1BBUlROIiwiUEE3L1NFQSJdLCJsZ19uYXJpZCI6IjExNTA5NS0xIiwibGVnaV9hdXRoIjpbImxnOlBBNy9QQVJUTiIsImxnOlBBNy9TRUEiXSwiaWF0IjoxNjUwNjg4NzM5fV0sImRibGVnaWlkIjoiQTIzMDA4IiwicHJlZmVycmVkX3VzZXJuYW1lIjoiYTIzMDA4IiwibWVtYmVyc2hpcHMiOltdfQ.TbU0ygbWdXQWtegC-tF7Dzl6uYECLSL1mMHQ43ls74g29W4SlAMQcruQVcydF69mSd0vbruTaRvrEG7CwyAIlFF8cYbRs62eQ6BDIim6WhFa0tOmLPRZ63gNGyVcpCbQisXjtzeFDYO6bq0eToTY_dntMkp6lsMXmgwOCVGXg1yopQnsl7XqrfRkZbwukeWBTQ3lbJYIkEIjqrDC1nU1fr9qwN6r2ntp71dGnqsiy6sZRQvlCKLlZSZ_NfWGuz4s-yxd9DFhIcSsvfSUhTuSZThJfw3_CCOSBTWB6Q4r0O9lHetwjI2h6-7DX2WZK_zl61nem1h1rd-EkcIjVU7uxg\r\n");
                printWriter.print("Content-Type: application/json\r\n");
                printWriter.print("Content-Length: " + payloadSmall.length() + "\r\n");
                printWriter.print("\r\n");
                printWriter.print(payloadSmall);
//                printWriter.print("\r\n");
                printWriter.flush();
//                sslSocket.close();
                String line = null;
                int i = 0;
                while ((line = bufferedReader.readLine()) != null) {
                    i++;
                    System.out.println("Inut : " + line);
                    if (i == 5) {
                        // close the socket while receiving the response
                        sslSocket.close();
                        System.exit(-1);
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
