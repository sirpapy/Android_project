package fr.amcf.contactview;

import java.util.ArrayList;
import java.util.List;

import fr.amcf.message.Message;

import static java.util.Objects.requireNonNull;

/**
 * Created by dchesnea on 09/02/2017.
 */
public class Contact {
    private final String name;
    private final String email;
    private final String primaryPhoneNumber;
    private List<Message> messages = new ArrayList<>();

    private Contact(Builder builder) {
        name = builder.name;
        email = builder.email;
        primaryPhoneNumber = builder.primaryPhoneNumber;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    @Override
    public String toString() {
        return "Name:" + name + " email:" + email;
    }

    public static class Builder {
        private String name;
        private String email;
        private String primaryPhoneNumber;

        public Builder() {
            //Default
        }

        public Builder(Contact contact) {
            name = contact.name;
            email = contact.email;
        }

        public Builder setEmail(String email) {
            this.email = requireNonNull(email);
            return this;
        }

        public Builder setName(String name) {
            this.name = requireNonNull(name);
            return this;
        }

        public Builder setPrimaryPhoneNumber(String phoneNumber) {
            this.primaryPhoneNumber = requireNonNull(phoneNumber);
            return this;
        }

        public Contact build() {
            return new Contact(this);
        }
    }
}
