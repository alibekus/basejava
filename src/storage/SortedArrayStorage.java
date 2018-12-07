package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void writeResume(Resume resume, int rIndex) {
        rIndex = -rIndex - 1;
        System.arraycopy(storage, rIndex, storage, rIndex + 1, size - rIndex);
        storage[rIndex] = resume;
        size++;
    }

    @Override
    protected void deleteResume(int rIndex) {
        int length = size - rIndex - 1;
        if (length > 0) {
            System.arraycopy(storage, rIndex + 1, storage, rIndex, length);
        }
    }
}
