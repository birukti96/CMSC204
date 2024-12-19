import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * ChatServer handles incoming client connections and facilitates message broadcasting.
 * It runs in a separate thread.
 */
public class ChatServer implements Runnable {
    private String name;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ChatServer(int port) {
        CHAT_ROOM_PORT = port;
    }

    /**
     * The port that the server listens on.
     */
    private static int CHAT_ROOM_PORT;

    /**
     * The set of all names of clients in the chat room. Maintained
     * to ensure new clients do not use duplicate names.
     */
    private static HashSet<String> names = new HashSet<>();

    /**
     * The set of all print writers for all clients. Used for broadcasting messages.
     */
    private static HashSet<PrintWriter> writers = new HashSet<>();

    @Override
    public void run() {
        ServerSocket listener = null;
        try {
            // Create a server socket
            listener = new ServerSocket(CHAT_ROOM_PORT);
            System.out.println("The chat server is running on port " + CHAT_ROOM_PORT);

            while (true) {
                // Listen for a client to join
                Socket socket = listener.accept();
                System.out.println("A new client has connected.");

                // Set up input and output channels for the client
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

                // Request a name from the client and ensure it is unique
                name = null;
                while (name == null) {
                    out.println("SUBMITNAME");
                    name = in.readLine();
                    if (name == null || name.isEmpty() || names.contains(name)) {
                        out.println("WRONGNAME");
                        name = null;
                    } else {
                        synchronized (names) {
                            names.add(name);
                        }
                    }
                }

                // Notify the client that the name is accepted
                out.println("NAMEACCEPTED " + name);
                System.out.println("Client name accepted: " + name);

                // Add the client's writer to the set of writers
                synchronized (writers) {
                    writers.add(out);
                }

                // Start a new thread to handle the client
                ServerThreadForClient svrForClient = new ServerThreadForClient(in, out, name, socket);
                Thread t = new Thread(svrForClient);
                t.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (listener != null) {
                try {
                    listener.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ServerThreadForClient implements Runnable {
        private BufferedReader in;
        private PrintWriter out;
        private String name;
        private Socket clientSocket;

        ServerThreadForClient(BufferedReader in, PrintWriter out, String name, Socket clientSocket) {
            this.in = in;
            this.out = out;
            this.name = name;
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    String input = in.readLine();
                    if (input == null) {
                        return;
                    }
                    System.out.println("Broadcasting message: " + name + ": " + input);

                    // Broadcast the message to all clients
                    synchronized (writers) {
                        for (PrintWriter writer : writers) {
                            writer.println("MESSAGE " + name + ": " + input);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Remove the client's name and writer from the sets and close the socket
                if (name != null) {
                    synchronized (names) {
                        names.remove(name);
                    }
                }
                if (out != null) {
                    synchronized (writers) {
                        writers.remove(out);
                    }
                }
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(name + " has disconnected.");
            }
        }
    }
}
