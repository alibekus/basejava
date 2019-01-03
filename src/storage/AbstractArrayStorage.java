package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] resumes = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public AbstractArrayStorage() {
        super();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return resumes[index];
    }

    @Override
    public void clear() {
        Arrays.fill(resumes, 0, size, null);
        size = 0;
    }

    @Override
    public void save(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex >= 0) {
            throw new ExistStorageException(resume.getUuid());
        } else if (size < resumes.length) {
            writeResume(rIndex, resume);
        } else {
            String uuid = resume.getUuid();
            throw new StorageException("Resume " + uuid + " can't be written. The storage is full!", uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        int rIndex = getIndex(uuid);
        if (rIndex >= 0) {
            deleteResume(rIndex);
            resumes[--size] = null;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int rIndex = getIndex(resume.getUuid());
        if (rIndex < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            resumes[rIndex] = resume;
        }
    }

    @Override
    protected void doSave(Resume resume) {

    }

    @Override
    protected void doDelete(String uuid) {

    }

    @Override
    protected String getSearchKey(String uuid) {
        return null;
    }

    @Override
    protected Resume doGet(String uuid) {
        return null;
    }

    /**
     * @return array, contains only Resumes in resumes (without null)
     */
    @Override
    public Resume[] getAll() {
        return Arrays.copyOfRange(resumes, 0, size);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void writeResume(int index, Resume resume);

    protected abstract void deleteResume(int index);

}
