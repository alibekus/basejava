package storage;

import storage.serialization.XmlSerializer;

public class XmlStorageTest extends AbstractStorageTest {

    public XmlStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new XmlSerializer()));
    }
}