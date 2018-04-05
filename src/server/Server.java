package server;

import java.awt.Dimension;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class Server {
    private static ServerSocket socket;
    private static Thread serverThread;
    private static ArrayList<User> users = new ArrayList<>();


    public static void main(String[] args) {
        JFrame NewFrame =new JFrame("Сервер");
        NewFrame.setPreferredSize(new Dimension(150,80));
        JLabel label=new JLabel();
        NewFrame.add(label);
        NewFrame.pack();
        NewFrame.setVisible(true);
        NewFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        serverThread  = new Thread(new Runnable() {
            @Override
            public void run(){
                try{
                socket = new ServerSocket(10069);
                while (true) {
                    Socket user = socket.accept();
                    users.add(new User(user));
                }
                }catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
            }
        });
        
        serverThread.start();
        System.out.println("Server started");
        label.setText("Server is running");
    }

    public static boolean isNameAvailable(String name) {
        for (User user : users)
            if (user.getName().equals(name)) return false;
        return true;
    }

    public static void getUsers(User user) {
        for (User connectedUser : users)
            if (connectedUser != user)
                user.sendMessage(new Message(MessageType.USER_CONNECTED, connectedUser.getName(), null));
    }

    public static void userConnected(User user) {
        System.out.println(user.getName() + " connected");
        for (User connectedUser : users)
            if (user != connectedUser)
                connectedUser.sendMessage(new Message(MessageType.USER_CONNECTED, user.getName(), null));
    }

    public static void userDisconnected(User user) {
        for (User connectedUser : users)
            if (!connectedUser.getName().equals(user.getName()))
                connectedUser.sendMessage(new Message(MessageType.USER_DISCONNECTED, user.getName(), null));
        users.remove(user);
    }

    public static void requestTransport(String username, Message message) {
        for (User user : users)
            if (user.getName().equals(username))
                user.sendMessage(message);
    }

    public static void sendTransport(Message message) {
        for (User user : users)
            if (user.getName().equals(message.getParameter()))
                user.sendMessage(message);
    }
}
