package fr.amcf.contactview;

import java.util.Collections;
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
    private final List<Message> messages;

    private Contact(Builder builder) {
        name = builder.name;
        email = builder.email;
        primaryPhoneNumber = builder.primaryPhoneNumber;
        messages = Collections.unmodifiableList(builder.messages);
    }

    public List<Message> getMessages() {
        return messages;
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
        private List<Message> messages;

        public Builder() {
            //Default
        }

        public Builder(Contact contact) {
            name = contact.name;
            email = contact.email;
            primaryPhoneNumber = contact.primaryPhoneNumber;
            messages = contact.messages;
        }

        public Builder setEmail(String email) {
            this.email = requireNonNull(email);
            return this;
        }

        public Builder setName(String name) {
            this.name = requireNonNull(name);
            return this;
        }

        public void setMessages(List<Message> messages) {
            this.messages = requireNonNull(messages);
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
