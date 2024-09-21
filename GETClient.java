

// import java.io.*;
// import java.net.*;

// public class GETClient {
//     private static LamportClock clock = new LamportClock();

//     public static void main(String[] args) {
//         if (args.length < 1) {
//             System.out.println("Usage: java GETClient <server:port>");
//             return;
//         }

//         String serverAddress = args[0];

//         try (Socket socket = new Socket(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1]));
//              BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//              PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

//             out.println("GET /weather.json HTTP/1.1");
//             clock.increment();

//             String response;
//             while ((response = in.readLine()) != null) {
//                 System.out.println(response);
//             }

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }


////////////////////////////////


import java.io.*;
import java.net.*;

public class GETClient {

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java GETClient <server:port>");
            return;
        }

        String serverAddress = args[0];

        try (Socket socket = new Socket(serverAddress.split(":")[0], Integer.parseInt(serverAddress.split(":")[1]));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            // Send GET request
            out.println("GET /weather.json HTTP/1.1");
            out.println("Host: " + serverAddress);
            out.println();  // Blank line to end headers

            // Read and print the server response
            String response;
            while ((response = in.readLine()) != null) {
                System.out.println("Response from server: " + response);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
