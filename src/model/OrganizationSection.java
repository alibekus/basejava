package model;

import java.util.*;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> organizations;

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations list must not be null!");
        this.organizations = organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        StringBuilder orgInfoBuilder = new StringBuilder();
        for (int i = 0; i < organizations.size(); i++) {
            if (i > 0) {
                if (organizations.get(i).getName().equals(organizations.get(i - 1).getName())) {
                    orgInfoBuilder.append("должность: ").append(organizations.get(i).getPosition()).append("\n");
                    orgInfoBuilder.append("обязанности: ").append(organizations.get(i).getDuty()).append("\n");
                    orgInfoBuilder.append("начало работы: ").append(organizations.get(i).getStartDate()).append("\n");
                    orgInfoBuilder.append("завершение: ").append(organizations.get(i).getEndDate()).append("\n");
                    orgInfoBuilder.append("-------------------------------------------------\n");
                } else {
                    orgInfoBuilder.append(organizations.get(i).toString()).append("\n");
                }
            } else {
                orgInfoBuilder.append(organizations.get(i).toString()).append("\n");
            }
        }
//        orgInfoBuilder.append(org.toString() + '\n');
        return orgInfoBuilder.toString();
    }
}
