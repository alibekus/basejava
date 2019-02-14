package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Organization {

    private final Contact nameLink;
    private final String position;
    private final String duty;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    public Organization(String name, String url, String position, String duty, LocalDate startDate,
                        LocalDate endDate) {
        Objects.requireNonNull(startDate, "startDate must not be null!");
        Objects.requireNonNull(endDate, "endDate must not be null!");
        Objects.requireNonNull(position, "positions must not be null!");
        this.nameLink = new Contact(name, url);
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.duty = duty;
    }

    String getName() {
        return nameLink.getTitle();
    }

    String getPosition() {
        return position;
    }

    String getDuty() {
        return duty;
    }

    String getStartDate() {
        return startDate.format(formatter);
    }

    String getEndDate() {
        return endDate.format(formatter);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return nameLink.equals(that.nameLink) &&
                position.equals(that.position) &&
                Objects.equals(duty, that.duty) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameLink, position, duty, startDate, endDate);
    }

    @Override
    public String toString() {
        return "=================================================" + "\n" +
                "организация: " + nameLink.getTitle() + "\n" +
                "url: " + nameLink.getValue() + "\n" +
                "должность: " + position + "\n" +
                "обязанности: " + duty + "\n" +
                "начало работы: " + startDate.format(formatter) + "\n" +
                "завершение: " + endDate.format(formatter) + "\n" +
                "-------------------------------------------------";
    }
}
