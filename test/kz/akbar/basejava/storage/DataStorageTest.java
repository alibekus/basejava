package kz.akbar.basejava.storage;

import kz.akbar.basejava.storage.serialization.DataSerializer;

public class DataStorageTest extends AbstractStorageTest {

    public DataStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataSerializer()));
    }
}