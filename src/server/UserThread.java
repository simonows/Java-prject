package server;

import java.io.IOException;
import java.io.ObjectInputStream;


public class UserThread extends Thread {
    private User parent;


    public UserThread(User parent) {
        this.parent = parent;
        start();
    }


    @Override
    public void run() {
        ObjectInputStream stream=null;
        boolean stop = false;
        try {
            stream = new ObjectInputStream(parent.getSocket().getInputStream());
            while (!stop) {
                Message message = (Message)stream.readObject();
                switch (message.getType()) {
                    case CONNECT:
                        if (Server.isNameAvailable(message.getParameter())) {
                            parent.setName(message.getParameter());
                            Server.userConnected(parent);
                            Server.getUsers(parent);
                        }
                        break;
                    case DISCONNECT:
                        stop = true;
                        stream.close();
                        Server.userDisconnected(parent);
                        parent.getSocket().close();
                        
                        break;
                    case OBJECTS_SWAP:
                        if (parent.getName().equals("")) continue;
                        if (message.getContent() == null) {
                            String returnTo = message.getParameter();
                            message.setParameter(parent.getName());
                            Server.requestTransport(returnTo, message);
                        }
                        else Server.sendTransport(message);
                        break;
                }
            }
        }
        catch (Exception exception) {
            stop = true;
            try {
                stream.close();
                parent.getSocket().close();
            } catch (IOException ex) {ex.printStackTrace(); }
            Server.userDisconnected(parent);
         
        }
    }
}
