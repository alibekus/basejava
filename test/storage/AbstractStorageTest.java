package storage;

import exception.NotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME5);
        Assert.assertEquals(5, storage.size());
        Assert.assertEquals(RESUME5, storage.get(UUID5));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        Assert.assertEquals(3,storage.size());
        storage.get(UUID1);
    }

    @Test
    public void getAll() {
        Resume[] actualResumes = storage.getAll();
        Resume[] expectedResumes = new Resume[]{RESUME1, RESUME2, RESUME3, RESUME4};
        Assert.assertArrayEquals(expectedResumes, actualResumes);
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }

    @Test
    public void update() {
        storage.update(RESUME4);
        Assert.assertEquals(RESUME4, storage.get(RESUME4.getUuid()));
    }

    @Test
    public void get() {
        Resume expectedResume = storage.get(UUID3);
        Assert.assertEquals(expectedResume, storage.get(UUID3));
    }
}