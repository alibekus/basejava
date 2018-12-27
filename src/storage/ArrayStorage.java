package storage;

import model.Resume;

import java.util.Collection;

/**
 * Array based resumes for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public ArrayStorage() {
        super();
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (resumes[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    protected void writeResume(int index, Resume resume) {
        resumes[size++] = resume;
    }

    protected void deleteResume(int index) {
        resumes[index] = resumes[size - 1];
    }
}
