package client;

import client.helpers.RequestMethods;
import client.helpers.TestPayloads;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientSendsInvalidChunks {

    private String host = "localhost";
    private int port = 8290;
    public static final String CRLF = "\r\n";

    static String payload = "[\n" +
            "   {\n" +
            "      \"id\":\"2489651045\",\n" +
            "      \"type\":\"CreateEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":665991,\n" +
            "         \"login\":\"petroav\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/petroav\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/665991?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28688495,\n" +
            "         \"name\":\"petroav/6.828\",\n" +
            "         \"url\":\"https://api.github.com/repos/petroav/6.828\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"ref\":\"master\",\n" +
            "         \"ref_type\":\"branch\",\n" +
            "         \"master_branch\":\"master\",\n" +
            "         \"description\":\"Solution to homework and assignments from MIT's 6.828 (Operating Systems Engineering). Done in my spare time.\",\n" +
            "         \"pusher_type\":\"user\"\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:00Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651051\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":3854017,\n" +
            "         \"login\":\"rspt\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/rspt\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/3854017?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28671719,\n" +
            "         \"name\":\"rspt/rspt-theme\",\n" +
            "         \"url\":\"https://api.github.com/repos/rspt/rspt-theme\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863970,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"6b089eb4a43f728f0a594388092f480f2ecacfcd\",\n" +
            "         \"before\":\"437c03652caa0bc4a7554b18d5c0a394c2f3d326\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"6b089eb4a43f728f0a594388092f480f2ecacfcd\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"5c682c2d1ec4073e277f9ba9f4bdf07e5794dabe@rspt.ch\",\n" +
            "                  \"name\":\"rspt\"\n" +
            "               },\n" +
            "               \"message\":\"Fix main header height on mobile\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/rspt/rspt-theme/commits/6b089eb4a43f728f0a594388092f480f2ecacfcd\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:01Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651053\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":6339799,\n" +
            "         \"login\":\"izuzero\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/izuzero\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/6339799?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28270952,\n" +
            "         \"name\":\"izuzero/xe-module-ajaxboard\",\n" +
            "         \"url\":\"https://api.github.com/repos/izuzero/xe-module-ajaxboard\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863972,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/develop\",\n" +
            "         \"head\":\"ec819b9df4fe612bb35bf562f96810bf991f9975\",\n" +
            "         \"before\":\"590433109f221a96cf19ea7a7d9a43ca333e3b3e\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"ec819b9df4fe612bb35bf562f96810bf991f9975\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"df05f55543db3c62cf64f7438018ec37f3605d3c@gmail.com\",\n" +
            "                  \"name\":\"Eunsoo Lee\"\n" +
            "               },\n" +
            "               \"message\":\"20 sample\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/izuzero/xe-module-ajaxboard/commits/ec819b9df4fe612bb35bf562f96810bf991f9975\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:01Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651057\",\n" +
            "      \"type\":\"WatchEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":6894991,\n" +
            "         \"login\":\"SametSisartenep\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/SametSisartenep\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/6894991?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":2871998,\n" +
            "         \"name\":\"visionmedia/debug\",\n" +
            "         \"url\":\"https://api.github.com/repos/visionmedia/debug\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"action\":\"started\"\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:03Z\",\n" +
            "      \"org\":{\n" +
            "         \"id\":9285252,\n" +
            "         \"login\":\"visionmedia\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/orgs/visionmedia\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/9285252?\"\n" +
            "      }\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651062\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":485033,\n" +
            "         \"login\":\"winterbe\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/winterbe\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/485033?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28593843,\n" +
            "         \"name\":\"winterbe/streamjs\",\n" +
            "         \"url\":\"https://api.github.com/repos/winterbe/streamjs\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863975,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"15b303203be31bd295bc831075da8f74b99b3981\",\n" +
            "         \"before\":\"0fef99f604154ccfe1d2fcd0aadeffb5c58e43ff\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"15b303203be31bd295bc831075da8f74b99b3981\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"52a47bffd52d9cea1ee1362f2bd0c5f87fac9262@googlemail.com\",\n" +
            "                  \"name\":\"Benjamin Winterberg\"\n" +
            "               },\n" +
            "               \"message\":\"Add comparator support for min, max operations\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/winterbe/streamjs/commits/15b303203be31bd295bc831075da8f74b99b3981\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:03Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651063\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":4319954,\n" +
            "         \"login\":\"hermanwahyudi\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/hermanwahyudi\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/4319954?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":27826205,\n" +
            "         \"name\":\"hermanwahyudi/selenium\",\n" +
            "         \"url\":\"https://api.github.com/repos/hermanwahyudi/selenium\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863976,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":0,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"1b58dd4c4e14ea9cf5212b981774bd448a266c3c\",\n" +
            "         \"before\":\"20b10e3a605bd177efff62f1130943774ac07bf3\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"1b58dd4c4e14ea9cf5212b981774bd448a266c3c\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"2bb20d8a71fb7adbc1d6239cc9ff4130f26819dc@gmail.com\",\n" +
            "                  \"name\":\"Herman\"\n" +
            "               },\n" +
            "               \"message\":\"Update README.md\",\n" +
            "               \"distinct\":false,\n" +
            "               \"url\":\"https://api.github.com/repos/hermanwahyudi/selenium/commits/1b58dd4c4e14ea9cf5212b981774bd448a266c3c\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:03Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651064\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":2881602,\n" +
            "         \"login\":\"jdilt\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/jdilt\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/2881602?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28682546,\n" +
            "         \"name\":\"jdilt/jdilt.github.io\",\n" +
            "         \"url\":\"https://api.github.com/repos/jdilt/jdilt.github.io\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863977,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\",\n" +
            "         \"before\":\"8515c4a9efb40332659e4389821a73800ce6a4bf\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"3e9bbe622d800410f1d4d0a4bb92004e147f1b1e@163.com\",\n" +
            "                  \"name\":\"jdilt\"\n" +
            "               },\n" +
            "               \"message\":\"refine index page and about page\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/jdilt/jdilt.github.io/commits/d13cbd1e5c68b189fc91cfa14fdae1f52ef6f9e1\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:03Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651066\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":3495129,\n" +
            "         \"login\":\"sundaymtn\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/sundaymtn\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/3495129?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":24147122,\n" +
            "         \"name\":\"sundaymtn/waterline\",\n" +
            "         \"url\":\"https://api.github.com/repos/sundaymtn/waterline\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863979,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\",\n" +
            "         \"before\":\"a7dba8faf22d2f342b7398ff76bfd10a30106191\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"7fbc091194a9488bfb16868527a7c3a8ba469dba@gmail.com\",\n" +
            "                  \"name\":\"Seth Carter\"\n" +
            "               },\n" +
            "               \"message\":\"Thu Jan  1 10:00:02 EST 2015\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/sundaymtn/waterline/commits/2a2ec35bfefb9341b1df2f213aad1dac804bc2ea\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:04Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651067\",\n" +
            "      \"type\":\"PushEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":10363514,\n" +
            "         \"login\":\"zhouzhi2015\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/zhouzhi2015\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/10363514?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":28686619,\n" +
            "         \"name\":\"zhouzhi2015/temp\",\n" +
            "         \"url\":\"https://api.github.com/repos/zhouzhi2015/temp\"\n" +
            "      },\n" +
            "      \"payload\":{\n" +
            "         \"push_id\":536863980,\n" +
            "         \"size\":1,\n" +
            "         \"distinct_size\":1,\n" +
            "         \"ref\":\"refs/heads/master\",\n" +
            "         \"head\":\"22019c081480435bb7d6e629766f2204c6c219bd\",\n" +
            "         \"before\":\"d5926ef8c6a8a43724f8dc94007c3c5a918391c3\",\n" +
            "         \"commits\":[\n" +
            "            {\n" +
            "               \"sha\":\"22019c081480435bb7d6e629766f2204c6c219bd\",\n" +
            "               \"author\":{\n" +
            "                  \"email\":\"421c4f4cb8c7fe07ea1166286558dc42a56cf3a7\",\n" +
            "                  \"name\":\"1184795629@qq.com\"\n" +
            "               },\n" +
            "               \"message\":\"测测\",\n" +
            "               \"distinct\":true,\n" +
            "               \"url\":\"https://api.github.com/repos/zhouzhi2015/temp/commits/22019c081480435bb7d6e629766f2204c6c219bd\"\n" +
            "            }\n" +
            "         ]\n" +
            "      },\n" +
            "      \"public\":true,\n" +
            "      \"created_at\":\"2015-01-01T15:00:04Z\"\n" +
            "   },\n" +
            "   {\n" +
            "      \"id\":\"2489651071\",\n" +
            "      \"type\":\"ReleaseEvent\",\n" +
            "      \"actor\":{\n" +
            "         \"id\":7659931,\n" +
            "         \"login\":\"petrkutalek\",\n" +
            "         \"gravatar_id\":\"\",\n" +
            "         \"url\":\"https://api.github.com/users/petrkutalek\",\n" +
            "         \"avatar_url\":\"https://avatars.githubusercontent.com/u/7659931?\"\n" +
            "      },\n" +
            "      \"repo\":{\n" +
            "         \"id\":20029610,\n" +
            "         \"name\":\"petrkutalek/png2pos\",\n" +
            "         \"url\":\"https://api.github.com/repos/petrkutalek/png2pos\"\n" +
            "      }\n" +
            "   }\n" +
            "]";

    static String payloadSmall = "{\"generalData\":{\"partnerClassification\":\"employedPerson\"," +
            "\"contexts\":[\"customer\"]},\"checkData\":{\"name\":\"Samsa\",\"firstName\":\"Gregor\",\"birthDate\":\"1963-02-25\",\"nationality\":\"DEU\",\"economicArea\":\"\",\"countryOfResidence\":0,\"industrySectors\":{\"affiliations\":[{}]}}}";

    public static void main(String[] args) {

        ClientSendsInvalidChunks client = new ClientSendsInvalidChunks();
        //        for (int i = 0; i < 1000; i++) {
        client.run();
//        }
    }

    ClientSendsInvalidChunks() {

    }

    ClientSendsInvalidChunks(String host, int port) {

        this.host = host;
        this.port = port;
    }

    // Start to run the server
    public void run() {

        try {
            // Create socket
            Socket socket = new Socket(this.host, this.port);

            System.out.println("client started");
            new ClientSendsInvalidChunks.ClientThread(socket).start();
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
                    printWriter.print(CRLF);
//                    printWriter.flush();
                }

                printWriter.print("0" + CRLF);
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
                socket.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}