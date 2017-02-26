package fr.amcf.message;

/**
 * Created by Sirpapy on 19/02/2017.
 */

public class Message implements Comparable<Message> {

    private String body;
    private MessageType type;
    private long date;
    private boolean isMine;

    public Message(String body, MessageType type, long date,boolean isMine) {
        this.body = body;
        this.type = type;
        this.date = date;
        this.isMine = isMine;
    }

    public Message(String body, MessageType type, long date){
        this.body = body;
        this.type = type;
        this.date = date;
    }

    public Message(String body, MessageType type) {
        this.body = body;
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Message other) {
        return (date < other.date) ? -1 : ((date == other.date) ? 0 : 1);
    }

    public boolean isMine() {
        return isMine;
    }

}
