package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static storage.AbstractArrayStorage.STORAGE_LIMIT;

public class AbstractArrayStorageTest {

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final String UUID5 = "uuid5";
    private final Resume resume1 = new Resume(UUID1);
    private final Resume resume2 = new Resume(UUID2);
    private final Resume resume3 = new Resume(UUID3);
    private final Resume resume4 = new Resume(UUID4);
    private final Resume resume5 = new Resume(UUID5);
    private Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Test
    public void initResumes() {
        Assert.assertNotNull(resume1);
        Assert.assertNotNull(resume2);
        Assert.assertNotNull(resume3);
        Assert.assertNotNull(resume4);
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(resume1);
        storage.save(resume2);
        storage.save(resume3);
        storage.save(resume4);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        Resume exceptedResume = new Resume(UUID1);
        Assert.assertEquals(exceptedResume, storage.get(UUID1));
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(resume5);
        Assert.assertEquals(storage.get(UUID5), resume5);
        Assert.assertEquals(5, storage.getAll().length);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(new Resume(UUID1));
    }


    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        Assert.assertEquals(3, storage.getAll().length);
        storage.get(UUID1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete("deleteNotExist");
    }

    @Test
    public void update() {
        Resume updatedResume = new Resume(UUID1);
        storage.update(updatedResume);
        Assert.assertEquals(updatedResume, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(new Resume("updateNotExist"));
    }

    @Test
    public void getAll() {
        Resume[] actualResumes = new Resume[]{new Resume("uuid1"), new Resume("uuid2"),
                new Resume("uuid3"), new Resume("uuid4")};
        Resume[] expectedResumes = storage.getAll();
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get("dummy");
    }

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
}