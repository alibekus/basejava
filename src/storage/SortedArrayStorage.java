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
        int rIndex = getIndex(r.getUuid());
        if (size < storage.length) {
            if (rIndex >= 0) {
                System.out.println("The resume with " + r.getUuid() + " already exist");
            } else if (rIndex < 0) {
                rIndex = Math.abs(rIndex) - 1;
                System.arraycopy(storage, rIndex, storage, rIndex + 1, size - rIndex);
                storage[rIndex] = r;
                size++;
            }
        } else {
            System.out.println("The storage is full!");
        }
    }

    @Override
    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex >= 0) {
            if (rIndex == size - 1) {
                storage[rIndex] = null;
                size--;
            } else {
                System.arraycopy(storage, rIndex + 1, storage, rIndex, size - rIndex - 1);
                storage[size--] = null;
            }
        } else {
            System.out.println("There is no resume with uuid: " + uuid);
        }
    }

    private void sortStorage(Resume[] storage) {
        Resume[] storageNoNull = Arrays.copyOfRange(storage, 0, size);
        Arrays.sort(storageNoNull);
        System.arraycopy(storageNoNull, 0, storage, 0, storageNoNull.length);
    }
}
