package fr.amcf.message;

/**
 * Created by Sirpapy on 19/02/2017.
 */

public class Message {

    private String body;

    private String type;
    public Message(String body, String type) {
        this.body = body;
        this.type = type;
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

}
