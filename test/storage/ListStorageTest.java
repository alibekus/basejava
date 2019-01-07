package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListStorageTest extends storage.AbstractStorageTest {

    private static final String UUID1 = "UUID1";
    private static final String UUID2 = "UUID2";
    private static final String UUID3 = "UUID3";
    private static final String UUID4 = "UUID4";
    private static final String UUID5 = "UUID5";

    private static final Resume RESUME1 = new Resume(UUID1);
    private static final Resume RESUME2 = new Resume(UUID2);
    private static final Resume RESUME3 = new Resume(UUID3);
    private static final Resume RESUME4 = new Resume(UUID4);
    private static final Resume RESUME5 = new Resume(UUID5);

    Storage storage = new ListStorage();

    @Before
    public void setUp() throws Exception {
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

    @Test(expected = ExistStorageException.class)
    public void save() {
        storage.save(RESUME5);
        Assert.assertEquals(5, storage.size());
        Assert.assertSame(RESUME5, storage.get(UUID5));
        storage.save(RESUME4);
    }

    @Test(expected = NotExistStorageException.class)
    public void update() {
        storage.update(RESUME1);
        Assert.assertSame(RESUME1, storage.get(UUID1));
        storage.update(RESUME5);
    }

    @Test
    public void get() {
        Assert.assertSame(RESUME2, storage.get(UUID2));
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID2);
        Assert.assertEquals(3, storage.size());
        storage.delete(UUID5);
    }

    @Test
    public void getAll() {
        Resume[] resumes = {RESUME1, RESUME2, RESUME3, RESUME4};
        Assert.assertArrayEquals(resumes, storage.getAll());
    }

    @Test
    public void size() {
        Assert.assertEquals(4, storage.size());
    }
}