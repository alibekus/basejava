package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private List<String> items = new ArrayList<>();

    public ListSection() {
        super();
    }

    public ListSection(SectionType type, String item) {
        super(type, item);
    }

    public ListSection(SectionType type, List<String> items) {
        Objects.requireNonNull(type, "type must not be null!");
        Objects.requireNonNull(items, "items must not be null!");
        this.items = items;
        this.type = type;
    }

    public List<String> getItems() {
        return items;
    }


    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(int index) {
        items.remove(index);
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "items=" + items +
                '}';
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

    @Override
    protected void printChildSection() {
        for (String item : items) {
            System.out.println("- " + item.trim());
        }
    }
}

