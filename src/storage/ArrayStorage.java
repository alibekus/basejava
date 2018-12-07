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

    @Override
    protected void writeResume(Resume resume, int index) {
        storage[size++] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        storage[index] = storage[--size];
        storage[size] = null;
    }
}
