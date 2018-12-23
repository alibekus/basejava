package storage;

import model.Resume;

/**
 * Array based resumes for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (resumes[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void writeResume(Resume resume, int index) {
        resumes[size++] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        System.out.println("deleteResume at index " + index);
        resumes[index] = resumes[size - 1];
    }
}
