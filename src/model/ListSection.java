package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private final List<String> items;

    public ListSection() {
        items = new ArrayList<>();
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null!");
        this.items = items;
    }


    @Override
    public String toString() {
        StringBuilder stringItems = new StringBuilder();
        for (String item : items) {
            stringItems.append("- ").append(item.trim()).append('\n');
        }
        return stringItems.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListSection that = (ListSection) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

}

