package model;

import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume> {

    private String uuid;
    private String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
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

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return uuid;
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
        return Objects.hash(uuid) + Objects.hash(fullName);
    }

    @Override
    public int compareTo(Resume resume) {
        return uuid.compareTo(resume.getUuid());
    }
}
