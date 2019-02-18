package storage;

import storage.serialization.DataSerializer;

public class DataStorageTest extends AbstractStorageTest {

    public DataStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataSerializer()));
    }
}