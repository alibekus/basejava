package model;

import java.util.*;

public class OrganizationSection extends AbstractSection {

    private static final long serialVersionUID = 1L;
    private final List<Organization> organizations;
    public OrganizationSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationSection(Organization... organizations) {
        this.organizations = Arrays.asList(organizations);
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
        for (Organization org : organizations) {
            orgInfoBuilder.append(org.toString() + '\n');
        }
        return orgInfoBuilder.toString();
    }
}
