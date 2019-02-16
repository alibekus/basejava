package storage;

import serialization.StorageSerialization;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new StorageSerialization()));
    }
}