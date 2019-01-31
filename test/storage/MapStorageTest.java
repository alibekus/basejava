package storage;

import exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }

//    @Ignore
//    @Test(expected = StorageException.class)
//    public void overflowStorage() {
//    }
}