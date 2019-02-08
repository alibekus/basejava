package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ListSection extends Section {

    private List<String> items;

    public ListSection() {
        items = new ArrayList<>();
    }

    public ListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null!");
        this.items = items;
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
    public void printSection() {
        for (String item : items) {
            System.out.println("- " + item.trim());
        }
    }

    @Override
    public String toString() {
        return items.toString();
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

