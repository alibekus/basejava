package storage;

import exception.NotExistStorageException;
import exception.OverflowStorageException;
import model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {

    protected static final String UUID1 = "uuid1";
    protected static final String UUID2 = "uuid2";
    protected static final String UUID3 = "uuid3";
    protected static final String UUID4 = "uuid4";
    protected Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        System.out.println("Constructor AbstractArrayStorageTest");
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting up AbstractArrayStorageTest...");
        storage.clear();
        storage.save(new Resume(UUID1));
        storage.save(new Resume(UUID2));
        storage.save(new Resume(UUID3));
        storage.save(new Resume(UUID4));
        System.out.println("End of setting up");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void size() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.size");
        System.out.println("-------------------------------");
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.get");
        System.out.println("-------------------------------");
        Resume exceptedResume = new Resume(UUID1);
        Assert.assertEquals(exceptedResume, storage.get(UUID1));
    }

    @Test
    public void clear() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.clear");
        System.out.println("-------------------------------");
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.save");
        System.out.println("Test save uuid5:");
        System.out.println("-------------------------------");
        Resume savedResume = new Resume("uuid5");
        storage.save(savedResume);
        Assert.assertEquals(storage.get("uuid5"), savedResume);
    }

    @Test
    public void delete() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.delete");
        System.out.println("-------------------------------");
        int sizeBefore = storage.getAll().length;
        storage.delete(UUID1);
        int sizeAfter = storage.getAll().length;
        Assert.assertEquals(sizeBefore - 1, sizeAfter);
    }

    @Test
    public void update() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.update");
        System.out.println("-------------------------------");
        storage.update(new Resume(UUID1));
        Assert.assertEquals(storage.get(UUID1), new Resume(UUID1));
    }

    @Test
    public void getAll() {
        Resume[] resumes = storage.getAll();
        Assert.assertEquals(storage.size(), resumes.length);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        System.out.println("-------------------------------");
        System.out.println("Getting dummy...");
        System.out.println("-------------------------------");
        storage.get("dummy");
    }

    @Test(expected = OverflowStorageException.class)
    public void overflowStorage() {
        System.out.println("-------------------------------");
        System.out.println("AbstractArrayStorageTest.overflowStorage:");
        System.out.println("-------------------------------");
        Resume[] resumesStorage = getResumes();
        storage.clear();
        StringBuilder uuid = new StringBuilder("uuid0");
        try {
            for (int i = 0; i < resumesStorage.length; i++) {
                storage.save(new Resume(uuid.toString()));
                uuid.replace(4, uuid.length(), Integer.toString(i + 1));
            }
        } catch (OverflowStorageException e) {
            Assert.fail("The storage is not yet overflow, but something is wrong");
        }
        uuid.replace(4, uuid.length(), Integer.toString(resumesStorage.length));
        storage.save(new Resume(uuid.toString()));
    }

    abstract protected Resume[] getResumes();
}