package model;

import java.util.*;

public class Resume implements Comparable<Resume> {

    private String uuid;
    private String fullName;

    private final Map<ContactType, Contact> contacts = new EnumMap<>(ContactType.class);

    private final Map<SectionType, Section> sections = new EnumMap<>(SectionType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid,"uuid must not be null!");
        Objects.requireNonNull(fullName,"full name must not be null!");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void addContact(Contact contact) {
        contacts.put(contact.getType(),contact);
    }

    public void addSection(Section section) {
        sections.put(section.getType(),section);
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    private String getFullName() {
        return fullName;
    }

    public Contact getContacts(ContactType type) {
        return contacts.get(type);
    }

    public Section getSections(SectionType type) {
        return sections.get(type);
    }

    @Override
    public String toString() {
        return "UUID: " + uuid + ", Name: " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) & Objects.equals(fullName, resume.getFullName());
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(uuid) + Objects.hash(fullName);
    }

    @Override
    public int compareTo(Resume resume) {
        return uuid.compareTo(resume.getUuid());
    }

    public void printContacts() {
        System.out.println("-----------------------Contacts-----------------------");
        for (ContactType type : ContactType.values()) {
            System.out.println(type.getTitle() + ": " + contacts.get(type).getValue());
        }
    }

    public void printSections() {
        System.out.println("-----------------------Sections-----------------------");
        for (SectionType type: SectionType.values()) {
            sections.get(type).printSection();
            System.out.println("==============================================================");
        }
    }
}
