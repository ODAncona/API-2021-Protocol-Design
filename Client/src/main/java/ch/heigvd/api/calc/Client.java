package ch.heigvd.api.calc;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.Socket;

/**
 * Calculator client implementation
 */


public class Client {

    static private final int LISTEN_PORT = 3101;

    private static final Logger LOG = Logger.getLogger(Client.class.getName());

    /**
     * Main function to run client
     *
     * @param args no args required
     */
    public static void main(String[] args) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");

        Socket clientSocket = null;
        BufferedReader stdin = null;
        BufferedWriter writer = null;

        /* TODO: Implement the client here, according to your specification
         *   The client has to do the following:
         *   - connect to the server
         *   - initialize the dialog with the server according to your specification
         *   - In a loop:
         *     - read the command from the user on stdin (already created)
         *     - send the command to the server
         *     - read the response line from the server (using BufferedReader.readLine)
         */

        try {
            clientSocket = new Socket("localhost", LISTEN_PORT);
            stdin = new BufferedReader(new InputStreamReader(System.in));
            writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            String line;
            while ((line = stdin.readLine()) != null) {
                System.out.println("nbolo" + line);
                writer.write(line + "\n");
                writer.flush();
            }

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, ex.toString(), ex);
        } finally {
            try {
                if (writer != null) writer.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (stdin != null) stdin.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
            try {
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, ex.toString(), ex);
            }
        }

    }
}
