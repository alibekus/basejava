package storage;

import serialization.StorageSerialization;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new StorageSerialization()));
    }

}