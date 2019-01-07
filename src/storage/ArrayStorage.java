package storage;

import model.Resume;

/**
 * Array based resumes for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (resumes[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void writeResume(int index, Resume resume) {
        resumes[size++] = resume;
    }

    @Override
    protected void deleteResume(int index) {
        resumes[index] = resumes[size - 1];
    }
}
