package Labs;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.DefaultListModel;
import server.Message;


public class Client {
    private Socket socket;
    private boolean stopThread;
    private ObjectOutputStream stream;
    
    public Client (final DefaultListModel<String> userListModel) {
            try{
            socket = new Socket("localhost", 10069);
            stream = new ObjectOutputStream(socket.getOutputStream());
            }catch(Exception e){e.printStackTrace();}
            stopThread = false;
            
            new Thread(new Runnable() {
            @Override
            public void run(){
                try {
                    ObjectInputStream stream = new ObjectInputStream(socket.getInputStream());
                    while (!stopThread) {
                        Message message = (Message)stream.readObject();
                        switch (message.getType()) {
                            case USER_CONNECTED:
                                userListModel.addElement(message.getParameter());
                                break;
                            case USER_DISCONNECTED:
                                userListModel.removeElement(message.getParameter());
                                stream.close();
                                socket.close();
                                break;
                            case OBJECTS_SWAP:
                               if (message.getContent() == null) {
                                    //message.setContent(Habitat.requestTransport());
                                    sendMessage(message);
                                }
                                //else Habitat.acquiredTransport(message.getContent());
                                break;
                        }
                    }
                }
                catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
                
            }
        }).start();
            
        
    }
    
    public void sendMessage(Message message) {
        try {
            stream.writeObject(message);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }
    
}
