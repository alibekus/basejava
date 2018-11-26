import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Array based storage for Resumes
 */
class ArrayStorage {
    private static int resumeCount = 0;
    private final Resume[] storage = new Resume[10000];
    // Для проверки содержимого произвольного элемента только что созданного массива
    void printAnyElement() {
        int randomIndex = (int) (Math.random() * 10000);
        System.out.println(randomIndex + " element in ArrayStorage: " + storage[randomIndex]);
    }

    void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    private int resumeIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid))
                return i;
        }
        return -1;
    }

    void save(Resume r) {
        int rIndex = resumeIndex(r.getUuid());
        if (rIndex != -1)
            System.out.println("The resume with " + r.getUuid() + "already exist");
        else if (resumeCount < storage.length) {
            storage[resumeCount++] = r;
        } else System.out.println("The storage is full!");
    }

    void update(Resume resume) {
        int rIndex = resumeIndex(resume.getUuid());
        if (rIndex == -1) {
            System.out.println("There is no to update: no such resume");
        } else {
            System.out.print("Enter new uuid: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String newUuid = reader.readLine();
                Resume r = new Resume();
                r.setUuid(newUuid);
                storage[rIndex] = r;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    Resume get(String uuid) {
        int rIndex = resumeIndex(uuid);
        if (rIndex == -1) {
            System.out.println("There is no resume with uuid: " + uuid);
        } else {
            return storage[rIndex];
        }
        return null;
    }

    void delete(String uuid) {
        int rIndex = resumeIndex(uuid);
        if (rIndex == -1) {
            System.out.println("Nothing to delete!");
        } else {
            storage[rIndex] = storage[--resumeCount];
            storage[resumeCount] = null;
            System.out.println("The resume with uuid " + uuid + " was deleted!");
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] allResumes = new Resume[resumeCount];
        for (int j = 0; j < resumeCount; j++) {
            allResumes[j] = storage[j];
        }
        return allResumes;
    }

    int size() {
        return resumeCount;
    }
}
