package storage;

import model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    public void save(Resume r) {
        int rIndex = getIndex(r.getUuid());
        if (rIndex >= 0) {
            System.out.println("The resume with " + r.getUuid() + " already exist");
        } else {
            if (size < storage.length) {
                storage[size++] = r;
            } else {
                System.out.println("The storage is full!");
            }
        }
    }

    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex != -1) {
            storage[rIndex] = storage[--size];
            storage[size] = null;
        } else {
            System.out.println("There is no resume with uuid: " + uuid);
        }
    }
}
