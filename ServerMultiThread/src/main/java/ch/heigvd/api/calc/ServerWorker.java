package ch.heigvd.api.calc;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Calculator worker implementation
 */
public class ServerWorker implements Runnable {

    private final static Logger LOG = Logger.getLogger(ServerWorker.class.getName());
    private Socket clientSocket = null;
    private final static int SERVER_PORT = 3101;
    /**
     * Instantiation of a new worker mapped to a socket
     *
     * @param clientSocket connected to worker
     */
    public ServerWorker(Socket clientSocket) {
        // Log output on a single line
        System.setProperty("java.util.logging.SimpleFormatter.format", "%4$s: %5$s%6$s%n");
        this.clientSocket = clientSocket;
    }

    /**
     * Run method of the thread.
     */
    @Override
    public void run() {

        /* TODO: implement the handling of a client connection according to the specification.
         *   The server has to do the following:
         *   - initialize the dialog according to the specification (for example send the list
         *     of possible commands)
         *   - In a loop:
         *     - Read a message from the input stream (using BufferedReader.readLine)
         *     - Handle the message
         *     - Send to result to the client
         */
        BufferedReader in = null;
        BufferedWriter out = null;

        try {
            LOG.log(Level.INFO, "Single-threaded: Waiting for a new client on port {0}", SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8));
            String fromClient;

            // Send available options
            out.write("HEIG Calculator, available operators : { +, - , *, / } \n");
            out.flush();
            LOG.info("Reading until client sends BYE or closes the connection...");
            while ((fromClient = in.readLine()) != null) {
                if (fromClient.toLowerCase(Locale.ROOT).contains("bye")) break;
                if (fromClient.matches("(^[\\d.]+)(\\s[+*\\/-]\\s)([\\d.]+)(\\n)?$")) {
                    String[] args = fromClient.split(" ");
                    double result, op1, op2;
                    op1 = Double.parseDouble(args[0]);
                    op2 = Double.parseDouble(args[2]);

                    switch (args[1]) {
                        case "+":
                            result = op1 + op2;
                            break;
                        case "-":
                            result = op1 - op2;
                            break;
                        case "*":
                            result = op1 * op2;
                            break;
                        case "/":
                            result = op1 / op2;
                            break;
                        default:
                            result = 0;
                            break;
                    }
                    out.write("result " + result + "\n");
                    out.flush();
                } else {
                    out.write("SyntaxError ! try again \n");
                    out.flush();
                }
            }
            out.write("GoodBye ! \n");
            out.flush();
            LOG.info("Cleaning up resources...");
            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException ex) {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            if (clientSocket != null) {
                try {
                    clientSocket.close();
                } catch (IOException ex1) {
                    LOG.log(Level.SEVERE, ex1.getMessage(), ex1);
                }
            }
            LOG.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
}