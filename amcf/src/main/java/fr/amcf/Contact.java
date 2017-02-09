package fr.amcf;

import static java.util.Objects.requireNonNull;

/**
 * Created by dchesnea on 09/02/2017.
 */

public class Contact {
    private final String name;
    private final String email;

    private Contact(Builder builder) {
        name = builder.name;
        email = builder.email;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Name:" + name + " email:" + email;
    }

    public static class Builder {
        private String name;
        private String email;

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

        public Contact build() {
            return new Contact(this);
        }
    }
}
