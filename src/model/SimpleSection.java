package model;

import java.util.Objects;

public class SimpleSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private final String description;

    public SimpleSection(String description) {
        Objects.requireNonNull(description, "description must not be null!");
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleSection that = (SimpleSection) o;
        return description.equals(that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }
}
