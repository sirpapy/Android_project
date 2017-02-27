package fr.amcf.message;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sirpapy on 19/02/2017.
 */

public class Message implements Comparable<Message>,Parcelable{

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

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(body);
        out.writeValue(type);
        out.writeLong(date);
        boolean[] arraybool = {isMine};
        out.writeBooleanArray(arraybool);
    }

    public static final Parcelable.Creator<Message> CREATOR
            = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    private Message(Parcel in) {
        body = in.readString();
        type = (MessageType) in.readValue(null);
        date = in.readLong();
        boolean[] arraybool = new boolean[1];
        in.readBooleanArray(arraybool);
        isMine = arraybool[0];
    }


}
