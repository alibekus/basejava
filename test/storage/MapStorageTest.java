package storage;

import model.Resume;

import java.util.LinkedHashMap;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage(new LinkedHashMap<String,Resume>()));
    }
}