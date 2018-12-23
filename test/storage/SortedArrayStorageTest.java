package storage;

import model.Resume;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {

    protected SortedArrayStorage sortedArrayStorage;

    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
        sortedArrayStorage = (SortedArrayStorage) super.storage;
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
        System.out.println("SortedArrayStorageTest.writeResume");
        System.out.println("-------------------------------");
        int index = sortedArrayStorage.getIndex(UUID1);
        Assert.assertNotNull(index);
    }

    @Test
    public void writeResume() {
        System.out.println("-------------------------------");
        System.out.println("SortedArrayStorageTest.writeResume");
        System.out.println("-------------------------------");
        int index = sortedArrayStorage.getIndex(UUID2);
        System.out.println("uuid2 index = " + index);
        if (index >= 0) {
            index = -(index + 1);
        }
        Resume savedResume = new Resume(UUID2);
        sortedArrayStorage.writeResume(savedResume, index);
        Assert.assertEquals(savedResume, sortedArrayStorage.get(savedResume.getUuid()));
    }

    @Test
    public void deleteResume() {
        System.out.println("-------------------------------");
        System.out.println("SortedArrayStorageTest.deleteResume");
        System.out.println("-------------------------------");
        int index = sortedArrayStorage.getIndex(UUID1);
        Resume deletedResume = storage.get(UUID1);
        sortedArrayStorage.deleteResume(index);
        Resume[] resumes = sortedArrayStorage.getAll();
        Assert.assertNotSame(deletedResume, resumes[index]);
    }

    @Override
    protected Resume[] getResumes() {
        return sortedArrayStorage.resumes;
    }
}