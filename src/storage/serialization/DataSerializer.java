package storage.serialization;

import model.*;
import storage.DataSerialization;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static model.Organization.Position;

public class DataSerializer implements Serialization {

    private <T> void writeData(Collection<T> collection, DataOutputStream dos, DataSerialization<T> dataSerializer)
            throws IOException {
        dos.writeInt(collection.size());
        for (T s : collection) {
            dataSerializer.dataSerialize(s);
        }
    }

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, Contact> contacts = resume.getContacts();
            writeData(contacts.entrySet(), dos, contactEntry -> {
                dos.writeUTF(contactEntry.getKey().name());
                dos.writeUTF(contactEntry.getValue().getValue());
            });
            Map<SectionType, Section> sections = resume.getSections();
            writeData(sections.entrySet(), dos, sectionEntry -> {
                SectionType sectionType = sectionEntry.getKey();
                dos.writeUTF(sectionType.name());
                Section section = resume.getSections().get(sectionType);
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(section.toString());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATION:
                        writeData(((ListSection) section).getItems(), dos, s -> {
                            dos.writeUTF(s);
                        });
                        break;
                    case EDUCATION:
                    case EXPERIENCE:
                        writeData(((OrganizationSection) section).getOrganizations(), dos, org -> {
                            dos.writeUTF(org.getNameLink().getTitle());
                            dos.writeUTF(org.getNameLink().getValue());
                            writeData(org.getPositions(), dos, pos -> {
                                dos.writeUTF(pos.getStartDate().toString());
                                dos.writeUTF(pos.getEndDate().toString());
                                dos.writeUTF(pos.getTitle());
                                dos.writeUTF(pos.getDescription());
                            });
                        });
                }
            });
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
                            List<Position> positions = new ArrayList<>();
                            for (int k = 0; k < positionCounts; k++) {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String desc = dis.readUTF();
                                positions.add(new Position(startDate, endDate, title, desc));
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
