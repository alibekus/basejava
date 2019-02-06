package model;

import java.util.*;

public class OrganizationSection extends Section {

    private final Map<String, Organization> organizations;

    public OrganizationSection() {
        organizations = new LinkedHashMap<>();
    }

    public OrganizationSection(SectionType type, Organization organization) {
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(organization, "organization must not be null");
        organizations = new LinkedHashMap<>();
        organizations.put(organization.getName(), organization);
        this.type = type;
    }

    public OrganizationSection(Map<String, Organization> organizations, SectionType type) {
        this.organizations = organizations;
        this.type = type;
    }

    public Map<String, Organization> getOrganizations() {
        return organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.put(organization.getName(), organization);
    }

    public Organization getOrganization(String name) {
        return organizations.get(name);
    }

    private void printDatesPositionsDuties(Organization organization) {
        for (int i = 0; i < organization.getDuties().size(); i++) {
            System.out.println("-----------------------Period---------------------------------");
            System.out.println("Start: " + organization.getStartDates().get(i).get(Calendar.YEAR)
                    + ", " + organization.getStartDates().get(i).getDisplayName(Calendar.MONTH,
                    Calendar.LONG_FORMAT,Locale.US));
            System.out.println("End: " + organization.getEndDates().get(i).get(Calendar.YEAR) + ", "
                    + organization.getEndDates().get(i).getDisplayName(Calendar.MONTH,
                    Calendar.LONG_FORMAT,Locale.US));
            System.out.println("--------------------------------------------------------------");
            if (!type.equals(SectionType.EDUCATION)) {
                System.out.println("Position: " + organization.getPositions().get(i));
            }
            System.out.println("Duty: " + organization.getDuties().get(i));
        }
    }

    @Override
    protected void printChildSection() {
        for (Map.Entry<String, Organization> stringOrganizationEntry : organizations.entrySet()) {
            Map.Entry orgEntry = (Map.Entry) stringOrganizationEntry;
            System.out.println("**************************************************************");
            Organization organization = (Organization) orgEntry.getValue();
            System.out.println("Organization: " + organization.getName());
            printDatesPositionsDuties(organization);
        }
    }
}
