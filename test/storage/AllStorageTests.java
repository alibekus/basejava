package storage;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArrayStorageTest.class,
        SortedArrayStorageTest.class,
        ListStorageTest.class,
        MapUuidStorageTest.class,
        MapStorageTest.class,
        FileStorageTest.class,
        PathStorageTest.class,
        XmlStorageTest.class,
        JsonStorageTest.class,
        DataStorageTest.class
})
public class AllStorageTests {
}
