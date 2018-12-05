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
                if (size == 0) {
                    storage[size++] = r;
                } else {
                    int k = 0;
                    for (int i = 0; i < size; i++) {
                        if (r.compareTo(storage[i]) < 0) {
                            System.arraycopy(storage, i, storage, i + 1, size - i);
                            storage[i] = r;
                            k++;
                            size++;
                            break;
                        }
                    }
                    if (k == 0) {
                        storage[size++] = r;
                    }
                }
            }
        } else {
            System.out.println("The storage is full!");
        }
    }

    @Override
    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex >= 0) {
            System.arraycopy(storage,rIndex+1,storage,rIndex,size-rIndex);
            storage[size--] = null;
        } else {
            System.out.println("There is no resume with uuid: " + uuid);
        }
    }

    private void sortStorage(Resume[] storage) {
        Resume[] storageNoNull = Arrays.copyOfRange(storage, 0, size);
        Arrays.sort(storageNoNull);
        System.arraycopy(storageNoNull,0,storage,0,storageNoNull.length);
    }
}
