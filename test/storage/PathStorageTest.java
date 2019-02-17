package storage;

import storage.serialization.StorageSerializer;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new StorageSerializer()));
    }
}