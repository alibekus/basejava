package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    @Override
    public Resume get(String uuid) {
        Object key = ifExistKey(uuid);
        return doGet(key);
    }

    @Override
    public void save(Resume resume) {
        Object key = ifNotExistKey(resume.getUuid());
        doSave(key, resume);
    }

    @Override
    public void update(Resume resume) {
        Object key = ifExistKey(resume.getUuid());
        doUpdate(key, resume);
    }

    @Override
    public void delete(String uuid) {
        Object key = ifExistKey(uuid);
        doDelete(key);
    }

    protected Object ifNotExistKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            return searchKey;
        } else {
            throw new ExistStorageException(uuid);
        }
    }

    protected Object ifExistKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            return searchKey;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }
}
