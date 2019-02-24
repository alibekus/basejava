package kz.akbar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    private String title;
    private String value;

    public Contact() {
    }

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

    public void setValue(String value) {
        this.value = value;
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
