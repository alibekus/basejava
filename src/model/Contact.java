package model;

import java.util.Objects;

public class Contact {

    private String title;
    private String value;

    public Contact(String title, String value) {
        this.title = title;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
