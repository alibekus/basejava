package kz.akbar.basejava.storage.serialization;

import kz.akbar.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static kz.akbar.basejava.model.Organization.Position;

public class DataSerializer implements Serialization {

    @FunctionalInterface
    private interface DataWriter<T> {
        void write(T data) throws IOException;
    }

    @FunctionalInterface
    private interface DataReader {
        void read() throws IOException;
    }

    private <T> void writeData(Collection<T> collection, DataOutputStream dos, DataWriter<T> dataWriter)
            throws IOException {
        dos.writeInt(collection.size());
        for (T s : collection) {
            dataWriter.write(s);
        }
    }

    private void readData(DataInputStream dis, DataReader dataReader)
            throws IOException {
        int count = dis.readInt();
        for (int i = 0; i < count; i++) {
            dataReader.read();
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
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        writeData(((ListSection) section).getItems(), dos, dos::writeUTF);
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
            readData(dis, () -> {
                ContactType type = ContactType.valueOf(dis.readUTF());
                String contactValue = dis.readUTF();
                Contact contact = new Contact(type.getTitle(), contactValue);
                resume.addContact(type, contact);
            });
            readData(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENTS:
                    case QUALIFICATIONS:
                        List<String> items = new ArrayList<>();
                        readData(dis, () -> items.add(dis.readUTF()));
                        resume.addSection(sectionType, new ListSection(items));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        OrganizationSection orgSection = new OrganizationSection();
                        readData(dis, () -> {
                            String name = dis.readUTF();
                            String url = dis.readUTF();
                            List<Position> positions = new ArrayList<>();
                            readData(dis, () -> {
                                LocalDate startDate = LocalDate.parse(dis.readUTF());
                                LocalDate endDate = LocalDate.parse(dis.readUTF());
                                String title = dis.readUTF();
                                String desc = dis.readUTF();
                                positions.add(new Position(startDate, endDate, title, desc));
                            });
                            orgSection.addOrganization(new Organization(name, url, positions));
                        });
                        resume.addSection(sectionType, orgSection);
                }
            });
            return resume;
        }
    }
}
