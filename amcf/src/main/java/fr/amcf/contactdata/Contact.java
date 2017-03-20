package fr.amcf.contactdata;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.i18n.phonenumbers.PhoneNumberUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.amcf.message.Message;

import static java.util.Objects.requireNonNull;

/**
 * Created by dchesnea on 09/02/2017.
 */
public class Contact implements Serializable {
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

    public boolean isPhoneNumberEquals(String phoneNumber) {
        requireNonNull(phoneNumber);
        PhoneNumberUtil pnu = PhoneNumberUtil.getInstance();
        PhoneNumberUtil.MatchType mt = pnu.isNumberMatch(primaryPhoneNumber, phoneNumber);
        return mt == PhoneNumberUtil.MatchType.NSN_MATCH || mt == PhoneNumberUtil.MatchType.EXACT_MATCH;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<Message> getSortedMessages() {
        ArrayList<Message> toSort = new ArrayList<>();
        toSort.addAll(messages);
        Collections.sort(toSort);
        return toSort;
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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String email;
        private String primaryPhoneNumber;
        private List<Message> messages = new ArrayList<>();

        private Builder() {
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

        public Builder setMessages(List<Message> messages) {
            this.messages = requireNonNull(messages);
            return this;
        }

        public Builder addMessage(Message messages) {
            this.messages.add(requireNonNull(messages));
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
