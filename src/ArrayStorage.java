import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage implements Storage{
    private int resumeCount = 0;
    private final int STORAGE_SIZE = 10_000;
    private final Resume[] storage = new Resume[STORAGE_SIZE];

    public void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }

    public void save(Resume r) {
        int rIndex = getIndex(r.getUuid());
        if (rIndex != -1) {
            System.out.println("The resume with " + r.getUuid() + "already exist");
        } else {
            if (resumeCount < storage.length) {
                storage[resumeCount++] = r;
            } else System.out.println("The storage is full!");
        }
    }

    public void update(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex == -1) {
            System.out.println("There is no to update: no such resume");
        } else {
            System.out.print("Enter new uuid: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                String newUuid = reader.readLine();
                resume.setUuid(newUuid);
                storage[rIndex] = resume;
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    public Resume get(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex == -1) {
            System.out.println("There is no resume with uuid: " + uuid);
            return null;
        } else {
            return storage[rIndex];
        }
    }

    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex != -1) {
            storage[rIndex] = storage[--resumeCount];
            storage[resumeCount] = null;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        Resume[] allResumes = new Resume[resumeCount];
        System.arraycopy(storage, 0, allResumes, 0, resumeCount);
        return allResumes;
    }

    public int size() {
        return resumeCount;
    }


    private int getIndex(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid))
                return i;
        }
        return -1;
    }
}
