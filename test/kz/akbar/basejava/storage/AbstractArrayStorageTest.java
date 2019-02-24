package kz.akbar.basejava.storage;

import kz.akbar.basejava.exception.StorageException;
import kz.akbar.basejava.model.Resume;
import org.junit.Assert;
import org.junit.Test;

import static kz.akbar.basejava.storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void overflowStorage() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume(""));
            }
        } catch (StorageException e) {
            Assert.fail("The kz.akbar.basejava.storage is not yet overflow, but something is wrong");
        }
        storage.save(new Resume(""));
    }
}