package server;

import java.io.Serializable;


public class Message implements Serializable {
    private MessageType type;
    private String parameter;
    private Object content;


    public Message(MessageType type, String parameter, Object content) {
        this.type = type;
        this.parameter = parameter;
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
