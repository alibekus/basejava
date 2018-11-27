/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private int resumeCount = 0;
    private final int STORAGE_SIZE = 10_000;
    private final Resume[] storage = new Resume[STORAGE_SIZE];

    void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    void save(Resume r) {
        int rIndex = getIndex(r.getUuid());
        if (rIndex != -1) {
            System.out.println("The resume with " + r.getUuid() + "already exist");
        } else {
            if (resumeCount < storage.length) {
                storage[resumeCount++] = r;
            } else {
                System.out.println("The storage is full!");
            }
        }
    }

    void update(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex == -1) {
            System.out.println("There is no to update: no such resume");
        } else {
            storage[rIndex] = resume;
        }
    }

    Resume get(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex == -1) {
            System.out.println("There is no resume with uuid: " + uuid);
            return null;
        } else {
            return storage[rIndex];
        }
    }

    void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex != -1) {
            storage[rIndex] = storage[--resumeCount];
            storage[resumeCount] = null;
        } else {
            System.out.println("There is no resume with uuid: " + uuid);
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[resumeCount];
        System.arraycopy(storage, 0, allResumes, 0, resumeCount);
        return allResumes;
    }

    int size() {
        return resumeCount;
    }


    private int getIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
