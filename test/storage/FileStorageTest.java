package storage;

import storage.serialization.ObjectSerializer;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectSerializer()));
    }

}