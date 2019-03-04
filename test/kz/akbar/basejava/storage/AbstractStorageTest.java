package kz.akbar.basejava.storage;

import kz.akbar.basejava.Config;
import kz.akbar.basejava.exception.ExistStorageException;
import kz.akbar.basejava.exception.NotExistStorageException;
import kz.akbar.basejava.model.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.*;

public class AbstractStorageTest {

    private static final String UUID1 = UUID.randomUUID().toString();
    private static final String UUID2 = UUID.randomUUID().toString();
    private static final String UUID3 = UUID.randomUUID().toString();
    private static final String UUID4 = UUID.randomUUID().toString();
    private static final String UUID5 = UUID.randomUUID().toString();

    private static final Resume RESUME1 = ResumeTestData.getResumeInstance(UUID1, "FullName1");
    private static final Resume RESUME2 = ResumeTestData.getResumeInstance(UUID2, "FullName2");
    private static final Resume RESUME3 = ResumeTestData.getResumeInstance(UUID3, "FullName3");
    private static final Resume RESUME4 = ResumeTestData.getResumeInstance(UUID4, "FullName4");
    private static final Resume RESUME5 = ResumeTestData.getResumeInstance(UUID5, "FullName5");

    final Storage storage;

    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();

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
        assertEquals(4, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME1, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID5);
    }

    @Test
    public void getAllSorted() {
        List<Resume> actualResumes = storage.getAllSorted();
        List<Resume> expectedResumes = Arrays.asList(RESUME1, RESUME2, RESUME3, RESUME4);
        assertEquals(expectedResumes.size(), actualResumes.size());
        assertEquals(expectedResumes, actualResumes);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void save() {
        storage.save(RESUME5);
        assertEquals(RESUME5, storage.get(UUID5));
        assertEquals(5, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID1);
        assertEquals(3, storage.size());
        storage.get(UUID1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(UUID5);
    }

    @Test
    public void update() {
        Resume updateResume = new Resume(UUID1, "Dummy");
        storage.update(updateResume);
        assertEquals(updateResume, storage.get(UUID1));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME5);
    }
}