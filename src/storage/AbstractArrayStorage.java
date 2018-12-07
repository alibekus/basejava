package storage;

import model.Resume;
import storage.Storage;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            System.out.println("model.Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume r) {
        int rIndex = getIndex(r.getUuid());
        if (rIndex >= 0) {
            System.out.println("The resume with " + r.getUuid() + " already exist");
        } else if (size < storage.length) {
            writeResume(r, rIndex);
        } else {
            System.out.println("The storage is full!");
        }
    }

    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex >= 0) {
            deleteResume(rIndex);
            storage[--size] = null;
        } else {
            System.out.println("There is no resume with uuid: " + uuid);
        }
    }

    public void update(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex < 0) {
            System.out.println("There is no to update: no such resume");
        } else {
            storage[rIndex] = resume;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void writeResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}
