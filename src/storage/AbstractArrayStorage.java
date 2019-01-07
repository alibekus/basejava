package storage;

import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] resumes = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume get(String uuid) {
        return doGet(uuid);
    }

    @Override
    public void clear() {
        Arrays.fill(resumes, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        int rIndex = (int) ifNotExistKey(resume.getUuid());
        if (size < resumes.length) {
            doSave(rIndex, resume);
        } else {
            String uuid = resume.getUuid();
            throw new StorageException("Resume " + uuid + " can't be written. The storage is full!", uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        doUpdate(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Object uuid, Resume resume) {
        int rIndex = (int) ifExistKey((String) uuid);
        resumes[rIndex] = resume;
    }

    @Override
    public void delete(String uuid) {
        int rIndex = (int) ifExistKey(uuid);
        doDelete(rIndex);
        resumes[--size] = null;
    }


    @Override
    protected Resume doGet(Object uuid) {
        int index = (int) ifExistKey((String) uuid);
        return resumes[index];
    }

    /**
     * @return array, contains only Resumes in resumes (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(resumes, 0, size);
    }
}
