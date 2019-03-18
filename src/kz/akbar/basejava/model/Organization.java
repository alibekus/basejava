package kz.akbar.basejava.model;

import kz.akbar.basejava.util.DateUtil;
import kz.akbar.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;
    private Contact nameLink;
    private List<Position> positions;

    public Organization(String name) {
        nameLink = new Contact();
        nameLink.setTitle(name);
        positions = new ArrayList<>();
    }

    public Organization(String name, String url, Position... positions) {
        this(name, url, Arrays.asList(positions));
    }

    public Organization(String name, String url, List<Position> positions) {
        this.nameLink = new Contact(name, url);
        this.positions = new ArrayList<>(positions);
    }

    public Organization() {

    }

    public void addUrl(String url) {
        this.nameLink.setValue(url);
    }

    public Contact getNameLink() {
        return nameLink;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void addPosition(Position position) {
        if (positions != null) {
            positions.add(position);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return nameLink.equals(that.nameLink) &&
                positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameLink, positions);
    }

    @Override
    public String toString() {
        return "Организация: " + nameLink + "\n" + printPositions();
    }

    private String printPositions() {
        StringBuilder positionsBuilder = new StringBuilder();
        for (Position position : positions) {
            positionsBuilder.append(position);
        }
        return positionsBuilder.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        private static final long serialVersionUID = 1L;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;
        private transient final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        public Position() {
        }

        public Position(LocalDate startDate, String title, String description) {
            this(startDate, DateUtil.NOW, title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            Objects.requireNonNull(startDate, "startDate must not be null");
            Objects.requireNonNull(endDate, "endDate must not be null");
            Objects.requireNonNull(title, "title must not be null");
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description == null ? "" : description;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public void setPositionTitle(String position) {
            this.title = position;
        }

        public void setDuties(String duties) {
            this.description = duties;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) &&
                    Objects.equals(endDate, position.endDate) &&
                    Objects.equals(title, position.title) &&
                    Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            return startDate.format(formatter) + " - " + endDate.format(formatter) + '\n'
                    + "Позиция: " + title + '\n'
                    + "Описание: " + description + '\n';
        }
    }
}
