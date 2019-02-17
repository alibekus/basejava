package storage;

import storage.serialization.StorageSerializer;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StorageSerializer()));
    }

}