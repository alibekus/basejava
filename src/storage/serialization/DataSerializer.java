package storage.serialization;

import model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSerializer implements Serialization {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, Contact> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, Contact> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue().getValue());
            }
            // TO DO implements sections
            Map<SectionType, Section> sections = resume.getSections();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                dos.writeUTF(sectionType.name());
                Section section = resume.getSections().get(sectionType);
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        List<String> items = ((ListSection) section).getItems();
                        dos.writeInt(items.size());
                        for (String item : items) {
                            dos.writeUTF(item);
                        }
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        List<Organization> orgs = ((OrganizationSection) section).getOrganizations();
                        dos.writeInt(orgs.size());
                        for (Organization org : orgs) {
                            dos.writeUTF(org.getNameLink().getTitle());
                            dos.writeUTF(org.getNameLink().getValue());
                            List<Organization.Position> positions = org.getPositions();
                            dos.writeInt(positions.size());
                            for (Organization.Position pos : org.getPositions()) {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            }
                        }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactsCount = dis.readInt();
            for (int i = 0; i < contactsCount; i++) {
                ContactType type = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                Contact contact = new Contact(type.getTitle(), contactValue);
                resume.addContact(type, contact);
            }
            // TO DO implements sections
            int sectionsCount = dis.readInt();
            for (int i = 0; i < sectionsCount; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new SimpleSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        int itemCounts = dis.readInt();
                        List<String> items = new ArrayList<>();
                        for (int j = 0; j < itemCounts; j++) {
                            items.add(dis.readUTF());
                        }
                        resume.addSection(sectionType, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        int orgCounts = dis.readInt();
                        OrganizationSection orgSection = new OrganizationSection();
                        for (int j = 0; j < orgCounts; j++) {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            int positionCounts = dis.readInt();
                            List<Organization.Position> positions = new ArrayList<>();
                            for (int k = 0; k < positionCounts; k++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String desc = dis.readUTF();
                                positions.add(new Organization.Position(startDate, endDate, title, desc));
                            }
                            orgSection.addOrganization(new Organization(name, url, positions));
                        }
                        resume.addSection(sectionType, orgSection);
                }
            }
            return resume;
        }
    }
}
