package storage;

import model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ArrayStorageTest extends AbstractArrayStorageTest {

    ArrayStorage arrayStorage;

    public ArrayStorageTest() {
        super(new ArrayStorage());
        arrayStorage = (ArrayStorage) super.storage;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getIndex() {
        System.out.println("-------------------------------");
        System.out.println("ArrayStorageTest.getIndex");
        System.out.println("-------------------------------");
        int index = arrayStorage.getIndex(UUID1);
        Assert.assertNotNull(index);
    }

    @Test
    public void writeResume() {
        System.out.println("-------------------------------");
        System.out.println("ArrayStorageTest.writeResume");
        System.out.println("-------------------------------");
        int index = arrayStorage.getIndex(UUID1);
        Resume resume = new Resume(UUID1);
        arrayStorage.writeResume(resume, index);
        Assert.assertNotNull(arrayStorage.get(resume.getUuid()));
    }

    @Test
    public void deleteResume() {
        System.out.println("-------------------------------");
        System.out.println("ArrayStorageTest.deleteResume");
        System.out.println("-------------------------------");
        int index = arrayStorage.getIndex(UUID1);
        Resume deletedResume = arrayStorage.get(UUID1);
        arrayStorage.deleteResume(index);
        Resume[] resumes = arrayStorage.getAll();
        Assert.assertNotSame(deletedResume, resumes[index]);
    }

    @Override
    protected Resume[] getResumes() {
        return arrayStorage.resumes;
    }
}