import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < storage.length; i++) {
            storage[i] = null;
        }
    }

    void save(Resume r) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = r;
                break;
            }
        }
    }

    Resume get(String uuid) {
        Resume resume = null;
        for (int i = 0; i < storage.length; i++) {
            resume = storage[i];
            if (resume.getUuid().equals(uuid)) break;
        }
        return resume;
    }

    void delete(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                storage[i] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int i = 0;
        while (storage[i] != null) i++;
        Resume[] allResumes = new Resume[i];
        for (int j = 0; j < allResumes.length; j++) {
            allResumes[j] = storage[j];
        }
        return allResumes;
    }

    int size() {
        return storage.length;
    }
}
