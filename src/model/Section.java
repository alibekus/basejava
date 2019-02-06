package model;

import java.util.Objects;

public abstract class Section {
    String description;
    SectionType type;

    protected abstract void printChildSection();

    Section() {}

    Section(SectionType type, String description) {
        Objects.requireNonNull(type, "type must not be null!");
        Objects.requireNonNull(description, "description must not be null!");
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SectionType getType() {
        return type;
    }

    public void setType(SectionType sectionType) {
        this.type = sectionType;
    }

    @Override
    public String toString() {
        return "Section{" +
                "title='" + type.getTitle() + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                '}';
    }

    void printSection() {
        System.out.println(type);
        System.out.println(type.getTitle());
        printChildSection();
    }
}
