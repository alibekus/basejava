package storage;

import storage.serialization.JsonSerializer;

public class JsonStorageTest extends AbstractStorageTest {

    public JsonStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonSerializer()));
    }
}