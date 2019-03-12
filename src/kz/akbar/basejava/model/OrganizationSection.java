package kz.akbar.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends Section {

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

    public List<Organization> getOrganizations() {
        return organizations;
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
