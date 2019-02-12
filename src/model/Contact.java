package model;

import java.util.Objects;

public class Contact {

    private final String title;
    private final String value;

    public Contact(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return title.equals(contact.title) &&
                value.equals(contact.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, value);
    }
}
