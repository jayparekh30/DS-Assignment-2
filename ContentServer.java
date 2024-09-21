// import java.io.*;
// import java.net.*;
// import org.json.JSONObject;

// public class ContentServer {

//     public static void main(String[] args) {
//         if (args.length < 2) {
//             System.out.println("Usage: java ContentServer <server:port> <file_path>");
//             return;
//         }

//         String serverAddress = args[0];
//         String filePath = args[1];

//         try {
//             // Convert key-value pairs from the file to a JSON object
//             JSONObject jsonData = convertFileToJson(filePath);
//             System.out.println("Converted weather_data.txt to JSON: " + jsonData.toString());
//             sendPutRequest(serverAddress, jsonData);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     // Function to convert file content to JSON
//     private static JSONObject convertFileToJson(String filePath) throws IOException {
//         JSONObject json = new JSONObject();
//         try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
//             String line;
//             while ((line = br.readLine()) != null) {
//                 String[] parts = line.split(":", 2);
//                 if (parts.length == 2) {
//                     String key = parts[0].trim();
//                     String value = parts[1].trim();
//                     json.put(key, value);  // Add key-value pairs to JSON object
//                 }
//             }
//         }
//         return json;
//     }

//     private static void sendPutRequest(String serverAddress, JSONObject jsonData) {
//         try (Socket socket = new Socket(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1]));
//              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

//             // Send PUT request headers
//             out.println("PUT /weather.json HTTP/1.1");
//             out.println("Host: " + serverAddress);
//             out.println("User-Agent: ContentServer/1.0");
//             out.println("Content-Type: application/json");
//             out.println("Content-Length: " + jsonData.toString().length());
//             out.println();  // Blank line to end headers

//             // Send the JSON data
//             out.println(jsonData.toString());
//             System.out.println("PUT request sent with data: " + jsonData.toString());

//             // Read and print the server response
//             String response;
//             while ((response = in.readLine()) != null) {
//                 System.out.println("Response from server: " + response);
//             }

//         } catch (IOException e) {
//             e.printStackTrace();
//             System.out.println("Failed to connect to AggregationServer at " + serverAddress);
//         }
//     }
// }


/////////////////


// import java.io.*;
// import java.net.*;

// public class ContentServer {

//     public static void main(String[] args) {
//         if (args.length < 2) {
//             System.out.println("Usage: java ContentServer <server:port> <message>");
//             return;
//         }

//         String serverAddress = args[0];
//         String message = args[1];

//         try {
//             Socket socket = new Socket(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1]));
//             System.out.println("Connected to AggregationServer");

//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
//             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

//             // Send a basic message
//             out.println(message);
//             System.out.println("Message sent: " + message);

//             // Read the server's response
//             String response = in.readLine();
//             System.out.println("Response from server: " + response);

//             socket.close();
//         } catch (IOException e) {
//             e.printStackTrace();
//             System.out.println("Failed to connect to AggregationServer at " + serverAddress);
//         }
//     }
// }



///////////




import java.io.*;
import java.net.*;
import org.json.JSONObject;

public class ContentServer {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java ContentServer <server:port> <file_path>");
            return;
        }

        String serverAddress = args[0];
        String filePath = args[1];

        try {
            // Convert the weather_data.txt file content into JSON
            JSONObject jsonData = convertFileToJson(filePath);
            System.out.println("Converted weather_data.txt to JSON: " + jsonData.toString());
            sendPutRequest(serverAddress, jsonData);  // Send the JSON data
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject convertFileToJson(String filePath) throws IOException {
        JSONObject json = new JSONObject();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2);  // Split each line by ':'
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    json.put(key, value);  // Add key-value pair to JSON object
                }
            }
        }
        return json;
    }

    private static void sendPutRequest(String serverAddress, JSONObject jsonData) {
        try (Socket socket = new Socket(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1]));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Log connection success
            System.out.println("Connected to AggregationServer");

            // Send PUT request headers
            out.println("PUT /weather.json HTTP/1.1");
            out.println("Host: " + serverAddress);
            out.println("User-Agent: ContentServer/1.0");
            out.println("Content-Type: application/json");
            out.println("Content-Length: " + jsonData.toString().length());
            out.println();  // Blank line to separate headers from the body

            // Send the actual JSON data in the body
            out.println(jsonData.toString());
            System.out.println("PUT request sent with data: " + jsonData.toString());

            // Read and print the server response
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Response from server: " + response);
            }

            // Ensure response is logged
            System.out.println("Response reading complete.");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to connect to AggregationServer at " + serverAddress);
        }
    }
}
