package storage;

import exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class MapUuidStorageTest extends AbstractStorageTest {

    public MapUuidStorageTest() {
        super(new MapUuidStorage());
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void overflowStorage() {
    }
}