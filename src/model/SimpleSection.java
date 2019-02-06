package model;

import java.util.Objects;

public class SimpleSection extends Section {

    public SimpleSection(SectionType type, String description) {
        super(type, description);
    }

    @Override
    public String toString() {
        return type.getTitle() + ":\n " + description + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleSection that = (SimpleSection) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

    @Override
    protected void printChildSection() {
        System.out.println(description);
    }
}
