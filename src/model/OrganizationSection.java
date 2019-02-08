package model;

import java.util.*;

public class OrganizationSection extends AbstractSection {

    private List<Organization> organizations;

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations list must not be null!");
        this.organizations = organizations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    public Organization getOrganization(String name) {
        for (Organization org : organizations) {
            if (name.equals(org.getName())) {
                return org;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder orgInfoBuilder = new StringBuilder();
        for (Organization org : organizations) {
            orgInfoBuilder.append(org.toString() + '\n');
        }
        return orgInfoBuilder.toString();
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
}
