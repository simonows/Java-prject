package server;

import java.io.ObjectOutputStream;
import java.net.Socket;


public class User {
    private String name = "";
    private Socket socket;
    private ObjectOutputStream stream;
    private UserThread thread;


    public User(Socket socket) {
        this.socket = socket;
        try {
            stream = new ObjectOutputStream(socket.getOutputStream());
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.thread = new UserThread(this);
    }

    public void sendMessage(Message message) {
        try {
            stream.writeObject(message);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Socket getSocket() {
        return socket;
    }
}
