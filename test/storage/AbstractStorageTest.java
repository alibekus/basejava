package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class AbstractStorageTest {

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final String UUID5 = "uuid5";

    private static final Resume RESUME1 = new ResumeTestData(UUID1, "FullName1").fillResume();
    private static final Resume RESUME2 = new ResumeTestData(UUID2, "FullName2").fillResume();
    private static final Resume RESUME3 = new ResumeTestData(UUID3, "FullName3").fillResume();
    private static final Resume RESUME4 = new ResumeTestData(UUID4, "FullName4").fillResume();
    private static final Resume RESUME5 = new ResumeTestData(UUID5, "FullName5").fillResume();

    final Storage storage;

    protected static final File STORAGE_DIR = new File("TestStorageDir");

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
        Assert.assertEquals(RESUME1, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME1, RESUME2, RESUME3, RESUME4);
        Assert.assertEquals(expectedResumes.size(), actualResumes.size());
        Assert.assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME5);
        Assert.assertEquals(RESUME5, storage.get(UUID5));
        Assert.assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
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
        Resume updateResume = new Resume(UUID1,"Dummy");
        storage.update(updateResume);
        Assert.assertEquals(storage.get(UUID1),updateResume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME5);
    }
}