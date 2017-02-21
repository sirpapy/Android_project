package fr.amcf.message;

/**
 * Created by Sirpapy on 19/02/2017.
 */

public class Message implements Comparable<Message> {

    private String body;
    private String type;
    private long date;

    public Message(String body, String type, long date) {
        this.body = body;
        this.type = type;
        this.date = date;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int compareTo(Message other) {
        return (date < other.date) ? -1 : ((date == other.date) ? 0 : 1);
    }
}
