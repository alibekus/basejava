package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] resumes = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    protected abstract void writeResume(int index, Resume resume);

    @Override
    protected Resume doGet(Object index) {
        return resumes[(int) index];
    }

    @Override
    public void doSave(Object index, Resume resume) {
        int rIndex = (int) index;
        if (size < resumes.length) {
            writeResume(rIndex, resume);
        } else {
            String uuid = resume.getUuid();
            throw new StorageException("Resume " + uuid + " can't be written. The storage is full!", uuid);
        }
    }

    @Override
    protected void doUpdate(Object index, Resume resume) {
        resumes[(int) index] = resume;
    }

    /**
     * @return array, contains only Resumes in resumes (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(resumes, 0, size);
    }

    @Override
    public void clear() {
        Arrays.fill(resumes, 0, size, null);
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }
}
