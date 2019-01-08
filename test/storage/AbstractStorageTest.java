package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

import static storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractStorageTest {

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final String UUID5 = "uuid5";

    private static final Resume RESUME1 = new Resume(UUID1);
    private static final Resume RESUME2 = new Resume(UUID2);
    private static final Resume RESUME3 = new Resume(UUID3);
    private static final Resume RESUME4 = new Resume(UUID4);
    private static final Resume RESUME5 = new Resume(UUID5);

    private Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME1);
        storage.save(RESUME2);
        storage.save(RESUME3);
        storage.save(RESUME4);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        Resume expectedResume = new Resume(UUID1);
        Assert.assertEquals(expectedResume, storage.get(expectedResume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID5);
    }

    @Test
    public void getAll() {
        Resume[] actualResumes = new Resume[]{RESUME1, RESUME2, RESUME3, RESUME4};
        Resume[] expectedResumes = storage.getAll();
        Assert.assertEquals(expectedResumes.length, actualResumes.length);
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME5);
        Assert.assertEquals(RESUME5, storage.get(RESUME5.getUuid()));
        Assert.assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID1));
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void overflowStorage() {
        storage.clear();
        try {
            for (int i = 0; i < STORAGE_LIMIT; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException e) {
            Assert.fail("The storage is not yet overflow, but something is wrong");
        }
        storage.save(new Resume());
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        Assert.assertEquals(3, storage.size());
        storage.get(UUID1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID5);
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume(UUID1);
        storage.update(updatedResume);
        Assert.assertSame(updatedResume, storage.get(updatedResume.getUuid()));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME5);
    }
}