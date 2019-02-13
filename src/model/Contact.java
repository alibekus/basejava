package model;

import java.util.Objects;

public class Contact {

    private final String title;
    private final String value;

    public Contact(String title, String value) {
        Objects.requireNonNull(title, "title must not be null!");
        Objects.requireNonNull(value, "value must not be null!");
        this.title = title;
        this.value = value;
    }

    public String getTitle() {return title;}

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

    @Override
    public String toString() {
        return title + ": " + value;
    }
}
