/**
 * ChatClientExec handles launching a chat client in a thread.
 * The client GUI becomes operational once connected to the server.
 */
public class ChatClientExec implements ChatClientExecInterface {

    private int CHAT_ROOM_PORT = 0;

    private static double clientX = 30.0;
    private static double clientY = 10.0;

    /**
     * Constructs the client and sets the port number.
     * @param port the port number for the chat server
     */
    public ChatClientExec(int port) {
        CHAT_ROOM_PORT = port;
    }

    /**
     * Starts the chat client in a separate thread.
     */
    public void startClient() throws Exception {
        // Adjust client window position for each new client instance
        setClientY(getClientY() + 50.0);
        setClientX(getClientX() + 50.0);

        // Create the client instance
        ChatClient client = new ChatClient(CHAT_ROOM_PORT);

        // Start the client in a new thread
        Thread clientThread = new Thread(client);
        clientThread.start();
    }

    public static double getClientX() {
        return clientX;
    }

    public static void setClientX(double clientX) {
        ChatClientExec.clientX = clientX;
    }

    public static double getClientY() {
        return clientY;
    }

    public static void setClientY(double clientY) {
        ChatClientExec.clientY = clientY;
    }
}
