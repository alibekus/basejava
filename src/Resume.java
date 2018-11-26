/**
 * Initial resume class
 */
class Resume {

    // Unique identifier
    String uuid;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }

    public boolean equals(Resume resume) {
        return this.uuid == resume.getUuid();
    }
}
