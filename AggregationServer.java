// import java.io.*;
// import java.net.*;
// import java.util.*;
// import org.json.JSONObject;

// public class AggregationServer {
//     private static final int DEFAULT_PORT = 4567;
//     private static Map<String, JSONObject> weatherDataMap = new HashMap<>();  // Using JSONObject to store weather data

//     public static void main(String[] args) {
//         int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;
        
//         try (ServerSocket serverSocket = new ServerSocket(port)) {
//             System.out.println("Aggregation Server started on port " + port);
//             while (true) {
//                 Socket clientSocket = serverSocket.accept();
//                 System.out.println("Connection accepted from: " + clientSocket.getInetAddress());
//                 new ClientHandler(clientSocket).start();
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     private static class ClientHandler extends Thread {
//         private Socket socket;

//         public ClientHandler(Socket socket) {
//             this.socket = socket;
//         }

//         public void run() {
//             try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                  PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

//                 String request = in.readLine();
//                 System.out.println("Received request: " + request);
                
//                 if (request != null && request.startsWith("GET")) {
//                     handleGetRequest(out);
//                 } else if (request != null && request.startsWith("PUT")) {
//                     System.out.println("Handling PUT request");
//                     handlePutRequest(in, out);
//                 } else {
//                     out.println("HTTP/1.1 400 Bad Request");
//                     System.out.println("Invalid request received");
//                 }

//             } catch (IOException e) {
//                 e.printStackTrace();
//             }
//         }

//         private void handleGetRequest(PrintWriter out) {
//             // Respond with all stored weather data
//             JSONObject jsonResponse = new JSONObject();
//             for (Map.Entry<String, JSONObject> entry : weatherDataMap.entrySet()) {
//                 jsonResponse.put(entry.getKey(), entry.getValue());
//             }
            
//             out.println("HTTP/1.1 200 OK");
//             out.println("Content-Type: application/json");
//             out.println();
//             out.println(jsonResponse.toString());
//             System.out.println("GET response sent: " + jsonResponse.toString());
//         }

//         private void handlePutRequest(BufferedReader in, PrintWriter out) {
//             try {
//                 StringBuilder body = new StringBuilder();
//                 String line;

//                 // Read request headers
//                 while ((line = in.readLine()) != null && !line.isEmpty()) {
//                     System.out.println("Header: " + line);
//                 }

//                 // Read request body
//                 while ((line = in.readLine()) != null) {
//                     body.append(line);
//                 }

//                 System.out.println("Received body: " + body.toString());

//                 // Parse and store weather data
//                 JSONObject jsonData = new JSONObject(body.toString());
//                 String id = jsonData.getString("id");  // Assumes that "id" is always present in the JSON
//                 weatherDataMap.put(id, jsonData);

//                 System.out.println("Weather data stored: " + jsonData.toString());

//                 out.println("HTTP/1.1 200 OK");
//                 out.println();

//             } catch (Exception e) {
//                 e.printStackTrace();
//                 out.println("HTTP/1.1 500 Internal Server Error");
//                 out.println();
//             }
//         }
//     }
// }


// ///////////////


// // import java.io.*;
// // import java.net.*;

// // public class AggregationServer {
// //     private static final int DEFAULT_PORT = 4567;

// //     public static void main(String[] args) {
// //         int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

// //         try (ServerSocket serverSocket = new ServerSocket(port)) {
// //             System.out.println("Aggregation Server started on port " + port);
// //             while (true) {
// //                 Socket clientSocket = serverSocket.accept();
// //                 System.out.println("Connection accepted from: " + clientSocket.getInetAddress());

// //                 // Test to ensure connection works
// //                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
// //                 out.println("Connection successful!");
// //                 clientSocket.close();
// //             }
// //         } catch (IOException e) {
// //             e.printStackTrace();
// //         }
// //     }
// // }



/////////////////////////////////

///last code


import java.io.*;
import java.net.*;
import java.util.*;
import org.json.JSONObject;

public class AggregationServer {
    private static final int DEFAULT_PORT = 4567;
    private static Map<String, JSONObject> weatherDataMap = new HashMap<>();

    public static void main(String[] args) {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : DEFAULT_PORT;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Aggregation Server started on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Connection accepted from: " + clientSocket.getInetAddress());
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

                String request = in.readLine();
                System.out.println("Received request: " + request);

                if (request != null && request.startsWith("GET")) {
                    handleGetRequest(out);
                } else if (request != null && request.startsWith("PUT")) {
                    System.out.println("Handling PUT request");
                    handlePutRequest(in, out);
                } else {
                    out.println("HTTP/1.1 400 Bad Request");
                    System.out.println("Invalid request received");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void handleGetRequest(PrintWriter out) {
            // Ensure the server returns the stored data
            if (!weatherDataMap.isEmpty()) {
                JSONObject jsonResponse = new JSONObject();
                for (Map.Entry<String, JSONObject> entry : weatherDataMap.entrySet()) {
                    jsonResponse.put(entry.getKey(), entry.getValue());
                }
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println();
                out.println(jsonResponse.toString());
                System.out.println("GET response sent: " + jsonResponse.toString());
            } else {
                System.out.println("No data available in weatherDataMap");
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: application/json");
                out.println();
                out.println("{}");
            }
        }

        private void handlePutRequest(BufferedReader in, PrintWriter out) {
            try {
                StringBuilder body = new StringBuilder();
                String line;

                // Read request headers
                while ((line = in.readLine()) != null && !line.isEmpty()) {
                    System.out.println("Header: " + line);
                }

                // Read the content body
                while ((line = in.readLine()) != null) {
                    body.append(line);
                }

                System.out.println("Received body: " + body.toString());

                // Parse and store weather data
                JSONObject jsonData = new JSONObject(body.toString());
                String id = jsonData.getString("id");
                weatherDataMap.put(id, jsonData);

                System.out.println("Weather data stored: " + jsonData.toString());

                out.println("HTTP/1.1 200 OK");
                out.println();

            } catch (Exception e) {
                e.printStackTrace();
                out.println("HTTP/1.1 500 Internal Server Error");
                out.println();
            }
        }
    }
}


