package kz.akbar.basejava.storage;

import kz.akbar.basejava.storage.serialization.JsonSerializer;

public class JsonStorageTest extends AbstractStorageTest {

    public JsonStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new JsonSerializer()));
    }
}