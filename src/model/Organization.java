package model;

import java.util.*;

public class Organization {

    private String name;
    private final Map<Calendar, Calendar> dates = new LinkedHashMap<>();
    private List<Calendar> startDates = new ArrayList<>();
    private List<Calendar> endDates = new ArrayList<>();
    private final Map<String, String> positionDuties = new LinkedHashMap<>();
    private List<String> positions = new ArrayList<>();
    private List<String> duties = new ArrayList<>();

    public Organization(String name) {
        this.name = name;
    }

    public Organization(String name, List<Calendar> startDates, List<Calendar> endDates, List<String> positions,
                        List<String> duties) {
        this.name = name;
        this.startDates = startDates;
        this.endDates = endDates;
        this.positions = positions;
        this.duties = duties;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Calendar> getStartDates() {
        return startDates;
    }

    public void setStartDates(List<Calendar> startDates) {
        this.startDates = startDates;
    }

    public List<Calendar> getEndDates() {
        return endDates;
    }

    public void setEndDates(List<Calendar> endDates) {
        this.endDates = endDates;
    }

    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    public List<String> getDuties() {
        return duties;
    }

    public void setDuties(List<String> duties) {
        this.duties = duties;
    }

    public void addStartDate(Calendar date) {
        startDates.add(date);
    }

    public void addEndDates(Calendar date) {
        endDates.add(date);
    }

    public void addPosition(String position) {
        positions.add(position);
    }

    public void addDuty(String duty) {
        duties.add(duty);
    }

    public void setDatesPositionsDuties(Calendar startDate, Calendar endDate, String position, String duty) {
        startDates.add(startDate);
        endDates.add(endDate);
        positions.add(position);
        duties.add(duty);
        dates.put(startDate, endDate);
        positionDuties.put(position, duty);
    }

    @Override
    public String toString() {
        return "Organization '" + name + "\'";
    }
}
