package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Organization {

    private String name;
    private String position;
    private String duty;
    private LocalDate startDate;
    private LocalDate endDate;

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name, String position, String duty, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.duty = duty;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return name.equals(that.name) &&
                Objects.equals(position, that.position) &&
                duty.equals(that.duty) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, position, duty, startDate, endDate);
    }

    @Override
    public String toString() {
        return "организация = '" + name + "\'\n" +
                "должность = '" + position + "\'\n" +
                "обязанности = '" + duty + "\'\n" +
                "начало работы = " + startDate.format(formatter) + "\n" +
                "завершение = " + endDate.format(formatter) + "\n" +
                "-------------------------------------------------";
    }
}
