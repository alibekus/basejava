package model;

public class Contact {

    private String value;
    private ContactType type;

    public Contact(ContactType type, String value) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ContactType getType() {
        return type;
    }

    public void setType(ContactType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type + type.getTitle() + ": " + value;
    }
}
