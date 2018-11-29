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
    public void save(Resume r) {
        super.save(r);
        if (size > 1) {
            sortStorage(storage);
        }
    }

    @Override
    public void update(Resume resume) {
        super.update(resume);
        if (size > 1) {
            sortStorage(storage);
        }
    }

    @Override
    public void delete(String uuid) {
        super.delete(uuid);
        if (size > 1) {
            sortStorage(storage);
        }
    }

    private void sortStorage(Resume[] storage) {
        Resume[] storageNoNull = Arrays.copyOfRange(storage, 0, size);
        Arrays.sort(storageNoNull);
        System.arraycopy(storageNoNull,0,storage,0,storageNoNull.length);
    }
}
