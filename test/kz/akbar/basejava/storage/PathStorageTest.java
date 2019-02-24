package kz.akbar.basejava.storage;

import kz.akbar.basejava.storage.serialization.ObjectSerializer;

public class PathStorageTest extends AbstractStorageTest {

    public PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new ObjectSerializer()));
    }
}