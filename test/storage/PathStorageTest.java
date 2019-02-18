package storage;

import storage.serialization.ObjectSerializer;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectSerializer()));
    }
}