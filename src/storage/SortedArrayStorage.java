package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(resumes, 0, size, searchKey);
    }

    @Override
    protected void writeResume(Resume resume, int rIndex) {
        rIndex = -rIndex - 1;
        System.arraycopy(resumes, rIndex, resumes, rIndex + 1, size - rIndex);
        resumes[rIndex] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int rIndex) {
        int length = size - rIndex - 1;
        if (length > 0) {
            System.arraycopy(resumes, rIndex + 1, resumes, rIndex, length);
        }
    }
}
